import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Canonicalize {

	public static void main(String[] args) {
		
		while (true) { // run indefinitely, end with control-c
			
			try {
				Scanner scan = new Scanner(System.in);;
				String modeSelected = "";
				
				System.out.print("File(F) or Interactive(I) Mode>");
				String modeChoice = scan.nextLine();
				
				String fileRegex = "[Ff](ile)?";
				String interactiveRegex = "[Ii](nteractive)?";
				
				if (modeChoice.matches(fileRegex)) {
					// System.out.println("File Mode");
					modeSelected = "F";
				} else if (modeChoice.matches(interactiveRegex)) {
					// System.out.println("Interactive Mode");
					modeSelected = "I";
				} else {
					System.out.println("Invalid Selection");
					continue;
				}

				if (modeSelected.matches("F")) { // open file
					// for each line of input in file, parse, then output to .out file
					
					//input
					System.out.print("Enter Filename>");
					String fileName = scan.next();
					FileReader fileReader = new FileReader(fileName);
					BufferedReader bufferedReader = new BufferedReader(fileReader);

					//output
					String outputFileName = fileName.split("\\.[a-zA-Z]+$")[0] + ".out";
		            FileWriter fileWriter = new FileWriter(outputFileName);
		            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

					// read line by line
					String line = null;
					while ((line = bufferedReader.readLine()) != null) {
						System.out.println(line);
						String output = createOutput(parseInput(line));
						System.out.println(output);
						bufferedWriter.write(output);
					}
					
					bufferedReader.close();
		            bufferedWriter.close();
				} else if (modeSelected.matches("I")) { //interactive mode
					System.out.print("Equation>");
					String input = scan.nextLine();
					System.out.println(createOutput(parseInput(input)));
				} else {
					System.out.println("ERROR: modeSelected");
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("No File Found");
			} catch (IOException e) {
				System.out.println("File Error");
			} catch(MalformedInputException e){
				System.out.println("Malformed Input");
			} catch(NumberFormatException e){
				System.out.println("Malformed Input"); //maybe brackets
			} catch (Exception e) {
				System.out.println("Error");
			}

		}
	}

	protected static ArrayList<Term> parseInput(String input) throws MalformedInputException{
		
		String[] splitEquation = input.split("=");

		if(splitEquation.length < 2){
			throw new MalformedInputException();
		}
		// check both sides of the split
		String splitEquationLeft = splitEquation[0].trim();
		String splitEquationRight = splitEquation[1].trim();

		// split by +,-
		// TODO: consider brackets ()
		// uses ?<= look ahead, and ?= look behind
		// TODO: need to handle variables in any order
		String termSplitRegex = "(?<=[+-])|(?=[+-])";

		String[] leftExpression = splitEquationLeft.split(termSplitRegex);
		String[] rightExpression = splitEquationRight.split(termSplitRegex);

		ArrayList<Term> leftTerms = new ArrayList<Term>();
		ArrayList<Term> rightTerms = new ArrayList<Term>();

		createTerms(leftTerms, leftExpression);
		createTerms(rightTerms, rightExpression);

		// combine left and right
		ArrayList<Term> ct = combineTerms(leftTerms, rightTerms);
		//printOutput(ct);
		return ct;
	}

	// format double to show cleanly
	private static String cleanDoubleFormat(Double d) {
		if (d == d.intValue())
			return String.format("%d", d.intValue());
		else
			return String.format("%s", d);
	}

	// returns output from arraylist of terms
	protected static String createOutput(ArrayList<Term> ct) {
		// print out final result in clean format
		String output = "";
		for (int i = 0; i < ct.size(); i++) {
			// System.out.println("coeff: " + t.getCoefficient() + " var: " +
			// t.getVariable());

			Term t = ct.get(i);
			String coef = "";
			// format
			if (t.getCoefficient() == 1.0 || t.getCoefficient() == -1.0) {
				coef = "";
			} else if (t.getCoefficient() < 0 && i != 0) {
				coef = cleanDoubleFormat(t.getCoefficient() * -1);
			} else {
				coef = cleanDoubleFormat(t.getCoefficient());
			}
			if (i == 0) {
				output = output + coef + t.getVariable() + " ";
				continue;
			}

			// print
			if (t.getCoefficient() < 0) {
				output = output + "- " + coef + t.getVariable() + " ";
			} else {
				output = output + "+ " + coef + t.getVariable() + " ";
			}

		}
		output = output + "= 0\n";
		
		return output;
	}

	// search for matching variables in left and right equation sides
	private static ArrayList<Term> combineTerms(ArrayList<Term> lt, ArrayList<Term> rt) throws MalformedInputException{
		ArrayList<Term> ct = new ArrayList<Term>();

		for (int i = 0; i < lt.size(); i++) {
			// System.out.println("lv: " + lt.get(i).getVariable() + "\n");
			for (int j = 0; j < rt.size(); j++) {
				if (lt.get(i).getVariable().equals(rt.get(j).getVariable())) {
					// same variable, add coefficients

					Double combinedCoef = lt.get(i).getCoefficient() - rt.get(j).getCoefficient();
					// System.out.println("lf: " + lt.get(i).getCoefficient()
					// + " rf: " + rt.get(j).getCoefficient()
					// + " res: " + combinedCoef
					// + " var: " + lt.get(i).getVariable());

					String operator = "";
					if (combinedCoef < 0) {
						operator = "-";
						combinedCoef = combinedCoef * (-1);
					} else if (combinedCoef > 0) {
						operator = "+";
					} else if (combinedCoef == 0) { // skip creating new term,
													// just delete
						// remove from leftTerm and rightTerm
						lt.remove(i);
						i--;
						rt.remove(j);
						continue;
					}

					String combinedVariable = lt.get(i).getVariable();

					Term combinedTerm = new Term(operator, combinedCoef + combinedVariable);
					ct.add(combinedTerm);

					// remove from leftTerm and rightTerm
					lt.remove(i);
					i--;
					rt.remove(j);
					continue;
				} else {
					// System.out.print("lv: " + lt.get(i).getVariable() + " rv:
					// " + rt.get(j).getVariable() + "\n");
				}

			}
		}

		// add the leftovers
		if (!lt.isEmpty()) {
			ct.addAll(lt);
		}
		if (!rt.isEmpty()) {
			for (Term t : rt) {
				if (t.getCoefficient() > 0) { //positive on the right, minus on the left
					Term leftOverRightTerm = new Term("-", t.getCoefficient() + t.getVariable());
					ct.add(leftOverRightTerm);
				}else{ //minus on the right, positive on left
					Term leftOverRightTerm = new Term("+", t.getCoefficient()*-1 + t.getVariable());
					ct.add(leftOverRightTerm);
				}
			}
		}

		return ct;
	}

	private static void createTerms(List<Term> termsList, String[] expressions) throws MalformedInputException{
		String exponent = "+";

		for (int i = 0; i < expressions.length; i++) {
			expressions[i] = expressions[i].trim();

			// odd or even determines exponent or term
			if (i % 2 != 0) { // even
				exponent = expressions[i];
			} else { // odd
				// System.out.println("exponent: " + exponent);
				Term t = new Term(exponent, expressions[i]);
				termsList.add(t);
			}
		}
	}

}
