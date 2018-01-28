package me.test.entity.answer;

import java.util.Map;
import java.util.Set;

import me.test.entity.test.Test;
import me.test.entity.test.TestsEntity;

public interface AnswersLoader {
	
	Map<String, Set<Answer>> load(TestsEntity tests); 
	
	Set<Answer> load(Test test);
	
	void save(Answer answer);
	
}
