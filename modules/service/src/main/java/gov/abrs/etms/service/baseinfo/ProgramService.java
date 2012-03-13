package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ProgramService extends CrudService<Program> {
	@Override
	public void delete(Long id) {
		Program program = dao.get(id);
		if (program.getOperationPs().size() > 0) {
			throw new CanNotDeleteException("被删除节目包含业务信息，请清除所有关联业务后，删除");
		} else {
			dao.delete(id);
		}
	}
}
