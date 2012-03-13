package gov.abrs.etms.model.stay;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

//留守人员
@Entity
@Table(name = "TB_ETMS_STAY_SECTION_PERSON")
public class StaySectionPerson {

	private SectionPersonId id;

	public StaySectionPerson() {}

	public StaySectionPerson(StaySection staySection, String empName) {
		this.id = new SectionPersonId(staySection, empName);
	}

	@EmbeddedId
	public SectionPersonId getId() {
		return id;
	}

	public void setId(SectionPersonId id) {
		this.id = id;
	}

}
