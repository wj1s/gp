package gov.abrs.etms.model.duty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

//其他记录
@Entity
@DiscriminatorValue(value = "O")
public class DutyRecordO extends DutyRecord {

	@Override
	@Transient
	public Map<String, String> getRecTypeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("O", "其他");
		return map;
	}

	public DutyRecordO() {}

	public DutyRecordO(Duty duty, Date startTime, String content) {
		super(duty, startTime, content);
	}
}
