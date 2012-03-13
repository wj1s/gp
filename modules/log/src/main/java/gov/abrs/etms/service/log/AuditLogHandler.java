package gov.abrs.etms.service.log;

import gov.abrs.etms.common.util.ReflectionUtils;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.util.ClassName;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.PropertyName;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Aspect
@Component
@Transactional(propagation = Propagation.REQUIRED)
public class AuditLogHandler {

	@SuppressWarnings("unchecked")
	@Around("execution(* gov.abrs.etms.dao.util.ExecuteDAO.save(..))")
	public void around(ProceedingJoinPoint point) throws Throwable {
		try {
			IdEntity model = (IdEntity) point.getArgs()[1];
			Class clazz = model.getClass();
			Annotation annotation = clazz.getAnnotation(ClassName.class);
			if (annotation != null) {
				if (auditLogConfService.isEnable(clazz.getName())) {
					String empName = securityService.getCurUser().getName();
					if (model.getId() == null) {
						//新增
						point.proceed();

						Map<String, Object> map = new HashMap<String, Object>();
						map.put("type", "A");
						map.put("newModel", model);
						map.put("empName", empName);
						addQueue(map);
					} else {
						//更新
						Long id = model.getId();
						IdEntity oldModel = executeDAO.getWithStatelessSession(clazz, id);
						Map<String, Object> valueMap = getFieldValues(clazz, oldModel);
						point.proceed();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("type", "U");
						map.put("oldValues", valueMap);
						map.put("newModel", model);
						map.put("empName", empName);
						addQueue(map);
					}
				} else {
					point.proceed();
				}
			} else {
				point.proceed();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 
	 *删除的日志记录暂未添加，需要时自行扩展
	 * 
	 */

	@SuppressWarnings("unchecked")
	private Map<String, Object> getFieldValues(final Class clazz, final Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Field field : clazz.getDeclaredFields()) {
			PropertyName fieldAnnotation = field.getAnnotation(PropertyName.class);
			if (fieldAnnotation != null) {
				String fieldName = field.getName();
				Object returnProperty = ReflectionUtils.getFieldValue(obj, fieldName);
				if (!fieldAnnotation.subProperty().equals("")) {
					String subPeopertyName = fieldAnnotation.subProperty();
					if (subPeopertyName.equals("id")) {
						map.put(fieldAnnotation.name() + "|" + fieldName + "." + subPeopertyName, ReflectionUtils
								.getFieldValue(returnProperty, subPeopertyName));
					} else {
						//除id之外的属性很有可能延迟加载
						//地球站系统只有这一种model的基类，如果是其他系统需要扩展
						if (returnProperty instanceof IdEntity) {
							Object subObj = executeDAO.get(returnProperty.getClass(), ((IdEntity) returnProperty)
									.getId());
							map.put(fieldAnnotation.name() + "|" + fieldName + "." + subPeopertyName, ReflectionUtils
									.getFieldValue(subObj, subPeopertyName));
						}

					}
				} else {
					map.put(fieldAnnotation.name() + "|" + fieldName, returnProperty);
				}
			}
		}
		return map;
	}

	@PostConstruct
	public void init() {
		AuditLogListener thread = new AuditLogListener(auditLogQueue, auditLogService, utilService, executeDAO);
		Thread t1 = new Thread(thread);
		t1.start();
	}

	private void addQueue(Map<String, Object> map) {
		auditLogQueue.add(map);
	}

	@Autowired
	private UtilService utilService;
	@Autowired
	private AuditLogConfService auditLogConfService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private AuditLogQueue auditLogQueue;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private ExecuteDAO executeDAO;

}
