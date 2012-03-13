package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.service.baseinfo.ParaDtlService;

import org.springframework.beans.factory.annotation.Autowired;

public class ParaDtlAction extends CrudAction<ParaDtl> {

	private static final long serialVersionUID = -1464836926556004459L;

	public String getEquipModelsAjax() {
		list = ((EquipType) paraDtlService.get(id)).getEquipModels();
		return NORMAL;
	}

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}
}
