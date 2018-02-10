package me.test.usecase.answer.create;

import me.test.entity.EntityProvider;
import me.test.entity.answer.Answer;
import me.test.entity.test.AnswerDescription;
import me.test.entity.test.Question;
import me.test.entity.test.Test;

public class TestAnswerUC {
	
	private final EntityProvider ENTITY_PROVIDER; 
	
	public TestAnswerUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public TestAnswerResponseData publishAnswer(TestAnswerRequestData data) {
		Answer answer = new Answer();
		
		Test test = ENTITY_PROVIDER.getTestEntity().getTestByName(data.getTestName());
		
		answer.setTest(test);
		answer.setAge(data.getResponderAge());
		answer.setTimestamp(System.currentTimeMillis());
		answer.setUser(data.getResponderName());
		
		Question[] questions = test.getQuestions();
		
		for (int i = 0; i < questions.length; i++) {
			answer.addAnswer(questions[i], getDescription(test.getAnswerDescriptions(), data.getAnswer(i)));
		}
		
		ENTITY_PROVIDER.getAnswersEntity().save(answer);
		
		return () -> answer;
	}
	
	private AnswerDescription getDescription(AnswerDescription[] descriptions, String text) {
		for (AnswerDescription it : descriptions) {
			if (it.getDescription().equals(text)) {
				return it;
			}
		}
		return null;
	}

}