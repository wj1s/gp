package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Station;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StationService extends CrudService<Station> {
	//获取台站信息
	public Station getStation() {
		return this.dao.getAll().get(0);
	}
}
