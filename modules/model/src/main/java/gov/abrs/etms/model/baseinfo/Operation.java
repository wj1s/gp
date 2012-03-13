package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.duty.DutyWarning;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.TransmitDef;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.model.rept.OpTime;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//业务
@Entity
@Table(name = "TB_ETMS_BASE_OPERATION")
@DiscriminatorColumn(name = "OPERATION_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Operation extends IdEntity implements Jsonable, AutoCompleteable {

	private String name;//业务名称
	private TransType transType;//传输类型
	private TransmitDef transmitDef;//业务传输方式

	private Double cawaveFrq;//载波频率
	private Double simbleRate;//符号率
	private Date startDate;//开始时间
	private Date endDate;//结束时间
	private String channalRate;//信道编码率
	private String rmks;//备注
	private Date updDate;//更新时间
	private String empName;//操作员
	private List<Schedule> schedules;//运行图
	private List<AbnOperation> abnOperations;//影响业务详细信息
	private List<DutyWarning> dutyWarnings;//业务在值班记录中的提醒

	private List<OpTime> OpTimes;//传输时间
	private List<BroadByTime> broadByTimes;//对应的代播被代信息

	//grid专用json
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());

		jsonObject
				.put("transmitDef.codeDesc", this.getTransmitDef() == null ? "" : this.getTransmitDef().getCodeDesc());
		jsonObject.put("transType.codeDesc", this.getTransType().getCodeDesc());

		jsonObject.put("cawaveFrq", this.getCawaveFrq());
		jsonObject.put("simbleRate", this.getSimbleRate());
		jsonObject.put("startDate", DateUtil.getDateStr(this.getStartDate()));
		jsonObject.put("endDate", DateUtil.getDateStr(this.getEndDate()));
		jsonObject.put("channalRate", this.getChannalRate());
		jsonObject.put("rmks", this.getRmks());
		jsonObject.put("updDate", DateUtil.getDateStr(this.getUpdDate()));
		jsonObject.put("empName", this.getEmpName());
		return jsonObject;
	}

	@Transient
	public String getAutoCompleteJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Override
	@Transient
	public List<Dept> getDeptsPopedom() {
		return this.getTransType().getDepts();
	}

	public Operation() {
		super();
	}

	public Operation(long id) {
		this.id = id;
	}

	public Operation(String name, TransmitDef transmitDef, TransType transType, Double cawaveFrq, Double simbleRate,
			Date startDate, Date updDate, String empName) {
		super();
		this.name = name;
		this.transmitDef = transmitDef;
		this.transType = transType;
		this.startDate = startDate;
		this.updDate = updDate;
		this.empName = empName;
		this.cawaveFrq = cawaveFrq;
		this.simbleRate = simbleRate;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "OP_NAME", length = 64, nullable = true, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "TRANS_MODE")
	public TransmitDef getTransmitDef() {
		return transmitDef;
	}

	public void setTransmitDef(TransmitDef transmitDef) {
		this.transmitDef = transmitDef;
	}

	@Column(name = "CAWAVE_FRQ", precision = 6)
	public Double getCawaveFrq() {
		return cawaveFrq;
	}

	public void setCawaveFrq(Double cawaveFrq) {
		this.cawaveFrq = cawaveFrq;
	}

	@Column(name = "SYMBLE_RATE", precision = 6)
	public Double getSimbleRate() {
		return simbleRate;
	}

	public void setSimbleRate(Double simbleRate) {
		this.simbleRate = simbleRate;
	}

	@Column(name = "START_DATE", nullable = false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", nullable = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "CHANNEL_RATE", nullable = true, length = 10)
	public String getChannalRate() {
		return channalRate;
	}

	public void setChannalRate(String channalRate) {
		this.channalRate = channalRate;
	}

	@Column(name = "RMKS", length = 128, nullable = true)
	public String getRmks() {
		return rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

	@Column(name = "EMP_NAME", length = 20, nullable = false)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@ManyToOne()
	@JoinColumn(name = "TRANS_TYPE", nullable = false)
	public TransType getTransType() {
		return transType;
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operation", fetch = FetchType.LAZY, targetEntity = Schedule.class)
	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "operation", fetch = FetchType.LAZY, targetEntity = AbnOperation.class)
	public List<AbnOperation> getAbnOperations() {
		return abnOperations;
	}

	public void setAbnOperations(List<AbnOperation> abnOperations) {
		this.abnOperations = abnOperations;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operation", fetch = FetchType.LAZY, targetEntity = DutyWarning.class)
	public List<DutyWarning> getDutyWarnings() {
		return dutyWarnings;
	}

	public void setDutyWarnings(List<DutyWarning> dutyWarnings) {
		this.dutyWarnings = dutyWarnings;
	}

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "operation", fetch = FetchType.LAZY, targetEntity = OpTime.class)
	public List<OpTime> getOpTimes() {
		return OpTimes;
	}

	public void setOpTimes(List<OpTime> opTimes) {
		OpTimes = opTimes;
	}

	@OneToMany(cascade = CascadeType.REFRESH, mappedBy = "operation", fetch = FetchType.LAZY, targetEntity = BroadByTime.class)
	public List<BroadByTime> getBroadByTimes() {
		return broadByTimes;
	}

	public void setBroadByTimes(List<BroadByTime> broadByTimes) {
		this.broadByTimes = broadByTimes;
	}

}
