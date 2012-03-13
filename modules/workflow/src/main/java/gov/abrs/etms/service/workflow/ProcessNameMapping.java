package gov.abrs.etms.service.workflow;

import gov.abrs.etms.model.rept.ProcessEnum;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ProcessNameMapping {
	private static Map<String, String> taskNameMapp = TaskNameEN();

	public static Map<String, String> TaskNameEN() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(ProcessEnum.ACCD_REPORT.getDataSource(), "事故上报流程");
		map.put(ProcessEnum.REPT_ACCD.getDataSource(), "事故报表整合上报流程");
		map.put(ProcessEnum.REPT_ZERO.getDataSource(), "零报告上报流程");
		map.put(ProcessEnum.REPT_IMPT.getDataSource(), "保证期报表上报流程");
		map.put(ProcessEnum.ACCD_TECH.getDataSource(), "技术安全事故上报流程");
		map.put(ProcessEnum.REPT_TECH.getDataSource(), "技术安全报告上报");
		return map;
	}

	public static String getTaskNameMapping(String taskNameEN) {
		String taskNameCN = taskNameMapp.get(taskNameEN);
		return taskNameCN;
	}

	public static String getTaskNameMappingCn(String taskNameCN) {
		for (Entry<String, String> es : taskNameMapp.entrySet()) {
			if (es.getValue().equals(taskNameCN)) {
				return es.getKey();
			}
		}
		throw new RuntimeException("没有对应的流程");
	}
}
