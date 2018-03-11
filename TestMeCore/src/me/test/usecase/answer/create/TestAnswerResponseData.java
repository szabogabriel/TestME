package me.test.usecase.answer.create;

import me.test.entity.test.AnswerType;

public interface TestAnswerResponseData {
	
	boolean isTestFilledCorrectly();
	
	AnswerType[] getDescriptions();

}
