/**
 * 
 */
package com.taiji.common.test;

import static org.junit.Assert.*;

import java.math.BigInteger;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Spring的支持数据库访问,事务控制和依赖注入的JUnit4 集成测试基类,相比Spring原基类名字更短.
 *   
 * 子类需要定义applicationContext文件的位置, 如:
 * @ContextConfiguration(locations = { "/applicationContext-test.xml" })
 * 
 * @author calvin
 */
public abstract class OpenSessionInUnitTests extends AbstractJUnit4SpringContextTests {
	protected SessionFactory sessionFactory;

	protected DataSource dataSource;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Before
	public void openSession() throws Exception {
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));

	}

	@After
	public void closeSession() throws Exception {
		Session s = null;
		try {
			SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
			s = holder.getSession();
			s.flush();
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(s);
		}

	}

	protected void flushSession() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
	}

	protected void assertRowsCount(Class entityClass, int expected) {
		String countHql = "select count(*) from " + entityClass.getSimpleName();
		try {

			Long count = (Long) sessionFactory.getCurrentSession().createQuery(countHql).uniqueResult();
			assertEquals(count.intValue(), expected);
		} catch (Exception e) {
			fail("hql can't be auto count, hql is:" + countHql);
			e.printStackTrace();
		}
	}

	protected int getRowsCount(Class entityClass) {
		String countHql = "select count(*) from " + entityClass.getSimpleName();
		try {
			Long size = (Long) sessionFactory.getCurrentSession().createQuery(countHql).uniqueResult();
			return size.intValue();
		} catch (Exception e) {
			fail("hql can't be auto count, hql is:" + countHql);
			e.printStackTrace();
			return -1;
		}
	}

	protected void assertRowsCount(String tableName, int expected) {
		String countHql = "select count(*) from " + tableName;
		try {
			BigInteger count = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(countHql).uniqueResult();
			assertEquals(count.intValue(), expected);
		} catch (Exception e) {
			fail("hql can't be auto count, hql is:" + countHql);
			e.printStackTrace();
		}
	}

	protected int getRowsCount(String tableName) {
		String countHql = "select count(*) from " + tableName;
		try {
			BigInteger size = (BigInteger) sessionFactory.getCurrentSession().createSQLQuery(countHql).uniqueResult();
			return size.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			fail("hql can't be auto count, hql is:" + countHql);
			return -1;
		}
	}

	protected void flushSessionAndCloseSessionAndNewASession() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession();
		try {
			s.flush();
		} finally {
			TransactionSynchronizationManager.unbindResource(sessionFactory);
			SessionFactoryUtils.closeSession(s);
			Session newSession = sessionFactory.openSession();
			TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(newSession));
		}
	}
}
