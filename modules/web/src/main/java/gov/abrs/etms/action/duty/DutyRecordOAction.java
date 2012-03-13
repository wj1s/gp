package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.duty.DutyRecordO;
import gov.abrs.etms.service.duty.DutyRecordService;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyRecordOAction extends GridAction<DutyRecordO> {
	private static final long serialVersionUID = 3038652978931265236L;

	@Override
	public String save() {
		Date now = getCurDate();
		model.setStartTime(now);

		this.dutyRecordService.save(model);
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("id", model.getId());
		result.put("content", model.getContent());
		json = result.toString();
		return EASY;
	}

	private DutyRecordService dutyRecordService;

	@Autowired
	public void setDutyRecordService(DutyRecordService dutyRecordService) {
		this.dutyRecordService = dutyRecordService;
	}
}
