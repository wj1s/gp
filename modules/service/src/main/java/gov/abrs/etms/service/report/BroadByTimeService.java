package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.service.util.CrudService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class BroadByTimeService extends CrudService<BroadByTime> {

	//根据开始时间和结束时间找
	public List<BroadByTime> get(Date startDate, Date endDate) {
		String hql = "from BroadByTime where startTime <=:endDate and (endTime >=:startDate or endTime is null)";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("startDate", startDate);
		values.put("endDate", DateUtil.addDay(endDate, 1));
		return this.dao.find(hql, values);
	}

	public List<BroadByTime> get(Date curDate, List<TransType> transTypes) {
		String hql = "from BroadByTime where startTime<=:curDate and endTime is null and operation.transType in (:transTypes)";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("curDate", curDate);
		values.put("transTypes", transTypes);
		return this.dao.find(hql, values);
	}

	public void get(Carrier<BroadByTime> carrier, String operationName, Date startDate, Date endDate,
			ParaDtl broadByReason, ParaDtl broadByStation, String broadByFlag) {
		String hql = "from BroadByTime where 1=1 ";
		if (broadByFlag != null) {
			hql += " and broadByFlag=:broadByFlag ";
		}
		if (operationName != null) {
			hql += " and operation.name=:operationName ";
		}
		if (startDate != null) {
			hql += " and startTime >= :startDate ";
		}
		if (endDate != null) {
			hql += " and endTime <= :endDate ";
		}
		if (broadByReason != null) {
			hql += " and broadByReason =:broadByReason ";
		}
		if (broadByStation != null) {
			hql += " and broadByStation =:broadByStation ";
		}
		hql += "and delFlag =0 order by startTime asc";
		Map<String, Object> values = new HashMap<String, Object>();
		if (operationName != null) {
			values.put("operationName", operationName);
		}
		if (startDate != null) {
			values.put("startDate", startDate);
		}
		if (endDate != null) {
			values.put("endDate", DateUtil.addDay(endDate, 1));
		}
		if (broadByReason != null) {
			values.put("broadByReason", broadByReason);
		}
		if (broadByStation != null) {
			values.put("broadByStation", broadByStation);
		}
		if (broadByFlag != null) {
			values.put("broadByFlag", broadByFlag);
		}
		this.dao.find(carrier, hql, values);
	}

}
