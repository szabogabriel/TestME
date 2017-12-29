package me.test.usecase.test.list;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import me.test.entity.EntityProvider;
import me.test.entity.test.Test;

public class QueryTestsUC {
	
	private final EntityProvider ENTITY_PROVIDER; 
	
	public QueryTestsUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public QueryTestsResponseData getTests(QueryTestsRequestData data) {
		List<Test> ret = 
				Arrays.asList(getUnfilteredTests(data.getActiveOnly()))
					.stream()
					.filter(data.getFilter())
					.collect(Collectors.toList());
		
		return () -> ret;
	}
	
	private Test[] getUnfilteredTests(boolean onlyActive) {
		return onlyActive ? ENTITY_PROVIDER.getTestEntity().getActiveTests() : ENTITY_PROVIDER.getTestEntity().getTests();
	}
	
}
