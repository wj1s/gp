package gov.abrs.etms.model.duty;

import gov.abrs.etms.model.abnormal.Abnormal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

//异态记录
@Entity
@DiscriminatorValue(value = "A")
public class DutyRecordA extends DutyRecord {

	private Abnormal abnormal; //异态

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ABN_ID")
	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	@Override
	@Transient
	public Map<String, String> getRecTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("A", "异态");
		return map;
	}

	public DutyRecordA() {}

	public DutyRecordA(Duty duty, Date startTime, String content, Abnormal abnormal) {
		super(duty, startTime, content);
		this.abnormal = abnormal;
	}
}
