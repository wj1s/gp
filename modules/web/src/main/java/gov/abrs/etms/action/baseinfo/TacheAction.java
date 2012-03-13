package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.TacheService;
import gov.abrs.etms.service.baseinfo.TransTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TacheAction extends CrudAction<Tache> {
	private static final long serialVersionUID = -6515264761126217793L;
	private List<TransType> transTypesPer;
	private Long transTypeId;

	public String show() throws Exception {
		transTypesPer = this.getCurTransTypes(FunModule.BASEINFO);
		return SUCCESS;
	}

	@Override
	public String load() {
		tacheService.get(carrier, transTypeId);
		return GRID;
	}

	@Override
	protected void preSave(Tache tache) {
		TransType transType = this.transTypeService.get(transTypeId);
		tache.setTransType(transType);
	}

	private TransTypeService transTypeService;
	private TacheService tacheService;

	@Autowired
	public void setTransTypeService(TransTypeService transTypeService) {
		this.transTypeService = transTypeService;
	}

	@Autowired
	public void setTacheService(TacheService tacheService) {
		this.tacheService = tacheService;
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

}
