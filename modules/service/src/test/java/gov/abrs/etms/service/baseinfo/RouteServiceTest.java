package gov.abrs.etms.service.baseinfo;

import static org.junit.Assert.*;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Route;
import gov.abrs.etms.service.exception.CanNotDeleteException;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class RouteServiceTest extends DatabaseTestCase {
	@Autowired
	RouteService routeService;

	@Test
	public void testSave() {
		routeService.save(new Route("a", "b", new Date(), "wj"));
		this.flushSessionAndCloseSessionAndNewASession();
		assertRowsCount(Route.class, 3);
	}

	@Test
	public void testDelete() {
		assertRowsCount(Route.class, 2);
		assertRowsCount(Operation.class, 5);
		routeService.delete(2L);
		assertRowsCount(Route.class, 1);
		assertRowsCount(Operation.class, 5);
	}

	@Test(expected = CanNotDeleteException.class)
	public void testDeleteInOperationT() {
		assertRowsCount(Route.class, 2);
		assertRowsCount(Operation.class, 5);
		routeService.delete(1L);
		assertRowsCount(Route.class, 2);
		assertRowsCount(Operation.class, 5);
	}

	@Test
	public void testGet() {
		Route route = routeService.get(1L);
		assertEquals(route.getFromPl(), "A");
		assertEquals(route.getOperationTs().size(), 1);//对应业务信息
	}

	@Test
	public void testGetAll() {
		assertEquals(routeService.getAll().size(), 2);
	}

}
