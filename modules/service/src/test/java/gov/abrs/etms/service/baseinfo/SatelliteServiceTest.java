package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Satellite;
import gov.abrs.etms.model.baseinfo.Transfer;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class SatelliteServiceTest extends DatabaseTestCase {
	@Autowired
	SatelliteService satelliteService;

	@Test
	public void testSave() {
		satelliteService.save(new Satellite("a", "b", new Date(), "wj"));
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Satellite.class, 3);
	}

	@Test
	public void testDeleteNotInOperation() {
		assertRowsCount(Satellite.class, 2);
		assertRowsCount(Transfer.class, 2);
		satelliteService.delete(2L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Satellite.class, 1);
		assertRowsCount(Transfer.class, 2);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInOperation() {
		assertRowsCount(Satellite.class, 2);
		assertRowsCount(Transfer.class, 2);
		assertRowsCount(Operation.class, 5);
		satelliteService.delete(1L);
		assertRowsCount(Satellite.class, 2);
		assertRowsCount(Transfer.class, 2);
		assertRowsCount(Operation.class, 5);
	}

	@Test
	public void testGet() {
		Satellite sa = satelliteService.get(1L);
		assertEquals(sa.getName(), "卫星1");
		assertEquals(sa.getTransfers().size(), 2);
	}

	@Test
	public void testGetAll() {
		assertEquals(satelliteService.getAll().size(), 2);
	}

}
