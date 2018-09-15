package io.fabiandev.validator;

import io.fabiandev.validator.support.StringHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringHelperTest
{

    @Test
    public void testSplit()
    {
        String str = "some.value";
        String[] parts = StringHelper.split(str, ".");

        assertEquals(2, parts.length);
        assertEquals("some", parts[0]);
        assertEquals("value", parts[1]);
    }

    @Test
    public void testSplitStringWithoutDelimiter()
    {
        String str = "some";
        String[] parts = StringHelper.split(str, ".");

        assertEquals(1, parts.length);
        assertEquals("some", parts[0]);
    }

    @Test
    public void testCamelToSnakeCase()
    {
        String str1 = "SomeCamelCaseString";
        String str2 = "String";
        String str3 = "string";
        String str4 = "someString";
        String str5 = "some_string";
        String str6 = "some_String";
        String str7 = "StringWithNumber1";

        String snakeCase1 = StringHelper.camelToSnakeCase(str1);
        String snakeCase2 = StringHelper.camelToSnakeCase(str2);
        String snakeCase3 = StringHelper.camelToSnakeCase(str3);
        String snakeCase4 = StringHelper.camelToSnakeCase(str4);
        String snakeCase5 = StringHelper.camelToSnakeCase(str5);
        String snakeCase6 = StringHelper.camelToSnakeCase(str6);
        String snakeCase7 = StringHelper.camelToSnakeCase(str7);

        assertEquals("some_camel_case_string", snakeCase1);
        assertEquals("string", snakeCase2);
        assertEquals("string", snakeCase3);
        assertEquals("some_string", snakeCase4);
        assertEquals("some_string", snakeCase5);
        assertEquals("some__string", snakeCase6);
        assertEquals("string_with_number1", snakeCase7);
    }


}
