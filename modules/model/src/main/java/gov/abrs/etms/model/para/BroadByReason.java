package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "DBRN")
public class BroadByReason extends ParaDtl {

	public BroadByReason() {
		super();
	}

	public BroadByReason(Long id) {
		super(id);
	}

}
