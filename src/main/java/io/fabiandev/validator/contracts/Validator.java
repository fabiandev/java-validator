package io.fabiandev.validator.contracts;

public interface Validator
{

    Validator rule(String key, String value);

    Validator message(String key, String value);

    boolean validate();

    boolean fails();

    Bag errors();

    boolean passes();

}