package io.fabiandev.validator;

import io.fabiandev.validator.contracts.Field;
import io.fabiandev.validator.contracts.Rule;
import io.fabiandev.validator.core.InputField;
import io.fabiandev.validator.core.RuleManager;
import io.fabiandev.validator.mock.TestRule1;
import io.fabiandev.validator.mock.TestRule2;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RuleManagerTest
{

    RuleManager ruleManager;

    @Before
    public void test()
    {
        this.ruleManager = new RuleManager();
    }

    @Test
    public void testAddRule()
    {
        this.ruleManager.addRule(TestRule1.class);
        assertEquals(1, this.ruleManager.numRules());
        assertTrue(this.ruleManager.hasRule("test_rule1"));
    }

    @Test
    public void testAddRules()
    {
        this.ruleManager.addRules(TestRule1.class, TestRule2.class);
        assertEquals(2, this.ruleManager.numRules());
        assertTrue(this.ruleManager.hasRule("test_rule1"));
        assertTrue(this.ruleManager.hasRule("test_rule2"));
    }

    @Test
    public void testAddRulesFromPackage()
    {
        this.ruleManager.addRulesFromPackage("tests.mock");
        assertEquals(2, this.ruleManager.numRules());
        assertTrue(this.ruleManager.hasRule("test_rule1"));
        assertTrue(this.ruleManager.hasRule("test_rule2"));
    }

    @Test
    public void testAddRulesFromNonExistingPackage()
    {
        this.ruleManager.addRulesFromPackage("some.not.existing.package");
        assertEquals(0, this.ruleManager.numRules());
    }

    @Test
    public void testMakeRule()
    {
        this.ruleManager.addRulesFromPackage("tests.mock");

        Map<String, Field> inputFields = new HashMap<String, Field>();

        Field field1 = new InputField("key");
        Field field2 = new InputField("key", "value");

        inputFields.put(field1.getKey(), field1);
        inputFields.put(field2.getKey(), field2);

        Rule rule1 = this.ruleManager.make("test_rule1", null, inputFields, field1.getKey());
        Rule rule2 = this.ruleManager.make("test_rule2", "someValue", inputFields, field2.getKey(), ":field rule value is :test_rule2");

        assertTrue(rule1 instanceof TestRule1);
        assertTrue(rule2 instanceof TestRule2);

        assertEquals("test_rule1", rule1.toString());
        assertEquals("test_rule2", rule2.toString());

        assertEquals(null, rule1.getValue());
        assertEquals("someValue", rule2.getValue());

        assertEquals(rule1.getDefaultMessage(), rule1.getUnparsedMessage());
        assertEquals(":field rule value is :test_rule2", rule2.getUnparsedMessage());
    }

}
