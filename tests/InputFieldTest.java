package tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import validator.contracts.Bag;
import validator.contracts.Field;
import validator.contracts.Rule;
import validator.core.InputField;
import validator.core.RuleManager;

public class InputFieldTest {
	
	private RuleManager ruleManager;
	
	private Rule rule1;
	private Rule rule2;
	
	private Field inputFieldWithoutValue;
	private Field inputFieldWithValue;
	
	@Before
	public void createInputField() {
		this.ruleManager = new RuleManager("tests.mock");
		
		Map<String, Field> inputFields = new HashMap<String, Field>();
		
		Field field1 = new InputField("key");
		
		inputFields.put(field1.getKey(), field1);
		
		this.rule1 = this.ruleManager.make("test_rule1", null, inputFields, field1.getKey());
		this.rule2 = this.ruleManager.make("test_rule2", null, inputFields, field1.getKey());
		
		this.inputFieldWithoutValue = new InputField("test");
		this.inputFieldWithValue = new InputField("test2", "value");
		
		this.inputFieldWithValue.addRule(rule1.toString(), this.rule1);
		this.inputFieldWithValue.addRule(rule2.toString(), this.rule2);
		this.inputFieldWithoutValue.addRule(rule2.toString(), this.rule2);
	}
	
	@Test
	public void testHasRule() {	
		assertTrue(this.inputFieldWithValue.hasRule(rule1.toString()));
		assertFalse(this.inputFieldWithoutValue.hasRule(rule1.toString()));
		
		assertTrue(this.inputFieldWithValue.hasRule(rule2.toString()));
		assertTrue(this.inputFieldWithoutValue.hasRule(rule2.toString()));
	}
	
	@Test
	public void testGetRule() {
		assertEquals(this.rule1, this.inputFieldWithValue.getRule(this.rule1.toString()));
		assertEquals(null, this.inputFieldWithoutValue.getRule(this.rule1.toString()));
		
		assertEquals(this.rule2, this.inputFieldWithValue.getRule(this.rule2.toString()));
		assertEquals(this.rule2, this.inputFieldWithoutValue.getRule(this.rule2.toString()));
	}
	
	@Test
	public void testGetKey() {
		String key1 = this.inputFieldWithoutValue.getKey();
		String key2 = this.inputFieldWithValue.getKey();
		
		assertEquals("test", key1);
		assertEquals("test2", key2);
	}
	
	@Test
	public void testGetValue() {
		String val1 = this.inputFieldWithoutValue.getValue();
		String val2 = this.inputFieldWithValue.getValue();
		
		assertEquals("", val1);
		assertEquals("value", val2);
	}
	
	@Test
	public void testErrors() {
		Bag bag1 = this.inputFieldWithValue.errors();
		Bag bag2 = this.inputFieldWithoutValue.errors();
		
		assertEquals(1, bag1.size());
		assertEquals(0, bag2.size());
	}

}
