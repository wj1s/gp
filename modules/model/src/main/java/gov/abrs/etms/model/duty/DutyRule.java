package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//排班规则名称
@Entity
@Table(name = "TB_ETMS_DUTY_SCHEDULE_RULE")
public class DutyRule extends IdEntity implements Jsonable {

	// Fields    
	private Dept dept; //部门
	private String ruleName; //规则名称
	private Integer dayPartCount; //运转数
	private String rmks; //备注
	private List<DutySchedule> dutySchedules = new ArrayList<DutySchedule>(0); //值班时间段规则

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("dept_Name", this.getDept().getName());
		jsonObject.put("dept_deptName", this.getDept().getName());
		jsonObject.put("ruleName", this.getRuleName());
		jsonObject.put("dayPartCount", this.getDayPartCount());
		jsonObject.put("rmks", this.getRmks());
		return jsonObject.toString();
	}

	// 获得已经排班的周期项的天数
	@Transient
	public Integer getLastCycle() {
		List<DutySchedule> dutySchedules = new ArrayList<DutySchedule>(this.getDutySchedules());
		if (dutySchedules != null && dutySchedules.size() != 0) {
			DutySchedule dutySchedule = dutySchedules.get(0);
			List<RuleItem> ruleItemList = dutySchedule.getRuleItems();
			if (ruleItemList != null && ruleItemList.size() != 0) {
				return ruleItemList.size();
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// Constructors

	/** default constructor */

	public DutyRule() {}

	public DutyRule(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public DutyRule(Dept dept, String ruleName, Integer dayPartCount) {
		this.dept = dept;
		this.ruleName = ruleName;
		this.dayPartCount = dayPartCount;
	}

	/** full constructor */
	public DutyRule(Dept dept, String ruleName, Integer dayPartCount, String rmks, List<DutySchedule> dutySchedules) {
		this.dept = dept;
		this.ruleName = ruleName;
		this.dayPartCount = dayPartCount;
		this.rmks = rmks;
		this.dutySchedules = dutySchedules;
	}

	// Property accessors
	@ManyToOne()
	@JoinColumn(name = "DP_ID")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "RULE_NAME", nullable = false, length = 50)
	public String getRuleName() {
		return this.ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	@Column(name = "DAY_PART_COUNT", nullable = false)
	public Integer getDayPartCount() {
		return this.dayPartCount;
	}

	public void setDayPartCount(Integer dayPartCount) {
		this.dayPartCount = dayPartCount;
	}

	@Column(name = "RMKS", length = 50)
	public String getRmks() {
		return this.rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dutyRule")
	@OrderBy("schOrder ASC")
	public List<DutySchedule> getDutySchedules() {
		return dutySchedules;
	}

	public void setDutySchedules(List<DutySchedule> dutySchedules) {
		this.dutySchedules = dutySchedules;
	}
}