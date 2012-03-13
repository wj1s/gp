package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.duty.DutyRecordA;
import gov.abrs.etms.service.duty.DutyRecordService;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyRecordAAction extends GridAction<DutyRecordA> {

	private static final long serialVersionUID = -5248780093561722475L;

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
