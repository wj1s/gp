package gov.abrs.etms.service.util;

import gov.abrs.etms.dao.util.ExecuteDAO;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UtilService {

	@Autowired
	private ExecuteDAO executeDAO;

	public Date getSysTime() {
		return (Date) executeDAO.getSession().createQuery("SELECT CURRENT_TIMESTAMP() from Station").uniqueResult();
	}
}
