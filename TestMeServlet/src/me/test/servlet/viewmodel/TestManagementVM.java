package me.test.servlet.viewmodel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import me.test.entity.test.Test;

public class TestManagementVM implements ViewModel {
	
	private Set<Test> tests = new HashSet<>();
	private Set<Test> activeTests = new HashSet<>();
	private String username = null;
	
	public void add(Test test) {
		tests.add(test);
	}
	
	public void add(List<Test> tests) {
		this.tests.addAll(tests);
	}
	
	public void remove(Test test) {
		tests.remove(test);
	}
	
	public void remove(List<Test> tests) {
		this.tests.removeAll(tests);
	}
	
	public void activate(Test test) {
		activeTests.add(test);
	}
	
	public void activate(List<Test> tests) {
		activeTests.addAll(tests);
	}
	
	public void deactivate(Test test) {
		activeTests.remove(test);
	}
	
	public void deactivate(List<Test> tests) {
		this.activeTests.removeAll(tests);
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Map<String, Object> provideData() {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("active-manage-tests", Boolean.TRUE);
		ret.put("isLoggedIn", Boolean.TRUE);
		ret.put("loggedIn-username", username);
		ret.put("tests", createTestsData());
		
		return ret;
	}
	
	private List<Map<String, Object>> createTestsData() {
		return tests.stream()
				.sorted((t1, t2) -> t1.getName().compareTo(t2.getName()))
				.map(t -> createTestData(t))
				.collect(Collectors.toList());
	}
	
	private Map<String, Object> createTestData(Test test) {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("test_name", test.getName());
		ret.put("test_active", activeTests.contains(test));
		ret.put("test_desc", test.getActiveDescription());
		
		return ret;
	}

}
