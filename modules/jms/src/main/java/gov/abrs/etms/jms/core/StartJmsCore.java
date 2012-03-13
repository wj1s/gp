package gov.abrs.etms.jms.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.jbpm.job.executor.CustomJobExecutorServiceStart;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartJmsCore {
	private static final Log log = LogFactory.getLog(StartJmsCore.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("D:/EtmsPlatform/etms-properties/log4j.properties");
		String[] strs = { "classpath*:conf/spring/applicationContext.xml",
				"classpath*:conf/spring/applicationContext-jms-all.xml" };
		ApplicationContext appContext = new ClassPathXmlApplicationContext(strs);
		CustomJobExecutorServiceStart customJobExecutorServiceStart = (CustomJobExecutorServiceStart) appContext
				.getBean("jobExecutorServiceStart");
		if ("on".equals(customJobExecutorServiceStart.getIsOpen())) {
			log.info("JBPM调度进程启动成功，并设置为on，开始定时扫描JOB。");
		} else {
			log.info("JBPM调度进程启动成功，但设置为off，不会扫描JOB。");
		}
	}
}
