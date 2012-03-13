package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.para.EquipType;
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

//设备型号
@Entity
@Table(name = "TB_ETMS_BASE_EQUIP_MODEL")
public class EquipModel extends IdEntity implements Jsonable {

	private String name;//型号标识
	private String equipModel;
	private EquipType equipType;//设备类型
	private Integer demand;
	private Long seqNo;
	private List<Device> devices;//部门备件

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Column(name = "MODEL_NAME", length = 64, nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "EQUIP_MODEL", length = 3, nullable = false)
	public String getEquipModel() {
		return equipModel;
	}

	public void setEquipModel(String equipModel) {
		this.equipModel = equipModel;
	}

	@ManyToOne()
	@JoinColumn(name = "EQUIP_TYPE", nullable = true)
	public EquipType getEquipType() {
		return equipType;
	}

	public void setEquipType(EquipType equipType) {
		this.equipType = equipType;
	}

	@Column(name = "DEMAND", nullable = true)
	public Integer getDemand() {
		return demand;
	}

	public void setDemand(Integer demand) {
		this.demand = demand;
	}

	@Column(name = "SEQ_NO", nullable = true)
	public Long getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Long seqNo) {
		this.seqNo = seqNo;
	}

	//增加设备的匹配
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equipModel", fetch = FetchType.LAZY, targetEntity = Device.class)
	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}
}
