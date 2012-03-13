package gov.abrs.etms.service.report;

import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.rept.OpTime;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OpTimeService extends CrudService<OpTime> {

	public List<OpTime> get(String reptTime) {
		return this.dao.findBy("reptTime", reptTime);
	}

	public void delete(String reptTime) {
		String hql = "delete from OpTime where reptTime =:reptTime";
		Map<String, String> values = new HashMap<String, String>();
		values.put("reptTime", reptTime);
		dao.batchExecute(hql, values);
	}

	//根据业务和年月取唯一的传输时间
	public OpTime get(Operation operation, String reptTime) {
		String hql = "from OpTime where operation =:operation and reptTime=:reptTime";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("operation", operation);
		values.put("reptTime", reptTime);
		List<OpTime> list = dao.find(hql, values);
		if (list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
