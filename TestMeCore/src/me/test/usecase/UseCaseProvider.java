package me.test.usecase;

import me.test.entity.EntityProvider;
import me.test.usecase.test.activate.ActivateTestUC;
import me.test.usecase.test.answer.TestAnswerUC;
import me.test.usecase.test.list.QueryTestsUC;
import me.test.usecase.user.credentials.AuthenticateUserUC;
import me.test.usecase.user.list.QueryUserUC;

public class UseCaseProvider {
	
	private final EntityProvider ENTITY_PROVIDER;
	
	public UseCaseProvider(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public AuthenticateUserUC createAuthenticateUserUC() {
		return new AuthenticateUserUC(ENTITY_PROVIDER);
	}
	
	public ActivateTestUC createActivateTestUC() {
		return new ActivateTestUC(ENTITY_PROVIDER);
	}
	
	public TestAnswerUC createTestAnswerUC() {
		return new TestAnswerUC(ENTITY_PROVIDER);
	}
	
	public QueryTestsUC createQueryTestsUC() {
		return new QueryTestsUC(ENTITY_PROVIDER);
	}
	
	public QueryUserUC createQueryUserUC() {
		return new QueryUserUC(ENTITY_PROVIDER);
	}
	
}
