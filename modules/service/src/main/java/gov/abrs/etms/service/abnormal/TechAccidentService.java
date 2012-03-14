package gov.abrs.etms.service.abnormal;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.abnormal.TechAccident;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TechAccidentService extends CrudService<TechAccident> {
	private ExecuteDAO executeDAO;

    @Autowired
    private AbnEquipService abnEquipService;


	@Autowired
	public void setExecuteDAO(ExecuteDAO executeDAO) {
		this.executeDAO = executeDAO;
	}

	//分页查询
	@SuppressWarnings("unchecked")
	public void findByInstance(TechAccident instance, final Carrier carrier) {
		String sql = this.getSQL(instance);
		Query query = executeDAO.getSession().createQuery(sql);
		this.setQuery(query, instance);
		carrier.setTotalSize(query.list().size());
		setPageParameterToQuery(query, carrier);
		List result = query.list();
		carrier.setResult(result);
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	protected Query setPageParameterToQuery(final Query q, final Carrier carrier) {

		Assert.isTrue(carrier.getPageSize() > 0, "Page Size must larger than zero");

		//hibernate的firstResult的序号从0开始
		q.setFirstResult(carrier.getFirstResult());
		q.setMaxResults(carrier.getPageSize());
		return q;
	}

	private String getSQL(TechAccident instance) {
		StringBuffer sql = new StringBuffer("from TechAccident trans where 1=1 ");
		if (instance != null) {
			//查询条件：事故性质，事故状态，事故发生时间
			if (instance.getAccdKind() != null) {//事故性质
				sql.append(" and trans.accdKind = :accdKind ");
			}
			if (instance.getStartTime() != null) {//开始时间
				sql.append(" and datediff (s , trans.startTime , :startTime) <= 0 ");
			}
			if (instance.getEndDate() != null) {
				sql.append("and datediff (s , trans.endDate , :endDate) >= 0 ");
			}
			if (instance.getEndFlag() != null) {//结束标志
				sql.append(" and  trans.endFlag = :endFlag  ");
			}
		}
		sql.append(" order by trans.updDate desc ");

		return sql.toString();

	}

	private void setQuery(Query query, TechAccident instance) {
		if (instance != null) {
			//查询条件：事故性质，事故状态，事故发生时间
			if (instance.getAccdKind() != null) {//事故性质
				query.setString("accdKind", instance.getAccdKind());
			}
			if (instance.getStartTime() != null) {//开始时间				
				query.setTimestamp("startTime", instance.getStartTime());
			}
			if (instance.getEndDate() != null) {//结束时间				
				query.setTimestamp("endDate", instance.getEndDate());
			}
			if (instance.getEndFlag() != null) {//结束标志				
				query.setString("endFlag", instance.getEndFlag());
			}
		}
	}
}