package io.fabiandev.validator;

import io.fabiandev.validator.contracts.Rule;
import io.fabiandev.validator.support.RulesScanner;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class RulesScannerTest
{
    private static final String RULES_PACKAGE = "io.fabiandev.validator.mock";

    private RulesScanner scanner;

    @Before
    public void createRulesScanner()
    {
        this.scanner = RulesScanner.getInstance();
    }

    @Test
    public void testSuccessfulScan()
    {
        try
        {
            List<Class<Rule>> rules = this.scanner.scan(RULES_PACKAGE);
            assertEquals(2, rules.size());
        }
        catch (ClassNotFoundException | IllegalAccessException e)
        {
            fail(e.getMessage());
        }
    }

    @Test
    public void testPackageNotExisting()
    {
        String packageName = "some.not.existing.package";

        try
        {
            this.scanner.scan(packageName);
            fail("Test should not pass.");
        }
        catch (ClassNotFoundException | IllegalAccessException e)
        {
            assertEquals("Package " + packageName + " not found.", e.getMessage());
        }
    }

}
