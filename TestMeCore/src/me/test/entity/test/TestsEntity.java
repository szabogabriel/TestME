package me.test.entity.test;

import java.util.List;
import java.util.stream.Collectors;

public class TestsEntity {
	
	private final TestsLoader LOADER;
	
	public TestsEntity(TestsLoader loader) {
		LOADER = loader;
	}
	
	public Test[] getTests() {
		return LOADER.load().stream().toArray(Test[]::new);
	}
	
	public Test[] getActiveTests() {
		return LOADER.load().stream().filter(t -> t.isActive()).toArray(Test[]::new);
	}
	
	public Test getTestByName(String name) {
		Test ret = LOADER.load(name);
		if (ret.getName() == null) {
			ret = Test.NULL_OBJECT;
		}
		return ret;
	}
	
	public boolean isKnownTest(String name) {
		return LOADER.load().stream().filter(t -> t.getName().equals(name)).findAny().isPresent();
	}
	
	public boolean isActiveTest(String name) {
		return getTestByName(name).isActive();
	}
	
	public void activate(Test test) {
		test.setActive(true);
		LOADER.persist(test);
	}
	
	public void activate(List<Test> tests) {
		tests.stream().forEach(t -> activate(t));
	}
	
	public void deactivate(Test test) {
		test.setActive(false);
		LOADER.persist(test);
	}
	
	public void deactivate() {
		List<Test> tmp = LOADER.load();
		for (Test it : tmp) {
			it.setActive(false);
		}
		LOADER.persist(tmp.stream().collect(Collectors.toList()));
	}
	
}
