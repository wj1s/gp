package gov.abrs.etms.action.abnormal;

import static gov.abrs.etms.action.abnormal.HasEmpAndDateEquals.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.abnormal.AbnormalO;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.AbnType;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

public class AbnormalOActionTest extends BaseActionTest {
	private AbnormalOAction abnormalOAction;
	private AbnormalService mockAbnormalService;
	private SecurityService mockSecurityService;
	private UtilService mockUtilService;
	private WorkFlowService mockWorkFlowService;
	private ParaDtlService mockParaDtlService;

	@Before
	public void setup() {
		abnormalOAction = new AbnormalOAction();
		mockAbnormalService = control.createMock(AbnormalService.class);
		mockSecurityService = control.createMock(SecurityService.class);
		mockUtilService = control.createMock(UtilService.class);
		mockWorkFlowService = control.createMock(WorkFlowService.class);
		mockParaDtlService = control.createMock(ParaDtlService.class);
		abnormalOAction.setAbnormalService(mockAbnormalService);
		abnormalOAction.setSecurityService(mockSecurityService);
		abnormalOAction.setUtilService(mockUtilService);
		abnormalOAction.setWorkFlowService(mockWorkFlowService);
		abnormalOAction.setParaDtlService(mockParaDtlService);
	}

	@Test
	public void testSave() {
		//准备数据
		AbnormalO abnormalO = new AbnormalO();
		abnormalO.setId(1L);
		abnormalO.setAbnType(new AbnType(1L));
		abnormalOAction.setModel(abnormalO);
		List<Long> abneIds = null;
		abnormalOAction.setAbneIds(abneIds);
		List<String> abneNames = null;
		abnormalOAction.setAbneNames(abneNames);
		List<Date> abneStartTimes = null;
		abnormalOAction.setAbneStartTimes(abneStartTimes);
		List<Date> abneEndTimes = null;
		abnormalOAction.setAbneEndTimes(abneEndTimes);
		List<Integer> faultFlags = null;
		abnormalOAction.setFaultFlags(faultFlags);
		List<Long> abnoIds = null;
		abnormalOAction.setAbnoIds(abnoIds);
		List<String> abnoNames = null;
		abnormalOAction.setAbnoNames(abnoNames);
		List<Date> abnoStartTimes = null;
		abnormalOAction.setAbnoStartTimes(abnoStartTimes);
		List<Date> abnoEndTimes = null;
		abnormalOAction.setAbnoEndTimes(abnoEndTimes);
		List<Integer> accdFlags = null;
		abnormalOAction.setAccdFlags(accdFlags);
		abnormalOAction.setEditInJb(0L);
		Dept dept = new Dept();
		Person person = new Person();
		person.setName("赵霞");
		person.setDept(dept);
		Date date = new Date();
		//录制脚本
		//差验证emp和参数
		mockAbnormalService.assemblyAbos(hasEmpAndDateEquals(abnormalO), same(abnoIds), same(abnoNames),
				same(abnoStartTimes), same(abnoEndTimes), same(accdFlags));
		mockAbnormalService.assemblyAbes(hasEmpAndDateEquals(abnormalO), same(abneIds), same(abneNames),
				same(abneStartTimes), same(abneEndTimes), same(faultFlags));
		EasyMock.expect(mockSecurityService.getCurUser()).andReturn(person).times(2);
		EasyMock.expect(mockUtilService.getSysTime()).andReturn(date);
		EasyMock.expect(mockParaDtlService.get(1L)).andReturn(new ParaDtl("", "", "1", ""));
		mockAbnormalService.save(abnormalO);
		EasyMock.expect(
				mockWorkFlowService.startProcessInstance("accidentReport", "1", "异态：1, 时间：到", person, "上报异态", dept,
						FunModule.ACCD)).andReturn(new ProcessInstance());
		control.replay();
		//执行测试
		String fow = abnormalOAction.save();
		//验证结果
		assertEquals(fow, AbnormalFAction.RIGHT);
	}

	//测试由技办提交时，只保存不操作流程
	@Test
	public void testSaveSuffix() {
		//准备数据
		AbnormalO abnormalO = new AbnormalO();
		abnormalO.setId(1L);
		abnormalOAction.setModel(abnormalO);
		List<Long> abneIds = null;
		abnormalOAction.setAbneIds(abneIds);
		List<String> abneNames = null;
		abnormalOAction.setAbneNames(abneNames);
		List<Date> abneStartTimes = null;
		abnormalOAction.setAbneStartTimes(abneStartTimes);
		List<Date> abneEndTimes = null;
		abnormalOAction.setAbneEndTimes(abneEndTimes);
		List<Integer> faultFlags = null;
		abnormalOAction.setFaultFlags(faultFlags);
		List<Long> abnoIds = null;
		abnormalOAction.setAbnoIds(abnoIds);
		List<String> abnoNames = null;
		abnormalOAction.setAbnoNames(abnoNames);
		List<Date> abnoStartTimes = null;
		abnormalOAction.setAbnoStartTimes(abnoStartTimes);
		List<Date> abnoEndTimes = null;
		abnormalOAction.setAbnoEndTimes(abnoEndTimes);
		List<Integer> accdFlags = null;
		abnormalOAction.setAccdFlags(accdFlags);
		abnormalOAction.setEditInJb(1L);
		Dept dept = new Dept();
		Person person = new Person();
		person.setName("赵霞");
		person.setDept(dept);
		Date date = new Date();
		//录制脚本
		//差验证emp和参数
		mockAbnormalService.assemblyAbos(hasEmpAndDateEquals(abnormalO), same(abnoIds), same(abnoNames),
				same(abnoStartTimes), same(abnoEndTimes), same(accdFlags));
		mockAbnormalService.assemblyAbes(hasEmpAndDateEquals(abnormalO), same(abneIds), same(abneNames),
				same(abneStartTimes), same(abneEndTimes), same(faultFlags));
		EasyMock.expect(mockSecurityService.getCurUser()).andReturn(person);
		EasyMock.expect(mockUtilService.getSysTime()).andReturn(date);
		mockAbnormalService.save(abnormalO);
		control.replay();
		//执行测试
		String fow = abnormalOAction.save();
		//验证结果
		assertEquals(fow, AbnormalFAction.MODEL);
		assertEquals(abnormalOAction.getModel(), abnormalO);
	}
}
