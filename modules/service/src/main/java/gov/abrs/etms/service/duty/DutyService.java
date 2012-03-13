package gov.abrs.etms.service.duty;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.duty.DutyCheck;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.duty.Moniter;
import gov.abrs.etms.model.duty.Watcher;
import gov.abrs.etms.service.baseinfo.GroupService;
import gov.abrs.etms.service.util.CrudService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DutyService extends CrudService<Duty> {

	private GroupService groupService;

	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	//删除方法，删除时从链表中剔除
	@Override
	public void delete(Long id) {
		Assert.notNull(id);
		Duty duty = this.dao.get(id);
		if (duty.getNextDuty() != null) {
			duty.getNextDuty().setPreDuty(null);
			duty.setNextDuty(null);
		}
		this.dao.delete(id);
	}

	//某部门某天是否有班
	public Boolean hasDutyThisDay(Dept dept, Date date) {
		Assert.notNull(dept);
		Assert.notNull(date);
		List<Duty> list = this.getByThisDay(dept, date);
		if (list != null && list.size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	//某部门某天是否有排班，并且所有的班都交接了
	public Boolean hasDutyShiftedThisDay(Dept dept, Date date) {
		Assert.notNull(dept);
		Assert.notNull(date);
		if (!this.hasDutyThisDay(dept, date)) {
			return false;
		}
		String hql = "from Duty where group.dept = :dept and startTime >= :thisDate and startTime <= :nextDay and updDate is null ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("thisDate", date);
		values.put("nextDay", DateUtil.addDay(date, 1));
		List<Duty> list = this.dao.find(hql, values);
		if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	//根据条件判断该时间段内的运转数是否固定
	//参数groupIds，开始日期，结束日期
	public Date getEndDay(List groupIds, Date startDay, Date endDay) {
		List<Duty> startList = this.getByStartDay(groupIds, startDay);//开始日期当天的所有排班
		List<Duty> list = this.getByBeginEndDay(groupIds, startDay, endDay);//查找所有的duty
		List<String> timeList = new ArrayList();
		if (!startList.isEmpty()) {
			for (Duty duty : startList) {
				Date startTime = duty.getStartTime();
				String startTimeStr = DateUtil.dateToString(startTime, DateUtil.FORMAT_TIME);
				timeList.add(startTimeStr);//讲时分秒 取出来
			}
		}
		if (!list.isEmpty()) {
			for (Duty duty : list) {
				Date startTime = duty.getStartTime();
				String startTimeStr = DateUtil.dateToString(startTime, DateUtil.FORMAT_TIME);
				if (!timeList.contains(startTimeStr)) {
					endDay = DateUtil.getPreviousDate(startTime);
					break;
				}
			}
		}
		return endDay;
	}

	//找到段时间所有班对应班组所有的班
	private List<Duty> getByBeginEndDay(List groupIds, Date startDay, Date endDay) {
		Assert.notNull(groupIds);
		Assert.notNull(startDay);
		Assert.notNull(endDay);
		String hql = "from Duty where group.id in(:groupIds) and startTime >= :thisDate and startTime <= :nextDay order by startTime asc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("groupIds", groupIds);
		values.put("thisDate", startDay);
		values.put("nextDay", endDay);
		List<Duty> list = this.dao.find(hql, values);
		return list;
	}

	//找到某天的所有班对应班组所有的班
	public List<Duty> getByNearDay(List groupIds, Date date) {
		Assert.notNull(groupIds);
		Assert.notNull(date);
		String hql = "from Duty where group.id in(:groupIds) and startTime >= :thisDate order by startTime asc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("groupIds", groupIds);
		values.put("thisDate", date);
		List<Duty> list = this.dao.find(hql, values);
		return list;
	}

	//找到某天的所有班对应班组所有的班
	public List<Duty> getByStartDay(List groupIds, Date date) {
		Assert.notNull(groupIds);
		Assert.notNull(date);
		String hql = "from Duty where group.id in(:groupIds) and startTime >= :thisDate and startTime <= :nextDay";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("groupIds", groupIds);
		values.put("thisDate", date);
		values.put("nextDay", DateUtil.addDay(date, 1));
		List<Duty> list = this.dao.find(hql, values);
		return list;
	}

	//找到某天的所有班
	public List<Duty> getByThisDay(Dept dept, Date date) {
		Assert.notNull(dept);
		Assert.notNull(date);
		String hql = "from Duty where group.dept = :dept and startTime >= :thisDate and startTime <= :nextDay";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("thisDate", date);
		values.put("nextDay", DateUtil.addDay(date, 1));
		List<Duty> list = this.dao.find(hql, values);
		return list;
	}

	//查找某天某部门的最后一个班，如果没有则返回NULL
	public Duty getOneDayLastDuty(Dept dept, Date date) {
		Assert.notNull(dept);
		Assert.notNull(date);
		List<Duty> list = this.getByThisDay(dept, date);
		for (Duty duty : list) {
			//如果这个班的下一个班是第二天的班，则这个班是当天的最后一个班
			if (duty.getNextDuty() == null
					|| DateUtil.dateToDateByFormat(duty.getNextDuty().getStartTime(), "yyyy-MM-dd").after(date)) {
				return duty;
			}
		}
		return null;
	}

	//查找某一天的第一个班
	public Duty getOneDayFirstDuty(Dept dept, Date date) {
		Assert.notNull(dept);
		Assert.notNull(date);
		List<Duty> list = this.getByThisDay(dept, date);
		for (Duty duty : list) {
			//如果这个班的前一个班是上一天的班，则这个班是当天的第一个班
			if (duty.getPreDuty() == null || duty.getPreDuty().getStartTime().before(date)) {
				return duty;
			}
		}
		return null;
	}

	//删除一段时间的排班(级联)
	public void delete(Dept dept, Date start, Date end) {
		Assert.notNull(start);
		Assert.notNull(end);

		Duty firstDuty = null;
		Duty lastDuty = null;
		Duty prevFirstDuty = null;
		Duty nextLastDuty = null;

		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("start", start);
		values.put("end", DateUtil.addDay(end, 1));

		String hql1 = "from Duty where group.dept =:dept and startTime>=:start and startTime<:end and first =1 order by startTime asc";
		List<Duty> list1 = this.dao.find(hql1, values);

		String hql2 = "from Duty where group.dept =:dept and startTime>=:start and startTime<:end and last = 1 order by startTime desc";
		List<Duty> list2 = this.dao.find(hql2, values);

		if (list1 != null && list1.size() != 0 && list2 != null && list2.size() != 0) {
			firstDuty = list1.get(0);
			prevFirstDuty = firstDuty.getPreDuty();
			lastDuty = list2.get(0);
			nextLastDuty = lastDuty.getNextDuty();
			if (nextLastDuty != null) {
				nextLastDuty.setPreDuty(prevFirstDuty);
			}
			firstDuty.setPreDuty(null);
			this.delete(lastDuty.getId());
		} else {
			//这段时间内没有任何排班，不用删除
		}
	}

	//查询排班信息的分页方法
	public void get(Carrier<Duty> carrier, Dept dept, Group group, Date startDate, Date endDate) {
		String hql = "from Duty where 1=1 ";
		if (dept != null) {
			hql += " and group.dept = :dept ";
		}
		if (group != null) {
			hql += " and group = :group ";
		}
		if (startDate != null) {
			hql += " and startTime >= :startDate ";
		}
		if (endDate != null) {
			hql += " and endTime <= :endDate ";
		}
		hql += " order by startTime asc";
		Map<String, Object> values = new HashMap<String, Object>();
		if (dept != null) {
			values.put("dept", dept);
		}
		if (group != null) {
			values.put("group", group);
		}
		if (startDate != null) {
			values.put("startDate", startDate);
		}
		if (endDate != null) {
			values.put("endDate", DateUtil.addDay(endDate, 1));
		}
		//如果按日期排序则自动转化成按开始时间排序
		if (carrier.getSidx().equals("date")) {
			carrier.setSidx("startTime");
		}
		if (carrier.getSidx().equals("staffOnDuty")) {
			carrier.setSidx("moniter");
		}
		this.dao.find(carrier, hql, values);
	}

	//获取某人某天已经交接过的班的集合(包括昨天的最后一个班)
	public List<Duty> getAlreadyShiftedDuties(Dept dept, Date date, Person person) {
		String hql = "from Duty where group.dept = :dept and startTime >= :thisDate and startTime <= :nextDay and updDate is not null ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("thisDate", date);
		values.put("nextDay", DateUtil.addDay(date, 1));
		List<Duty> list = this.dao.find(hql, values);
		// 添加前一天最后一个运转的值班记录；
		Date lastDate = DateUtil.addDay(date, -1);
		Duty lastDuty = this.getOneDayLastDuty(dept, lastDate);
		if (lastDuty != null && lastDuty.getShift()) {
			list.add(lastDuty);
		}
		return this.hasThisPersonsDuties(list, person);
	}

	//所有我所在的班都已交接
	public Boolean isAllDayDutiesShifted(Dept dept, Date date, Person person) {
		List<Duty> list = getCanShiftDuties(dept, date, person);
		if (list.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	//查找一天内所有未交接的班
	public List<Duty> getCanShiftDuties(Dept dept, Date date, Person person) {
		String hql = "from Duty where group.dept=:dept and startTime >= :thisDate"
				+ " and startTime <= :nextDay and updDate is null ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("thisDate", date);
		values.put("nextDay", DateUtil.addDay(date, 1));
		List<Duty> list = this.dao.find(hql, values);
		return this.hasThisPersonsDuties(list, person);
	}

	//循环去班集合的上班人员中有没有这个人
	private List<Duty> hasThisPersonsDuties(List<Duty> list, Person person) {
		List<Duty> returnList = new ArrayList<Duty>();
		if (list != null) {
			for (Duty duty : list) {
				Moniter moniter = duty.getMoniter();
				if (moniter.getName().equals(person.getName())) {
					returnList.add(duty);
					continue;
				} else {
					List<Watcher> watchers = duty.getWatchers();
					for (Watcher watcher : watchers) {
						if (watcher.getName().equals(person.getName())) {
							returnList.add(duty);
							break;
						}
					}
				}
			}
		}
		return returnList;
	}

	//查找某部门某天之前的最后一个班
	public Duty getPrevLastDuty(Dept dept, Date start) {
		String hql = "from Duty where startTime <=:start and group.dept=:dept and last = 1 order by startTime desc ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("start", start);
		List<Duty> list = this.dao.find(hql, values);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	//查找某部门某天之后的第一个班
	public Duty getNextFirstDuty(Dept dept, Date end) {
		String hql = "from Duty where startTime >=:end and group.dept=:dept and first = 1 order by startTime asc ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("end", end);
		List<Duty> list = this.dao.find(hql, values);
		if (list == null || list.size() == 0) {
			return null;
		} else {
			return list.get(0);
		}
	}

	public void arrangeClass(Dept dept, Date start, Date end, List<DutySchedule> dutySchedules, int[] groupIds,
			int cycle, int firstDay) {
		//先删除
		this.delete(dept, start, end);

		int days = DateUtil.getIntervalDays(start, end) + 1;
		Duty lastDuty = this.getPrevLastDuty(dept, start);

		//级联保存的递归方法
		assembllyDuty(lastDuty, dutySchedules, groupIds, dept, days, start, end, cycle, firstDay, 1, 0, 0);
	}

	private void assembllyDuty(Duty lastDuty, List<DutySchedule> dutySchedules, int[] groupIds, Dept dept, int days,
			Date start, Date end, int cycle, int firstDay, int scheIndex, int dayIndex, int daySum) {
		int scheLength = dutySchedules.size();
		//这个计算公式是得出每个班对应的班组在数组中的位置
		int groupIndex = scheIndex * cycle - (cycle - (firstDay + dayIndex) + 1);
		DutySchedule dutySche = dutySchedules.get(scheIndex - 1);
		Duty duty = new Duty();
		duty.setPreDuty(lastDuty);
		Date startTime = DateUtil.spliceDate(DateUtil.addDay(start, daySum), dutySche.getStartTime());
		Date endTime;
		if (dutySche.getStartTime().before(dutySche.getEndTime())) {
			endTime = DateUtil.spliceDate(DateUtil.addDay(start, daySum), dutySche.getEndTime());
		} else {
			endTime = DateUtil.spliceDate(DateUtil.addDay(start, daySum + 1), dutySche.getEndTime());
		}
		duty.setStartTime(startTime);
		duty.setEndTime(endTime);
		duty.setWeek(DateUtil.getWeek(startTime));
		Group group = this.groupService.get((long) groupIds[groupIndex]);
		duty.setGroup(group);
		List<Watcher> watchers = new ArrayList<Watcher>();
		int pass = 0;
		//先看这个班有没有职务是班长的
		boolean flag = false;
		for (Person person : group.getPeople()) {
			if (person.getPost() != null && person.getPost().getName().equals("班长")) {
				flag = true;
				break;
			}
		}
		if (flag) {
			for (Person person : group.getPeople()) {
				if (person.getPost().getName().equals("班长")) {
					duty.setMoniter(new Moniter(duty, person.getName(), startTime, endTime));
					pass++;
				} else if (person.getPost().getName().equals("值班员") || person.getPost().getName().equals("副班长")) {
					watchers.add(new Watcher(duty, person.getName(), startTime, endTime));
				}
			}
		} else {
			for (int i = 0; i < group.getPeople().size(); i++) {
				if (i == 0) {
					duty.setMoniter(new Moniter(duty, group.getPeople().get(i).getName(), startTime, endTime));
				} else {
					watchers.add(new Watcher(duty, group.getPeople().get(i).getName(), startTime, endTime));
				}
			}
		}
		//如果本班组没有职务是班长的人，则默认把第一个人当做班长
		if (pass == 0) {
			for (Person person : group.getPeople()) {
				duty.setMoniter(new Moniter(duty, person.getName(), startTime, endTime));
				break;
			}
		}
		duty.setWatchers(watchers);
		duty.setDayPartCount(dutySche.getDutyRule().getDayPartCount());
		duty.setSchName(dutySche.getSchName());
		if (scheLength == 1) {
			daySum++;
			scheIndex = 1;
			dayIndex++;
			duty.setLast(true);
			duty.setFirst(true);
		} else {
			if (scheIndex == scheLength) {
				duty.setLast(true);
				scheIndex = 1;
				dayIndex++;
				daySum++;
			} else {
				if (scheIndex == 1) {
					duty.setFirst(true);
				}
				scheIndex++;
			}
		}
		if (daySum < days) {
			if (groupIndex == groupIds.length - 1) {
				dayIndex = 0;
				firstDay = 1;
			}
			this.assembllyDuty(duty, dutySchedules, groupIds, dept, days, start, end, cycle, firstDay, scheIndex,
					dayIndex, daySum);
		} else {
			Duty nextDuty = this.getNextFirstDuty(dept, DateUtil.getNextDate(end));
			if (nextDuty != null) {
				nextDuty.setPreDuty(duty);
			}
		}
		this.save(duty);
	}

	public List<Duty> getDuties(Dept dept, Date startDate, Date endDate) {
		String hql = "from Duty where startTime >=:startDate and startTime <:endDate and group.dept =:dept";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("startDate", startDate);
		values.put("endDate", DateUtil.getNextDate(endDate));
		return this.dao.find(hql, values);
	}

	public void get(Carrier<Duty> carrier, Dept dept, Group group, String empName, Boolean hasCheckes, Date startDate,
			Date endDate) {
		String hql = "from Duty d where 1=1 ";
		if (dept != null) {
			hql += " and group.dept = :dept ";
		}
		if (group != null) {
			hql += " and group = :group ";
		}
		//属性是集合的where条件筛选我终于搞定了！
		if (empName != null && !"".equals(empName)) {
			hql += " and (moniter.name like :empName or ( exists (from d.watchers w where w.name like :empName )))";
		}
		if (hasCheckes != null) {
			if (hasCheckes) {
				hql += " and exists (from d.dutyChecks c)";
			} else {
				hql += " and not exists (from d.dutyChecks c)";
			}
		}
		if (startDate != null) {
			hql += " and startTime >= :startDate ";
		}
		if (endDate != null) {
			hql += " and endTime <= :endDate ";
		}
		hql += " and updDate is not null order by startTime desc";
		Map<String, Object> values = new HashMap<String, Object>();
		if (dept != null) {
			values.put("dept", dept);
		}
		if (group != null) {
			values.put("group", group);
		}
		if (empName != null && !"".equals(empName)) {
			values.put("empName", "%" + empName + "%");
		}
		if (startDate != null) {
			values.put("startDate", startDate);
		}
		if (endDate != null) {
			values.put("endDate", DateUtil.addDay(endDate, 1));
		}
		//如果按日期排序则自动转化成按开始时间排序
		if (carrier.getSidx().equals("date")) {
			carrier.setSidx("startTime");
		}
		if (carrier.getSidx().equals("staffOnDuty")) {
			carrier.setSidx("moniter");
		}

		this.dao.find(carrier, hql, values);
	}

	//向duty中添加一条值班记录审核信息
	public void addADutyCheck(Duty duty, DutyCheck dutyCheck) {
		duty.getDutyChecks().add(dutyCheck);
		this.dao.flush();
	}

	//为了生成排班表查询某部门，某个运转数的，一段时间内的班
	public List<Duty> get(Dept dept, Date start, Date end, int dayPartCount) {
		String hql = "from Duty where startTime >=:startDate and startTime <=:endDate and group.dept =:dept and dayPartCount =:dayPartCount";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("startDate", start);
		values.put("endDate", DateUtil.getNextDate(end));
		values.put("dayPartCount", dayPartCount);
		return this.dao.find(hql, values);
	}

	//保存快速调班结果
	public void savechangeDetails(JSONArray array) {
		for (int i = 0; i < array.size(); i++) {
			JSONObject json = array.getJSONObject(i);
			Long dutyId = json.getLong("id");
			Duty duty = this.get(dutyId);
			duty.getMoniter().setDutyM(null);
			duty.setMoniter(null);
			duty.getWatchers().clear();
			Moniter moniter = new Moniter(duty, json.getString("moniter"), duty.getStartTime(), duty.getEndTime());
			duty.setMoniter(moniter);
			List<Watcher> list = Lists.newArrayList();
			for (int j = 0; j < json.getJSONArray("watchers").size(); j++) {
				JSONObject jsonInner = json.getJSONArray("watchers").getJSONObject(j);
				Watcher watcher = new Watcher(duty, jsonInner.getString("watcher"), duty.getStartTime(),
						duty.getEndTime());
				list.add(watcher);
			}
			duty.getWatchers().addAll(list);
			this.save(duty);
		}
	}

	//按特定条件查找符合条件的班组数
	public int getEffectedGroupNum(String empName, DutySchedule schedule, Date start, Date end) {
		String hql = "from Duty as d where startTime >= :start and endTime <= :end"
				+ " and (moniter.name=:empName or ( exists (from d.watchers w where w.name=:empName )))";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("start", start);
		values.put("end", DateUtil.addDay(end, 1));
		values.put("empName", empName);
		int num = 0;
		List<Duty> list = this.dao.find(hql, values);
		for (Duty duty : list) {
			if (DateUtil.dateToString(duty.getStartTime(), "1970-01-01 HH:mm:00").equals(
					DateUtil.getDateTimeStr(schedule.getStartTime()))
					&& DateUtil.dateToString(duty.getEndTime(), "1970-01-01 HH:mm:00").equals(
							DateUtil.getDateTimeStr(schedule.getEndTime()))) {
				num++;
			}
		}
		return num;
	}

	//替换班预览功能
	public List<Duty> getCompareList(List<Duty> oldList, Map<String, String[]> params)
			throws UnsupportedEncodingException {
		//替换班后的结果
		List<Duty> newList = cloneDuties(oldList);

		//替换班条件
		String[] startDateArray = params.get("startDateArray");
		String[] endDateArray = params.get("endDateArray");
		String[] dutyScheduleNameArray = params.get("dutyScheduleNameArray");
		String[] empNameArray = params.get("empNameArray");
		String[] flagsArray = params.get("flagsArray");
		String[] empRpNameArray = params.get("empRpNameArray");
		String[] dutyScheduleRpNameArray = params.get("dutyScheduleRpNameArray");

		for (int i = 0; i < startDateArray.length; i++) {
			Date startDate = DateUtil.createDate(startDateArray[i]);
			Date endDate = DateUtil.createDate(endDateArray[i]);

			if (flagsArray[i].equals("0")) {
				//替班
				replaceDuties(startDate, endDate, encodeContent(dutyScheduleNameArray[i]),
						encodeContent(empNameArray[i]), encodeContent(empRpNameArray[i]), oldList, newList, "white",
						"#9901d8");
			} else if (flagsArray[i].equals("1")) {
				//换班
				replaceDuties(startDate, endDate, encodeContent(dutyScheduleNameArray[i]),
						encodeContent(empNameArray[i]), encodeContent(empRpNameArray[i]), oldList, newList, "white",
						"blue");
				replaceDuties(startDate, endDate, encodeContent(dutyScheduleRpNameArray[i]),
						encodeContent(empRpNameArray[i]), encodeContent(empNameArray[i]), oldList, newList, "white",
						"blue");
			}
		}

		return newList;
	}

	//克隆排班表
	private List<Duty> cloneDuties(List<Duty> oldList) {
		List<Duty> newList = Lists.newArrayList();
		for (Duty duty : oldList) {
			Duty dutyClone = new Duty();
			dutyClone.setSchName(duty.getSchName());
			dutyClone.setStartTime(duty.getStartTime());
			dutyClone.setDayPartCount(duty.getDayPartCount());
			dutyClone.setStartTime(duty.getStartTime());
			Moniter moniterClone = duty.getMoniter();
			dutyClone.setMoniter(moniterClone);
			List<Watcher> watchersClone = Lists.newArrayList();
			for (Watcher watcher : duty.getWatchers()) {
				Watcher watcherClone = new Watcher();
				watcherClone.setName(watcher.getName());
				watchersClone.add(watcherClone);
			}
			dutyClone.setWatchers(watchersClone);
			newList.add(dutyClone);
		}
		return newList;
	}

	//替班操作
	private void replaceDuties(Date startDate, Date endDate, String schNameAndCount, String empName, String empRpName,
			List<Duty> oldList, List<Duty> newList, String fontColor, String bgColor) {
		Date date = startDate;
		Integer count = 0;
		String schName = schNameAndCount.split("_")[0];
		int dayPartCount = Integer.parseInt(schNameAndCount.split("_")[1]);
		while (!endDate.before(date)) {
			for (int i = 0; i < oldList.size(); i++) {
				Duty duty = oldList.get(i);
				if (DateUtil.getDateStr(date).equals(DateUtil.getDateStr(duty.getStartTime()))
						&& schName.equals(duty.getSchName()) && dayPartCount == duty.getDayPartCount()) {
					//在newList中放入一个替换过的staff
					//标示要替的班中有没有这个人
					boolean mark = false;
					if (duty.getMoniter().getName().equals(empRpName)) {
						mark = true;
						break;
					} else {
						for (Watcher watcher : duty.getWatchers()) {
							if (watcher.getName().equals(empRpName)) {
								mark = true;
								break;
							}
						}
					}
					if (mark) {
						continue;
					}

					//如果没有继续进行替班
					if (duty.getMoniter().getName().equals(empName)) {
						newList.get(i).setMoniter(
								new Moniter("<span style='font-weight:bold;background:" + bgColor + "'><font color='"
										+ fontColor + "'>" + empRpName + "</font></span>"));
					} else {
						for (int j = 0; j < duty.getWatchers().size(); j++) {
							if (duty.getWatchers().get(j).getName().equals(empName)) {
								newList.get(i)
										.getWatchers()
										.get(j)
										.setName(
												"<span style='font-weight:bold;background:" + bgColor
														+ "'><font color='" + fontColor + "'>" + empRpName
														+ "</font></span>");
							}
						}
					}
				}
			}
			date = DateUtil.addDay(startDate, ++count);
		}
	}

	//转码
	private String encodeContent(String s) throws UnsupportedEncodingException {
		if (s != null) {
			s = new String(s.getBytes("iso8859_1"), "UTF-8");
		}
		return s;
	}

	//保存替换班结果
	public void updateReplaceResult(JSONObject obj) {
		Date startDateMin = DateUtil.createDate(obj.getString("startDateMin"));
		Date endDateMax = DateUtil.createDate(obj.getString("endDateMax"));
		Dept dept = new Dept(obj.getLong("dpId"));

		List<Duty> oldList = this.getDuties(dept, startDateMin, endDateMax);
		List<Duty> newList = cloneDuties(oldList);
		for (int i = 0; i < obj.getJSONArray("list").size(); i++) {
			JSONObject json = obj.getJSONArray("list").getJSONObject(i);
			Date startDate = DateUtil.createDate(json.getString("startDate"));
			Date endDate = DateUtil.createDate(json.getString("endDate"));
			int flag = json.getInt("flag");
			String empName = json.getString("isReplacedName");
			String empRpName = json.getString("replaceName");
			String schName = json.getString("schName");
			int dayPartCount = json.getInt("dayPartCount");
			if (flag == 0) {
				//替班
				replaceDuties(startDate, endDate, schName, dayPartCount, empName, empRpName, oldList, newList);
			} else {
				//换班
				String schNameRp = json.getString("schNameRp");
				int dayPartCountRp = json.getInt("dayPartCountRp");
				replaceDuties(startDate, endDate, schName, dayPartCount, empName, empRpName, oldList, newList);
				replaceDuties(startDate, endDate, schNameRp, dayPartCountRp, empRpName, empName, oldList, newList);
			}
		}
	}

	private void replaceDuties(Date startDate, Date endDate, String schName, int dayPartCount, String empName,
			String empRpName, List<Duty> oldList, List<Duty> newList) {
		Date date = startDate;
		Integer count = 0;
		while (!endDate.before(date)) {
			for (int i = 0; i < newList.size(); i++) {
				Duty duty = newList.get(i);
				if (DateUtil.getDateStr(date).equals(DateUtil.getDateStr(duty.getStartTime()))
						&& schName.equals(duty.getSchName()) && dayPartCount == duty.getDayPartCount()) {
					//在newList中放入一个替换过的staff
					//标示要替的班中有没有这个人
					boolean mark = false;
					if (duty.getMoniter().getName().equals(empRpName)) {
						mark = true;
						break;
					} else {
						for (Watcher watcher : duty.getWatchers()) {
							if (watcher.getName().equals(empRpName)) {
								mark = true;
								break;
							}
						}
					}
					if (mark) {
						continue;
					}

					//如果没有继续进行替班
					if (duty.getMoniter().getName().equals(empName)) {
						oldList.get(i).getMoniter().setName(empRpName);
					} else {
						for (int j = 0; j < duty.getWatchers().size(); j++) {
							if (oldList.get(i).getWatchers().get(j).getName().equals(empName)) {
								oldList.get(i).getWatchers().get(j).setName(empRpName);
							}
						}
					}
					this.save(oldList.get(i));
				}
			}
			date = DateUtil.addDay(startDate, ++count);
		}
	}

	public List<Duty> getDuties(Dept dept, Date start, Date end, String empName) {
		String hql = "from Duty as d where startTime >= :start and endTime <= :end and group.dept=:dept"
				+ " and (moniter.name=:empName or ( exists (from d.watchers w where w.name=:empName )))";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("start", start);
		values.put("end", DateUtil.addDay(end, 1));
		values.put("empName", empName);
		values.put("dept", dept);
		return this.dao.find(hql, values);
	}

	//删除某些班中名字为empName的人员
	public void deleteStaffOnDuty(List<Long> duIds, String empName) {
		for (Long id : duIds) {
			Duty duty = get(id);
			if (duty.getMoniter().getName().equals(empName)) {
				Watcher watcher = duty.getWatchers().get(0);
				duty.getMoniter().setName(watcher.getName());
				duty.getWatchers().remove(watcher);
			} else {
				for (int i = 0; i < duty.getWatchers().size(); i++) {
					if (duty.getWatchers().get(i).getName().equals(empName)) {
						duty.getWatchers().remove(i);
					}
				}
			}
			save(duty);
		}
	}

	public List<Duty> getDuties(Dept dept, Date start, Date end, Group group) {
		String hql = "from Duty as d where startTime >= :start and endTime <= :end and group.dept=:dept ";
		if (group != null) {
			hql += "and group =:group";
		}
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("start", start);
		values.put("end", DateUtil.addDay(end, 1));
		values.put("dept", dept);
		values.put("group", group);
		return this.dao.find(hql, values);
	}

	//向班中增加empName的值班员
	public void addStaffOnDuty(List<Long> duIds, String empName) {
		for (Long id : duIds) {
			Duty duty = get(id);
			Moniter moniter = duty.getMoniter();
			Watcher watcher = new Watcher(duty, empName, moniter.getStartTime(), moniter.getEndTime());
			duty.getWatchers().add(watcher);
			save(duty);
		}
	}

	//查找某部门到某天位置最后一个已交接的班
	public Duty get(Dept dept, Date date) {
		String hql = "from Duty where endTime <= :end and group.dept=:dept and updDate is not null order by startTime desc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("end", DateUtil.addDay(date, 1));
		values.put("dept", dept);
		List<Duty> list = this.dao.find(hql, values);
		return list.size() > 0 ? list.get(0) : null;
	}

	//根据结束时间和部门取班的列表
	public List<Duty> getDuties(Dept dept, Date curDate) {
		String hql = "from Duty where startTime <=:endDate and group.dept =:dept and updDate is not null order by startTime desc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("endDate", DateUtil.getNextDate(curDate));
		return this.dao.find(hql, values);
	}

	public Boolean hasDutyShiftedFromThisDay(Dept dept, Date updDate) {
		String hql = "from Duty where startTime >=:updDate and group.dept =:dept and updDate is not null";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		values.put("updDate", updDate);
		List<Duty> list = this.dao.find(hql, values);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
