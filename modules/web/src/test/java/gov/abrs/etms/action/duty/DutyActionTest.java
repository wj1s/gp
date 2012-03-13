package gov.abrs.etms.action.duty;

import static gov.abrs.etms.action.duty.AssembllyModelUtil.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.Post;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.duty.DutyCheck;
import gov.abrs.etms.model.duty.DutyPrompt;
import gov.abrs.etms.model.duty.DutyRecord;
import gov.abrs.etms.model.duty.DutyRecordO;
import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.duty.Moniter;
import gov.abrs.etms.model.duty.Watcher;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.GroupService;
import gov.abrs.etms.service.duty.DutyCheckService;
import gov.abrs.etms.service.duty.DutyPromptService;
import gov.abrs.etms.service.duty.DutyRuleService;
import gov.abrs.etms.service.duty.DutyScheduleService;
import gov.abrs.etms.service.duty.DutyService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DutyActionTest extends BaseActionTest {
	private DutyAction dutyAction;
	private DutyService dutyService;
	private DutyRuleService dutyRuleService;
	private DeptService deptService;
	private ExecuteDAO mockexecuteDAO;
	private DutyScheduleService dutyScheduleService;
	private UtilService utilService;
	private DutyPromptService dutyPromptService;
	private SecurityService securityService;
	private DutyCheckService dutyCheckService;
	private GroupService groupService;

	@Before
	public void setup() {
		dutyAction = new DutyAction();
		mockexecuteDAO = control.createMock(ExecuteDAO.class);
		dutyService = control.createMock(DutyService.class);
		dutyRuleService = control.createMock(DutyRuleService.class);
		deptService = control.createMock(DeptService.class);
		dutyScheduleService = control.createMock(DutyScheduleService.class);
		utilService = control.createMock(UtilService.class);
		dutyPromptService = control.createMock(DutyPromptService.class);
		securityService = control.createMock(SecurityService.class);
		dutyCheckService = control.createMock(DutyCheckService.class);
		groupService = control.createMock(GroupService.class);
		dutyAction.setExecuteDAO(mockexecuteDAO);
		dutyAction.setDutyService(dutyService);
		dutyAction.setDutyRuleService(dutyRuleService);
		dutyAction.setDeptService(deptService);
		dutyAction.setDutyScheduleService(dutyScheduleService);
		dutyAction.setUtilService(utilService);
		dutyAction.setDutyPromptService(dutyPromptService);
		dutyAction.setSecurityService(securityService);
		dutyAction.setDutyCheckService(dutyCheckService);
		dutyAction.setGroupService(groupService);
	}

	@Test
	public void testAdd() {
		Long ruleId = 1L;
		dutyAction.setRuleId(ruleId);
		//准备数据
		DutyRule dutyRule = new DutyRule();
		Dept dept = new Dept();
		dept.setGroups(Lists.newArrayList(new Group(1L)));
		dutyRule.setDept(dept);
		//录制脚本
		EasyMock.expect(dutyRuleService.get(ruleId)).andReturn(dutyRule);
		control.replay();
		//执行测试
		String result = dutyAction.add();
		assertEquals(result, "add");
		assertEquals(dutyAction.getDutyRule(), dutyRule);
		assertEquals(dutyAction.getGroups(), dutyRule.getDept().getGroups());
	}

	@Test
	public void testValidateDateAjax1() {
		String startDate = "2010-10-24";
		Long dpId = 1L;
		dutyAction.setStartDate(startDate);
		dutyAction.setDpId(dpId);
		Boolean flag = true;
		EasyMock.expect(
				dutyService.hasDutyShiftedFromThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(flag);
		control.replay();

		String result = dutyAction.validateDateAjax();
		assertEquals(result, dutyAction.RIGHT);
	}

	@Test
	public void testValidateDateAjax2() {
		String startDate = "2010-10-24";
		Long dpId = 1L;
		dutyAction.setStartDate(startDate);
		dutyAction.setDpId(dpId);
		EasyMock.expect(
				dutyService.hasDutyShiftedFromThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(false);
		control.replay();

		String result = dutyAction.validateDateAjax();
		assertEquals(result, dutyAction.WRONG);
	}

	@Test
	public void testValidateGroup1() {
		Long groupId = 1L;
		Group group = new Group();
		group.setPeople(Lists.newArrayList(new Person()));
		dutyAction.setGroupId(groupId);
		EasyMock.expect(groupService.get(EasyMock.notNull(Long.class))).andReturn(group);
		control.replay();

		String result = dutyAction.validateGroup();
		assertEquals(result, dutyAction.RIGHT);
	}

	@Test
	public void testValidateGroup2() {
		Long groupId = 1L;
		Group group = new Group();
		group.setName("一班");
		group.setId(1L);
		dutyAction.setGroupId(groupId);
		EasyMock.expect(groupService.get(EasyMock.notNull(Long.class))).andReturn(group);
		control.replay();

		String result = dutyAction.validateGroup();
		JSONObject gp = new JSONObject();
		gp.put("result", false);
		gp.put("groupId", group.getId());
		gp.put("groupName", group.getName());

		assertEquals(result, dutyAction.EASY);
		assertEquals(gp.toString(), dutyAction.getJson());
	}

	@Test
	public void testAddDutyAjax1() {
		Long[] dutyScheduleIds = { 1L };
		int cycle = 1;
		int[] groupIds = { 1 };
		Long ruleId = 1L;
		String startDate = "2010-10-24";
		String endDate = "2010-10-25";
		Long dpId = 1L;
		int firstDay = 1;

		DutySchedule dutySchedule = new DutySchedule();
		dutySchedule.setSchName("早班");
		dutySchedule.setStartTime(DateUtil.createDateTime("2010-10-24 01:00:00"));
		dutySchedule.setEndTime(DateUtil.createDateTime("2010-10-24 05:00:00"));
		DutyRule dutyRule = new DutyRule();
		Duty duty2 = new Duty();
		Group group = new Group();
		Person person1 = new Person();
		Person person2 = new Person();
		person1.setPost(new Post(1L));
		person1.setName("王建");
		person2.setPost(new Post(2L));
		person2.setName("边迪");
		group.setPeople(Lists.newArrayList(person1, person2));

		dutyAction.setDutyScheduleIds(dutyScheduleIds);
		dutyAction.setCycle(cycle);
		dutyAction.setGroupIds(groupIds);
		dutyAction.setRuleId(ruleId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);
		dutyAction.setDpId(dpId);
		dutyAction.setFirstDay(firstDay);

		EasyMock.expect(dutyScheduleService.get(EasyMock.notNull(Long.class))).andReturn(dutySchedule);
		EasyMock.expect(dutyRuleService.get(ruleId)).andReturn(dutyRule);
		dutyRuleService.save(EasyMock.notNull(DutyRule.class));
		dutyService.arrangeClass(notNull(Dept.class), notNull(Date.class), notNull(Date.class), notNull(List.class),
				eq(groupIds), eq(cycle), eq(firstDay));
		control.replay();
		String result = dutyAction.addDutyAjax();
		assertEquals(result, DutyAction.RIGHT);
	}

	@Test
	public void testAddDutyAjax2() {
		Long[] dutyScheduleIds = { 1L, 2L };
		int cycle = 1;
		int[] groupIds = { 1, 2 };
		Long ruleId = 1L;
		String startDate = "2010-10-24";
		String endDate = "2010-10-24";
		Long dpId = 1L;
		int firstDay = 1;

		DutySchedule dutySchedule1 = new DutySchedule();
		dutySchedule1.setSchName("早班");
		dutySchedule1.setStartTime(DateUtil.createDateTime("1970-01-01 01:00:00"));
		dutySchedule1.setEndTime(DateUtil.createDateTime("1970-01-01 05:00:00"));
		DutySchedule dutySchedule2 = new DutySchedule();
		dutySchedule2.setSchName("晚班");
		dutySchedule2.setStartTime(DateUtil.createDateTime("1970-01-01 22:00:00"));
		dutySchedule2.setEndTime(DateUtil.createDateTime("1970-01-01 05:00:00"));
		DutyRule dutyRule = new DutyRule();
		Duty duty2 = new Duty();
		Group group1 = new Group();
		Person person1 = new Person();
		Person person2 = new Person();
		person1.setPost(new Post(1L));
		person1.setName("王建");
		person2.setPost(new Post(2L));
		person2.setName("边迪");
		group1.setPeople(Lists.newArrayList(person1, person2));

		Group group2 = new Group();
		Person person3 = new Person();
		Person person4 = new Person();
		person1.setPost(new Post(1L));
		person1.setName("王建");
		person2.setPost(new Post(2L));
		person2.setName("边迪");
		group2.setPeople(Lists.newArrayList(person1, person2));

		dutyAction.setDutyScheduleIds(dutyScheduleIds);
		dutyAction.setCycle(cycle);
		dutyAction.setGroupIds(groupIds);
		dutyAction.setRuleId(ruleId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);
		dutyAction.setDpId(dpId);
		dutyAction.setFirstDay(firstDay);

		EasyMock.expect(dutyScheduleService.get(1L)).andReturn(dutySchedule1);
		EasyMock.expect(dutyScheduleService.get(2L)).andReturn(dutySchedule2);
		EasyMock.expect(dutyRuleService.get(ruleId)).andReturn(dutyRule);
		dutyRuleService.save(EasyMock.notNull(DutyRule.class));
		dutyService.arrangeClass(notNull(Dept.class), notNull(Date.class), notNull(Date.class), notNull(List.class),
				eq(groupIds), eq(cycle), eq(firstDay));
		control.replay();
		String result = dutyAction.addDutyAjax();
		assertEquals(result, DutyAction.RIGHT);
	}

	@Test
	public void testShow() {
		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		List<Dept> depts = Lists.newArrayList(dept);
		Date date = new Date();
		Person person = new Person();
		DeptPer deptPer = new DeptPer();
		deptPer.setDept(dept);
		deptPer.setFunModuleKey(FunModule.DUTY.toString());
		person.setDeptPers(Lists.newArrayList(deptPer));
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();
		String result = dutyAction.show();
		assertEquals(result, dutyAction.SUCCESS);
		for (Dept dept1 : dutyAction.getDepts()) {
			if (dept1.getId() == 1L) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}
		assertEquals(groups, dutyAction.getGroups());
		assertSame(1L, dutyAction.getDpId());
		assertEquals(DateUtil.getDateStr(date), dutyAction.getCurrentDate());
	}

	@Test
	public void testLoadArrange() {
		Long dpId = 1L;
		Long groupId = 1L;
		String startDate = "2010-10-24";
		String endDate = "2010-10-25";
		dutyAction.setDpId(dpId);
		dutyAction.setGroupId(groupId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);
		dutyAction.setSidx("startTime");
		dutyService.get(EasyMock.notNull(Carrier.class), EasyMock.notNull(Dept.class), EasyMock.notNull(Group.class),
				EasyMock.notNull(Date.class), EasyMock.notNull(Date.class));
		control.replay();

		String result = dutyAction.loadArrange();
		assertEquals(result, dutyAction.GRID);
	}

	@Test
	public void testShift1() {
		Date today = new Date();
		Person person = new Person();
		person.setDept(new Dept(1L));
		Dept dept = person.getDept();
		List<Duty> todayDutyList = Lists.newArrayList(new Duty());
		List<Duty> alreadyShiftDuties = Lists.newArrayList(new Duty());
		Duty crossAndLastDuty = new Duty();
		crossAndLastDuty.setStartTime(DateUtil.createDateTime("2010-10-24 23:00:00"));
		crossAndLastDuty.setEndTime(DateUtil.createDateTime("2010-10-25 2:00:00"));

		EasyMock.expect(utilService.getSysTime()).andReturn(today);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(dutyService.getByThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(todayDutyList);
		EasyMock.expect(dutyService.hasDutyThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(false);
		EasyMock.expect(
				dutyService.getAlreadyShiftedDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class),
						EasyMock.notNull(Person.class))).andReturn(alreadyShiftDuties);
		EasyMock.expect(
				dutyService.isAllDayDutiesShifted(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(false);
		EasyMock.expect(
				dutyService.getCanShiftDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(Lists.newArrayList(crossAndLastDuty));
		control.replay();

		String result = dutyAction.shift();
		assertEquals(result, "shift");
		assertEquals(DateUtil.dateToDateByFormat(today, DateUtil.FORMAT_DAY), dutyAction.getToday());
		assertEquals(dept, dutyAction.getDept());
		assertEquals(todayDutyList, dutyAction.getTodayDutyList());
		assertEquals("本日没有任何排班,请联系机房主任,制定排班表!", dutyAction.getMessage());
		assertEquals(alreadyShiftDuties, dutyAction.getAlreadyShiftDuties());
		assertEquals(DateUtil.getPreviousDate(DateUtil.dateToDateByFormat(today, DateUtil.FORMAT_DAY)), dutyAction
				.getYesterday());
		assertEquals(crossAndLastDuty, dutyAction.getCrossAndLastDuty());
	}

	@Test
	public void testShift2() {
		Date today = new Date();
		Person person = new Person();
		person.setDept(new Dept(1L));
		Dept dept = person.getDept();
		List<Duty> todayDutyList = Lists.newArrayList(new Duty());
		List<Duty> alreadyShiftDuties = Lists.newArrayList(new Duty());
		Duty crossAndLastDuty = new Duty();
		crossAndLastDuty.setStartTime(DateUtil.createDateTime("2010-10-24 23:00:00"));
		crossAndLastDuty.setEndTime(DateUtil.createDateTime("2010-10-25 2:00:00"));

		EasyMock.expect(utilService.getSysTime()).andReturn(today);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(dutyService.getByThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(todayDutyList);
		EasyMock.expect(dutyService.hasDutyThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(true);
		EasyMock.expect(
				dutyService.isAllDayDutiesShifted(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(true);
		EasyMock.expect(
				dutyService.getAlreadyShiftedDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class),
						EasyMock.notNull(Person.class))).andReturn(alreadyShiftDuties);
		EasyMock.expect(
				dutyService.isAllDayDutiesShifted(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(false);
		EasyMock.expect(
				dutyService.getCanShiftDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(Lists.newArrayList(crossAndLastDuty));
		control.replay();

		String result = dutyAction.shift();
		assertEquals(result, "shift");
		assertEquals(DateUtil.dateToDateByFormat(today, DateUtil.FORMAT_DAY), dutyAction.getToday());
		assertEquals(dept, dutyAction.getDept());
		assertEquals(todayDutyList, dutyAction.getTodayDutyList());
		assertEquals("本日我所在的所有班次都已完成交接,无法再进行交接班操作!", dutyAction.getMessage());
		assertEquals(alreadyShiftDuties, dutyAction.getAlreadyShiftDuties());
		assertEquals(DateUtil.getPreviousDate(DateUtil.dateToDateByFormat(today, DateUtil.FORMAT_DAY)), dutyAction
				.getYesterday());
		assertEquals(crossAndLastDuty, dutyAction.getCrossAndLastDuty());
	}

	@Test
	public void testShift3() {
		Date today = new Date();
		Person person = new Person();
		person.setDept(new Dept(1L));
		Dept dept = person.getDept();
		List<Duty> todayDutyList = Lists.newArrayList(new Duty());
		List<Duty> alreadyShiftDuties = Lists.newArrayList(new Duty());
		Duty crossAndLastDuty = new Duty();
		crossAndLastDuty.setStartTime(DateUtil.createDateTime("2010-10-24 23:00:00"));
		crossAndLastDuty.setEndTime(DateUtil.createDateTime("2010-10-25 2:00:00"));
		List<Duty> canShiftDuties = Lists.newArrayList(new Duty());

		EasyMock.expect(utilService.getSysTime()).andReturn(today);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(dutyService.getByThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(todayDutyList);
		EasyMock.expect(dutyService.hasDutyThisDay(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(true);
		EasyMock.expect(
				dutyService.isAllDayDutiesShifted(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(false);
		EasyMock.expect(
				dutyService.getAlreadyShiftedDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class),
						EasyMock.notNull(Person.class))).andReturn(alreadyShiftDuties);
		EasyMock.expect(
				dutyService.isAllDayDutiesShifted(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(false);

		EasyMock.expect(
				dutyService.getCanShiftDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(canShiftDuties);
		EasyMock.expect(
				dutyService.getCanShiftDuties(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock
						.notNull(Person.class))).andReturn(Lists.newArrayList(crossAndLastDuty));
		control.replay();

		String result = dutyAction.shift();
		assertEquals(result, "shift");
		assertEquals(DateUtil.dateToDateByFormat(today, DateUtil.FORMAT_DAY), dutyAction.getToday());
		assertEquals(dept, dutyAction.getDept());
		assertEquals(todayDutyList, dutyAction.getTodayDutyList());
		assertEquals("本日还有未交接的班次，请点击交接班完成交接!", dutyAction.getMessage());
		assertEquals(canShiftDuties, dutyAction.getCanShiftDuties());
		assertEquals(alreadyShiftDuties, dutyAction.getAlreadyShiftDuties());
		assertEquals(DateUtil.getPreviousDate(DateUtil.dateToDateByFormat(today, DateUtil.FORMAT_DAY)), dutyAction
				.getYesterday());
		assertEquals(crossAndLastDuty, dutyAction.getCrossAndLastDuty());
	}

	@Test
	public void testPrepareToWork() {
		Long id = 1L;
		Duty model = new Duty();
		Date date = new Date();
		Person person = new Person();
		person.setName("春哥");

		dutyAction.setId(id);

		EasyMock.expect(this.dutyService.get(id)).andReturn(model);
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();

		dutyAction.prepareToWork();

		assertEquals(date, dutyAction.getModel().getUpdDate());
		assertEquals("春哥", dutyAction.getModel().getEmpName());
	}

	@Test
	public void testToWork1() {
		Duty duty = new Duty();
		duty.setStartTime(DateUtil.createDateTime("2010-11-12 2:00:00"));
		duty.setEndTime(DateUtil.createDateTime("2010-11-12 5:00:00"));
		duty.setSchName("早班");
		duty.setWeather("晴");
		duty.setTemperature("12");
		duty.setWeek("星期二");
		Group group = new Group();
		group.setName("一班");
		group.setDept(new Dept());
		Moniter moniter = new Moniter();
		moniter.setName("赵霞");
		Watcher watcher = new Watcher();
		watcher.setName("吕斌");
		duty.setMoniter(moniter);
		duty.setWatchers(Lists.newArrayList(watcher));
		duty.setGroup(group);
		duty.setConfirm(null);
		DutyRecord dutyRecordO = new DutyRecordO();
		dutyRecordO.setContent("123");
		dutyRecordO.setId(1L);
		duty.setDutyRecords(Lists.newArrayList(dutyRecordO));
		DutyPrompt dutyPrompt = new DutyPrompt();
		dutyPrompt.setEmpName("赵霞");
		dutyPrompt.setContent("234");
		duty.setDutyPrompts(Lists.newArrayList(dutyPrompt));
		duty.setUpdDate(new Date());
		Date date = new Date();

		dutyAction.setModel(duty);

		dutyService.save(setModelId(duty));
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(dutyPromptService.getDutyPromptNow(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(Lists.newArrayList(dutyPrompt));
		control.replay();

		String result = dutyAction.toWork();
		assertEquals(result, dutyAction.EASY);
	}

	@Test
	public void testToWork2() {
		Duty duty = new Duty();
		duty.setConfirm(false);
		duty.setStartTime(DateUtil.createDateTime("2010-11-12 2:00:00"));
		duty.setEndTime(DateUtil.createDateTime("2010-11-12 5:00:00"));
		duty.setSchName("早班");
		duty.setWeather("晴");
		duty.setTemperature("12");
		duty.setWeek("星期二");
		Group group = new Group();
		group.setName("一班");
		group.setDept(new Dept());
		Moniter moniter = new Moniter();
		moniter.setName("赵霞");
		Watcher watcher = new Watcher();
		watcher.setName("吕斌");
		duty.setMoniter(moniter);
		duty.setWatchers(Lists.newArrayList(watcher));
		duty.setGroup(group);
		duty.setConfirm(false);
		DutyRecord dutyRecordO = new DutyRecordO();
		dutyRecordO.setContent("123");
		dutyRecordO.setId(1L);
		duty.setDutyRecords(Lists.newArrayList(dutyRecordO));
		DutyPrompt dutyPrompt = new DutyPrompt();
		dutyPrompt.setEmpName("赵霞");
		dutyPrompt.setContent("234");
		duty.setDutyPrompts(Lists.newArrayList(dutyPrompt));
		duty.setUpdDate(new Date());
		Date date = new Date();

		Duty preDuty = new Duty();
		preDuty.setOffDutyRecord("23232");
		Group group1 = new Group();
		group1.setName("二班");
		Moniter moniter1 = new Moniter();
		moniter1.setName("赵霞1");
		Watcher watcher1 = new Watcher();
		watcher1.setName("吕斌1");
		preDuty.setMoniter(moniter1);
		preDuty.setWatchers(Lists.newArrayList(watcher1));
		preDuty.setGroup(group1);
		preDuty.setOffDutyRecord("2323");
		duty.setPreDuty(preDuty);

		dutyAction.setModel(duty);

		dutyService.save(setModelId(duty));
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(dutyPromptService.getDutyPromptNow(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(Lists.newArrayList(dutyPrompt));
		control.replay();

		String result = dutyAction.toWork();
		assertEquals(result, dutyAction.EASY);
	}

	@Test
	public void testToWork3() {
		Duty duty = new Duty();
		duty.setId(1L);
		duty.setStartTime(DateUtil.createDateTime("2010-11-12 2:00:00"));
		duty.setEndTime(DateUtil.createDateTime("2010-11-12 5:00:00"));
		duty.setSchName("早班");
		duty.setWeather("晴");
		duty.setTemperature("12");
		duty.setWeek("星期二");
		Group group = new Group();
		group.setName("一班");
		group.setDept(new Dept());
		Moniter moniter = new Moniter();
		moniter.setName("赵霞");
		Watcher watcher = new Watcher();
		watcher.setName("吕斌");
		duty.setMoniter(moniter);
		duty.setWatchers(Lists.newArrayList(watcher));
		duty.setGroup(group);
		duty.setConfirm(false);
		DutyRecord dutyRecordO = new DutyRecordO();
		dutyRecordO.setContent("123");
		dutyRecordO.setId(1L);
		duty.setDutyRecords(Lists.newArrayList(dutyRecordO));
		DutyPrompt dutyPrompt = new DutyPrompt();
		dutyPrompt.setEmpName("赵霞");
		dutyPrompt.setContent("234");
		duty.setDutyPrompts(Lists.newArrayList(dutyPrompt));
		duty.setUpdDate(new Date());
		duty.setOffDutyRecord("2323");

		Duty preDuty = new Duty();
		Group group1 = new Group();
		group1.setName("二班");
		Moniter moniter1 = new Moniter();
		moniter1.setName("赵霞1");
		Watcher watcher1 = new Watcher();
		watcher1.setName("吕斌1");
		preDuty.setMoniter(moniter1);
		preDuty.setWatchers(Lists.newArrayList(watcher1));
		preDuty.setGroup(group1);
		duty.setPreDuty(preDuty);
		Date date = new Date();

		dutyAction.setModel(duty);

		dutyService.save(EasyMock.notNull(Duty.class));
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(dutyPromptService.getDutyPromptNow(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class)))
				.andReturn(Lists.newArrayList(dutyPrompt));
		control.replay();

		String result = dutyAction.toWork();
		assertEquals(result, dutyAction.EASY);
	}

	@Test
	public void testToDelete() {
		Long dpId = 1L;
		dutyAction.setDpId(dpId);
		Dept dept = new Dept(1L);
		EasyMock.expect(deptService.get(dpId)).andReturn(dept);
		control.replay();
		String result = dutyAction.toDelete();
		assertEquals(result, "delete");
	}

	@Test
	public void testDelete() {
		Long dpId = 1L;
		String startDate = "2010-11-20";
		String endDate = "2010-11-28";

		dutyAction.setDpId(dpId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);

		dutyService.delete(EasyMock.notNull(Dept.class), EasyMock.notNull(Date.class), EasyMock.notNull(Date.class));
		control.replay();

		String result = dutyAction.delete();
		assertEquals(result, dutyAction.RIGHT);
	}

	@Test
	public void testOffDuty() {
		Duty model = new Duty();
		model.setId(1L);
		dutyAction.setId(1L);
		dutyAction.setOffDutyRecord("123");
		EasyMock.expect(dutyService.get(1L)).andReturn(model);
		dutyService.save(model);
		control.replay();

		dutyAction.offDuty();
		assertSame(dutyAction.getModel().getId(), 1L);
		assertSame(dutyAction.getModel().getOffDutyRecord(), "123");
	}

	@Test
	public void testView() {
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.toString());
		Long dpId = 1L;
		Dept dept = new Dept(dpId);
		Group group = new Group();
		List<Group> groups = Lists.newArrayList(group);
		dept.setGroups(groups);
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		List<Dept> depts = person.getDeptsFun(FunModule.DUTY);
		Date date = new Date();
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(this.utilService.getSysTime()).andReturn(date);
		control.replay();

		String result = dutyAction.view();
		assertEquals("view", result);
		assertEquals(depts, dutyAction.getDepts());
		assertEquals(DateUtil.getDateStr(date), dutyAction.getCurrentDate());
		assertEquals(groups, dutyAction.getGroups());
		assertEquals(dpId, dutyAction.getDpId());
	}

	@Test
	public void testLoadDuty() throws UnsupportedEncodingException {
		Long dpId = 1L;
		Dept dept = new Dept(dpId);
		Long groupId = 1L;
		Group group = new Group(groupId);
		String startDate = "2010-11-11";
		String endDate = "2010-11-13";
		Integer hasChecks = -1;
		Carrier<Duty> carrier = new Carrier<Duty>();
		String empName = "赵霞";
		dutyAction.setDpId(dpId);
		dutyAction.setGroupId(groupId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);
		dutyAction.setHasCheckes(hasChecks);
		dutyAction.setCarrier(carrier);
		dutyAction.setEmpName(empName);

		this.dutyService.get(carrier, dept, group, dutyAction.encodeContent("赵霞"), null,
				DateUtil.createDate(startDate), DateUtil.createDate(endDate));
		control.replay();

		String result = dutyAction.loadDuty();
		assertEquals(dutyAction.GRID, result);
	}

	@Test
	public void testScanDuty() {
		Long id = 1L;
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.getKey());
		Duty duty = new Duty();
		Dept dept = new Dept();
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		List<Dept> depts = person.getDeptsFun(FunModule.DUTY);
		dutyAction.setId(id);

		EasyMock.expect(this.dutyService.get(id)).andReturn(duty);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();

		String result = dutyAction.scanDuty();
		assertEquals("scan", result);
		assertEquals(depts, dutyAction.getDepts());
		assertEquals(duty, dutyAction.getDutyScan());
	}

	@Test
	public void testAddCheckAjax() {
		String check = "123";
		Long id = 1L;
		Person person = new Person(1L);
		person.setName("赵霞");
		Date date = DateUtil.createDateTime("2011-01-15 23:25:00");
		Duty duty = new Duty();
		DutyCheck dutyCheck = new DutyCheck(duty, check, "赵霞", date);
		dutyAction.setCheck(check);
		dutyAction.setId(id);

		EasyMock.expect(this.dutyService.get(id)).andReturn(duty);
		EasyMock.expect(this.utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		this.dutyService.addADutyCheck(duty, dutyCheck);
		control.replay();

		String result = dutyAction.addCheckAjax();
		assertEquals(dutyAction.EASY, result);
		assertEquals("{\"result\":true,\"empName\":\"赵霞\",\"startTime\":\"01月15日 23:25\",\"content\":\"123\"}",
				dutyAction.getJson());
	}

	@Test
	public void testDelCheckAjax() {
		Long checkId = 1L;
		dutyAction.setCheckId(checkId);

		dutyCheckService.delete(checkId);
		control.replay();

		String result = dutyAction.delCheckAjax();
		assertEquals(dutyAction.EASY, result);
		assertEquals("{\"result\":true,\"id\":1}", dutyAction.getJson());
	}

	@Test
	public void testQuickChange() {
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.getKey());
		Dept dept = new Dept();
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		List<Dept> depts = person.getDeptsFun(FunModule.DUTY);

		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();

		String result = dutyAction.quickChange();
		assertEquals("quickchange", result);
		assertEquals(depts, dutyAction.getDepts());
	}

	@Test
	public void testQuickChangeDetail1() {
		Long dpId = 1L;
		String startDate = "2010-11-11";
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.addDay(start, 6);
		Dept dept = new Dept();
		Duty duty = new Duty();
		duty.setDayPartCount(1);
		List<Duty> list = Lists.newArrayList(duty);
		dutyAction.setDpId(dpId);
		dutyAction.setStartDate(startDate);

		EasyMock.expect(deptService.get(dpId)).andReturn(dept);
		EasyMock.expect(dutyService.getDuties(dept, start, end)).andReturn(list);
		EasyMock.expect(dutyService.get(dept, start, end, 1)).andReturn(list);
		control.replay();

		String result = dutyAction.quickChangeDetail();
		assertEquals(dutyAction.NORMAL, result);
		assertEquals(list, dutyAction.getList());
	}

	@Test
	public void testQuickChangeDetail2() {
		Long dpId = 1L;
		String startDate = "2010-11-11";
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.addDay(start, 6);
		Dept dept = new Dept();
		List<Duty> list = Lists.newArrayList();
		dutyAction.setDpId(dpId);
		dutyAction.setStartDate(startDate);

		EasyMock.expect(deptService.get(dpId)).andReturn(dept);
		EasyMock.expect(dutyService.getDuties(dept, start, end)).andReturn(list);
		control.replay();

		String result = dutyAction.quickChangeDetail();
		assertEquals(dutyAction.WRONG, result);
	}

	@Test
	public void testSaveQuickChangeDetail() {
		String changeDetails = "[{id:\"160\",watchers:[],moniter:\"解林娜\"},{id:\"164\",watchers:[{watcher:\"计岑\"}],moniter:\"郁涛\"}]";
		JSONArray array = JSONArray.fromObject(changeDetails);
		dutyAction.setChangeDetails(changeDetails);

		dutyService.savechangeDetails(array);
		control.replay();
		String result = dutyAction.saveQuickChangeDetail();
		assertEquals(dutyAction.RIGHT, result);
	}

	@Test
	public void testToReplace() {
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.toString());
		Dept dept = new Dept(1L);
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		List<Dept> depts = person.getDeptsFun(FunModule.DUTY);

		DutyRule dutyRule = new DutyRule();
		DutySchedule dutySchedule = new DutySchedule();
		List<DutySchedule> list1 = Lists.newArrayList(dutySchedule);
		dutyRule.setDutySchedules(list1);

		List<DutyRule> list2 = Lists.newArrayList(dutyRule);
		dept.setDutyRules(list2);

		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();

		String result = dutyAction.toReplace();
		assertEquals("replace", result);
		assertEquals(depts, dutyAction.getDepts());
		assertEquals(dutySchedule, dutyAction.getDutySchedules().get(1L).get(0));
	}

	@Test
	public void testGetEffectedGroupNumAjax() {
		String startDate = "2011-1-1";
		String endDate = "2011-1-2";
		DutySchedule dutySchedule = new DutySchedule();
		String empName = "郁涛";

		Long scheduleId = 1L;
		int num = 1;

		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);
		dutyAction.setScheduleId(scheduleId);
		dutyAction.setEmpName(empName);

		EasyMock.expect(dutyScheduleService.get(1L)).andReturn(dutySchedule);
		EasyMock.expect(
				dutyService.getEffectedGroupNum(empName, dutySchedule, DateUtil.createDate(startDate), DateUtil
						.createDate(endDate))).andReturn(num);
		control.replay();

		String result = dutyAction.getEffectedGroupNumAjax();
		assertEquals(dutyAction.EASY, result);
		assertEquals("{\"result\":true,\"num\":1}", dutyAction.getJson());
	}

	@Test
	public void testToPreviewReplaceDuties() throws UnsupportedEncodingException {
		Long dpId = 1L;
		Dept dept = new Dept(dpId);
		String startDateArray = "2011-1-1";
		String endDateArray = "2011-1-1";
		String dutyScheduleNameArray = "早班";
		String empNameArray = "赵霞";
		String flagsArray = "0";
		String empRpNameArray = "吕斌";
		String dutyScheduleRpNameArray = "晚班";
		String startDate = "2011-1-1";
		String endDate = "2011-1-1";

		dutyAction.setStartDateArray(startDateArray);
		dutyAction.setDpId(dpId);
		dutyAction.setEndDateArray(endDateArray);
		dutyAction.setDutyScheduleNameArray(dutyScheduleNameArray);
		dutyAction.setEmpNameArray(empNameArray);
		dutyAction.setEmpRpNameArray(empRpNameArray);
		dutyAction.setFlagsArray(flagsArray);
		dutyAction.setDutyScheduleRpNameArray(dutyScheduleRpNameArray);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);

		List<Duty> oldList = Lists.newArrayList();
		List<Duty> newList = Lists.newArrayList();
		EasyMock.expect(dutyService.getDuties(dept, DateUtil.createDate("2011-1-1"), DateUtil.createDate("2011-1-1")))
				.andReturn(oldList);
		//因为方法内部要new一个对象，我才这么做的，不是假测试
		EasyMock.expect(dutyService.getCompareList(EasyMock.notNull(ArrayList.class), EasyMock.notNull(HashMap.class)))
				.andReturn(newList);
		control.replay();

		String result = dutyAction.toPreviewReplaceDuties();
		assertEquals("snapshot", result);

		assertEquals(oldList, dutyAction.getOldList());
		assertEquals(newList, dutyAction.getNewList());
	}

	@Test
	public void testUpdateReplaceResult() {
		String updateRp = "{list:[{startDate:\"2010-11-11\"}]}";
		JSONObject obj = JSONObject.fromObject(updateRp);

		dutyAction.setUpdateRp(updateRp);
		dutyService.updateReplaceResult(obj);
		control.replay();

		String result = dutyAction.updateReplaceResult();
		assertEquals(dutyAction.RIGHT, result);
	}

	@Test
	public void testToDeleteStaff() {
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.getKey());
		Dept dept = new Dept();
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();

		String result = dutyAction.toDeleteStaff();
		assertEquals("staffdel", result);
		assertEquals(dept, dutyAction.getDepts().get(0));
	}

	@Test
	public void testSelectDelStaffAjax() {
		String empName = "赵霞";
		Long dpId = 1L;
		String startDate = "2010-11-24";
		String endDate = "2010-11-24";

		Duty duty = new Duty();
		duty.setMoniter(new Moniter("赵霞"));
		List<Duty> list = Lists.newArrayList(duty);

		dutyAction.setEmpName(empName);
		dutyAction.setDpId(dpId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);

		EasyMock.expect(
				dutyService.getDuties(notNull(Dept.class), notNull(Date.class), notNull(Date.class),
						notNull(String.class))).andReturn(list);
		control.replay();

		String result = dutyAction.selectDelStaffAjax();
		assertEquals(dutyAction.NORMAL, result);
		assertEquals("<font color='#9901d8'>赵霞</font>", ((Duty) dutyAction.getList().get(0)).getMoniter().getName());
	}

	@Test
	public void testDeleteStaffAjax() {
		List<Long> duIds = Lists.newArrayList(1L);
		String empName = "赵霞";
		dutyAction.setDuIds(duIds);
		dutyAction.setEmpName(empName);
		dutyService.deleteStaffOnDuty(duIds, empName);
		control.replay();

		String result = dutyAction.deleteStaffAjax();
		assertEquals(dutyAction.RIGHT, result);
	}

	@Test
	public void testToAddStaff() {
		Person person = new Person(1L);
		DeptPer deptPer = new DeptPer();
		deptPer.setFunModuleKey(FunModule.DUTY.getKey());
		Dept dept = new Dept();
		Group group = new Group();
		dept.setGroups(Lists.newArrayList(group));
		deptPer.setDept(dept);
		person.setDeptPers(Lists.newArrayList(deptPer));
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();

		String result = dutyAction.toAddStaff();
		assertEquals("staffadd", result);
		assertEquals(dept, dutyAction.getDepts().get(0));
		assertEquals(group, dutyAction.getGroups().get(0));
	}

	@Test
	public void testSelectAddStaffAjax() {
		String empName = "赵霞";
		Long dpId = 1L;
		String startDate = "2010-11-24";
		String endDate = "2010-11-24";
		Long groupId = 1L;

		Duty duty = new Duty();
		duty.setMoniter(new Moniter("赵霞"));
		List<Duty> list = Lists.newArrayList(duty);
		Group group = new Group();

		dutyAction.setEmpName(empName);
		dutyAction.setDpId(dpId);
		dutyAction.setStartDate(startDate);
		dutyAction.setEndDate(endDate);
		dutyAction.setGroupId(groupId);

		EasyMock.expect(
				dutyService.getDuties(notNull(Dept.class), notNull(Date.class), notNull(Date.class),
						notNull(Group.class))).andReturn(list);
		EasyMock.expect(groupService.get(groupId)).andReturn(group);
		control.replay();

		String result = dutyAction.selectAddStaffAjax();
		assertEquals(dutyAction.NORMAL, result);
		assertEquals("<font color='#9901d8'>赵霞</font>", ((Duty) dutyAction.getList().get(0)).getMoniter().getName());
	}

	@Test
	public void testAddStaffAjax() {
		List<Long> duIds = Lists.newArrayList(1L);
		String empName = "赵霞";
		dutyAction.setDuIds(duIds);
		dutyAction.setEmpName(empName);
		dutyService.addStaffOnDuty(duIds, empName);
		control.replay();

		String result = dutyAction.addStaffAjax();
		assertEquals(dutyAction.RIGHT, result);
	}
}
