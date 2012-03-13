package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//备件
@Entity
@Table(name = "TB_ETMS_BASE_EQUIP")
@DiscriminatorColumn(name = "IS_EQUIP", discriminatorType = DiscriminatorType.STRING, length = 1)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Device extends IdEntity {

	private String name;//设备名称
	private EquipModel equipModel;//设备型号
	private Date updDate;//更新时间
	private String empName;//操作员

	//以下备用
	private Dept dept;//所属部门
	private Boolean flag;//整机标识
	private String code;//设备序列号
	private String sn;//设备标识
	private Date dealTime;//到货时间
	private Date runTime;//投入运行时间
	private Integer designLife;//设计使用寿命
	private String position;//使用位置
	private String equipStatus;//设备状态
	private Equip oriEquip;//现归属设备
	private Equip paEquip;//现用于设备
	private String productAddr;//产地
	private String maName;//生产商

	public Device() {
		super();
	}

	public Device(long id) {
		this.id = id;
	}

	public Device(String name, Dept dept, Boolean flag, String code, Date updDate, String empName) {
		super();
		this.name = name;
		this.dept = dept;
		this.flag = flag;
		this.code = code;
		this.updDate = updDate;
		this.empName = empName;
	}

	@Column(name = "EQUIP_NAME", length = 50, nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "EQUIP_MODEL", nullable = true)
	public EquipModel getEquipModel() {
		return equipModel;
	}

	public void setEquipModel(EquipModel equipModel) {
		this.equipModel = equipModel;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "EMP_NAME", length = 20, nullable = false)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	//以下备用

	@ManyToOne()
	@JoinColumn(name = "DP_ID", nullable = true)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "EQUIP_FLAG", nullable = true)
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	@Column(name = "EQUIP_CODE", length = 24, nullable = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "EQUIP_SN", length = 20, nullable = true)
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	@Column(name = "DEAL_TIME", nullable = true)
	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	@Column(name = "RUN_TIME", nullable = true)
	public Date getRunTime() {
		return runTime;
	}

	public void setRunTime(Date runTime) {
		this.runTime = runTime;
	}

	@Column(name = "DESIGN_LIFE", nullable = true)
	public Integer getDesignLife() {
		return designLife;
	}

	public void setDesignLife(Integer designLife) {
		this.designLife = designLife;
	}

	@Column(name = "POSITION", length = 4, nullable = true)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Column(name = "EQUIP_STATUS", length = 4, nullable = true)
	public String getEquipStatus() {
		return equipStatus;
	}

	public void setEquipStatus(String equipStatus) {
		this.equipStatus = equipStatus;
	}

	@ManyToOne()
	@JoinColumn(name = "ORI_EQUIP_ID", nullable = true)
	public Equip getOriEquip() {
		return oriEquip;
	}

	public void setOriEquip(Equip oriEquip) {
		this.oriEquip = oriEquip;
	}

	@ManyToOne()
	@JoinColumn(name = "PA_EQUIP_ID", nullable = true)
	public Equip getPaEquip() {
		return paEquip;
	}

	public void setPaEquip(Equip paEquip) {
		this.paEquip = paEquip;
	}

	@Column(name = "PRODUCT_ADDR", length = 20, nullable = true)
	public String getProductAddr() {
		return productAddr;
	}

	public void setProductAddr(String productAddr) {
		this.productAddr = productAddr;
	}

	@Column(name = "MA_NAME", length = 20, nullable = true)
	public String getMaName() {
		return maName;
	}

	public void setMaName(String maName) {
		this.maName = maName;
	}

}