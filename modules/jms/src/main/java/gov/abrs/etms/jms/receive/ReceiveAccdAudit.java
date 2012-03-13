package gov.abrs.etms.jms.receive;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.workflow.WorkFlowService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiveAccdAudit {

	private static final Logger log = Logger.getLogger(ReceiveAccdAudit.class);

	public void doProcess(JSONObject json, String type) {
		String accdCode = json.getString("accdCode");
		String procCode = json.getString("procCode");
		String auditPerson = json.getString("auditPerson");
		String desc = json.getString("desc");
		String auditTime = json.getString("auditTime");
		Abnormal abnormal = abnormalService.get(accdCode);
		String processName = "";
		if (type.equalsIgnoreCase("ACCD")) {
			processName = "accidentReport";
		} else if (type.equalsIgnoreCase("COMMUTE")) {
			processName = "commuteAccdReport";
		}
		ProcessInstance pi = workFlowService.getProcessInstance(processName, abnormal.getId().toString());
		if (pi.hasEnded()) {
			//已经结束得任务重新驳回
			if (procCode.equals("A21")) {
				workFlowService.reviveProcessInstence(pi, "上报局端");
				TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
				workFlowService.backTaskInstance(taskInstance.getId(), auditPerson, desc, DateUtil
						.createDateTime(auditTime));
				log.info("已经结束的编号为" + accdCode + "事故驳回成功!");
			} else {
				log.info("事故驳回有问题，本已经结束得任务不应该重新审核通过");
			}
		} else {
			//正常流程
			TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
			if ("A21".equals(procCode)) {
				workFlowService.backTaskInstance(taskInstance.getId(), auditPerson, desc, DateUtil
						.createDateTime(auditTime));
				log.info("编号为" + accdCode + "事故驳回成功!");
			} else if ("A22".equals(procCode)) {
				workFlowService.endTaskInstance(taskInstance.getId(), auditPerson, desc, DateUtil
						.createDateTime(auditTime));
				log.info("编号为" + accdCode + "事故审核通过!");
			}
		}
	}

	private AbnormalService abnormalService;
	private WorkFlowService workFlowService;

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}
}
