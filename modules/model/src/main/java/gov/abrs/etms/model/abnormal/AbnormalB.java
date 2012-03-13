package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.model.baseinfo.Equip;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//由异态引起的故障信息
@Entity
@DiscriminatorValue(value = "B")
public class AbnormalB extends Abnormal {

	private Equip equipB;//故障设备

	private AbnEquip abnEquip;//

	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = super.getJson();
		jsonObject.put("equipB_name", this.getEquipB().getName());
		return jsonObject.toString();
	}

	@OneToOne(cascade = CascadeType.PERSIST, mappedBy = "abnormalB", optional = false)
	public AbnEquip getAbnEquip() {
		return abnEquip;
	}

	public void setAbnEquip(AbnEquip abnEquip) {
		this.abnEquip = abnEquip;
	}

	@ManyToOne()
	@JoinColumn(name = "EQUIP_B_ID", nullable = true)
	public Equip getEquipB() {
		return equipB;
	}

	public void setEquipB(Equip equipB) {
		this.equipB = equipB;
	}

}
