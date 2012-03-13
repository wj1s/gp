package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.OperationP;
import gov.abrs.etms.model.baseinfo.OperationS;
import gov.abrs.etms.model.baseinfo.OperationT;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.model.baseinfo.Route;
import gov.abrs.etms.model.baseinfo.Schedule;
import gov.abrs.etms.model.baseinfo.Transfer;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.TransmitDef;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class OperationServiceTest extends DatabaseTestCase {
	@Autowired
	OperationService operationService;

	@Test
	public void testGet() {
		//平台业务
		Operation opP = operationService.get(1L);
		assertTrue(opP instanceof OperationP);
		assertNotNull(((OperationP) opP).getProgram());
		//传输业务
		Operation opT = operationService.get(2L);
		assertTrue(opT instanceof OperationT);
		assertNotNull(((OperationT) opT).getCawaveT());
		assertNotNull(((OperationT) opT).getRoute());
		//卫星业务
		Operation opS = operationService.get(3L);
		assertTrue(opS instanceof OperationS);
		assertEquals(opS.getSchedules().size(), 1);
		assertNotNull(((OperationS) opS).getCawaveS());
		assertNotNull(((OperationS) opS).getTransfer());
	}

	@Test
	public void testGetAll() {
		assertEquals(operationService.getAll().size(), 5);
	}

	@Test
	public void testSaveP() {
		OperationP op = new OperationP("a", new TransmitDef(17L), new TransType(1L), 1.1, 1.1, new Date(), new Date(),
				"wj", new Program(1L));
		operationService.save(op);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(OperationP.class, 2);
	}

	@Test
	public void testSaveS() {
		OperationS op = new OperationS("a", new TransmitDef(17L), new TransType(1L), 1.1, 1.1, new Date(), new Date(),
				"wj", new Cawave(1L), new Transfer(1L));
		operationService.save(op);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(OperationS.class, 4);
	}

	@Test
	public void testSaveT() {
		OperationT op = new OperationT("a", new TransmitDef(17L), new TransType(1L), 1.1, 1.1, new Date(), new Date(),
				"wj", new Cawave(1L), new Route(1L));
		operationService.save(op);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(OperationT.class, 2);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDelete() {
		//其他信息不受影响，运行图级联删除，通路不变
		operationService.delete(2L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Program.class, 5);//节目不受影响
		assertRowsCount(Operation.class, 4);//业务删除
		assertRowsCount(OperationT.class, 0);//业务删除
		assertRowsCount(Channel.class, 2);//通路不受影响
		assertRowsCount(Schedule.class, 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteT() {
		//其他信息不受影响，运行图级联删除，通路不变
		operationService.delete(2L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Cawave.class, 3);//节目流不受影响
		assertRowsCount(Route.class, 2);//路由不受影响
		assertRowsCount(Operation.class, 4);//业务删除
		assertRowsCount(OperationT.class, 0);//业务删除
		assertRowsCount(Channel.class, 2);//通路不受影响
		assertRowsCount(Schedule.class, 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteP2() {
		assertRowsCount(Operation.class, 5);//业务删除
		operationService.delete(1L);
		assertRowsCount(Operation.class, 5);//业务删除
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteS() {
		//判断有异态的情况，如果有异态，则不能删除
		assertRowsCount(Operation.class, 5);//业务删除
		assertRowsCount(OperationS.class, 3);//业务删除
		operationService.delete(3L);
		assertRowsCount(Operation.class, 5);//业务删除
		assertRowsCount(OperationS.class, 3);//业务删除
	}

	@Test
	public void testGetByStartDateEndDate() {
		Date startDate = DateUtil.createDate("2010-10-01");
		Date endDate = DateUtil.createDate("2010-10-31");
		List<Operation> list = operationService.get(startDate, endDate);
		assertEquals(5, list.size());
	}

}
