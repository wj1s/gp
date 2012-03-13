package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TbEtmsTechAccidentH entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_TECH_ACCIDENT_H")
public class TechAccidentH extends IdEntity implements java.io.Serializable {

	// Fields    
	private TechAccident techAccident;
	private String accdResult;//处理结果
	private String updName;//更新人
	private Date updDate;//更新时间

	// Constructors

	/** default constructor */
	public TechAccidentH() {}

	/** full constructor */
	public TechAccidentH(long id, TechAccident techAccident, String accdResult, String updName, Date updDate) {
		this.id = id;
		this.techAccident = techAccident;
		this.accdResult = accdResult;
		this.updName = updName;
		this.updDate = updDate;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCD_ID", nullable = false)
	public TechAccident getTechAccident() {
		return this.techAccident;
	}

	public void setTechAccident(TechAccident techAccident) {
		this.techAccident = techAccident;
	}

	@Column(name = "ACCD_RESULT", nullable = false, length = 2048)
	public String getAccdResult() {
		return this.accdResult;
	}

	public void setAccdResult(String accdResult) {
		this.accdResult = accdResult;
	}

	@Column(name = "UPD_NAME", nullable = false, length = 50)
	public String getUpdName() {
		return this.updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	@Column(name = "UPD_DATE", nullable = false, length = 23)
	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}