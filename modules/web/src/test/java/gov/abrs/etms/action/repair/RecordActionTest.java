/**
 * 
 */
package gov.abrs.etms.action.repair;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.model.repair.Record;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.repair.CardService;
import gov.abrs.etms.service.repair.CycleCellService;
import gov.abrs.etms.service.repair.CycleService;
import gov.abrs.etms.service.repair.RecordService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.util.UtilService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author Administrator
 *
 */
public class RecordActionTest extends BaseActionTest {
	private RecordAction recordAction;
	private CycleService cycleService;
	private CycleCellService cycleCellService;
	private RecordService recordService;
	private SecurityService securityService;
	private UtilService utilService;
	private CardService cardService;

	@Before
	public void setup() {
		recordAction = new RecordAction();
		cycleService = control.createMock(CycleService.class);
		utilService = control.createMock(UtilService.class);
		cycleCellService = control.createMock(CycleCellService.class);
		securityService = control.createMock(SecurityService.class);
		recordService = control.createMock(RecordService.class);
		cardService = control.createMock(CardService.class);
		recordAction.setCycleCellService(cycleCellService);
		recordAction.setCycleService(cycleService);
		recordAction.setRecordService(recordService);
		recordAction.setSecurityService(securityService);
		recordAction.setUtilService(utilService);
		recordAction.setCardService(cardService);
	}

	@Test
	public void testSave() {
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
		record.setGroup(new Group(1L));

		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		List<Dept> depts = Lists.newArrayList(dept);
		Date date = new Date();
		Person person = new Person();
		recordAction.setCardIds(cardIds);
		recordAction.setEmpIds(empIds);
		//录制脚本
		EasyMock.expect(securityService.getCurUser()).andReturn(person).anyTimes();
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		recordService.saveRecord(record, empIds, cardIds);
		control.replay();
		String result = recordAction.save();
		JSONObject a = new JSONObject();
		a.put("result", true);
		String json = a.toString();
		assertEquals(result, RecordAction.EASY);
		assertEquals(json, recordAction.getJson());
	}

	@Test
	public void testUpdate() {
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
		record.setGroup(new Group(1L));
		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		Date date = new Date();
		Person person = new Person();
		recordAction.setCardIds(cardIds);
		recordAction.setEmpIds(empIds);
		recordAction.setId(1L);
		Record recordTemp = new Record(1L);

		//录制脚本
		EasyMock.expect(recordService.get(recordAction.getId())).andReturn(recordTemp);
		EasyMock.expect(securityService.getCurUser()).andReturn(person).anyTimes();
		EasyMock.expect(utilService.getSysTime()).andReturn(date);
		recordService.saveRecord(record, empIds, cardIds);
		control.replay();
		String result = recordAction.save();
		JSONObject a = new JSONObject();
		a.put("result", true);
		String json = a.toString();
		assertEquals(result, RecordAction.EASY);
		assertEquals(json, recordAction.getJson());
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#itemInputAjax()}.
	 */
	@Test
	public void testItemInputAjax() {
		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		Person person = new Person();
		person.setDept(dept);
		recordAction.setDpId(1L);
		List<CycleCell> cycleCells = Lists.newArrayList(
				new CycleCell(1L, new Cycle(1L, dept, "测试", "1"), "分区", Lists.newArrayList(new Card(1L))),
				new CycleCell(2L));
		List<Cycle> cycles = Lists.newArrayList(new Cycle(1L, dept, "测试", "1", cycleCells), new Cycle(2L));
		EasyMock.expect(securityService.getCurUser()).andReturn(person).anyTimes();
		EasyMock.expect(cycleService.getCycleByDept(dept)).andReturn(cycles);
		EasyMock.expect(cycleCellService.getCycleCellByDept(cycles.get(0))).andReturn(cycleCells);
		control.replay();
		String result = recordAction.itemInputAjax();
		assertEquals(result, "item_input");

		assertEquals(2, recordAction.getCycles().size());
		assertEquals(2, recordAction.getCycleCells().size());
		assertEquals(1, recordAction.getCards().size());
	}

	@Test
	public void testItemTemp() {
		Dept dept = new Dept(1L);
		//准备数据
		List<Cycle> cycles = Lists.newArrayList(new Cycle(1L, dept, "周期1", "1"));
		Person person = new Person();
		person.setDept(dept);
		//录制脚本
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		EasyMock.expect(cycleService.getCycleByDept(dept)).andReturn(cycles);
		control.replay();
		//开始测试
		String result = recordAction.itemTemp();
		assertEquals(result, "item_temp");
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#getCycleCellList()}.
	 * @throws Exception 
	 */
	@Test
	public void testGetCycleCellList() throws Exception {
		List<CycleCell> cellCellList = new ArrayList<CycleCell>();
		cellCellList.add(new CycleCell(1L));
		cellCellList.add(new CycleCell(2L));
		recordAction.setId(1L);
		Dept dept = new Dept(1L);
		Cycle cycle = new Cycle(1L);
		cycle.setDept(dept);
		cycle.setActive("1");
		EasyMock.expect(cycleService.get(recordAction.getId())).andReturn(cycle);
		EasyMock.expect(cycleCellService.getCycleCellByDept(cycle)).andReturn(cellCellList);
		control.replay();
		String result = recordAction.getCycleCellList();
		JSONObject a = new JSONObject();
		a.put("result", true);
		JSONArray array = new JSONArray();
		JSONObject json1 = new JSONObject();
		json1.put("value", 1);
		array.add(json1);
		json1.put("value", 2);
		array.add(json1);
		a.put("data", array.toString());
		String json = a.toString();
		assertEquals(result, RecordAction.EASY);
		assertEquals(json, recordAction.getJson());
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#getCardList()}.
	 */
	@Test
	public void testGetCardList() {
		recordAction.setId(1L);
		String active = "1";
		List cards = Lists.newArrayList(new Card(1L), new Card(2L));
		EasyMock.expect(cardService.getCards(recordAction.getId(), active)).andReturn(cards);
		control.replay();
		String result = recordAction.getCardList();
		assertEquals(result, recordAction.NORMAL);
		assertEquals(2, recordAction.getList().size());

	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#view()}.
	 */
	@Test
	public void testView() {
		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		Person person = new Person();
		DeptPer deptPer = new DeptPer();
		deptPer.setDept(dept);
		deptPer.setFunModuleKey(FunModule.REPAIR.toString());
		person.setDeptPers(Lists.newArrayList(deptPer));
		EasyMock.expect(securityService.getCurUser()).andReturn(person);
		control.replay();
		String result = recordAction.view();
		assertEquals(result, recordAction.SUCCESS);
		for (Dept dept1 : recordAction.getDepts()) {
			if (dept1.getId() == 1L) {
				assertTrue(true);
			} else {
				assertTrue(false);
			}
		}
		assertEquals(groups, recordAction.getGroups());
		assertSame(1L, recordAction.getDpId());
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#loadRecord()}.
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testLoadRecord() throws UnsupportedEncodingException {
		Long dpId = 1L;
		Long groupId = 1L;
		String cardName = "";
		String start = "2010-10-22";
		String end = "2010-10-24";
		Long cycleId = 1L;
		Dept dept = new Dept();
		List<Group> groups = Lists.newArrayList(new Group(1L), new Group(2L));
		dept.setId(1L);
		dept.setGroups(groups);
		Cycle cycle = new Cycle(cycleId);
		cycle.setName("周期");
		recordAction.setCardName(cardName);
		recordAction.setDpId(dpId);
		recordAction.setGroupId(groupId);
		recordAction.setStartDate(start);
		recordAction.setEndDate(end);
		recordAction.setCycleId(cycleId);
		EasyMock.expect(cycleService.get(cycleId)).andReturn(cycle);
		recordService.get(recordAction.getCarrier(), dept, cycle, new Group(groupId), DateUtil.createDate(start),
				DateUtil.createDate(end), "", null);
		control.replay();
		String result = recordAction.loadRecord();
		assertEquals(result, recordAction.GRID);
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#scanRecordDetail()}.
	 */
	@Test
	public void testScanRecordDetail() {
		recordAction.setId(1L);
		Record record = new Record(1L);
		recordAction.setRecordDetail(record);
		EasyMock.expect(recordService.get(recordAction.getId())).andReturn(record);
		control.replay();
		String result = recordAction.scanRecordDetail();
		assertTrue(record.getId().equals(recordAction.getRecordDetail().getId()));
		assertEquals(result, "detail");
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#inputRecordDetail()}.
	 */
	@Test
	public void testInputRecordDetail() {
		recordAction.setId(1L);
		Record record = new Record(1L);
		recordAction.setRecordDetail(record);
		EasyMock.expect(recordService.get(recordAction.getId())).andReturn(record);
		control.replay();
		String result = recordAction.inputRecordDetail();
		assertTrue(record.getId().equals(recordAction.getRecordDetail().getId()));
		assertEquals(result, "edit");
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.repair.RecordAction#delRecord()}.
	 */
	@Test
	public void testDelRecord() {
		String deleteRp = "[{id:'1'},{id:'2'}]";
		recordAction.setDelIds(deleteRp);
		JSONArray array = JSONArray.fromObject(recordAction.getCarrier().getDelIds());
		for (Object object : array) {
			Long delId = ((JSONObject) object).getLong("id");
			recordService.delete(delId);
		}
		control.replay();
		String result = recordAction.delRecord();
		assertEquals(result, RecordAction.RIGHT);
	}
}
