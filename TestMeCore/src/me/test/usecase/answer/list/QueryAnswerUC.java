package me.test.usecase.answer.list;

import me.test.entity.EntityProvider;

public class QueryAnswerUC {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public QueryAnswerUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public QueryAnswerResponseData getAnswers(QueryAnswerRequestData request) {
		return () -> null;
	}

}
