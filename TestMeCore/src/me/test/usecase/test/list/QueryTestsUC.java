
package me.test.usecase.test.list;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import me.test.entity.EntityProvider;
import me.test.entity.test.Test;

public class QueryTestsUC {
	
	private final EntityProvider ENTITY_PROVIDER; 
	
	public QueryTestsUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public QueryTestsResponseData getTests(QueryTestsRequestData data) {
		Predicate<Test> filterToUse = (data.getFilter() == null) ? t -> true : data.getFilter();
		
		List<Test> ret = Arrays.asList(ENTITY_PROVIDER.getTestEntity().getTests()).stream()
				.filter(filterToUse)
				.collect(Collectors.toList());
				
		return () -> ret;
	}
	
}
