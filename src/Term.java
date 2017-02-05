
public class Term {

	private Integer operator;
	private Double coefficient;
	private String variable;
	//TODO: exponent
	
	//constructor, user will give coefficient and term
	//constructor will break down into components
	public Term(String signOperator, String term){
		convertSignOperator(signOperator);
		splitToCOandVA(term);
		combineOPandCO();
		
	}
	
	//break down the term into coefficient and variable
	//could probably move this to Canonicalize class instead
	String splitTermRegex = "(?<=\\d)(?=[a-zA-Z])";
	void splitToCOandVA(String term){
		String[] terms = term.split(splitTermRegex);
		if(terms.length == 1){
			//System.out.println("term2: " + terms[0]);
			coefficient = 1.0;
			variable = terms[0];
			
		}else{
			//System.out.println("term1: " + terms[0] + " terms2: " + terms[1]);
			coefficient = Double.valueOf(terms[0]);
			variable = terms[1];
		}
		
		
	}
	
	//takes + or -, convert to 1 or -1
	void convertSignOperator(String signOperator){
		if(signOperator.equals("+")){
			operator = 1;
		}else if(signOperator.equals("-")){
			operator = -1;
		}else{
			System.out.println("ERROR: malformed expression");
		}
	}

	//combine operator and coefficient
	void combineOPandCO(){
		coefficient = coefficient*operator;
		if(operator == -1){
			operator = 1;
		}else if(operator == 1){
			
		}else{
			System.out.println("ERROR: combineOPandCO. operator: " + operator + " coefficient: " + coefficient);
		}
	}
	
	//getters and setters
	Double getCoefficient(){
		return coefficient;
	}
	String getVariable(){
		return variable;
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
