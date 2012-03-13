package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.OperationP;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OperationPService extends CrudService<OperationP> {
	public List find(String hsql) {
		return dao.find(hsql);
	}
}
