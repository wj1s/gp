package gov.abrs.etms.service.stay;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.stay.StayDetail;
import gov.abrs.etms.model.stay.StayDetailPerson;
import gov.abrs.etms.model.stay.StayRule;
import gov.abrs.etms.model.stay.StaySection;
import gov.abrs.etms.model.stay.StaySectionPerson;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class StayDetailServiceTest extends DatabaseTestCase {

	@Autowired
	private StayDetailService stayDetailService;
	@Autowired
	private StayRuleService stayRuleService;

	@Test
	public void getTest() {
		Date start = DateUtil.createDate("2011-11-29");
		Date end = DateUtil.createDate("2011-12-01");
		List<StayDetail> list = stayDetailService.get(start, end, new Dept(1L));
		assertNotNull(list);
		assertEquals(5, list.size());
	}

	@Test
	public void saveTest() {
		int count1 = getRowsCount(StayDetail.class);
		int count2 = getRowsCount(StayDetailPerson.class);

		StayDetail stayDetail = new StayDetail();
		stayDetail.setDept(new Dept(1L));
		stayDetail.setStartTime(DateUtil.createDateTime("2011-12-22 00:00:00"));
		stayDetail.setEndTime(DateUtil.createDateTime("2011-12-22 08:00:00"));
		stayDetail.setStayDetailPeople(Lists.newArrayList(new StayDetailPerson(stayDetail, "边迪")));
		stayDetailService.save(stayDetail);
		assertEquals(count1 + 1, getRowsCount(StayDetail.class));
		assertEquals(count2 + 1, getRowsCount(StayDetailPerson.class));

	}

	@Test
	public void isExistTest1() {
		Date start = DateUtil.createDate("2011-11-29");
		Date end = DateUtil.createDate("2011-12-01");
		Date curDate = DateUtil.createDateTime("2011-11-29 13:00:00");
		String result = stayDetailService.isExist(start, end, curDate, new Dept(1L));
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "今天的排班已经上班,请从明天开始排班");
		assertEquals(json.toString(), result);
	}

	@Test
	public void isExistTest2() {
		Date start = DateUtil.createDate("2011-11-26");
		Date end = DateUtil.createDate("2011-11-27");
		Date curDate = DateUtil.createDate("2011-11-26");
		String result = stayDetailService.isExist(start, end, curDate, new Dept(1L));
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("isExist", false);
		assertEquals(json.toString(), result);
	}

	@Test
	public void isExistTest3() {
		Date start = DateUtil.createDate("2011-11-27");
		Date end = DateUtil.createDate("2011-12-07");
		Date curDate = DateUtil.createDate("2011-11-27");
		String result = stayDetailService.isExist(start, end, curDate, new Dept(1L));
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("isExist", true);
		assertEquals(json.toString(), result);
	}

	@Test
	public void isExistTest4() {
		Date start = DateUtil.createDate("2011-11-27");
		Date end = DateUtil.createDate("2011-12-07");
		Date curDate = DateUtil.createDate("2011-11-28");
		String result = stayDetailService.isExist(start, end, curDate, new Dept(1L));
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "开始排班日期不能小于今天");
		assertEquals(json.toString(), result);
	}

	@Test
	public void isExistTest5() {
		Date start = DateUtil.createDate("2011-12-01");
		Date end = DateUtil.createDate("2011-12-07");
		Date curDate = DateUtil.createDate("2011-11-28");
		String result = stayDetailService.isExist(start, end, curDate, new Dept(1L));
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("isExist", true);
		assertEquals(json.toString(), result);
	}

	@Test
	public void isExistTest6() {
		Date start = DateUtil.createDate("2011-12-07");
		Date end = DateUtil.createDate("2011-12-08");
		Date curDate = DateUtil.createDate("2011-12-05");
		String result = stayDetailService.isExist(start, end, curDate, new Dept(1L));
		JSONObject json = new JSONObject();
		json.put("success", true);
		json.put("isExist", false);
		assertEquals(json.toString(), result);
	}

	@Test
	public void createRuleAndDetailTest1() {
		int count1 = getRowsCount(StayDetail.class);
		int count2 = getRowsCount(StaySectionPerson.class);
		int count3 = getRowsCount(StayDetailPerson.class);

		StayRule stayRule = new StayRule();
		String[] startTimeArray = { "08:00", "12:00", "08:00", "12:00", "18:00", "08:00", "12:00" };
		String[] endTimeArray = { "12:00", "22:00", "12:00", "18:00", "23:00", "12:00", "23:00" };
		String[] empNamesArray = { "边迪,", "边迪,", "边迪,", "边迪,", "边迪,", "边迪,", "边迪," };
		int[] periodIndexArray = { 2, 3, 2 };
		int periodCount = 3;
		Dept dept = new Dept(1L);
		Date startTime = DateUtil.createDate("2011-11-29");
		Date endTime = DateUtil.createDate("2011-12-01");
		int startIndex = 0;
		stayDetailService.createRuleAndDetail(stayRule, startTimeArray, endTimeArray, empNamesArray, periodIndexArray,
				periodCount, dept, startTime, endTime, startIndex);
		StayDetail sd1 = stayDetailService.get(1L);
		assertNotNull(sd1);
		assertEquals(DateUtil.createDateTime("2011-11-29 00:00:00"), sd1.getEndTime());

		StayDetail sd2 = stayDetailService.get(8L);
		assertNotNull(sd2);
		assertEquals(DateUtil.createDateTime("2011-12-02 00:00:00"), sd2.getStartTime());

		StayDetail sd3 = stayDetailService.get(5L);
		assertNull(sd3);

		assertEquals(count1 + 1, getRowsCount(StayDetail.class));
		assertEquals(count2 + 7, getRowsCount(StaySectionPerson.class));
		assertEquals(count3 + 1, getRowsCount(StayDetailPerson.class));

	}

	@Test
	public void createRuleAndDetailTest2() {
		int count1 = getRowsCount(StayDetail.class);
		int count2 = getRowsCount(StaySectionPerson.class);
		int count3 = getRowsCount(StayDetailPerson.class);

		StayRule stayRule = stayRuleService.get(1L);
		String[] startTimeArray = { "08:00", "12:00", "08:00", "12:00", "18:00", "08:00", "12:00" };
		String[] endTimeArray = { "12:00", "22:00", "12:00", "18:00", "23:00", "12:00", "23:00" };
		String[] empNamesArray = { "边迪,", "边迪,", "边迪,", "边迪,", "边迪,", "边迪,", "边迪," };
		int[] periodIndexArray = { 2, 3, 2 };
		int periodCount = 3;
		Dept dept = new Dept(1L);
		Date startTime = DateUtil.createDate("2011-11-29");
		Date endTime = DateUtil.createDate("2011-12-01");
		int startIndex = 0;
		stayDetailService.createRuleAndDetail(stayRule, startTimeArray, endTimeArray, empNamesArray, periodIndexArray,
				periodCount, dept, startTime, endTime, startIndex);
		StayDetail sd1 = stayDetailService.get(1L);
		assertNotNull(sd1);
		assertEquals(DateUtil.createDateTime("2011-11-29 00:00:00"), sd1.getEndTime());

		StayDetail sd2 = stayDetailService.get(8L);
		assertNotNull(sd2);
		assertEquals(DateUtil.createDateTime("2011-12-02 00:00:00"), sd2.getStartTime());

		StayDetail sd3 = stayDetailService.get(5L);
		assertNull(sd3);

		assertEquals(count1 + 1, getRowsCount(StayDetail.class));
		assertEquals(count2, getRowsCount(StaySectionPerson.class));
		assertEquals(count3 + 1, getRowsCount(StayDetailPerson.class));
	}

	@Test
	public void createRuleAndDetailTest3() {
		int count1 = getRowsCount(StayDetail.class);
		int count2 = getRowsCount(StaySectionPerson.class);
		int count3 = getRowsCount(StayDetailPerson.class);

		StayRule stayRule = stayRuleService.get(1L);
		String[] startTimeArray = { "08:00", "12:00", "08:00", "12:00", "18:00", "08:00", "12:00" };
		String[] endTimeArray = { "12:00", "22:00", "12:00", "18:00", "23:00", "12:00", "23:00" };
		String[] empNamesArray = { "边迪,", "边迪,", "边迪,", "边迪,", "边迪,", "边迪,", "边迪," };
		int[] periodIndexArray = { 2, 3, 2 };
		int periodCount = 3;
		Dept dept = new Dept(1L);
		Date startTime = DateUtil.createDate("2011-11-29");
		Date endTime = DateUtil.createDate("2011-12-01");
		int startIndex = 0;
		stayDetailService.createRuleAndDetail(stayRule, startTimeArray, endTimeArray, empNamesArray, periodIndexArray,
				periodCount, dept, startTime, endTime, startIndex);
		StayDetail sd1 = stayDetailService.get(1L);
		assertNotNull(sd1);
		assertEquals(DateUtil.createDateTime("2011-11-29 00:00:00"), sd1.getEndTime());

		StayDetail sd2 = stayDetailService.get(8L);
		assertNotNull(sd2);
		assertEquals(DateUtil.createDateTime("2011-12-02 00:00:00"), sd2.getStartTime());

		StayDetail sd3 = stayDetailService.get(5L);
		assertNull(sd3);

		assertEquals(count1 + 1, getRowsCount(StayDetail.class));
		assertEquals(count2, getRowsCount(StaySectionPerson.class));
		assertEquals(count3 + 1, getRowsCount(StayDetailPerson.class));

	}

	@Test
	public void getCarrierTest1() {
		Date startTime = DateUtil.createDate("2011-11-28");
		Date endTime = DateUtil.createDate("2011-11-30");
		Carrier c = new Carrier();
		stayDetailService.get(c, startTime, endTime, new Dept(1L), "边");
		assertEquals(5, c.getTotalSize());
	}

	@Test
	public void getCarrierTest2() {
		Date startTime = DateUtil.createDate("2011-11-28");
		Date endTime = DateUtil.createDate("2011-11-30");
		Carrier c = new Carrier();
		stayDetailService.get(c, startTime, endTime, new Dept(1L), "王");
		assertEquals(0, c.getTotalSize());
	}

	@Test
	public void deleteTest1() {
		int count1 = getRowsCount(StaySection.class);
		int count2 = getRowsCount(StaySectionPerson.class);
		StayRule stayRule = stayRuleService.get(1L);
		stayRule.getStayPeriods().get(0).getStaySections().clear();
		stayRuleService.save(stayRule);
		assertEquals(count1 - 2, getRowsCount(StaySection.class));
		assertEquals(count2 - 2, getRowsCount(StaySectionPerson.class));
	}

	@Test
	public void getDetailsTest() {
		Date date = DateUtil.createDateTime("2011-11-29 08:00:00");
		List<StayDetail> list = stayDetailService.getDetails(date);
		assertNotNull(list);
		assertEquals(2, list.size());
		assertEquals(1, list.get(0).getId().intValue());
		assertEquals(2, list.get(1).getId().intValue());
	}

	@Test
	public void deleteOrphenTest1() {
		StayDetail stayDetail = stayDetailService.get(1L);
		stayDetail.getStayDetailPeople().clear();
		assertEquals(0, stayDetail.getStayDetailPeople().size());
	}

	@Test
	public void deleteOrphenTest2() {
		StayDetail stayDetail = stayDetailService.get(1L);
		stayDetailService.clearPeople(stayDetail);
		assertEquals(0, stayDetail.getStayDetailPeople().size());
	}
}
