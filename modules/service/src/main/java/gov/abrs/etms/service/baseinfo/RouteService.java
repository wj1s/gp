package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Route;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RouteService extends CrudService<Route> {
	@Override
	public void delete(Long id) {
		Route route = dao.get(id);
		if (route.getOperationTs().size() > 0) {
			throw new CanNotDeleteException("被删除路由包含业务信息，请清除所有关联业务后，删除");
		} else {
			dao.delete(id);
		}
	}
}
