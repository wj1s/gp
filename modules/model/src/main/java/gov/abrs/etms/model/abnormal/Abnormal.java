package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.AbnType;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;

//异态信息父类
@Entity
@Table(name = "TB_ETMS_ACCD_ABNORMAL")
@DiscriminatorColumn(name = "ABNORMAL_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Abnormal extends IdEntity implements Jsonable {

	private TransType transType;//传输类型
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private AbnType abnType;//异态类型
	private String desc;//异态现象
	private String reason;//异态原因
	private String processStep;//处理措施
	private Date updDate;//修改时间
	private String empName;//操作员
	private List<AbnOperation> abnOperations = new ArrayList<AbnOperation>();//影响业务
	//事故信息
	private String accdCode;//事故编号
	private String accdDesc;//事故描述
	private String accdReason;//事故编号
	private String accdPrevWay;//事故预防措施

	public Abnormal() {}

	public Abnormal(TransType transType, Date startTime, Date endTime, AbnType abnType, String desc, String reason,
			String processStep, Date updDate, String empName, List<AbnOperation> abnOperations) {
		super();
		this.transType = transType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.abnType = abnType;
		this.desc = desc;
		this.reason = reason;
		this.processStep = processStep;
		this.updDate = updDate;
		this.empName = empName;
		this.abnOperations = abnOperations;
	}

	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("accdCode", accdCode);
		jsonObject.put("accdReason", accdReason);
		jsonObject.put("accdDesc", accdDesc);
		jsonObject.put("transType", transType.getCodeDesc());
		jsonObject.put("accdPrevWay", accdPrevWay);
		jsonObject.put("abnType", abnType.getCodeDesc());
		jsonObject.put("startTime", DateUtil.dateToString(startTime, DateUtil.FORMAT_DAYTIME));
		jsonObject.put("endTime", DateUtil.dateToString(endTime, DateUtil.FORMAT_DAYTIME));
		jsonObject.put("shutTimeSumStr", this.getShutTimeSumStr());
		jsonObject.put("processTimeSumStr", this.getProcessTimeSumStr());
		jsonObject.put("desc", desc);
		jsonObject.put("reason", reason);
		jsonObject.put("processStep", processStep);
		jsonObject.put("updDate", DateUtil.getDateStr(updDate));
		jsonObject.put("empName", empName);
		JSONArray abnOperations = new JSONArray();
		for (AbnOperation abnOperation : this.abnOperations) {
			abnOperations.add(abnOperation.getJsonObject());
		}
		jsonObject.put("abnOperations", abnOperations.toString());
		return jsonObject;
	}

	@Transient
	public List<AbnOperationA> getAbnOperationAs() {
		List<AbnOperationA> res = new ArrayList<AbnOperationA>();
		for (AbnOperation abnOperation : abnOperations) {
			if (abnOperation instanceof AbnOperationA) {
				res.add((AbnOperationA) abnOperation);
			}
		}
		return res;
	}

	// 获取事故总的停播时长，查询方法
	@Transient
	public long getShutTimeSum() {
		long res = 0;
		for (AbnOperationA abnOperationA : this.getAbnOperationAs()) {
			res += abnOperationA.getShutTime();
		}
		return res;
	}

	// 获取事故总的处理时长，查询方法
	@Transient
	public String getShutTimeSumStr() {
		return DateUtil.getTimeHMSstr(this.getShutTimeSum());
	}

	// 获取事故总的处理时长，查询方法
	@Transient
	public long getProcessTimeSum() {
		Date endTime = this.getEndTime();
		Date startTime = this.getStartTime();
		long res = 0;
		if (endTime != null && startTime != null) {
			res = (endTime.getTime() - startTime.getTime()) / 1000;
		}
		return res;
	}

	// 获取事故总的处理时长，查询方法
	@Transient
	public String getProcessTimeSumStr() {
		return DateUtil.getTimeHMSstr(this.getProcessTimeSum());
	}

	@Transient
	public String getAccdPrevWayStr() {
		return accdPrevWay == null ? null : accdPrevWay.replaceAll("[\\r]?[\\n]", "<br/>");
	}

	@Transient
	public String getAccdDescStr() {
		return accdDesc == null ? null : accdDesc.replaceAll("[\\r]?[\\n]", "<br/>");
	}

	@Transient
	public String getAccdReasonStr() {
		return accdReason == null ? null : accdReason.replaceAll("[\\r]?[\\n]", "<br/>");
	}

	@ManyToOne()
	@JoinColumn(name = "TRANS_TYPE", nullable = false)
	public TransType getTransType() {
		return transType;
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
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

	@ManyToOne()
	@JoinColumn(name = "ABN_TYPE", nullable = false)
	public AbnType getAbnType() {
		return abnType;
	}

	public void setAbnType(AbnType abnType) {
		this.abnType = abnType;
	}

	@Column(name = "ABN_DESC", nullable = false, length = 512)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Column(name = "ABN_REASON", nullable = false, length = 512)
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Column(name = "PROCESS_STEP", nullable = false, length = 512)
	public String getProcessStep() {
		return processStep;
	}

	public void setProcessStep(String processStep) {
		this.processStep = processStep;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "EMP_NAME", nullable = false, length = 20)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "abnormal")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OrderBy(value = "sortby")
	public List<AbnOperation> getAbnOperations() {
		return abnOperations;
	}

	public void setAbnOperations(List<AbnOperation> abnOperations) {
		this.abnOperations = abnOperations;
	}

	@Column(name = "ACCD_CODE", nullable = true, length = 20)
	public String getAccdCode() {
		return accdCode;
	}

	public void setAccdCode(String accdCode) {
		this.accdCode = accdCode;
	}

	@Column(name = "PREV_WAY", nullable = true, length = 2048)
	public String getAccdPrevWay() {
		return accdPrevWay;
	}

	public void setAccdPrevWay(String accdPrevWay) {
		this.accdPrevWay = accdPrevWay;
	}

	@Column(name = "ACCD_DESC", nullable = true, length = 2048)
	public String getAccdDesc() {
		return accdDesc;
	}

	public void setAccdDesc(String accdDesc) {
		this.accdDesc = accdDesc;
	}

	@Column(name = "ACCD_REASON", nullable = true, length = 2048)
	public String getAccdReason() {
		return accdReason;
	}

	public void setAccdReason(String accdReason) {
		this.accdReason = accdReason;
	}
}
