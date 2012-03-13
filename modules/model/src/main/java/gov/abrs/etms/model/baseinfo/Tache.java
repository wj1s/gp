package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//环节
@Entity
@Table(name = "TB_ETMS_BASE_TACHE")
public class Tache extends IdEntity implements Jsonable, AutoCompleteable {

	private String name;//环节名称
	private TransType transType;//传输类型
	private List<Equip> equips;//设备

	public Tache() {}

	public Tache(long id) {
		this.id = id;
	}

	public Tache(String name, TransType transType) {
		super();
		this.name = name;
		this.transType = transType;
	}

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("transType.codeDesc", this.getTransType().getCodeDesc());
		return jsonObject.toString();
	}

	@Transient
	@Override
	public String getAutoCompleteJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Transient
	@Override
	public List<Dept> getDeptsPopedom() {
		return getTransType().getDepts();
	}

	@Column(name = "TA_NAME", length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "TRANS_TYPE", nullable = false)
	public TransType getTransType() {
		return transType;
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "tache", fetch = FetchType.LAZY, targetEntity = Equip.class)
	public List<Equip> getEquips() {
		return equips;
	}

	public void setEquips(List<Equip> equips) {
		this.equips = equips;
	}

}
