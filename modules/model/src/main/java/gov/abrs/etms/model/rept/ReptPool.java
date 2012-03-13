package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ETMS_REPT_LIST")
@DiscriminatorColumn(name = "REPT_TYPE", discriminatorType = DiscriminatorType.STRING, length = 4)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ReptPool extends IdEntity {

	private String name;//报告名称
	private String reptTime;//报表时段
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private Date repStartTime;//开始上报时间
	private Date submitTime;//上报结束日期
	private Date promtTime;//提示时间
	private Date updDate;//修改时间
	private String updName;//修改人员
	private Boolean reptFlag;//上报标志 
	private Long reptSeq;

	@Column(name = "REPT_NAME", nullable = false, length = 64)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "REPT_TIME", nullable = true, length = 6)
	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	@Column(name = "START_DATE", nullable = false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", nullable = false)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "REP_START_TIME", nullable = true)
	public Date getRepStartTime() {
		return repStartTime;
	}

	public void setRepStartTime(Date repStartTime) {
		this.repStartTime = repStartTime;
	}

	@Column(name = "SUBMIT_DATE", nullable = false)
	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Column(name = "PROMT_TIME", nullable = false)
	public Date getPromtTime() {
		return promtTime;
	}

	public void setPromtTime(Date promtTime) {
		this.promtTime = promtTime;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "UPD_EMP", nullable = false, length = 50)
	public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	@Column(name = "REPT_FLAG", nullable = true, length = 1)
	public Boolean getReptFlag() {
		return reptFlag;
	}

	public void setReptFlag(Boolean reptFlag) {
		this.reptFlag = reptFlag;
	}

	@Column(name = "SEQ", nullable = false)
	public Long getReptSeq() {
		return reptSeq;
	}

	public void setReptSeq(Long reptSeq) {
		this.reptSeq = reptSeq;
	}
}
