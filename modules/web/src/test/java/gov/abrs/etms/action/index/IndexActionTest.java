package gov.abrs.etms.action.index;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.duty.DutyService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class IndexActionTest extends BaseActionTest {

	private IndexAction indexAction;
	private SecurityService securityServiceInBase;
	private UtilService utilService;
	private DutyService dutyService;
	private WorkFlowService workFlowService;

	@Before
	public void setup() {
		indexAction = new IndexAction();
		securityServiceInBase = control.createMock(SecurityService.class);
		utilService = control.createMock(UtilService.class);
		dutyService = control.createMock(DutyService.class);
		workFlowService = control.createMock(WorkFlowService.class);
		indexAction.setSecurityService(securityServiceInBase);
		indexAction.setUtilService(utilService);
		indexAction.setDutyService(dutyService);
		indexAction.setWorkFlowService(workFlowService);
	}

	@Test
	public void testToIndex() {
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.getKey());
		Dept dept = new Dept();
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		Date date = new Date();
		Duty dutyScan = new Duty();
		ProcessInstance pi = new ProcessInstance();
		List<ProcessInstance> piList = Lists.newArrayList(pi);

		EasyMock.expect(securityServiceInBase.getCurUser()).andReturn(person);
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(dutyService.get(dept, date)).andReturn(dutyScan);
		EasyMock.expect(workFlowService.getProcessInstances(0, person)).andReturn(piList);
		control.replay();

		String result = indexAction.toIndex();
		assertEquals(dutyScan, indexAction.getDutyScan());
		assertEquals(dept, indexAction.getDepts().get(0));
		assertEquals(result, indexAction.SUCCESS);
		assertEquals(1, indexAction.getNewList().size());
	}
}
