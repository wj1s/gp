package gov.abrs.etms.model.repair;

import gov.abrs.etms.model.baseinfo.Equip;
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

/**
 * Card entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_CARD")
public class Card extends IdEntity implements Jsonable {

	// Fields

	private CycleCell cycleCell;//检修区间
	private Period period;//检修周期
	private double shutdownTime;//停机时间
	private double processTime;//需用时间
	private String tools;//仪器工具
	private String measure;//安全措施
	private String attention;//注意事项
	private String other;//其它措施
	private String methods;//检修方法
	private String technicalStandards;//技术标准
	private String remark;//备注
	private String name;//名称
	private String active;//启用/停用
	private String equipType;//设备类型
	private List<RecordItem> recordItems;//检修记录项
	private Equip equip;//设备

	// Constructors
	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("measure", this.getMeasure());
		jsonObject.put("methods", this.getMethods());
		jsonObject.put("technicalStandards", this.getTechnicalStandards());
		jsonObject.put("period.name", this.getPeriod().getName());
		jsonObject.put("cycleCell.name", this.getCycleCell().getName());

		return jsonObject.toString();
	}

	/** default constructor */
	public Card() {}

	public Card(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public Card(long id, CycleCell cycleCell, Period period, String name, String active) {
		this.id = id;
		this.cycleCell = cycleCell;
		this.period = period;
		this.name = name;
		this.active = active;
	}

	/** full constructor */
	public Card(long id, CycleCell cycleCell, Period period, double shutdownTime, double processTime, String tools,
			String measure, String attention, String other, String methods, String technicalStandards, String remark,
			String name, String active, String equipType, List<RecordItem> recordItems) {
		this.id = id;
		this.cycleCell = cycleCell;
		this.period = period;
		this.shutdownTime = shutdownTime;
		this.processTime = processTime;
		this.tools = tools;
		this.measure = measure;
		this.attention = attention;
		this.other = other;
		this.methods = methods;
		this.technicalStandards = technicalStandards;
		this.remark = remark;
		this.name = name;
		this.active = active;
		this.equipType = equipType;
		this.recordItems = recordItems;
	}

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CELL_ID", nullable = false)
	public CycleCell getCycleCell() {
		return this.cycleCell;
	}

	public void setCycleCell(CycleCell cycleCell) {
		this.cycleCell = cycleCell;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERIOD_ID", nullable = false)
	public Period getPeriod() {
		return this.period;
	}

	public void setPeriod(Period period) {
		this.period = period;
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

	@Column(name = "TOOLS", length = 600)
	public String getTools() {
		return this.tools;
	}

	public void setTools(String tools) {
		this.tools = tools;
	}

	@Column(name = "MEASURE", length = 600)
	public String getMeasure() {
		return this.measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	@Column(name = "ATTENTION", length = 600)
	public String getAttention() {
		return this.attention;
	}

	public void setAttention(String attention) {
		this.attention = attention;
	}

	@Column(name = "OTHER", length = 600)
	public String getOther() {
		return this.other;
	}

	public void setOther(String other) {
		this.other = other;
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

	@Column(name = "REMARK", length = 600)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ACTIVE", nullable = false, length = 1)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name = "EQUIP_TYPE", length = 3)
	public String getEquipType() {
		return this.equipType;
	}

	public void setEquipType(String equipType) {
		this.equipType = equipType;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "card")
	public List<RecordItem> getRecordItems() {
		return this.recordItems;
	}

	public void setRecordItems(List<RecordItem> recordItems) {
		this.recordItems = recordItems;
	}

	@ManyToOne
	@JoinColumn(name = "EQUIP_ID")
	public Equip getEquip() {
		return equip;
	}

	public void setEquip(Equip equip) {
		this.equip = equip;
	}
}