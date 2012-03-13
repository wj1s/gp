package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.GroupType;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class GroupServiceTest extends DatabaseTestCase {
	@Autowired
	GroupService groupService;

	@Test
	public void testSave() {
		Group group = new Group("平台一班", new Dept(2L), new GroupType(5L));
		groupService.save(group);
		this.flushSessionAndCloseSessionAndNewASession();
		assertEquals(groupService.getAll().size(), 4);
	}

	@Test
	public void testGet() {
		Group group = groupService.get(1L);
		assertEquals(group.getName(), "卫星机房一班");
		assertEquals(group.getDept().getCode(), "1102");
		assertEquals(group.getPeople().size(), 1);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDel() {
		groupService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Dept.class, 3);
		assertRowsCount(Group.class, 1);
		assertRowsCount(Person.class, 1);
	}

	@Test
	public void testGetAll() {
		assertEquals(groupService.getAll().size(), 3);
	}

}
