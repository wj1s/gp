package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.model.baseinfo.Operation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;

//由异态产生的引起停传的业务信息
@Entity
@DiscriminatorValue(value = "A")
public class AbnOperationA extends AbnOperation {
	private List<AccdDutyTime> accdDutyTimes = new ArrayList<AccdDutyTime>();//故障设备

	public AbnOperationA() {
		super();
	}

	public AbnOperationA(Operation operation, Date startTime, Date endTime, Integer sortby) {
		super(operation, startTime, endTime, sortby);
	}

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = super.getJson();
		jsonObject.put("type", "A");
		JSONArray accdDutyTimes = new JSONArray();
		for (AccdDutyTime accdDutyTime : this.accdDutyTimes) {
			accdDutyTimes.add(accdDutyTime.getJsonObject());
		}
		jsonObject.put("accdDutyTimes", accdDutyTimes.toString());
		return jsonObject.toString();
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "abnOperationA", fetch = FetchType.LAZY, targetEntity = AccdDutyTime.class)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OrderBy(value = "sortby")
	public List<AccdDutyTime> getAccdDutyTimes() {
		return accdDutyTimes;
	}

	public void setAccdDutyTimes(List<AccdDutyTime> accdDutyTimes) {
		this.accdDutyTimes = accdDutyTimes;
	}

}
