package gov.abrs.etms.model.abnormal;

import static org.junit.Assert.*;

import org.junit.Test;

public class AbnOperationATest {

	@Test
	public void testGetJsonObject() {
		AbnOperationA aoa = new AbnOperationA();
		assertNotNull(aoa.getJsonObject());
	}
}
