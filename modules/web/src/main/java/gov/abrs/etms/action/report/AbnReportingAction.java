package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.BaseAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.report.ReptGroup;
import gov.abrs.etms.service.report.ReptService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Results( { @Result(name = "return", type = "redirect", location = "rept!report.action") })
public class AbnReportingAction extends BaseAction {

	private static final long serialVersionUID = -5082752460464523962L;

	//private String operation; //判断是否是任务查看
	private String reptTime;
	private ReptGroup reptGroup;
	private String reSubmitFlag;
	private Long id;
	private List<String> comments;
	private Long taskId;

	//转到上报事故报表页面
	public String toSubmit() {
		reptGroup = reptService.getAbnReptGroup(reptTime);
		return "submit";
	}

	//技办上报事故报表
	public String submit() {
		Person person = getCurUser();
		ReptGroup reptGroup = reptService.getAbnReptGroup(reptTime);
		//给事故报表组添加技办审核的人员信息
		for (Role role : person.getRoles()) {
			if (role.getName().equals("TEKOFFICER")) {
				for (Rept rept : reptGroup.getRepts()) {
					rept.setTekOfficer(person.getName());
				}
				break;
			}
		}
		ProcessInstance piTemp = workFlowService.getProcessInstance(ProcessEnum.REPT_ACCD.getDataSource(), reptTime);
		if (piTemp == null) {
			Dept dept = deptService.get((DeptType) paraDtlService.getByCode(DeptType.class, "TKMG")).get(0);
			//拥有技办的报表权限的流程
			workFlowService.startProcessInstance(ProcessEnum.REPT_ACCD.getDataSource(), reptTime, reptGroup
					.getReptTime()
					+ "事故报表", person, "上报报表", dept, FunModule.REPORT);
		}
		return "return";
	}

	//转到上报事故报表页面
	public String toReSubmit() {
		Assert.notNull(id);
		reSubmitFlag = "reSubmit";
		reptGroup = reptService.getAbnReptGroup(id.toString());
		ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.REPT_ACCD.getDataSource(), id.toString());
		if (pi != null) {
			comments = workFlowService.getComments(pi);
		} else {
			throw new RuntimeException("都到了审核页面了还没有流程，肯定出问题了");
		}
		return "submit";
	}

	private ReptService reptService;
	private WorkFlowService workFlowService;
	private DeptService deptService;
	private ParaDtlService paraDtlService;

	@Autowired
	public void setReptService(ReptService reptService) {
		this.reptService = reptService;
	}

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
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

	//	public String getOperation() {
	//		return operation;
	//	}
	//
	//	public void setOperation(String operation) {
	//		this.operation = operation;
	//	}

	public ReptGroup getReptGroup() {
		return reptGroup;
	}

	public void setReptGroup(ReptGroup reptGroup) {
		this.reptGroup = reptGroup;
	}

	public String getReSubmitFlag() {
		return reSubmitFlag;
	}

	public void setReSubmitFlag(String reSubmitFlag) {
		this.reSubmitFlag = reSubmitFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
