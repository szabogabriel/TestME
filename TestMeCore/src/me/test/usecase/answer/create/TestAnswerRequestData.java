package me.test.usecase.answer.create;

public interface TestAnswerRequestData {
	
	String getAnswer(Integer questionNumber);
	
	String getResponderName();
	
	int getResponderAge();
	
	String getTestName();

}
