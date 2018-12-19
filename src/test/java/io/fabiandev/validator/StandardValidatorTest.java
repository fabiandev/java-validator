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

public class StandardValidatorTest
{

    Request emptyRequest;
    Request request;

    @Before
    public void createRequests()
    {
        Map<String, String> requestData = new HashMap<String, String>();

        requestData.put("email", "test@example");
        requestData.put("name", "John");
        requestData.put("phone", "0123827494");

        this.request = new MapRequest(requestData);
        this.emptyRequest = new MapRequest(new HashMap<String, String>());
    }

    @Test
    public void testEmptyValidator()
    {
        Validator validator = new StandardValidator(this.emptyRequest);

        assertFalse(validator.fails());
        assertEquals(0, validator.errors().size());
    }

    @Test
    public void testFailingValidator()
    {
        Validator validator = new StandardValidator(this.request);

        validator
                .rule("email", "required|email")
                .rule("name", "required|min:6")
                .rule("phone", "required_unless:email|digits|min:9");

        assertTrue(validator.fails());
        assertEquals(validator.fails(), !validator.passes());
        assertEquals(validator.passes(), validator.validate());
        assertEquals(2, validator.errors().size());
    }

    @Test
    public void testSuccessfulValidator()
    {
        Validator validator = new StandardValidator(this.request);

        validator
                .rule("name", "required|min:4")
                .rule("phone", "digits|min:9");

        assertFalse(validator.fails());
        assertEquals(validator.fails(), !validator.passes());
        assertEquals(validator.passes(), validator.validate());
        assertEquals(0, validator.errors().size());
    }

    @Test
    public void testCustomErrorMessages()
    {
        int min = 5;

        Validator validator = new StandardValidator(this.request);

        validator
                .rule("name", "required|min:" + min)
                .message("name.min", "Your full name is only :min characters long?")

                .rule("email", "email")
                .message("email.email", ":field is not an email.");

        validator.validate();

        assertEquals(2, validator.errors().size());
        assertTrue(validator.errors().getMessages().containsValue("Your full name is only " + min + " characters long?"));
        assertTrue(validator.errors().getMessages().containsValue("email is not an email."));
    }

}
