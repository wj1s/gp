package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Station;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class StationServiceTest extends DatabaseTestCase {
	@Autowired
	StationService stationService;

	@Test
	public void testGetStation() {
		Station st = stationService.getStation();
		assertNotNull(st);
		assertEquals(st.getName(), "北京地球站");
		assertEquals(st.getDepts().size(), 4);
	}
}
