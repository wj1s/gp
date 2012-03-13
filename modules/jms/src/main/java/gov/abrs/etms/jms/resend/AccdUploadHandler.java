package gov.abrs.etms.jms.resend;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnOperationA;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.model.abnormal.AbnormalF;
import gov.abrs.etms.model.abnormal.AccdDutyTime;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.StationService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.Comment;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class AccdUploadHandler implements ActionHandler {

	private static final long serialVersionUID = -9167079506719669314L;

	private static final Logger log = Logger.getLogger(AccdUploadHandler.class);

	public void execute(ExecutionContext context) throws Exception {
		ProcessInstance pi = context.getContextInstance().getProcessInstance();
		String accdId = pi.getKey();
		Abnormal abnormal = abnormalService.get(Long.valueOf(accdId));
		boolean flag = false;
		try {
			JSONObject obj = assembleAccd(abnormal, pi);
			sendDataService.sendData(obj, "ACCD");
			log.info("调用服务,向局端上报编号" + abnormal.getAccdCode() + "的事故!");
			flag = true;
		} catch (Exception e) {
			log.info("编号" + abnormal.getAccdCode() + "的事故上报不成功!");
			log.info("错误信息为" + e);
		}
		if (flag) {
			try {
				TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
				workFlowService.startTaskInstance(taskInstance);
				log.info("编号" + abnormal.getAccdCode() + "的事故已成功上报至局端!");
			} catch (Exception e) {
				e.printStackTrace();
				log.info("错误信息为" + e);
			} finally {}
		} else {
			log.info("编号" + abnormal.getAccdCode() + "的事故上报不成功,等待1分钟后下一次重发!");
		}
	}

	private JSONObject assembleAccd(Abnormal abnormal, ProcessInstance pi) {
		JSONObject obj = new JSONObject();
		String stationCode = stationService.getStation().getCode();
		obj.put("type", "E");
		obj.put("accdCode", abnormal.getAccdCode());
		obj.put("stationCode", stationCode);
		obj.put("accdReason", abnormal.getAccdReason());
		obj.put("accdDesc", abnormal.getAccdDesc());
		if (!"".equals(abnormal.getAccdPrevWay())) {
			obj.put("prevWay", abnormal.getAccdPrevWay());
		}
		obj.put("processStep", abnormal.getProcessStep());
		obj.put("startTime", DateUtil.getDateTimeStr(abnormal.getStartTime()));
		obj.put("endTime", DateUtil.getDateTimeStr(abnormal.getEndTime()));
		if (abnormal instanceof AbnormalF) {
			obj.put("equipName", ((AbnormalF) abnormal).getEquipF().getName());
			obj.put("taId", ((AbnormalF) abnormal).getEquipF().getTache().getId().toString());
		}
		obj.put("transType", abnormal.getTransType().getParaCode());
		obj.put("empName", abnormal.getEmpName());
		obj.put("updDate", abnormal.getUpdDate());

		JSONArray operationList = new JSONArray();
		for (AbnOperationA opa : abnormal.getAbnOperationAs()) {
			JSONObject objOpa = new JSONObject();
			objOpa.put("opId", opa.getOperation().getId().toString());
			objOpa.put("startTime", DateUtil.getDateTimeStr(opa.getStartTime()));
			objOpa.put("endTime", DateUtil.getDateTimeStr(opa.getEndTime()));
			JSONArray dutyTimeList = new JSONArray();
			for (AccdDutyTime dutyTime : opa.getAccdDutyTimes()) {
				JSONObject objDt = new JSONObject();
				objDt.put("sortby", dutyTime.getSortby().toString());
				objDt.put("dutyCode", dutyTime.getAccdDuty().getParaCode());
				objDt.put("dutyTime", dutyTime.getDutyTime().toString());
				dutyTimeList.add(objDt);
			}
			objOpa.put("dutyTimeList", dutyTimeList);
			operationList.add(objOpa);
		}
		obj.put("operationList", operationList);

		JSONArray commentList = new JSONArray();
		List<Comment> comments = workFlowService.getCommentList(pi);
		for (Comment comment : comments) {
			JSONObject jsonComment = new JSONObject();
			jsonComment.put("auditTime", DateUtil.getDateTimeStr(comment.getTime()));
			String roleName = comment.getTaskInstance().getActorId();
			if ("ROLE_TEKOFFICER".equals(roleName)) {
				jsonComment.put("procCode", "A05");
			} else if ("ROLE_OFFICER".equals(roleName)) {
				jsonComment.put("procCode", "A07");
			} else if ("ROLE_GOVERNOR".equals(roleName)) {
				jsonComment.put("procCode", "A10");
			} else if ("上报局端".equals(roleName)) {
				jsonComment.put("procCode", "A21");
			} else {
				continue;
			}
			jsonComment.put("auditPerson", comment.getActorId());
			jsonComment.put("dscr", comment.getMessage());
			commentList.add(jsonComment);
		}
		obj.put("commentList", commentList);
		return obj;
	}

	private AbnormalService abnormalService;
	private StationService stationService;
	private WorkFlowService workFlowService;
	private SendDataService sendDataService;

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@Autowired
	public void setSendDataService(SendDataService sendDataService) {
		this.sendDataService = sendDataService;
	}
}
