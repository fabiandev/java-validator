package io.fabiandev.validator.core;

import io.fabiandev.validator.contracts.Field;
import io.fabiandev.validator.contracts.Rule;
import io.fabiandev.validator.support.RulesScanner;
import io.fabiandev.validator.support.StringHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RuleManager
{

    private Map<String, Class<Rule>> rules = new HashMap<String, Class<Rule>>();

    public RuleManager()
    {

    }

    public RuleManager(String packageName)
    {
        this.addRulesFromPackage(packageName);
    }

    public Map<String, Class<Rule>> getRules()
    {
        return this.rules;
    }

    @SuppressWarnings("unchecked")
    public <T extends Rule> Class<Rule> addRule(Class<T> rule)
    {
        return this.rules.put(getRuleName(rule), (Class<Rule>) rule);
    }

    public final int addRules(Class<? extends Rule>... rules)
    {
        int addedRules = 0;

        for (Class<? extends Rule> rule : rules)
        {

            if (this.addRule(rule) != null)
            {
                addedRules++;
            }
        }

        return addedRules;
    }

    public boolean addRulesFromPackage(String packageName)
    {
        RulesScanner scanner = RulesScanner.getInstance();

        try
        {
            List<Class<Rule>> ruleList = scanner.scan(packageName);

            for (Class<Rule> rule : ruleList)
            {
                this.rules.put(getRuleName(rule), rule);
            }
        }
        catch (ClassNotFoundException | IllegalAccessException e)
        {
            return false;
        }

        return true;
    }

    public static <T extends Rule> String getRuleName(Class<T> rule)
    {
        return StringHelper.camelToSnakeCase(rule.getSimpleName());
    }

    public boolean hasRule(String rule)
    {
        return this.rules.containsKey(rule);
    }

    public int numRules()
    {
        return this.rules.size();
    }

    public static String getIdentifier(String fieldName, String ruleName)
    {
        return fieldName + "." + ruleName;
    }

    public Rule make(String ruleName, String ruleValue, Map<String, Field> inputFields, String fieldName, String message)
    {
        if (this.hasRule(ruleName))
        {
            Rule ruleInstance;
            try
            {
                ruleInstance = this.rules.get(ruleName).getConstructor().newInstance();
                ruleInstance.setData(inputFields, fieldName, ruleValue, message);
                return ruleInstance;
            }
            catch (InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public Rule make(String ruleName, String ruleValue, Map<String, Field> inputFields, String fieldName)
    {
        return this.make(ruleName, ruleValue, inputFields, fieldName, null);
    }

}
