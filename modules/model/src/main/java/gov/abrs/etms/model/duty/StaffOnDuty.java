package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

//值班人员
@Entity
@Table(name = "TB_ETMS_DUTY_STAFF_ON_DUTY")
@DiscriminatorColumn(name = "MONITER", discriminatorType = DiscriminatorType.STRING, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StaffOnDuty extends IdEntity {

	private String name; //人员
	private Date startTime; //实际上班时间
	private Date endTime; //实际下班时间

	// Constructors

	/** default constructor */
	public StaffOnDuty() {}

	public StaffOnDuty(String name) {
		this.name = name;
	}

	public StaffOnDuty(String name, Date startTime, Date endTime) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors

	@Column(name = "EMP_NAME", nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

}