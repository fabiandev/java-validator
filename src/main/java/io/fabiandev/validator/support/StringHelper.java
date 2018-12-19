package io.fabiandev.validator.support;

import java.util.regex.Pattern;

public abstract class StringHelper
{

    public static String[] split(String key, String delimiter)
    {
        if (!key.contains(delimiter))
        {
            return new String[]{key};
        }

        return key.split(Pattern.quote(delimiter));
    }

    public static String camelToSnakeCase(String str)
    {
        return str.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
    }

}
