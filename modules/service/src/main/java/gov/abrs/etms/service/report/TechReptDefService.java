package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.rept.TechReptDef;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TechReptDefService extends CrudService<TechReptDef> {
	private ExecuteDAO executeDAO;

	@Autowired
	public void setExecuteDAO(ExecuteDAO executeDAO) {
		this.executeDAO = executeDAO;
	}

	public TechReptDef findBySeq(String seq) {
		String hql = "from TechReptDef c where c.reptSeq = :reptSeq ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("reptSeq", seq);
		List<TechReptDef> list = this.dao.find(hql, values);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}

	//分页查询
	@SuppressWarnings("unchecked")
	public void findByInstance(TechReptDef instance, final Carrier carrier) {
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

	private String getSQL(TechReptDef instance) {
		StringBuffer sql = new StringBuffer("from TechReptDef trans where 1=1 ");
		if (instance != null) {
			//查询条件：事故性质，事故状态，事故发生时间
			if (instance.getReptTime() != null) {//201104
				sql.append(" and trans.reptTime = :reptTime ");
			}
			if (instance.getStartDate() != null) {//
				sql.append(" and datediff (s , trans.startDate , :startDate) <= 0 ");
			}
			if (instance.getEndDate() != null) {//-列表用
				sql.append("and datediff (s , trans.endDate , :endDate) >= 0 ");
			}
			if (instance.getPromtTime() != null) {//列表可以显示允许看到的时间
				sql.append(" and datediff (s , trans.promtTime , :promtTime) >= 0 ");
			}
			if (instance.getReptFlag() != null) {//是否被编辑过
				if (instance.getReptFlag() == true) {
					sql.append(" and trans.reptFlag is not null ");
				}
			}
		}
		sql.append(" order by trans.endDate desc ");

		return sql.toString();

	}

	private void setQuery(Query query, TechReptDef instance) {
		if (instance != null) {
			//查询条件：事故性质，事故状态，事故发生时间
			if (instance.getReptTime() != null) {//201104
				query.setString("reptTime", instance.getReptTime());
			}
			if (instance.getStartDate() != null) {//开始时间				
				query.setTimestamp("startDate", instance.getStartDate());
			}
			if (instance.getEndDate() != null) {//结束时间				
				query.setTimestamp("endDate", instance.getEndDate());
			}
			if (instance.getPromtTime() != null) {//列表可以显示允许看到的时间			
				query.setTimestamp("promtTime", instance.getPromtTime());
			}
		}
	}
}
