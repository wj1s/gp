package gov.abrs.etms.service.stay;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.stay.StayDetail;
import gov.abrs.etms.model.stay.StayDetailPerson;
import gov.abrs.etms.model.stay.StayPeriod;
import gov.abrs.etms.model.stay.StayRule;
import gov.abrs.etms.model.stay.StaySection;
import gov.abrs.etms.model.stay.StaySectionPerson;
import gov.abrs.etms.service.util.CrudService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class StayDetailService extends CrudService<StayDetail> {

	public List<StayDetail> get(Date startTime, Date endTime, Dept dept) {
		StringBuffer hql = new StringBuffer("from StayDetail as detail where 1=1 ");
		hql.append("and detail.dept = :dept ");
		if (startTime != null) {
			hql.append(" and detail.endTime >= :startTime");
		}
		if (endTime != null) {
			hql.append(" and detail.startTime <= :endTime ");
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		if (startTime != null) {
			values.put("startTime", startTime);
		}
		if (endTime != null) {
			values.put("endTime", endTime);
		}
		return dao.find(hql.toString(), values);
	}

	//根据时间判断是否允许排班，或是否影响到排班的历史数据
	public String isExist(Date startTime, Date endTime, Date currentTime, Dept dept) {
		JSONObject object = new JSONObject();
		List<StayDetail> list;
		if (DateUtil.dateToString(startTime, DateUtil.FORMAT_DAY).equals(
				DateUtil.dateToString(currentTime, DateUtil.FORMAT_DAY))) {
			//首先判断该部门是否已经有排班
			list = get(startTime, DateUtil.addDay(startTime, 1), dept);
			if (list != null && list.size() > 0) {
				object.put("success", false);
				object.put("message", "今天的排班已经上班,请从明天开始排班");
			} else {
				list = get(startTime, endTime, dept);
				object.put("success", true);
				if (list != null && list.size() > 0) {
					object.put("isExist", true);
				} else {
					object.put("isExist", false);
				}
			}
		} else if (startTime.before(currentTime)) {
			object.put("success", false);
			object.put("message", "开始排班日期不能小于今天");
		} else {
			list = get(startTime, endTime, dept);
			object.put("success", true);
			if (list != null && list.size() > 0) {
				object.put("isExist", true);
			} else {
				object.put("isExist", false);
			}
		}
		return object.toString();
	}

	public void createRuleAndDetail(StayRule stayRule, String[] startTimeArray, String[] endTimeArray,
			String[] empNamesArray, int[] periodIndexArray, int periodCount, Dept dept, Date startTime, Date endTime,
			int startIndex) {
		//组装留守规则
		List<StayPeriod> stayPeriodList = new ArrayList<StayPeriod>();
		for (int i = 0; i < periodCount; i++) {
			StayPeriod stayPeriod = new StayPeriod();
			List<StaySection> staySections = new ArrayList<StaySection>();
			for (int j = 0; j < periodIndexArray[i]; j++) {
				//刨去之前的几个周期
				int tmp = 0;
				for (int k = 0; k < i; k++) {
					tmp += periodIndexArray[k];
				}
				StaySection staySection = new StaySection();
				staySection.setStartTime(DateUtil.stringToDate("1900-01-01 " + startTimeArray[j + tmp].trim() + ":00",
						"yyyy-MM-dd HH:mm:ss"));
				staySection.setEndTime(DateUtil.stringToDate("1900-01-01 " + endTimeArray[j + tmp].trim() + ":00",
						"yyyy-MM-dd HH:mm:ss"));
				String empNameStr = empNamesArray[j + tmp];

				String[] eachEmpNameStr = empNameStr.split(",");
				List<StaySectionPerson> sspList = new ArrayList<StaySectionPerson>();
				for (int k = 0; k < eachEmpNameStr.length; k++) {
					if (!eachEmpNameStr[k].equals("") && !eachEmpNameStr[k].equals(" ")) {
						StaySectionPerson ssp = new StaySectionPerson(staySection, eachEmpNameStr[k]);
						sspList.add(ssp);
					}
				}
				staySection.setStaySectionPeople(sspList);
				staySection.setStayPeriod(stayPeriod);
				staySections.add(staySection);

			}
			stayPeriod.setPeriodIndex((i + 1));
			stayPeriod.setStayRule(stayRule);
			stayPeriod.setStaySections(staySections);
			stayPeriodList.add(stayPeriod);
		}
		stayRule.setDept(dept);
		stayRule.setPeriodCount(periodCount);
		//判断是否新增还是更新
		if (stayRule.getId() == null) {
			stayRule.setStayPeriods(stayPeriodList);
		} else {
			stayRule.getStayPeriods().clear();
			stayRule.getStayPeriods().addAll(stayPeriodList);
		}
		stayRuleService.save(stayRule);

		//组装留守信息部分

		//生成留守规则 和留守信息
		List<StayDetail> stayDetailList = new ArrayList<StayDetail>(0);

		int days = Integer.valueOf((endTime.getTime() - startTime.getTime()) / (1000 * 60 * 60 * 24) + "");
		for (int i = 0; i < days + 1; i++) {
			if (i != 0)
				if (startIndex < periodCount - 1) {
					startIndex++;
				} else {
					startIndex = 0;
				}
			StayPeriod period = stayPeriodList.get(startIndex);
			List<StaySection> sections = period.getStaySections();
			for (StaySection staySection : sections) {
				Date startTemp = DateUtil.addDay(startTime, i);
				StayDetail stayDetail = new StayDetail();
				stayDetail.setDept(dept);
				Date start = DateUtil.stringToDate(DateUtil.dateToString(startTemp, "yyyy-MM-dd") + " "
						+ DateUtil.dateToString(staySection.getStartTime(), "HH:mm:00"), DateUtil.FORMAT_DAYTIME);
				Date end = DateUtil.stringToDate(DateUtil.dateToString(startTemp, "yyyy-MM-dd") + " "
						+ DateUtil.dateToString(staySection.getEndTime(), "HH:mm:00"), DateUtil.FORMAT_DAYTIME);
				stayDetail.setStartTime(start);
				if ((start.after(end)) || (start.equals(end))) {
					end = DateUtil.addDay(end, 1);
				}
				stayDetail.setEndTime(end);

				List<StayDetailPerson> stayDetailPeople = new ArrayList<StayDetailPerson>();
				for (StaySectionPerson ssp : staySection.getStaySectionPeople()) {
					StayDetailPerson sdp = new StayDetailPerson(stayDetail, ssp.getId().getEmpName());
					stayDetailPeople.add(sdp);
				}
				stayDetail.setStayDetailPeople(stayDetailPeople);
				stayDetailList.add(stayDetail);
			}
		}

		//保存留守明晰信息前要先删除已经存在的信息
		List<StayDetail> list = get(startTime, DateUtil.addDay(endTime, 1), dept);
		for (StayDetail stayDetail : list) {
			if (DateUtil.dateToString(stayDetail.getEndTime(), "yyyy-MM-dd").equals(
					DateUtil.dateToString(startTime, "yyyy-MM-dd"))
					&& DateUtil.dateToString(stayDetail.getStartTime(), "yyyy-MM-dd").equals(
							DateUtil.dateToString(DateUtil.addDay(startTime, -1), "yyyy-MM-dd"))) {
				//如果要删除的留守记录是在开始日期是跨天的，比如开始日期是：2011-01-15, 留守记录时间段是：2011-01-14 20:00:00 ~ 2011-01-15 8:00:00
				//要把留守记录的结束日期设为2011-01-15 00:00:00
				stayDetail.setEndTime(startTime);
				this.save(stayDetail);
			} else if (DateUtil.dateToString(stayDetail.getEndTime(), "yyyy-MM-dd").equals(
					DateUtil.dateToString(DateUtil.addDay(endTime, 1), "yyyy-MM-dd"))
					&& DateUtil.dateToString(stayDetail.getStartTime(), "yyyy-MM-dd").equals(
							DateUtil.dateToString(endTime, "yyyy-MM-dd"))) {
				//同理结束日期也要判断，比如结束日期是：2011-01-15, 留守记录时间段是：2011-01-14 20:00:00 ~ 2011-01-15 8:00:00
				//要把留守记录的开始日期设为2011-01-15 00:00:00
				stayDetail.setStartTime(DateUtil.addDay(endTime, 1));
				this.save(stayDetail);
			} else {
				this.delete(stayDetail.getId());
			}
		}
		for (StayDetail stayDetail : stayDetailList) {
			this.save(stayDetail);
		}
	}

	public void get(Carrier<StayDetail> carrier, Date startTime, Date endTime, Dept dept, String empName) {
		String hql = "from StayDetail s where 1=1 ";
		if (dept != null) {
			hql += " and s.dept = :dept ";
		}
		//属性是集合的where条件筛选我终于搞定了！
		if (empName != null && !"".equals(empName)) {
			hql += " and ( exists (from s.stayDetailPeople sdp where sdp.id.empName like :empName ))";
		}
		if (startTime != null) {
			hql += " and startTime >= :startTime ";
		}
		if (endTime != null) {
			hql += " and endTime <= :endTime ";
		}
		hql += " order by startTime asc";
		Map<String, Object> values = new HashMap<String, Object>();
		if (dept != null) {
			values.put("dept", dept);
		}
		if (empName != null && !"".equals(empName)) {
			values.put("empName", "%" + empName + "%");
		}
		if (startTime != null) {
			values.put("startTime", startTime);
		}
		if (endTime != null) {
			values.put("endTime", DateUtil.addDay(endTime, 1));
		}
		this.dao.find(carrier, hql, values);
	}

	public List<StayDetail> getDetails(Date curDate) {
		String hql = "from StayDetail detail where detail.startTime <=:curDate and detail.endTime >=:curDate order by detail.dept.code ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("curDate", curDate);
		return dao.find(hql, values);
	}

	public void clearPeople(StayDetail stayDetail) {
		stayDetail.getStayDetailPeople().clear();
	}

	@Autowired
	private StayRuleService stayRuleService;

}
