package gov.abrs.etms.service.security;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.FunModule;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class SecurityServiceTest extends DatabaseTestCase {
	private final String userName = "wj";
	@Autowired
	private SecurityService securityService;

	protected IMocksControl control = EasyMock.createControl();

	@Before
	public void setUp() {
		UserDetails userDetails = control.createMock(UserDetails.class);
		Authentication authentication = control.createMock(Authentication.class);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		EasyMock.expect(authentication.getName()).andReturn(userName);
		control.replay();
	}

	@After
	public void tearDown() {
		control.verify();
	}

	@Test
	public void testGetCurUser() {
		Person person = securityService.getCurUser();
		assertNotNull(person);
		assertEquals(person.getName(), "王健");
	}

	@Test
	public void testGetCurRoles() {
		assertEquals(securityService.getCurRoles().size(), 2);
	}

	@Test
	public void testGetCurDeptPers() {
		assertEquals(securityService.getCurDeptPers().size(), 3);
	}

	@Test
	public void testGetCurDeptPersSuffix() {
		assertEquals(securityService.getCurDeptPers(FunModule.ACCD).size(), 2);
	}

	@Test
	public void testGetCurTechSystems() {
		assertEquals(securityService.getCurTechSystems(FunModule.ACCD).size(), 3);
	}

}
