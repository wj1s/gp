package gov.abrs.etms.service.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.duty.DutyPrompt;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class DutyPromptServiceTest extends DatabaseTestCase {
	@Autowired
	private DutyPromptService dutyPromptService;

	//查找某部门某天的值班提醒
	@Test
	public void testGetDutyPromptThisDay() {
		List<DutyPrompt> list = this.dutyPromptService.getDutyPromptNow(new Dept(1L), DateUtil
				.createDateTime("2010-11-25 9:30:00"));
		assertEquals(1, list.size());
	}

	//测试某部门某天是否有值班提醒(是)
	@Test
	public void testHasDutyPromptNow1() {
		Boolean flag = this.dutyPromptService.hasDutyPromptNow(new Dept(1L), DateUtil
				.createDateTime("2010-11-24 9:20:22"));
		assertTrue(flag);
	}

	//测试某部门某天是否有值班提醒(否)
	@Test
	public void testHasDutyPromptNow2() {
		Boolean flag = this.dutyPromptService.hasDutyPromptNow(new Dept(1L), DateUtil
				.createDateTime("2010-11-26 9:40:00"));
		assertFalse(flag);
	}

	//测试删除值班提醒
	@Test
	public void testDelete() {
		int countPrompt = getRowsCount(DutyPrompt.class);
		this.dutyPromptService.delete(1L);
		assertEquals(getRowsCount(DutyPrompt.class), countPrompt - 1);
	}
}
