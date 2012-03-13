package gov.abrs.etms.action.abnormal;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.abnormal.AbnormalB;

public class AbnormalBAction extends GridAction<AbnormalB> {
	private static final long serialVersionUID = -6515264761126217793L;

	@Override
	public String load() {
		try {
			carrier.setSidx("updDate");
			carrier.setSord("desc");
			executeDAO.find(entityClass, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GRID;
	}

}