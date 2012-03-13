package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.duty.DutyRecordW;
import gov.abrs.etms.model.duty.DutyWarning;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.WarnType;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class DutyRecordWActionTest extends BaseActionTest {
	private DutyRecordWAction dutyRecordWAction;
	private DutyRecordService dutyRecordService;
	private UtilService utilService;
	private OperationService operationService;
	private ParaDtlService paraDtlService;

	@Before
	public void setup() {
		dutyRecordWAction = new DutyRecordWAction();
		utilService = control.createMock(UtilService.class);
		dutyRecordService = control.createMock(DutyRecordService.class);
		operationService = control.createMock(OperationService.class);
		paraDtlService = control.createMock(ParaDtlService.class);
		dutyRecordWAction.setOperationService(operationService);
		dutyRecordWAction.setDutyRecordService(dutyRecordService);
		dutyRecordWAction.setUtilService(utilService);
		dutyRecordWAction.setParaDtlService(paraDtlService);
	}

	@Test
	public void testSave() {
		Date date = new Date();
		DutyRecordW model = new DutyRecordW();
		Long opId = 1L;
		Operation opertion = new Operation();
		Long wnTpId = 1L;
		ParaDtl warnType = new WarnType();
		model.setId(1L);
		model.setDutyWarning(new DutyWarning());

		dutyRecordWAction.setModel(model);
		dutyRecordWAction.setOpId(opId);
		dutyRecordWAction.setWnTpId(wnTpId);

		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(operationService.get(opId)).andReturn(opertion);
		EasyMock.expect(paraDtlService.get(wnTpId)).andReturn(warnType);
		this.dutyRecordService.save(model);
		control.replay();

		String result = dutyRecordWAction.save();
		assertEquals(result, dutyRecordWAction.EASY);
	}
}
