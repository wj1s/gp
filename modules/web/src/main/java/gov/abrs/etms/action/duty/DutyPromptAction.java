package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.duty.DutyPrompt;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.duty.DutyPromptService;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyPromptAction extends GridAction<DutyPrompt> {

	private static final long serialVersionUID = 2562809904869112824L;
	private List<Dept> deptList;
	private Long dutyId;
	private DutyPrompt dutyPrompt;
	private List<DutyPrompt> dutyPrompts;

	public String toAdd() {
		deptList = this.deptService.getAll();
		return "input";
	}

	@Override
	public String save() {
		Date now = getCurDate();
		model.setDdate(now);
		this.dutyPromptService.save(model);
		JSONObject dutyPrompt = new JSONObject();
		dutyPrompt.put("result", true);
		dutyPrompt.put("id", model.getId());
		String content = model.getEmpName() + "提醒：" + model.getContent();
		dutyPrompt.put("content", content);
		json = dutyPrompt.toString();
		return EASY;
	}

	public String toEdit() {
		dutyPrompt = this.dutyPromptService.get(id);
		return "input";
	}

	@Override
	public String delete() {
		this.dutyPromptService.delete(id);
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("id", id);
		json = result.toString();
		return EASY;
	}

	//显示今天的值班提醒
	public String viewTodayPrompt() {
		Date date = this.getCurDate();
		//通过权限获得当前部门
		Dept dept = this.getCurUser().getDept();
		dutyPrompts = this.dutyPromptService.getDutyPromptNow(dept, date);
		return "show";
	}

	//检查当前时间有没有值班提醒
	public String checkPromptAjax() {
		Date date = getCurDate();
		//通过权限获得当前部门
		Dept dept = this.getCurUser().getDept();
		Boolean flag = this.dutyPromptService.hasDutyPromptNow(dept, date);
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	private DeptService deptService;
	private DutyPromptService dutyPromptService;

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setDutyPromptService(DutyPromptService dutyPromptService) {
		this.dutyPromptService = dutyPromptService;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public Long getDutyId() {
		return dutyId;
	}

	public void setDutyId(Long dutyId) {
		this.dutyId = dutyId;
	}

	public DutyPrompt getDutyPrompt() {
		return dutyPrompt;
	}

	public void setDutyPrompt(DutyPrompt dutyPrompt) {
		this.dutyPrompt = dutyPrompt;
	}

	public List<DutyPrompt> getDutyPrompts() {
		return dutyPrompts;
	}

	public void setDutyPrompts(List<DutyPrompt> dutyPrompts) {
		this.dutyPrompts = dutyPrompts;
	}

}
