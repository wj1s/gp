package gov.abrs.etms.service.report;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ImptPeriod;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class ImptPeriodServiceTest extends DatabaseTestCase {
	@Autowired
	private ImptPeriodService imptPeriodService;

	@Test
	public void testGet() {
		String reptTime = "201101";
		List<ImptPeriod> list = imptPeriodService.get(reptTime);
		assertNotNull(list);
		assertEquals(2, list.size());
		assertEquals(new Long(1), list.get(0).getId());
		assertEquals(new Long(2), list.get(1).getId());
	}

	@Test
	public void testGet1() {
		Date start = DateUtil.createDate("2011-1-12");
		Date end = DateUtil.createDate("2011-2-12");
		List<ImptPeriod> list = imptPeriodService.get(start, end);
		assertNotNull(list);
		assertEquals(2, list.size());
	}

}
