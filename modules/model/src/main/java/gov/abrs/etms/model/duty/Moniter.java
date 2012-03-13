package gov.abrs.etms.model.duty;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue(value = "M")
public class Moniter extends StaffOnDuty {

	// Fields    
	private Duty dutyM; //班

	public Moniter() {

	}

	public Moniter(Duty duty, String empName, Date startTime, Date endTime) {
		super(empName, startTime, endTime);
		this.dutyM = duty;
	}

	public Moniter(String name) {
		super(name);
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DU_M_ID")
	public Duty getDutyM() {
		return dutyM;
	}

	public void setDutyM(Duty dutyM) {
		this.dutyM = dutyM;
	}
}
