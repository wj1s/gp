package org.jbpm.job.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomJobExecutor extends JobExecutor {

	private static final long serialVersionUID = -8923441429976941922L;
	public static JbpmConfiguration jbpmConfig;
	public static SessionFactory sessionFactory;

	@Autowired
	public static void setJbpmConfiguration(JbpmConfiguration config) {
		jbpmConfig = config;
	}

	@Autowired
	public static void setSessionFactory(SessionFactory sessionFac) {
		sessionFactory = sessionFac;
	}

	@Override
	public synchronized void start() {
		// 变更，提供现成数据库环境，可以自动轮寻查找待处理JOB，JOB执行
		// 的HANDLER不使用此处环境，建议使用JBPMTEMPLETE。
		if (!isStarted) {
			log.debug("starting thread group '" + name + "'...");
			for (int i = 0; i < nbrOfThreads; i++) {
				startThread();
			}
			isStarted = true;
		} else {
			log.debug("ignoring start: thread group '" + name + "' is already started'");
		}

		lockMonitorThread = new LockMonitorThread(jbpmConfig, lockMonitorInterval, maxLockTime, lockBufferTime);
	}

	@Override
	protected synchronized void startThread() {
		String threadName = getNextThreadName();
		// 变更：调用自定义JOBEXECUTORTHREAD类，实现调度线程的OPENSESSION
		Thread thread = new CustomJobExecutorThread(threadName, this, jbpmConfig, idleInterval, maxIdleInterval,
				maxLockTime, historyMaxSize, sessionFactory);
		threads.put(threadName, thread);
		log.debug("starting new job executor thread '" + threadName + "'");
		thread.start();
	}

	private static Log log = LogFactory.getLog(CustomJobExecutor.class);
}
