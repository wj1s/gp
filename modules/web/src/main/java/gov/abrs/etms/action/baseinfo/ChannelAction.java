package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.baseinfo.TechSystemService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ChannelAction extends CrudAction<Channel> {
	private static final long serialVersionUID = -6515264761126217793L;

	private String transTypeList;

	private String techSystemList;

	@Override
	protected void beforeUpdate(Channel model) {
		model.setTechSystem(null);
	}

	public String show() throws Exception {
		List<ParaDtl> transTypes = this.paraDtlService.get(TransType.class);
		transTypeList = this.assemblyJsonArray(transTypes, "codeDesc");
		List<TechSystem> techSystems = this.techSystemService.getAll();
		techSystemList = this.assemblyJsonArray(techSystems, "name");

		return SUCCESS;
	}

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	private TechSystemService techSystemService;

	@Autowired
	public void setTechSystemService(TechSystemService techSystemService) {
		this.techSystemService = techSystemService;
	}

	public String getTransTypeList() {
		return transTypeList;
	}

	public void setTransTypeList(String transTypeList) {
		this.transTypeList = transTypeList;
	}

	public String getTechSystemList() {
		return techSystemList;
	}

	public void setTechSystemList(String techSystemList) {
		this.techSystemList = techSystemList;
	}

}
