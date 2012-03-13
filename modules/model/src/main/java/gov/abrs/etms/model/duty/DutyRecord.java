package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

//值班记录明晰表
@Entity
@Table(name = "TB_ETMS_DUTY_RECORD")
@DiscriminatorColumn(name = "REC_TYPE", discriminatorType = DiscriminatorType.STRING, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class DutyRecord extends IdEntity {

	// Fields    
	private Duty duty; //对应的班
	private Date startTime; //开始时间
	private String content; //记录内容

	//	private String recType; //记录类型 D:代播  A:异态 W:告警P:巡视 B:播出文件 O:其他通知

	@Transient
	public Map<String, String> getRecTypeMap() {
		return null;
	}

	// Constructors
	/** default constructor */
	public DutyRecord() {}

	public DutyRecord(Duty duty, Date startTime, String content) {
		this.duty = duty;
		this.startTime = startTime;
		this.content = content;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DU_ID")
	public Duty getDuty() {
		return this.duty;
	}

	public void setDuty(Duty duty) {
		this.duty = duty;
	}

	@Column(name = "START_TIME", nullable = false, length = 23)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "CONTENT", length = 1024)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}