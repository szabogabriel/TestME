package me.test.usecase.answer.list;

import java.util.function.Predicate;

import me.test.entity.answer.Answer;

public interface ListAnswerRequestData {
	
	Predicate<Answer> getFilter();

}
