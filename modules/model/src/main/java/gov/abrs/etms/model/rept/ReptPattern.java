package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * TbEtmsReptPattern entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPT_PATTERN")
public class ReptPattern extends IdEntity {

	// Fields    
	private long seq;
	private String patternName;
	private List<ReptTechDtl> reptTechDtl = new ArrayList<ReptTechDtl>();

	// Constructors

	/** default constructor */
	public ReptPattern() {}

	/** minimal constructor */
	public ReptPattern(long id, String patternName) {
		this.id = id;
		this.patternName = patternName;
	}

	/** full constructor */
	public ReptPattern(long id, long seq, String patternName, List<ReptTechDtl> reptTechDtl) {
		this.id = id;
		this.seq = seq;
		this.patternName = patternName;
		this.reptTechDtl = reptTechDtl;
	}

	// Property accessors
	@Column(name = "SEQ")
	public long getSeq() {
		return this.seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Column(name = "PATTERN_NAME", nullable = false, length = 50)
	public String getPatternName() {
		return this.patternName;
	}

	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "reptPattern")
	public List<ReptTechDtl> getReptTechDtl() {
		return this.reptTechDtl;
	}

	public void setReptTechDtl(List<ReptTechDtl> reptTechDtl) {
		this.reptTechDtl = reptTechDtl;
	}

}