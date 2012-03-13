package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Device;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class TacheServiceTest extends DatabaseTestCase {
	@Autowired
	TacheService tacheService;
	@Autowired
	EquipService equipService;

	@Test
	public void testSave() {
		tacheService.save(new Tache("hj1", new TransType(3L)));
		assertRowsCount(Tache.class, 8);
	}

	@Test
	public void testDelete() {
		Equip equip = equipService.get(1L);
		assertNotNull(equip);
		assertNotNull(equip.getTache());
		tacheService.delete(7L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(TechSystem.class, 3);
		assertRowsCount(Tache.class, 6);
		//环节中的设备并不删除，但是对应环路为空
		assertRowsCount(Device.class, 3);
		assertRowsCount(Equip.class, 2);
		Equip equip1 = equipService.get(1L);
		assertNotNull(equip1);
		assertNull(equip1.getTache());
	}

	@Test
	public void testGet() {
		Tache tache = tacheService.get(7L);
		assertEquals(tache.getName(), "上变频");
		assertEquals(tache.getTransType().getCodeDesc(), "卫星上行");
		assertEquals(tache.getEquips().size(), 1);
	}

	@Test
	public void testGetAll() {
		assertEquals(tacheService.getAll().size(), 7);
	}

}
