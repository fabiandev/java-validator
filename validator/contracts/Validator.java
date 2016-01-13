package validator.contracts;

import validator.contracts.Validator;

public interface Validator {

	Validator rule(String key, String value);

	Validator message(String key, String value);

	boolean validate();
	
	boolean fails();

	Bag errors();

	boolean passes();

}