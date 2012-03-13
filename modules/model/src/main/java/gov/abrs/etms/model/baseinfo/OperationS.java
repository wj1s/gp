package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.TransmitDef;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//卫星上行
@Entity
@DiscriminatorValue(value = "S")
public class OperationS extends Operation implements Jsonable {
	private Cawave cawaveS;//节目流
	private Transfer transfer;//转发器

	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = super.getJson();
		jsonObject.put("cawaveS.name", this.getCawaveS().getName());
		jsonObject.put("transfer.name", this.getTransfer().getName());
		jsonObject.put("satellite.name", this.getTransfer().getSatellite().getName());

		return jsonObject.toString();
	}

	public OperationS() {
		super();
	}

	public OperationS(long id) {
		super(id);
	}

	public OperationS(String name, TransmitDef transmitDef, TransType transType, Double cawaveFrq, Double simbleRate,
			Date startDate, Date updDate, String empName, Cawave cawave, Transfer transfer) {
		super(name, transmitDef, transType, cawaveFrq, simbleRate, startDate, updDate, empName);
		this.cawaveS = cawave;
		this.transfer = transfer;
	}

	@ManyToOne()
	@JoinColumn(name = "CAWAVE_S_ID", nullable = true)
	public Cawave getCawaveS() {
		return cawaveS;
	}

	public void setCawaveS(Cawave cawaveS) {
		this.cawaveS = cawaveS;
	}

	@ManyToOne()
	@JoinColumn(name = "SK_ID", nullable = true)
	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
}
