package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//留守值班人员巡视时间
@Entity
@Table(name = "TB_ETMS_DUTY_PATROL_TIME")
public class PatrolTime extends IdEntity {

	// Fields    
	private Date ddate; //日期
	private String empName; //巡视员工
	private Date startTime; //巡视开始时间
	private Date endTime; //巡视结束时间
	private String content; //巡视内容

	// Constructors

	/** default constructor */
	public PatrolTime() {}

	public PatrolTime(Long id) {
		this.id = id;
	}

	/** full constructor */
	public PatrolTime(Date ddate, String empName, Date startTime, Date endTime, String content) {
		this.ddate = ddate;
		this.empName = empName;
		this.startTime = startTime;
		this.endTime = endTime;
		this.content = content;
	}

	// Property accessors
	@Column(name = "DDATE", nullable = false, length = 23)
	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	@Column(name = "EMP_NAME", nullable = false, length = 20)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "START_TIME", nullable = false, length = 23)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = false, length = 23)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "CONTENT", length = 512)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}