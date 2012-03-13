package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DBBD")
public class BroadByStation extends ParaDtl {

	public BroadByStation() {
		super();
	}

	public BroadByStation(Long id) {
		super(id);
	}

}
