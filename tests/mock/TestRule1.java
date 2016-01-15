package tests.mock;

import validator.core.BaseRule;

public class TestRule1 extends BaseRule {

	@Override
	protected void validate() {
		if (this.inputField.getValue().isEmpty()) {
			this.fail();
		}
	}

	@Override
	protected String defaultMessage() {
		return "Test rule error message";
	}

}
