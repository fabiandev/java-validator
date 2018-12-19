package io.fabiandev.validator.support;

import io.fabiandev.validator.contracts.Rule;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RulesScanner
{

    private static RulesScanner instance = null;

    private RulesScanner()
    {

    }

    public static RulesScanner getInstance()
    {
        if (instance == null)
        {
            instance = new RulesScanner();
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    public List<Class<Rule>> scan(String packageName) throws ClassNotFoundException, IllegalAccessException
    {
        List<Class<Rule>> rules = new ArrayList<>();
        URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));

        if (root == null)
        {
            throw new IllegalAccessException("Package " + packageName + " not found.");
        }

        // Filter .class files.
        File[] files = new File(root.getFile()).listFiles((dir, name) -> name.endsWith(".class"));

        if (files == null) {
            return rules;
        }

        // Find classes implementing Rule.
        for (File file : files)
        {
            String className = file.getName().replaceAll(".class$", "");
            Class<?> cls = Class.forName(packageName + "." + className);

            if (Rule.class.isAssignableFrom(cls))
            {
                rules.add((Class<Rule>) cls);
            }
        }

        return rules;
    }

}
