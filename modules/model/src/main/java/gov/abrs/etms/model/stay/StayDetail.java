package gov.abrs.etms.model.stay;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
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

//留守
@Entity
@Table(name = "TB_ETMS_STAY_DETAIL")
public class StayDetail extends IdEntity implements Jsonable {

	private Dept dept;//部门
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private List<StayDetailPerson> stayDetailPeople;//留守明晰人员

	public StayDetail() {}

	public StayDetail(Dept dept, Date startTime, Date endTime) {
		this.dept = dept;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("dept.name", dept.getName());
		obj.put("startTime", DateUtil.dateToString(startTime, "yyyy-MM-dd HH:mm"));
		obj.put("endTime", DateUtil.dateToString(endTime, "yyyy-MM-dd HH:mm"));
		obj.put("empNames", getEmpNames());
		return obj.toString();
	}

	@Transient
	private String getEmpNames() {
		StringBuffer sb = new StringBuffer();
		for (StayDetailPerson sdp : stayDetailPeople) {
			sb.append(sdp.getId().getEmpName() + ",");
		}
		String str = sb.toString();
		if (!sb.equals("")) {
			return str.substring(0, str.length() - 1);
		} else {
			return "";
		}
	}

	@ManyToOne
	@JoinColumn(name = "DP_ID", nullable = false)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = StayDetailPerson.class, mappedBy = "id.stayDetail")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<StayDetailPerson> getStayDetailPeople() {
		return stayDetailPeople;
	}

	public void setStayDetailPeople(List<StayDetailPerson> stayDetailPeople) {
		this.stayDetailPeople = stayDetailPeople;
	}
}
