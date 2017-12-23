package me.test.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public enum Config {
	
	DIR_TESTS("dir.tests", "./tests"),
	DIR_TEMPLATES("dir.templates", "/templates"),
	DIR_USERS("dir.users", "/users"),
	TEMPLATE_RELOAD("templates.reload", "true"),
	
	SESSION_TIMEOUT("session.timeout", "30"),
	;
	
	private static final Properties PROPS = new Properties();
	
	static {
		String workingDir = System.getenv("TESTME_HOME");
		if (workingDir == null) {
			workingDir = System.getProperty("TESTME_HOME");
		}
		if (workingDir != null) {
			File in = new File(workingDir + "/config.properties");
			if (in.exists() && in.isFile()) {
				try {
					PROPS.load(new FileInputStream(in));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			PROPS.put("dir.templates", workingDir + "/templates");
			PROPS.put("dir.tests", workingDir + "/tests");
			PROPS.put("dir.users", workingDir + "/users");
		} else {
			System.err.println("Working directory not found (TESTME_HOME).");
		}
	}
	
	private final String KEY;
	private final String DEFAULT;
	
	private Config(String key, String defaultValue) {
		this.KEY = key;
		this.DEFAULT = defaultValue;
	}
	
	@Override
	public String toString() {
		return PROPS.getProperty(KEY, DEFAULT);
	}

}
