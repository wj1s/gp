package gov.abrs.etms.service.repair;

import gov.abrs.etms.model.repair.Period;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PeriodService extends CrudService<Period> {

	@Override
	public void delete(Long id) {
		String queryString = "from Card c where c.period.id= '" + id + "' ";
		List<Period> period = this.dao.find(queryString);
		if (period.size() != 0) {
			throw new CanNotDeleteException("该检修周期已被使用，不能删除");
		} else {
			dao.delete(id);
		}

	}

}
