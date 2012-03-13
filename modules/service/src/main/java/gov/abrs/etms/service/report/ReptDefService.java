package gov.abrs.etms.service.report;

import gov.abrs.etms.model.rept.ReptDef;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ReptDefService extends CrudService<ReptDef> {

	public ReptDef get(String reptId) {
		return dao.findUniqueBy("reptId", reptId);
	}

	public List<ReptDef> getByDataSource(String dataSource) {
		return dao.findBy("dataSource", dataSource);
	}

}
