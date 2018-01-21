package me.test.entity.answer;

import java.util.Map;
import java.util.Set;

public interface AnswersLoader {
	
	Map<String, Set<Answer>> load(); 
	
	Set<Answer> load(String test);
	
	void save(String test, Answer answer);
	
}
