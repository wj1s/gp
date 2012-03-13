package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Satellite;
import gov.abrs.etms.model.baseinfo.Transfer;
import gov.abrs.etms.service.baseinfo.SatelliteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TransferAction extends CrudAction<Transfer> {
	private static final long serialVersionUID = -6515264761126217793L;

	private String satelliteList;

	@Override
	protected void beforeUpdate(Transfer model) {
		model.setSatellite(null);
	}

	@Override
	protected void preSave(Transfer model) {
		model.setEmpName(this.getCurUser().getName());
		model.setUpdDate(this.getCurDate());
	}

	public String show() throws Exception {
		List<Satellite> satellites = this.satelliteService.getAll();
		satelliteList = this.assemblyJsonArray(satellites, "name");
		return SUCCESS;
	}

	private SatelliteService satelliteService;

	@Autowired
	public void setSatelliteService(SatelliteService satelliteService) {
		this.satelliteService = satelliteService;
	}

	public String getSatelliteList() {
		return satelliteList;
	}

	public void setSatelliteList(String satelliteList) {
		this.satelliteList = satelliteList;
	}

}
