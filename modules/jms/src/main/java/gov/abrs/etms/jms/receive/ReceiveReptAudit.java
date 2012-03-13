package gov.abrs.etms.jms.receive;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.service.workflow.WorkFlowService;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiveReptAudit {

	private static final Logger log = Logger.getLogger(ReceiveReptAudit.class);

	public void doProcess(JSONObject json) {
		String reptTime = json.getString("reptTime");
		String rmks = json.getString("rmks");
		String auditPerson = json.getString("empName");
		String procCode = json.getString("procCode");//20通过21驳回 现在没用，万一以后要用呢，先留着吧
		String auditTime = json.getString("auditTime");
		String dataSource = json.getString("dataSource");
		try {
			ProcessInstance pi = workFlowService.getProcessInstance(dataSource, reptTime);
			if (pi.hasEnded()) {
				workFlowService.reviveProcessInstence(pi, "上报局端");
				TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
				workFlowService.backTaskInstance(taskInstance.getId(), auditPerson, rmks, DateUtil
						.createDateTime(auditTime));
				log.info("报表驳回信息下发成功");
			} else {
				log.info("报表驳回时发现流程还没结束，说明出问题了");
				throw new RuntimeException("报表驳回时发现流程还没结束，说明出问题了");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.toString());
		}
	}

	private WorkFlowService workFlowService;

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
