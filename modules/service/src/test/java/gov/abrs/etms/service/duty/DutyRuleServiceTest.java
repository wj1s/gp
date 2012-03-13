package gov.abrs.etms.service.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.duty.RuleItem;
import gov.abrs.etms.service.baseinfo.DeptService;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class DutyRuleServiceTest extends DatabaseTestCase {
	@Autowired
	private DutyRuleService dutyRuleService;
	@Autowired
	private DeptService deptService;

	//测试保存
	@Test
	public void testSave() {
		Dept dept = this.deptService.get(1L);
		int countDutyRule = this.getRowsCount(DutyRule.class);
		int countDutySchedule = this.getRowsCount(DutySchedule.class);

		DutyRule dutyRule = new DutyRule();
		dutyRule.setDept(dept);
		dutyRule.setRuleName("一运转");
		dutyRule.setDayPartCount(1);

		List<DutySchedule> dutySchedules = new ArrayList<DutySchedule>();
		DutySchedule dutySchedule = new DutySchedule();
		dutySchedule.setDutyRule(dutyRule);
		dutySchedule.setSchName("早班");
		dutySchedule.setStartTime(DateUtil.stringToDate("1970-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
		dutySchedule.setEndTime(DateUtil.stringToDate("1970-01-01 8:00:00", "yyyy-MM-dd HH:mm:ss"));
		dutySchedule.setSchOrder(1);
		dutySchedules.add(dutySchedule);
		dutyRule.setDutySchedules(dutySchedules);
		this.dutyRuleService.save(dutyRule);

		dutyRule.setDutySchedules(dutySchedules);

		assertEquals(this.getRowsCount(DutyRule.class), countDutyRule + 1);
		assertEquals(this.getRowsCount(DutySchedule.class), countDutySchedule + 1);
	}

	//测试某部门是否有某运转输的规则(是)
	@Test
	public void testHasDutyRule1() {
		Boolean flag = this.dutyRuleService.hasDutyRule(1L, 1);
		assertTrue(flag);
	}

	//测试某部门是否有某运转输的规则(否)
	@Test
	public void testHasDutyRule2() {
		Boolean flag = this.dutyRuleService.hasDutyRule(3L, 1);
		assertFalse(flag);
	}

	//测试删除排班规则（级联）
	@Test
	public void testDelete() {
		int countDutyRule = this.getRowsCount(DutyRule.class);
		int countDutySchedule = this.getRowsCount(DutySchedule.class);
		int countRuleItem = this.getRowsCount(RuleItem.class);
		this.dutyRuleService.delete(1L);

		assertEquals(this.getRowsCount(DutyRule.class), countDutyRule - 1);
		assertEquals(this.getRowsCount(DutySchedule.class), countDutySchedule - 1);
		assertEquals(this.getRowsCount(RuleItem.class), countRuleItem - 2);

	}
}
