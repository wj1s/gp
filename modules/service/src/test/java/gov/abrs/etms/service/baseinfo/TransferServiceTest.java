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
public class TransferServiceTest extends DatabaseTestCase {
	@Autowired
	TransferService transferService;

	@Test
	public void testSave() {
		transferService.save(new Transfer("zfq1", new Date(), "wj", new Satellite(1L)));
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Transfer.class, 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInOperation() {
		assertRowsCount(Satellite.class, 2);
		assertRowsCount(Transfer.class, 2);
		assertRowsCount(Operation.class, 5);
		transferService.delete(1L);
		assertRowsCount(Satellite.class, 2);
		assertRowsCount(Transfer.class, 2);
		assertRowsCount(Operation.class, 5);
	}

	@Test
	public void testGet() {
		Transfer ta = transferService.get(1L);
		assertEquals(ta.getName(), "转发器1");
		assertEquals(ta.getSatellite().getName(), "卫星1");
		assertEquals(ta.getOperationSs().size(), 2);
	}

	@Test
	public void testGetAll() {
		assertEquals(transferService.getAll().size(), 2);
	}

}
