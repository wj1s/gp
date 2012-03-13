package gov.abrs.etms.action.abnormal;

import static gov.abrs.etms.action.abnormal.HasEmpAndDateEquals.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.AbnType;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class AbnormalActionTest extends BaseActionTest {
	private AbnormalAction abnormalAction;
	private ExecuteDAO mockexecuteDAO;
	private ParaDtlService mockParaDtlService;
	private AbnormalService mockAbnormalService;
	private SecurityService securityService;
	private UtilService utilService;

	@Before
	public void setup() {
		abnormalAction = new AbnormalAction();
		mockexecuteDAO = control.createMock(ExecuteDAO.class);
		mockParaDtlService = control.createMock(ParaDtlService.class);
		mockAbnormalService = control.createMock(AbnormalService.class);
		securityService = control.createMock(SecurityService.class);
		utilService = control.createMock(UtilService.class);
		abnormalAction.setExecuteDAO(mockexecuteDAO);
		abnormalAction.setParaDtlService(mockParaDtlService);
		abnormalAction.setAbnormalService(mockAbnormalService);
		abnormalAction.setSecurityService(securityService);
		abnormalAction.setUtilService(utilService);
	}

	@Test
	public void testInput() {
		//准备数据
		List<ParaDtl> abnTypes = new ArrayList<ParaDtl>();
		//录制脚本
		EasyMock.expect(mockParaDtlService.get(AbnType.class)).andReturn(abnTypes);
		control.replay();
		//执行测试
		String result = abnormalAction.input();
		assertEquals(result, AbnormalAction.INPUT);
		assertEquals(abnormalAction.getAbnTypes(), abnTypes);

	}

	@Test
	public void testSaveAccd() {
		//准备数据
		Abnormal abnormal = new Abnormal();
		abnormalAction.setModel(abnormal);
		List<Long> abnOperationIds = null;
		abnormalAction.setAbnOperationIds(abnOperationIds);
		List<Long> dutyTimeIds = null;
		abnormalAction.setDutyTimeIds(dutyTimeIds);
		List<String> accdDutys = null;
		abnormalAction.setAccdDutys(accdDutys);
		List<String> dutyTimes = null;
		abnormalAction.setDutyTimes(dutyTimes);
		Person person = new Person();
		person.setName("赵霞");
		Date date = new Date();
		List transTypes = new ArrayList<TransType>();
		//录制脚本
		//差验证emp和参数
		//mockParaDtlService
		EasyMock.expect(mockParaDtlService.get(TransType.class)).andReturn(transTypes);
		mockAbnormalService.assemblyAbts(hasEmpAndDateEquals(abnormal), same(abnOperationIds), same(dutyTimeIds),
				same(accdDutys), same(dutyTimes));
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		mockAbnormalService.save(abnormal);
		control.replay();
		//执行测试
		String fow = abnormalAction.save();
		//
		assertEquals(fow, AbnormalAction.SUCCESS);
	}
}
