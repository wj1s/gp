package gov.abrs.etms.service.log;

import gov.abrs.etms.model.log.AuditLogConf;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditLogConfService extends CrudService<AuditLogConf> {

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Boolean isEnable(String className) {
		String hql = "from AuditLogConf where className=:className and enable=1";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("className", className);
		List<AuditLogConf> list = this.dao.find(hql, values);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
