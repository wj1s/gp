package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.OperationT;
import gov.abrs.etms.service.baseinfo.TransTypeService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class OperationTAction extends CrudAction<OperationT> {

	private static final long serialVersionUID = -6515264761126217793L;

	private String startDate;
	private String endDate;

	@Override
	protected void beforeUpdate(OperationT model) {
		model.setTransmitDef(null);
		model.setCawaveT(null);
		model.setRoute(null);
	}

	@Override
	protected void preSave(OperationT model) {
		model.setTransType(this.transTypeService.get("T"));
		model.setEmpName(this.getCurUser().getName());
		model.setUpdDate(this.getCurDate());
		Date start = DateUtil.createDate(startDate);
		if (!endDate.equals("")) {
			Date end = DateUtil.createDate(endDate);
			model.setEndDate(end);
		}
		model.setStartDate(start);
	}

	private TransTypeService transTypeService;

	@Autowired
	public void setTransTypeService(TransTypeService transTypeService) {
		this.transTypeService = transTypeService;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
