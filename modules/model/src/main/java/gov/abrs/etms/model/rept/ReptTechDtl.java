package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TbEtmsReptTechDtl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPT_TECH_DTL")
public class ReptTechDtl extends IdEntity {

	// Fields    
	private ReptPattern reptPattern;
	private TechReptDef techReptDef;
	private long seq;
	private String attachName;
	private String uploadName;
	private String saveName;
	private String tekofficer;
	private String governor;
	private Date updDate;

	// Constructors

	/** default constructor */
	public ReptTechDtl() {}

	/** minimal constructor */
	public ReptTechDtl(long id, TechReptDef techReptDef, Date updDate) {
		this.id = id;
		this.techReptDef = techReptDef;
		this.updDate = updDate;
	}

	/** full constructor */
	public ReptTechDtl(long id, ReptPattern reptPattern, TechReptDef techReptDef, long seq, String attachName,
			String uploadName, String saveName, String tekofficer, String governor, Date updDate) {
		this.id = id;
		this.reptPattern = reptPattern;
		this.techReptDef = techReptDef;
		this.seq = seq;
		this.attachName = attachName;
		this.uploadName = uploadName;
		this.saveName = saveName;
		this.tekofficer = tekofficer;
		this.governor = governor;
		this.updDate = updDate;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PATTERN_ID")
	public ReptPattern getReptPattern() {
		return this.reptPattern;
	}

	public void setReptPattern(ReptPattern reptPattern) {
		this.reptPattern = reptPattern;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REPT_LIST_ID", nullable = false)
	public TechReptDef getTechReptDef() {
		return this.techReptDef;
	}

	public void setTechReptDef(TechReptDef techReptDef) {
		this.techReptDef = techReptDef;
	}

	@Column(name = "SEQ")
	public long getSeq() {
		return this.seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Column(name = "ATTACH_NAME", length = 50)
	public String getAttachName() {
		return this.attachName;
	}

	public void setAttachName(String attachName) {
		this.attachName = attachName;
	}

	@Column(name = "UPLOAD_NAME", length = 64)
	public String getUploadName() {
		return this.uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	@Column(name = "SAVE_NAME", length = 50)
	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	@Column(name = "TEKOFFICER", length = 20)
	public String getTekofficer() {
		return this.tekofficer;
	}

	public void setTekofficer(String tekofficer) {
		this.tekofficer = tekofficer;
	}

	@Column(name = "GOVERNOR", length = 20)
	public String getGovernor() {
		return this.governor;
	}

	public void setGovernor(String governor) {
		this.governor = governor;
	}

	@Column(name = "UPD_DATE", nullable = false, length = 23)
	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}