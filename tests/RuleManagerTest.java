package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tests.mock.TestRule1;
import tests.mock.TestRule2;
import validator.core.RuleManager;

public class RuleManagerTest {
	
	RuleManager ruleManager;
	
	@Before
	public void test() {
		this.ruleManager = new RuleManager();
	}
	
	@Test
	public void testAddRule() {
		this.ruleManager.addRule(TestRule1.class);
		assertEquals(1, this.ruleManager.numRules());
		assertTrue(this.ruleManager.hasRule("test_rule1"));
	}
	
	@Test
	public void testAddRules() {
		this.ruleManager.addRules(TestRule1.class, TestRule2.class);
		assertEquals(2, this.ruleManager.numRules());
		assertTrue(this.ruleManager.hasRule("test_rule1"));
		assertTrue(this.ruleManager.hasRule("test_rule2"));
	}
	
	@Test
	public void testAddRulesFromPackage() {
		this.ruleManager.addRulesFromPackage("tests.mock");
		assertEquals(2, this.ruleManager.numRules());
		assertTrue(this.ruleManager.hasRule("test_rule1"));
		assertTrue(this.ruleManager.hasRule("test_rule2"));
	}
	
	@Test
	public void testAddRulesFromNonExistingPackage() {
		this.ruleManager.addRulesFromPackage("some.not.existing.package");
		assertEquals(0, this.ruleManager.numRules());
	}

}
