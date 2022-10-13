package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

	private static final String FORMAT_REGULAR_EPRESSION = "[0-9]*([\\+|\\-|\\/|\\*])[0-9]*";
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

	//Format: Number[+-/*]Number without spaces in between.
	private static boolean validate(String input) {
		Pattern pattern = Pattern.compile(FORMAT_REGULAR_EPRESSION);
		Matcher matcher = pattern.matcher(input);
		boolean formatFound = matcher.matches();

		return(formatFound);
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

		//Remove whitespace before validating.
		input = input.trim();

		//Validate, find the location of the operator, then calculate values.
		if(validate(input)) {
			for(Operator operator : Operator.values()) {
				operatorLocation = input.indexOf(operator.getOperator());
				if(operatorLocation != -1) {
					int leftValue = Integer.parseInt(input.substring(0,operatorLocation));
					int rightValue = Integer.parseInt(input.substring(operatorLocation+1));
					return calculate(leftValue,rightValue,operator);
				}
			}
		}
		//When validation fails.
		throw new IllegalArgumentException("Incorrect format(Must be number{+|-|*|/}number without spaces in between).");
	}


}
