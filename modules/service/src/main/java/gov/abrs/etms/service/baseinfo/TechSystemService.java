package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TechSystemService extends CrudService<TechSystem> {
	//删除系统时设备不会被级联删除
	@Override
	public void delete(Long id) {
		TechSystem ts = dao.get(id);
		for (Channel channel : ts.getChannels()) {
			for (Equip equip : channel.getEquips()) {
				equip.getChannels().clear();
				equip.setTache(null);
			}
		}
		dao.delete(id);
	}

	//按TransType取TechSystem
	public void get(Carrier<TechSystem> carrier, Long transTypeId) {
		String hql = "from TechSystem where transType.id =:transTypeId";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("transTypeId", transTypeId);
		this.dao.find(carrier, hql, values);
	}
}
