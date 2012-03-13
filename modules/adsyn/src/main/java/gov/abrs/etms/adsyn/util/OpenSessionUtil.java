package gov.abrs.etms.adsyn.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Repository
public class OpenSessionUtil {
	private SessionFactory sessionFactoryAdSyn;

	/**/
	/*
	 * 模拟opensessionInView
	 */
	public void onSetUp() throws Exception {
		Session s = sessionFactoryAdSyn.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactoryAdSyn, new SessionHolder(s));

	}

	public void onTearDown() throws Exception {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactoryAdSyn);
		Session s = holder.getSession();
		s.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactoryAdSyn);
		SessionFactoryUtils.closeSession(s);

	}

	public void flushSession() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactoryAdSyn);
		Session s = holder.getSession();
		s.flush();
	}

	private void flushSessionAndCloseSessionAndNewASession() {
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactoryAdSyn);
		Session s = holder.getSession();
		try {
			s.flush();
		} finally {
			TransactionSynchronizationManager.unbindResource(sessionFactoryAdSyn);
			SessionFactoryUtils.closeSession(s);
			Session newSession = sessionFactoryAdSyn.openSession();
			TransactionSynchronizationManager.bindResource(sessionFactoryAdSyn, new SessionHolder(newSession));
		}
	}

	@Autowired
	public void setSessionFactoryAdSyn(SessionFactory sessionFactoryAdSyn) {
		this.sessionFactoryAdSyn = sessionFactoryAdSyn;
	}

}
