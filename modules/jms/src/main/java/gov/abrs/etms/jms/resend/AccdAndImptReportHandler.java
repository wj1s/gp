package gov.abrs.etms.jms.resend;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.model.abnormal.AbnormalB;
import gov.abrs.etms.model.abnormal.AbnormalF;
import gov.abrs.etms.model.abnormal.AbnormalO;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.model.rept.OpTime;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.StationService;
import gov.abrs.etms.service.report.BroadByTimeService;
import gov.abrs.etms.service.report.OpTimeService;
import gov.abrs.etms.service.workflow.ProcessNameMapping;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
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

public class AccdAndImptReportHandler implements ActionHandler {

	private static final long serialVersionUID = -907738700544451877L;

	private static final Logger log = Logger.getLogger(AccdAndImptReportHandler.class);

	public void execute(ExecutionContext context) throws Exception {
		ProcessInstance pi = context.getContextInstance().getProcessInstance();
		String reptTime = pi.getKey();
		String processName = pi.getProcessDefinition().getName();
		String dataSource = ProcessNameMapping.getTaskNameMappingCn(processName).substring(4).toUpperCase();
		String reportName = "";
		if (dataSource.equals("ACCD")) {
			reportName = "事故报表";
		} else if (dataSource.equals("IMPT")) {
			reportName = "重要保证期报表";
		} else {
			throw new RuntimeException("dataSource有问题，既非ACCD也非IMPT");
		}
		log.info("调用服务,向局端上报" + reptTime + reportName);
		boolean flag = false;
		try {
			JSONObject obj = assembleAccdAndImptReport(reptTime, dataSource, pi);
			sendDataService.sendData(obj, "REPT");
			flag = true;
		} catch (Exception e) {
			log.info(reportName + "上报不成功!");
			log.info("错误信息为" + e);
		}
		if (flag) {
			try {
				TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
				workFlowService.startTaskInstance(taskInstance);
				workFlowService.endTaskInstance(taskInstance.getId());
				log.info(reportName + "已成功上报至局端!");
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e);
			} finally {}
		} else {
			log.info(reportName + "上报不成功,等待1分钟后下一次重发!");
		}
	}

	private JSONObject assembleAccdAndImptReport(String reptTime, String dataSource, ProcessInstance pi) {
		JSONObject obj = new JSONObject();
		String stationCode = stationService.getStation().getCode();
		obj.put("type", dataSource);

		if (dataSource.equals("ACCD")) {
			obj.put("reptTime", reptTime);

		} else if (dataSource.equals("IMPT")) {
			obj.put("imptPeriodSeq", reptTime);
		}
		obj.put("stationCode", stationCode);

		JSONArray broadTimeList = new JSONArray();
		for (OpTime roTime : reptOpTimeService.get(reptTime)) {
			JSONObject objRoTime = new JSONObject();
			objRoTime.put("opId", roTime.getOperation().getId().toString());
			objRoTime.put("transType", roTime.getTransType().getParaCode());
			if (roTime.getTransmitDef() != null) {
				objRoTime.put("transMode", roTime.getTransmitDef().getParaCode());
			}
			objRoTime.put("broadTime", roTime.getBroadTime().toString());
			objRoTime.put("updDate", DateUtil.getDateTimeStr(roTime.getUpdDate()));
			objRoTime.put("empName", roTime.getEmpName());
			broadTimeList.add(objRoTime);
		}
		obj.put("broadTimeList", broadTimeList);

		Date startDateQuery = DateUtil.getFirstDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM"));
		Date endDateQuery = DateUtil.getLastDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM"));
		List<BroadByTime> list = broadByTimeService.get(startDateQuery, endDateQuery);
		if (list.size() > 0) {
			JSONArray broadByList = new JSONArray();
			for (BroadByTime bbt : list) {
				JSONObject objBbt = new JSONObject();
				objBbt.put("id", bbt.getId().toString());
				objBbt.put("opId", bbt.getOperation().getId().toString());
				objBbt.put("broadReason", bbt.getBroadByReason().getParaCode());
				objBbt.put("autoFlag", bbt.getAutoFlag() ? "Y" : "N");
				objBbt.put("notifyPerson", bbt.getNotifyPerson());
				objBbt.put("empName", bbt.getEmpName());
				objBbt.put("startTime", DateUtil.getDateTimeStr(bbt.getStartTime()));
				if (bbt.getEndTime() != null) {
					objBbt.put("endTime", DateUtil.getDateTimeStr(bbt.getEndTime()));
				}
				objBbt.put("broadByFlag", bbt.getBroadByFlag());
				objBbt.put("broadByStation", bbt.getBroadByStation().getParaCode());
				if (!"".equals(bbt.getBroadResult())) {
					objBbt.put("broadResult", bbt.getBroadResult());
				}
				broadByList.add(objBbt);
			}
			obj.put("broadByList", broadByList);
		}

		List<Abnormal> abnormalListQuery = abnormalService.getAll(startDateQuery, endDateQuery);
		JSONArray abnormalList = new JSONArray();
		if (abnormalListQuery.size() > 0) {
			for (Abnormal abn : abnormalListQuery) {
				JSONObject objAbn = new JSONObject();
				String type = "";
				objAbn.put("id", abn.getId().toString());
				objAbn.put("transType", abn.getTransType().getParaCode());
				objAbn.put("startTime", DateUtil.getDateTimeStr(abn.getStartTime()));
				objAbn.put("endTime", DateUtil.getDateTimeStr(abn.getEndTime()));
				objAbn.put("abnDesc", abn.getDesc());
				objAbn.put("abnReason", abn.getReason());
				objAbn.put("processStep", abn.getProcessStep());
				objAbn.put("updDate", abn.getUpdDate());
				objAbn.put("empName", abn.getEmpName());

				if (abn instanceof AbnormalB) {
					type = "B";
					objAbn.put("abnormalType", ((AbnormalB) abn).getAbnType().getParaCode());
					objAbn.put("equipName", ((AbnormalB) abn).getEquipB().getName());
					objAbn.put("taId", ((AbnormalB) abn).getEquipB().getTache().getId().toString());
					objAbn.put("abnType", ((AbnormalB) abn).getAbnType().getParaCode());
				} else if (abn instanceof AbnormalF) {
					type = "F";
					objAbn.put("equipName", ((AbnormalF) abn).getEquipF().getName());
					objAbn.put("taId", ((AbnormalF) abn).getEquipF().getTache().getId().toString());
					objAbn.put("accdCode", abn.getAccdCode());
					objAbn.put("operationList", assembleOps(abn, objAbn));
				} else if (abn instanceof AbnormalO) {
					type = "O";
					objAbn.put("abnormalType", ((AbnormalO) abn).getAbnType().getParaCode());
					objAbn.put("abnType", ((AbnormalO) abn).getAbnType().getParaCode());
					objAbn.put("accdCode", abn.getAccdCode());
					objAbn.put("operationList", assembleOps(abn, objAbn));
				}
				objAbn.put("type", type);
				abnormalList.add(objAbn);
			}

			obj.put("abnormalList", abnormalList);
		}

		JSONArray commentList = new JSONArray();
		List<Comment> comments = workFlowService.getCommentList(pi);
		for (Comment comment : comments) {
			JSONObject jsonComment = new JSONObject();
			jsonComment.put("auditTime", DateUtil.getDateTimeStr(comment.getTime()));
			String roleName = comment.getTaskInstance().getActorId();
			if ("ROLE_TEKOFFICER".equals(roleName)) {
				jsonComment.put("procCode", "06");
			} else if ("ROLE_OFFICER".equals(roleName)) {
				jsonComment.put("procCode", "08");
			} else if ("ROLE_GOVERNOR".equals(roleName)) {
				jsonComment.put("procCode", "10");
			} else if ("上报局端".equals(roleName)) {
				jsonComment.put("procCode", "21");
			}
			jsonComment.put("auditPerson", comment.getActorId());
			jsonComment.put("dscr", comment.getMessage());
			commentList.add(jsonComment);
		}
		obj.put("commentList", commentList);
		return obj;
	}

	private JSONArray assembleOps(Abnormal abnormal, JSONObject objAbn) {
		JSONArray operationList = new JSONArray();
		for (AbnOperation abnOp : abnormal.getAbnOperations()) {
			JSONObject objAbnOp = new JSONObject();
			objAbnOp.put("opId", abnOp.getOperation().getId().toString());
			objAbnOp.put("startTime", DateUtil.getDateTimeStr(abnOp.getStartTime()));
			objAbnOp.put("endTime", DateUtil.getDateTimeStr(abnOp.getEndTime()));
			operationList.add(objAbnOp);
		}
		return operationList;
	}

	private SendDataService sendDataService;
	private StationService stationService;
	private OpTimeService reptOpTimeService;
	private BroadByTimeService broadByTimeService;
	private AbnormalService abnormalService;
	private WorkFlowService workFlowService;

	@Autowired
	public void setSendDataService(SendDataService sendDataService) {
		this.sendDataService = sendDataService;
	}

	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	@Autowired
	public void setReptOpTimeService(OpTimeService reptOpTimeService) {
		this.reptOpTimeService = reptOpTimeService;
	}

	@Autowired
	public void setBroadByTimeService(BroadByTimeService broadByTimeService) {
		this.broadByTimeService = broadByTimeService;
	}

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}
}
