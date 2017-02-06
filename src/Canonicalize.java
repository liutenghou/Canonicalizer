import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Canonicalize {

	public static void main(String[] args) {
		
//		System.out.println("Read Input From File? (Y/N)?  ");
//		
//		System.out.println("Read Input From Console? (Y/N)? ");
		Scanner scan = new Scanner(System.in);
		
		String modeSelected = "";
		while(true){
			System.out.print("File(F) or Interactive(I) Mode>");
			String modeChoice = scan.nextLine();
			String fileRegex = "[Ff](ile)?";
			String interactiveRegex = "[Ii](nteractive)?";
			if(modeChoice.matches(fileRegex)){
				//System.out.println("File Mode");
				modeSelected = "F";
				break;
			}else if(modeChoice.matches(interactiveRegex)){
				//System.out.println("Interactive Mode");
				modeSelected = "I";
				break;
			}else{
				System.out.println("Invalid Selection");
			}
		}
		
		if(modeSelected.matches("F")){ //open file
			
		}else if(modeSelected.matches("I")){
			while(true){
				System.out.print("Equation>");
				String input = scan.nextLine();
				parseAndPrintInput(input);
				
			}
			
		}else{
			System.out.println("ERROR: modeSelected");
		}
		
		scan.close();
		
	}
	
	private static void parseAndPrintInput(String input){

		//input = "3x^2 + 3.5xy - 3.3y = y^2 - 555xy + y + x^2";
		
		String[] splitEquation = input.split("=");
		
		//check both sides of the split
		String splitEquationLeft = splitEquation[0].trim();
		String splitEquationRight = splitEquation[1].trim();
		
		//System.out.println("splitInputLeft: " + splitEquationLeft + " splitInputRight: " + splitEquationRight);
		
		//leftside
		//split by +,-,(,)
		//TODO: /,*?
		//TODO: consider brackets ()
		//uses ?<= look ahead, and ?= look behind
		//TODO: need to handle variables in any order
		String termSplitRegex = "(?<=[+-])|(?=[+-])" ;
		
		String[] leftExpression = splitEquationLeft.split(termSplitRegex);
		String[] rightExpression = splitEquationRight.split(termSplitRegex);
		
		ArrayList<Term> leftTerms = new ArrayList<Term>();
		ArrayList<Term> rightTerms = new ArrayList<Term>();
		
		createTerms(leftTerms, leftExpression);
		createTerms(rightTerms, rightExpression);
		
		//Test Code
//		for(Term t: leftTerms){
//			System.out.print("coef: " + t.getCoefficient() + " variable: " + t.getVariable() + ". \n");
//		}
//		System.out.println("");
//		for(Term t: rightTerms){
//			System.out.print("coef: " + t.getCoefficient() + " variable: " + t.getVariable() + ". \n");
//		}
//		System.out.println("");
		
		//combine left and right
		ArrayList<Term> ct = combineTerms(leftTerms, rightTerms);
		printOutput(ct);
		
	}
	
	//format double to show cleanly
	private static String cleanDoubleFormat(Double d)
	{
	    if(d == d.intValue())
	        return String.format("%d",d.intValue());
	    else
	        return String.format("%s",d);
	}
	
	//prints the final output
	private static void printOutput(ArrayList<Term> ct){
		//print out final result in clean format
		for(int i=0; i<ct.size(); i++){
			//System.out.println("coeff: " + t.getCoefficient() + " var: " + t.getVariable());
			
			Term t = ct.get(i);
			String coef = "";
			//format
			if(t.getCoefficient() == 1.0 || t.getCoefficient() == -1.0){
				coef = "";
			}else if(t.getCoefficient() < 0 && i != 0){
				coef = cleanDoubleFormat(t.getCoefficient()*-1);
			}else{
				coef = cleanDoubleFormat(t.getCoefficient());
			}
			if(i==0){
				System.out.print(coef + t.getVariable() + " ");
				continue;
			}
			
			//print
			if(t.getCoefficient() < 0){
				System.out.print("- " + coef + t.getVariable() + " ");
			}else{
				System.out.print("+ " + coef + t.getVariable() + " ");
			}

		}
		System.out.print("= 0\n");
	}
	
	//TODO: assume that coefficients only appear once per side, error if not
	//search for matching variables in left and right equation sides
	private static ArrayList<Term> combineTerms(ArrayList<Term> lt, ArrayList<Term> rt){
		ArrayList<Term> ct = new ArrayList<Term>();
		
		for(int i=0; i<lt.size(); i++){
			System.out.println("lv: " + lt.get(i).getVariable() + "\n");
			for(int j=0; j<rt.size(); j++){
				if(lt.get(i).getVariable().equals(rt.get(j).getVariable())){
					//same variable, add coefficients
					
					Double combinedCoef = lt.get(i).getCoefficient() - rt.get(j).getCoefficient();
//					System.out.println("lf: " + lt.get(i).getCoefficient()
//							+ " rf: " + rt.get(j).getCoefficient()
//							+ " res: " + combinedCoef
//							+ " var: " + lt.get(i).getVariable());
					
					String operator = "";
					if(combinedCoef < 0){
						operator = "-";
						combinedCoef = combinedCoef*(-1);
					}else if(combinedCoef > 0){
						operator = "+";
					}else if(combinedCoef == 0){ //skip creating new term, just delete
						//remove from leftTerm and rightTerm
						lt.remove(i);
						i--;
						rt.remove(j);
						continue;
					}
					
					String combinedVariable = lt.get(i).getVariable();
					
					Term combinedTerm = new Term(operator, combinedCoef + combinedVariable);
					ct.add(combinedTerm);
					
					//remove from leftTerm and rightTerm
					lt.remove(i);
					i--;
					rt.remove(j);
					continue;
				}else{
//					System.out.print("lv: " + lt.get(i).getVariable() + " rv: " + rt.get(j).getVariable() + "\n");
				}
				
			}
		}
		
		//add the leftovers
		if(!lt.isEmpty()){
			ct.addAll(lt);
		}
		if(!rt.isEmpty()){
			for(Term t: rt){
				if(t.getCoefficient() > 0){
					Term leftOverRightTerm = new Term("-", t.getCoefficient() + t.getVariable());
					ct.add(leftOverRightTerm);
				}
			}
		}
		
		return ct;
	}
	
	private static void createTerms(List<Term> termsList, String[] expressions){
		String exponent = "+";
	
		for(int i=0; i<expressions.length; i++){
			expressions[i] = expressions[i].trim();
	
			//odd or even determines exponent or term
			if(i%2 != 0){ //even
				exponent = expressions[i];
			}else{ //odd
				//System.out.println("exponent: " + exponent);
				Term t = new Term(exponent, expressions[i]);
				termsList.add(t);
			}
		}
	}
	
}
