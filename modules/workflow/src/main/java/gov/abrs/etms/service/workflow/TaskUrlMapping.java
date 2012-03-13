package gov.abrs.etms.service.workflow;

import java.util.HashMap;
import java.util.Map;

public class TaskUrlMapping {
	private static Map<String, String> taskNameMapp = TaskNameEN();

	public static Map<String, String> TaskNameEN() {
		Map<String, String> map = new HashMap<String, String>();
		//事故上报流程
		map.put("accdReport.director.audit", "/abnormal/abnormal!input.action");
		map.put("accdReport.officeclerk.audit", "/abnormal/abnormal!report.action");
		map.put("accdReport.leader.audit", "/abnormal/abnormal!review.action");
		map.put("accdReport.officeclerk.reaudit", "/abnormal/abnormal!report.action");
		//事故报表报表上报流程
		map.put("rept.accd.tekOfficer.report", "/report/abn-reporting!toSubmit.action");
		map.put("rept.accd.leader.audit", "/report/rept!toAudit.action?dataSource=ACCD");
		map.put("rept.accd.tekOfficer.reReport", "/report/abn-reporting!toReSubmit.action");
		//零报告上报流程
		map.put("rept.zero.tekOfficer.report", "/report/zero-reporting!toSubmit.action");
		map.put("rept.zero.leader.audit", "/report/zero-reporting!toAudit.action");
		map.put("rept.zero.tekOfficer.reReport", "/report/zero-reporting!toResubmit.action");
		//重要保证期报表报表上报流程
		map.put("rept.impt.tekOfficer.report", "/report/impt-reporting!toSubmit.action");
		map.put("rept.impt.leader.audit", "/report/rept!toAudit.action?dataSource=IMPT");
		map.put("rept.impt.tekOfficer.reReport", "/report/impt-reporting!toReSubmit.action");
		//技术安全事故上报流程
		map.put("tech.accd.tekOfficer.report", "/abnormal/tech-accident!toInsert.action");
		map.put("tech.accd.leader.audit", "/abnormal/tech-accident!toUpdate.action");
		map.put("tech.accd.tekOfficer.reReportR", "/abnormal/tech-accident!toUpdateR.action");
		map.put("tech.accd.tekOfficer.reReportH", "/abnormal/tech-accident!toUpdateH.action");
		//技术安全报告上报
		map.put("rept.tech.tekOfficer.report", "/report/tech-reporting!toSubmitTechReport.action");
		map.put("rept.tech.leader.audit", "/report/tech-reporting!toAuditTechReport.action");
		map.put("rept.tech.tekOfficer.reReport", "/report/tech-reporting!toReSubmitTechReport.action");
		return map;
	}

	public static String getTaskUrl(String taskNameEN) {
		String taskNameCN = taskNameMapp.get(taskNameEN);
		return taskNameCN;
	}
}
