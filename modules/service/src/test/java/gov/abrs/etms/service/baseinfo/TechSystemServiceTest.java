package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Device;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class TechSystemServiceTest extends DatabaseTestCase {
	@Autowired
	TechSystemService techSystemService;
	@Autowired
	EquipService equipService;

	@Test
	public void testSave() {
		techSystemService.save(new TechSystem("a", "b", new TransType(3L)));
		assertRowsCount(TechSystem.class, 4);
	}

	@Test
	public void testDelete() {
		techSystemService.delete(3L);
		assertRowsCount(TechSystem.class, 2);
		assertRowsCount(Channel.class, 0);
		//环节中的设备并不删除，但是对应环路为空
		assertRowsCount(Device.class, 3);
		assertRowsCount(Equip.class, 2);
		Equip equip = equipService.get(1L);
		assertNotNull(equip);
		assertNull(equip.getTache());
		assertEquals(equip.getChannels().size(), 0);
	}

	@Test
	public void testGet() {
		TechSystem ts = techSystemService.get(3L);
		assertEquals(ts.getName(), "C1系统");
		assertEquals(ts.getChannels().size(), 2);
	}

	@Test
	public void testGetAll() {
		assertEquals(techSystemService.getAll().size(), 3);
	}

}
