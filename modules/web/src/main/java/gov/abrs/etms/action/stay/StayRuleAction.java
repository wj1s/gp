package gov.abrs.etms.action.stay;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.stay.StayRule;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.stay.StayRuleService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class StayRuleAction extends GridAction<StayRule> {

	private static final long serialVersionUID = 5785122957936799401L;

	private List<Dept> deptList;
	private String startTime;
	private List<StayRule> stayRules;
	private Long deptId;

	public String show() {
		deptList = getCurUser().getDeptsFun(FunModule.GENERAL);
		//排班的默认开始时间为当天
		startTime = DateUtil.getDateStr(getCurDate());
		//取出第一个部门的历史留守记录
		if (deptList.size() > 0) {
			stayRules = stayRuleService.getRules(deptList.get(0));
		}
		return "show";
	}

	public String getRuleDetail() {
		model = this.stayRuleService.get(id);
		return "detail";
	}

	public String getRulesAjax() {
		List<StayRule> stayRules = stayRuleService.getRules(new Dept(deptId));
		JSONArray stayRulesArray = new JSONArray();
		for (StayRule stayRule : stayRules) {
			stayRulesArray.add(stayRule.getJsonObject());
		}
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("stayRules", stayRulesArray);
		json = result.toString();
		return EASY;
	}

	private StayRuleService stayRuleService;

	@Autowired
	public void setStayRuleService(StayRuleService stayRuleService) {
		this.stayRuleService = stayRuleService;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public List<StayRule> getStayRules() {
		return stayRules;
	}

	public void setStayRules(List<StayRule> stayRules) {
		this.stayRules = stayRules;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

}
