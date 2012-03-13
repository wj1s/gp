package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Satellite;

public class SatelliteAction extends CrudAction<Satellite> {
	private static final long serialVersionUID = -6515264761126217793L;

	@Override
	protected void preSave(Satellite model) {
		model.setEmpName(this.getCurUser().getName());
		model.setUpdDate(this.getCurDate());
	}

	public String show() throws Exception {
		return SUCCESS;
	}

}
