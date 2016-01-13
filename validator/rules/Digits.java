package validator.rules;

public class Digits extends Regex {
	
	@Override
	protected String regex() {
		return "^[0-9]+$";
	}
	
	@Override
	protected String defaultMessage() {
		return ":field is not a number.";
	}
	
}
