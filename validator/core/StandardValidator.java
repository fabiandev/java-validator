package validator.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import validator.contracts.Bag;
import validator.contracts.Rule;
import validator.contracts.Field;
import validator.contracts.Request;
import validator.contracts.Validator;
import validator.support.StringHelper;

public class StandardValidator implements Validator {
	
	public static final String DEFAULT_RULES_PACKAGE = "validator.rules";
	
	private RuleManager ruleManager;
	private Map<String, Field> inputFields;
	private Bag errors;
	
	public StandardValidator(Request request) {
		this.ruleManager = new RuleManager(DEFAULT_RULES_PACKAGE);
		this.inputFields = new HashMap<String, Field>();
		this.errors = new MessageBag();
		
		this.processRequest(request);
	}
	
	@Override
	public Validator rule(String fieldName, String fieldValue) {
		String[] rules = StringHelper.split(fieldValue, "|");
		
		Field inputField = this.inputFields.get(fieldName);
		
		if (inputField == null) {
			inputField = new InputField(fieldName);
			inputFields.put(fieldName, inputField);
		}
		
		for (String rule : rules) {
			String[] ruleParts = StringHelper.split(rule, ":");
			String ruleName = ruleParts[0];
			String ruleValue = ruleParts.length < 2 ? null : ruleParts[1];
			
			if (ruleManager.hasRule(ruleName)) {
				Rule fieldRule = this.ruleManager.make(ruleName, ruleValue, this.inputFields, fieldName);
				inputField.addRule(ruleName, fieldRule);
			}
		}
		
		return this;
	}
	
	@Override
	public Validator message(String messageKey, String messageValue) {
		String[] parts = StringHelper.split(messageKey, ".");
		String fieldName = parts[0];
		String ruleName = parts.length > 1 ? parts[1] : null;
		
		if (this.inputFields.containsKey(fieldName)) {	
			if (this.inputFields.containsKey(fieldName)) {
				Field field = this.inputFields.get(fieldName);
				
				if (field.hasRule(ruleName)) {
					field.getRule(ruleName).setMessage(messageValue);
				}
			}
		}
		
		return this;
	}

	@Override
	public boolean fails() {
		return !this.validate();
	}
	
	@Override
	public boolean validate() {
		for (Entry<String, Field> entry : this.inputFields.entrySet()) {
			Field inputField = entry.getValue();
			this.errors.add(inputField.errors());
		}
		
		return this.errors.isEmpty();
	}
	
	@Override
	public Bag errors() {
		return this.errors;
	}
	
	@Override
	public boolean passes() {
		return !fails();
	}
	
	private void processRequest(Request request) {
		for (Entry<String, String> entry : request) {
			if (!inputFields.containsKey(entry.getKey())) {
				inputFields.put(entry.getKey(), new InputField(entry.getKey(), entry.getValue()));
			}
		}
	}
	
}
