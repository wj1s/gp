package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.duty.DutyRuleService;
import gov.abrs.etms.service.security.SecurityService;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DutyRuleActionTest extends BaseActionTest {

	private DutyRuleAction dutyRuleAction;
	private DutyRuleService dutyRuleService;
	private DeptService deptService;
	private SecurityService securityService;

	@Before
	public void setup() {
		dutyRuleAction = new DutyRuleAction();
		dutyRuleService = control.createMock(DutyRuleService.class);
		securityService = control.createMock(SecurityService.class);
		deptService = control.createMock(DeptService.class);
		dutyRuleAction.setDutyRuleService(dutyRuleService);
		dutyRuleAction.setDeptService(deptService);
		dutyRuleAction.setSecurityService(securityService);
	}

	@Test
	public void testGetRuleDetailAjax() {
		Long id = 1L;
		DutyRule dutyRule = new DutyRule();
		List<DutySchedule> list = Lists.newArrayList(new DutySchedule());
		dutyRule.setDutySchedules(list);
		dutyRuleAction.setId(id);

		EasyMock.expect(dutyRuleService.get(EasyMock.notNull(Long.class))).andReturn(dutyRule);
		control.replay();

		String result = dutyRuleAction.getRuleDetailAjax();
		assertEquals(result, dutyRuleAction.NORMAL);
		assertEquals(dutyRule.getDutySchedules(), dutyRuleAction.getList());
	}

	@Test
	public void testArrange() {
		Dept dept = new Dept();
		List<DutyRule> dutyRules = Lists.newArrayList(new DutyRule());
		dept.setDutyRules(dutyRules);

		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		List<Dept> depts = Lists.newArrayList(dept);
		Person person = new Person();
		DeptPer deptPer = new DeptPer();
		deptPer.setDept(dept);
		deptPer.setFunModuleKey(FunModule.DUTY.toString());
		person.setDeptPers(Lists.newArrayList(deptPer));

		EasyMock.expect(securityService.getCurUser()).andReturn(person);

		control.replay();

		String result = dutyRuleAction.arrange();
		assertEquals(result, "arrange");
		assertEquals(depts, dutyRuleAction.getDepts());
		assertEquals(dutyRules, dutyRuleAction.getDutyRules());
	}

	@Test
	public void testGetRuleByDeptAjax() {
		Long dpId = 1L;
		Dept dept = new Dept();
		DutyRule first = new DutyRule();
		first.setId(1L);
		first.setRuleName("一运转");
		List<DutySchedule> dutySchedules = Lists.newArrayList(new DutySchedule());
		first.setDutySchedules(dutySchedules);
		List<DutyRule> dutyRules = Lists.newArrayList(first);
		dept.setDutyRules(dutyRules);

		dutyRuleAction.setDpId(dpId);

		EasyMock.expect(deptService.get(EasyMock.notNull(Long.class))).andReturn(dept);
		control.replay();

		String result = dutyRuleAction.getRuleByDeptAjax();
		assertEquals(result, dutyRuleAction.EASY);
	}

	@Test
	public void testValidateRuleAjax1() {
		Long dpId = 1L;
		int dayPartCount = 1;

		dutyRuleAction.setDpId(dpId);
		dutyRuleAction.setDayPartCount(dayPartCount);

		EasyMock.expect(dutyRuleService.hasDutyRule(dpId, dayPartCount)).andReturn(true);
		control.replay();

		String result = dutyRuleAction.validateRuleAjax();
		assertEquals(result, dutyRuleAction.RIGHT);
	}

	@Test
	public void testValidateRuleAjax2() {
		Long dpId = 1L;
		int dayPartCount = 2;

		dutyRuleAction.setDpId(dpId);
		dutyRuleAction.setDayPartCount(dayPartCount);

		EasyMock.expect(dutyRuleService.hasDutyRule(dpId, dayPartCount)).andReturn(false);
		control.replay();

		String result = dutyRuleAction.validateRuleAjax();
		assertEquals(result, dutyRuleAction.WRONG);
	}

	@Test
	public void testSave() {
		DutyRule model = new DutyRule();
		model.setId(1L);

		dutyRuleAction.setModel(model);

		dutyRuleService.save(model);
		control.replay();

		String result = dutyRuleAction.save();
		assertEquals(result, dutyRuleAction.EASY);
	}

	@Test
	public void testPrepareSave1() throws InstantiationException, IllegalAccessException {
		DutyRule dutyRule = new DutyRule();
		DutySchedule dutySchedule = new DutySchedule();
		List<DutySchedule> list = Lists.newArrayList(dutySchedule);
		dutyRule.setDutySchedules(list);
		dutyRuleAction.setStartTimes(new String[] { "10:10" });
		dutyRuleAction.setEndTimes(new String[] { "15:10" });
		dutyRuleAction.setSchNames(new String[] { "早班" });
		dutyRuleAction.setSchOrders(new int[] { 1 });
		dutyRuleAction.setId(1L);
		dutyRuleAction.setModel(dutyRule);

		EasyMock.expect(dutyRuleService.get(dutyRuleAction.getId())).andReturn(dutyRule);
		control.replay();

		dutyRuleAction.prepareSave();

		DutySchedule result = dutyRuleAction.getModel().getDutySchedules().get(0);
		assertEquals("1970-01-01 10:10:00", DateUtil.getDateTimeStr(result.getStartTime()));
		assertEquals("1970-01-01 15:10:00", DateUtil.getDateTimeStr(result.getEndTime()));
		assertEquals("早班", result.getSchName());
		assertEquals(new Integer(1), result.getSchOrder());
	}

	@Test
	public void testPrepareSave2() throws InstantiationException, IllegalAccessException {
		DutyRule dutyRule = new DutyRule();
		DutySchedule dutySchedule = new DutySchedule();
		List<DutySchedule> list = Lists.newArrayList(dutySchedule);
		dutyRule.setDutySchedules(list);
		dutyRuleAction.setStartTimes(new String[] { "10:10" });
		dutyRuleAction.setEndTimes(new String[] { "15:10" });
		dutyRuleAction.setSchNames(new String[] { "早班" });
		dutyRuleAction.setSchOrders(new int[] { 1 });

		dutyRuleAction.prepareSave();

		DutySchedule result = dutyRuleAction.getModel().getDutySchedules().get(0);
		assertEquals("1970-01-01 10:10:00", DateUtil.getDateTimeStr(result.getStartTime()));
		assertEquals("1970-01-01 15:10:00", DateUtil.getDateTimeStr(result.getEndTime()));
		assertEquals("早班", result.getSchName());
		assertEquals(new Integer(1), result.getSchOrder());
		needVerify = false;
	}
}
