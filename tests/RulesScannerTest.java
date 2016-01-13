package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import validator.contracts.Rule;
import validator.support.RulesScanner;

public class RulesScannerTest {

	private RulesScanner scanner;
	private final String packageToScan = "tests.mock";
	
	@Before
	public void createRulesScanner() {
		this.scanner = RulesScanner.getInstance();
	}
	
	@Test
	public void testSuccessfulScan() {
		try {
			List<Class<Rule>> rules = this.scanner.scan(this.packageToScan);
			assertEquals(2, rules.size());
		} catch (ClassNotFoundException | IllegalAccessException e) {
			fail(e.getMessage());
		}
	}
	
	@Test
	public void testPackageNotExisting() {
		String packageName = "some.not.existing.package";
		
		try {
			this.scanner.scan(packageName);
			fail("Test should not pass.");
		} catch (ClassNotFoundException | IllegalAccessException e) {
			assertEquals("Package " + packageName + " not found.", e.getMessage());
		}
	}

}
