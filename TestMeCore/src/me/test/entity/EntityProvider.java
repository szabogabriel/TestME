package me.test.entity;

import me.test.entity.answer.AnswersEntity;
import me.test.entity.test.TestsEntity;
import me.test.entity.user.UserEntity;

public class EntityProvider {
	
	private final EntityProviderInitData INIT_DATA;
	
	private TestsEntity testEntity = null;
	private UserEntity userEntity = null;
	private AnswersEntity answerEntity = null;
	
	
	// ========================================
	//              Constructors
	// ========================================
	public EntityProvider() {
		INIT_DATA = EntityProviderInitData.DEFAULT_OBJECT;
	}
	
	public EntityProvider(EntityProviderInitData initData) {
		INIT_DATA = initData;
	}
	
	
	// ========================================
	//                Entities
	// ========================================
	public TestsEntity getTestEntity() {
		if (testEntity == null) {
			testEntity = new TestsEntity(INIT_DATA.getTestsLoader());
		}
		return testEntity;
	}
	
	public UserEntity getUserEntity() {
		if (userEntity == null) {
			userEntity = new UserEntity(INIT_DATA.getUsersFolder());
		}
		 return userEntity;
	}
	
	public AnswersEntity getAnswersEntity() {
		if (answerEntity == null) {
			answerEntity = new AnswersEntity(INIT_DATA.getAnswersLoader());
		}
		return answerEntity;
	}

}
