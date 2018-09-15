package io.fabiandev.validator;

import io.fabiandev.validator.contracts.Field;
import io.fabiandev.validator.contracts.Rule;
import io.fabiandev.validator.core.InputField;
import io.fabiandev.validator.core.RulesManager;
import io.fabiandev.validator.mock.TestRule1;
import io.fabiandev.validator.mock.TestRule2;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RulesManagerTest
{

    private static final String RULES_PACKAGE = "io.fabiandev.validator.mock";

    @Before
    public void beforeEach()
    {
        RulesManager.reset();
    }

    @Test
    public void testAddRule()
    {
        RulesManager.addRule(TestRule1.class);
        assertEquals(1, RulesManager.numRules());
        assertTrue(RulesManager.hasRule("test_rule1"));
    }

    @Test
    public void testAddRules()
    {
        RulesManager.addRules(TestRule1.class, TestRule2.class);
        assertEquals(2, RulesManager.numRules());
        assertTrue(RulesManager.hasRule("test_rule1"));
        assertTrue(RulesManager.hasRule("test_rule2"));
    }

    @Test
    public void testAddRulesFromPackage()
    {
        RulesManager.addRulesFromPackage(RULES_PACKAGE);
        assertEquals(2, RulesManager.numRules());
        assertTrue(RulesManager.hasRule("test_rule1"));
        assertTrue(RulesManager.hasRule("test_rule2"));
    }

    @Test
    public void testAddRulesFromNonExistingPackage()
    {
        RulesManager.addRulesFromPackage(String.format("%s.%s", RULES_PACKAGE, "404"));
        assertEquals(0, RulesManager.numRules());
    }

    @Test
    public void testMakeRule()
    {
        RulesManager.addRulesFromPackage(RULES_PACKAGE);

        Map<String, Field> inputFields = new HashMap<String, Field>();

        Field field1 = new InputField("key");
        Field field2 = new InputField("key", "value");

        inputFields.put(field1.getKey(), field1);
        inputFields.put(field2.getKey(), field2);

        Rule rule1 = RulesManager.make("test_rule1", null, inputFields, field1.getKey());
        Rule rule2 = RulesManager.make("test_rule2", "someValue", inputFields, field2.getKey(), ":field rule value is :test_rule2");

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
