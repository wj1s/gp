package gov.abrs.etms.service.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.model.duty.DutySchedule;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class DutyScheduleServiceTest extends DatabaseTestCase {
	@Autowired
	private DutyScheduleService dutyScheduleService;

	//测试按id查找
	@Test
	public void testGet() {
		DutySchedule dutySchedule = this.dutyScheduleService.get(1L);
		assertNotNull(dutySchedule);
	}
}
