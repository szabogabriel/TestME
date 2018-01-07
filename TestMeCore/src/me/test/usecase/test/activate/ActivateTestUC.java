package me.test.usecase.test.activate;

import java.util.stream.Collectors;

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
			
			te.activate(request.getTestCasesName().stream().map(t -> te.getTestByName(t)).collect(Collectors.toList()));
		} catch (Exception e) {
			e.printStackTrace();
			return () -> Boolean.FALSE;
		}
		
		
		return () -> Boolean.TRUE;
	}
	
}
