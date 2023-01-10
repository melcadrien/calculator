package calculator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ComplexCalculatorTest {

  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_NonValidOperator_Fail() {
    // Arrange
    String input = "12|2";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_AlphanumericCharacter_Fail() {
    // Arrange
    String input = "12 + 2y";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_ReversedParentheses_Fail() {
    // Arrange
    String input = ")12 + 2(";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_MissingParentheses_Fail() {
    // Arrange
    String input = "(12 + 2";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
  }
  
  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_MissingOpenParentheses_Fail() {
    // Arrange
    String input = "12 + 2)";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
  }
  

  public void preCalculate_WithSpaces() {
    // Arrange
    String input = "12 + 2";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(14, result);
  }


  @Test
  public void preCalculate_Single_MultiplicationOperator() {
    // Arrange
    String input = "12*2";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(24, result);
  }

  @Test
  public void preCalculate_Multiple_MultiplicationOperators() {
    // Arrange
    String input = "12*2*3";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(72, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperators() {
    // Arrange
    String input = "12*2+3";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(27, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperators_ReverseOrder() {
    // Arrange
    String input = "3+2*12";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(27, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperatorsWithParentheses_ReverseOrder() {
    // Arrange
    String input = "(3+2)*12";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(60, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperatorsWithMultipleParentheses_ReverseOrder() {
    // Arrange
    String input = "((3+2)*12)";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(60, result);
  }
  
  @Test
  public void preCalculate_Multiple_MixedOperatorsWithMultipleParentheses_ReverseParentheses() {
    // Arrange
    String input = "(3+(2*12))";
    
    // Act
    int result = ComplexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(27, result);
  }
  
  @Test
  public void preCalculate_Multiple_MixedOperatorsWithMultipleParentheses_DoubleParentheses() {
	    // Arrange
	    String input = "((3+2*12))";
	    
	    // Act
	    int result = ComplexCalculator.preCalculate(input);
	    
	    // Assert
	    assertEquals(27, result);
	  }
  
  @Test
  public void preCalculate_Multiple_MixedOperatorsWithMultipleParentheses_TwoSeparateParentheses() {
	    // Arrange
	    String input = "(3+2)+(4*3)";
	    
	    // Act
	    int result = ComplexCalculator.preCalculate(input);
	    
	    // Assert
	    assertEquals(17, result);
	  }
  
  @Test
  public void preCalculate_Multiple_MixedOperatorsWithMultipleParentheses_NestedParentheses() {
	    // Arrange
	    String input = "((3+2)+(4*3)+4)";
	    
	    // Act
	    int result = ComplexCalculator.preCalculate(input);
	    
	    // Assert
	    assertEquals(21, result);
	  }
  
}
