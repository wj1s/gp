package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.EquipModel;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.service.baseinfo.ChannelService;
import gov.abrs.etms.service.baseinfo.EquipModelService;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.baseinfo.TacheService;
import gov.abrs.etms.service.baseinfo.TechSystemService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class EquipAction extends CrudAction<Equip> {

	private static final long serialVersionUID = -5917228913239404886L;

	private List<ParaDtl> equiptypeList;
	private List<TechSystem> techSystemList;
	private List<Equip> equipLists;
	private List<Tache> tacheLists;
	private List<Long> channelIds;
	private String dealTime;
	private String runTime;
	private List<ParaDtl> equipTypes;
	private List<EquipModel> equipModelList;
	private Equip equip;
	private String transType;
	private String autoData;

	public String getAutoData() {
		return autoData;
	}

	public void setAutoData(String autoData) {
		this.autoData = autoData;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String show() {
		return SUCCESS;
	}

	public String input() {
		techSystemService.getAll();
		if (id != null) {
			equip = this.equipService.get(id);
		}
		equipTypes = paraDtlService.get(EquipType.class);
		tacheLists = tacheService.getAll();
		return "input";
	}

	@SuppressWarnings("unchecked")
	public String autocompleteTAjax() throws UnsupportedEncodingException {

		q = new String(q.getBytes("iso-8859-1"), "UTF-8");
		List<Object> tempList = equipService.getByNameLike(entityClass, q);
		//判断此类对象是否与部门有关系
		boolean classWithDept = false;
		for (Object object : tempList) {
			if (((AutoCompleteable) object).getDeptsPopedom().size() > 0) {
				classWithDept = true;
				break;
			}
		}
		List finalList = Lists.newArrayList();
		//当传入的自动完成url带权限信息，并且此类自动完成的对象与dept有关系时，对结果进行按权限筛选
		if (!"".equals(funModule) && classWithDept) {
			List<Dept> depts = getCurUser().getDeptsFun(FunModule.getFunModule(funModule));
			for (Object obj : tempList) {
				List<Dept> deptTarget = ((AutoCompleteable) obj).getDeptsPopedom();
				List<Dept> listTarget = (List<Dept>) CollectionUtils.intersection(depts, deptTarget);
				if (deptTarget.size() == 0 || listTarget != null && listTarget.size() > 0) {
					finalList.add(obj);
				}
			}
			list = finalList;
		} else {
			list = tempList;
		}
		return AUTO;
	}

	@Override
	protected void preSave(Equip equip) {
		//	TechSystem techSystem = techSystemService.getByName(equip.getTechSystem().getName());
		//	techSystemService.getAll();
		List<Channel> channels = Lists.newArrayList();
		if (channelIds != null && channelIds.size() != 0) {
			for (Long channelId : channelIds) {
				Channel channel = channelService.get(channelId);
				channels.add(channel);
			}
		}
		Tache tache = tacheService.getByName(equip.getTache().getName());
		if (equip.getId() != null) {
			equip = equipService.get(id);
			equip.getChannels().clear();
			equip.getChannels().addAll(channels);
		} else {
			equip.setChannels(channels);
		}
		if (equip.getEquipModel().getId() != null) {
			equip.setEquipModel(equipModelService.get(equip.getEquipModel().getId()));
		}
		equip.setTache(tache);
		equip.setEmpName(getCurUser().getName());
		equip.setUpdDate(getCurDate());
	}

	private ParaDtlService paraDtlService;
	private TacheService tacheService;
	private EquipModelService equipModelService;
	private ChannelService channelService;
	private TechSystemService techSystemService;
	private EquipService equipService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Autowired
	public void setTechSystemService(TechSystemService techSystemService) {
		this.techSystemService = techSystemService;
	}

	@Autowired
	public void setTacheService(TacheService tacheService) {
		this.tacheService = tacheService;
	}

	@Autowired
	public void setEquipModelService(EquipModelService equipModelService) {
		this.equipModelService = equipModelService;
	}

	@Autowired
	public void setEquipService(EquipService equipService) {
		this.equipService = equipService;
	}

	public void setList(List<Equip> list) {
		this.list = list;
	}

	public List<ParaDtl> getEquiptypeList() {
		return equiptypeList;
	}

	public void setEquiptypeList(List<ParaDtl> equiptypeList) {
		this.equiptypeList = equiptypeList;
	}

	public List<Equip> getEquipLists() {
		return equipLists;
	}

	public void setEquipLists(List<Equip> equipLists) {
		this.equipLists = equipLists;
	}

	public List<Tache> getTacheLists() {
		return tacheLists;
	}

	public void setTacheLists(List<Tache> tacheLists) {
		this.tacheLists = tacheLists;
	}

	public String getDealTime() {
		return dealTime;
	}

	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public List<ParaDtl> getEquipTypes() {
		return equipTypes;
	}

	public void setEquipTypes(List<ParaDtl> equipTypes) {
		this.equipTypes = equipTypes;
	}

	public List<TechSystem> getTechSystemList() {
		return techSystemList;
	}

	public void setTechSystemList(List<TechSystem> techSystemList) {
		this.techSystemList = techSystemList;
	}

	public List<EquipModel> getEquipModelList() {
		return equipModelList;
	}

	public void setEquipModelList(List<EquipModel> equipModelList) {
		this.equipModelList = equipModelList;
	}

	public List<Long> getChannelIds() {
		return channelIds;
	}

	public void setChannelIds(List<Long> channelIds) {
		this.channelIds = channelIds;
	}

	public Equip getEquip() {
		return equip;
	}

	public void setEquip(Equip equip) {
		this.equip = equip;
	}

}
