package io.fabiandev.validator.contracts;

import java.util.Iterator;
import java.util.Map.Entry;

public interface Request extends Iterable<Entry<String, String>>
{

    String get(String key);

    boolean contains(String value);

    boolean containsKey(String key);

    boolean isEmpty();

    int size();

    String delete(String key);

    String put(String key, String value);

    void clear();

    Iterator<Entry<String, String>> iterator();

}