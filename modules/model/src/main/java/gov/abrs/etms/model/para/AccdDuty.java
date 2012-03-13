package gov.abrs.etms.model.para;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ADDU")
public class AccdDuty extends ParaDtl {}
