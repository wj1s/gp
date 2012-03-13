package gov.abrs.etms.action.abnormal;

import static gov.abrs.etms.action.abnormal.HasEmpAndDateEquals.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.abnormal.AbnormalF;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

public class AbnormalFActionTest extends BaseActionTest {
	private AbnormalFAction abnormalFAction;
	private AbnormalService mockAbnormalService;
	private EquipService mockEquipService;
	private SecurityService mockSecurityService;
	private UtilService mockUtilService;
	private WorkFlowService mockWorkFlowService;

	@Before
	public void setup() {
		abnormalFAction = new AbnormalFAction();
		mockAbnormalService = control.createMock(AbnormalService.class);
		mockEquipService = control.createMock(EquipService.class);
		mockSecurityService = control.createMock(SecurityService.class);
		mockUtilService = control.createMock(UtilService.class);
		mockWorkFlowService = control.createMock(WorkFlowService.class);
		abnormalFAction.setAbnormalService(mockAbnormalService);
		abnormalFAction.setEquipService(mockEquipService);
		abnormalFAction.setSecurityService(mockSecurityService);
		abnormalFAction.setUtilService(mockUtilService);
		abnormalFAction.setWorkFlowService(mockWorkFlowService);
	}

	@Test
	public void testSave() {
		//准备数据
		AbnormalF abnormalF = new AbnormalF();
		abnormalF.setId(1L);
		abnormalFAction.setModel(abnormalF);
		List<Long> abnoIds = null;
		abnormalFAction.setAbnoIds(abnoIds);
		List<String> abnoNames = null;
		abnormalFAction.setAbnoNames(abnoNames);
		List<Date> abnoStartTimes = null;
		abnormalFAction.setAbnoStartTimes(abnoStartTimes);
		List<Date> abnoEndTimes = null;
		abnormalFAction.setAbnoEndTimes(abnoEndTimes);
		List<Integer> accdFlags = null;
		abnormalFAction.setAccdFlags(accdFlags);
		String equipName = "设备名称";
		abnormalFAction.setEquipName(equipName);
		abnormalFAction.setEditInJb(0L);
		TechSystem ts = new TechSystem();
		ts.setTransType(new TransType());
		Equip equip = new Equip();
		Dept dept = new Dept();
		Person person = new Person();
		person.setName("赵霞");
		person.setDept(dept);
		Date date = new Date();

		//录制脚本
		//差验证emp和参数
		expect(mockEquipService.getByName(equipName)).andReturn(equip);
		mockAbnormalService.assemblyAbos(hasEmpAndDateEquals(abnormalF), same(abnoIds), same(abnoNames),
				same(abnoStartTimes), same(abnoEndTimes), same(accdFlags));
		EasyMock.expect(mockSecurityService.getCurUser()).andReturn(person).times(2);
		EasyMock.expect(mockUtilService.getSysTime()).andReturn(date);
		mockAbnormalService.save(abnormalF);
		EasyMock.expect(
				mockWorkFlowService.startProcessInstance("accidentReport", "1", "设备故障：null, 时间：到", person, "上报故障",
						dept, FunModule.ACCD)).andReturn(new ProcessInstance());
		control.replay();
		//执行测试
		String fow = abnormalFAction.save();
		//
		assertEquals(fow, AbnormalFAction.RIGHT);
	}

	//测试由技办提交时，只保存不操作流程
	@Test
	public void testSaveSuffix() {
		//准备数据
		AbnormalF abnormalF = new AbnormalF();
		abnormalF.setId(1L);
		abnormalFAction.setModel(abnormalF);
		List<Long> abnoIds = null;
		abnormalFAction.setAbnoIds(abnoIds);
		List<String> abnoNames = null;
		abnormalFAction.setAbnoNames(abnoNames);
		List<Date> abnoStartTimes = null;
		abnormalFAction.setAbnoStartTimes(abnoStartTimes);
		List<Date> abnoEndTimes = null;
		abnormalFAction.setAbnoEndTimes(abnoEndTimes);
		List<Integer> accdFlags = null;
		abnormalFAction.setAccdFlags(accdFlags);
		String equipName = "设备名称";
		abnormalFAction.setEquipName(equipName);
		abnormalFAction.setEditInJb(1L);
		TechSystem ts = new TechSystem();
		ts.setTransType(new TransType());
		Equip equip = new Equip();
		Dept dept = new Dept();
		Person person = new Person();
		person.setName("赵霞");
		person.setDept(dept);
		Date date = new Date();

		//录制脚本
		//差验证emp和参数
		expect(mockEquipService.getByName(equipName)).andReturn(equip);
		mockAbnormalService.assemblyAbos(hasEmpAndDateEquals(abnormalF), same(abnoIds), same(abnoNames),
				same(abnoStartTimes), same(abnoEndTimes), same(accdFlags));
		EasyMock.expect(mockSecurityService.getCurUser()).andReturn(person).times(2);
		EasyMock.expect(mockUtilService.getSysTime()).andReturn(date);
		mockAbnormalService.save(abnormalF);
		control.replay();
		//执行测试
		String fow = abnormalFAction.save();
		//验证结果
		assertEquals(fow, AbnormalFAction.MODEL);
		assertEquals(abnormalFAction.getModel(), abnormalF);
	}
}
