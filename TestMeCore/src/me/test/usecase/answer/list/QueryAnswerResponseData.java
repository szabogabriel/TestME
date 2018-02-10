package me.test.usecase.answer.list;

import java.util.List;
import java.util.Map;

import me.test.entity.answer.Answer;
import me.test.entity.test.Test;

public interface QueryAnswerResponseData {
	
	Map<Test, List<Answer>> getAnswers();

}
