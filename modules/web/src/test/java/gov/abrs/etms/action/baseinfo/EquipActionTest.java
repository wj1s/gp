package gov.abrs.etms.action.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.security.SecurityService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class EquipActionTest extends BaseActionTest {

	private EquipAction equipAction;

	private ExecuteDAO mockExecuteDAO;

	private SecurityService mockSecurityService;

	@Before
	public void setUp() {
		equipAction = new EquipAction();
		mockExecuteDAO = control.createMock(ExecuteDAO.class);
		mockSecurityService = control.createMock(SecurityService.class);
		equipAction.setExecuteDAO(mockExecuteDAO);
		equipAction.setSecurityService(mockSecurityService);
	}

	@Test
	public void testAutocompleteAjax() throws UnsupportedEncodingException {
		//设备1对应的部门是1
		TransType tt1 = new TransType(1L);
		tt1.setDepts(Lists.newArrayList(new Dept(1L)));
		TechSystem ts1 = new TechSystem(1L);
		ts1.setTransType(tt1);
		Equip e1 = new Equip(1L);
		//设备2对应的部门是2
		TransType tt2 = new TransType(2L);
		tt2.setDepts(Lists.newArrayList(new Dept(2L)));
		TechSystem ts2 = new TechSystem(2L);
		ts2.setTransType(tt2);
		Equip e2 = new Equip(2L);

		//当前人有1，3的基础信息部门权限，2的事故部门权限
		Person curPerson = new Person(1L);
		DeptPer dp = new DeptPer(new Dept(1L), curPerson, FunModule.BASEINFO.getKey());
		DeptPer dp2 = new DeptPer(new Dept(2L), curPerson, FunModule.ACCD.getKey());
		DeptPer dp3 = new DeptPer(new Dept(3L), curPerson, FunModule.BASEINFO.getKey());
		curPerson.setDeptPers(Lists.newArrayList(dp, dp2, dp3));

		//准备数据(模拟页面AJAX提交的编码)
		equipAction.setQ(new String("设备".getBytes("UTF-8"), "iso-8859-1"));
		ArrayList<Object> equips = Lists.newArrayList((Object) e1, (Object) e2);
		//录制脚本
		EasyMock.expect(mockExecuteDAO.getByNameLike(Equip.class, "设备")).andReturn(equips);
		control.replay();
		//执行测试
		equipAction.setFunModule(FunModule.BASEINFO.getKey());
		String result = equipAction.autocompleteAjax();
		//校验结果
		assertEquals(result, GridAction.AUTO);
		//设备已经和部门权限脱钩了，所以每次都全查出来
		assertEquals(equipAction.getList().size(), 2);
		assertEquals(equipAction.getList().get(0), tt1);
	}
}
