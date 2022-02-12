package fr.codewise.samplewebitemlist.entities;

public class Calculator {
	
	public int multiply(int a, int b) {
		return a * b;
	}
	
	public int divide(int a, int b) {
		return a / b;
	}
	
	public boolean isPalindrome(String str) {
		String reverse = "";
		int length = str.length();
		
		for (int i = length - 1; i >= 0; i--) {
			reverse = reverse + str.charAt(i);
		}
		
		if (str.equals(reverse)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String evenOrOdd(int num) {
		if (num % 2 == 0) {
			return "even";
		}
		return "odd";
	}
	
	public int convertToInt(String str) {
		if (str == null || str.trim().length() == 0) {
			throw new IllegalArgumentException("String must not be null or empty");
		}
		
		return Integer.valueOf(str);
	}
}
