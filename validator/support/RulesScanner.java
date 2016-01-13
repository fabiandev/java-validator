package validator.support;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import validator.contracts.Rule;

public class RulesScanner {

	private static RulesScanner instance = null;
	
	private RulesScanner() {
		
	}
	
	public static RulesScanner getInstance() {
		if (instance == null) {
			instance = new RulesScanner();
		}
		
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<Class<Rule>> scan(String packageName) throws ClassNotFoundException, IllegalAccessException {
		List<Class<Rule>> rules = new ArrayList<Class<Rule>>();
		URL root = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".", "/"));
		
		if (root == null) {
			throw new IllegalAccessException("Package " + packageName + " not found.");
		}
		
		// Filter .class files.
		File[] files = new File(root.getFile()).listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".class");
		    }
		});

		// Find classes implementing Rule.
		for (File file : files) {
		    String className = file.getName().replaceAll(".class$", "");
		    Class<?> cls = Class.forName(packageName + "." + className);
		    
		    if (Rule.class.isAssignableFrom(cls)) {
		        rules.add((Class<Rule>) cls);
		    }
		}
		
		return rules;
	}
	
}
