package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.TransmitDef;
import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//传输时间
@Entity
@Table(name = "TB_ETMS_REPT_OP_TIME")
public class OpTime extends IdEntity {

	private Operation operation;//业务
	private String reptTime;//年月
	private TransType transType;//传输类型
	private TransmitDef transmitDef;//传输方式
	private Double broadTime;//传输时间
	private Date updDate;//更新日期
	private String empName;//更新人员

	public OpTime() {}

	public OpTime(Operation operation, String reptTime, TransType transType, TransmitDef transmitDef,
			Double broadTime, Date updDate, String empName) {
		this.operation = operation;
		this.reptTime = reptTime;
		this.transType = transType;
		this.transmitDef = transmitDef;
		this.broadTime = broadTime;
		this.updDate = updDate;
		this.empName = empName;
	}

	@ManyToOne()
	@JoinColumn(name = "OP_ID", nullable = false)
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@ManyToOne()
	@JoinColumn(name = "TRANS_TYPE", nullable = false)
	public TransType getTransType() {
		return transType;
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
	}

	@ManyToOne()
	@JoinColumn(name = "TRANS_MODE", nullable = true)
	public TransmitDef getTransmitDef() {
		return transmitDef;
	}

	public void setTransmitDef(TransmitDef transmitDef) {
		this.transmitDef = transmitDef;
	}

	@Column(name = "BROAD_TIME", nullable = false)
	public Double getBroadTime() {
		return broadTime;
	}

	public void setBroadTime(Double broadTime) {
		this.broadTime = broadTime;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "EMP_NAME", length = 20)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "REPT_TIME", length = 6)
	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

}
