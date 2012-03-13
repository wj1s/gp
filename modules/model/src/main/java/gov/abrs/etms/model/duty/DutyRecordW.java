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

//告警记录
@Entity
@DiscriminatorValue(value = "W")
public class DutyRecordW extends DutyRecord {

	private DutyWarning dutyWarning; //告警

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "WARN_ID")
	public DutyWarning getDutyWarning() {
		return this.dutyWarning;
	}

	public void setDutyWarning(DutyWarning dutyWarning) {
		this.dutyWarning = dutyWarning;
	}

	@Override
	@Transient
	public Map<String, String> getRecTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("W", "告警");
		return map;
	}

	public DutyRecordW() {}

	public DutyRecordW(Duty duty, Date startTime, String content, DutyWarning dutyWarning) {
		super(duty, startTime, content);
		this.dutyWarning = dutyWarning;
	}
}
