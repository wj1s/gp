package gov.abrs.etms.action.repair;

import static org.junit.Assert.assertEquals;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.repair.Period;
import gov.abrs.etms.service.repair.PeriodService;

import java.io.UnsupportedEncodingException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class PeriodActionTest extends BaseActionTest {

	private PeriodAction periodAction;
	private PeriodService periodService;
	private ExecuteDAO mockExecuteDAO;
	private DateUtil dateUtil;

	@Before
	public void setUp() {
		periodAction = new PeriodAction();
		mockExecuteDAO = control.createMock(ExecuteDAO.class);
		periodService = control.createMock(PeriodService.class);
		dateUtil = control.createMock(DateUtil.class);
		periodAction.setExecuteDAO(mockExecuteDAO);
		periodAction.setPeriodService(periodService);
	}

	@Test
	public void testUpdatePresave() throws UnsupportedEncodingException {
		//准备数据
		Period period = new Period(1L, "test", 20, "2");
		periodAction.setId(1L);
		periodAction.setStartDay("2010-01-02");
		periodAction.setEndDay("2011-01-01");
		//录制脚本
		EasyMock.expect(periodService.get(periodAction.getId())).andReturn(period);
		control.replay();
		//执行测试
		periodAction.preSave(period);
		//校验结果		
		assertEquals(DateUtil.dateToString(period.getStartDay(), "yyyy-MM-dd"), periodAction.getStartDay());
		assertEquals(DateUtil.dateToString(period.getEndDay(), "yyyy-MM-dd"), periodAction.getEndDay());
	}

	@Test
	public void testAddPresave() throws UnsupportedEncodingException {
		//准备数据
		Period period = new Period();
		period.setType("2");
		period.setName("test");
		period.setPreviousValue(20);
		periodAction.setStartDay("2010-01-02");
		periodAction.setEndDay("2011-01-01");
		//录制脚本				
		control.replay();
		//执行测试
		periodAction.preSave(period);
		//校验结果		
		assertEquals(DateUtil.dateToString(period.getStartDay(), "yyyy-MM-dd"), periodAction.getStartDay());
		assertEquals(DateUtil.dateToString(period.getEndDay(), "yyyy-MM-dd"), periodAction.getEndDay());
	}
}
