package me.test.usecase.answer.list;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import me.test.entity.EntityProvider;
import me.test.entity.answer.Answer;
import me.test.entity.test.Test;

public class QueryAnswerUC {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public QueryAnswerUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public QueryAnswerResponseData getAnswers(QueryAnswerRequestData request) {
		Predicate<Test> filterRule = (request.getFilter() == null) ? (t -> true) : request.getFilter();
		Map<Test, List<Answer>> returnValue = new HashMap<>();
		
		Arrays.asList(ENTITY_PROVIDER.getTestEntity().getTests()).stream()
			.filter(filterRule)
			.forEach(t -> returnValue.put(t, ENTITY_PROVIDER.getAnswersEntity().load(t)));
		
		return () -> returnValue;
	}

}
