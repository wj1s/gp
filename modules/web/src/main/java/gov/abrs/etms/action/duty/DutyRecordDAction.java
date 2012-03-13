package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.duty.DutyRecordD;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.report.BroadByTimeService;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyRecordDAction extends GridAction<DutyRecordD> {

	private static final long serialVersionUID = 7757318924758653462L;
	private Long opId;
	private Long reasonId;//原因id

	@Override
	public void prepareSave() {
		if (id != null) {
			model = (DutyRecordD) dutyRecordService.get(id);
		} else {
			model = new DutyRecordD();
		}
	}

	@Override
	public String save() {
		Date now = getCurDate();
		model.setStartTime(now);
		BroadByTime bbtForSave = null;
		Long bbtId = model.getBroadByTime().getId();
		BroadByTime broadByTime = model.getBroadByTime();
		if (model.getId() == null) {
			//新增情况
			if (bbtId != null) {
				//编辑已经存在的未结束的代播记录
				bbtForSave = broadByTimeService.get(bbtId);
			} else {
				//新增一个代播记录
				bbtForSave = model.getBroadByTime();
			}

		} else {
			//编辑的情况
			bbtForSave = broadByTimeService.get(bbtId);
		}
		bbtForSave.setOperation(this.operationService.get(opId));
		bbtForSave.setEmpName(this.getCurUser().getName());
		bbtForSave.setBroadByReason((BroadByReason) paraDtlService.get(reasonId));
		bbtForSave.setBroadByStation((BroadByStation) paraDtlService.get(broadByTime.getBroadByStation().getId()));
		String broadTypeStr = "";
		if (broadByTime.getBroadByFlag().equals("D")) {
			broadTypeStr = "代播";
		} else {
			broadTypeStr = "被代";
		}
		String autoStr = "";
		if (broadByTime.getAutoFlag()) {
			bbtForSave.setAutoFlag(true);
			autoStr += "自动";
		} else {
			bbtForSave.setAutoFlag(false);
			autoStr += "手动";
		}
		String contentStr = "业务" + bbtForSave.getOperation().getName() + "由于"
				+ bbtForSave.getBroadByReason().getCodeDesc() + "原因于";
		bbtForSave.setStartTime(broadByTime.getStartTime());
		if (broadByTime.getEndTime() == null) {
			contentStr += DateUtil.getDateCNHMSStr(broadByTime.getStartTime()) + "开始进行(" + autoStr + ")" + broadTypeStr;
		} else {
			bbtForSave.setEndTime(broadByTime.getEndTime());
			contentStr += DateUtil.getDateCNHMSStr(broadByTime.getStartTime()) + "到"
					+ DateUtil.getDateCNHMSStr(broadByTime.getEndTime()) + "进行了(" + autoStr + ")" + broadTypeStr;
		}
		contentStr += "。对方站名称为： " + bbtForSave.getBroadByStation().getCodeDesc() + "， 通知人是"
				+ broadByTime.getNotifyPerson() + "。";
		if (!"".equals(broadByTime.getBroadResult())) {
			bbtForSave.setBroadResult(broadByTime.getBroadResult());
			contentStr += "代播结果为：" + broadByTime.getBroadResult();
		}
		model.setContent(contentStr);
		model.setBroadByTime(bbtForSave);
		this.dutyRecordService.save(model);
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("id", model.getId());
		result.put("content", model.getContent());
		json = result.toString();
		return EASY;
	}

	private DutyRecordService dutyRecordService;
	private OperationService operationService;
	private ParaDtlService paraDtlService;
	private BroadByTimeService broadByTimeService;

	@Autowired
	public void setDutyRecordService(DutyRecordService dutyRecordService) {
		this.dutyRecordService = dutyRecordService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@Autowired
	public void setBroadByTimeService(BroadByTimeService broadByTimeService) {
		this.broadByTimeService = broadByTimeService;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getReasonId() {
		return reasonId;
	}

	public void setReasonId(Long reasonId) {
		this.reasonId = reasonId;
	}

}
