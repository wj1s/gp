package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.TechSystemService;
import gov.abrs.etms.service.baseinfo.TransTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TechSystemAction extends CrudAction<TechSystem> {

	private static final long serialVersionUID = -6515264761126217793L;

	private List<TransType> transTypesPer;
	private Long transTypeId;
	private String systemName;

	public String show() throws Exception {
		transTypesPer = this.getCurTransTypes(FunModule.BASEINFO);
		return SUCCESS;
	}

	//系统通路的ajax
	public String getChannelsAjax() throws Exception {
		list = this.techSystemService.getByName(systemName).getChannels();
		return NORMAL;
	}

	@Override
	public String load() {
		techSystemService.get(carrier, transTypeId);
		return GRID;
	}

	@Override
	protected void preSave(TechSystem techSystem) {
		TransType transType = this.transTypeService.get(transTypeId);
		techSystem.setTransType(transType);
	}

	private TransTypeService transTypeService;
	private TechSystemService techSystemService;

	@Autowired
	public void setTransTypeService(TransTypeService transTypeService) {
		this.transTypeService = transTypeService;
	}

	@Autowired
	public void setTechSystemService(TechSystemService techSystemService) {
		this.techSystemService = techSystemService;
	}

	public Long getTransTypeId() {
		return transTypeId;
	}

	public void setTransTypeId(Long transTypeId) {
		this.transTypeId = transTypeId;
	}

	public List<TransType> getTransTypesPer() {
		return transTypesPer;
	}

	public void setTransTypesPer(List<TransType> transTypesPer) {
		this.transTypesPer = transTypesPer;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

}
