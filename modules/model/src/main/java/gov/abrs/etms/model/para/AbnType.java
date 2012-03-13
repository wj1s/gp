package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ABTP")
public class AbnType extends ParaDtl {
	public AbnType() {}

	public AbnType(Long id) {
		super(id);
	}
}
