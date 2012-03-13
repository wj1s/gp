/**
 * 
 */
package gov.abrs.etms.service.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Record;
import gov.abrs.etms.model.repair.RecordItem;
import gov.abrs.etms.service.baseinfo.GroupService;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

/**
 * @author 郭翔
 *
 */
@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class RecordServiceTest extends DatabaseTestCase {
	@Autowired
	private RecordService recordService;
	@Autowired
	private GroupService groupService;
	private int recordCount = 0;
	private int recordItemCount = 0;

	@Before
	public void setup() {
		recordCount = this.getRowsCount(Record.class);
		recordItemCount = this.getRowsCount(RecordItem.class);
	}

	@Test
	public void testSaveRecordSuffix() {

		String[] empIds = { "0!heng-gang!检修1(十日检)!dot!周期表1!heng-gang!1,2,",
				"0!heng-gang!检修3(十日检)!dot!周期表1!heng-gang!1,2,", "1!heng-gang!qwsdsd!dot!周期表4!heng-gang!1," };
		String[] cardIds = { "1", "2", "D21" };
		Record record = new Record();
		record.setDdate(new Date());
		record.setExaminePersons("管理员,用户,测试用户");
		record.setMeasure("戴防静电手镯、挂检修人的安全名牌。戴防静电手镯、挂检修人的安全名牌。戴防静电手镯、挂检修人的安全名牌。戴防静电手镯、挂检修人的安全名牌。");
		record.setPrincipal("管理员");
		record.setSecurity("管理员");
		record.setPersonName("管理员");
		record.setExamineRecord("1.擦拭卫星接收机的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机后面的插线是否插好、插牢。  1.擦拭卫星接收机的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机后面的插线是否插好、插牢。  1.擦拭卫星接收机、功分器的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机、功分器后面的插线是否插好、插牢。  4.查看功分器前面板的电源指示灯是否正常  1.擦拭卫星接收机的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机后面的插线是否插好、插牢。  1.擦拭卫星接收机、功分器的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机、功分器后面的插线是否插好、插牢。  4.查看功分器前面板的电源指示灯是否正常 ");
		record.setTest("试机成功");
		record.setUpdDate(new Date());
		record.setTimeLength(120);
		record.setGroup(groupService.get(1L));
		recordService.saveRecord(record, empIds, cardIds);
		Long id = record.getId();
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Record.class, recordCount + 1);
		Record ca = recordService.get(id);
		assertNotNull(ca);
		assertEquals(ca.getPersonName(), "管理员");
		assertEquals(ca.getRecordItems().size(), 3);
		assertEquals(ca.getRecordItems().get(0).getPersons().size(), 2);
		assertEquals(ca.getRecordItems().get(1).getPersons().size(), 2);
		assertEquals(ca.getRecordItems().get(2).getPersons().size(), 1);
		assertEquals(getRowsCount(RecordItem.class), recordItemCount + 3);
	}

	@Test
	public void testUpdateRecordSuffix() {
		String[] empIds = { "0!heng-gang!检修1(十日检)!dot!周期表1!heng-gang!1,2,",
				"0!heng-gang!检修3(十日检)!dot!周期表1!heng-gang!1,2,", "1!heng-gang!qwsdsd!dot!周期表4!heng-gang!1," };
		String[] cardIds = { "1", "2", "D21" };
		Record record = recordService.get(1L);
		record.setDdate(new Date());
		record.setExaminePersons("管理员,用户,测试用户");
		record.setMeasure("戴防静电手镯、挂检修人的安全名牌。戴防静电手镯、挂检修人的安全名牌。戴防静电手镯、挂检修人的安全名牌。戴防静电手镯、挂检修人的安全名牌。");
		record.setPrincipal("管理员");
		record.setSecurity("管理员");
		record.setPersonName("管理员");
		record.setExamineRecord("1.擦拭卫星接收机的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机后面的插线是否插好、插牢。  1.擦拭卫星接收机的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机后面的插线是否插好、插牢。  1.擦拭卫星接收机、功分器的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机、功分器后面的插线是否插好、插牢。  4.查看功分器前面板的电源指示灯是否正常  1.擦拭卫星接收机的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机后面的插线是否插好、插牢。  1.擦拭卫星接收机、功分器的面板。  2.查看接收机前面板的电源和锁定灯是否为正常的红色。  3.检查接收机、功分器后面的插线是否插好、插牢。  4.查看功分器前面板的电源指示灯是否正常 ");
		record.setTest("试机成功");
		record.setUpdDate(new Date());
		record.setTimeLength(120);
		recordService.saveRecord(record, empIds, cardIds);
		Long id = record.getId();
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Record.class, recordCount);
		Record ca = recordService.get(id);
		assertNotNull(ca);
		assertEquals(ca.getPersonName(), "管理员");
		assertEquals(ca.getRecordItems().size(), 3);
		assertEquals(ca.getRecordItems().get(0).getPersons().size(), 2);
		assertEquals(ca.getRecordItems().get(1).getPersons().size(), 2);
		assertEquals(ca.getRecordItems().get(2).getPersons().size(), 1);
		assertEquals(getRowsCount(RecordItem.class), recordItemCount + 1);
	}

	/**
	 * Test method for {@link gov.abrs.etms.service.repair.RecordService#get(gov.abrs.etms.common.util.Carrier, gov.abrs.etms.model.baseinfo.Dept, gov.abrs.etms.model.repair.Cycle, gov.abrs.etms.model.baseinfo.Group, java.util.Date, java.util.Date, java.lang.String)}.
	 */
	@Test
	public void testGetCarrierOfRecordDeptCycleGroupDateDateString() {
		//this.recordService.get(carrier, new Dept(1L), new Cycle(1L), new Group(1L), "20110101", "20110111", "");
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link gov.abrs.etms.service.repair.RecordService#delete(java.lang.String)}.
	 */

	@Test
	public void testDeleteStringSuffix() {
		int personCount = this.getRowsCount(Person.class);
		int equipCount = this.getRowsCount(Equip.class);
		int personInRecordCount = this.getRowsCount("TB_ETMS_REPAIR_RECORD_PERSON");
		int equipInRecordCount = this.getRowsCount("TB_ETMS_REPAIR_RECORD_EQUIP");
		int cardCount = this.getRowsCount(Card.class);
		this.recordService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertEquals(getRowsCount(Record.class), recordCount - 1);
		assertEquals(getRowsCount(RecordItem.class), recordItemCount - 2);
		assertEquals(getRowsCount(Person.class), personCount);
		assertEquals(getRowsCount(Equip.class), equipCount);
		assertEquals(getRowsCount("TB_ETMS_REPAIR_RECORD_PERSON"), personInRecordCount - 3);
		assertEquals(getRowsCount("TB_ETMS_REPAIR_RECORD_EQUIP"), equipInRecordCount - 2);
		assertEquals(getRowsCount(Card.class), cardCount);
	}

}
