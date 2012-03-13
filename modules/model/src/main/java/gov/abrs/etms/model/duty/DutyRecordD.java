package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.rept.BroadByTime;

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

//代播记录
@Entity
@DiscriminatorValue(value = "D")
public class DutyRecordD extends DutyRecord {
	private BroadByTime broadByTime; //代播

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "BROAD_ID")
	public BroadByTime getBroadByTime() {
		return broadByTime;
	}

	public void setBroadByTime(BroadByTime broadByTime) {
		this.broadByTime = broadByTime;
	}

	@Override
	@Transient
	public Map<String, String> getRecTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("D", "代播");
		return map;
	}

	public DutyRecordD() {

	}

	public DutyRecordD(Duty duty, Date startTime, String content, BroadByTime broadByTime) {
		super(duty, startTime, content);
		this.broadByTime = broadByTime;
	}
}
