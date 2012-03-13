package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.duty.RuleItem;
import gov.abrs.etms.model.para.GroupType;
import gov.abrs.etms.model.repair.Record;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//班组
@Entity
@Table(name = "TB_ETMS_BASE_GROUP")
public class Group extends IdEntity implements Jsonable {

	private String name;//班组名称
	private Dept dept;//所属部门
	private GroupType groupType;//班组类型
	private List<Person> people;//下属人员
	private List<Duty> duties;//班组的排班记录
	private List<RuleItem> ruleItems;//排班规则项
	private List<Record> records;

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("dept.name", this.getDept().getName());
		jsonObject.put("name", this.getName());
		jsonObject.put("groupType.codeDesc", this.getGroupType().getCodeDesc());
		return jsonObject.toString();
	}

	public Group() {}

	public Group(long id) {
		this.id = id;
	}

	public Group(String name, Dept dept, GroupType groupType) {
		this.name = name;
		this.dept = dept;
		this.groupType = groupType;
	}

	@ManyToOne()
	@JoinColumn(name = "DP_ID", nullable = false)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "GROUP_NAME", length = 64, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "GROUP_TYPE", nullable = false)
	public GroupType getGroupType() {
		return groupType;
	}

	public void setGroupType(GroupType groupType) {
		this.groupType = groupType;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "group", fetch = FetchType.LAZY, targetEntity = Person.class)
	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.LAZY, targetEntity = Duty.class)
	public List<Duty> getDuties() {
		return duties;
	}

	public void setDuties(List<Duty> duties) {
		this.duties = duties;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "group", fetch = FetchType.LAZY, targetEntity = RuleItem.class)
	public List<RuleItem> getRuleItems() {
		return ruleItems;
	}

	public void setRuleItems(List<RuleItem> ruleItems) {
		this.ruleItems = ruleItems;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "group")
	public List<Record> getRecords() {
		return this.records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}
}
