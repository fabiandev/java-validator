package io.fabiandev.validator.rules;

import io.fabiandev.validator.core.BaseRule;

public class RequiredUnless extends BaseRule
{

    @Override
    protected void validate()
    {
        if (!this.inputFields.containsKey(this.ruleValue))
        {
            return;
        }

        if (!this.inputFields.get(this.ruleValue).getValue().isEmpty())
        {
            return;
        }

        if (this.inputField.getValue().isEmpty())
        {
            this.fail();
        }
    }

    @Override
    protected String defaultMessage()
    {
        return ":field is required if field :required_unless is empty.";
    }

}
