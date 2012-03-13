package gov.abrs.etms.adsyn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 * description:
 * author:郭翔
 * date：2011/11/15
 */

public class StartAdsynCore {
	private static final Log logger = LogFactory.getLog(StartAdsynCore.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("D:/EtmsPlatform/etms-properties/log4j.properties");
		String[] strs = { "classpath*:conf/spring/applicationContext.xml",
				"classpath*:conf/spring/applicationContext-adsyn.xml" };
		ApplicationContext appContext = new ClassPathXmlApplicationContext(strs);
		logger.info("AD人员同步已经开始！");

	}
}
