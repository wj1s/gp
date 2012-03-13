package gov.abrs.etms.service.duty;

import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DutyRuleService extends CrudService<DutyRule> {

	//某部门是否有某个运转的排班规则
	public Boolean hasDutyRule(Long dpId, int dayPartCount) {
		String hql = "from DutyRule rule where rule.dept.id=? and rule.dayPartCount = ?";
		DutyRule dutyRule = this.dao.findUnique(hql, dpId, dayPartCount);
		if (dutyRule == null) {
			return false;
		} else {
			return true;
		}
	}
}
