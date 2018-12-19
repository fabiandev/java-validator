package io.fabiandev.validator.core;

import io.fabiandev.validator.contracts.Field;
import io.fabiandev.validator.contracts.Rule;


import java.util.Map;

public abstract class BaseRule implements Rule
{

    protected Map<String, Field> inputFields;
    protected Field inputField;
    protected String ruleValue;
    protected String message;

    private String error;

    protected abstract void validate();

    protected abstract String defaultMessage();

    protected void fail()
    {
        String message = this.message == null ? this.defaultMessage() : this.message;
        this.error = this.parseMessage(message);
    }

    @Override
    public void setData(Map<String, Field> inputFields, String inputFieldKey, String ruleValue, String message)
    {
        this.inputFields = inputFields;
        this.inputField = inputFields.get(inputFieldKey);
        this.ruleValue = ruleValue;
        this.message = message;
    }

    @Override
    public boolean fails()
    {
        this.validate();
        return this.error != null && !this.error.isEmpty();
    }

    @Override
    public boolean passes()
    {
        return !this.fails();
    }

    @Override
    public String getError()
    {
        return this.error == null ? "" : this.error;
    }

    @Override
    public String getDefaultMessage()
    {
        return this.defaultMessage();
    }

    @Override
    public String getUnparsedMessage()
    {
        return this.message == null ? this.defaultMessage() : this.message;
    }

    @Override
    public void setMessage(String message)
    {
        this.message = message;
    }

    @Override
    public String getValue()
    {
        return this.ruleValue;
    }

    @Override
    public String toString()
    {
        return RulesManager.getRuleName(this.getClass());
    }

    private String parseMessage(String message)
    {
        if (message.contains(":field"))
        {
            message = message.replaceAll(":field", this.inputField.getKey());
        }

        if (message.contains(":length"))
        {
            message = message.replaceAll(":length", "" + this.inputField.getValue().length());
        }

        if (message.contains(":" + this))
        {
            message = message.replaceAll(":" + this, this.ruleValue);
        }

        return message;
    }

}
