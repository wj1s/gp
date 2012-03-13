package org.jbpm.job.executor;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.job.Job;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class CustomJobExecutorThread extends JobExecutorThread {
	private final SessionFactory sessionFactory;

	public CustomJobExecutorThread(String name, JobExecutor jobExecutor, JbpmConfiguration jbpmConfiguration,
			int idleInterval, int maxIdleInterval, long maxLockTime, int maxHistory, SessionFactory sessionFactory) {
		super(name, jobExecutor, jbpmConfiguration, idleInterval, maxIdleInterval, maxLockTime, maxHistory);
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void run() {
		long waitPeriod = 0;
		try {
			currentIdleInterval = idleInterval;
			while (isActive) {
				try {
					//变更：开启OPENSESSION
					onSetUp();
					//变更结束
					Collection acquiredJobs = acquireJobs();

					if (!acquiredJobs.isEmpty()) {
						Iterator iter = acquiredJobs.iterator();
						while (iter.hasNext() && isActive) {
							Job job = (Job) iter.next();
							executeJob(job);
						}

					} else { // no jobs acquired
						if (isActive) {
							waitPeriod = getWaitPeriod();
						}
					}

					// no exception so resetting the currentIdleInterval
					currentIdleInterval = idleInterval;

				} catch (InterruptedException e) {
					log.info((isActive ? "active" : "inactivated") + " job executor thread '" + getName()
							+ "' got interrupted");
				} catch (Exception e) {
					log.error("exception in job executor thread. waiting " + currentIdleInterval + " milliseconds", e);
					try {
						synchronized (jobExecutor) {
							jobExecutor.wait(currentIdleInterval);
						}
					} catch (InterruptedException e2) {
						log.debug("delay after exception got interrupted", e2);
					}
					// after an exception, the current idle interval is doubled
					// to prevent
					// continuous exception generation when e.g. the db is
					// unreachable
					currentIdleInterval = currentIdleInterval * 2;
				} finally {
					//变更：关闭OPENSESSION
					onTearDown();
					//变更结束
				}
				//变更，将线程等待移到外边，先关闭连接
				if (isActive) {
					if (waitPeriod > 0) {
						synchronized (jobExecutor) {
							jobExecutor.wait(waitPeriod);
						}
					}
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			log.info(getName() + " leaves cyberspace");
		}
	}

	protected void onSetUp() throws Exception {
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));

	}

	protected void onTearDown() throws Exception {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.closeSession(s);

	}

	protected void flushSession() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
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

	private static Log log = LogFactory.getLog(CustomJobExecutorThread.class);
}
