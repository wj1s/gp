package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.para.AbnType;
import gov.abrs.etms.model.para.ParaDtl;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class ParaDtlServiceTest extends DatabaseTestCase {
	@Autowired
	ParaDtlService paraDtlService;

	@Test
	public void testGetFaultType() {
		List<ParaDtl> result = paraDtlService.get(AbnType.class);
		assertEquals(result.size(), 1);
		assertEquals(result.get(0).getCodeDesc(), "故障类型1");
	}

}
