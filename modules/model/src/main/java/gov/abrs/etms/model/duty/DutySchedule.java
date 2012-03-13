package gov.abrs.etms.model.duty;

import gov.abrs.etms.common.util.DateUtil;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;

//值班时间段规则
@Entity
@Table(name = "TB_ETMS_DUTY_SCHEDULE")
public class DutySchedule extends IdEntity implements Jsonable {

	// Fields    
	private DutyRule dutyRule; //值班时间段
	private String schName; //值班时间段名称
	private Integer schOrder; //值班时间段序号
	private Date startTime; //开始时间
	private Date endTime; //结束时间
	private List<RuleItem> ruleItems = new ArrayList<RuleItem>(0); //周期项

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("schName", this.getSchName());
		jsonObject.put("schOrder", this.getSchOrder());
		jsonObject.put("startTime", DateUtil.getTimeShortStr(this.getStartTime()));
		jsonObject.put("endTime", DateUtil.getTimeShortStr(this.getEndTime()));
		return jsonObject.toString();
	}

	// Constructors

	/** default constructor */
	public DutySchedule() {}

	public DutySchedule(Long id) {
		super();
		this.id = id;
	}

	/** minimal constructor */
	public DutySchedule(DutyRule dutyRule, String schName, Integer schOrder, Date startTime, Date endTime) {
		this.dutyRule = dutyRule;
		this.schName = schName;
		this.schOrder = schOrder;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/** full constructor */
	public DutySchedule(DutyRule dutyRule, String schName, Integer schOrder, Date startTime, Date endTime,
			List<RuleItem> ruleItems) {
		this.dutyRule = dutyRule;
		this.schName = schName;
		this.schOrder = schOrder;
		this.startTime = startTime;
		this.endTime = endTime;
		this.ruleItems = ruleItems;
	}

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RU_ID", nullable = false)
	public DutyRule getDutyRule() {
		return this.dutyRule;
	}

	public void setDutyRule(DutyRule dutyRule) {
		this.dutyRule = dutyRule;
	}

	@Column(name = "SCH_NAME", nullable = false, length = 20)
	public String getSchName() {
		return this.schName;
	}

	public void setSchName(String schName) {
		this.schName = schName;
	}

	@Column(name = "SCH_ORDER", nullable = false)
	public Integer getSchOrder() {
		return this.schOrder;
	}

	public void setSchOrder(Integer schOrder) {
		this.schOrder = schOrder;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dutySchedule")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OrderBy("periodDayNum ASC")
	public List<RuleItem> getRuleItems() {
		return ruleItems;
	}

	public void setRuleItems(List<RuleItem> ruleItems) {
		this.ruleItems = ruleItems;
	}
}