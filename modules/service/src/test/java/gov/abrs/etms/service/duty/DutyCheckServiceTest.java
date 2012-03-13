package gov.abrs.etms.service.duty;

import gov.abrs.etms.model.duty.DutyCheck;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class DutyCheckServiceTest extends DatabaseTestCase {
	@Autowired
	private DutyCheckService dutyCheckService;

	//测试单独删除一条值班记录的审核信息
	@Test
	public void testDelete() {
		int count = getRowsCount(DutyCheck.class);
		dutyCheckService.delete(1L);
		assertRowsCount(DutyCheck.class, count - 1);
	}
}
