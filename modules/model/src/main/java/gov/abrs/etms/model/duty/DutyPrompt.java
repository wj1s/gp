package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//值班提示
@Entity
@Table(name = "TB_ETMS_DUTY_PROMPT")
public class DutyPrompt extends IdEntity implements Jsonable {

	// Fields    
	private Duty duty; //对应的班
	private String content; //提醒内容
	private Date ddate; //提醒时间
	private String empName; //提醒人
	private Date startTime; //有效开始时间
	private Date endTime; //有效结束时间

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("startTime", startTime);
		json.put("endTime", endTime);
		json.put("content", content);
		json.put("empName", empName);
		return json.toString();
	}

	// Constructors

	/** default constructor */
	public DutyPrompt() {}

	/** minimal constructor */
	public DutyPrompt(Long id) {
		this.id = id;
	}

	/** full constructor */
	public DutyPrompt(Duty duty, String content, Date ddate, String empName, Date startTime, Date endTime) {
		this.duty = duty;
		this.content = content;
		this.ddate = ddate;
		this.empName = empName;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DU_ID")
	public Duty getDuty() {
		return this.duty;
	}

	public void setDuty(Duty duty) {
		this.duty = duty;
	}

	@Column(name = "CONTENT", length = 512)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

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

}