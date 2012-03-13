package gov.abrs.etms.action.duty;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.duty.DutyRecordO;
import gov.abrs.etms.service.duty.DutyRecordService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class DutyRecordOActionTest extends BaseActionTest {
	private DutyRecordOAction dutyRecordOAction;
	private DutyRecordService dutyRecordService;
	private UtilService utilService;

	@Before
	public void setup() {
		dutyRecordOAction = new DutyRecordOAction();
		utilService = control.createMock(UtilService.class);
		dutyRecordService = control.createMock(DutyRecordService.class);
		dutyRecordOAction.setDutyRecordService(dutyRecordService);
		dutyRecordOAction.setUtilService(utilService);
	}

	@Test
	public void testSave() {
		Date date = new Date();
		DutyRecordO model = new DutyRecordO();
		model.setId(1L);

		dutyRecordOAction.setModel(model);

		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		this.dutyRecordService.save(model);
		control.replay();

		String result = dutyRecordOAction.save();
		assertEquals(result, dutyRecordOAction.EASY);
	}
}
