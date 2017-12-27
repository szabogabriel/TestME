package me.test.entity;

public class EntityProvider {
	
	private final EntityProviderInitData INIT_DATA;
	
	public EntityProvider() {
		INIT_DATA = EntityProviderInitData.DEFAULT_OBJECT;
	}
	
	public EntityProvider(EntityProviderInitData initData) {
		INIT_DATA = initData;
	}

}
