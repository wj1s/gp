package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.duty.DutyRecordW;
import gov.abrs.etms.model.duty.DutyWarning;
import gov.abrs.etms.model.para.WarnType;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyRecordWAction extends GridAction<DutyRecordW> {

	private static final long serialVersionUID = 2063985437276126519L;

	private Long opId;
	private Long wnTpId;

	@Override
	public String save() {
		Date now = getCurDate();
		model.setStartTime(now);
		DutyWarning dutyWarning = model.getDutyWarning();
		dutyWarning.setOperation(this.operationService.get(opId));
		dutyWarning.setWarnType((WarnType) this.paraDtlService.get(wnTpId));
		String contentStr = "业务" + dutyWarning.getOperation().getName() + "于"
				+ DateUtil.getDateCNHMStr(dutyWarning.getWarnTime()) + "告警。";
		contentStr += "告警类型为" + dutyWarning.getWarnType().getCodeDesc() + "，处理情况:" + dutyWarning.getProcess()
				+ " 原因分析: " + dutyWarning.getAnalysis();

		model.setContent(contentStr);
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

	@Autowired
	public void setDutyRecordService(DutyRecordService dutyRecordService) {
		this.dutyRecordService = dutyRecordService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	public Long getOpId() {
		return opId;
	}

	public void setOpId(Long opId) {
		this.opId = opId;
	}

	public Long getWnTpId() {
		return wnTpId;
	}

	public void setWnTpId(Long wnTpId) {
		this.wnTpId = wnTpId;
	}

}
