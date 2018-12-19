package io.fabiandev.validator.rules;

public class Email extends Regex
{

    @Override
    protected String regex()
    {
        return "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    }

    @Override
    protected String defaultMessage()
    {
        return ":field is not a valid Email.";
    }

}
