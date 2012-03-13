package gov.abrs.etms.model.stay;

import gov.abrs.etms.model.baseinfo.Dept;
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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;

//留守
@Entity
@Table(name = "TB_ETMS_STAY_RULE")
public class StayRule extends IdEntity implements Jsonable {

	private Dept dept;//部门
	private Integer periodCount;//周期数
	private List<StayPeriod> stayPeriods;//留守周期

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject object = new JSONObject();
		object.put("id", getId());
		object.put("periodCount", getPeriodCount());
		object.put("deptName", getDept().getName());
		return object.toString();
	}

	@ManyToOne
	@JoinColumn(name = "DP_ID", nullable = false)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "PERIOD_COUNT", nullable = false)
	public Integer getPeriodCount() {
		return periodCount;
	}

	public void setPeriodCount(Integer periodCount) {
		this.periodCount = periodCount;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stayRule", fetch = FetchType.LAZY, targetEntity = StayPeriod.class)
	@OrderBy(value = "periodIndex")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<StayPeriod> getStayPeriods() {
		return stayPeriods;
	}

	public void setStayPeriods(List<StayPeriod> stayPeriods) {
		this.stayPeriods = stayPeriods;
	}

}
