package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.duty.DutyRecordP;
import gov.abrs.etms.model.duty.PatrolTime;
import gov.abrs.etms.service.duty.DutyRecordService;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class DutyRecordPAction extends GridAction<DutyRecordP> {

	private static final long serialVersionUID = 2063985437276126519L;

	@Override
	public String save() {
		Date now = getCurDate();
		model.setStartTime(now);
		PatrolTime patrolTime = model.getPatrolTime();
		patrolTime.setDdate(now);
		String contentStr = patrolTime.getEmpName() + "于" + DateUtil.getDateCNHMStr(patrolTime.getStartTime()) + " 到 ";
		contentStr += DateUtil.getDateCNHMStr(patrolTime.getEndTime()) + "巡视值班情况。 详细内容：" + patrolTime.getContent();

		model.setContent(contentStr);
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
