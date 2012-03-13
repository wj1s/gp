package gov.abrs.etms.service.log;

import gov.abrs.etms.common.util.ReflectionUtils;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.log.AuditLog;
import gov.abrs.etms.model.log.AuditLogHis;
import gov.abrs.etms.model.util.ClassName;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.service.util.UtilService;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class AuditLogListener implements Runnable {

	public AuditLogListener(AuditLogQueue auditLogQueue, AuditLogService auditLogService, UtilService utilService,
			ExecuteDAO executeDAO) {
		this.auditLogQueue = auditLogQueue;
		this.auditLogService = auditLogService;
		this.utilService = utilService;
		this.executeDAO = executeDAO;
	}

	@SuppressWarnings("unchecked")
	public void run() {
		while (true) {
			try {
				while (!auditLogQueue.isEmpty()) {
					Map<String, Object> map = auditLogQueue.poll();
					if (map.get("type").equals("A")) {
						IdEntity model = (IdEntity) map.get("newModel");
						AuditLog auditLog = new AuditLog();
						auditLog.setClassNameCn(getClassNameChn(model));
						auditLog.setModelName(model.getClass().getName());
						auditLog.setModelId(model.getId());
						auditLog.setUpdDate(utilService.getSysTime());
						auditLog.setUpdName(map.get("empName").toString());
						auditLogService.save(auditLog);
					} else if (map.get("type").equals("U")) {
						//更新的话大量的对比工作移到线程中做异步处理
						Map<String, Object> oldValues = (HashMap) map.get("oldValues");
						IdEntity model = (IdEntity) map.get("newModel");
						Boolean isChanged = false;
						AuditLog auditLog = new AuditLog();
						List<AuditLogHis> alhList = new ArrayList<AuditLogHis>();
						for (Entry<String, Object> entry : oldValues.entrySet()) {
							String propertyNameChn = entry.getKey().toString().split("\\|")[0];
							String propertyName = entry.getKey().toString().split("\\|")[1];
							if (propertyName.indexOf(".") != -1) {
								//如果属性是对象
								String propertyValue = null;
								Object propertyObj = ReflectionUtils.getFieldValue(model, propertyName.split("\\.")[0]);
								if (propertyName.split("\\.")[1].equals("id")) {
									//地球站系统只有这一种model的基类，如果是其他系统需要扩展
									propertyValue = ((IdEntity) propertyObj).getId().toString();
								} else {
									Object tempObj = executeDAO.get(propertyObj.getClass(), ((IdEntity) propertyObj)
											.getId());
									propertyValue = ReflectionUtils
											.getFieldValue(tempObj, propertyName.split("\\.")[1]).toString();
								}

								if (!propertyValue.equals(entry.getValue())) {
									isChanged = true;
									AuditLogHis alh = new AuditLogHis();
									alh.setAuditLog(auditLog);
									alh.setPropertyName(propertyNameChn);
									alh.setOldVal(entry.getValue().toString());
									alh.setNewVal(propertyValue.toString());
									alhList.add(alh);
								}
							} else {
								//如果对象是基础数据类型
								Object propertyValue = ReflectionUtils.getFieldValue(model, propertyName);
								if (!propertyValue.equals(entry.getValue())) {
									isChanged = true;
									AuditLogHis alh = new AuditLogHis();
									alh.setAuditLog(auditLog);
									alh.setPropertyName(propertyNameChn);
									alh.setOldVal(entry.getValue().toString());
									alh.setNewVal(propertyValue.toString());
									alhList.add(alh);
								}
							}
						}
						if (isChanged) {
							auditLog.setClassNameCn(getClassNameChn(model));
							auditLog.setModelName(model.getClass().getName());
							auditLog.setModelId(model.getId());
							auditLog.setUpdDate(utilService.getSysTime());
							auditLog.setUpdName(map.get("empName").toString());
							auditLog.setAuditLogHisList(alhList);
							auditLogService.save(auditLog);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {}
			}

		}
	}

	private String getClassNameChn(Object model) {
		Annotation classAnnotation = model.getClass().getAnnotation(ClassName.class);
		if (classAnnotation != null) {
			return ((ClassName) classAnnotation).name();
		} else {
			return null;
		}
	}

	private final AuditLogQueue auditLogQueue;
	private final AuditLogService auditLogService;
	private final UtilService utilService;
	private final ExecuteDAO executeDAO;
}
