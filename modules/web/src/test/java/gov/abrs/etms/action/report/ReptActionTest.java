package gov.abrs.etms.action.report;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ReptDef;
import gov.abrs.etms.service.report.ReptDefService;
import gov.abrs.etms.service.report.ReptGroup;
import gov.abrs.etms.service.report.ReptService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class ReptActionTest extends BaseActionTest {

	private ReptAction reptAction;
	private UtilService utilService;
	private ReptService reptService;
	private ReptDefService reptDefService;

	@Before
	public void setup() {
		reptAction = new ReptAction();
		utilService = control.createMock(UtilService.class);
		reptService = control.createMock(ReptService.class);
		reptDefService = control.createMock(ReptDefService.class);
		reptAction.setUtilService(utilService);
		reptAction.setReptService(reptService);
		reptAction.setReptDefService(reptDefService);
	}

	@Test
	public void testShow() {
		ReptDef reptDef = new ReptDef();
		List<ReptDef> reptDefs = Lists.newArrayList(reptDef);
		EasyMock.expect(reptDefService.getByDataSource("EXAM")).andReturn(reptDefs);
		control.replay();

		String result = reptAction.show();
		assertEquals("common", result);
	}

	@Test
	public void testReport() {
		Date date = DateUtil.createDate("2011-01-01");
		List<ReptGroup> curReptGroupList = Lists.newArrayList();
		List<ReptGroup> lastReptGroupList = Lists.newArrayList();
		List<ReptGroup> nextReptGroupList = Lists.newArrayList();

		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		EasyMock.expect(reptService.getReptGroups("201101")).andReturn(curReptGroupList);
		EasyMock.expect(reptService.getReptGroups("201012")).andReturn(lastReptGroupList);
		EasyMock.expect(reptService.getReptGroups("201102")).andReturn(nextReptGroupList);
		control.replay();
		String result = reptAction.report();
		assertEquals("report", result);
		assertEquals("2011年01月", reptAction.getCurMonth());
		assertEquals("2010年12月", reptAction.getLastMonth());
		assertEquals("2011年02月", reptAction.getNextMonth());
		assertEquals(curReptGroupList, reptAction.getCurReptGroupList());
		assertEquals(lastReptGroupList, reptAction.getLastReptGroupList());
		assertEquals(curReptGroupList, reptAction.getCurReptGroupList());

	}
}
