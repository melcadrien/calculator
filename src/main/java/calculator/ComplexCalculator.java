package calculator;

import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.lang3.Validate;

public class ComplexCalculator {

	//private static final String FORMAT_REGULAR_EXPRESSION = "[0-9]*([\\+|\\-|\\/|\\*])[0-9]*";
	//	private static final String FORMAT_REGULAR_EXPRESSION = "([0-9])*\\s*[\\+\\-\\/\\*]*\\s*([0-9])*";
	//	private static final String FORMAT_REGULAR_EXPRESSION = "(([0-9])*\\s*[\\+\\-\\/\\*]\\s*)*([0-9])+";
	private static final String FORMAT_REGULAR_EXPRESSION = "(([0-9])*\\s*[\\+\\-\\/\\*\\^]\\s*)*([0-9])+";
	private static final String FORMAT_PARENTHESES_EXPRESSION = ".*\\(.*\\).*";
	//TODO: Order of operations: PEMDAS
	//Rough idea is to go through the operator array, find either multiply or divide and run those.
	//Then Add/subtract
	//Will have to do parentheses in the next part.
	//After that, floating point.
	//TODO: create unit tests.

	/*
	 * In Steps:
	 * # Start with separating addition and subtraction from multiplication and division.
	 * # Add Parentheses search.[For exponents, ^ might be used or be ignored.]
	 * # Add BigDecimal in future update.
	 */

	public static enum Operator{
		PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('/'), EXPONENT('^');

		private final char operator;

		Operator(char operator){
			this.operator = operator;
		}

		public char getOperator() {
			return this.operator;
		}
	}


	public static void main(String[] args) {
		Scanner userInput = new Scanner(System.in);

		System.out.println("Insert two numbers and an operation, without space, in between:");
		String toCalculate = userInput.nextLine();
		System.out.println(preCalculate(toCalculate));

		userInput.close();
	}

	private static int calculateByInput(String[] values, Operator[] operators) {
		int totalValue = Integer.parseInt(values[0]);

		for(int pos = 1; pos < values.length; pos++) {
			switch(operators[pos-1]){
			case PLUS:
				totalValue += Integer.parseInt(values[pos]);
				break;
			case MINUS:
				totalValue -= Integer.parseInt(values[pos]);
				break;
			case MULTIPLY:
				totalValue *= Integer.parseInt(values[pos]);
				break;
			case DIVIDE:
				totalValue /= Integer.parseInt(values[pos]);
				break;
			}
		}
		return totalValue;
	}

	private static int calculateByOperationOrder(ArrayList<Integer> values, ArrayList<Operator> operators) {
		int totalValue = values.get(0);
		//NOTES: Okay, so I need to do some fixing in the exponent method.  For some reason it's eating one of the numbers.
		ArrayList<Integer> newValues = new ArrayList<Integer>();
		ArrayList<Operator> newOperators = new ArrayList<Operator>();

		//For Multiply and Divide
		//Results are added into ArrayList to make it easier to add in values to be used for addition/subtraction
		for(int pos = 1; pos < values.size(); pos++) {
			while(operators.get(pos-1) != Operator.PLUS && operators.get(pos-1) != Operator.MINUS) {
				switch(operators.get(pos-1)){
				case MULTIPLY:
					totalValue *= values.get(pos);
					break;
				case DIVIDE:
					totalValue /= values.get(pos);
					break;
				}
				pos++;
				if(pos == values.size())
					break;
			}
			newValues.add(totalValue);
			if(pos != values.size()) {
				newOperators.add(operators.get(pos-1));
				totalValue = values.get(pos);
			}
			if(pos == values.size()-1 && (operators.get(pos-1) == Operator.PLUS || operators.get(pos-1) == Operator.MINUS)){
				newValues.add(totalValue);
			}
		}

		//For Addition and Subtraction
		if(newValues.size() >= 1) {
			totalValue = newValues.get(0);
			for(int pos = 1; pos < newValues.size(); pos++) {
				switch(newOperators.get(pos-1)){
				case PLUS:
					totalValue += newValues.get(pos);
					break;
				case MINUS:
					totalValue -= newValues.get(pos);
					break;
				}
			}

		}
		return totalValue;
	}

	//Gets the values using the operators as a separator.
	private static String[] createValues(String input) {
		return input.split("\\s*\\D\\s*");
	}

	//Gives us the operators in the input.
	private static Operator[] createOperators(String input) {
		//Creates an initial whitespace, which has to be removed.
		//Maybe some sort of array that has a queue?
		String[] initialOperators = input.split("\\s*\\d+\\s*");
		String[] OperatorString = new String[initialOperators.length-1];
		System.arraycopy(initialOperators, 1, OperatorString, 0, OperatorString.length);

		Operator[] operators = new Operator[OperatorString.length];
		int pos = 0;

		for(String OperStr : OperatorString) {
			for(Operator operator : Operator.values()) {
				if(OperStr.charAt(0) == (operator.getOperator())){
					operators[pos] = operator;
					pos++;
				}
			}
		}
		return operators;
	}

	//Used when parentheses are found.
	//Will calculate what is in the parentheses, then rebuilds the string to return.
	//This also works with nested parentheses.
	private static String processParentheses(String input) {
		int endParentheses = -1;//Initialized at -1.
		int extraParentheses = 0;//Used in case another open is found

		//This helped with all that error checking.
		Validate.matchesPattern(input, FORMAT_PARENTHESES_EXPRESSION);

		for(int charPos = input.indexOf("(") + 1; charPos < input.length(); charPos++) {
			if(input.charAt(charPos) == ')' && extraParentheses == 0) {
				endParentheses = charPos;
				break;
			}
			else if(input.charAt(charPos) == '(') {
				extraParentheses += 1;
			}
			else if(input.charAt(charPos) == ')' && extraParentheses != 0) {
				extraParentheses -= 1;
			}

		}

		if(endParentheses == input.length() - 1) {
			input = input.substring(0, input.indexOf("(")) +
					preCalculate(input.substring(input.indexOf("(") + 1, endParentheses));

		}
		else {
			input = input.substring(0, input.indexOf("(")) +
					preCalculate(input.substring(input.indexOf("(") + 1, endParentheses)) +
					input.substring(endParentheses + 1);
		}
		return input;
	}

	//Used to handle exponents then pushes the rest to be handled by calculateByOperationOrder.
	private static int exponentFirst(String[] values, Operator[] operators) {
		int totalValue = 1;
		int exponentValue = 0;

		boolean nestedExponent = false;
		//Next up: Exponents.  I believe ^ is the most recognized form for notating exponents
		//	not in actual exponent form.

		ArrayList<Integer> newValues = new ArrayList<Integer>();
		ArrayList<Operator> newOperators = new ArrayList<Operator>();

		//this needs to be reworked to handle exponents.
		for(int pos = 1; pos < values.length; pos++) {
			if(operators[pos-1] == Operator.EXPONENT) {
				//Say it's 8^2, it should be 8*8.
				//Here's a thought, what about nested exponents?
				if(!nestedExponent) {
					exponentValue = Integer.parseInt(values[pos-1]);
				}
				for(int i = 1; i <= Integer.parseInt(values[pos]); i++) {
					totalValue = totalValue * exponentValue;
				}
				//NOTES: Figure out how to solve the nested exponent problem.
				if(pos < operators.length && operators[pos] == Operator.EXPONENT) {
					exponentValue = totalValue;
					totalValue = 1;
					nestedExponent = true;
					continue;
				}
				newValues.add(totalValue);
				//reset value.				
				totalValue = 1;
			}
			else {
				newValues.add(Integer.parseInt(values[pos-1]));
				newOperators.add(operators[pos-1]);
			}

			if(pos == values.length-1 && (operators[pos-1] != Operator.EXPONENT)){
				newValues.add(Integer.parseInt(values[pos]));
			}
		}

		return calculateByOperationOrder(newValues, newOperators);
	}


	public static int preCalculate(String input) {
		//Remove whitespace before validating.
		input = input.trim();

		while(input.contains("(") && input.contains(")")) {
			input = processParentheses(input);
		}
		//Next Task: BigInteger.
		
		//Validate, find the location of the operator, then calculate values.
		//Validate function will throw IllegalArgumentException if it fails.
		Validate.matchesPattern(input, FORMAT_REGULAR_EXPRESSION);

		//If it does validate, split the values and operators into separate arrays.
		String[] values = createValues(input);
		if(values.length == 1) {
			return Integer.parseInt(values[0]);
		}
		Operator[] operators = createOperators(input);

		return exponentFirst(values,operators);
	}


}
