package fr.codewise.samplewebitemlist.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PhoneNumberValidatorTest {
	private PhoneNumberValidator underTest;
	
	@BeforeEach
	void setUp() {
		underTest = new  PhoneNumberValidator();
	}
	
	@Test
	@DisplayName("Should fail when length != 13")
	void itShouldValidatePhoneNumberWhenLengthIsGreaterThan13() {
		String phoneNumber = "+33780987654055677";
		boolean isValid = underTest.test(phoneNumber);
		
		assertFalse(isValid);
	}
	
	@Test
	@DisplayName("Should fail when it does not start with plus")
	void itShouldValidatePhoneNumberWhenItDoesNotStartWithPlus() {
		String phoneNumber = "337809876540";
		boolean isValid = underTest.test(phoneNumber);
		
		assertFalse(isValid);
	}
	
	@ParameterizedTest
	@CsvSource({"337809876540, TRUE"})
	void itShouldValidatePhoneNumberParameterized(String phoneNumber, String expected ) {
		//String phoneNumber = "337809876540";
		boolean isValid = underTest.test(phoneNumber);
		
		assertEquals(Boolean.valueOf(isValid), isValid);
	}
	
	@Test
	@DisplayName("Should pass when length is 13")
	void itShouldValidatePhoneNumber() {
		String phoneNumber = "+337809876540";
		boolean isValid = underTest.test(phoneNumber);
		
		assertTrue(isValid);
	}
}
