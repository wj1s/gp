package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//异态影响的业务对应信息
@Entity
@Table(name = "TB_ETMS_ACCD_ABN_OPERATION")
@DiscriminatorColumn(name = "ACCD_FLAG", discriminatorType = DiscriminatorType.STRING, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class AbnOperation extends IdEntity implements Jsonable {
	private Abnormal abnormal;//异态
	private Operation operation;//影响设备
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer sortby;//顺序

	@Override
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("startTime", DateUtil.getDateTimeStr(this.getStartTime()));
		jsonObject.put("endTime", DateUtil.getDateTimeStr(this.getEndTime()));
		jsonObject.put("empName", DateUtil.getDateTimeStr(this.getEndTime()));
		jsonObject.put("operation", operation != null ? operation.getJsonObject() : "");
		return jsonObject;
	}

	@Transient
	public long getShutTime() {
		return (endTime.getTime() - startTime.getTime()) / 1000;
	}

	@Transient
	public String getShutTimeStr() {
		return DateUtil.getTimeHMSstr(this.getShutTime());
	}

	@ManyToOne
	@JoinColumn(name = "ABN_ID", nullable = false)
	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public AbnOperation() {
		super();
	}

	public AbnOperation(Long id) {
		this.id = id;
	}

	public AbnOperation(Operation operation, Date startTime, Date endTime, Integer sortby) {
		super();
		this.operation = operation;
		this.startTime = startTime;
		this.endTime = endTime;
		this.sortby = sortby;
	}

	@ManyToOne
	@JoinColumn(name = "OP_ID", nullable = false)
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "SORTBY", nullable = false)
	public Integer getSortby() {
		return sortby;
	}

	public void setSortby(Integer sortby) {
		this.sortby = sortby;
	}
}
