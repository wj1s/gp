package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.para.AbnType;
import gov.abrs.etms.model.para.TransType;

import java.util.Date;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//故障
@Entity
@DiscriminatorValue(value = "F")
public class AbnormalF extends Abnormal {
	private Equip equipF;//故障设备

	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = getJson();
		jsonObject.put("type", "F");
		jsonObject.put("equipF", equipF.getJsonObject());
		jsonObject.put("equipF_name", this.getEquipF().getName());
		return jsonObject.toString();
	}

	public AbnormalF() {
		super();
	}

	public AbnormalF(TransType transType, Date startTime, Date endTime, AbnType abnType, String desc, String reason,
			String processStep, Date updDate, String empName, List<AbnOperation> abnOperations, Equip equipF) {
		super(transType, startTime, endTime, abnType, desc, reason, processStep, updDate, empName, abnOperations);
		this.equipF = equipF;
	}

	@ManyToOne()
	@JoinColumn(name = "EQUIP_F_ID", nullable = true)
	public Equip getEquipF() {
		return equipF;
	}

	public void setEquipF(Equip equipF) {
		this.equipF = equipF;
	}
}
