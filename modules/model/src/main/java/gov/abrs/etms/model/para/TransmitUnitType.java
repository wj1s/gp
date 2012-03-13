package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//发射单位类型
@Entity
@DiscriminatorValue(value = "TUTP")
public class TransmitUnitType extends ParaDtl {}
