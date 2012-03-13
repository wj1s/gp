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

//集成平台
@Entity
@DiscriminatorValue(value = "P")
public class OperationP extends Operation implements Jsonable {
	private Program program;//节目
	private Cawave cawaveP;//节目流

	public OperationP() {
		super();
	}

	public OperationP(long id) {
		super(id);
	}

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = super.getJson();
		jsonObject.put("program.name", this.getProgram().getName());
		jsonObject.put("cawaveP.name", this.getCawaveP().getName());
		return jsonObject.toString();
	}

	public OperationP(String name, TransmitDef transmitDef, TransType transType, Double cawaveFrq, Double simbleRate,
			Date startDate, Date updDate, String empName, Program program) {
		super(name, transmitDef, transType, cawaveFrq, simbleRate, startDate, updDate, empName);
		this.program = program;
	}

	@ManyToOne()
	@JoinColumn(name = "CAWAVE_P_ID", nullable = true)
	public Cawave getCawaveP() {
		return cawaveP;
	}

	public void setCawaveP(Cawave cawaveP) {
		this.cawaveP = cawaveP;
	}

	@ManyToOne()
	@JoinColumn(name = "PROGRAM_ID", nullable = true)
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}
}
