package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Device;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.Station;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.duty.RuleItem;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class DeptServiceTest extends DatabaseTestCase {
	@Autowired
	private DeptService deptService;
	@Autowired
	private ParaDtlService paraDtlService;

	private int deptCount;
	private int groupCount;
	private int personCount;
	private int deviceCount;
	private int transTypeCount;

	@Before
	public void setup() {
		deptCount = this.getRowsCount(Dept.class);
		groupCount = this.getRowsCount(Group.class);
		personCount = this.getRowsCount(Person.class);
		deviceCount = this.getRowsCount(Device.class);
		transTypeCount = this.getRowsCount(TransType.class);
	}

	//测试新增部门
	@Test
	public void testSave() {
		Dept dept = new Dept();
		dept.setCode("1");
		dept.setName("a");
		dept.setDeptType(new DeptType(new Long(14)));
		dept.setStation(new Station(new Long(1)));
		deptService.save(dept);
		List<Dept> all = deptService.getAll();
		assertEquals(all.size(), 5);
	}

	//测试新增部门
	@Test
	public void testUpdate() {
		assertRowsCount(TransType.class, 3);
		Dept dept = deptService.get(3L);
		assertEquals(dept.getTransTypes().size(), 0);
		TransType transType = (TransType) this.paraDtlService.get(3L);
		dept.getTransTypes().add(transType);
		deptService.save(dept);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(TransType.class, 3);
		Dept result = deptService.get(3L);
		assertEquals(result.getTransTypes().size(), 1);
	}

	@Test
	public void testUpdate1() {
		assertRowsCount(TransType.class, 3);
		Dept dept = deptService.get(3L);
		assertEquals(dept.getTransTypes().size(), 0);
		TransType transType = (TransType) this.paraDtlService.get(3L);
		dept.setTransTypes(Lists.newArrayList(transType));
		deptService.save(dept);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(TransType.class, 3);
		Dept result = deptService.get(3L);
		assertEquals(result.getTransTypes().size(), 1);
	}

	@Test
	public void testGetAll() {
		List<Dept> all = deptService.getAll();
		assertEquals(all.size(), 4);
	}

	@Test
	public void testDelOn() {
		int midTable = this.getRowsCount("TB_ETMS_BASE_DP_TRTP");
		deptService.delete(3L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Dept.class, deptCount - 1);
		assertRowsCount(Group.class, groupCount - 1);
		assertRowsCount(Person.class, personCount);
		assertRowsCount(Device.class, deviceCount);
		//业务类型不变化，因为3不关联业务类型，所以删除不会导致中间表变化
		assertRowsCount(TransType.class, transTypeCount);
		assertRowsCount("TB_ETMS_BASE_DP_TRTP", midTable);

		assertRowsCount(DutyRule.class, 2);
		assertRowsCount(DutySchedule.class, 3);
		assertRowsCount(RuleItem.class, 2);
		assertRowsCount(Duty.class, 17);
	}

	@Test
	public void testDelOn1() {
		int midTable = this.getRowsCount("TB_ETMS_BASE_DP_TRTP");
		deptService.delete(4L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Dept.class, deptCount - 1);
		assertRowsCount(Group.class, groupCount);
		assertRowsCount(Person.class, personCount);
		assertRowsCount(Device.class, deviceCount);
		//业务类型不变化，因为4关联业务类型，所以删除会导致中间表变化
		assertRowsCount(TransType.class, transTypeCount);
		assertRowsCount("TB_ETMS_BASE_DP_TRTP", midTable - 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDelOff() {
		deptService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Dept.class, deptCount);
		assertRowsCount(Group.class, groupCount);
		assertRowsCount(Person.class, personCount);
		assertRowsCount(Device.class, deviceCount);
		assertRowsCount(DutyRule.class, 3);
		assertRowsCount(DutySchedule.class, 5);
		assertRowsCount(RuleItem.class, 3);
		assertRowsCount(Duty.class, 17);
		assertRowsCount(Cycle.class, 3);
	}

	@Test
	public void testGet() {
		Dept dept = deptService.get(1L);
		assertEquals(dept.getName(), "卫星机房");
		assertEquals(dept.getStation().getName(), "北京地球站");
		assertEquals(dept.getGroups().size(), 2);
		assertEquals(dept.getPersons().size(), 3);
	}

	@Test
	public void testGet1() {
		DeptType paraDtl = (DeptType) paraDtlService.getByCode(DeptType.class, "TKMG");
		Dept dept = deptService.get(paraDtl).get(0);
		assertEquals(dept.getName(), "技办室");
	}

}
