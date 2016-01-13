package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import validator.contracts.Request;
import validator.contracts.Validator;
import validator.core.MapRequest;
import validator.core.StandardValidator;

public class RuleRequiredTest {

	Request request;
	Validator validator;
	
	@Before
	public void createRequestAndValidator() {
		Map<String, String> requestData = new HashMap<String, String>();
		
		requestData.put("email", "test@example");
		requestData.put("phone", "");
		requestData.put("street", "abc");
		
		this.request = new MapRequest(requestData);
		this.validator = new StandardValidator(this.request);
	}
	
	@Test
	public void testRequired() {
		Validator validator = this.validator;
		
		validator.rule("email", "required");
		
		assertFalse(validator.fails());
		
		validator.rule("phone", "required");
		
		assertTrue(validator.fails());
	}
	
	@Test
	public void testRequiredIfFieldIsMissingInRequest() {
		Validator validator = this.validator;
		
		validator.rule("tos_accepted", "required");
		
		assertTrue(validator.fails());
	}
	
	@Test
	public void testFieldNotValidatedIfEmptyAndNotRequired() {
		validator.rule("phone", "digits");
		
		assertTrue(validator.passes());
	}
	
	@Test
	public void testFieldValidatedIfNotEmptyAndNotRequired() {
		validator.rule("street", "min:6");
		
		assertTrue(validator.fails());
	}

}
