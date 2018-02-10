package me.test.usecase.answer.list;

import java.util.function.Predicate;

import me.test.entity.test.Test;

public interface QueryAnswerRequestData {
	
	Predicate<Test> getFilter();
	
}
