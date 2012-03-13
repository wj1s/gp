package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.Post;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.PersonService;
import gov.abrs.etms.service.baseinfo.PostService;
import gov.abrs.etms.service.sys.RoleService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class PersonAction extends CrudAction<Person> {

	private static final long serialVersionUID = -6515264761126217793L;

	private String groupList;
	private String deptList;
	private String sexList;
	private String postList;
	private int sex;

	@Override
	protected void beforeUpdate(Person model) {
		model.setDept(null);
		model.setGroup(null);
		model.setPost(null);
		model.setSex(null);
	}

	@Override
	protected void preSave(Person model) {
		model.setEnable("1");
		//判断班组为空的情况
		if (model.getGroup() != null && model.getGroup().getId() == -1L) {
			model.setGroup(null);
		}
		switch (sex) {
		case -1:
			model.setSex(null);
			break;
		case 1:
			model.setSex(true);
			break;
		case 0:
			model.setSex(false);
		default:
			break;
		}
		//配置默认的功能权限
		if (model.getId() == null) {
			List<DeptPer> deptPers = new ArrayList<DeptPer>();
			for (FunModule fun : FunModule.values()) {
				DeptPer deptPer = new DeptPer();
				deptPer.setDept(model.getDept());
				deptPer.setPerson(model);
				deptPer.setFunModuleKey(fun.getKey());
				deptPers.add(deptPer);
			}
			if (model.getDeptPers() != null && model.getDeptPers().size() != 0) {
				model.getDeptPers().clear();
				model.getDeptPers().addAll(deptPers);
			} else {
				if (model.getDeptPers() == null) {
					model.setDeptPers(deptPers);
				} else {
					model.getDeptPers().addAll(deptPers);
				}
			}
		}
		//配个默认的User角色
		if (model.getRoles() == null || model.getRoles().size() == 0) {
			model.setRoles(Lists.newArrayList(roleService.getByName("USER")));
		}
	}

	public String show() throws Exception {
		List<Dept> depts = getCurUser().getDeptsFun(FunModule.BASEINFO);
		deptList = this.assemblyJsonArray(depts, "name");
		if (depts != null && depts.size() != 0) {
			List<Group> groups = depts.get(0).getGroups();
			groupList = this.assemblyJsonArray(groups, "name");
		}
		sexList = assembleSex();

		List<Post> posts = this.postService.getAllBySort();
		postList = this.assemblyJsonArray(posts, "name");
		return SUCCESS;
	}

	private String assembleSex() {
		JSONArray array1 = new JSONArray();
		JSONObject obj2 = new JSONObject();
		obj2.put("value", 1);
		obj2.put("name", "男");
		array1.add(obj2);
		JSONObject obj3 = new JSONObject();
		obj3.put("value", 0);
		obj3.put("name", "女");
		array1.add(obj3);
		return array1.toString();
	}

	@Override
	public String autocompleteAjax() throws UnsupportedEncodingException {
		q = new String(q.getBytes("iso-8859-1"), "UTF-8");
		List<Person> tempList = personService.getByNameLike(q);//查找名字对应的人
		List<Person> finalList = Lists.newArrayList();//
		if ((funModule != null) && (!"".equals(funModule))) {//funModule
			List<Dept> depts = getCurUser().getDeptsFun(FunModule.getFunModule(funModule));
			for (Person obj : tempList) {
				List<Dept> deptTarget = ((AutoCompleteable) obj).getDeptsPopedom();
				List<Dept> listTarget = (List<Dept>) CollectionUtils.intersection(depts, deptTarget);
				if (listTarget != null && listTarget.size() > 0) {
					finalList.add(obj);
				}
			}
			list = finalList;
		} else {
			list = tempList;
		}
		return AUTO;
	}

	private DeptService deptService;
	private PostService postService;
	private RoleService roleService;
	private PersonService personService;

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setPostService(PostService postService) {
		this.postService = postService;
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public String getGroupList() {
		return groupList;
	}

	public void setGroupList(String groupList) {
		this.groupList = groupList;
	}

	public String getDeptList() {
		return deptList;
	}

	public void setDeptList(String deptList) {
		this.deptList = deptList;
	}

	public String getSexList() {
		return sexList;
	}

	public void setSexList(String sexList) {
		this.sexList = sexList;
	}

	public String getPostList() {
		return postList;
	}

	public void setPostList(String postList) {
		this.postList = postList;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

}
