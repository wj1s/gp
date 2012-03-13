package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ETMS_REPT_IMPT_PERIOD")
public class ImptPeriod extends IdEntity {

	private String imptPeriodName;//重要保证期名称
	private String year;//年
	private Date startDate;//开始日期
	private Date endDate;//结束日期
	private Date submitDate;//上报日期
	private Date updDate;//更新日期
	private String empName;//更新人员

	@Column(name = "IMPT_PERIOD_NAME", length = 40)
	public String getImptPeriodName() {
		return imptPeriodName;
	}

	public void setImptPeriodName(String imptPeriodName) {
		this.imptPeriodName = imptPeriodName;
	}

	@Column(name = "YEAR", length = 4)
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	@Column(name = "START_DATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "SUBMIT_DATE")
	public Date getSubmitDate() {
		return submitDate;
	}

	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}

	@Column(name = "UPD_DATE")
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
}
