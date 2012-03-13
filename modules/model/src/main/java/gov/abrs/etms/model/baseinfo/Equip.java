package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnEquip;
import gov.abrs.etms.model.abnormal.AbnormalB;
import gov.abrs.etms.model.abnormal.AbnormalF;
import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import com.google.common.collect.Lists;

//设备
@Entity
@DiscriminatorValue(value = "E")
public class Equip extends Device implements Jsonable, AutoCompleteable {

	private Tache tache;//所属环节
	private List<Channel> channels;//通路
	private List<AbnormalF> abnormalFs;//设备故障信息
	private List<AbnEquip> abnEquips;//由异态引起的设备异常
	private List<AbnormalB> abnormalBs;//由异态引发的故障信息

	public Equip() {
		super();
	}

	public Equip(long id) {
		super(id);
	}

	public Equip(String name, Dept dept, Boolean flag, String code, EquipType equipType, Date updDate, String empName,
			Tache tache, List<Channel> channels) {
		super(name, dept, flag, code, updDate, empName);
		this.tache = tache;
		this.channels = channels;
	}

	//grid专用json
	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("tache.name", this.getTache().getName());
		jsonObject.put("equipModel.equipType.codeDesc", this.getEquipModel().getEquipType().getCodeDesc());
		jsonObject.put("equipModel.name", this.getEquipModel().getName());
		jsonObject.put("updDate", DateUtil.getDateStr(this.getUpdDate()));
		jsonObject.put("empName", this.getEmpName());
		//jsonObject.put("techSystem.name", this.getTechSystem().getName());
		String channelStr = "";
		for (Channel channel : this.getChannels()) {
			channelStr += channel.getName() + ",";
		}
		if (!channelStr.equals("")) {
			channelStr = channelStr.substring(0, channelStr.length() - 1);
		}
		jsonObject.put("channels.name", channelStr);
		return jsonObject.toString();
	}

	@Override
	@Transient
	public String getAutoCompleteJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Override
	@Transient
	public List<Dept> getDeptsPopedom() {
		return Lists.newArrayList();
	}

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, targetEntity = Channel.class)
	@JoinTable(name = "TB_ETMS_BASE_CHANNEL_EQUIP_REF", joinColumns = { @JoinColumn(name = "EQUIP_ID") }, inverseJoinColumns = { @JoinColumn(name = "CHANNEL_ID") })
	public List<Channel> getChannels() {
		return channels;
	}

	public void setChannels(List<Channel> channels) {
		this.channels = channels;
	}

	@ManyToOne()
	@JoinColumn(name = "TA_ID", nullable = true)
	public Tache getTache() {
		return tache;
	}

	public void setTache(Tache tache) {
		this.tache = tache;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equipF", fetch = FetchType.LAZY, targetEntity = AbnormalF.class)
	public List<AbnormalF> getAbnormalFs() {
		return abnormalFs;
	}

	public void setAbnormalFs(List<AbnormalF> abnormalFs) {
		this.abnormalFs = abnormalFs;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equip", fetch = FetchType.LAZY, targetEntity = AbnEquip.class)
	public List<AbnEquip> getAbnEquips() {
		return abnEquips;
	}

	public void setAbnEquips(List<AbnEquip> abnEquips) {
		this.abnEquips = abnEquips;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equipB", fetch = FetchType.LAZY, targetEntity = AbnormalB.class)
	public List<AbnormalB> getAbnormalBs() {
		return abnormalBs;
	}

	public void setAbnormalBs(List<AbnormalB> abnormalBs) {
		this.abnormalBs = abnormalBs;
	}

}
