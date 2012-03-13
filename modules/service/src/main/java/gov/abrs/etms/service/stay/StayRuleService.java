package gov.abrs.etms.service.stay;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.stay.StayRule;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StayRuleService extends CrudService<StayRule> {

	public List<StayRule> getRules(Dept dept) {
		String hql = " from StayRule where dept.id =:deptId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("deptId", dept.getId());
		return dao.find(hql, values);
	}

	public List<StayRule> getRules(Dept dept, int periodCount) {
		String hql = " from StayRule as rule where rule.dept.id = :deptId and rule.periodCount = :periodCount ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("deptId", dept.getId());
		values.put("periodCount", periodCount);
		return dao.find(hql, values);
	}
}
