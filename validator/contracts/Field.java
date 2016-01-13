package validator.contracts;

public interface Field {

	Field addRule(String ruleName, Rule rule);

	boolean hasRule(String ruleName);

	String getKey();

	String getValue();

	Bag errors();

	Rule getRule(String ruleName);

}