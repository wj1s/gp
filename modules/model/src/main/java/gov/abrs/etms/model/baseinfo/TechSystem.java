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

//技术系统
@Entity
@Table(name = "TB_ETMS_BASE_TECHSYSTEM")
public class TechSystem extends IdEntity implements Jsonable, AutoCompleteable {

	private String code;//技术系统代码
	private String name;//技术系统名称
	private TransType transType;//传输类型
	private List<Channel> channels;//拥有通路

	public TechSystem() {}

	public TechSystem(long id) {
		this.id = id;
	}

	public TechSystem(String code, String name, TransType transType) {
		super();
		this.code = code;
		this.name = name;
		this.transType = transType;
	}

	@Transient
	@Override
	public List<Dept> getDeptsPopedom() {
		return getTransType().getDepts();
	}

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("code", this.getCode());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Transient
	public String getAutoCompleteJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Column(name = "TECH_CODE", length = 4, nullable = false, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "TECH_NAME", length = 60, nullable = true)
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "techSystem", fetch = FetchType.LAZY, targetEntity = Channel.class)
	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}
}
