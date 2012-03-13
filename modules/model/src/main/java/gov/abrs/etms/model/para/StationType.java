package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "STTP")
public class StationType extends ParaDtl {
	public StationType() {
		super();
	}

	public StationType(Long id) {
		super(id);
	}

}
