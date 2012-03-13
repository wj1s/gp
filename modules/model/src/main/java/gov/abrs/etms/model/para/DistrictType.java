package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//台站归属
@Entity
@DiscriminatorValue(value = "DTTP")
public class DistrictType extends ParaDtl {}
