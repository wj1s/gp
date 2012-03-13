package gov.abrs.etms.service.report;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.TransmitDef;
import gov.abrs.etms.model.rept.OpTime;
import gov.abrs.etms.service.baseinfo.OperationService;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class OpTimeServiceTest extends DatabaseTestCase {

	@Autowired
	private OpTimeService opTimeService;
	@Autowired
	private OperationService operationService;

	@Test
	public void testDelete() {
		int count1 = getRowsCount(OpTime.class);
		int count2 = getRowsCount(Operation.class);
		int count3 = getRowsCount(TransType.class);
		int count4 = getRowsCount(TransmitDef.class);

		String reptTime = "201010";
		opTimeService.delete(reptTime);
		assertEquals(3, count1 - getRowsCount(OpTime.class));
		assertEquals(0, count2 - getRowsCount(Operation.class));
		assertEquals(0, count3 - getRowsCount(TransType.class));
		assertEquals(0, count4 - getRowsCount(TransmitDef.class));
	}

	@Test
	public void testSave() {
		int count = getRowsCount(OpTime.class);
		Operation op = operationService.get(1L);
		OpTime reptOpTime = new OpTime(op, "201111", op.getTransType(), op.getTransmitDef(), 12D, new Date(), "123");
		opTimeService.save(reptOpTime);

		assertEquals(count + 1, getRowsCount(OpTime.class));
	}
}
