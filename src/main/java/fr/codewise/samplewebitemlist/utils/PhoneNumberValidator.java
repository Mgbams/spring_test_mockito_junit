package fr.codewise.samplewebitemlist.utils;

import java.util.function.Predicate;

public class PhoneNumberValidator implements Predicate<String> {

	public boolean test(String phoneNumber) {
		return phoneNumber.startsWith("+33") && phoneNumber.length() == 13;
	}

}
