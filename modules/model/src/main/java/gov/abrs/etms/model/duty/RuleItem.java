package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//周期项
@Entity
@Table(name = "TB_ETMS_DUTY_RULE_ITEM")
public class RuleItem extends IdEntity {

	// Fields    
	private DutySchedule dutySchedule; //值班时间段
	private Group group; //班组
	private Integer periodDayNum; //周期的天序号

	// Constructors

	/** default constructor */
	public RuleItem() {}

	/** minimal constructor */
	public RuleItem(DutySchedule dutySchedule, Integer periodDayNum) {
		this.dutySchedule = dutySchedule;
		this.periodDayNum = periodDayNum;
	}

	/** full constructor */
	public RuleItem(DutySchedule dutySchedule, Group group, Integer periodDayNum) {
		this.dutySchedule = dutySchedule;
		this.group = group;
		this.periodDayNum = periodDayNum;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCH_ID", nullable = false)
	public DutySchedule getDutySchedule() {
		return this.dutySchedule;
	}

	public void setDutySchedule(DutySchedule dutySchedule) {
		this.dutySchedule = dutySchedule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID", nullable = false)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Column(name = "PERIOD_DAY_NUM", nullable = false)
	public Integer getPeriodDayNum() {
		return this.periodDayNum;
	}

	public void setPeriodDayNum(Integer periodDayNum) {
		this.periodDayNum = periodDayNum;
	}

}