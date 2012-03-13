package gov.abrs.etms.model.baseinfo;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//设备
@Entity
@DiscriminatorValue(value = "N")
public class DeviceNotEquip extends Device {

	public DeviceNotEquip() {
		super();
	}

	public DeviceNotEquip(long id) {
		super(id);
	}

}
