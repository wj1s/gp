package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "PGTP")
public class ProgramType extends ParaDtl {

	public ProgramType() {
		super();
	}

	public ProgramType(Long id) {
		super(id);
	}

}
