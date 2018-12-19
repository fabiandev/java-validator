package io.fabiandev.validator.core;

import io.fabiandev.validator.contracts.Request;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class MapRequest implements Request
{

    private Map<String, String> request;

    public MapRequest()
    {
        this.request = new HashMap<>();
    }

    public MapRequest(Map<String, String> request)
    {
        this.request = request;
    }

    @Override
    public String get(String key)
    {
        return this.request.get(key);
    }

    @Override
    public boolean contains(String value)
    {
        return this.request.containsValue(value);
    }

    @Override
    public boolean containsKey(String key)
    {
        return this.request.containsKey(key);
    }

    @Override
    public boolean isEmpty()
    {
        return this.request.isEmpty();
    }

    @Override
    public int size()
    {
        return this.request.size();
    }

    @Override
    public String delete(String key)
    {
        return this.request.remove(key);
    }

    @Override
    public String put(String key, String value)
    {
        return this.request.put(key, value);
    }

    @Override
    public void clear()
    {
        this.request.clear();
    }

    @Override
    public Iterator<Entry<String, String>> iterator()
    {
        return this.request.entrySet().iterator();
    }

}