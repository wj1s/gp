package gov.abrs.etms.model.repair;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;
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

import org.hibernate.annotations.Cascade;

/**
 * Record entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_RECORD")
public class Record extends IdEntity implements Jsonable {

	// Fields
	private Dept dept;//部门
	private Group group;//班组
	private Date ddate;//检修时间
	private Integer timeLength;//时长
	private String deptment;//配合部门
	private String measure;//安全措施
	private String examineRecord;//检修情况记录
	private String security;//安全员
	private String checker;//复核人
	private String test;//试机情况
	private String principal;//负责人
	private Date updDate;//更新日期
	private String personName;//操作员
	private String examinePersons;//检修人员
	private List<RecordItem> recordItems;//检修记录项

	// Constructors
	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("group.dept.name", this.getDept() == null ? "" : this.getDept().getName());
		jsonObject.put("group.name", this.getGroup() == null ? "" : this.getGroup().getName());
		jsonObject.put("examinePersons", this.getExaminePersons().trim());
		jsonObject.put("ddate", DateUtil.dateToString(this.getDdate(), "yyyy-MM-dd"));
		jsonObject.put("timeLength", this.getTimeLength());
		jsonObject.put("principal", this.getPrincipal());
		jsonObject.put("security", this.getSecurity());
		jsonObject.put("measure", this.getMeasure());
		jsonObject.put("examineRecord", this.getExamineRecord());
		jsonObject.put("test", this.getTest());
		return jsonObject.toString();
	}

	/** default constructor */
	public Record() {}

	public Record(long id) {
		this.id = id;

	}

	/** full constructor */
	public Record(long id, Group group, Date ddate, Integer timeLength, String deptment, String measure,
			String examineRecord, String security, String checker, String test, String principal, Date updDate,
			String personName, String examinePersons, List<RecordItem> recordItems) {
		this.id = id;
		this.group = group;
		this.ddate = ddate;
		this.timeLength = timeLength;
		this.deptment = deptment;
		this.measure = measure;
		this.examineRecord = examineRecord;
		this.security = security;
		this.checker = checker;
		this.test = test;
		this.principal = principal;
		this.updDate = updDate;
		this.personName = personName;
		this.examinePersons = examinePersons;
		this.recordItems = recordItems;
	}

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUP_ID")
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DP_ID")
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "DDATE", nullable = false, length = 23)
	public Date getDdate() {
		return this.ddate;
	}

	public void setDdate(Date ddate) {
		this.ddate = ddate;
	}

	@Column(name = "TIME_LENGTH", nullable = false)
	public Integer getTimeLength() {
		return this.timeLength;
	}

	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}

	@Column(name = "DEPTMENT", length = 100)
	public String getDeptment() {
		return this.deptment;
	}

	public void setDeptment(String deptment) {
		this.deptment = deptment;
	}

	@Column(name = "MEASURE", length = 512)
	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	@Column(name = "EXAMINE_RECORD", length = 1024)
	public String getExamineRecord() {
		return this.examineRecord;
	}

	public void setExamineRecord(String examineRecord) {
		this.examineRecord = examineRecord;
	}

	@Column(name = "SECURITY", length = 40)
	public String getSecurity() {
		return this.security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	@Column(name = "CHECKER", length = 40)
	public String getChecker() {
		return this.checker;
	}

	public void setChecker(String checker) {
		this.checker = checker;
	}

	@Column(name = "TEST", length = 512)
	public String getTest() {
		return this.test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	@Column(name = "PRINCIPAL", length = 40)
	public String getPrincipal() {
		return this.principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	@Column(name = "UPD_DATE", nullable = false, length = 23)
	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "PERSON_NAME", nullable = false, length = 20)
	public String getPersonName() {
		return this.personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	@Column(name = "EXAMINE_PERSONS", length = 100)
	public String getExaminePersons() {
		return this.examinePersons;
	}

	public void setExaminePersons(String examinePersons) {
		this.examinePersons = examinePersons;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "record")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<RecordItem> getRecordItems() {
		return this.recordItems;
	}

	public void setRecordItems(List<RecordItem> recordItems) {
		this.recordItems = recordItems;
	}

}