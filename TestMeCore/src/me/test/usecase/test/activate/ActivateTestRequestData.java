package me.test.usecase.test.activate;

import java.util.Set;

public interface ActivateTestRequestData {
	
	Set<String> getTestCasesName();
	
	String getDescription(String testName);
	
}
