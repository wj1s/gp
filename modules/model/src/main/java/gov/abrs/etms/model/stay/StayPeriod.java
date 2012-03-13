package gov.abrs.etms.model.stay;

import gov.abrs.etms.model.util.IdEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

//留守周期
@Entity
@Table(name = "TB_ETMS_STAY_PERIOD")
public class StayPeriod extends IdEntity {

	private StayRule stayRule;//留守规则
	private Integer periodIndex;//顺序
	private List<StaySection> staySections;//留守区间

	@ManyToOne
	@JoinColumn(name = "RULE_ID", nullable = false)
	public StayRule getStayRule() {
		return stayRule;
	}

	public void setStayRule(StayRule stayRule) {
		this.stayRule = stayRule;
	}

	@Column(name = "PERIOD_INDEX", nullable = false)
	public Integer getPeriodIndex() {
		return periodIndex;
	}

	public void setPeriodIndex(Integer periodIndex) {
		this.periodIndex = periodIndex;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "stayPeriod", fetch = FetchType.LAZY, targetEntity = StaySection.class)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<StaySection> getStaySections() {
		return staySections;
	}

	public void setStaySections(List<StaySection> staySections) {
		this.staySections = staySections;
	}

}
