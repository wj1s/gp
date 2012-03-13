package gov.abrs.etms.action.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.baseinfo.ScheduleService;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ScheduleActionTest extends BaseActionTest {

	private ScheduleAction scheduleAction;

	private ScheduleService mockScheduleService;
	private EquipService mockEquipService;

	@Before
	public void setUp() {
		scheduleAction = new ScheduleAction();
		mockScheduleService = control.createMock(ScheduleService.class);
		mockEquipService = control.createMock(EquipService.class);
		scheduleAction.setScheduleService(mockScheduleService);
		scheduleAction.setEquipService(mockEquipService);
	}

	@Test
	public void testGetOperation() throws UnsupportedEncodingException {
		//准备数据
		Date startTime = new Date();
		Date endTime = new Date();
		Equip equip = new Equip(1L);
		String equipName = "s";
		String transType = "1";
		scheduleAction.setEquipName(equipName);
		scheduleAction.setStartTime(startTime);
		scheduleAction.setEndTime(endTime);
		scheduleAction.setTransType(transType);
		List<AbnOperation> aos = Lists.newArrayList(new AbnOperation(1L));
		//录制脚本
		EasyMock.expect(mockEquipService.getByName(equipName)).andReturn(equip);
		EasyMock.expect(mockScheduleService.getAbnOperation(equip, startTime, endTime, transType)).andReturn(aos);
		control.replay();
		//执行测试
		scheduleAction.getAbnOperationsByEquipAjax();
		//验证结构
		assertEquals(scheduleAction.getList(), aos);
	}
}
