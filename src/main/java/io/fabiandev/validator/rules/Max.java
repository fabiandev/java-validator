package io.fabiandev.validator.rules;

import io.fabiandev.validator.core.BaseRule;

public class Max extends BaseRule
{

    @Override
    protected void validate()
    {
        if (this.inputField.getValue().length() <= 0)
        {
            return;
        }

        int max = Integer.parseInt(this.ruleValue);

        if (this.inputField.getValue().length() > max)
        {
            this.fail();
        }
    }

    @Override
    protected String defaultMessage()
    {
        return ":field must be no more than :max characters!";
    }

}
