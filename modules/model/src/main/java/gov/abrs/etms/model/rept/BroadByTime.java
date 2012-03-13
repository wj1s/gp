package gov.abrs.etms.model.rept;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//代播
@Entity
@Table(name = "TB_ETMS_REPT_BROAD_BY_TIME")
public class BroadByTime extends IdEntity implements Jsonable {

	private Operation operation; //业务序列
	private BroadByReason broadByReason; //代播原因
	private Boolean autoFlag; //是否自动代播
	private String notifyPerson; //通知人
	private String empName; //操作员
	private Date startTime; //代播开始时间
	private Date endTime; //代播结束时间
	private String broadByFlag; //代播被代标志
	private BroadByStation broadByStation; //对方站代码
	private String broadResult; //代播结果
	private String notified;//被通知人
	private String rmks;//评论
	private String delFlag = "0";//删除标志，有默认值
	private String otherReason;//其他原因

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("id", getId());
		obj.put("operation.name", getOperation().getName());
		obj.put("startTime", DateUtil.getDateTimeStr(getStartTime()));
		obj.put("endTime", DateUtil.getDateTimeStr(getEndTime()));
		obj.put("braodByFlagStr", getBroadByFlag().equals("D") ? "代播" : "被代");
		obj.put("broadByStation.codeDesc", getBroadByStation().getCodeDesc());
		obj.put("broadByReason.codeDesc", getBroadByReason().getCodeDesc().equals("其他") ? getOtherReason()
				: getBroadByReason().getCodeDesc());
		obj.put("operation.id", getOperation().getId());
		obj.put("autoFlag", getAutoFlag());
		obj.put("broadResult", getBroadResult());
		obj.put("operation.transType.id", getOperation().getTransType().getId());
		obj.put("notifyPerson", getNotifyPerson());
		obj.put("autoFlagStr", getAutoFlag() ? "自动" : "手动");
		obj.put("notified", getNotified());
		obj.put("rmks", getRmks() == null ? "" : getRmks());
		return obj.toString();
	}

	public BroadByTime() {}

	public BroadByTime(Long id) {
		this.id = id;
	}

	public BroadByTime(Operation operation, BroadByReason broadByReason, Boolean autoFlag, String notifyPerson,
			String empName, Date startTime, Date endTime, String broadByFlag, BroadByStation broadByStation,
			String broadResult, String notified, String delFlag) {
		this.operation = operation;
		this.broadByReason = broadByReason;
		this.autoFlag = autoFlag;
		this.notifyPerson = notifyPerson;
		this.empName = empName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.broadByFlag = broadByFlag;
		this.broadByStation = broadByStation;
		this.broadResult = broadResult;
		this.notified = notified;
		this.delFlag = delFlag;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OP_ID")
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Column(name = "AUTO_FLAG", length = 1)
	public Boolean getAutoFlag() {
		return autoFlag;
	}

	public void setAutoFlag(Boolean autoFlag) {
		this.autoFlag = autoFlag;
	}

	@Column(name = "NOTIFY_PERSON", length = 64)
	public String getNotifyPerson() {
		return notifyPerson;
	}

	public void setNotifyPerson(String notifyPerson) {
		this.notifyPerson = notifyPerson;
	}

	@Column(name = "EMP_NAME", length = 20)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = true)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "BROAD_BY_FLAG", length = 1)
	public String getBroadByFlag() {
		return broadByFlag;
	}

	public void setBroadByFlag(String broadByFlag) {
		this.broadByFlag = broadByFlag;
	}

	@Column(name = "BROAD_RESULT", length = 512)
	public String getBroadResult() {
		return broadResult;
	}

	public void setBroadResult(String broadResult) {
		this.broadResult = broadResult;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BROAD_REASON")
	public BroadByReason getBroadByReason() {
		return broadByReason;
	}

	public void setBroadByReason(BroadByReason broadByReason) {
		this.broadByReason = broadByReason;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATION")
	public BroadByStation getBroadByStation() {
		return broadByStation;
	}

	public void setBroadByStation(BroadByStation broadByStation) {
		this.broadByStation = broadByStation;
	}

	@Column(name = "NOTIFIED", length = 64, nullable = false)
	public String getNotified() {
		return notified;
	}

	public void setNotified(String notified) {
		this.notified = notified;
	}

	@Column(name = "RMKS", length = 512, nullable = true)
	public String getRmks() {
		return rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

	@Column(name = "DEL_FLAG", length = 1, nullable = false)
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	@Column(name = "OTHER_REASON", length = 64, nullable = true)
	public String getOtherReason() {
		return otherReason;
	}

	public void setOtherReason(String otherReason) {
		this.otherReason = otherReason;
	}

}
