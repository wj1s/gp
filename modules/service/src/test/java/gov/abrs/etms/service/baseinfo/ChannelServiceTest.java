package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Device;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Schedule;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.model.baseinfo.TechSystem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class ChannelServiceTest extends DatabaseTestCase {
	@Autowired
	ChannelService channelService;
	@Autowired
	EquipService equipService;

	@Test
	public void testSave() {
		channelService.save(new Channel("tl1", new TechSystem(3L)));
		assertRowsCount(Channel.class, 3);
	}

	@Test
	public void testDelete() {
		channelService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(TechSystem.class, 3);
		assertRowsCount(Tache.class, 7);
		assertRowsCount(Channel.class, 1);
		assertRowsCount(Schedule.class, 1);
		//通路中的设备并不删除，但是对应通路为空
		assertRowsCount(Device.class, 3);
		assertRowsCount(Equip.class, 2);
		Equip equip = equipService.get(1L);
		assertNotNull(equip);
		assertEquals(equip.getChannels().size(), 0);
		assertNotNull(equip.getTache());
	}

	@Test
	public void testGet() {
		Channel channel = channelService.get(1L);
		assertEquals(channel.getName(), "上星通路1");
		assertEquals(channel.getTechSystem().getName(), "C1系统");
		assertEquals(channel.getEquips().size(), 2);
		assertEquals(channel.getSchedules().size(), 2);
	}

	@Test
	public void testGetAll() {
		assertEquals(channelService.getAll().size(), 2);
	}

}
