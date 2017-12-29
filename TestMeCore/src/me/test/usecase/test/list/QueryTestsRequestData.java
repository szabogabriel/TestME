package me.test.usecase.test.list;

import java.util.function.Predicate;

import me.test.entity.test.Test;

public interface QueryTestsRequestData {
	
	Boolean getActiveOnly();
	
	Predicate<Test> getFilter();

}
