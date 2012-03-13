package gov.abrs.etms.model;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.persister.entity.EntityPersister;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.taiji.common.test.DatabaseTestCase;

/**
 * 简单测试所有Entity类的O/R Mapping.
 */
@ContextConfiguration(locations = { "/conf/spring/applicationContext-test.xml" })
public class HibernateMappingTest extends DatabaseTestCase {
	@Test
	@SuppressWarnings("unchecked")
	public void allClassMapping() throws Exception {
		Session session = sessionFactory.openSession();

		try {
			Map metadata = sessionFactory.getAllClassMetadata();
			for (Object o : metadata.values()) {
				EntityPersister persister = (EntityPersister) o;
				String className = persister.getEntityName();
				Query q = session.createQuery("from " + className + " c");
				q.iterate();
				logger.debug("ok: " + className);
			}
		} finally {
			session.close();
		}
	}
}
