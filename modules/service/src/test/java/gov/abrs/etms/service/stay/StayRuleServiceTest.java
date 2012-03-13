package gov.abrs.etms.service.stay;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.stay.StayPeriod;
import gov.abrs.etms.model.stay.StayRule;
import gov.abrs.etms.model.stay.StaySection;
import gov.abrs.etms.model.stay.StaySectionPerson;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class StayRuleServiceTest extends DatabaseTestCase {

	@Autowired
	private StayRuleService stayRuleService;

	@Test
	public void getRulesTest() {
		List<StayRule> stayRules = stayRuleService.getRules(new Dept(1L));
		assertEquals(2, stayRules.size());
	}

	@Test
	public void getRulesTest1() {
		Dept dept = new Dept(1L);
		int periodCount = 4;
		List<StayRule> list = stayRuleService.getRules(dept, periodCount);
		assertNotNull(list);
		assertTrue(2L == list.get(0).getId());
	}

	@Test
	public void saveTest1() {
		int count1 = getRowsCount(StayRule.class);
		int count2 = getRowsCount(StayPeriod.class);
		int count3 = getRowsCount(StaySection.class);
		int count4 = getRowsCount(StaySectionPerson.class);

		StayRule stayRule = new StayRule();
		stayRule.setDept(new Dept(1L));
		stayRule.setPeriodCount(1);
		StayPeriod stayPeriod = new StayPeriod();
		StaySection staySection = new StaySection();
		staySection.setStartTime(DateUtil.createDateTime("2011-11-11 00:00:00"));
		staySection.setEndTime(DateUtil.createDateTime("2011-11-11 00:00:00"));
		staySection.setStaySectionPeople(Lists.newArrayList(new StaySectionPerson(staySection, "123")));
		staySection.setStayPeriod(stayPeriod);
		List<StaySection> staySections = new ArrayList<StaySection>();
		staySections.add(staySection);
		stayPeriod.setStaySections(staySections);
		stayPeriod.setStayRule(stayRule);
		stayPeriod.setPeriodIndex(1);
		List<StayPeriod> stayPeriods = new ArrayList<StayPeriod>();
		stayPeriods.add(stayPeriod);
		stayRule.setStayPeriods(stayPeriods);
		stayRuleService.save(stayRule);

		assertEquals(count1 + 1, getRowsCount(StayRule.class));
		assertEquals(count2 + 1, getRowsCount(StayPeriod.class));
		assertEquals(count3 + 1, getRowsCount(StaySection.class));
		assertEquals(count4 + 1, getRowsCount(StaySectionPerson.class));
	}

	@Test
	public void saveTest2() {
		int count1 = getRowsCount(StayRule.class);
		int count2 = getRowsCount(StayPeriod.class);
		int count3 = getRowsCount(StaySection.class);
		int count4 = getRowsCount(StaySectionPerson.class);

		StayRule stayRule = stayRuleService.get(1L);
		stayRule.getStayPeriods().clear();

		stayRule.setDept(new Dept(1L));
		stayRule.setPeriodCount(1);
		StayPeriod stayPeriod = new StayPeriod();
		StaySection staySection = new StaySection();
		staySection.setStartTime(DateUtil.createDateTime("2011-11-11 00:00:00"));
		staySection.setEndTime(DateUtil.createDateTime("2011-11-11 00:00:00"));
		staySection.setStaySectionPeople(Lists.newArrayList(new StaySectionPerson(staySection, "123")));
		staySection.setStayPeriod(stayPeriod);
		List<StaySection> staySections = new ArrayList<StaySection>();
		staySections.add(staySection);
		stayPeriod.setStaySections(staySections);
		stayPeriod.setStayRule(stayRule);
		stayPeriod.setPeriodIndex(1);
		List<StayPeriod> stayPeriods = new ArrayList<StayPeriod>();
		stayPeriods.add(stayPeriod);

		stayRule.getStayPeriods().addAll(stayPeriods);
		stayRuleService.save(stayRule);

		assertEquals(count1, getRowsCount(StayRule.class));
		assertEquals(count2 - 2, getRowsCount(StayPeriod.class));
		assertEquals(count3 - 6, getRowsCount(StaySection.class));
		assertEquals(count4 - 6, getRowsCount(StaySectionPerson.class));
	}

}
