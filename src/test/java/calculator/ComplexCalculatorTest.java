package calculator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ComplexCalculatorTest {

  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_NonValidOperator_Fail() {
    // Arrange
    String input = "12|2";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
  }

  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_WithSpaces_Fail() {
    // Arrange
    String input = "12 + 2";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
  }

  @Test(expected = IllegalArgumentException.class)
  public void preCalculate_AlphanumericCharacter_Fail() {
    // Arrange
    String input = "12 + 2y";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
  }

  @Test
  public void preCalculate_Single_MultiplicationOperator() {
    // Arrange
    String input = "12*2";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(24, result);
  }

  @Test
  public void preCalculate_Multiple_MultiplicationOperators() {
    // Arrange
    String input = "12*2*3";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(72, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperators() {
    // Arrange
    String input = "12*2+3";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(27, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperators_ReverseOrder() {
    // Arrange
    String input = "3+2*12";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(27, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperatorsWithParentheses_ReverseOrder() {
    // Arrange
    String input = "(3+2)*12";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(60, result);
  }

  @Test
  public void preCalculate_Multiple_MixedOperatorsWithMultipleParentheses_ReverseOrder() {
    // Arrange
    String input = "((3+2)*12)";
    
    // Act
    int result = complexCalculator.preCalculate(input);
    
    // Assert
    assertEquals(60, result);
  }
  
}
