package validator.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import validator.contracts.Bag;
import validator.contracts.Field;
import validator.contracts.Rule;

public class InputField implements Field {

	private String fieldName;
	private String fieldValue;

	Map<String, Rule> rules;

	public InputField(String fieldName, String fieldValue) {
		this.rules = new HashMap<String, Rule>();
		
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	public InputField(String fieldName) {
		this(fieldName, "");
	}

	@Override
	public Field addRule(String key, Rule rule) {
		
		if (!this.rules.containsKey(key)) {
			this.rules.put(key, rule);
		}
		
		return this;
	}

	@Override
	public boolean hasRule(String ruleName) {
		if (this.rules.containsKey(ruleName)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public Rule getRule(String ruleName) {
		if (this.hasRule(ruleName)) {
			return this.rules.get(ruleName);
		}
		
		return null;
	}
	
	@Override
	public String getKey() {
		return this.fieldName == null ? "" : this.fieldName;
	}

	@Override
	public String getValue() {
		return this.fieldValue == null ? "" : this.fieldValue;
	}

	@Override
	public Bag errors() {
		Bag errors = new MessageBag();
		
		for (Entry<String, Rule> entry : this.rules.entrySet()) {
			Rule rule = entry.getValue();
			if (rule.fails()) {
				errors.add(RuleManager.getIdentifier(this.fieldName, rule.toString()), rule.getError());
			}
		}
		
	    return errors;
	}
	
}