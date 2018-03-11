package me.test.usecase.test.activate;

import me.test.entity.EntityProvider;
import me.test.entity.test.TestsEntity;

public class ActivateTestUC {
	
	private final EntityProvider ENTITY_PROVIDER; 
	
	public ActivateTestUC(EntityProvider entityProvider) {
		this.ENTITY_PROVIDER = entityProvider;
	}
	
	public ActivateTestResponseData setActiveTests(ActivateTestRequestData request) {
		try {
			TestsEntity te = ENTITY_PROVIDER.getTestEntity();
			
			te.deactivate();
			
			request.getTestCasesName().stream().map(t -> te.getTestByName(t)).forEach(t -> te.activate(t, request.getDescription(t.getName())));
		} catch (Exception e) {
			e.printStackTrace();
			return () -> Boolean.FALSE;
		}
		
		
		return () -> Boolean.TRUE;
	}
	
}
