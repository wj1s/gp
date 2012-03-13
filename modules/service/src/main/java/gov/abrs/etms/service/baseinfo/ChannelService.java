package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ChannelService extends CrudService<Channel> {
	//删除通路时设备不会被级联删除
	@Override
	public void delete(Long id) {
		Channel channel = dao.get(id);
		for (Equip equip : channel.getEquips()) {
			equip.getChannels().remove(channel);
			System.out.println(equip);
		}
		channel.getEquips().clear();
		dao.delete(id);
	}
}
