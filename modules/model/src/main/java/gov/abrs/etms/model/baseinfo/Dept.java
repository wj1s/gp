package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.util.ClassName;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;
import gov.abrs.etms.model.util.PropertyName;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//部门
@Entity
@Table(name = "TB_ETMS_BASE_DEPT")
@ClassName(name = "部门")
public class Dept extends IdEntity implements Jsonable {
	@PropertyName(name = "部门代码")
	private String code;
	@PropertyName(name = "部门名称")
	private String name;
	@PropertyName(name = "部门类型", subProperty = "codeDesc")
	private DeptType deptType;//部门类型
	private Station station;//所属台站
	private String adId;//AD的编号
	private String enable;//是否禁用1正常，0禁用
	private List<Group> groups;//下属班组
	private List<Person> persons;//下属人员
	private List<Device> devices;//部门备件
	private List<DutyRule> dutyRules;//值班规则
	private List<TransType> transTypes; //部门对应的传输方式
	private List<DeptPer> deptPers;
	private List<Cycle> cycles;//检修系统

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("code", this.getCode());
		jsonObject.put("deptType.codeDesc", this.getDeptType().getCodeDesc());
		jsonObject.put("station.name", this.getStation().getName());
		String transTypesStr = "";
		for (TransType transType : this.getTransTypes()) {
			transTypesStr += transType.getCodeDesc() + ",";
		}
		if (!transTypesStr.equals("")) {
			transTypesStr = transTypesStr.substring(0, transTypesStr.length() - 1);
		}
		jsonObject.put("transTypes.codeDesc", transTypesStr);
		return jsonObject.toString();
	}

	public Dept() {}

	public Dept(long id) {
		this.id = id;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "dept", fetch = FetchType.LAZY, targetEntity = Person.class)
	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	@Column(name = "DEPT_CODE", nullable = false, unique = true, length = 4)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "DEPT_NAME", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AD_ID", length = 50)
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	@Column(name = "ENABLE", length = 1)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@ManyToOne()
	@JoinColumn(name = "ST_ID", nullable = false)
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dept", fetch = FetchType.LAZY, targetEntity = Group.class)
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	@ManyToOne()
	@JoinColumn(name = "DEPT_TYPE", nullable = false)
	public DeptType getDeptType() {
		return deptType;
	}

	public void setDeptType(DeptType deptType) {
		this.deptType = deptType;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "dept", fetch = FetchType.LAZY, targetEntity = Device.class)
	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dept", fetch = FetchType.LAZY, targetEntity = DutyRule.class)
	@OrderBy(value = "dayPartCount")
	public List<DutyRule> getDutyRules() {
		return dutyRules;
	}

	public void setDutyRules(List<DutyRule> dutyRules) {
		this.dutyRules = dutyRules;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "TB_ETMS_BASE_DP_TRTP", joinColumns = @JoinColumn(name = "DP_ID"), inverseJoinColumns = @JoinColumn(name = "TRANS_TYPE"))
	public List<TransType> getTransTypes() {
		return transTypes;
	}

	public void setTransTypes(List<TransType> transTypes) {
		this.transTypes = transTypes;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "dept", fetch = FetchType.LAZY, targetEntity = DeptPer.class)
	public List<DeptPer> getDeptPers() {
		return deptPers;
	}

	public void setDeptPers(List<DeptPer> deptPers) {
		this.deptPers = deptPers;
	}

	/*郭翔 添加检修*/
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dept")
	public List<Cycle> getCycles() {
		return cycles;
	}

	public void setCycles(List<Cycle> cycles) {
		this.cycles = cycles;
	}

}
