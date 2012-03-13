package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//值班日志领导审核
@Entity
@Table(name = "TB_ETMS_DUTY_CHECK")
public class DutyCheck extends IdEntity {

	// Fields    
	private Duty duty; //对应的班
	private String content; //审核信息
	private String empName; //审核人
	private Date startTime; //审核时间

	// Constructors

	/** default constructor */
	public DutyCheck() {}

	public DutyCheck(Long id) {
		this.id = id;
	}

	/** full constructor */
	public DutyCheck(Duty duty, String content, String empName, Date startTime) {
		this.duty = duty;
		this.content = content;
		this.empName = empName;
		this.startTime = startTime;
	}

	// Property accessors
	@ManyToOne
	@JoinColumn(name = "DU_ID")
	public Duty getDuty() {
		return duty;
	}

	public void setDuty(Duty duty) {
		this.duty = duty;
	}

	@Column(name = "CONTENT", nullable = false, length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
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

}