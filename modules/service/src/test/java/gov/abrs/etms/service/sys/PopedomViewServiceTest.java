package gov.abrs.etms.service.sys;

import static org.junit.Assert.*;
import gov.abrs.etms.model.sys.PopedomView;
import gov.abrs.etms.model.sys.Role;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.google.common.collect.Lists;
import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class PopedomViewServiceTest extends DatabaseTestCase {

	@Autowired
	private PopedomViewService popedomViewService;

	@Test
	public void testGetNeedCrudPop() {
		List<Role> roles = Lists.newArrayList(new Role(1L), new Role(2L));
		String url = "baseinfo/tache!show.action";
		List<PopedomView> list = this.popedomViewService.getNeedCrudPop(roles, url);
		assertNotNull(list);
		assertEquals(3, list.size());
	}
}
