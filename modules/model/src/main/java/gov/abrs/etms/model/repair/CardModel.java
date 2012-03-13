package gov.abrs.etms.model.repair;

import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

/**
 * TbEtmsRepairCardmodel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_CARD_MODEL")
public class CardModel extends IdEntity implements Jsonable {

	// Fields    
	private String active;//启用/停用
	private String name;//名称
	private EquipType equipType;//设备类型
	private double shutdownTime;//停机时间
	private double processTime;//需用时间
	private String measure;//安全措施
	private String tools;//仪器工具
	private String other;//其它措施
	private String attention;
	private String methods;//检修方法
	private String technicalStandards;//技术标准
	private String remark;//备注

	// Constructors

	/** default constructor */
	public CardModel() {}

	/** minimal constructor */
	public CardModel(long id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public CardModel(long id, String active, String name, EquipType equipType, double shutdownTime, double processTime,
			String measure, String tools, String other, String attention, String methods, String technicalStandards,
			String remark) {
		this.id = id;
		this.active = active;
		this.name = name;
		this.equipType = equipType;
		this.shutdownTime = shutdownTime;
		this.processTime = processTime;
		this.measure = measure;
		this.tools = tools;
		this.other = other;
		this.attention = attention;
		this.methods = methods;
		this.technicalStandards = technicalStandards;
		this.remark = remark;
	}

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("measure", this.getMeasure());
		jsonObject.put("remark", this.getRemark());
		jsonObject.put("methods", this.getMethods());
		jsonObject.put("technicalStandards", this.getTechnicalStandards());
		jsonObject.put("equipType.codeDesc", this.getEquipType().getCodeDesc());
		return jsonObject.toString();
	}

	@Column(name = "ACTIVE", length = 1)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EQUIP_TYPE")
	public EquipType getEquipType() {
		return this.equipType;
	}

	public void setEquipType(EquipType equipType) {
		this.equipType = equipType;
	}

	@Column(name = "SHUTDOWN_TIME", precision = 6)
	public double getShutdownTime() {
		return this.shutdownTime;
	}

	public void setShutdownTime(double shutdownTime) {
		this.shutdownTime = shutdownTime;
	}

	@Column(name = "PROCESS_TIME", precision = 6)
	public double getProcessTime() {
		return this.processTime;
	}

	public void setProcessTime(double processTime) {
		this.processTime = processTime;
	}

	@Column(name = "MEASURE", length = 600)
	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	@Column(name = "TOOLS", length = 600)
	public String getTools() {
		return this.tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	@Column(name = "OTHER", length = 600)
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Column(name = "ATTENTION", length = 600)
	public String getAttention() {
		return this.attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	@Column(name = "METHODS", length = 4000)
	public String getMethods() {
		return this.methods;
	}

	public void setMethods(String methods) {
		this.methods = methods;
	}

	@Column(name = "TECHNICAL_STANDARDS", length = 1024)
	public String getTechnicalStandards() {
		return this.technicalStandards;
	}

	public void setTechnicalStandards(String technicalStandards) {
		this.technicalStandards = technicalStandards;
	}

	@Column(name = "REMARK", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}