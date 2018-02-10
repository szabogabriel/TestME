package me.test.servlet.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.test.entity.answer.Answer;
import me.test.entity.test.Test;

public class ResponseManagementVM implements ViewModel {
	
	private Map<Test, List<Answer>> answers = new HashMap<>();
	private String username = null;
	
	public void add(Map<Test, List<Answer>> response) {
		if (response != null) {
			this.answers = response;
		}
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Map<String, Object> provideData() {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("active-manage-answers", Boolean.TRUE);
		ret.put("isLoggedIn", Boolean.TRUE);
		ret.put("loggedIn-username", username);
		ret.put("tests", createTestsData());
		
		return ret;
	}
	
	private List<Map<String, Object>> createTestsData() {
		List<Map<String, Object>> ret = new ArrayList<>();
		
		answers.keySet().stream()
			.sorted((k1, k2) -> k1.getName().compareTo(k2.getName()))
			.filter(k -> answers.get(k) != null)
			.filter(k -> answers.get(k).size() > 0)
			.forEach(k -> ret.add(getData(k)));
		
		return ret;
	}
	
	private Map<String, Object> getData(Test test) {
		Map<String, Object> ret = new HashMap<>();
		
		ret.put("testName", test.getName());
		
		return ret;
	}
	
}
