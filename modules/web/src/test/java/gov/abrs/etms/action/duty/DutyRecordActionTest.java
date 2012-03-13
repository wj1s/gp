package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.duty.DutyRecord;
import gov.abrs.etms.model.duty.DutyRecordD;
import gov.abrs.etms.model.duty.DutyRecordW;
import gov.abrs.etms.model.duty.DutyWarning;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.WarnType;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.security.SecurityService;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DutyRecordActionTest extends BaseActionTest {

	private DutyRecordAction dutyRecordAction;
	private DutyRecordService dutyRecordService;
	private SecurityService securityService;
	private ParaDtlService paraDtlService;
	private OperationService operationService;

	@Before
	public void setup() {
		dutyRecordAction = new DutyRecordAction();
		dutyRecordService = control.createMock(DutyRecordService.class);
		securityService = control.createMock(SecurityService.class);
		paraDtlService = control.createMock(ParaDtlService.class);
		operationService = control.createMock(OperationService.class);
		dutyRecordAction.setDutyRecordService(dutyRecordService);
		dutyRecordAction.setSecurityService(securityService);
		dutyRecordAction.setParaDtlService(paraDtlService);
		dutyRecordAction.setOperationService(operationService);
	}

	@Test
	public void testDelete() {
		Long id = 1L;

		dutyRecordAction.setId(id);

		dutyRecordService.delete(id);
		control.replay();

		String result = dutyRecordAction.delete();
		assertEquals(result, dutyRecordAction.EASY);
	}

	@Test
	public void testToAdd() {
		TransType transType = new TransType(1L);
		List<TransType> transTypeList = Lists.newArrayList(transType);
		List<Operation> operationList = Lists.newArrayList(new Operation(1L));
		List<ParaDtl> broadByReasonList = Lists.newArrayList(new ParaDtl(15L));
		List<ParaDtl> broadByStationList = Lists.newArrayList(new ParaDtl(15L));
		List<ParaDtl> warnTypes = Lists.newArrayList(new ParaDtl(15L));
		EasyMock.expect(securityService.getCurTransTypes(FunModule.DUTY)).andReturn(transTypeList);
		EasyMock.expect(paraDtlService.get(WarnType.class)).andReturn(warnTypes);
		EasyMock.expect(operationService.get(transType)).andReturn(operationList);
		EasyMock.expect(paraDtlService.get(BroadByReason.class)).andReturn(broadByReasonList);
		EasyMock.expect(paraDtlService.get(BroadByStation.class)).andReturn(broadByStationList);
		control.replay();

		String result = dutyRecordAction.toAdd();

		List<Operation> ops = dutyRecordAction.getOperationList();
		assertEquals(ops.size(), 1);
		assertSame(ops.get(0).getId(), 1L);
		List<ParaDtl> wts = dutyRecordAction.getWarnTypeList();
		assertEquals(wts.size(), 1);
		assertSame(wts.get(0).getId(), 15L);
		assertEquals(dutyRecordAction.getTransTypeList(), transTypeList);
		assertEquals(result, "input");
	}

	@Test
	public void testToEdit1() {
		TransType transType = new TransType(1L);
		List<TransType> transTypeList = Lists.newArrayList(transType);
		BroadByTime broadByTime = new BroadByTime();
		Operation operation = new Operation();
		operation.setTransType(transType);
		broadByTime.setOperation(operation);
		DutyRecord dutyRecordD = new DutyRecordD(new Duty(), new Date(), "123", broadByTime);
		dutyRecordAction.setId(1L);
		List<Operation> operationList = Lists.newArrayList(new Operation(1L));
		List<ParaDtl> broadByReasonList = Lists.newArrayList(new ParaDtl(15L));
		List<ParaDtl> broadByStationList = Lists.newArrayList(new ParaDtl(15L));
		EasyMock.expect(securityService.getCurTransTypes(FunModule.DUTY)).andReturn(transTypeList);
		EasyMock.expect(operationService.get(transType)).andReturn(operationList);
		EasyMock.expect(dutyRecordService.get(1L)).andReturn(dutyRecordD);
		EasyMock.expect(paraDtlService.get(BroadByReason.class)).andReturn(broadByReasonList);
		EasyMock.expect(paraDtlService.get(BroadByStation.class)).andReturn(broadByStationList);
		control.replay();

		String result = dutyRecordAction.toEdit();

		List<Operation> ops = dutyRecordAction.getOperationList();
		assertEquals(ops.size(), 1);
		assertSame(ops.get(0).getId(), 1L);
		assertEquals(dutyRecordAction.getTransTypeList(), transTypeList);
		assertEquals(result, "input");
	}

	@Test
	public void testToEdit2() {
		Dept dept = new Dept();
		TransType transType = new TransType(1L);
		dept.setTransTypes(Lists.newArrayList(transType));
		Person person = new Person();
		person.setDept(dept);
		DutyWarning dutyWarning = new DutyWarning();
		Operation operation = new Operation();
		operation.setTransType(transType);
		dutyWarning.setOperation(operation);
		DutyRecord dutyRecordW = new DutyRecordW(new Duty(), new Date(), "123", dutyWarning);
		dutyRecordAction.setId(1L);
		List<Operation> operationList = Lists.newArrayList(new Operation(1L));
		List<ParaDtl> warnTypes = Lists.newArrayList(new ParaDtl(15L));
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(paraDtlService.get(WarnType.class)).andReturn(warnTypes);
		EasyMock.expect(operationService.get(transType)).andReturn(operationList);
		EasyMock.expect(dutyRecordService.get(1L)).andReturn(dutyRecordW);
		control.replay();

		String result = dutyRecordAction.toEdit();

		List<Operation> ops = dutyRecordAction.getOperationList();
		assertEquals(ops.size(), 1);
		assertSame(ops.get(0).getId(), 1L);
		List<ParaDtl> wts = dutyRecordAction.getWarnTypeList();
		assertEquals(wts.size(), 1);
		assertSame(wts.get(0).getId(), 15L);
		assertEquals(result, "input");
	}

}
