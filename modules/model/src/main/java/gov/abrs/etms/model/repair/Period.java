package gov.abrs.etms.model.repair;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

/**
 * Period entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_PERIOD")
public class Period extends IdEntity implements java.io.Serializable, Jsonable {

	// Fields

	private String name;//周期名称
	private Integer previousValue;//提前时间
	private Integer value;//一周期时间
	private String type;//检修周期类型
	private Date startDay;//开始时间
	private Date endDay;//结束时间
	private List<Card> cards;

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("previousValue", this.getPreviousValue());
		jsonObject.put("value", this.getValue());
		if (this.getType().equals("1"))
			jsonObject.put("rule", this.getIvRule());
		else
			jsonObject.put("rule", this.getTRule());
		jsonObject.put("startDay", DateUtil.getDateCNMDStr(this.getStartDay()));
		jsonObject.put("endDay", DateUtil.getDateCNMDStr(this.getEndDay()));
		return jsonObject.toString();
	}

	@Transient
	public String getIvRule() {
		return "每两次检修间隔" + this.getValue() + "天";
	}

	@Transient
	public String getTRule() {
		return "需要在" + DateUtil.getDateCNMDStr(this.getStartDay()) + "与" + DateUtil.getDateCNMDStr(this.getEndDay())
				+ "之间进行检修";
	}

	// Constructors

	/** default constructor */
	public Period() {}

	/** minimal constructor */
	public Period(long id, String name, Integer previousValue, String type) {
		this.id = id;
		this.name = name;
		this.previousValue = previousValue;
		this.type = type;
	}

	/** full constructor */
	public Period(long id, String name, Integer previousValue, Integer value, String type, Date startDay, Date endDay,
			List<Card> cards) {
		this.id = id;
		this.name = name;
		this.previousValue = previousValue;
		this.value = value;
		this.type = type;
		this.startDay = startDay;
		this.endDay = endDay;
		this.cards = cards;
	}

	// Property accessors

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PREVIOUS_VALUE", nullable = false)
	public Integer getPreviousValue() {
		return this.previousValue;
	}

	public void setPreviousValue(Integer previousValue) {
		this.previousValue = previousValue;
	}

	@Column(name = "VALUE")
	public Integer getValue() {
		return this.value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Column(name = "TYPE", nullable = false, length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "START_DAY", length = 23)
	public Date getStartDay() {
		return this.startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	@Column(name = "END_DAY", length = 23)
	public Date getEndDay() {
		return this.endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "period")
	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}