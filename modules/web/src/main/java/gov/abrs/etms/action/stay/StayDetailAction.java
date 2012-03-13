package gov.abrs.etms.action.stay;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.stay.DetailPersonId;
import gov.abrs.etms.model.stay.StayDetail;
import gov.abrs.etms.model.stay.StayDetailPerson;
import gov.abrs.etms.model.stay.StayRule;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.PersonService;
import gov.abrs.etms.service.stay.StayDetailService;
import gov.abrs.etms.service.stay.StayRuleService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class StayDetailAction extends CrudAction<StayDetail> {

	private static final long serialVersionUID = -5912175567040907399L;

	private Date startTime;
	private Date endTime;
	private Long deptId;
	private int periodCount;
	private Dept dept;
	private StayRule stayRule;
	private String[] empNames;
	private String[] startTimes;
	private String[] endTimes;
	private int[] periodIndexArray;
	private Long ruleId;
	private int startIndex;
	private List<Dept> deptList;
	private String empName;
	private int canChange;
	private String peopleNames;

	public String valToAddDetailAjax() {
		Dept dept = new Dept(deptId);
		json = stayDetailService.isExist(startTime, endTime, getCurDate(), dept);
		return EASY;
	}

	public String toAddDetail() {
		dept = deptService.get(deptId);
		List<StayRule> list = stayRuleService.getRules(dept, periodCount);
		if (list != null && list.size() != 0) {
			stayRule = list.get(0);
		}
		model.setDept(dept);
		return "add";
	}

	public String toGenerateDetails() {
		StayRule stayRule = new StayRule();
		Dept dept = new Dept(deptId);
		if (ruleId != null) {
			stayRule = stayRuleService.get(ruleId);
		}
		try {
			stayDetailService.createRuleAndDetail(stayRule, startTimes, endTimes, empNames, periodIndexArray,
					periodCount, dept, startTime, endTime, startIndex);
			return RIGHT;
		} catch (Exception e) {
			return WRONG;
		}
	}

	public String searchDetails() {
		deptList = getCurUser().getDeptsFun(FunModule.GENERAL);
		return "show";
	}

	@Override
	public String load() throws Exception {
		Dept dept = null;
		if (deptId != null) {
			dept = new Dept(deptId);
		}
		stayDetailService.get(carrier, startTime, endTime, dept, empName);
		return GRID;
	}

	public String showCurrent() {
		List<StayDetail> stayDetails = stayDetailService.getDetails(getCurDate());
		JSONObject obj = new JSONObject();
		JSONArray array = new JSONArray();
		for (StayDetail stayDetail : stayDetails) {
			JSONObject sd = new JSONObject();
			sd.put("deptName", stayDetail.getDept().getName());
			sd.put("startTime", DateUtil.dateToString(stayDetail.getStartTime(), "HH:mm"));
			sd.put("endTime", DateUtil.dateToString(stayDetail.getEndTime(), "HH:mm"));
			JSONArray innerArray = new JSONArray();
			for (StayDetailPerson sdp : stayDetail.getStayDetailPeople()) {
				JSONObject objInner = new JSONObject();
				StringBuffer sb = new StringBuffer();
				sb.append(sdp.getId().getEmpName());
				Person person = personService.getByName(sdp.getId().getEmpName());
				if (person.getOfficeTel() != null && !person.getOfficeTel().equals("")) {
					sb.append("Tel:" + person.getOfficeTel() + "&nbsp;");
				}
				if (person.getMobile() != null && !person.getMobile().equals("")) {
					sb.append("Mobile:" + person.getMobile() + "");
				}
				objInner.put("person", sb.toString());
				innerArray.add(objInner);
			}
			sd.put("personList", innerArray);
			array.add(sd);
		}
		obj.put("result", true);
		obj.put("data", array);
		json = obj.toString();
		return EASY;
	}

	public String change() {
		StayDetail stayDetail = stayDetailService.get(id);
		if (DateUtil.beforeDay(stayDetail.getStartTime(), DateUtil.addDay(getCurDate(), -1))) {
			canChange = 0;
		} else {
			canChange = 1;
			model = stayDetail;
		}
		return "change";
	}

	@Override
	public void beforeUpdate(StayDetail model) {
		stayDetailService.clearPeople(model);
		String[] empNames = peopleNames.split(",");
		List<StayDetailPerson> list = new ArrayList<StayDetailPerson>();
		for (String empName : empNames) {
			if (!empName.equals("") && !empName.equals(" ")) {
				StayDetailPerson sdp = new StayDetailPerson();
				sdp.setId(new DetailPersonId(model, empName));
				list.add(sdp);
			}
		}
		model.getStayDetailPeople().addAll(list);
	}

	private StayDetailService stayDetailService;
	private StayRuleService stayRuleService;
	private DeptService deptService;
	private PersonService personService;

	@Autowired
	public void setStayDetailService(StayDetailService stayDetailService) {
		this.stayDetailService = stayDetailService;
	}

	@Autowired
	public void setStayRuleService(StayRuleService stayRuleService) {
		this.stayRuleService = stayRuleService;
	}

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public int getPeriodCount() {
		return periodCount;
	}

	public void setPeriodCount(int periodCount) {
		this.periodCount = periodCount;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public StayRule getStayRule() {
		return stayRule;
	}

	public void setStayRule(StayRule stayRule) {
		this.stayRule = stayRule;
	}

	public String[] getEmpNames() {
		return empNames;
	}

	public void setEmpNames(String[] empNames) {
		this.empNames = empNames;
	}

	public String[] getStartTimes() {
		return startTimes;
	}

	public void setStartTimes(String[] startTimes) {
		this.startTimes = startTimes;
	}

	public String[] getEndTimes() {
		return endTimes;
	}

	public void setEndTimes(String[] endTimes) {
		this.endTimes = endTimes;
	}

	public int[] getPeriodIndexArray() {
		return periodIndexArray;
	}

	public void setPeriodIndexArray(int[] periodIndexArray) {
		this.periodIndexArray = periodIndexArray;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getCanChange() {
		return canChange;
	}

	public void setCanChange(int canChange) {
		this.canChange = canChange;
	}

	public String getPeopleNames() {
		return peopleNames;
	}

	public void setPeopleNames(String peopleNames) {
		this.peopleNames = peopleNames;
	}

}
