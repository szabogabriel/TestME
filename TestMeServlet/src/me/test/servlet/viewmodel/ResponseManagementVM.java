package me.test.servlet.viewmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import me.test.entity.answer.Answer;
import me.test.entity.test.Test;

public class ResponseManagementVM implements ViewModel {
	
	private Set<Answer> answers = new HashSet<>();
	private String username = null;
	
	public void add(Answer response) {
		answers.add(response);
	}
	
	public void add(List<Answer> response) {
		this.answers.addAll(response);
	}
	
	public void remove(Test response) {
		answers.remove(response);
	}
	
	public void remove(List<Test> response) {
		this.answers.removeAll(response);
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
	
	private List<String> createTestsData() {
		List<String> ret = new ArrayList<>();
		
		for (Test t : getUniqueTests()) {
			Map<String, Object> tmp = new HashMap<>();
			
			
		}
		
		return ret;
	}
	
	private List<Test> getUniqueTests() {
		return answers.stream().map(a -> a.getTest()).sorted((t1, t2) -> t1.getName().compareTo(t2.getName())).collect(Collectors.toList());
	}
	
	private List<Answer> getAnswers(Test t) {
		return answers.stream().filter(a -> a.getTest().equals(t)).collect(Collectors.toList());
	}
}
