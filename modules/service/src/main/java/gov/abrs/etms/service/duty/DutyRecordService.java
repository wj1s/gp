package gov.abrs.etms.service.duty;

import gov.abrs.etms.model.duty.DutyRecord;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DutyRecordService extends CrudService<DutyRecord> {

	public List<DutyRecord> getInfluenceRecords(Long broadByTimeId) {
		String hql = "from DutyRecord where broadByTime.id =:broadByTimeId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("broadByTimeId", broadByTimeId);
		return dao.find(hql, values);
	}

}
