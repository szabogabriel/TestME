package me.test.entity.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestsEntity {
	
	private final List<Test> TESTS = new ArrayList<>();
	
	private final Set<Test> ACTIVE = new HashSet<>();
	
	private final File ROOT;
	
	public TestsEntity(File rootFolder) {
		ROOT = rootFolder;
		reload();
	}
	
	public void reload() {
		TESTS.clear();
		ACTIVE.clear();
		
		for (File it : ROOT.listFiles()) {
			if (it.getName().endsWith("properties")) {
				try {
					TESTS.add(new Test(it));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public Test[] getTests() {
		return TESTS.stream().toArray(Test[]::new);
	}
	
	public Test[] getActiveTests() {
		return ACTIVE.stream().toArray(Test[]::new);
	}
	
	public Test getTest(String name) {
		return TESTS.stream().filter(t -> t.getName().equals(name)).findAny().orElse(null);
	}
	
	public boolean isKnownTest(String name) {
		return TESTS.stream().filter(t -> t.getName().equals(name)).findAny().isPresent();
	}
	
	public boolean isActiveTest(String name) {
		return ACTIVE.stream().filter(t -> t.getName().equals(name)).findAny().isPresent();
	}
	
	public void activate(Test test) {
		if (!ACTIVE.contains(test)) {
			ACTIVE.add(test);
		}
	}
	
	public void deactivate(Test test) {
		if (ACTIVE.contains(test)) {
			ACTIVE.remove(test);
		}
	}

}
