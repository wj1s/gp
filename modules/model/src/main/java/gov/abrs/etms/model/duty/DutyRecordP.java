package gov.abrs.etms.model.duty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//巡视记录
@Entity
@DiscriminatorValue(value = "P")
public class DutyRecordP extends DutyRecord {

	private PatrolTime patrolTime; //巡视记录

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "PA_ID")
	public PatrolTime getPatrolTime() {
		return this.patrolTime;
	}

	public void setPatrolTime(PatrolTime patrolTime) {
		this.patrolTime = patrolTime;
	}

	@Override
	@Transient
	public Map<String, String> getRecTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("P", "巡视");
		return map;
	}

	public DutyRecordP() {}

	public DutyRecordP(Duty duty, Date startTime, String content, PatrolTime patrolTime) {
		super(duty, startTime, content);
		this.patrolTime = patrolTime;
	}
}
