package me.test.entity.test;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import me.test.tools.IOUtils;

public class TestsEntity {
	
	private final Set<Test> TESTS = new HashSet<>();
	
	private final File ROOT;
	
	public TestsEntity(File rootFolder) {
		ROOT = rootFolder;
		reload();
	}
	
	public void reload() {
		TESTS.clear();
		
		for (File it : ROOT.listFiles()) {
			if (it.getName().endsWith("properties")) {
				try {
					TESTS.add(new Test(it));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		File activeFile = getActivateFile();
		
		if (activeFile.exists()) {
			IOUtils.readFilesByLine(activeFile).stream()
				.filter(t -> getTestByFileName(t) != Test.NULL_OBJECT)
				.forEach(t -> getTestByFileName(t).setActive(true));
		}
	}
	
	public Test[] getTests() {
		return TESTS.stream().toArray(Test[]::new);
	}
	
	public Test[] getActiveTests() {
		return TESTS.stream().filter(t -> t.isActive()).toArray(Test[]::new);
	}
	
	public Test getTestByFileName(String fileName) {
		return TESTS.stream().filter(t -> t.getFileName().equals(fileName)).findAny().orElse(Test.NULL_OBJECT);
	}
	
	public Test getTestByName(String name) {
		return TESTS.stream().filter(t -> t.getName().equals(name)).findAny().orElse(Test.NULL_OBJECT);
	}
	
	public boolean isKnownTest(String name) {
		return TESTS.stream().filter(t -> t.getName().equals(name)).findAny().isPresent();
	}
	
	public boolean isActiveTest(String name) {
		return getTestByName(name).isActive();
	}
	
	public void activate(Test test) {
		test.setActive(true);
		TESTS.add(test);
		updateActivateFile();
	}
	
	public void activate(List<Test> tests) {
		tests.stream().forEach(t -> activate(t));
	}
	
	public void deactivate(Test test) {
		test.setActive(false);
		TESTS.add(test);
		updateActivateFile();
	}
	
	public void deactivate() {
		TESTS.stream().forEach(t -> t.setActive(false));
		getActivateFile().delete();
	}
	
	private void updateActivateFile() {
		IOUtils.writeFile(getActivateFile(), TESTS.stream().filter(t -> t.isActive()).map(t -> t.getFileName()).collect(Collectors.joining(System.lineSeparator())));
	}
	
	private File getActivateFile() {
		return new File(ROOT.getAbsolutePath() + "/.active");
	}

}
