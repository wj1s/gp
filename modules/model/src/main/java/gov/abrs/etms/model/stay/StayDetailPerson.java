package gov.abrs.etms.model.stay;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

//留守明晰人员
@Entity
@Table(name = "TB_ETMS_STAY_DETAIL_PERSON")
public class StayDetailPerson {

	private DetailPersonId id;

	public StayDetailPerson() {}

	public StayDetailPerson(StayDetail stayDetail, String empName) {
		this.id = new DetailPersonId(stayDetail, empName);
	}

	@EmbeddedId
	public DetailPersonId getId() {
		return id;
	}

	public void setId(DetailPersonId id) {
		this.id = id;
	}

}
