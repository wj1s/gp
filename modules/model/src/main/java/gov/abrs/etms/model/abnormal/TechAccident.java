package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Station;
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
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.hibernate.annotations.OrderBy;

/**
 * TbEtmsTechAccident entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_TECH_ACCIDENT")
public class TechAccident extends IdEntity implements Jsonable {

	// Fields    
	private String accdCode;//事故编号：'A'+单位S_CODE+记录生成时间YYYYMMDDHH24MISS年月日时分秒
	private Station station;
	//private Integer stId;//台站ID
	private Date startTime;//发生时间
	private Date endDate;//结束时间
	private String happenLocation;//发生处所
	private String dutyPerson;//责任人
	private String accdKind;//事故性质
	private String accdCourse;//事故经过
	private String accdReason;//事故原因
	private String accdResult;//事故后果
	private String accdManage;//事故处理情况
	private String accdPrev;//预防措施
	private String endFlag;//事故结束标志,Y:结束  N:等待后果
	private String updName;//上报人
	private Date updDate;//上报时间
	private List<TechAccidentH> techAccidentHs = new ArrayList<TechAccidentH>();//历史结果
	private List<TechAccidentMedia> techAccidentMedias = new ArrayList<TechAccidentMedia>();// 历史结果

	// Constructors
	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", id);
		jsonObject.put("accdCode", accdCode);
		jsonObject.put("startTime", DateUtil.getDateTimeStr(this.getStartTime()));
		jsonObject.put("endDate", DateUtil.getDateTimeStr(this.getEndDate()));
		jsonObject.put("accdReason", accdReason);
		jsonObject.put("dutyPerson", dutyPerson);
		return jsonObject;
	}

	/** default constructor */
	public TechAccident() {}

	/** minimal constructor */
	public TechAccident(long id, String accdCode, Station station, Date startTime, String happenLocation,
			String dutyPerson, String accdKind, String accdCourse, String accdReason, String accdManage,
			String accdPrev, String endFlag, String updName, Date updDate) {
		this.id = id;
		this.accdCode = accdCode;
		this.station = station;
		this.startTime = startTime;
		this.happenLocation = happenLocation;
		this.dutyPerson = dutyPerson;
		this.accdKind = accdKind;
		this.accdCourse = accdCourse;
		this.accdReason = accdReason;
		this.accdManage = accdManage;
		this.accdPrev = accdPrev;
		this.endFlag = endFlag;
		this.updName = updName;
		this.updDate = updDate;
	}

	/** full constructor */
	public TechAccident(long id, String accdCode, Station station, Date startTime, Date endDate, String happenLocation,
			String dutyPerson, String accdKind, String accdCourse, String accdReason, String accdResult,
			String accdManage, String accdPrev, String endFlag, String updName, Date updDate,
			List<TechAccidentH> techAccidentHs) {
		this.id = id;
		this.accdCode = accdCode;
		this.station = station;
		this.startTime = startTime;
		this.endDate = endDate;
		this.happenLocation = happenLocation;
		this.dutyPerson = dutyPerson;
		this.accdKind = accdKind;
		this.accdCourse = accdCourse;
		this.accdReason = accdReason;
		this.accdResult = accdResult;
		this.accdManage = accdManage;
		this.accdPrev = accdPrev;
		this.endFlag = endFlag;
		this.updName = updName;
		this.updDate = updDate;
		this.techAccidentHs = techAccidentHs;
	}

	// Property accessors
	@Column(name = "ACCD_CODE", nullable = false, length = 20)
	public String getAccdCode() {
		return this.accdCode;
	}

	public void setAccdCode(String accdCode) {
		this.accdCode = accdCode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ST_ID", nullable = false)
	public Station getStation() {
		return this.station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	@Column(name = "START_TIME", nullable = false, length = 23)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_DATE", length = 23)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Column(name = "HAPPEN_LOCATION", nullable = false, length = 60)
	public String getHappenLocation() {
		return this.happenLocation;
	}

	public void setHappenLocation(String happenLocation) {
		this.happenLocation = happenLocation;
	}

	@Column(name = "DUTY_PERSON", nullable = false, length = 60)
	public String getDutyPerson() {
		return this.dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}

	@Column(name = "ACCD_KIND", nullable = false, length = 2)
	public String getAccdKind() {
		return this.accdKind;
	}

	public void setAccdKind(String accdKind) {
		this.accdKind = accdKind;
	}

	@Column(name = "ACCD_COURSE", nullable = false, length = 2048)
	public String getAccdCourse() {
		return this.accdCourse;
	}

	public void setAccdCourse(String accdCourse) {
		this.accdCourse = accdCourse;
	}

	@Column(name = "ACCD_REASON", nullable = false, length = 2048)
	public String getAccdReason() {
		return this.accdReason;
	}

	public void setAccdReason(String accdReason) {
		this.accdReason = accdReason;
	}

	@Column(name = "ACCD_RESULT", length = 2048)
	public String getAccdResult() {
		return this.accdResult;
	}

	public void setAccdResult(String accdResult) {
		this.accdResult = accdResult;
	}

	@Column(name = "ACCD_MANAGE", nullable = false, length = 2048)
	public String getAccdManage() {
		return this.accdManage;
	}

	public void setAccdManage(String accdManage) {
		this.accdManage = accdManage;
	}

	@Column(name = "ACCD_PREV", nullable = false, length = 2048)
	public String getAccdPrev() {
		return this.accdPrev;
	}

	public void setAccdPrev(String accdPrev) {
		this.accdPrev = accdPrev;
	}

	@Column(name = "END_FLAG", nullable = false, length = 1)
	public String getEndFlag() {
		return this.endFlag;
	}

	public void setEndFlag(String endFlag) {
		this.endFlag = endFlag;
	}

	@Column(name = "UPD_NAME", nullable = false, length = 50)
	public String getUpdName() {
		return this.updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	@Column(name = "UPD_DATE", nullable = false, length = 23)
	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "techAccident")
	@OrderBy(clause = "UPD_DATE desc")
	public List<TechAccidentH> getTechAccidentHs() {
		return this.techAccidentHs;
	}

	public void setTechAccidentHs(List<TechAccidentH> techAccidentHs) {
		this.techAccidentHs = techAccidentHs;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "techAccident")
	public List<TechAccidentMedia> getTechAccidentMedias() {
		return techAccidentMedias;
	}

	public void setTechAccidentMedias(List<TechAccidentMedia> techAccidentMedias) {
		this.techAccidentMedias = techAccidentMedias;
	}

}