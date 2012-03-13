package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.baseinfo.StationService;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DeptAction extends CrudAction<Dept> {
	private static final long serialVersionUID = -6515264761126217793L;
	private String deptTypeList;
	private String transTypeList;
	private List<Long> transTypes_id;
	private DeptType deptType;

	@Override
	protected void beforeUpdate(Dept model) {
		model.setDeptType(deptType);
	}

	@Override
	protected void beforeSave(Dept model) {
		model.setStation(stationService.getAll().get(0));
	}

	@Override
	protected void preSave(Dept model) {
		model.setEnable("1");
		//设置部门对应的传输类型
		List<TransType> transTypes = new ArrayList<TransType>();
		if (transTypes_id != null && transTypes_id.size() != 0) {
			for (Long transTypeId : transTypes_id) {
				TransType transType = (TransType) this.paraDtlService.get(transTypeId);
				transTypes.add(transType);
			}
		}
		if (model.getTransTypes() != null && model.getTransTypes().size() != 0) {
			model.getTransTypes().clear();
			model.getTransTypes().addAll(transTypes);
		} else {
			model.setTransTypes(transTypes);
		}
	}

	public String show() throws Exception {
		List<ParaDtl> deptTypes = this.paraDtlService.get(DeptType.class);
		deptTypeList = this.assemblyJsonArray(deptTypes, "codeDesc");
		List<ParaDtl> transTypes = this.paraDtlService.get(TransType.class);
		transTypeList = this.assemblyJsonArray(transTypes, "codeDesc");
		return SUCCESS;
	}

	//部门联动班组的ajax
	public String getGroupsAjax() throws Exception {
		List<Group> groups = this.deptService.get(id).getGroups();
		JSONObject jsonObject = new JSONObject();
		if (groups.size() != 0) {
			jsonObject.put("result", true);
			jsonObject.put("data", json = this.assemblyJsonArray(groups, "name"));
			json = jsonObject.toString();
		} else {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	//部门联动系统的ajax
	public String getCyclesAjax() throws Exception {
		List<Cycle> cycles = this.deptService.get(id).getCycles();
		JSONObject jsonObject = new JSONObject();
		if (cycles.size() != 0) {
			jsonObject.put("result", true);
			jsonObject.put("data", json = this.assemblyJsonArray(cycles, "name"));
			json = jsonObject.toString();
		} else {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	// 部门与人员的级联关系
	public String getPersonAjax() throws Exception {
		JSONObject jsonObject = new JSONObject();
		if (id == null) {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		} else {
			Dept dept = deptService.get(id);
			List personList = dept.getPersons();//获取部门对应的人员
			if (personList != null && personList.size() > 0) {
				jsonObject.put("result", true);
				jsonObject.put("data", json = this.assemblyJsonArray(personList, "name"));
				json = jsonObject.toString();
			} else {
				jsonObject.put("result", false);
				json = jsonObject.toString();
			}
		}
		return EASY;
	}

	private ParaDtlService paraDtlService;
	private DeptService deptService;
	private StationService stationService;

	@Override
	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String getDeptTypeList() {
		return deptTypeList;
	}

	public void setDeptTypeList(String deptTypeList) {
		this.deptTypeList = deptTypeList;
	}

	public String getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(String transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<Long> getTransTypes_id() {
		return transTypes_id;
	}

	public void setTransTypes_id(List<Long> transTypesId) {
		transTypes_id = transTypesId;
	}

	public DeptType getDeptType() {
		return deptType;
	}

	public void setDeptType(DeptType deptType) {
		this.deptType = deptType;
	}

}
