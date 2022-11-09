package calculator;

import java.util.Scanner;

import org.apache.commons.lang3.Validate;

public class Calculator {

	private static final String FORMAT_REGULAR_EXPRESSION = "[0-9]*([\\+\\-\\/\\*]))*[0-9]*";
	//TODO: Replace one function with one in the Maven Repo.

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

	private static int calculate(int leftValue, int rightValue, Operator operator) {
		switch(operator){
		case PLUS:
			return (leftValue + rightValue);
		case MINUS:
			return (leftValue - rightValue);
		case MULTIPLY:
			return (leftValue * rightValue);
		case DIVIDE:
			return (leftValue / rightValue);
		}
		return 0;
	}

	public static int preCalculate(String input) {
		int operatorLocation = -1;
		//Initialize values.
		int leftValue = 0;
		int rightValue = 0;
		Operator operatorChosen = Operator.PLUS;
		
		//Remove whitespace before validating.
		input = input.trim();

		//Validate, find the location of the operator, then calculate values.
		//Validate function will throw IllegalArgumentException if it fails.
		Validate.matchesPattern(input, FORMAT_REGULAR_EXPRESSION);
		for(Operator operator : Operator.values()) {
			operatorLocation = input.indexOf(operator.getOperator());
			if(operatorLocation != -1) {
				leftValue = Integer.parseInt(input.substring(0,operatorLocation));
				rightValue = Integer.parseInt(input.substring(operatorLocation+1));
				operatorChosen = operator;
			}
		}
		
		return calculate(leftValue,rightValue,operatorChosen);
	}


}
