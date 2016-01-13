package tests.mock;

import validator.core.BaseRule;

public class TestRule2 extends BaseRule {

	@Override
	protected void validate() {
		
	}

	@Override
	protected String defaultMessage() {
		return "Test rule error message";
	}

}
