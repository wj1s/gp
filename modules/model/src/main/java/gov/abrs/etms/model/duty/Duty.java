package gov.abrs.etms.model.duty;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;

//排班&上班
@Entity
@Table(name = "TB_ETMS_DUTY_DUTY")
public class Duty extends IdEntity implements Jsonable {

	// Fields    
	private Duty nextDuty; //下一个班
	private Duty preDuty; //上一个班
	private Date startTime; //排班开始时间
	private Date endTime; //排班结束时间
	private Group group; //班组
	private Integer dayPartCount; //运转数
	private String schName; //班次名称
	private String week; //星期
	private String weather; //天气
	private String temperature; //温度
	private String offDutyRecord; //交班记录
	private String onDutyRecord; //接班记录
	private Boolean confirm; //接班确认
	private Date updDate; //接班日期
	private String empName; //接班人员
	private Boolean first;
	private Boolean Last;
	private List<DutyCheck> dutyChecks = new ArrayList<DutyCheck>(); //值班日志领导审核
	private List<DutyRecord> dutyRecords = new ArrayList<DutyRecord>(); //值班记录
	private List<DutyPrompt> dutyPrompts = new ArrayList<DutyPrompt>(); //值班提醒

	private Moniter moniter; //班长
	private List<Watcher> watchers; //值班人员

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("dayPartCount", dayPartCount);
		jsonObject.put("dutyRuleName", this.getDutyRuleName());
		jsonObject.put("schName", this.getSchName());
		jsonObject.put("date", DateUtil.getDateStr(startTime));
		jsonObject.put("startTime", DateUtil.getTimeShortStr(startTime));
		jsonObject.put("endTime", DateUtil.getTimeShortStr(endTime));
		jsonObject.put("group_name", this.getGroup().getName());
		jsonObject.put("staffOnDuty", this.getStaffOnDuty());
		jsonObject.put("moniterName", moniter.getName());
		jsonObject.put("dept_name", this.getGroup().getDept().getName());
		jsonObject.put("updDate", DateUtil.dateToString(this.getUpdDate(), "yyyy-MM-dd HH:mm"));
		return jsonObject.toString();
	}

	//获得所有值班人员的名字，用","连接，班长加*
	@Transient
	public String getStaffOnDuty() {
		String staffs = this.getMoniter().getName() + "*,";
		if (this.getWatchers() != null) {
			for (Watcher watcher : this.getWatchers()) {
				staffs += watcher.getName() + ",";
			}
		}
		staffs = staffs.substring(0, staffs.length() - 1);
		return staffs;
	}

	//判断是否已经交接过班
	@Transient
	public Boolean getShift() {
		return this.updDate == null ? false : true;
	}

	//判断是否已经下班
	@Transient
	public Boolean getFinished() {
		return this.offDutyRecord == null ? false : true;
	}

	//判断是否是跨天班
	@Transient
	public Boolean isCrossDay() {
		Date start = DateUtil.dateToDateByFormat(startTime, DateUtil.FORMAT_DAY);
		Date end = DateUtil.dateToDateByFormat(endTime, DateUtil.FORMAT_DAY);
		return DateUtil.afterDay(end, start);
	}

	//判断是否是当天最后一个班
	@Transient
	public Boolean isOneDayLastDuty() {
		Duty nextDuty = this.getNextDuty();
		if (nextDuty == null) {
			return true;
		} else {
			Date nextStart = DateUtil.dateToDateByFormat(this.nextDuty.getStartTime(), DateUtil.FORMAT_DAY);
			Date start = DateUtil.dateToDateByFormat(startTime, DateUtil.FORMAT_DAY);
			return DateUtil.afterDay(nextStart, start);
		}
	}

	@Transient
	public String getDutyRuleName() {
		String name = "";
		switch (this.getDayPartCount()) {
		case 1:
			name = "一运转";
			break;
		case 2:
			name = "二运转";
			break;
		case 3:
			name = "三运转";
			break;
		case 4:
			name = "四运转";
			break;
		case 5:
			name = "五运转";
			break;
		case 6:
			name = "六运转";
			break;
		case 7:
			name = "七运转";
			break;
		case 8:
			name = "八运转";
			break;
		case 9:
			name = "九运转";
			break;
		default:
			break;
		}
		return name;
	}

	// Constructors

	/** default constructor */
	public Duty() {}

	public Duty(Long id) {
		this.id = id;
	}

	// Property accessors
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "preDuty")
	public Duty getNextDuty() {
		return this.nextDuty;
	}

	public void setNextDuty(Duty nextDuty) {
		this.nextDuty = nextDuty;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PRE_DUTY", nullable = true)
	public Duty getPreDuty() {
		return preDuty;
	}

	public void setPreDuty(Duty preDuty) {
		this.preDuty = preDuty;
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

	@ManyToOne()
	@JoinColumn(name = "GROUP_ID", nullable = false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "DAY_PART_COUNT", nullable = false)
	public Integer getDayPartCount() {
		return this.dayPartCount;
	}

	public void setDayPartCount(Integer dayPartCount) {
		this.dayPartCount = dayPartCount;
	}

	@Column(name = "WEEK")
	public String getWeek() {
		return this.week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	@Column(name = "WEATHER", length = 100)
	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Column(name = "TEMPERATURE", length = 10)
	public String getTemperature() {
		return this.temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	@Column(name = "OFF_DUTY_RECORD", length = 1024)
	public String getOffDutyRecord() {
		return this.offDutyRecord;
	}

	public void setOffDutyRecord(String offDutyRecord) {
		this.offDutyRecord = offDutyRecord;
	}

	@Column(name = "ON_DUTY_RECORD", length = 1024)
	public String getOnDutyRecord() {
		return this.onDutyRecord;
	}

	public void setOnDutyRecord(String onDutyRecord) {
		this.onDutyRecord = onDutyRecord;
	}

	@Column(name = "UPD_DATE", length = 23)
	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "EMP_NAME", length = 20)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "duty")
	public List<DutyCheck> getDutyChecks() {
		return dutyChecks;
	}

	public void setDutyChecks(List<DutyCheck> dutyChecks) {
		this.dutyChecks = dutyChecks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "duty")
	public List<DutyRecord> getDutyRecords() {
		return dutyRecords;
	}

	public void setDutyRecords(List<DutyRecord> dutyRecords) {
		this.dutyRecords = dutyRecords;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "duty")
	public List<DutyPrompt> getDutyPrompts() {
		return dutyPrompts;
	}

	public void setDutyPrompts(List<DutyPrompt> dutyPrompts) {
		this.dutyPrompts = dutyPrompts;
	}

	@Column(name = "SCH_NAME")
	public String getSchName() {
		return schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dutyW", targetEntity = Watcher.class)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<Watcher> getWatchers() {
		return watchers;
	}

	public void setWatchers(List<Watcher> watchers) {
		this.watchers = watchers;
	}

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dutyM", targetEntity = Moniter.class)
	@Cascade(org.hibernate.annotations.CascadeType.REMOVE)
	public Moniter getMoniter() {
		return moniter;
	}

	public void setMoniter(Moniter moniter) {
		this.moniter = moniter;
	}

	@Column(name = "CONFIRM")
	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
	}

	@Column(name = "FIRST")
	public Boolean getFirst() {
		return first;
	}

	public void setFirst(Boolean first) {
		this.first = first;
	}

	@Column(name = "LAST")
	public Boolean getLast() {
		return Last;
	}

	public void setLast(Boolean last) {
		Last = last;
	}
}