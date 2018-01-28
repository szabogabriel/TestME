package me.test.usecase.test.answer;

public interface TestAnswerRequestData {
	
	String getAnswer(Integer questionNumber);
	
	String getResponderName();
	
	int getResponderAge();
	
	String getTestName();

}
