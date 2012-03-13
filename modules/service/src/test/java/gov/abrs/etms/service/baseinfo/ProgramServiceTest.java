package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.model.baseinfo.ProgramInCawave;
import gov.abrs.etms.model.para.ProgramType;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class ProgramServiceTest extends DatabaseTestCase {
	@Autowired
	ProgramService programService;

	@Test
	public void testSave() {
		programService.save(new Program("a", "b", new ProgramType(12L), new Date(), "wj"));
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Program.class, 6);
	}

	//测试孤立节目的删除
	@Test
	public void testDeleteNotInOperationAndNotInCawave() {
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 4);
		assertRowsCount(Cawave.class, 3);
		assertRowsCount(Operation.class, 5);
		programService.delete(4L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Program.class, 4);
		assertRowsCount(ProgramInCawave.class, 4);
		assertRowsCount(Cawave.class, 3);
		assertRowsCount(Operation.class, 5);
	}

	//测试不关联编码业务，但是属于节目流，但节目流又不在业务中的
	@Test
	public void testDeleteNotInOperationButInCawave() {
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 4);
		assertRowsCount(Cawave.class, 3);
		programService.delete(5L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Program.class, 4);
		assertRowsCount(ProgramInCawave.class, 3);
		assertRowsCount(Cawave.class, 3);
	}

	//测试关联业务
	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInOperation() {
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 4);
		assertRowsCount(Cawave.class, 3);
		assertRowsCount(Operation.class, 5);
		programService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Program.class, 5);
		assertRowsCount(ProgramInCawave.class, 4);
		assertRowsCount(Cawave.class, 3);
		assertRowsCount(Operation.class, 5);
	}

	@Test
	public void testGet() {
		Program pgm = programService.get(1L);
		assertEquals(pgm.getName(), "中一");
		assertEquals(pgm.getProgramInCawaves().size(), 1);
		assertEquals(pgm.getOperationPs().size(), 1);//对应业务信息
	}

	@Test
	public void testGetAll() {
		assertEquals(programService.getAll().size(), 5);
	}

}
