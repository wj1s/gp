package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.model.rept.ImptPeriod;
import gov.abrs.etms.service.util.CrudService;
import gov.abrs.etms.service.util.UtilService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ImptPeriodService extends CrudService<ImptPeriod> {

	public List<ImptPeriod> get(String reptTime) {
		String hql = "from ImptPeriod impt where impt.submitDate >= :startDate and impt.submitDate<=:endDate";
		Map<String, Object> values = new HashMap<String, Object>();
		Date date = DateUtil.stringToDate(reptTime, DateUtil.FORMAT_YYYYMM);
		Date firstDateInMonth = DateUtil.getFirstDayOfMonth(date);
		Date lastDateInMonth = DateUtil.getLastDayOfMonth(date);
		values.put("startDate", firstDateInMonth);
		values.put("endDate", lastDateInMonth);
		return dao.find(hql, values);
	}

	//去这段时间内的所有ImptPeriod的id
	public List<ImptPeriod> get(Date startDate, Date endDate) {
		String hql = "from ImptPeriod impt where impt.startDate <=:endDate and impt.endDate >=:startDate";
		Map<String, Date> values = new HashMap<String, Date>();
		values.put("startDate", startDate);
		values.put("endDate", endDate);
		return dao.find(hql, values);
	}

	public List<ImptPeriod> get(Date startTime, Date endTime, Long id, String broadByFlag, BroadByStation broadByStation) {
		if (id != null) {
			//影响事故报表分为3段（开始时间、结束时间、其他参数）
			BroadByTime bbt = broadByTimeService.get(id);
			//判断开始时间
			List<ImptPeriod> list1 = Lists.newArrayList();
			if (startTime != bbt.getStartTime()) {
				if (startTime.before(bbt.getStartTime())) {
					list1 = get(startTime, bbt.getStartTime());
				} else {
					list1 = get(bbt.getStartTime(), startTime);
				}
			}
			//判断结束时间
			List<ImptPeriod> list2 = Lists.newArrayList();
			Date curDate = utilService.getSysTime();
			if (bbt.getEndTime() == null && endTime != null) {
				if (endTime.before(curDate)) {
					list2 = get(endTime, curDate);
				} else {
					list2 = get(curDate, endTime);
				}
			} else if (bbt.getEndTime() != null && endTime == null) {
				if (bbt.getEndTime().before(curDate)) {
					list2 = get(bbt.getEndTime(), curDate);
				} else {
					list2 = get(curDate, bbt.getEndTime());
				}
			} else if (bbt.getEndTime() != null && endTime != null) {
				if (endTime.before(bbt.getEndTime())) {
					list2 = get(endTime, bbt.getEndTime());
				} else {
					list2 = get(bbt.getEndTime(), endTime);
				}
			}
			//判断其他参数
			List<ImptPeriod> list3 = Lists.newArrayList();
			if (!broadByFlag.equals(bbt.getBroadByFlag()) || broadByStation.getId() != bbt.getBroadByStation().getId()) {
				if (bbt.getEndTime() == null) {
					list3 = get(bbt.getStartTime(), curDate);
				} else {
					list3 = get(bbt.getStartTime(), bbt.getEndTime());
				}
			}
			//取出重复
			List<ImptPeriod> finalList = Lists.newArrayList();
			finalList.addAll(list1);
			for (ImptPeriod ip : list2) {
				if (!list1.contains(ip)) {
					finalList.add(ip);
				}
			}
			for (ImptPeriod ip : list3) {
				if (!list1.contains(ip)) {
					finalList.add(ip);
				}
			}
			return finalList;
		} else {
			if (endTime == null) {
				endTime = utilService.getSysTime();
			}
			List<ImptPeriod> finalList = get(startTime, endTime);
			return finalList;
		}
	}

	private BroadByTimeService broadByTimeService;

	private UtilService utilService;

	@Autowired
	public void setBroadByTimeService(BroadByTimeService broadByTimeService) {
		this.broadByTimeService = broadByTimeService;
	}

	@Autowired
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

}
