package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//通路
@Entity
@Table(name = "TB_ETMS_BASE_CHANNEL")
public class Channel extends IdEntity implements Jsonable {

	private String name;//通路名称
	private TechSystem techSystem;//所属技术系统
	private List<Equip> equips;//设备
	private List<Schedule> schedules;//运行图

	public Channel() {}

	public Channel(long id) {
		this.id = id;
	}

	public Channel(String name, TechSystem techSystem) {
		super();
		this.name = name;
		this.techSystem = techSystem;
	}

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("techSystem.name", this.getTechSystem().getName());
		return jsonObject.toString();
	}

	@Column(name = "CHANNEL_NAME", length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "SYSTEM_ID", nullable = false)
	public TechSystem getTechSystem() {
		return techSystem;
	}

	public void setTechSystem(TechSystem techSystem) {
		this.techSystem = techSystem;
	}

	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "channels")
	public List<Equip> getEquips() {
		return equips;
	}

	public void setEquips(List<Equip> equips) {
		this.equips = equips;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "channel", fetch = FetchType.LAZY, targetEntity = Schedule.class)
	public List<Schedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}
}
