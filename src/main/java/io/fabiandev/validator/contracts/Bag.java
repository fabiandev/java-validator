package io.fabiandev.validator.contracts;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public interface Bag extends Iterable<Entry<String, String>>
{

    void add(Bag bag);

    String add(String key, String value);

    Map<String, String> getMessages();

    Iterator<Entry<String, String>> iterator();

    boolean isEmpty();

    int size();

}