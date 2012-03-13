package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.model.rept.ImptPeriod;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.report.ImptReptGroup;
import gov.abrs.etms.service.report.ReptGroup;
import gov.abrs.etms.service.report.ReptService;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Results( { @Result(name = "return", type = "redirect", location = "rept!report.action") })
public class ImptReportingAction extends GridAction<ImptPeriod> {

	private static final long serialVersionUID = 3939751733420496868L;

	private String reptTime;
	private ImptReptGroup reptGroup;
	private String reSubmitFlag;
	private List<String> comments;
	private Long taskId;

	//跳转到保证期报表上报页面
	public String toSubmit() {
		reptGroup = reptService.getImptReptGroup(reptTime);
		return "submit";
	}

	//技办上报保证期报表
	public String submit() {
		Person person = getCurUser();
		ReptGroup reptGroup = reptService.getImptReptGroup(reptTime);
		//给事故报表组添加技办审核的人员信息
		for (Role role : person.getRoles()) {
			if (role.getName().equals("TEKOFFICER")) {
				for (Rept rept : reptGroup.getRepts()) {
					rept.setTekOfficer(person.getName());
				}
				break;
			}
		}
		ProcessInstance piTemp = workFlowService.getProcessInstance(ProcessEnum.REPT_IMPT.getDataSource(), reptTime);
		if (piTemp == null) {
			Dept dept = deptService.get((DeptType) paraDtlService.getByCode(DeptType.class, "TKMG")).get(0);
			//拥有技办的报表权限的流程
			workFlowService.startProcessInstance(ProcessEnum.REPT_IMPT.getDataSource(), reptTime, reptGroup
					.getReptGroupName()
					+ "保证期报表", person, "上报报表", dept, FunModule.REPORT);
		}
		return "return";
	}

	//重新上报重要保证期报表
	public String toReSubmit() {
		Assert.notNull(id);
		reSubmitFlag = "reSubmit";
		reptGroup = reptService.getImptReptGroup(id.toString());
		ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.REPT_IMPT.getDataSource(), id.toString());
		if (pi != null) {
			comments = workFlowService.getComments(pi);
		} else {
			throw new RuntimeException("都到了审核页面了还没有流程，肯定出问题了");
		}
		return "submit";
	}

	private ReptService reptService;
	private DeptService deptService;
	private ParaDtlService paraDtlService;

	@Autowired
	public void setReptService(ReptService reptService) {
		this.reptService = reptService;
	}

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	public ImptReptGroup getReptGroup() {
		return reptGroup;
	}

	public void setReptGroup(ImptReptGroup reptGroup) {
		this.reptGroup = reptGroup;
	}

	public String getReSubmitFlag() {
		return reSubmitFlag;
	}

	public void setReSubmitFlag(String reSubmitFlag) {
		this.reSubmitFlag = reSubmitFlag;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

}
