package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.duty.DutyRecord;
import gov.abrs.etms.model.duty.DutyRecordA;
import gov.abrs.etms.model.duty.DutyRecordD;
import gov.abrs.etms.model.duty.DutyRecordW;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.WarnType;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.duty.DutyRecordService;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyRecordAction extends GridAction<DutyRecord> {

	private static final long serialVersionUID = 5097762618234428387L;
	private Long dutyId;
	private List<Operation> operationList;
	private List<TransType> transTypeList;
	private List<ParaDtl> warnTypeList;
	private List<ParaDtl> broadByStationList;
	private List<ParaDtl> broadByReasonList;
	private DutyRecord dutyRecord;
	private Long opId;
	private Long wnTpId;

	public String toAdd() {
		transTypeList = this.getCurTransTypes(FunModule.DUTY);
		if (transTypeList != null && transTypeList.size() != 0) {
			operationList = this.operationService.get(transTypeList.get(0));
		}
		warnTypeList = this.paraDtlService.get(WarnType.class);
		broadByStationList = paraDtlService.get(BroadByStation.class);
		broadByReasonList = paraDtlService.get(BroadByReason.class);
		return "input";
	}

	public String toEdit() {
		dutyRecord = this.dutyRecordService.get(id);
		if (dutyRecord instanceof DutyRecordA) {
			//待添加
		} else if (dutyRecord instanceof DutyRecordD) {
			//考虑到代播信息有可能被删除
			if (((DutyRecordD) dutyRecord).getBroadByTime() != null) {
				transTypeList = this.getCurTransTypes(FunModule.DUTY);
				operationList = this.operationService.get(((DutyRecordD) dutyRecord).getBroadByTime().getOperation()
						.getTransType());
				broadByStationList = paraDtlService.get(BroadByStation.class);
				broadByReasonList = paraDtlService.get(BroadByReason.class);
			}
		} else if (dutyRecord instanceof DutyRecordW) {
			transTypeList = this.getCurUser().getDept().getTransTypes();
			operationList = this.operationService.get(((DutyRecordW) dutyRecord).getDutyWarning().getOperation()
					.getTransType());
			warnTypeList = this.paraDtlService.get(WarnType.class);
		}
		return "input";
	}

	@Override
	public String delete() {
		this.dutyRecordService.delete(id);
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("id", id);
		json = result.toString();
		return EASY;
	}

	private DutyRecordService dutyRecordService;
	private ParaDtlService paraDtlService;
	private OperationService operationService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@Autowired
	public void setDutyRecordService(DutyRecordService dutyRecordService) {
		this.dutyRecordService = dutyRecordService;
	}

	public Long getDutyId() {
		return dutyId;
	}

	public void setDutyId(Long dutyId) {
		this.dutyId = dutyId;
	}

	public List<Operation> getOperationList() {
		return operationList;
	}

	public void setOperationList(List<Operation> operationList) {
		this.operationList = operationList;
	}

	public List<ParaDtl> getWarnTypeList() {
		return warnTypeList;
	}

	public void setWarnTypeList(List<ParaDtl> warnTypeList) {
		this.warnTypeList = warnTypeList;
	}

	public void setDutyRecord(DutyRecord dutyRecord) {
		this.dutyRecord = dutyRecord;
	}

	public DutyRecord getDutyRecord() {
		return dutyRecord;
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

	public List<TransType> getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(List<TransType> transTypeList) {
		this.transTypeList = transTypeList;
	}

	public List<ParaDtl> getBroadByStationList() {
		return broadByStationList;
	}

	public void setBroadByStationList(List<ParaDtl> broadByStationList) {
		this.broadByStationList = broadByStationList;
	}

	public List<ParaDtl> getBroadByReasonList() {
		return broadByReasonList;
	}

	public void setBroadByReasonList(List<ParaDtl> broadByReasonList) {
		this.broadByReasonList = broadByReasonList;
	}

}
