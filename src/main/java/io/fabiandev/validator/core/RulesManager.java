package io.fabiandev.validator.core;

import io.fabiandev.validator.contracts.Field;
import io.fabiandev.validator.contracts.Rule;
import io.fabiandev.validator.support.RulesScanner;
import io.fabiandev.validator.support.StringHelper;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RulesManager
{
    private static final Map<String, Class<Rule>> rules = new HashMap<>();

    private RulesManager()
    {

    }

    public static void reset()
    {
        rules.clear();
    }

    public static Map<String, Class<Rule>> getRules()
    {
        return rules;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Rule> Class<Rule> addRule(Class<T> rule)
    {
        String ruleName = getRuleName(rule);

        if (hasRule(ruleName))
        {
            return null;
        }

        return rules.put(getRuleName(rule), (Class<Rule>) rule);
    }

    public static int addRules(Class<? extends Rule>... rules)
    {
        int addedRules = 0;

        for (Class<? extends Rule> rule : rules)
        {

            if (addRule(rule) != null)
            {
                addedRules++;
            }
        }

        return addedRules;
    }

    public static boolean addRulesFromPackage(String packageName)
    {
        RulesScanner scanner = RulesScanner.getInstance();

        try
        {
            List<Class<Rule>> ruleList = scanner.scan(packageName);

            for (Class<Rule> rule : ruleList)
            {
                addRule(rule);
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

    public static boolean hasRule(String rule)
    {
        return rules.containsKey(rule);
    }

    public static int numRules()
    {
        return rules.size();
    }

    public static String getIdentifier(String fieldName, String ruleName)
    {
        return fieldName + "." + ruleName;
    }

    public static Rule make(String ruleName, String ruleValue, Map<String, Field> inputFields, String fieldName, String message)
    {
        if (hasRule(ruleName))
        {
            Rule ruleInstance;
            try
            {
                ruleInstance = rules.get(ruleName).getConstructor().newInstance();
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

    public static Rule make(String ruleName, String ruleValue, Map<String, Field> inputFields, String fieldName)
    {
        return make(ruleName, ruleValue, inputFields, fieldName, null);
    }

}
