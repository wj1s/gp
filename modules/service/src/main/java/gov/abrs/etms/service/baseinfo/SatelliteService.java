package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Satellite;
import gov.abrs.etms.model.baseinfo.Transfer;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SatelliteService extends CrudService<Satellite> {
	@Override
	public void delete(Long id) {
		Satellite satellite = dao.get(id);
		boolean candel = true;
		for (Transfer transfer : satellite.getTransfers()) {
			if (transfer.getOperationSs().size() > 0) {
				candel = false;
				break;
			}
		}
		if (candel) {
			dao.delete(id);
		} else {
			throw new CanNotDeleteException("由于卫星的转发器关联业务，不能删除卫星");
		}
	}
}
