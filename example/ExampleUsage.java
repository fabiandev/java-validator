package example;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import validator.contracts.Bag;
import validator.contracts.Request;
import validator.contracts.Validator;
import validator.core.MapRequest;
import validator.core.StandardValidator;

public class ExampleUsage {

	public static void main(String[] args) {
		Request request = createRequest();
		
		Validator validator = new StandardValidator(request);
		
		validator
	    	.rule("email", "max:35|email")
	    	
	    	.rule("phone", "required_unless:email|min:9|digits")
	    		.message("phone.min", ":field must be at least :min characters long.")
	    		.message("phone.digits", "For :field only digits are allowed")
	    		
	    	.rule("name", "required|min:6")
	    		.message("name.min", "Please fill in your name with at least :min characters.")
	    		
			.rule("tos_accepted", "required")
				.message("tos_accepted.required", "Please accept the Terms of Service.");
		
		if (validator.fails()) {
			printErrors(validator.errors());
			return;
		}
		
		System.out.println("Validation passed!");
	}
	
	private static Request createRequest() {
		Map<String, String> requestData = new HashMap<String, String>();
		
		requestData.put("email", "");
		requestData.put("name", "John");
		requestData.put("phone", "");
		
		Request request = new MapRequest(requestData);
		
		return request;
	}
	
	private static void printErrors(Bag errors) {
		for (Entry<String, String> error : errors) {
			System.out.println(error.getValue());
		}
	}

}
