package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.AccdDuty;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//由异态引起的停传业务的定性信息
@Entity
@Table(name = "TB_ETMS_ACCD_DUTY_TIME")
public class AccdDutyTime extends IdEntity implements Jsonable {
	private AbnOperationA abnOperationA;
	private AccdDuty accdDuty;
	private Integer dutyTime;
	private Integer sortby;//顺序

	public AccdDutyTime() {
		super();
	}

	public AccdDutyTime(AbnOperationA abnOperationA, AccdDuty accdDuty, Integer dutyTime) {
		super();
		this.abnOperationA = abnOperationA;
		this.accdDuty = accdDuty;
		this.dutyTime = dutyTime;
	}

	@Override
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("accdDuty", accdDuty.getJsonObject());
		jsonObject.put("dutyTime", dutyTime);
		jsonObject.put("dutyTimeStr", this.getDutyTimeStr());
		return jsonObject;
	}

	@Transient
	public String getDutyTimeStr() {
		return DateUtil.getTimeHMSstr(new Long(dutyTime));
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ABN_OP_ID", nullable = false)
	public AbnOperationA getAbnOperationA() {
		return abnOperationA;
	}

	public void setAbnOperationA(AbnOperationA abnOperationA) {
		this.abnOperationA = abnOperationA;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DUTY_CODE", nullable = false)
	public AccdDuty getAccdDuty() {
		return accdDuty;
	}

	public void setAccdDuty(AccdDuty accdDuty) {
		this.accdDuty = accdDuty;
	}

	@Column(name = "DUTY_TIME", nullable = false)
	public Integer getDutyTime() {
		return this.dutyTime;
	}

	public void setDutyTime(Integer dutyTime) {
		this.dutyTime = dutyTime;
	}

	@Column(name = "SORTBY", nullable = false)
	public Integer getSortby() {
		return sortby;
	}

	public void setSortby(Integer sortby) {
		this.sortby = sortby;
	}
}