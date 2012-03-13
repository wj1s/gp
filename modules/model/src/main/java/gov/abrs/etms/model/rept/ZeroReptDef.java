package gov.abrs.etms.model.rept;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ZERO")
public class ZeroReptDef extends ReptPool {

}
