package io.fabiandev.validator;

import io.fabiandev.validator.contracts.Request;
import io.fabiandev.validator.contracts.Validator;
import io.fabiandev.validator.core.MapRequest;
import io.fabiandev.validator.core.StandardValidator;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RulesTest
{

    Request request;
    Validator validator;

    @Before
    public void createRequestAndValidator()
    {
        Map<String, String> requestData = new HashMap<String, String>();

        requestData.put("validEmail", "test@example.com");
        requestData.put("email", "test@example");
        requestData.put("phone", "");
        requestData.put("street", "abc");

        this.request = new MapRequest(requestData);
        this.validator = new StandardValidator(this.request);
    }

    @Test
    public void testRequired()
    {
        Validator validator = this.validator;

        validator.rule("email", "required");

        assertFalse(validator.fails());

        validator.rule("phone", "required");

        assertTrue(validator.fails());
    }

    @Test
    public void testRequiredIfFieldIsMissingInRequest()
    {
        Validator validator = this.validator;

        validator.rule("tos_accepted", "required");

        assertTrue(validator.fails());
    }

    @Test
    public void testFieldNotValidatedIfEmptyAndNotRequired()
    {
        validator.rule("phone", "digits");

        assertTrue(validator.passes());
    }

    @Test
    public void testFieldValidatedIfNotEmptyAndNotRequired()
    {
        validator.rule("street", "min:6");

        assertTrue(validator.fails());
    }

    @Test
    public void testMin()
    {
        validator.rule("street", "min:4");
        assertTrue(validator.fails());

        this.createRequestAndValidator();

        validator.rule("street", "min:3");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("street", "min:-1");
        assertFalse(validator.fails());
    }

    @Test
    public void testMax()
    {
        validator.rule("street", "max:2");
        assertTrue(validator.fails());

        this.createRequestAndValidator();

        validator.rule("street", "max:3");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("street", "max:-1");
        assertTrue(validator.fails());
    }

    @Test
    public void testRequiredWith()
    {
        validator.rule("phone", "required_with:street");
        assertTrue(validator.fails());

        this.createRequestAndValidator();

        validator.rule("street", "required_with:phone");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("phone", "required_with:notExisting");
        assertFalse(validator.fails());
    }

    @Test
    public void testRequiredUnless()
    {
        validator.rule("phone", "required_unless:street");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("street", "required_unless:phone");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("phone", "required_unless:notExisting");
        assertFalse(validator.fails());
    }

    @Test
    public void testRegex()
    {
        validator.rule("phone", "regex:^[0-9]+$");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("phone", "required|regex:^[0-9]+$");
        assertTrue(validator.fails());
    }

    @Test
    public void testDigits()
    {
        validator.rule("phone", "digits");
        assertFalse(validator.fails());

        this.createRequestAndValidator();

        validator.rule("phone", "required|digits");
        assertTrue(validator.fails());
    }

    @Test
    public void testEmail()
    {
        validator.rule("email", "email");
        assertTrue(validator.fails());

        this.createRequestAndValidator();

        validator.rule("validEmail", "email");
        assertFalse(validator.fails());
    }

}
