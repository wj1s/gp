package gov.abrs.etms.model.repair;

import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.util.IdEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 * RecordItem entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_RECORD_ITEM")
public class RecordItem extends IdEntity implements java.io.Serializable {

	// Fields

	private Card card;//检修周期表卡片
	private Record record;//检修记录
	private String content;//内容
	private String type;//类型
	private String cycleName;//检修周期表名称
	private List<Person> persons;//检修人员
	private List<Equip> equips;//设备

	// Constructors

	/** default constructor */
	public RecordItem() {}

	/** minimal constructor */
	public RecordItem(long id) {
		this.id = id;
	}

	/** full constructor */
	public RecordItem(long id, Card card, Record record, String content, String type, String cycleName,
			List<Person> persons, List<Equip> equips) {
		this.id = id;
		this.card = card;
		this.record = record;
		this.content = content;
		this.type = type;
		this.cycleName = cycleName;
		this.persons = persons;
		this.equips = equips;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CARD_ID")
	public Card getCard() {
		return this.card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RECORD_ID")
	public Record getRecord() {
		return this.record;
	}

	public void setRecord(Record record) {
		this.record = record;
	}

	@Column(name = "CONTENT", length = 512)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "CYCLE_NAME", length = 100)
	public String getCycleName() {
		return this.cycleName;
	}

	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "TB_ETMS_REPAIR_RECORD_PERSON", joinColumns = { @JoinColumn(name = "RECORD_ITEM_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "PERSON_ID", nullable = false, updatable = false) })
	@OrderBy("name ASC")
	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "TB_ETMS_REPAIR_RECORD_EQUIP", joinColumns = { @JoinColumn(name = "RECORD_ITEM_ID", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EQUIP_ID", nullable = false, updatable = false) })
	public List<Equip> getEquips() {
		return this.equips;
	}

	public void setEquips(List<Equip> equips) {
		this.equips = equips;
	}

}