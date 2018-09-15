package io.fabiandev.validator.rules;

import io.fabiandev.validator.core.BaseRule;

public class Required extends BaseRule
{

    @Override
    protected void validate()
    {
        if (this.inputField.getValue().isEmpty())
        {
            this.fail();
        }
    }

    @Override
    protected String defaultMessage()
    {
        return ":field is required!";
    }

}
