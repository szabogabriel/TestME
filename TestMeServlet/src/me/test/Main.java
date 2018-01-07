package me.test;

import java.io.File;

import me.test.config.Config;
import me.test.entity.EntityProvider;
import me.test.entity.EntityProviderInitData;
import me.test.usecase.UseCaseProvider;
import me.test.usecase.test.activate.ActivateTestUC;
import me.test.usecase.test.answer.TestAnswerUC;
import me.test.usecase.test.list.QueryTestsUC;
import me.test.usecase.user.create.CreateUserUC;
import me.test.usecase.user.credentials.AuthenticateUserUC;
import me.test.usecase.user.forcepassword.ForcePasswordUC;
import me.test.usecase.user.list.QueryUserUC;

public class Main implements EntityProviderInitData {
	
	public static final Main INSTANCE = new Main();
	
	private final EntityProvider ENTITY_PROVIDER;
	private final UseCaseProvider USE_CASE_PROVIDER;
	
	private Main() {
		ENTITY_PROVIDER = new EntityProvider(this);
		USE_CASE_PROVIDER = new UseCaseProvider(ENTITY_PROVIDER);
	}

	// ==============================================
	//              Overriden methods
	// ==============================================
	@Override
	public File getTestsFolder() {
		return new File(Config.DIR_TESTS.toString());
	}

	@Override
	public File getUsersFolder() {
		return new File(Config.DIR_USERS.toString());
	}
	
	// ==============================================
	//            Use Case access methods
	// ==============================================
	public AuthenticateUserUC getAuthenticateUserUC() {
		return USE_CASE_PROVIDER.createAuthenticateUserUC();
	}
	
	public ActivateTestUC getActivateTestUC() {
		return USE_CASE_PROVIDER.createActivateTestUC();
	}
	
	public CreateUserUC getCreateUserUC() {
		return USE_CASE_PROVIDER.createCreateUserUC();
	}
	
	public TestAnswerUC getTestAnswerUC() {
		return USE_CASE_PROVIDER.createTestAnswerUC();
	}
	
	public QueryTestsUC getQueryTestsUC() {
		return USE_CASE_PROVIDER.createQueryTestsUC();
	}
	
	public QueryUserUC getQueryUserUC() {
		return USE_CASE_PROVIDER.createQueryUserUC();
	}
	
	public ForcePasswordUC getForcePasswordUC() {
		return USE_CASE_PROVIDER.createForcePasswordUC();
	}
}
