package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.WarnType;
import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//自动调度告警
@Entity
@Table(name = "TB_ETMS_DUTY_WARNING")
public class DutyWarning extends IdEntity {

	// Fields    
	private Operation operation; //业务
	private Date warnTime; //告警时间
	private WarnType warnType; //告警类型
	private String process; //处理情况
	private String analysis; //原因分析

	// Constructors

	/** default constructor */
	public DutyWarning() {}

	/** full constructor */
	public DutyWarning(Operation operation, Date warnTime, WarnType warnType, String process, String analysis) {
		this.operation = operation;
		this.warnTime = warnTime;
		this.warnType = warnType;
		this.process = process;
		this.analysis = analysis;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OP_ID")
	public Operation getOperation() {
		return this.operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@Column(name = "WARN_TIME", length = 23)
	public Date getWarnTime() {
		return this.warnTime;
	}

	public void setWarnTime(Date warnTime) {
		this.warnTime = warnTime;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WARN_TYPE")
	public WarnType getWarnType() {
		return warnType;
	}

	public void setWarnType(WarnType warnType) {
		this.warnType = warnType;
	}

	@Column(name = "PROCESS", length = 256)
	public String getProcess() {
		return this.process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	@Column(name = "ANALYSIS", length = 512)
	public String getAnalysis() {
		return this.analysis;
	}

	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
}