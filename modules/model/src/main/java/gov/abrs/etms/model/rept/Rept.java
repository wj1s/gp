package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

//报表详细
@Entity
@Table(name = "TB_ETMS_REPT_DTL")
public class Rept extends IdEntity {

	private ReptDef reptDef;//报表代码
	private String reptTime;//报表时间
	private Date updDate;
	//用来记录3个审核人
	private String tekOfficer;//技办
	private String officer;//总工
	private String governor;//台长

	public Rept() {}

	public Rept(ReptDef reptDef, String reptTime) {
		this.reptDef = reptDef;
		this.reptTime = reptTime;
	}

	@Transient
	public String getReportViewUrl() {
		return "report/rept!rqMonthRept.blank?id=" + this.getId();
	}

	@ManyToOne()
	@JoinColumn(name = "REPT_ID", nullable = false)
	public ReptDef getReptDef() {
		return reptDef;
	}

	public void setReptDef(ReptDef reptDef) {
		this.reptDef = reptDef;
	}

	@Column(name = "REPT_TIME", nullable = false, length = 6)
	public String getReptTime() {
		return this.reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	@Column(name = "TEKOFFICER", length = 20)
	public String getTekOfficer() {
		return tekOfficer;
	}

	public void setTekOfficer(String tekOfficer) {
		this.tekOfficer = tekOfficer;
	}

	@Column(name = "OFFICER", length = 20)
	public String getOfficer() {
		return officer;
	}

	public void setOfficer(String officer) {
		this.officer = officer;
	}

	@Column(name = "GOVERNOR", length = 20)
	public String getGovernor() {
		return governor;
	}

	public void setGovernor(String governor) {
		this.governor = governor;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

}