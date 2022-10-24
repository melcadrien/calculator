package calculator;

import java.util.Scanner;

import org.apache.commons.lang3.Validate;

public class complexCalculator {

	//private static final String FORMAT_REGULAR_EXPRESSION = "[0-9]*([\\+|\\-|\\/|\\*])[0-9]*";
	private static final String FORMAT_REGULAR_EXPRESSION = "([0-9]*([\\+|\\-|\\/|\\*]))*[0-9]*";
	//TODO: Take in a longer string of numbers and operators.
	//Primary change: Regex needs to accept more or be designed to handle less.
	//2 Possible ways:
	//1: Branching out by operators.
	//2: Search and re-Search.
	//Let's go by how it is put in.  If it's 2+2*2, then it should be(2+2)*2.
	//And 2+2+2+2 is ((2+2)+2)+2.

	public static enum Operator{
		PLUS('+'), MINUS('-'), MULTIPLY('*'), DIVIDE('/');

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

	private static int calculate(String[] values, Operator[] operators) {
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

	//Gets the values using the operators as a separator.
	private static String[] createValues(String input) {
		return input.split("\\D");
	}

	
	private static Operator[] createOperators(String input) {
		//Creates an initial whitespace, which has to be removed.
		String[] initialOperators = input.split("\\d+");
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


	public static int preCalculate(String input) {
		//Remove whitespace before validating.
		input = input.trim();

		//Validate, find the location of the operator, then calculate values.
		//Validate function will throw IllegalArgumentException if it fails.
		Validate.matchesPattern(input, FORMAT_REGULAR_EXPRESSION);

		//If it does validate, split the values and operators into separate arrays.
		String[] values = createValues(input);
		
		Operator[] operators = createOperators(input);

		return calculate(values,operators);
	}


}
