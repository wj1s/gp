package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.sys.RoleService;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class PersonServiceTest extends DatabaseTestCase {
	@Autowired
	PersonService personService;
	@Autowired
	RoleService roleService;
	@Autowired
	DeptService deptService;

	@Test
	public void testGet() {
		Person person = personService.get(1L);
		assertEquals(person.getName(), "王健");
		assertEquals(person.getGroup().getName(), "卫星机房一班");
		assertEquals(person.getDept().getName(), "卫星机房");
	}

	@Test
	public void testGetSuffix() {
		Person person = personService.get("wj");
		assertEquals(person.getName(), "王健");
		assertEquals(person.getGroup().getName(), "卫星机房一班");
		assertEquals(person.getDept().getName(), "卫星机房");
		assertEquals(person.getRoles().size(), 2);
		assertEquals(person.getDeptPers().size(), 3);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDelete() {
		int count = this.getRowsCount(Person.class);
		int deptPerCount = this.getRowsCount(DeptPer.class);
		personService.delete(1L);
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Person.class, count - 1);
		assertRowsCount(DeptPer.class, deptPerCount - 3);
	}

	@Test
	public void testGetAll() {
		assertEquals(personService.getAll().size(), 4);
	}

	@Test
	public void testPersons() {
		String personIds = "1L,2L";
		List<Person> persons = personService.getPersons(personIds);
		assertEquals(persons.size(), 2);
	}

	@Test
	public void testPersonsNo() {
		String personIds = "";
		List<Person> persons = personService.getPersons(personIds);
		assertEquals(persons.size(), 0);
	}

	@Test
	public void testSave() {
		Person person = personService.get(1L);
		person.setRoles(Lists.newArrayList(roleService.get(1L)));
		personService.save(person);
		assertEquals(1, personService.getByName("王健").getRoles().size());
		assertEquals("值班员", personService.getByName("王健").getRoles().get(0).getDesc());
	}

	@Test
	public void testGetByNameLike() {
		String q = "w";
		List<Person> list = personService.getByNameLike(q);
		assertEquals(1, list.size());
	}
}
