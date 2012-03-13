package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.ZeroReptDtl;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.report.ReptGroup;
import gov.abrs.etms.service.report.ReptService;
import gov.abrs.etms.service.report.ZeroReptDtlService;
import gov.abrs.etms.service.report.ZeroReptGroup;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

@Results( { @Result(name = "return", type = "redirect", location = "rept!report.action"),
		@Result(name = "toTaskList", type = "redirect", location = "/workflow/task-list.action?taskType=1") })
public class ZeroReportingAction extends GridAction<ZeroReptDtl> {

	private static final long serialVersionUID = 4517633597617926907L;

	private String reptTime;
	private ReptGroup reptGroup;
	private String operate;
	private String rmks;
	private String accdFlag;
	private String time;
	private List<Abnormal> accds;
	private String reAuditFlag;
	private List<String> comments;
	private Long taskId;
	private String transitionName;
	private String checkContent;

	public String toSubmit() {
		reptGroup = reptService.getZeroReptGroup(Long.valueOf(reptTime));
		accds = new ArrayList<Abnormal>();
		ZeroReptDtl dtl = ((ZeroReptGroup) reptGroup).getZeroReptDtl();
		if (dtl != null) {
			accds = abnormalService.get(dtl.getStartTime(), dtl.getEndTime());
			if (operate != null && operate.equals("preview")) {
				rmks = dtl.getZeroRmks().getRmks();
				accdFlag = dtl.getZeroRmks().getAccdFlag().equalsIgnoreCase("Y") ? "是" : "否";
			} else {
				if (accds != null && accds.size() != 0) {
					rmks = "有" + accds.size() + "次事故发生，详细情况为：";
				} else {
					rmks = "安全播出无事故";
				}
			}
			time = DateUtil.dateToString(dtl.getStartTime(), "MM月dd日 HH:mm") + " - "
					+ DateUtil.dateToString(dtl.getEndTime(), "MM月dd日 HH:mm");
		}
		return "submit";
	}

	public String toResubmit() {
		Long dtlSeq = id;
		ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.REPT_ZERO.getDataSource(), dtlSeq
				.toString());
		if (pi != null) {
			comments = workFlowService.getComments(pi);
		} else {
			throw new RuntimeException("都到了审核页面了还没有流程，肯定出问题了");
		}

		reptGroup = reptService.getZeroReptGroup(id);
		accds = new ArrayList<Abnormal>();
		ZeroReptDtl dtl = ((ZeroReptGroup) reptGroup).getZeroReptDtl();
		if (dtl != null) {
			accds = abnormalService.get(dtl.getStartTime(), dtl.getEndTime());
			accdFlag = dtl.getZeroRmks().getAccdFlag();
			rmks = dtl.getZeroRmks().getRmks();
			time = DateUtil.dateToString(dtl.getStartTime(), "MM月dd日 HH:mm") + " - "
					+ DateUtil.dateToString(dtl.getEndTime(), "MM月dd日 HH:mm");
		}
		reAuditFlag = "1";
		return "submit";
	}

	public String submit() {
		Person person = getCurUser();
		String auditDesc = "";
		if (reAuditFlag == null || reAuditFlag.equals("")) {
			auditDesc = "上报零报告";
		} else if (reAuditFlag.equals("1")) {
			auditDesc = "重新上报零报告";
		}
		ZeroReptDtl dtl = zeroReptDtlService.getBySeq(Long.valueOf(reptTime));
		dtl.getZeroRmks().setRmks(rmks);
		if (accdFlag == null) {
			dtl.getZeroRmks().setAccdFlag("N");
		} else if (accdFlag.equals("on")) {
			dtl.getZeroRmks().setAccdFlag("Y");
		}

		ReptGroup reptGroup = reptService.getZeroReptGroup(Long.valueOf(reptTime));
		// 添加工作流描述
		//先检查是否有这个流程
		ProcessInstance piTemp = workFlowService.getProcessInstance(ProcessEnum.REPT_ZERO.getDataSource(), reptTime);
		if (piTemp == null) {
			ProcessInstance pi = workFlowService.startProcessInstance(ProcessEnum.REPT_ZERO.getDataSource(), reptTime,
					reptGroup.getReptGroupName(), person, auditDesc, person.getDept(), FunModule.REPORT);
		} else {
			ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.REPT_ZERO.getDataSource(), reptTime);
			TaskInstance ti = workFlowService.findActiveTaskInstance(pi);
			workFlowService.endTaskInstance(ti.getId(), person.getName(), auditDesc);
		}
		dtl.setTekOfficer(person.getName());
		zeroReptDtlService.save(dtl);
		return "return";
	}

	public String toAudit() {
		Long dtlSeq = id;
		ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.REPT_ZERO.getDataSource(), dtlSeq
				.toString());
		if (pi != null) {
			comments = workFlowService.getComments(pi);
		} else {
			throw new RuntimeException("都到了审核页面了还没有流程，肯定出问题了");
		}
		reptGroup = reptService.getZeroReptGroup(dtlSeq);
		ZeroReptDtl dtl = ((ZeroReptGroup) reptGroup).getZeroReptDtl();
		accdFlag = dtl.getZeroRmks().getAccdFlag().equalsIgnoreCase("Y") ? "是" : "否";
		rmks = dtl.getZeroRmks().getRmks();
		taskId = workFlowService.findActiveTaskInstance(pi).getId();
		return "audit";
	}

	//ReptPool相关报表审核
	public String audit() {
		Person person = getCurUser();
		String name = person.getName();
		ZeroReptGroup zeroReptGroup = reptService.getZeroReptGroup(Long.valueOf(reptTime));
		//给事故报表组添加技办审核的人员信息
		String temp = workFlowService.findTaskInstance(taskId.toString()).getActorId();
		temp = temp.replace("ROLE_", "").trim();
		if (temp.equals("TEKOFFICER")) {
			zeroReptGroup.getZeroReptDtl().setTekOfficer(name);
		}

		if (temp.equals("OFFICER")) {
			zeroReptGroup.getZeroReptDtl().setOfficer(name);
		}

		if (temp.equals("GOVERNOR")) {
			zeroReptGroup.getZeroReptDtl().setGovernor(name);
		}

		if (transitionName.equals("驳回")) {
			workFlowService.backTaskInstance(taskId, person.getName(), checkContent);
		} else {
			workFlowService.endTaskInstance(taskId, person.getName(), checkContent);
		}
		return "toTaskList";
	}

	private ReptService reptService;
	private AbnormalService abnormalService;
	private ZeroReptDtlService zeroReptDtlService;
	private WorkFlowService workFlowService;

	@Override
	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@Autowired
	public void setZeroReptDtlService(ZeroReptDtlService zeroReptDtlService) {
		this.zeroReptDtlService = zeroReptDtlService;
	}

	@Autowired
	public void setReptService(ReptService reptService) {
		this.reptService = reptService;
	}

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	public ReptGroup getReptGroup() {
		return reptGroup;
	}

	public void setReptGroup(ReptGroup reptGroup) {
		this.reptGroup = reptGroup;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getRmks() {
		return rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

	public String getAccdFlag() {
		return accdFlag;
	}

	public void setAccdFlag(String accdFlag) {
		this.accdFlag = accdFlag;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<Abnormal> getAccds() {
		return accds;
	}

	public void setAccds(List<Abnormal> accds) {
		this.accds = accds;
	}

	public String getReAuditFlag() {
		return reAuditFlag;
	}

	public void setReAuditFlag(String reAuditFlag) {
		this.reAuditFlag = reAuditFlag;
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

}
