package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.para.GroupType;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.GroupService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class GroupAction extends CrudAction<Group> {
	private static final long serialVersionUID = -6515264761126217793L;

	private String groupTypeList;
	private String deptList;
	private GroupService groupService;

	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public String show() throws Exception {
		List<ParaDtl> groupTypes = this.paraDtlService.get(GroupType.class);
		groupTypeList = this.assemblyJsonArray(groupTypes, "codeDesc");
		List<Dept> depts = this.deptService.getAll();
		deptList = this.assemblyJsonArray(depts, "name");
		return SUCCESS;
	}

	//删除班组，当班组有人员时也可以删除
	@Override
	public String delete() {
		JSONArray array = JSONArray.fromObject(carrier.getDelIds());
		try {
			for (Object object : array) {
				Long delId = ((JSONObject) object).getLong("id");
				this.groupService.delete(delId);
			}
			return RIGHT;
		} catch (Exception e) {
			e.printStackTrace();
			return WRONG;
		}
	}

	@Override
	protected void beforeUpdate(Group model) {
		model.setDept(null);
		model.setGroupType(null);
	}

	private ParaDtlService paraDtlService;
	private DeptService deptService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getGroupTypeList() {
		return groupTypeList;
	}

	public void setGroupTypeList(String groupTypeList) {
		this.groupTypeList = groupTypeList;
	}

	public String getDeptList() {
		return deptList;
	}

	public void setDeptList(String deptList) {
		this.deptList = deptList;
	}

}
