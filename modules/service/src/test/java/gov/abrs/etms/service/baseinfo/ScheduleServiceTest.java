package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Schedule;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class ScheduleServiceTest extends DatabaseTestCase {
	@Autowired
	ScheduleService scheduleService;
	@Autowired
	EquipService equipService;

	@Test
	public void testGetOperation() {
		Equip equip1 = equipService.get(1L);
		Equip equip2 = equipService.get(3L);
		//		没有交集
		List<AbnOperation> result = scheduleService.getAbnOperation(equip1,
				DateUtil.createDateTime("2010-11-17 00:00:00"), DateUtil.createDateTime("2010-11-18 00:00:00"), "1");
		assertEquals(result.size(), 0);
		//一个交集
		List<AbnOperation> result1 = scheduleService.getAbnOperation(equip1,
				DateUtil.createDateTime("2010-11-17 00:00:00"), DateUtil.createDateTime("2010-11-18 00:00:01"), "3");
		assertEquals(result1.size(), 1);
		assertEquals(result1.get(0).getOperation().getName(), "卫星业务1");
		assertEquals(DateUtil.dateToString(result1.get(0).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-18 00:00:00");
		assertEquals(DateUtil.dateToString(result1.get(0).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-18 00:00:01");
		//一个交集
		List<AbnOperation> result2 = scheduleService.getAbnOperation(equip1,
				DateUtil.createDateTime("2010-11-17 00:00:00"), DateUtil.createDateTime("2010-11-20 00:00:00"), "3");
		assertEquals(result2.size(), 1);
		assertEquals(result2.get(0).getOperation().getName(), "卫星业务1");
		assertEquals(DateUtil.dateToString(result2.get(0).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-18 00:00:00");
		assertEquals(DateUtil.dateToString(result2.get(0).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-20 00:00:00");
		//2个交集
		List<AbnOperation> result3 = scheduleService.getAbnOperation(equip1,
				DateUtil.createDateTime("2010-11-17 00:00:00"), DateUtil.createDateTime("2010-11-20 00:00:01"), "3");
		assertEquals(result3.size(), 2);
		assertEquals(result3.get(0).getOperation().getName(), "卫星业务1");
		assertEquals(DateUtil.dateToString(result3.get(0).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-18 00:00:00");
		assertEquals(DateUtil.dateToString(result3.get(0).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-20 00:00:00");
		assertEquals(result3.get(1).getOperation().getName(), "卫星业务2");
		assertEquals(DateUtil.dateToString(result3.get(1).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-20 00:00:00");
		assertEquals(DateUtil.dateToString(result3.get(1).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-20 00:00:01");
		//交叉
		List<AbnOperation> result4 = scheduleService.getAbnOperation(equip1,
				DateUtil.createDateTime("2010-11-18 00:00:01"), DateUtil.createDateTime("2010-11-20 00:00:01"), "3");
		assertEquals(result4.size(), 2);
		assertEquals(result4.get(0).getOperation().getName(), "卫星业务1");
		assertEquals(DateUtil.dateToString(result4.get(0).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-18 00:00:01");
		assertEquals(DateUtil.dateToString(result4.get(0).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-20 00:00:00");
		assertEquals(result4.get(1).getOperation().getName(), "卫星业务2");
		assertEquals(DateUtil.dateToString(result4.get(1).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-20 00:00:00");
		assertEquals(DateUtil.dateToString(result4.get(1).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-20 00:00:01");
		//设备对应多个通路
		List<AbnOperation> result5 = scheduleService.getAbnOperation(equip2,
				DateUtil.createDateTime("2010-11-17 00:00:00"), DateUtil.createDateTime("2010-11-18 00:00:01"), "3");
		assertEquals(result5.size(), 2);
		assertEquals(result5.get(0).getOperation().getName(), "卫星业务1");
		assertEquals(DateUtil.dateToString(result5.get(0).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-18 00:00:00");
		assertEquals(DateUtil.dateToString(result5.get(0).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-18 00:00:01");
		assertEquals(result5.get(1).getOperation().getName(), "卫星业务3");
		assertEquals(DateUtil.dateToString(result5.get(1).getStartTime(), DateUtil.FORMAT_DAYTIME),
				"2010-11-18 00:00:00");
		assertEquals(DateUtil.dateToString(result5.get(1).getEndTime(), DateUtil.FORMAT_DAYTIME), "2010-11-18 00:00:01");
	}

	@Test
	public void testSave() {
		scheduleService.save(new Schedule(new Operation(1L), new Channel(1L), new Date(), new Date()));
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Schedule.class, 4);
	}

	@Test
	public void testDelete() {
		scheduleService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Schedule.class, 2);
		assertRowsCount(Channel.class, 2);
		assertRowsCount(Operation.class, 5);
	}

	@Test
	public void testGet() {
		Schedule sche = scheduleService.get(1L);
		assertNotNull(sche);
		assertNotNull(sche.getChannel());
		assertNotNull(sche.getOperation());
	}

	@Test
	public void testGetAll() {
		assertEquals(scheduleService.getAll().size(), 3);
	}

}
