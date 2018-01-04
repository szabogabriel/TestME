package me.test.entity.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import me.test.tools.IOUtils;

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
		
		File activeFile = getActivateFile();
		
		if (activeFile.exists()) {
			IOUtils.readFilesByLine(activeFile).stream()
				.map(t -> getTestByFileName(t))
				.forEach(t -> ACTIVE.add(t));
		}
	}
	
	public Test[] getTests() {
		return TESTS.stream().toArray(Test[]::new);
	}
	
	public Test[] getActiveTests() {
		return ACTIVE.stream().toArray(Test[]::new);
	}
	
	public Test getTestByFileName(String fileName) {
		return TESTS.stream().filter(t -> t.getFileName().equals(fileName)).findAny().orElse(null);
	}
	
	public Test getTestByName(String name) {
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
			IOUtils.writeFile(getActivateFile(), test.getFileName(), true);
		}
	}
	
	public void deactivate(Test test) {
		if (ACTIVE.contains(test)) {
			ACTIVE.remove(test);
			IOUtils.writeFile(getActivateFile(), ACTIVE.stream().map(t -> t.getFileName()).collect(Collectors.joining(System.lineSeparator())));
		}
	}
	
	private File getActivateFile() {
		return new File(ROOT.getAbsolutePath() + "/.active");
	}

}
