package gov.abrs.etms.model.stay;

import gov.abrs.etms.model.util.IdEntity;

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

//留守区间
@Entity
@Table(name = "TB_ETMS_STAY_SECTION")
public class StaySection extends IdEntity {

	private StayPeriod stayPeriod;//留守周期
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private List<StaySectionPerson> staySectionPeople;//留守人员

	public StaySection() {}

	public StaySection(StayPeriod stayPeriod, Date startTime, Date endTime) {
		this.stayPeriod = stayPeriod;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@ManyToOne
	@JoinColumn(name = "PERIOD_ID", nullable = false)
	public StayPeriod getStayPeriod() {
		return stayPeriod;
	}

	public void setStayPeriod(StayPeriod stayPeriod) {
		this.stayPeriod = stayPeriod;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = StaySectionPerson.class, mappedBy = "id.staySection")
	public List<StaySectionPerson> getStaySectionPeople() {
		return staySectionPeople;
	}

	public void setStaySectionPeople(List<StaySectionPerson> staySectionPeople) {
		this.staySectionPeople = staySectionPeople;
	}
}
