/**
 * 
 */
package gov.abrs.etms.action.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.repair.CycleService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author 赵振喜
 *
 */
public class CycleActionTest extends BaseActionTest {
	private CycleAction cycleAction;
	private CycleService cycleService;
	private SecurityService securityService;
	private UtilService utilService;

	@Before
	public void setup() {
		cycleAction = new CycleAction();
		cycleService = control.createMock(CycleService.class);
		utilService = control.createMock(UtilService.class);
		securityService = control.createMock(SecurityService.class);
		cycleAction.setCycleService(cycleService);
		cycleAction.setSecurityService(securityService);
		cycleAction.setUtilService(utilService);
	}

	@Test
	public void testShow() {
		Map formMap = new HashMap();//定义form
		cycleAction.setFormMap(formMap);//设置form
		Dept dept = new Dept();//新建dept
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));//添加2个班组
		dept.setId(Long.parseLong("1"));//设置部门
		dept.setGroups(groups);//设置班组
		Person person = new Person();//新建人
		DeptPer deptPer = new DeptPer();//新建
		deptPer.setDept(dept);//设置部门
		deptPer.setFunModuleKey(FunModule.REPAIR.toString());//设置权限
		person.setDeptPers(Lists.newArrayList(deptPer));//设置
		EasyMock.expect(securityService.getCurUser()).andReturn(person);//添加getCueUser的返回对象
		EasyMock.expect(cycleService.getShowList(person.getDeptsFun(FunModule.REPAIR))).andReturn(new ArrayList());//添加getCueUser的返回对象
		control.replay();//
		String result = cycleAction.show();//获取结果
		assertEquals("0", cycleAction.getCycShowNum());
		assertEquals(result, CycleAction.SUCCESS);
	}

	@Test
	public void testAdd() {
		Map formMap = new HashMap();
		String deptId[] = { "1" };
		String name[] = { "甲机房配电室" };
		formMap.put("deptId", deptId);
		formMap.put("name", name);
		cycleAction.setFormMap(formMap);
		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(Long.parseLong("1"));
		dept.setGroups(groups);
		Person person = new Person();
		DeptPer deptPer = new DeptPer();
		deptPer.setDept(dept);
		deptPer.setFunModuleKey(FunModule.REPAIR.toString());
		person.setDeptPers(Lists.newArrayList(deptPer));
		EasyMock.expect(cycleService.addCycle(deptId[0], name[0])).andReturn("");
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();
		String result = cycleAction.add();
		assertEquals(result, "redirectShow");
	}

	@Test
	public void testCheckDelete() {
		String ids = "1!1538ck!2";
		cycleAction.setIds(ids);
		boolean flag = true;
		EasyMock.expect(cycleService.checkDelete((String[]) EasyMock.anyObject())).andReturn(flag).anyTimes();
		control.replay();
		String result = cycleAction.checkDelete();
		assertEquals(result, CycleAction.RIGHT);
	}

	@Test
	public void testEdit() {
		Map formMap = new HashMap();
		String newNameAndIds[] = { "1!1643in!甲机房配电室" };
		formMap.put("newNameAndIds", newNameAndIds);
		cycleAction.setFormMap(formMap);
		cycleService.editCycle((String[]) EasyMock.anyObject());
		EasyMock.expectLastCall().anyTimes();
		control.replay();
		String result = cycleAction.edit();
		assertEquals(result, "redirectShow");
	}

	@Test
	public void testSetAble() {
		Map formMap = new HashMap();
		String[] tabNum = { "0" };
		String[] ableNum = { "0" };
		String[] id = { "1" };
		formMap.put("tabNum", tabNum);
		formMap.put("ableNum", ableNum);
		formMap.put("autoIDActive0", id);
		cycleAction.setFormMap(formMap);
		cycleService.setAbleCycle((String[]) EasyMock.anyObject(), (String) EasyMock.anyObject());
		EasyMock.expectLastCall().anyTimes();
		control.replay();
		String result = cycleAction.setAble();
		assertEquals(result, "redirectShow");
	}

	@Test
	public void testSearchExecute() {
		Map formMap = new HashMap();
		String[] cycleId = { "1" };
		String[] year = { "2011" };
		formMap.put("cycleId", cycleId);
		formMap.put("year", year);
		cycleAction.setFormMap(formMap);
		EasyMock.expect(cycleService.getCycle(1, "2011")).andReturn(new ArrayList()).anyTimes();
		control.replay();
		String result = cycleAction.searchExecute();
		assertEquals(result, "executeContent");
	}

	@Test
	public void testShowItemContext() {
		Map formMap = new HashMap();
		String[] cardId = { "1" };
		formMap.put("cardId", cardId);
		cycleAction.setFormMap(formMap);
		EasyMock.expect(cycleService.getitemContext("1")).andReturn(new HashMap()).anyTimes();
		control.replay();
		String result = cycleAction.showItemContext();
		assertEquals(result, "itemContext");
	}

	@Test
	public void testShowExecute() {
		Map formMap = new HashMap();//定义form
		cycleAction.setFormMap(formMap);//设置form
		Dept dept = new Dept();//新建dept
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));//添加2个班组
		dept.setId(Long.parseLong("1"));//设置部门
		dept.setGroups(groups);//设置班组
		Person person = new Person();//新建人
		DeptPer deptPer = new DeptPer();//新建
		deptPer.setDept(dept);//设置部门
		deptPer.setFunModuleKey(FunModule.REPAIR.toString());//设置权限
		person.setDeptPers(Lists.newArrayList(deptPer));//设置
		EasyMock.expect(securityService.getCurUser()).andReturn(person);//添加getCueUser的返回对象
		EasyMock.expect(cycleService.getCycleByDept(person.getDeptsFun(FunModule.REPAIR))).andReturn(new ArrayList());//添加getCueUser的返回对象
		control.replay();//
		String result = cycleAction.showExecute();
		assertEquals(result, "execute");
	}
}
