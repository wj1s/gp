/**
 * 
 */
package gov.abrs.etms.action.common;

import static org.junit.Assert.*;
import gov.abrs.etms.action.BaseActionTest;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.service.baseinfo.PersonService;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class PersonExActionTest extends BaseActionTest {
	private PersonExAction personExAction;
	private PersonService personService;

	@Before
	public void setup() {
		personExAction = new PersonExAction();
		personService = control.createMock(PersonService.class);
		personExAction.setPersonService(personService);
	}

	@Test
	public void testGetUserUsedAjax() {
		String ids = "1,2,3";
		personExAction.setIds(ids);
		List<Person> persons = new ArrayList<Person>();
		persons.add(new Person(1L));
		persons.add(new Person(2L));
		persons.add(new Person(3L));
		EasyMock.expect(personService.getPersons(ids)).andReturn(persons);
		control.replay();
		String result = personExAction.getUserUsedAjax();
		List<Person> personTest = personExAction.getPersons();
		assertEquals(personTest.size(), 3);
	}

	/**
	 * Test method for {@link gov.abrs.etms.action.common.PersonExAction#getUserTree()}.
	 */
	//@Test
	//public void testGetUserTree() {
	//fail("Not yet implemented");
	//}

}
