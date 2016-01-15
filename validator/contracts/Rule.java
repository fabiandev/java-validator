package validator.contracts;

import java.util.Map;

public interface Rule {

	void setData(Map<String, Field> inputFields, String inputFieldKey, String ruleValue, String message);

	boolean fails();

	boolean passes();

	String getError();

	void setMessage(String message);

	String toString();
	
	String getDefaultMessage();
	
	String getUnparsedMessage();
	
	String getValue();

}