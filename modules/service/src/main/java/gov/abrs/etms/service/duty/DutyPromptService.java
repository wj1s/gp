package gov.abrs.etms.service.duty;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.duty.DutyPrompt;
import gov.abrs.etms.service.util.CrudService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DutyPromptService extends CrudService<DutyPrompt> {

	//查找某部门当前时间的值班提醒
	public List<DutyPrompt> getDutyPromptNow(Dept dept, Date date) {
		String hql = "from DutyPrompt where duty.group.dept =:dept and startTime <=:thisDate and endTime >=:thisDate order by ddate desc,startTime desc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("thisDate", date);
		List<DutyPrompt> list = this.dao.find(hql, values);
		return list;
	}

	//某部门当前时间是否有值班提醒
	public Boolean hasDutyPromptNow(Dept dept, Date date) {
		String hql = "from DutyPrompt where duty.group.dept =:dept and startTime =:thisDate order by ddate desc,startTime desc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("thisDate", DateUtil.dateToDateByFormat(date, "yyyy-MM-dd HH:mm:00"));
		List<DutyPrompt> list = this.dao.find(hql, values);
		if (list != null && list.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
}
