package org.jbpm.job.executor;

import org.hibernate.SessionFactory;
import org.jbpm.JbpmConfiguration;

public class CustomJobExecutorServiceStart {
	JbpmConfiguration jbpmConfiguration;

	SessionFactory sessionFactory;

	private String isOpen;

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration) {
		this.jbpmConfiguration = jbpmConfiguration;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void startTmsJbpmJob() {
		if ("on".equals(isOpen)) {
			CustomJobExecutor.setJbpmConfiguration(jbpmConfiguration);
			CustomJobExecutor.setSessionFactory(sessionFactory);
			jbpmConfiguration.startJobExecutor();
			System.out.println("JBPM调度进程启动!");
		}
	}
}
