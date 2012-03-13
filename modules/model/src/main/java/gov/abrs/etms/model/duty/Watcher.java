package gov.abrs.etms.model.duty;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "W")
public class Watcher extends StaffOnDuty {

	// Fields    
	private Duty dutyW; //班

	public Watcher() {

	}

	public Watcher(String name) {
		super(name);
	}

	public Watcher(Duty duty, String empName, Date startTime, Date endTime) {
		super(empName, startTime, endTime);
		this.dutyW = duty;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DU_W_ID")
	public Duty getDutyW() {
		return dutyW;
	}

	public void setDutyW(Duty dutyW) {
		this.dutyW = dutyW;
	}
}
