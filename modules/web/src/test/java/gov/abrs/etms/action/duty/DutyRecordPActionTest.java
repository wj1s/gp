package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.duty.DutyRecordP;
import gov.abrs.etms.model.duty.PatrolTime;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class DutyRecordPActionTest extends BaseActionTest {
	private DutyRecordPAction dutyRecordPAction;
	private DutyRecordService dutyRecordService;
	private UtilService utilService;

	@Before
	public void setup() {
		dutyRecordPAction = new DutyRecordPAction();
		utilService = control.createMock(UtilService.class);
		dutyRecordService = control.createMock(DutyRecordService.class);
		dutyRecordPAction.setDutyRecordService(dutyRecordService);
		dutyRecordPAction.setUtilService(utilService);
	}

	@Test
	public void testSave() {
		Date date = new Date();
		DutyRecordP model = new DutyRecordP();
		model.setPatrolTime(new PatrolTime());
		model.setId(1L);

		dutyRecordPAction.setModel(model);

		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		this.dutyRecordService.save(model);
		control.replay();

		String result = dutyRecordPAction.save();
		assertEquals(result, dutyRecordPAction.EASY);
	}
}
