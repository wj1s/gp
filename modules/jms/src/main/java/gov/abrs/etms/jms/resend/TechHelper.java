package gov.abrs.etms.jms.resend;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.TechAccident;
import gov.abrs.etms.model.baseinfo.Station;
import gov.abrs.etms.model.rept.ReptTechDtl;
import gov.abrs.etms.service.baseinfo.StationService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jbpm.graph.exe.Comment;
import org.springframework.beans.factory.annotation.Autowired;

public class TechHelper {

	private WorkFlowService workFlowService;

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	private StationService stationService;

	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	public JSONObject assembleAccd(TechAccident instance, String type) {
		JSONObject obj = new JSONObject();
		Station st = stationService.getStation();
		try {
			obj.put("type", type);//TECH || TECHR
			obj.put("accdCode", instance.getAccdCode());
			if (type.equalsIgnoreCase("TECH")) {
				obj.put("stationCode", st.getCode());
				obj.put("startTime", DateUtil.dateToString(instance.getStartTime(), DateUtil.FORMAT));
				if (instance.getEndDate() != null) {
					obj.put("endTime", DateUtil.dateToString(instance.getEndDate(), DateUtil.FORMAT));
				}
				obj.put("happenLocation", instance.getHappenLocation());
				obj.put("dutyPerson", instance.getDutyPerson());
				obj.put("accdDutyCode", instance.getAccdKind());
				obj.put("accdCourse", instance.getAccdCourse());
				obj.put("accdReason", instance.getAccdReason());
				obj.put("accdResult", instance.getAccdResult());
				obj.put("accdManage", instance.getAccdManage());
				obj.put("accdPrev", instance.getAccdPrev());
			}
			obj.put("updName", instance.getUpdName());
			obj.put("updDate", DateUtil.dateToString(instance.getUpdDate(), DateUtil.FORMAT));
			obj.put("endFlag", instance.getEndFlag());
			if (instance.getAccdResult() != null) {
				obj.put("accdResult", instance.getAccdResult());
			}
			obj.put("procCode", "A10");
			obj.put("techAuditList", assembleAccdAuditList("techAccident", instance.getId() + ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	//报表json
	public JSONObject assembleRept(ReptTechDtl instance) {
		JSONObject obj = new JSONObject();
		Station st = stationService.getStation();
		try {
			obj.put("stationCode", st.getCode());//台站
			obj.put("id", instance.getSeq() + "");//模版
			obj.put("reptId", instance.getTechReptDef().getReptSeq() + "");//报表
			obj.put("reptName", instance.getTechReptDef().getName());//哪个报表
			obj.put("attachName", instance.getAttachName());//哪个模版
			obj.put("uploadName", instance.getUploadName());//上传的文件名
			obj.put("tekofficer", instance.getTekofficer());//技办职员名
			obj.put("governor", instance.getTechReptDef().getName());//单位主管名
			obj.put("updDate", DateUtil.dateToString(instance.getUpdDate(), DateUtil.FORMAT));
			obj.put("procCode", "A10");
			obj.put("TECH", assembleAccdAuditList("reptTech", instance.getTechReptDef().getId() + ""));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	private JSONArray assembleAccdAuditList(String processName, String key) {
		JSONArray array = new JSONArray();
		List<Comment> comments = workFlowService.getCommentObjects(processName, key);
		for (Comment comment : comments) {
			JSONObject jsonComment = new JSONObject();
			jsonComment.put("auditTime", DateUtil.dateToString(comment.getTime(), DateUtil.FORMAT));
			String roleName = comment.getTaskInstance().getActorId();
			if ("ROLE_TEKOFFICER".equals(roleName)) {
				jsonComment.put("procCode", "A05");
			} else if ("ROLE_GOVERNOR".equals(roleName)) {
				jsonComment.put("procCode", "A10");
			}
			jsonComment.put("auditPerson", comment.getActorId());
			jsonComment.put("dscr", comment.getMessage());
			array.add(jsonComment);
		}
		return array;
	}
}
