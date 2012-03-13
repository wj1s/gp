package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.duty.DutyRecord;
import gov.abrs.etms.model.duty.DutyRecordD;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.model.rept.ImptPeriod;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.report.BroadByTimeService;
import gov.abrs.etms.service.report.ImptPeriodService;
import gov.abrs.etms.service.report.ReptService;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class BroadByTimeAction extends CrudAction<BroadByTime> {

	private static final long serialVersionUID = 4591267500225628062L;
	private List<BroadByTime> broadByTimes;
	private List<ParaDtl> broadByReasons;
	private List<ParaDtl> broadByStations;
	private List<ParaDtl> transTypes;
	private List<Operation> operations;
	private String startDate;
	private String endDate;
	private Long broadByReasonId;
	private String opName;
	private Long broadByStationId;
	private BroadByTime broadByTime;
	private Long opId;
	private String msg;
	private String broadByFlag;

	public String getBroadByTimeAjax() {
		list = broadByTimeService.get(getCurDate(), getCurUser().getDept().getTransTypes());
		return NORMAL;
	}

	public String show() {
		broadByReasons = paraDtlService.get(BroadByReason.class);
		broadByStations = paraDtlService.get(BroadByStation.class);
		startDate = DateUtil.getDateStr(DateUtil.getFirstDayOfMonth(getCurDate()));
		return SUCCESS;
	}

	public String input() {
		if (id != null) {
			broadByTime = broadByTimeService.get(id);
			Date startTime = broadByTime.getStartTime();
			Date endTime = broadByTime.getEndTime();
			if (endTime != null) {
				msg = this.judgeReportAnInterval(startTime, endTime);
			}
		}
		if (msg == null || msg.equals("")) {
			broadByReasons = paraDtlService.get(BroadByReason.class);
			broadByStations = paraDtlService.get(BroadByStation.class);
			transTypes = paraDtlService.get(TransType.class);
			if (transTypes.size() > 0) {
				operations = ((TransType) transTypes.get(0)).getOperations();
			}
		}
		return "input";
	}

	@Override
	public String load() throws Exception {
		Date start = null;
		Date end = null;
		String operationName = null;
		ParaDtl broadByReason = null;
		ParaDtl broadByStation = null;
		if (opName != null && !"".equals(opName)) {
			operationName = encodeContent(opName);
		}
		if (startDate != null && !"".equals(startDate)) {
			start = DateUtil.createDate(startDate);
		}
		if (endDate != null && !"".equals(endDate)) {
			end = DateUtil.createDate(endDate);
		}
		if (broadByReasonId != null) {
			broadByReason = paraDtlService.get(broadByReasonId);
		}
		if (broadByStationId != null) {
			broadByStation = paraDtlService.get(broadByStationId);
		}
		if (!(broadByFlag != null && !"".equals(broadByFlag))) {
			broadByFlag = null;
		}
		broadByTimeService.get(carrier, operationName, start, end, broadByReason, broadByStation, broadByFlag);
		return GRID;
	}

	@Override
	protected void beforeUpdate(BroadByTime model) {
		model.setOperation(null);
		model.setBroadByReason(null);
		model.setBroadByStation(null);
	}

	@Override
	protected void preSave(BroadByTime model) {
		String empName = getCurUser().getName();
		model.setEmpName(empName);
		if (model.getId() != null) {
			List<DutyRecord> dutyRecords = dutyRecordService.getInfluenceRecords(model.getId());
			for (DutyRecord dutyRecord : dutyRecords) {
				dutyRecord.setContent(dutyRecord.getContent() + " (本条代播信息由" + empName + "在"
						+ DateUtil.getDateCNHMStr(getCurDate()) + "进行了修改)");
			}
		}
	}

	@Override
	public String delete() {
		JSONArray array = JSONArray.fromObject(carrier.getDelIds());
		String msg = null;
		String finalMsg = "";
		for (int i = 0; i < array.size(); i++) {
			Long delId = ((JSONObject) array.get(i)).getLong("id");
			BroadByTime bbt = broadByTimeService.get(delId);
			msg = this.judgeReportAnInterval(bbt.getStartTime(), bbt.getEndTime());
			if (msg == null || msg.equals("")) {
				List<DutyRecord> dutyRecords = dutyRecordService.getInfluenceRecords(delId);
				for (DutyRecord dutyRecord : dutyRecords) {
					((DutyRecordD) dutyRecord).setBroadByTime(null);
					dutyRecord.setContent(dutyRecord.getContent() + "(本条代播信息由" + getCurUser().getName() + "在"
							+ DateUtil.getDateCNHMStr(getCurDate()) + "删除)");
				}
				bbt.setDelFlag("1");
				broadByTimeService.save(bbt);
			} else {
				finalMsg += "第" + (i + 1) + "条代播信息由于" + msg;
				break;
			}
		}
		JSONObject obj = new JSONObject();
		if (msg != null && !msg.equals("")) {
			obj.put("result", false);
			obj.put("msg", msg);
		} else {
			obj.put("result", true);
		}
		json = obj.toString();
		return EASY;
	}

	public String validateTimeAjax() {
		JSONObject obj = new JSONObject();
		Date startTime = null;
		Date endTime = null;
		if (startDate != null && !startDate.equals("")) {
			startTime = DateUtil.createDateTime(startDate);
		}
		//如果结束时间为空，则认为当前月的上个月为结束时间
		if (endDate != null && !endDate.equals("")) {
			endTime = DateUtil.createDateTime(endDate);
		}
		BroadByStation broadByStation = (BroadByStation) paraDtlService.get(broadByStationId);

		List<String> accdList = reptService.getAccdReptTimeArrayForSave(startTime, endTime, id, broadByFlag,
				broadByStation);
		msg = getJudgeAccdMsg(accdList);

		List<ImptPeriod> imptList = imptPeriodService.get(startTime, endTime, id, broadByFlag, broadByStation);
		msg += getJudgeImptMsg(imptList);
		obj.put("result", true);
		if (!msg.equals("")) {
			obj.put("msg", msg);
		}
		json = obj.toString();
		return EASY;
	}

	//判断这条代播信息是否影响到事故报表或保证其报表的上报,如果影响了则不让编辑
	private String judgeReportAnInterval(Date startTime, Date endTime) {
		List<String> accdReptTimeArray = reptService.getAccdReptTimeArray(startTime, endTime);
		String msg = getJudgeAccdMsg(accdReptTimeArray);
		//判断这条代播信息是否影响到保证期报表
		List<ImptPeriod> imptReptTimeArray = imptPeriodService.get(startTime, startTime);
		msg += getJudgeImptMsg(imptReptTimeArray);
		return msg;
	}

	private String getJudgeImptMsg(List<ImptPeriod> imptReptTimeArray) {
		String msg = "";
		for (ImptPeriod imptPeriod : imptReptTimeArray) {
			ProcessInstance piImpt = workFlowService.getProcessInstance("reptImpt", imptPeriod.getId() + "");
			String imptReptName = imptPeriod.getImptPeriodName();
			if (piImpt == null) {
				//流程尚未开始，可以编辑
			} else {
				//如果流程已经开始
				if (piImpt.hasEnded()) {
					//如果流程已经结束，则不能编辑
					msg += imptReptName + "保证期报表已结束，请驳回技办再编辑本条代播信息!";
				} else {
					//如果流程尚未结束
					TaskInstance tiImpt = workFlowService.findActiveTaskInstance(piImpt);//肯定不为空
					if (tiImpt.getName().equals("技办整合上报") || tiImpt.getName().equals("技办重新上报")) {
						//处于技办节点时可以编辑
					} else if (tiImpt.getName().equals("技术主管审核")) {
						msg += imptReptName + "保证期报表处于技术主管审核节点，请驳回技办再编辑本条代播信息!";
					} else if (tiImpt.getName().equals("单位主管审核")) {
						msg += imptReptName + "保证期报表处于单位主管审核节点，请驳回技办再编辑本条代播信息!";
					}
				}
			}
		}
		return msg;
	}

	private String getJudgeAccdMsg(List<String> accdReptTimeArray) {
		String msg = "";
		for (String reptTime : accdReptTimeArray) {
			//首先判断这条代播信息是否影响到事故报表
			ProcessInstance piAccd = workFlowService.getProcessInstance("reptAccd", reptTime);
			if (piAccd == null) {
				//流程尚未开始，可以编辑
			} else {
				//如果流程已经开始
				if (piAccd.hasEnded()) {
					//如果流程已经结束，则不能编辑
					msg += reptTime + "事故报表已结束，请驳回技办再编辑本条代播信息!";
				} else {
					//如果流程尚未结束
					TaskInstance tiAccd = workFlowService.findActiveTaskInstance(piAccd);//肯定不为空
					if (tiAccd.getName().equals("技办整合上报") || tiAccd.getName().equals("技办重新上报")) {
						//处于技办节点时可以编辑
					} else if (tiAccd.getName().equals("技术主管审核")) {
						msg += reptTime + "事故报表处于技术主管审核节点，请驳回技办再编辑本条代播信息!";
					} else if (tiAccd.getName().equals("单位主管审核")) {
						msg += reptTime + "事故报表处于单位主管审核节点，请驳回技办再编辑本条代播信息!";
					}
				}
			}
		}
		return msg;
	}

	private BroadByTimeService broadByTimeService;
	private ParaDtlService paraDtlService;
	private DutyRecordService dutyRecordService;
	private ReptService reptService;
	private ImptPeriodService imptPeriodService;

	@Autowired
	public void setBroadByTimeService(BroadByTimeService broadByTimeService) {
		this.broadByTimeService = broadByTimeService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setDutyRecordService(DutyRecordService dutyRecordService) {
		this.dutyRecordService = dutyRecordService;
	}

	@Autowired
	public void setReptService(ReptService reptService) {
		this.reptService = reptService;
	}

	@Autowired
	public void setImptPeriodService(ImptPeriodService imptPeriodService) {
		this.imptPeriodService = imptPeriodService;
	}

	public List<BroadByTime> getBroadByTimes() {
		return broadByTimes;
	}

	public void setBroadByTimes(List<BroadByTime> broadByTimes) {
		this.broadByTimes = broadByTimes;
	}

	public List<ParaDtl> getBroadByReasons() {
		return broadByReasons;
	}

	public void setBroadByReasons(List<ParaDtl> broadByReasons) {
		this.broadByReasons = broadByReasons;
	}

	public List<ParaDtl> getBroadByStations() {
		return broadByStations;
	}

	public void setBroadByStations(List<ParaDtl> broadByStations) {
		this.broadByStations = broadByStations;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Long getBroadByReasonId() {
		return broadByReasonId;
	}

	public void setBroadByReasonId(Long broadByReasonId) {
		this.broadByReasonId = broadByReasonId;
	}

	public String getOpName() {
		return opName;
	}

	public void setOpName(String opName) {
		this.opName = opName;
	}

	public Long getBroadByStationId() {
		return broadByStationId;
	}

	public void setBroadByStationId(Long broadByStationId) {
		this.broadByStationId = broadByStationId;
	}

	public List<ParaDtl> getTransTypes() {
		return transTypes;
	}

	public void setTransTypes(List<ParaDtl> transTypes) {
		this.transTypes = transTypes;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public BroadByTime getBroadByTime() {
		return broadByTime;
	}

	public void setBroadByTime(BroadByTime broadByTime) {
		this.broadByTime = broadByTime;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBroadByFlag() {
		return broadByFlag;
	}

	public void setBroadByFlag(String broadByFlag) {
		this.broadByFlag = broadByFlag;
	}

}
