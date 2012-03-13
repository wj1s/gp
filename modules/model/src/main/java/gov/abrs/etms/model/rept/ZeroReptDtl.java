package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ETMS_REPT_ZERO_DTL")
public class ZeroReptDtl extends IdEntity {

	private ZeroReptDef zeroReptDef;//零报告定义
	private ZeroRmks zeroRmks;//零报告评论
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Date repEndTime;//上报结束时间
	private Long dtlSeq;

	//用来记录3个审核人
	private String tekOfficer;//技办
	private String officer;//总工
	private String governor;//台长

	@ManyToOne()
	@JoinColumn(name = "ZERO_ID", nullable = false)
	public ZeroReptDef getZeroReptDef() {
		return zeroReptDef;
	}

	public void setZeroReptDef(ZeroReptDef zeroReptDef) {
		this.zeroReptDef = zeroReptDef;
	}

	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "REP_END_TIME", nullable = false)
	public Date getRepEndTime() {
		return this.repEndTime;
	}

	public void setRepEndTime(Date repEndTime) {
		this.repEndTime = repEndTime;
	}

	@OneToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "zeroReptDtl")
	public ZeroRmks getZeroRmks() {
		return zeroRmks;
	}

	public void setZeroRmks(ZeroRmks zeroRmks) {
		this.zeroRmks = zeroRmks;
	}

	@Column(name = "TEKOFFICER", nullable = true, length = 20)
	public String getTekOfficer() {
		return tekOfficer;
	}

	public void setTekOfficer(String tekOfficer) {
		this.tekOfficer = tekOfficer;
	}

	@Column(name = "OFFICER", nullable = true, length = 20)
	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	@Column(name = "GOVERNOR", nullable = true, length = 20)
	public String getGovernor() {
		return governor;
	}

	public void setGovernor(String governor) {
		this.governor = governor;
	}

	@Column(name = "SEQ", nullable = false)
	public Long getDtlSeq() {
		return dtlSeq;
	}

	public void setDtlSeq(Long dtlSeq) {
		this.dtlSeq = dtlSeq;
	}

}
