import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Canonicalize {

	public static void main(String[] args) {
		
//		System.out.println("Read Input From File? (Y/N)?  ");
//		
//		System.out.println("Read Input From Console? (Y/N)? ");
		
		String input = "3x^2 + 3.5xy - 3.3y = y^2 - xy + y + x^2";
		
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
		//TODO: need to handle - -number
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
//			System.out.print("coef: " + t.getCoefficient() + " varaiabe: " + t.getVariable() + ". \n");
//		}
//		for(Term t: rightTerms){
//			System.out.print("coef: " + t.getCoefficient() + " varaiabe: " + t.getVariable() + ". \n");
//		}
		
		//combine left and right
		ArrayList<Term> combinedTerms = new ArrayList<Term>();
//		Term[] leftTermsArray = (Term[])leftTerms.toArray();
//		Term[] rightTermsArray = (Term[])rightTerms.toArray();
		
//		if(leftTermsArray.length > rightTermsArray.length){
//			
//		}
		
		//TODO: assume that coefficients only appear once in equation, error if not
		for(int i=0; i<leftTerms.size(); i++){
			for(int j=0; j<rightTerms.size(); j++){
				if(leftTerms.get(i).getVariable().equals(rightTerms.get(j).getVariable())){
					//same variable, add coefficients
					Double combinedCoef = leftTerms.get(i).getCoefficient() - rightTerms.get(j).getCoefficient();
					String combinedVariable = leftTerms.get(i).getVariable();
					String operator = "";
					if(combinedCoef < 0){
						operator = "-";
						combinedCoef = combinedCoef*(-1);
					}else{
						operator = "+";
					}
					Term combinedTerm = new Term(operator, combinedCoef + combinedVariable);
					combinedTerms.add(combinedTerm);
					
					//remove from leftTerm and rightTerm
					leftTerms.remove(i);
					rightTerms.remove(j);
				}
			}
		}
		
		//add the leftovers
		if(!leftTerms.isEmpty()){
			combinedTerms.addAll(leftTerms);
		}
		if(!rightTerms.isEmpty()){
			for(Term t: rightTerms){
				if(t.getCoefficient() > 0){
					Term leftOverRightTerm = new Term("-", t.getCoefficient() + t.getVariable());
					combinedTerms.add(leftOverRightTerm);
				}
			}
		}
		
		for(Term t: combinedTerms){
			System.out.println("coeff: " + t.getCoefficient() + " var: " + t.getVariable());
		}
		
		
//		String exponent = "+";
//		
//		for(int i=0; i<leftExpression.length; i++){
//			leftExpression[i] = leftExpression[i].trim();
//	
//			//odd or even determines exponent or term
//			if(i%2 != 0){ //even
//				exponent = leftExpression[i];
//			}else{ //odd
//				System.out.println("exponent: " + exponent);
//				Term t = new Term(exponent, leftExpression[i]);
//				leftTerms.add(t);
//			}
//
//		}
//		System.out.println("\n");
//		for(String x: splitInput_RightItems){
//			System.out.println(x.trim());
//		}
		//get the integer value
		
	}
	
	
	static void createTerms(List<Term> termsList, String[] expressions){
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
	
//	String[] mergeOperator(String[] equation){
//		String[] mergedEquation;
//		for(String x: equation){
//			if(x.equals("+"))
//		}
//	}
//	
//	private class equationsParts{
//		
//		public Integer coefficient;
//		public String variable;
//		public Integer operator;
//		
//		//constants?
//		
//	}
	

}
