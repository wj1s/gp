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

//节目源传输
@Entity
@DiscriminatorValue(value = "T")
public class OperationT extends Operation implements Jsonable {
	private Cawave cawaveT;//节目流
	private Route route;//路由

	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = super.getJson();
		jsonObject.put("cawaveT.name", this.getCawaveT().getName());
		jsonObject.put("route.pl", this.getRoute().getPl());
		return jsonObject.toString();
	}

	public OperationT() {
		super();
	}

	public OperationT(long id) {
		super(id);
	}

	public OperationT(String name, TransmitDef transmitDef, TransType transType, Double cawaveFrq, Double simbleRate,
			Date startDate, Date updDate, String empName, Cawave cawave, Route route) {
		super(name, transmitDef, transType, cawaveFrq, simbleRate, startDate, updDate, empName);
		this.cawaveT = cawave;
		this.route = route;
	}

	@ManyToOne()
	@JoinColumn(name = "CAWAVE_T_ID", nullable = true)
	public Cawave getCawaveT() {
		return cawaveT;
	}

	public void setCawaveT(Cawave cawaveT) {
		this.cawaveT = cawaveT;
	}

	@ManyToOne()
	@JoinColumn(name = "ROUTE_ID", nullable = true)
	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}
}
