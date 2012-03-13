package gov.abrs.etms.action.system;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.sys.PopedomView;
import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.PersonService;
import gov.abrs.etms.service.sys.PopedomViewService;
import gov.abrs.etms.service.sys.RoleService;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class SystemAction extends GridAction<Person> {

	private static final long serialVersionUID = -72926939102983301L;

	private List<Role> rolesInit;
	private List<Dept> deptList;
	private List<FunModule> funs = Lists.newArrayList();
	private String roleIds;
	private List<String> funsStr;
	private List<Long> enableFlag;
	private Role modleRole;
	private List<Person> personList;
	private Long dpId;
	private Long roleId;
	private String personsInRole;
	private String popedomInRole;
	private Long popedomId;
	private String contextPath;
	private JSONArray zNodesArry = new JSONArray();
	private String zNodes;

	public String popedom() {
		return "user";
	}

	public String getUserTree() {
		List<Dept> depts = deptService.getAll();
		JSONArray jsonArray = new JSONArray();
		for (Dept dept : depts) {
			JSONObject jsonDept = new JSONObject();
			jsonDept.put("data", dept.getName());
			List<Person> people = dept.getPersons();
			JSONArray arrayPeople = new JSONArray();
			for (Person person : people) {
				JSONObject jsonPerson = new JSONObject();
				jsonPerson.put("data", person.getName());
				JSONObject temp = new JSONObject();
				temp.put("empId", person.getId());
				jsonPerson.put("attr", temp);
				arrayPeople.add(jsonPerson);
			}
			jsonDept.put("children", arrayPeople);
			jsonArray.add(jsonDept);
		}
		json = jsonArray.toString();
		return EASY;
	}

	public String getUserPopedom() {
		model = personService.get(id);
		rolesInit = roleService.getAll();
		List<Role> rolesTemp = model.getRoles();
		for (int i = 0; i < rolesInit.size(); i++) {
			for (int j = 0; j < rolesTemp.size(); j++) {
				if (rolesInit.get(i).getId() == rolesTemp.get(j).getId()) {
					rolesInit.remove(i);
					break;
				}
			}
		}
		deptList = deptService.getAll();
		for (FunModule fun : FunModule.values()) {
			funs.add(fun);
		}
		return "userContent";
	}

	public String saveRoles() {
		Person person = personService.get(id);
		person.getRoles().clear();
		List<Role> list = Lists.newArrayList();
		String[] roleIdArray = roleIds.split(",");
		for (String roleId : roleIdArray) {
			Role role = roleService.get(Long.valueOf(roleId));
			list.add(role);
		}
		person.getRoles().addAll(list);
		personService.save(person);
		return RIGHT;
	}

	public String savePersonFunByAjax() {
		Person person = personService.get(id);
		person.getDeptPers().clear();

		if (enableFlag == null)
			enableFlag = Lists.newArrayList();
		for (Integer i = 0; i < enableFlag.size(); i++) {
			String[] funs = funsStr.get(i).split(",");
			for (String fun : funs) {
				person.getDeptPers().add(new DeptPer(new Dept(enableFlag.get(i)), new Person(id), fun));
			}
		}
		personService.save(person);
		return RIGHT;
	}

	public String showRole() {
		return "role";
	}

	//初始化角色tree
	public String getRoleTree() {
		rolesInit = roleService.getAll();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonDept = new JSONObject();
		jsonDept.put("data", "角色列表");
		jsonDept.put("state", "open");
		JSONArray arrayPeople = new JSONArray();
		for (Role role : rolesInit) {
			JSONObject jsonPerson = new JSONObject();
			jsonPerson.put("data", role.getDesc());
			JSONObject temp = new JSONObject();
			temp.put("roleId", role.getId());
			jsonPerson.put("attr", temp);
			arrayPeople.add(jsonPerson);
		}
		jsonDept.put("children", arrayPeople);
		jsonArray.add(jsonDept);
		json = jsonArray.toString();
		return EASY;
	}

	//打开某一个角色的管理页面
	// 得到本部门的所有人员
	//显示角色的页面元素权限列表
	public String getRolePopedom() {
		modleRole = roleService.get(id);//获取角色信息		
		deptList = deptService.getAll();//得到所有的部门
		personList = modleRole.getPersons();//对应的人
		return "roleContent";
	}

	//保存角色的人员配置
	public String roleSavePerson() {
		String[] empIds = personsInRole.split(",");
		Role role = roleService.get(roleId);
		List list = new ArrayList();
		role.setPersons(list);
		for (int i = 0; i < empIds.length; i++) {
			String empId = empIds[i];
			if (!(("").equals(empId))) {
				Person person = personService.get(new Long(empId));
				role.getPersons().add(person);
			}
		}
		JSONObject jsonTem = new JSONObject();
		try {
			roleService.save(role);
			jsonTem.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonTem.put("result", false);
		}
		json = jsonTem.toString();
		return EASY;
	}

	//保存角色的页面元素权限配置
	public String roleSavePopedom() {
		Role role = roleService.get(roleId);
		String[] pops = popedomInRole.split(",");
		List list = new ArrayList();
		role.setPopedomViews(list);
		for (int i = 0; i < pops.length; i++) {
			String popId = pops[i];
			if (!(("").equals(popId))) {
				PopedomView popedom = popedomViewService.findById(popId);
				role.getPopedomViews().add(popedom);
			}
		}
		JSONObject jsonTem = new JSONObject();
		try {
			roleService.save(role);
			jsonTem.put("result", true);
		} catch (Exception e) {
			e.printStackTrace();
			jsonTem.put("result", false);
		}
		json = jsonTem.toString();
		return EASY;
	}

	//显示角色的页面元素权限列表async
	public String popCheckBoxAsync() {
		/* 得到本角色的所有相关权限 */
		Role role = roleService.get(roleId);
		if (id == null) {
			checkWithRoleAsyncOra();
		} else {
			PopedomView popedom = popedomViewService.findById(id.toString());
			checkWithRoleAsync(role, popedom);
		}
		json = zNodesArry.toString();
		return EASY;
	}

	//根据角色判断
	private void checkWithRoleAsyncOra() {
		PopedomView pop = popedomViewService.findById("-1");
		JSONObject jsonTem = new JSONObject();
		jsonTem.put("id", pop.getPopeId());
		jsonTem.put("pId", pop.getPopedomView().getPopeId());
		jsonTem.put("name", "选择菜单");
		jsonTem.put("nocheck", true);
		jsonTem.put("open", true);
		zNodesArry.add(jsonTem);
	}

	//根据角色判断
	private void checkWithRoleAsync(Role role, PopedomView popedom) {
		if (popedom.getPopedomViews() != null) {
			for (PopedomView pop : popedom.getPopedomViews()) {
				JSONObject jsonTem = new JSONObject();
				jsonTem.put("id", pop.getPopeId());
				jsonTem.put("pId", pop.getPopedomView().getPopeId());
				jsonTem.put("name", pop.getPopeName());
				if (hasPerToTheView(role, pop)) {
					if (!pop.getPopeId().equals("-1")) {
						jsonTem.put("checked", true);
					}
				} else {
					jsonTem.put("checked", false);
				}
				if (pop.getPopedomViews() != null) {
					jsonTem.put("open", true);
				}
				zNodesArry.add(jsonTem);
			}
		}

		return;
	}

	//显示角色的页面元素权限列表
	public String popCheckBox() {
		/* 得到本角色的所有相关权限 */
		Role role = roleService.get(roleId);
		PopedomView popedom = popedomViewService.findById("-1");
		checkWithRoleAll(role, popedom);
		json = zNodesArry.toString();
		return EASY;
	}

	//根据角色判断
	private void checkWithRoleAll(Role role, PopedomView popedom) {
		JSONObject jsonTem = new JSONObject();
		jsonTem.put("id", popedom.getPopeId());
		if (popedom.getPopeId().equals("-1")) {
			jsonTem.put("pId", "-2");
			jsonTem.put("name", "选择菜单");
			jsonTem.put("open", true);
			jsonTem.put("nocheck", true);
		} else {
			jsonTem.put("pId", popedom.getPopedomView().getPopeId());
			jsonTem.put("name", popedom.getPopeName());
		}
		if (hasPerToTheView(role, popedom)) {
			if (!popedom.getPopeId().equals("-1")) {
				jsonTem.put("checked", true);
			}
		} else {
			jsonTem.put("checked", false);
		}
		if (popedom.getPopedomViews() != null) {
			jsonTem.put("open", true);
			zNodesArry.add(jsonTem);
			for (PopedomView pop : popedom.getPopedomViews()) {
				checkWithRoleAll(role, pop);
			}
		} else {
			zNodesArry.add(jsonTem);
		}
		return;
	}

	//根据角色判断
	private boolean hasPerToTheView(Role role, PopedomView popedom) {
		if (popedom.getPopeId().equals("-1")) {
			return true;
		}
		List<Role> roleList = popedom.getRoles();
		if (roleList.contains(role)) {
			return true;
		}
		return false;
	}

	private DeptService deptService;
	private PersonService personService;
	private RoleService roleService;
	@Autowired
	private PopedomViewService popedomViewService;

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Role> getRolesInit() {
		return rolesInit;
	}

	public void setRolesInit(List<Role> rolesInit) {
		this.rolesInit = rolesInit;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public List<FunModule> getFuns() {
		return funs;
	}

	public void setFuns(List<FunModule> funs) {
		this.funs = funs;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getFunsStr() {
		return funsStr;
	}

	public void setFunsStr(List<String> funsStr) {
		this.funsStr = funsStr;
	}

	public List<Long> getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(List<Long> enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Role getModleRole() {
		return modleRole;
	}

	public void setModleRole(Role modleRole) {
		this.modleRole = modleRole;
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}

	public Long getDpId() {
		return dpId;
	}

	public void setDpId(Long dpId) {
		this.dpId = dpId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getPersonsInRole() {
		return personsInRole;
	}

	public void setPersonsInRole(String personsInRole) {
		this.personsInRole = personsInRole;
	}

	public String getPopedomInRole() {
		return popedomInRole;
	}

	public void setPopedomInRole(String popedomInRole) {
		this.popedomInRole = popedomInRole;
	}

	public Long getPopedomId() {
		return popedomId;
	}

	public void setPopedomId(Long popedomId) {
		this.popedomId = popedomId;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getzNodes() {
		return zNodes;
	}

	public void setzNodes(String zNodes) {
		this.zNodes = zNodes;
	}

	public JSONArray getzNodesArry() {
		return zNodesArry;
	}

	public void setzNodesArry(JSONArray zNodesArry) {
		this.zNodesArry = zNodesArry;
	}

}
