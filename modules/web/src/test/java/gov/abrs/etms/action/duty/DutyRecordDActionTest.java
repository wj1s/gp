package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.DutyRecordD;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.report.BroadByTimeService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;

import net.sf.json.JSONObject;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class DutyRecordDActionTest extends BaseActionTest {
	private DutyRecordDAction dutyRecordDAction;
	private DutyRecordService dutyRecordService;
	private UtilService utilService;
	private OperationService operationService;
	private SecurityService securityService;
	private BroadByTimeService broadByTimeService;
	private ParaDtlService paraDtlService;

	@Before
	public void setup() {
		dutyRecordDAction = new DutyRecordDAction();
		utilService = control.createMock(UtilService.class);
		dutyRecordService = control.createMock(DutyRecordService.class);
		operationService = control.createMock(OperationService.class);
		securityService = control.createMock(SecurityService.class);
		broadByTimeService = control.createMock(BroadByTimeService.class);
		paraDtlService = control.createMock(ParaDtlService.class);
		dutyRecordDAction.setDutyRecordService(dutyRecordService);
		dutyRecordDAction.setUtilService(utilService);
		dutyRecordDAction.setOperationService(operationService);
		dutyRecordDAction.setSecurityService(securityService);
		dutyRecordDAction.setBroadByTimeService(broadByTimeService);
		dutyRecordDAction.setParaDtlService(paraDtlService);
	}

	@Test
	public void testSave() {
		Date date = new Date();
		Person person = new Person();
		person.setName("赵霞");
		DutyRecordD model = new DutyRecordD();
		Long opId = 1L;
		BroadByTime broadByTime = new BroadByTime();
		broadByTime.setId(3L);
		Date date1 = DateUtil.createDate("2010-12-12 8:00:00");
		Date date2 = DateUtil.createDate("2010-12-12 12:00:00");
		broadByTime.setStartTime(date1);
		broadByTime.setEndTime(date2);
		broadByTime.setAutoFlag(true);
		broadByTime.setBroadByFlag("D");
				broadByTime.setNotifyPerson("赵霞");
		broadByTime.setBroadResult("好");
		BroadByReason broadByReason = new BroadByReason(3L);
		broadByTime.setBroadByReason(broadByReason);
		BroadByStation broadByStation = new BroadByStation(4L);
		broadByTime.setBroadByStation(broadByStation);
		model.setId(1L);
		model.setBroadByTime(broadByTime);
		Operation opertion = new Operation();
		opertion.setName("CCTV1/2/3");

		JSONObject a = new JSONObject();
		a.put("result", true);
		a.put("id", model.getId());
		a.put("content",
				"业务CCTV1/2/3于2010年12月12日 00:00 - 2010年12月12日 00:00进行代播。代播原因为null，是自动代播。 代播被代标志为：是。 对方站名称为： 北京地球站， 通知人是赵霞。 代播结果为：好");
		String json = a.toString();

		dutyRecordDAction.setOpId(opId);
		dutyRecordDAction.setModel(model);
		dutyRecordDAction.setReasonId(3L);

		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(operationService.get(opId)).andReturn(opertion);
		EasyMock.expect(broadByTimeService.get(3L)).andReturn(broadByTime);
		EasyMock.expect(paraDtlService.get(3L)).andReturn(broadByReason);
		EasyMock.expect(paraDtlService.get(4L)).andReturn(broadByStation);

		this.dutyRecordService.save(model);
		control.replay();

		String result = dutyRecordDAction.save();
		assertEquals(result, dutyRecordDAction.EASY);
	}
}
