package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.duty.DutyRuleService;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DutyRuleAction extends GridAction<DutyRule> {

	private static final Logger LOG = Logger.getLogger(DutyRuleAction.class);
	private static final long serialVersionUID = 5411515585858983703L;
	private Long dpId;
	private int dayPartCount;
	private List<Dept> depts;
	private List<DutyRule> dutyRules;
	private String[] schNames;
	private String[] startTimes;
	private String[] endTimes;
	private int[] schOrders;

	//转到新增排班总页面
	public String show() {
		return SUCCESS;
	}

	//根据排班规则查找规则详细的ajax
	public String getRuleDetailAjax() {
		list = dutyRuleService.get(id).getDutySchedules();
		return NORMAL;
	}

	//跳转到排班页面的ajax
	public String arrange() {
		depts = getCurUser().getDeptsFun(FunModule.DUTY);
		if (depts != null && depts.size() != 0) {
			dutyRules = depts.get(0).getDutyRules();
		}
		return "arrange";
	}

	//根据部门查找排班规则和规则详细的ajax
	public String getRuleByDeptAjax() {
		Dept dept = this.deptService.get(dpId);
		List<DutyRule> dutyRuleList = dept.getDutyRules();
		DutyRule first = null;
		if (dutyRuleList != null && dutyRuleList.size() != 0) {
			first = dutyRuleList.get(0);
		}
		JSONArray dutyRuleArray = new JSONArray();
		for (DutyRule dutyRule : dutyRuleList) {
			JSONObject json = new JSONObject();
			json.put("id", dutyRule.getId());
			json.put("ruleName", dutyRule.getRuleName());
			dutyRuleArray.add(json);
		}
		JSONArray dutyScheduleArray = new JSONArray();
		if (first != null) {
			List<DutySchedule> dutyScheduleList = first.getDutySchedules();
			for (DutySchedule dutySchedule : dutyScheduleList) {
				dutyScheduleArray.add(dutySchedule.getJsonObject());
			}
		}
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("dutyRules", dutyRuleArray.toString());
		result.put("dutySchedules", dutyScheduleArray.toString());
		json = result.toString();
		return EASY;
	}

	//判断是否存在同部门同运转数的排班规则ajax
	public String validateRuleAjax() {
		boolean flag = dutyRuleService.hasDutyRule(dpId, dayPartCount);
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	@Override
	public void prepareSave() throws InstantiationException, IllegalAccessException {
		if (id != null) {
			model = dutyRuleService.get(id);
			List<DutySchedule> list = model.getDutySchedules();
			int i = 0;
			for (DutySchedule dutySchedule : list) {
				dutySchedule.setSchName(schNames[i]);
				dutySchedule.setStartTime(DateUtil.stringToDate("1970-01-01 " + startTimes[i] + ":00",
						"yyyy-MM-dd HH:mm:ss"));
				dutySchedule.setEndTime(DateUtil.stringToDate("1970-01-01  " + endTimes[i] + ":00",
						"yyyy-MM-dd HH:mm:ss"));
				dutySchedule.setSchOrder(schOrders[i]);
				i++;
			}
		} else {
			model = new DutyRule();
			List<DutySchedule> dutySchedules = new ArrayList<DutySchedule>();
			for (int i = 0; i < schNames.length; i++) {
				DutySchedule dutySchedule = new DutySchedule();
				dutySchedule.setDutyRule(model);
				dutySchedule.setSchName(schNames[i]);
				dutySchedule.setStartTime(DateUtil.stringToDate("1970-01-01 " + startTimes[i] + ":00",
						"yyyy-MM-dd HH:mm:ss"));
				dutySchedule.setEndTime(DateUtil.stringToDate("1970-01-01  " + endTimes[i] + ":00",
						"yyyy-MM-dd HH:mm:ss"));
				dutySchedule.setSchOrder(schOrders[i]);
				dutySchedules.add(dutySchedule);
			}
			model.setDutySchedules(dutySchedules);
		}
	}

	@Override
	public String save() {
		dutyRuleService.save(model);
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("id", model.getId());
		json = result.toString();
		return EASY;
	}

	private DeptService deptService;

	private DutyRuleService dutyRuleService;

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setDutyRuleService(DutyRuleService dutyRuleService) {
		this.dutyRuleService = dutyRuleService;
	}

	//////
	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public List<DutyRule> getDutyRules() {
		return dutyRules;
	}

	public void setDutyRules(List<DutyRule> dutyRules) {
		this.dutyRules = dutyRules;
	}

	public void setDpId(Long dpId) {
		this.dpId = dpId;
	}

	public void setSchNames(String[] schNames) {
		this.schNames = schNames;
	}

	public void setStartTimes(String[] startTimes) {
		this.startTimes = startTimes;
	}

	public void setEndTimes(String[] endTimes) {
		this.endTimes = endTimes;
	}

	public void setSchOrders(int[] schOrders) {
		this.schOrders = schOrders;
	}

	public void setDayPartCount(int dayPartCount) {
		this.dayPartCount = dayPartCount;
	}

}
