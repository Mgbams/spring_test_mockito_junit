package fr.codewise.samplewebitemlist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fr.codewise.samplewebitemlist.entities.Calculator;

public class CalculatorTest {
	Calculator calculator;

	@Test
	public void testMultiply() {
		calculator = new Calculator();
		assertEquals(6, calculator.multiply(2, 3));
		assertEquals(12, calculator.multiply(6, 2));
	}
	
	@Test
	public void testDivide() {
		calculator = new Calculator();
		assertEquals(5, calculator.divide(15, 3));
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"racecar", "radar", "madam", "liril" })
	public void testPalindrome(String str) {
		calculator = new Calculator();
		boolean truthVal = calculator.isPalindrome(str);
		assertTrue(truthVal);
	}
	
	/*@ParameterizedTest
	@CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
	public void testEvenOrOdd(String input, String expected) {
		//System.out.println(input);
		calculator = new Calculator();
		String actual = calculator.evenOrOdd(Integer.parseInt(input.trim()));
		System.out.println(actual);
		assertEquals(expected, actual);
	}*/
	
	@Test
	public void convertToIntNullParameterAssertThrows() {
	    calculator = new Calculator();
		String str = null;
		assertThrows(IllegalArgumentException.class, () -> calculator.convertToInt(str));
	}
}
