package gov.abrs.etms.jms.receive;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.AssertionFailure;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReceiveData {

	private static final Logger log = Logger.getLogger(ReceiveData.class);

	public void doProcess(Object object) {
		try {
			//首先开启session,咱终于知道这个方法干嘛用的了
			onSetUp();

			JSONObject json = JSONObject.fromObject(object.toString());
			String type = json.getString("type");
			if (type.equalsIgnoreCase("REPT_AUDIT")) {
				receiveReptAudit.doProcess(json);
			} else if (type.equalsIgnoreCase("ACCD_AUDIT")) {
				receiveAccdAudit.doProcess(json, "ACCD");
			} else if (type.equalsIgnoreCase("COMMUTE_AUDIT")) {
				receiveAccdAudit.doProcess(json, "COMMUTE");
			}
		} catch (Exception e) {
			log.info(e.toString());
		} finally {
			try {
				onTearDown();
				// 变更结束
			} catch (Exception e2) {
				// 忽略更改job状态抛出的异常
				if (!(e2 instanceof AssertionFailure)) {
					e2.printStackTrace();
				}
			}
		}
	}

	protected void onSetUp() {
		Session s = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(s));
	}

	protected void onTearDown() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.closeSession(s);
	}

	private ReceiveReptAudit receiveReptAudit;

	private SessionFactory sessionFactory;

	private ReceiveAccdAudit receiveAccdAudit;

	public void setReceiveAccdAudit(ReceiveAccdAudit receiveAccdAudit) {
		this.receiveAccdAudit = receiveAccdAudit;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setReceiveReptAudit(ReceiveReptAudit receiveReptAudit) {
		this.receiveReptAudit = receiveReptAudit;
	}
}
