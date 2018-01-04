package me.test.servlet.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.entity.test.Test;

public class MainVM implements ViewModel {
	
	private Test[] tests = null;
	
	private boolean loggedIn = false;
	
	public void setTests(Test[] tests) {
		this.tests = tests;
	}
	
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	@Override
	public Map<String, Object> provideData() {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		ret.put("tests", getTestsData());
		ret.put("isLoggedIn", loggedIn);
		
		return ret;
	}
	
	private List<Map<String, Object>> getTestsData() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		if (tests != null) {
			Arrays.asList(tests).stream()
				.forEach(t -> {
					Map<String, Object> tmp = new HashMap<>();
					tmp.put("test_name", t.getName());
					ret.add(tmp);
				}
			);
		}
		
		return ret;
	}

}
