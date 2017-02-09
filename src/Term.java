/*
 * This class represents a term in an equation
 * ie. ax^k
 */
public class Term{

	private Integer operator;
	private Double coefficient;
	private String variable;
	
	//constructor, user will give coefficient and term
	//constructor will break down into components
	public Term(String signOperator, String term) throws MalformedInputException{
		//initial values
		operator = 0;
		coefficient = 0.0;
		variable = "";
		try{
			convertSignOperator(signOperator);
			splitCoefficientAndVariable(term);
			combineOperatorAndCoefficient();
		}catch(MalformedInputException e){
			throw e;
		}
		
		
	}
	
	//first thing to do
	//takes + or -, convert to 1 or -1
	//takes +/- sets operator to 1/-1
	void convertSignOperator(String signOperator) throws MalformedInputException{
		if(signOperator.equals("+")){
			operator = 1;
		}else if(signOperator.equals("-")){
			operator = -1;
		}else{
			throw new MalformedInputException();
		}
	}
	
	//2nd thing done
	//break down the term into coefficient and variable
	//could probably move this to Canonicalize class instead
	String splitTermRegex = "(?<=\\d)(?=[a-zA-Z])";
	void splitCoefficientAndVariable(String term){
		String[] terms = term.split(splitTermRegex);
		if(terms.length == 1 && terms[0].matches("^[a-zA-Z].*")){ //just variable
			//System.out.println("term2: " + terms[0]);
			coefficient = 1.0;
			variable = terms[0];
		}else if(terms.length == 1 && terms[0].matches("^\\d.*")){ //just coeff
			//System.out.println("term2: " + terms[0]);
			coefficient = Double.valueOf(terms[0]);
		}else{
			//System.out.println("term1: " + terms[0] + " terms2: " + terms[1]);
			coefficient = Double.valueOf(terms[0]);
			variable = terms[1];
		}
	}

	//3rd thing done
	//combine operator and coefficient
	void combineOperatorAndCoefficient(){
		coefficient = coefficient*operator;
	}
	
	//getters and setters
	Double getCoefficient(){
		return coefficient;
	}
	String getVariable(){
		return variable;
	}
	Integer getOperator(){
		return operator;
	}
	void setOperator(Integer op){
		operator = op;
	}
	void setCoefficient(Double co){
		coefficient = co;
	}
	void setVariable(String va){
		variable = va;
	}
	
	
}
