package validator.rules;

import validator.core.BaseRule;

public class Min extends BaseRule {
	
	@Override
	protected void validate() {
		if (this.inputField.getValue().length() <= 0) {
			return;
		}
		
		int min = Integer.parseInt(this.ruleValue);
		
		if (this.inputField.getValue().length() < min) {
			this.fail();
		}
	}
	
	@Override
	protected String defaultMessage() {
		return ":field must be at least :min characters long!";
	}
	
}
