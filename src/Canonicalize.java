import java.util.ArrayList;
import java.util.List;

public class Canonicalize {

	public static void main(String[] args) {
		
//		System.out.println("Read Input From File? (Y/N)?  ");
//		
//		System.out.println("Read Input From Console? (Y/N)? ");
		
		String input = "x^2 + 3.5xy + y = y^2 - xy + y";
		
		String[] splitEquation = input.split("=");
		
		//check both sides of the split
		String splitEquationLeft = splitEquation[0].trim();
		String splitEquationRight = splitEquation[1].trim();
		
		System.out.println("splitInputLeft: " + splitEquationLeft + " splitInputRight: " + splitEquationRight);
		
		//leftside
		//split by +,-,(,)
		//TODO: /,*?
		//TODO: consider brackets ()
		//uses ?<= look ahead, and ?= look behind
		String termSplitRegex = "(?<=[+-])|(?=[+-])" ;
		
		String[] leftExpression = splitEquationLeft.split(termSplitRegex);
		String[] rightExpression = splitEquationRight.split(termSplitRegex);
		List<Term> leftTerms = new ArrayList<Term>();
		String exponent = "+";
		for(int i=0; i<leftExpression.length; i++){
			leftExpression[i] = leftExpression[i].trim();
			String le = leftExpression[i]; //convenience
			
			
			//odd or even determines exponent or term
			if(i%2 != 0){ //even
				exponent = leftExpression[i];
			}else{ //odd
				System.out.println("exponent: " + exponent);
				Term t = new Term(exponent, leftExpression[i]);
				leftTerms.add(t);
			}

		}
//		System.out.println("\n");
//		for(String x: splitInput_RightItems){
//			System.out.println(x.trim());
//		}
		//get the integer value
		
		
		
		
		
	}
	
//	String[] mergeOperator(String[] equation){
//		String[] mergedEquation;
//		for(String x: equation){
//			if(x.equals("+"))
//		}
//	}
	
	private class equationsParts{
		
		public Integer coefficient;
		public String variable;
		public Integer operator;
		
		//constants?
		
	}
	

}
