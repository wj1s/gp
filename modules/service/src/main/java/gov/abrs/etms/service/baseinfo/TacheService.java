package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TacheService extends CrudService<Tache> {
	//删除环节时设备不会被级联删除
	@Override
	public void delete(Long id) {
		Tache tache = dao.get(id);
		for (Equip equip : tache.getEquips()) {
			equip.setTache(null);
		}
		dao.delete(id);
	}

	//按TransType取Tache
	public void get(Carrier<Tache> carrier, Long transTypeId) {
		String hql = "from Tache where transType.id =:transTypeId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("transTypeId", transTypeId);
		this.dao.find(carrier, hql, values);
	}
}
