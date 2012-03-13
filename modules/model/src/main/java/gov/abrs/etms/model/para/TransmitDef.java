package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "TRMD")
public class TransmitDef extends ParaDtl {

	public TransmitDef() {
		super();
	}

	public TransmitDef(Long id) {
		super(id);
	}
}
