package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.model.baseinfo.ProgramInCawave;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class CawaveServiceTest extends DatabaseTestCase {
	@Autowired
	CawaveService cawaveService;

	@Test
	public void testSave() {
		//需要级联保存PROGRAM
		ProgramInCawave pic1 = new ProgramInCawave(new Program(1L), new Date(), new Date());
		ProgramInCawave pic2 = new ProgramInCawave(new Program(2L), new Date(), new Date());
		cawaveService.save(new Cawave("fff", new Date(), "wj", Lists.newArrayList(pic1, pic2)));
		this.flushSessionAndCloseSessionAndNewASession();
		//验证已经级联保存对应关系信息
		assertRowsCount(Cawave.class, 4);
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 6);
		Cawave ca = cawaveService.get(4L);
		assertNotNull(ca);
		assertEquals(ca.getProgramInCawaves().size(), 2);
	}

	@Test
	public void testDelete() {
		cawaveService.delete(2L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Cawave.class, 2);
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInOperation() {
		assertRowsCount(Cawave.class, 3);
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 4);
		cawaveService.delete(1L);
		assertRowsCount(Cawave.class, 3);
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 4);
	}

	@Test
	public void testGet() {
		Cawave ca = cawaveService.get(1L);
		assertNotNull(ca);
		assertEquals(ca.getName(), "中央套播");
		//对应信息总数
		assertEquals(ca.getProgramInCawaves().size(), 2);
		//对应业务信息
		assertEquals(ca.getOperationSs().size(), 2);
		assertEquals(ca.getOperationTs().size(), 1);
	}

	@Test
	public void testGetAll() {
		assertEquals(cawaveService.getAll().size(), 3);
	}

}
