package gov.abrs.etms.service.report;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.BroadByReason;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.rept.BroadByTime;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class BroadByTimeServiceTest extends DatabaseTestCase {

	@Autowired
	private BroadByTimeService broadByTimeService;

	@Test
	public void testGetByCurDateAndTransTypes() {
		Date date = DateUtil.createDateTime("2010-11-25 00:00:00");
		List<TransType> transTypes = Lists.newArrayList(new TransType(3L));
		List<BroadByTime> list = broadByTimeService.get(date, transTypes);
		assertNotNull(list);
		assertEquals(1, list.size());
	}

	@Test
	public void testGet() {
		Date startDate = DateUtil.createDateTime("2010-11-01 00:00:00");
		Date endDate = DateUtil.createDateTime("2010-11-30 00:00:00");
		ParaDtl broadByStation = new BroadByStation(20L);
		ParaDtl broadByReason = new BroadByReason(19L);
		Carrier<BroadByTime> carrier = new Carrier<BroadByTime>();
		String operationName = "卫星业务3";
		String broadByFlag = "D";
		broadByTimeService.get(carrier, operationName, startDate, endDate, broadByReason, broadByStation, broadByFlag);
		assertNotNull(carrier.getResult());
		assertEquals(1, carrier.getResult().size());
		assertEquals("卫星业务3", carrier.getResult().get(0).getOperation().getName());
	}
}
