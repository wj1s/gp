package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class EquipServiceTest extends DatabaseTestCase {

	@Autowired
	private EquipService equipService;
	@Autowired
	private ChannelService channelService;

	@Test
	public void testgetByNameLike() {
		List<Equip> byNames = equipService.getByNameLike("器");
		assertEquals(byNames.size(), 2);
		List<Equip> byNames1 = equipService.getByNameLike("调");
		assertEquals(byNames1.size(), 1);
	}

	@Test
	public void testgetByName() {
		Equip equip = equipService.getByName("上变频器");
		assertEquals(equip.getId(), new Long(1));
	}

	@Test
	public void testSave() {
		assertRowsCount(Equip.class, 2);
		Channel channel = channelService.get(1L);
		equipService.save(new Equip("a", new Dept(1L), false, "1", new EquipType(10L), new Date(), "wj", new Tache(7L),
				Lists.newArrayList(channel)));
		assertRowsCount(Equip.class, 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInAbnEquips() {
		assertRowsCount(Equip.class, 2);
		Channel channel = channelService.get(1L);
		assertEquals(channel.getEquips().size(), 2);
		equipService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Equip.class, 1);
		Channel channel1 = channelService.get(1L);
		assertEquals(channel1.getEquips().size(), 1);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInOperation() {
		assertRowsCount(Equip.class, 2);
		equipService.delete(3L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Equip.class, 2);
	}

	@Test
	public void testGet() {
		Equip device = equipService.get(2L);
		assertNull(device);
		Equip equip = equipService.get(3L);
		assertNotNull(equip);
		assertEquals(equip.getName(), "调制器");
		assertEquals(equip.getDept().getName(), "传输机房");
		assertEquals(equip.getChannels().size(), 2);
		assertEquals(equip.getTache().getName(), "调制");
		assertEquals(equip.getAbnormalFs().size(), 1);
		assertEquals(equip.getAbnormalBs().size(), 0);
		assertEquals(equip.getAbnEquips().size(), 2);
	}

	@Test
	public void testGetAll() {
		assertEquals(equipService.getAll().size(), 2);
	}
}
