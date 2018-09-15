package io.fabiandev.validator.core;

import io.fabiandev.validator.contracts.Bag;
import io.fabiandev.validator.contracts.Field;
import io.fabiandev.validator.contracts.Request;
import io.fabiandev.validator.contracts.Rule;
import io.fabiandev.validator.contracts.Validator;
import io.fabiandev.validator.support.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class StandardValidator implements Validator
{

    private static final String DEFAULT_RULES_PACKAGE = "io.fabiandev.validator.rules";

    private RuleManager ruleManager;
    private Map<String, Field> inputFields;
    private Bag errors;

    public StandardValidator(Request request)
    {
        this.ruleManager = new RuleManager(DEFAULT_RULES_PACKAGE);
        this.inputFields = new HashMap<>();
        this.errors = new MessageBag();

        this.processRequest(request);
    }

    public Validator rule(String fieldName, String fieldValue)
    {
        String[] rules = StringHelper.split(fieldValue, "|");

        Field inputField = this.inputFields.get(fieldName);

        if (inputField == null)
        {
            inputField = new InputField(fieldName);
            this.inputFields.put(fieldName, inputField);
        }

        for (String rule : rules)
        {
            String[] ruleParts = StringHelper.split(rule, ":");
            String ruleName = ruleParts[0];
            String ruleValue = ruleParts.length < 2 ? null : ruleParts[1];

            if (this.ruleManager.hasRule(ruleName))
            {
                Rule fieldRule = this.ruleManager.make(ruleName, ruleValue, this.inputFields, fieldName);
                inputField.addRule(ruleName, fieldRule);
            }
        }

        return this;
    }

    public Validator message(String messageKey, String messageValue)
    {
        String[] parts = StringHelper.split(messageKey, ".");
        String fieldName = parts[0];
        String ruleName = parts.length > 1 ? parts[1] : null;

        if (this.inputFields.containsKey(fieldName))
        {
            if (this.inputFields.containsKey(fieldName))
            {
                Field field = this.inputFields.get(fieldName);

                if (field.hasRule(ruleName))
                {
                    field.getRule(ruleName).setMessage(messageValue);
                }
            }
        }

        return this;
    }

    public boolean fails()
    {
        return !this.validate();
    }

    public boolean validate()
    {
        for (Entry<String, Field> entry : this.inputFields.entrySet())
        {
            Field inputField = entry.getValue();
            this.errors.add(inputField.errors());
        }

        return this.errors.isEmpty();
    }

    public Bag errors()
    {
        return this.errors;
    }

    public boolean passes()
    {
        return !this.fails();
    }

    private void processRequest(Request request)
    {
        for (Entry<String, String> entry : request)
        {
            if (!this.inputFields.containsKey(entry.getKey()))
            {
                this.inputFields.put(entry.getKey(), new InputField(entry.getKey(), entry.getValue()));
            }
        }
    }

}
