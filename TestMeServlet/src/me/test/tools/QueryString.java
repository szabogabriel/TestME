package me.test.tools;

import java.util.Arrays;
import java.util.Properties;

public class QueryString {
	
	private Properties props = new Properties();
	
	public QueryString(String queryString) {
		String tmp = (queryString != null) ? queryString : "";
		if (tmp.startsWith("?")) {
			tmp = tmp.substring(1);
		}
		
		Arrays.asList(tmp.split("&")).stream().forEach(q -> handle(q));
	}
	
	private void handle(String query) {
		String key = "";
		String val = "";
		
		if (query.contains("=")) {
			String [] tmp = query.split("=");
			key = tmp[0];
			val = (tmp.length > 1) ? tmp[1] : "";
		} else {
			key = query;
		}
		
		props.put(key, val);
	}
	
	public boolean knownKey(String key) {
		return props.containsKey(key);
	}
	
	public String getValue(String key) {
		return props.getProperty(key);
	}
	
	public String getValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
}
