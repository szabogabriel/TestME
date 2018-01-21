package me.test.entity.test;

import java.util.List;

public interface TestsLoader {
	
	List<Test> load();
	
	Test load(String name);
	
	void persist(Test test);
	
	void persist(List<Test> tests);

}
