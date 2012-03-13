package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.action.util.ReptCarrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.rept.ImptPeriod;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.rept.ReptDef;
import gov.abrs.etms.service.baseinfo.TransTypeService;
import gov.abrs.etms.service.report.ImptPeriodService;
import gov.abrs.etms.service.report.ReptDefService;
import gov.abrs.etms.service.report.ReptGroup;
import gov.abrs.etms.service.report.ReptService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

@Results( { @Result(name = "toTaskList", type = "redirect", location = "/workflow/task-list.action?taskType=1") })
public class ReptAction extends GridAction<Rept> {

	private static final long serialVersionUID = -2412473346042933508L;

	private List<ReptGroup> curReptGroupList;
	private String curMonth;
	private List<ReptGroup> lastReptGroupList;
	private String lastMonth;
	private List<ReptGroup> nextReptGroupList;
	private String nextMonth;
	private String reptId;
	private ReptDef reptDefExam;
	private List<TransType> transTypes;
	private List<ImptPeriod> imptPeriods;
	private List<ReptDef> reptDefs;
	private ReptCarrier reptCarrier;
	private List<String> inputs;
	private int index;
	private String reptTime;
	private String dataSource;
	private Long taskId;
	private List<String> comments;
	private ReptGroup reptGroup;
	private String transitionName;
	private String checkContent;
	private String reptDate;

	//转到报表上报页面
	public String report() {
		Date date = getCurDate();
		String curMonthStr = DateUtil.dateToString(date, DateUtil.FORMAT_YYYYMM);
		String lastMonthStr = DateUtil.dateToString(DateUtil.addMonth(date, -1), DateUtil.FORMAT_YYYYMM);
		String nextMonthStr = DateUtil.dateToString(DateUtil.addMonth(date, 1), DateUtil.FORMAT_YYYYMM);
		//查询所有本月应该上报的报表
		curReptGroupList = reptService.getReptGroups(curMonthStr);
		//查询所有上月应该上报的报表
		lastReptGroupList = reptService.getReptGroups(lastMonthStr);
		//查询所有下月应该上报的报表
		nextReptGroupList = reptService.getReptGroups(nextMonthStr);
		curMonth = DateUtil.dateToString(date, "yyyy年MM月");
		lastMonth = DateUtil.dateToString(DateUtil.addMonth(date, -1), "yyyy年MM月");
		nextMonth = DateUtil.dateToString(DateUtil.addMonth(date, 1), "yyyy年MM月");
		return "report";
	}

	//转到日常报表查询页面
	public String show() {
		reptDefs = reptDefService.getByDataSource("EXAM");
		return "common";
	}

	//各种报表查询的公共方法
	public String query() {
		if (reptCarrier == null) {
			reptCarrier = new ReptCarrier();
			Date startTime = DateUtil.getFirstDayOfMonth(DateUtil.addMonth(getCurDate(), -1));
			Date endTime = DateUtil.getLastDayOfMonth(DateUtil.addMonth(getCurDate(), -1));
			reptCarrier.setStartTime(DateUtil.getDateStr(startTime));
			reptCarrier.setEndTime(DateUtil.getDateStr(endTime));
		}
		reptDefExam = reptDefService.get(reptId);
		inputs = Lists.newArrayList(reptDefExam.getModelInput().split(","));
		transTypes = transTypeService.getAll();
		imptPeriods = imptPeriodService.getAll();
		return "content";
	}

	//浏览月报表中每一张润前报表的方法
	public String rqMonthRept() {
		model = reptService.get(id);
		return "rqMonthRept";
	}

	//浏览查询报表的方法
	public String rqExamRept() {
		reptDefExam = reptDefService.get(reptId);
		return "rqExamRept";
	}

	//报表审核的统一页面
	public String toAudit() {
		Assert.notNull(taskId);
		Assert.notNull(id);
		ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.getDataSource("REPT_"
				+ dataSource.toString()), id.toString());
		if (pi != null) {
			comments = workFlowService.getComments(pi);
		} else {
			throw new RuntimeException("都到了审核页面了还没有流程，肯定出问题了");
		}
		reptGroup = reptService.getReptGroup(id.toString(), dataSource);
		return "audit";
	}

	//报表审核
	public String audit() {
		Person person = getCurUser();
		ReptGroup reptGroup = null;
		if (dataSource.equals("ACCD")) {
			reptGroup = reptService.getAbnReptGroup(reptDate.replaceAll(",", "").trim());
		} else if (dataSource.equals("IMPT")) {
			reptGroup = reptService.getImptReptGroup(reptDate.replaceAll(",", "").trim());
		}

		//给事故报表组添加技办审核的人员信息
		String temp = workFlowService.findTaskInstance(taskId.toString()).getActorId();
		temp = temp.replace("ROLE_", "").trim();
		if (temp.equals("TEKOFFICER")) {
			for (Rept rept : reptGroup.getRepts()) {
				rept.setTekOfficer(person.getName());
			}
		}

		if (temp.equals("OFFICER")) {
			for (Rept rept : reptGroup.getRepts()) {
				rept.setOfficer(person.getName());
			}
		}

		if (temp.equals("GOVERNOR")) {
			for (Rept rept : reptGroup.getRepts()) {
				rept.setGovernor(person.getName());
			}
		}

		if (transitionName.equals("驳回")) {
			workFlowService.backTaskInstance(taskId, person.getName(), checkContent);
		} else {
			workFlowService.endTaskInstance(taskId, person.getName(), checkContent);
		}
		return "toTaskList";
	}

	private ReptService reptService;
	private ReptDefService reptDefService;
	private TransTypeService transTypeService;
	private ImptPeriodService imptPeriodService;
	private WorkFlowService workFlowService;

	@Autowired
	public void setReptService(ReptService reptService) {
		this.reptService = reptService;
	}

	@Autowired
	public void setReptDefService(ReptDefService reptDefService) {
		this.reptDefService = reptDefService;
	}

	@Autowired
	public void setTransTypeService(TransTypeService transTypeService) {
		this.transTypeService = transTypeService;
	}

	@Autowired
	public void setImptPeriodService(ImptPeriodService imptPeriodService) {
		this.imptPeriodService = imptPeriodService;
	}

	@Override
	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public List<ReptGroup> getCurReptGroupList() {
		return curReptGroupList;
	}

	public void setCurReptGroupList(List<ReptGroup> curReptGroupList) {
		this.curReptGroupList = curReptGroupList;
	}

	public String getCurMonth() {
		return curMonth;
	}

	public void setCurMonth(String curMonth) {
		this.curMonth = curMonth;
	}

	public List<ReptGroup> getLastReptGroupList() {
		return lastReptGroupList;
	}

	public void setLastReptGroupList(List<ReptGroup> lastReptGroupList) {
		this.lastReptGroupList = lastReptGroupList;
	}

	public String getLastMonth() {
		return lastMonth;
	}

	public void setLastMonth(String lastMonth) {
		this.lastMonth = lastMonth;
	}

	public List<ReptGroup> getNextReptGroupList() {
		return nextReptGroupList;
	}

	public void setNextReptGroupList(List<ReptGroup> nextReptGroupList) {
		this.nextReptGroupList = nextReptGroupList;
	}

	public String getNextMonth() {
		return nextMonth;
	}

	public void setNextMonth(String nextMonth) {
		this.nextMonth = nextMonth;
	}

	public ReptService getReptService() {
		return reptService;
	}

	public String getReptId() {
		return reptId;
	}

	public void setReptId(String reptId) {
		this.reptId = reptId;
	}

	public List<TransType> getTransTypes() {
		return transTypes;
	}

	public void setTransTypes(List<TransType> transTypes) {
		this.transTypes = transTypes;
	}

	public ReptDef getReptDefExam() {
		return reptDefExam;
	}

	public void setReptDefExam(ReptDef reptDefExam) {
		this.reptDefExam = reptDefExam;
	}

	public List<ImptPeriod> getImptPeriods() {
		return imptPeriods;
	}

	public void setImptPeriods(List<ImptPeriod> imptPeriods) {
		this.imptPeriods = imptPeriods;
	}

	public List<ReptDef> getReptDefs() {
		return reptDefs;
	}

	public void setReptDefs(List<ReptDef> reptDefs) {
		this.reptDefs = reptDefs;
	}

	public ReptCarrier getReptCarrier() {
		return reptCarrier;
	}

	public void setReptCarrier(ReptCarrier reptCarrier) {
		this.reptCarrier = reptCarrier;
	}

	public List<String> getInputs() {
		return inputs;
	}

	public void setInputs(List<String> inputs) {
		this.inputs = inputs;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public ReptGroup getReptGroup() {
		return reptGroup;
	}

	public void setReptGroup(ReptGroup reptGroup) {
		this.reptGroup = reptGroup;
	}

	public String getTransitionName() {
		return transitionName;
	}

	public void setTransitionName(String transitionName) {
		this.transitionName = transitionName;
	}

	public String getCheckContent() {
		return checkContent;
	}

	public void setCheckContent(String checkContent) {
		this.checkContent = checkContent;
	}

	public String getReptDate() {
		return reptDate;
	}

	public void setReptDate(String reptDate) {
		this.reptDate = reptDate;
	}

}
