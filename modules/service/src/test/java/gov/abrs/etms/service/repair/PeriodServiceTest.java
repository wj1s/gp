/**
 * 
 */
package gov.abrs.etms.service.repair;

import static org.junit.Assert.assertEquals;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Period;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

/**
 * @author 郑扬
 *
 */
@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class PeriodServiceTest extends DatabaseTestCase {
	@Autowired
	private PeriodService periodService;
	private int periodCount = 0;
	private int cardCount = 0;

	@Before
	public void setup() {
		periodCount = this.getRowsCount(Period.class);
		cardCount = this.getRowsCount(Card.class);
	}

	@Test
	public void testSavePeriod() {
		Period period1 = new Period();
		period1.setName("五日检");
		period1.setPreviousValue(3);
		period1.setType("1");
		period1.setValue(5);
		periodService.save(period1);
		List<Period> all = periodService.getAll();
		assertEquals(all.size(), periodCount + 1);

		Period period2 = new Period();
		period2.setName("防雨检");
		period2.setStartDay(new Date());
		period2.setEndDay(new Date());
		period2.setPreviousValue(2);
		period2.setValue(10);
		period2.setType("2");
		periodService.save(period2);
		List<Period> all2 = periodService.getAll();
		assertEquals(all2.size(), periodCount + 2);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeletePeriod() {
		periodService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Period.class, periodCount);
		assertRowsCount(Card.class, cardCount);
	}
}
