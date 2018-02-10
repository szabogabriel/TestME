package me.test.entity.answer;

import java.util.List;
import java.util.Map;

import me.test.entity.test.Test;
import me.test.entity.test.TestsEntity;

public interface AnswersLoader {
	
	Map<String, List<Answer>> load(TestsEntity tests); 
	
	List<Answer> load(Test test);
	
	void save(Answer answer);
	
}
