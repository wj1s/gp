package gov.abrs.etms.action.repair;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.repair.Period;
import gov.abrs.etms.service.repair.PeriodService;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

public class PeriodAction extends GridAction<Period> {

	private static final long serialVersionUID = -6393594551226786867L;
	private String startDay;
	private String endDay;
	private Period periodInput;

	public String show() {
		return SUCCESS;
	}

	public String input() {
		if (this.getId() != null) {
			periodInput = this.periodService.get(id);
		}
		return "input";
	}

	@Override
	protected void preSave(Period model) {
		if (this.getId() != null) {
			if (this.periodService.get(id).getType().equals("2")) {
				Date start = DateUtil.createDate(startDay);
				Date end = DateUtil.createDate(endDay);
				model.setStartDay(start);
				model.setEndDay(end);

			}
		} else {
			if (this.getStartDay() != null) {
				Date start = DateUtil.createDate(startDay);
				Date end = DateUtil.createDate(endDay);
				model.setStartDay(start);
				model.setEndDay(end);
			}
		}
	}

	private PeriodService periodService;

	@Autowired
	public void setPeriodService(PeriodService periodService) {
		this.periodService = periodService;
	}

	public Period getPeriodInput() {
		return periodInput;
	}

	public void setPeriodInput(Period periodInput) {
		this.periodInput = periodInput;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

}
