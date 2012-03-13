package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.para.TransType;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class TransTypeServiceTest extends DatabaseTestCase {
	@Autowired
	TransTypeService transTypeService;

	@Test
	public void testGet() {
		assertEquals(transTypeService.getAll().size(), 3);
	}

	@Test
	public void testGetAll() {
		TransType transType = transTypeService.get(3L);
		assertEquals(transType.getCodeDesc(), "卫星上行");
		assertEquals(transType.getTechSystems().size(), 1);
		assertEquals(transType.getTaches().size(), 3);
		assertEquals(transType.getOperations().size(), 3);
	}

}
