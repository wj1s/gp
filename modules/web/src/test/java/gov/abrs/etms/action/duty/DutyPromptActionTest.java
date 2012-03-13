package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.DutyPrompt;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.duty.DutyPromptService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DutyPromptActionTest extends BaseActionTest {

	private DutyPromptAction dutyPromptAction;
	private DeptService deptService;
	private ExecuteDAO mockexecuteDAO;
	private DutyPromptService dutyPromptService;
	private UtilService utilService;
	private SecurityService securityService;

	@Before
	public void setup() {
		dutyPromptAction = new DutyPromptAction();
		mockexecuteDAO = control.createMock(ExecuteDAO.class);
		deptService = control.createMock(DeptService.class);
		utilService = control.createMock(UtilService.class);
		securityService = control.createMock(SecurityService.class);
		dutyPromptService = control.createMock(DutyPromptService.class);
		dutyPromptAction.setDutyPromptService(dutyPromptService);
		dutyPromptAction.setDeptService(deptService);
		dutyPromptAction.setUtilService(utilService);
		dutyPromptAction.setSecurityService(securityService);
	}

	@Test
	public void testToAdd() {
		List<Dept> depts = Lists.newArrayList(new Dept(1L));
		EasyMock.expect(deptService.getAll()).andReturn(depts);
		control.replay();

		String result = dutyPromptAction.toAdd();
		assertEquals(result, "input");
		assertSame(dutyPromptAction.getDeptList().get(0).getId(), 1L);
	}

	@Test
	public void testSave() {
		Date date = new Date();
		DutyPrompt model = new DutyPrompt();
		model.setId(1L);
		model.setEmpName("赵霞");
		model.setContent("123");
		dutyPromptAction.setModel(model);
		JSONObject dutyPrompt = new JSONObject();
		dutyPrompt.put("result", true);
		dutyPrompt.put("id", model.getId());
		String content = model.getEmpName() + "提醒：" + model.getContent();
		dutyPrompt.put("content", content);
		String json = dutyPrompt.toString();

		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		dutyPromptService.save(model);
		control.replay();

		String result = dutyPromptAction.save();
		assertEquals(result, dutyPromptAction.EASY);
		assertEquals(json, dutyPromptAction.getJson());
	}

	@Test
	public void testToEdit() {
		DutyPrompt dutyPrompt = new DutyPrompt(1L);
		dutyPromptAction.setId(1L);
		EasyMock.expect(dutyPromptService.get(1L)).andReturn(dutyPrompt);
		control.replay();

		String result = dutyPromptAction.toEdit();
		assertEquals(result, "input");
		assertEquals(dutyPrompt, dutyPromptAction.getDutyPrompt());
	}

	@Test
	public void testDelete() {
		Date date = new Date();
		dutyPromptAction.setId(1L);
		JSONObject a = new JSONObject();
		a.put("result", true);
		a.put("id", 1L);
		String json = a.toString();
		this.dutyPromptService.delete(1L);
		control.replay();

		String result = dutyPromptAction.delete();
		assertEquals(result, dutyPromptAction.EASY);
		assertEquals(json, dutyPromptAction.getJson());
	}

	@Test
	public void testViewTodayPrompt() {
		Date date = new Date();
		Person person = new Person();
		Dept dept = new Dept(1L);
		person.setDept(dept);
		List<DutyPrompt> dutyPrompts = Lists.newArrayList(new DutyPrompt());
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(dutyPromptService.getDutyPromptNow(dept, date)).andReturn(dutyPrompts);
		control.replay();

		String result = dutyPromptAction.viewTodayPrompt();
		assertEquals(result, "show");
		assertEquals(dutyPrompts, dutyPromptAction.getDutyPrompts());
	}

	@Test
	public void testCheckPromptAjax1() {
		Date date = new Date();
		Person person = new Person();
		Dept dept = new Dept(1L);
		person.setDept(dept);
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(dutyPromptService.hasDutyPromptNow(dept, date)).andReturn(true);
		control.replay();

		String result = dutyPromptAction.checkPromptAjax();
		assertEquals(result, dutyPromptAction.RIGHT);
	}

	@Test
	public void testCheckPromptAjax2() {
		Date date = new Date();
		Person person = new Person();
		Dept dept = new Dept(1L);
		person.setDept(dept);
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(dutyPromptService.hasDutyPromptNow(dept, date)).andReturn(false);
		control.replay();

		String result = dutyPromptAction.checkPromptAjax();
		assertEquals(result, dutyPromptAction.WRONG);
	}
}
