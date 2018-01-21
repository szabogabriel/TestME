package me.test.entity.answer;

import java.util.Map;
import java.util.Set;

public interface AnswersLoader {
	
	Map<String, Set<Answer>> load(); 
	
	void save(String test, Answer answer);

}
