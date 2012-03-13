package gov.abrs.etms.action.duty;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.duty.DutyCheck;
import gov.abrs.etms.model.duty.DutyPrompt;
import gov.abrs.etms.model.duty.DutyRecord;
import gov.abrs.etms.model.duty.DutyRule;
import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.model.duty.RuleItem;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.GroupService;
import gov.abrs.etms.service.duty.DutyCheckService;
import gov.abrs.etms.service.duty.DutyPromptService;
import gov.abrs.etms.service.duty.DutyRuleService;
import gov.abrs.etms.service.duty.DutyScheduleService;
import gov.abrs.etms.service.duty.DutyService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class DutyAction extends GridAction<Duty> {

	private static final long serialVersionUID = -4436749449674179805L;
	private Long ruleId;
	private List<Group> groups;
	private DutyRule dutyRule;
	private Long dpId;
	private String startDate;
	private String endDate;
	private Long[] dutyScheduleIds;
	private int[] groupIds;
	private int firstDay;
	private int cycle;
	private String currentDate;
	private List<Dept> depts;
	private Long groupId;
	private Dept dept;
	private List<Duty> todayDutyList;
	private List<Duty> canWriteRecordDuties;
	private String message;
	private List<Duty> canShiftDuties;
	private Date yesterday;
	private Duty crossAndLastDuty;
	private Date today;
	private List<Duty> alreadyShiftDuties;
	private String weather;
	private String temperature;
	private String offDutyRecord;
	private String empName;
	private Integer hasCheckes;
	private Duty dutyScan;
	private String check;
	private Long checkId;
	private String changeDetails;
	private Map<Long, List<DutySchedule>> dutySchedules;
	private Long empId;
	private Long scheduleId;

	//预览替换班专用
	private String startDateArray;
	private String endDateArray;
	private String dutyScheduleNameArray;
	private String empNameArray;
	private String flagsArray;
	private String empRpNameArray;
	private String dutyScheduleRpNameArray;

	private List<Duty> oldList;
	private List<Duty> newList;
	private String updateRp;

	private List<Long> duIds;

	private String print;

	//报表用
	private String runqianParams;//润乾参数
	private String reportFileName;//润乾参数

	//跳转到排班页面
	public String add() {
		dutyRule = dutyRuleService.get(ruleId);
		//获得登陆人部门下的所有班组
		groups = dutyRule.getDept().getGroups();
		return "add";
	}

	//验证当天是否存在已经交接过的班
	public String validateDateAjax() {
		Date updDate = DateUtil.stringToDate(startDate, DateUtil.FORMAT_DAY);
		Boolean flag = dutyService.hasDutyShiftedFromThisDay(new Dept(dpId), updDate);
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	//验证排班部门内有没有没有人员的班组
	public String validateGroup() {
		Group group = groupService.get(groupId);
		List<Person> people = group.getPeople();
		if (people == null || people.size() == 0) {
			JSONObject gp = new JSONObject();
			gp.put("result", false);
			gp.put("groupId", group.getId());
			gp.put("groupName", group.getName());
			this.json = gp.toString();
			return EASY;
		} else {
			return RIGHT;
		}
	}

	//生成排班表
	public String addDutyAjax() {
		//更新排班历史记录
		List<DutySchedule> prepareDutySchList = new ArrayList<DutySchedule>();
		DutyRule dutyRule = this.dutyRuleService.get(ruleId);
		for (int i = 0; i < dutyScheduleIds.length; i++) {
			DutySchedule dutySchedule = this.dutyScheduleService.get(dutyScheduleIds[i]);
			dutySchedule.getRuleItems().clear();
			List<RuleItem> ruleItemList = new ArrayList<RuleItem>();

			for (int j = 0; j < cycle; j++) {
				RuleItem ruleItem = new RuleItem(dutySchedule, new Group(new Long(groupIds[i * cycle + j])), j + 1);
				ruleItemList.add(ruleItem);
			}
			dutySchedule.getRuleItems().addAll(ruleItemList);
			dutySchedule.setDutyRule(dutyRule);
			prepareDutySchList.add(dutySchedule);
		}
		dutyRule.setDutySchedules(prepareDutySchList);
		dutyRuleService.save(dutyRule);

		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.createDate(endDate);
		this.dutyService.arrangeClass(new Dept(dpId), start, end, prepareDutySchList, groupIds, cycle, firstDay);
		return RIGHT;
	}

	//查询排班信息页面
	public String show() {
		depts = getCurUser().getDeptsFun(FunModule.DUTY);
		if (depts != null && depts.size() != 0) {
			groups = depts.get(0).getGroups();
			dpId = depts.get(0).getId();
		}
		currentDate = DateUtil.getDateStr(getCurDate());
		return SUCCESS;
	}

	//排班信息查询方法
	public String loadArrange() {
		Dept dept = null;
		Group group = null;
		Date start = null;
		Date end = null;
		if (dpId != null && !"".equals(dpId)) {
			dept = new Dept(dpId);
		}
		if (groupId != null && !"".equals(groupId)) {
			group = new Group(groupId);
		}
		if (startDate != null && !"".equals(startDate)) {
			start = DateUtil.createDate(startDate);
		}
		if (endDate != null && !"".equals(endDate)) {
			end = DateUtil.createDate(endDate);
		}
		this.dutyService.get(carrier, dept, group, start, end);
		return GRID;
	}

	//排班信息报表方法
	public String toShowReport() {
		Dept dept = null;
		Date start = null;//开始日期
		Date end = null;//结束日期
		String deptName = "";//部门名称
		String groupids = "";//班组id
		dept = deptService.get(dpId);//部门
		deptName = dept.getName();//部门名称
		List<Long> groupidList = new ArrayList();
		if (groupId != null && !"".equals(groupId)) {//一个班组
			groupids = groupId + "";//班组id
			groupidList.add(groupId);
		} else {//所有的班组
			for (Group groups : dept.getGroups()) {//把所有的groupid都存放进去
				groupidList.add(groups.getId());
				if (groupids.equals("")) {//如果是第一个
					groupids = groups.getId() + "";
				} else {//拼字符串
					groupids = groupids + "," + groups.getId();
				}
			}
		}
		if (!"".equals(groupids)) {//不能为空
			//界面设置开始日期、结束日期不能为空
			start = DateUtil.createDate(startDate);
			end = DateUtil.createDate(endDate);
			//判断开始时间当天是否有排班记录，如果没有选取最近的排班记录的那天
			List<Duty> listOnDay = dutyService.getByStartDay(groupidList, start);
			boolean flag = false;//开始日期是否有效
			if (listOnDay.isEmpty()) {//如果当天没有排班，重新确定开始日期
				List<Duty> listOnNearDay = dutyService.getByNearDay(groupidList, start);
				if (!listOnNearDay.isEmpty()) {
					Duty duty = listOnNearDay.get(0);
					start = duty.getStartTime();
					startDate = DateUtil.dateToString(start, DateUtil.FORMAT_DAY);
					start = DateUtil.stringToDate(startDate, DateUtil.FORMAT_DAY);
					flag = true;
				}
			} else {
				flag = true;
			}
			if (flag) {//如果开始日期是有效的则计算结束日期是否有效
				//判断开始日期到结束日期对应的部门或者班组是否有不同运转的情况，重新确定结束日期
				end = dutyService.getEndDay(groupidList, start, end);//如果有不同运转的情况查找实际的第一个运转的结束日期传出
				endDate = DateUtil.dateToString(end, DateUtil.FORMAT_DAY);
			}
		} else {//为空说明，该部门根本就没有班组
			groupids = "0";
		}
		reportFileName = "排班查询报表.raq";
		runqianParams = "dept_name=" + deptName + ";start_time=" + startDate + ";end_time=" + endDate + ";group_id="
				+ groupids;
		//System.out.println(runqianParams);
		return "toShowReport";
	}

	//跳转到交接班页面
	public String shift() {
		// 从数据库取得今天的时间
		today = DateUtil.dateToDateByFormat(getCurDate(), DateUtil.FORMAT_DAY);
		// 取得登录人
		Person person = this.getCurUser();
		// 取得登录人所在的部门
		dept = person.getDept();
		// 取今天的上班情况，在帮助中展现
		todayDutyList = dutyService.getByThisDay(dept, today);
		// 查询今天能交接的班
		if (!dutyService.hasDutyThisDay(dept, today)) {
			//没有任何排班
			message = "本日没有任何排班,请联系机房主任,制定排班表!";
		} else if (dutyService.isAllDayDutiesShifted(dept, today, person)) {
			// 所有我所在的班都已交接
			message = "本日我所在的所有班次都已完成交接,无法再进行交接班操作!";
		} else {
			//还有未交接的班
			List<Duty> tempList2 = dutyService.getCanShiftDuties(dept, today, person);
			if (tempList2.size() != 0) {
				canShiftDuties = tempList2;
			}
			message = "本日还有未交接的班次，请点击交接班完成交接!";
		}
		//已经上过的班（但可以继续填写值班记录）
		alreadyShiftDuties = this.dutyService.getAlreadyShiftedDuties(dept, today, person);
		// 查询昨天最后一个班能不能交接(跨天交接班的处理方法)
		yesterday = DateUtil.getPreviousDate(today);
		if (!dutyService.isAllDayDutiesShifted(dept, yesterday, person)) {
			// 昨天有没上的班
			List<Duty> yesterdayCanShiftDuties = dutyService.getCanShiftDuties(dept, yesterday, person);
			for (Duty duty : yesterdayCanShiftDuties) {
				if (duty.isCrossDay() && duty.isOneDayLastDuty()) {
					crossAndLastDuty = duty;
				}
			}
		}
		return "shift";
	}

	//准备交接班提交
	public void prepareToWork() {
		model = this.dutyService.get(id);
		Date updDate = getCurDate();
		model.setUpdDate(updDate);
		model.setEmpName(this.getCurUser().getName());
	}

	//交接班提交
	public String toWork() {
		//confirm为false说明页面上选中了  "是"，如果为null说明没选中 "否"
		if (model.getId() != null) {
			if (model.getConfirm() == null) {
				model.setConfirm(false);
			} else {
				if (!model.getConfirm()) {
					model.setConfirm(true);
					if (model.getPreDuty() != null) {
						if (model.getPreDuty().getOffDutyRecord() == null) {
							model.setOnDutyRecord("前一个班次还没有下班或是下班时忘记填写交班记录");
						} else {
							model.setOnDutyRecord(model.getPreDuty().getOffDutyRecord());
						}
					} else {
						model.setOnDutyRecord("系统内的第一个班，没有接班记录");
					}
				}
			}
		}
		this.dutyService.save(model);

		JSONObject dutyJson = new JSONObject();
		//本班信息
		dutyJson.put("id", model.getId());
		dutyJson.put("date", DateUtil.getDateCNStr(model.getStartTime()));
		dutyJson.put("schName", model.getSchName());
		dutyJson.put("time",
				DateUtil.getTimeShortStr(model.getStartTime()) + "-" + DateUtil.getTimeShortStr(model.getEndTime()));
		dutyJson.put("weather", model.getWeather());
		dutyJson.put("temperature", model.getTemperature());
		dutyJson.put("week", model.getWeek());
		dutyJson.put("group", model.getGroup().getName());
		dutyJson.put("people", model.getStaffOnDuty());
		if (model.getOffDutyRecord() != null) {
			dutyJson.put("offDutyRecord", model.getOffDutyRecord());
		} else {
			dutyJson.put("offDutyRecord", "请您下班前填写交班记录！");
		}

		//上一个班信息
		Duty preDuty = model.getPreDuty();
		if (preDuty == null) {
			dutyJson.put("preDutyGroup", "无班组");
		} else {
			if (preDuty.getOffDutyRecord() != null) {
				dutyJson.put("preDutyOffRecord", preDuty.getOffDutyRecord());
			} else {
				dutyJson.put("preDutyOffRecord", "前一个班次还没有下班或是下班时忘记填写交班记录");
			}
			dutyJson.put("preDutyGroup", preDuty.getGroup().getName() + "(" + preDuty.getStaffOnDuty() + ")");
		}

		// 设置值班记录
		JSONArray recordsArr = new JSONArray();
		for (DutyRecord dutyRecord : model.getDutyRecords()) {
			JSONObject recordJson = new JSONObject();
			recordJson.put("id", dutyRecord.getId());
			recordJson.put("content", dutyRecord.getContent());
			recordsArr.add(recordJson);
		}
		dutyJson.put("records", recordsArr.toString());

		//设置值班提醒
		JSONArray promptArray = new JSONArray();
		for (DutyPrompt dutyPrompt : model.getDutyPrompts()) {
			JSONObject promptJson = new JSONObject();
			promptJson.put("id", dutyPrompt.getId());
			promptJson.put("content", dutyPrompt.getEmpName() + "提醒: " + dutyPrompt.getContent());
			promptArray.add(promptJson);
		}
		dutyJson.put("prompts", promptArray.toString());

		//查询当前部门当天提醒
		Date nowDate = DateUtil.dateToDateByFormat(getCurDate(), DateUtil.FORMAT_DAY);
		Dept dept = model.getGroup().getDept();
		List<DutyPrompt> promptsNow = this.dutyPromptService.getDutyPromptNow(dept, nowDate);
		if (promptsNow != null && promptsNow.size() != 0) {
			dutyJson.put("dutyPromptToday", true);
		} else {
			dutyJson.put("dutyPromptToday", false);
		}

		//判断填写交班记录按钮是否可点击
		if (model.getFinished()) {
			dutyJson.put("canClick", false);
		} else {
			dutyJson.put("canClick", true);
		}
		json = dutyJson.toString();
		return EASY;
	}

	//删除排班页面
	public String toDelete() {
		dept = deptService.get(dpId);
		return "delete";
	}

	//删除排班
	@Override
	public String delete() {
		this.dutyService.delete(new Dept(dpId), DateUtil.createDate(startDate), DateUtil.createDate(endDate));
		return RIGHT;
	}

	//下班
	public String offDuty() {
		model = this.dutyService.get(id);
		model.setOffDutyRecord(offDutyRecord);
		this.dutyService.save(model);
		return RIGHT;
	}

	//查询值班记录的页面
	public String view() {
		depts = getCurUser().getDeptsFun(FunModule.DUTY);
		if (depts != null && depts.size() != 0) {
			groups = depts.get(0).getGroups();
			dpId = depts.get(0).getId();
		}
		currentDate = DateUtil.getDateStr(getCurDate());
		return "view";
	}

	//值班记录查询的方法
	public String loadDuty() throws UnsupportedEncodingException {
		Dept dept = null;
		Group group = null;
		Date start = null;
		Date end = null;
		if (dpId != null) {
			dept = new Dept(dpId);
		}
		if (groupId != null) {
			group = new Group(groupId);
		}
		if (startDate != null && !"".equals(startDate)) {
			start = DateUtil.createDate(startDate);
		}
		if (endDate != null && !"".equals(endDate)) {
			end = DateUtil.createDate(endDate);
		}
		Boolean flag = null;
		if (hasCheckes != null) {
			switch (hasCheckes) {
			case -1:
				flag = null;
				break;
			case 0:
				flag = false;
				break;
			case 1:
				flag = true;
				break;
			default:
				break;
			}
		}
		this.dutyService.get(carrier, dept, group, encodeContent(empName), flag, start, end);
		return GRID;
	}

	//跳转到查询单独的值班记录的页面
	public String scanDuty() {
		if (id != null) {
			dutyScan = this.dutyService.get(id);
		}
		depts = this.getCurUser().getDeptsFun(FunModule.DUTY);
		return "scan";
	}

	//查询单独的值班记录时换部门
	public String scanDutyChangeDept() {
		List<Duty> list = dutyService.getDuties(new Dept(dpId), getCurDate());
		if (list == null || list.size() == 0) {
			dutyScan = null;
		} else {
			dutyScan = list.get(0);
		}
		depts = this.getCurUser().getDeptsFun(FunModule.DUTY);
		return "scan";
	}

	//添加审核信息的ajax
	public String addCheckAjax() {
		DutyCheck dutyCheck = null;
		if (check != null && id != null) {
			Duty duty = this.dutyService.get(id);
			dutyCheck = new DutyCheck(duty, check, this.getCurUser().getName(), this.getCurDate());
			this.dutyService.addADutyCheck(duty, dutyCheck);
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", true);
		jsonObject.put("id", dutyCheck.getId());
		jsonObject.put("empName", dutyCheck.getEmpName());
		jsonObject.put("startTime", DateUtil.dateToString(dutyCheck.getStartTime(), "MM月dd日 HH:mm"));
		jsonObject.put("content", check);
		json = jsonObject.toString();
		return EASY;
	}

	//删除审核信息的ajax
	public String delCheckAjax() {
		dutyCheckService.delete(checkId);
		JSONObject obj = new JSONObject();
		obj.put("result", true);
		obj.put("id", checkId);
		json = obj.toString();
		return EASY;
	}

	//跳转到快速调班页面
	public String quickChange() {
		depts = this.getCurUser().getDeptsFun(FunModule.DUTY);
		return "quickchange";
	}

	//快速调班获取排班表
	public String quickChangeDetail() {
		Dept dept = deptService.get(dpId);
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.addDay(start, 6);
		//只取当天为准，的X运转的排班
		List<Duty> tempDuty = dutyService.getDuties(dept, start, end);
		if (tempDuty == null || tempDuty.size() == 0) {
			return WRONG;
		} else {
			Duty duty = tempDuty.get(0);
			int dayPartCount = duty.getDayPartCount();
			list = dutyService.get(dept, start, end, dayPartCount);
		}
		return NORMAL;
	}

	//保存快速调班信息
	public String saveQuickChangeDetail() {
		JSONArray array = JSONArray.fromObject(changeDetails);
		dutyService.savechangeDetails(array);
		return RIGHT;
	}

	//转到替换班页面
	public String toReplace() {
		depts = this.getCurUser().getDeptsFun(FunModule.DUTY);
		dutySchedules = new HashMap<Long, List<DutySchedule>>();
		for (Dept dept : depts) {
			List<DutySchedule> tmp = Lists.newArrayList();
			List<DutyRule> dutyRuleList = dept.getDutyRules();
			for (DutyRule rule : dutyRuleList) {
				for (DutySchedule schedule : rule.getDutySchedules()) {
					tmp.add(schedule);
				}
			}
			dutySchedules.put(dept.getId(), tmp);
		}
		return "replace";
	}

	//根据开始结束时间和人员检查替换班影响了多少个班次
	public String getEffectedGroupNumAjax() {
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.createDate(endDate);
		DutySchedule dutySchedule = dutyScheduleService.get(scheduleId);
		int num = dutyService.getEffectedGroupNum(empName, dutySchedule, start, end);
		JSONObject obj = new JSONObject();
		obj.put("result", true);
		obj.put("num", num);
		json = obj.toString();
		return EASY;
	}

	//替换班预览功能
	public String toPreviewReplaceDuties() throws UnsupportedEncodingException {
		Dept dept = new Dept(dpId);
		Map<String, String[]> params = new HashMap<String, String[]>();
		params.put("startDateArray", startDateArray.split(","));
		params.put("endDateArray", endDateArray.split(","));
		params.put("dutyScheduleNameArray", dutyScheduleNameArray.split(","));
		params.put("empNameArray", empNameArray.split(","));
		params.put("flagsArray", flagsArray.split(","));
		params.put("empRpNameArray", empRpNameArray.split(","));
		params.put("dutyScheduleRpNameArray", dutyScheduleRpNameArray.split(","));
		oldList = dutyService.getDuties(dept, DateUtil.createDate(startDate), DateUtil.createDate(endDate));
		newList = dutyService.getCompareList(oldList, params);
		return "snapshot";
	}

	//保存替换班结果
	public String updateReplaceResult() {
		JSONObject obj = JSONObject.fromObject(updateRp);
		dutyService.updateReplaceResult(obj);
		return RIGHT;
	}

	//转到删除人员
	public String toDeleteStaff() {
		depts = this.getCurUser().getDeptsFun(FunModule.DUTY);
		return "staffdel";
	}

	//ajax查删除人员排班表
	public String selectDelStaffAjax() {
		Dept dept = new Dept(dpId);
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.createDate(endDate);

		List<Duty> duties = dutyService.getDuties(dept, start, end, empName);
		for (Duty duty : duties) {
			if (duty.getMoniter().getName().equals(empName)) {
				duty.getMoniter().setName("<font color='#9901d8'>" + empName + "</font>");
			} else {
				for (int i = 0; i < duty.getWatchers().size(); i++) {
					if (duty.getWatchers().get(i).getName().equals(empName)) {
						duty.getWatchers().get(i).setName("<font color='#9901d8'>" + empName + "</font>");
					}
				}
			}
		}
		list = duties;
		return NORMAL;
	}

	//删除人员
	public String deleteStaffAjax() {
		dutyService.deleteStaffOnDuty(duIds, empName);
		return RIGHT;
	}

	//转到添加人员页面
	public String toAddStaff() {
		depts = getCurUser().getDeptsFun(FunModule.DUTY);
		if (depts != null && depts.size() != 0) {
			groups = depts.get(0).getGroups();
		}
		return "staffadd";
	}

	//ajax查添加人员排班表
	public String selectAddStaffAjax() {
		Dept dept = new Dept(dpId);
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.createDate(endDate);
		Group group = null;
		if (groupId != -1L) {
			group = groupService.get(groupId);
		}

		List<Duty> duties = dutyService.getDuties(dept, start, end, group);
		for (Duty duty : duties) {
			if (duty.getMoniter().getName().equals(empName)) {
				duty.getMoniter().setName("<font color='#9901d8'>" + empName + "</font>");
			} else {
				for (int i = 0; i < duty.getWatchers().size(); i++) {
					if (duty.getWatchers().get(i).getName().equals(empName)) {
						duty.getWatchers().get(i).setName("<font color='#9901d8'>" + empName + "</font>");
					}
				}
			}
		}
		list = duties;
		return NORMAL;
	}

	//添加人员
	public String addStaffAjax() {
		dutyService.addStaffOnDuty(duIds, empName);
		return RIGHT;
	}

	private DutyRuleService dutyRuleService;
	private DutyService dutyService;
	private DutyScheduleService dutyScheduleService;
	private DeptService deptService;
	private DutyPromptService dutyPromptService;
	private DutyCheckService dutyCheckService;
	private GroupService groupService;

	@Autowired
	public void setDutyRuleService(DutyRuleService dutyRuleService) {
		this.dutyRuleService = dutyRuleService;
	}

	@Autowired
	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Autowired
	public void setDutyScheduleService(DutyScheduleService dutyScheduleService) {
		this.dutyScheduleService = dutyScheduleService;
	}

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setDutyPromptService(DutyPromptService dutyPromptService) {
		this.dutyPromptService = dutyPromptService;
	}

	@Autowired
	public void setDutyCheckService(DutyCheckService dutyCheckService) {
		this.dutyCheckService = dutyCheckService;
	}

	@Autowired
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public DutyRule getDutyRule() {
		return dutyRule;
	}

	public void setDutyRule(DutyRule dutyRule) {
		this.dutyRule = dutyRule;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Long[] getDutyScheduleIds() {
		return dutyScheduleIds;
	}

	public void setDutyScheduleIds(Long[] dutyScheduleIds) {
		this.dutyScheduleIds = dutyScheduleIds;
	}

	public int[] getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(int[] groupIds) {
		this.groupIds = groupIds;
	}

	public int getCycle() {
		return cycle;
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public Long getDpId() {
		return dpId;
	}

	public void setDpId(Long dpId) {
		this.dpId = dpId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public List<Duty> getTodayDutyList() {
		return todayDutyList;
	}

	public void setTodayDutyList(List<Duty> todayDutyList) {
		this.todayDutyList = todayDutyList;
	}

	public List<Duty> getCanWriteRecordDuties() {
		return canWriteRecordDuties;
	}

	public void setCanWriteRecordDuties(List<Duty> canWriteRecordDuties) {
		this.canWriteRecordDuties = canWriteRecordDuties;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Duty> getCanShiftDuties() {
		return canShiftDuties;
	}

	public void setCanShiftDuties(List<Duty> canShiftDuties) {
		this.canShiftDuties = canShiftDuties;
	}

	public Date getYesterday() {
		return yesterday;
	}

	public void setYesterday(Date yesterday) {
		this.yesterday = yesterday;
	}

	public Duty getCrossAndLastDuty() {
		return crossAndLastDuty;
	}

	public void setCrossAndLastDuty(Duty crossAndLastDuty) {
		this.crossAndLastDuty = crossAndLastDuty;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public List<Duty> getAlreadyShiftDuties() {
		return alreadyShiftDuties;
	}

	public void setAlreadyShiftDuties(List<Duty> alreadyShiftDuties) {
		this.alreadyShiftDuties = alreadyShiftDuties;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public int getFirstDay() {
		return firstDay;
	}

	public void setFirstDay(int firstDay) {
		this.firstDay = firstDay;
	}

	public String getOffDutyRecord() {
		return offDutyRecord;
	}

	public void setOffDutyRecord(String offDutyRecord) {
		this.offDutyRecord = offDutyRecord;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Duty getDutyScan() {
		return dutyScan;
	}

	public void setDutyScan(Duty dutyScan) {
		this.dutyScan = dutyScan;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public Integer getHasCheckes() {
		return hasCheckes;
	}

	public void setHasCheckes(Integer hasCheckes) {
		this.hasCheckes = hasCheckes;
	}

	public Long getCheckId() {
		return checkId;
	}

	public void setCheckId(Long checkId) {
		this.checkId = checkId;
	}

	public String getChangeDetails() {
		return changeDetails;
	}

	public void setChangeDetails(String changeDetails) {
		this.changeDetails = changeDetails;
	}

	public Map<Long, List<DutySchedule>> getDutySchedules() {
		return dutySchedules;
	}

	public void setDutySchedules(Map<Long, List<DutySchedule>> dutySchedules) {
		this.dutySchedules = dutySchedules;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public Long getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Long scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getStartDateArray() {
		return startDateArray;
	}

	public void setStartDateArray(String startDateArray) {
		this.startDateArray = startDateArray;
	}

	public String getEndDateArray() {
		return endDateArray;
	}

	public void setEndDateArray(String endDateArray) {
		this.endDateArray = endDateArray;
	}

	public String getFlagsArray() {
		return flagsArray;
	}

	public void setFlagsArray(String flagsArray) {
		this.flagsArray = flagsArray;
	}

	public String getDutyScheduleRpNameArray() {
		return dutyScheduleRpNameArray;
	}

	public void setDutyScheduleRpNameArray(String dutyScheduleRpNameArray) {
		this.dutyScheduleRpNameArray = dutyScheduleRpNameArray;
	}

	public List<Duty> getOldList() {
		return oldList;
	}

	public void setOldList(List<Duty> oldList) {
		this.oldList = oldList;
	}

	public List<Duty> getNewList() {
		return newList;
	}

	public void setNewList(List<Duty> newList) {
		this.newList = newList;
	}

	public String getDutyScheduleNameArray() {
		return dutyScheduleNameArray;
	}

	public void setDutyScheduleNameArray(String dutyScheduleNameArray) {
		this.dutyScheduleNameArray = dutyScheduleNameArray;
	}

	public String getEmpNameArray() {
		return empNameArray;
	}

	public void setEmpNameArray(String empNameArray) {
		this.empNameArray = empNameArray;
	}

	public String getEmpRpNameArray() {
		return empRpNameArray;
	}

	public void setEmpRpNameArray(String empRpNameArray) {
		this.empRpNameArray = empRpNameArray;
	}

	public String getUpdateRp() {
		return updateRp;
	}

	public void setUpdateRp(String updateRp) {
		this.updateRp = updateRp;
	}

	public List<Long> getDuIds() {
		return duIds;
	}

	public void setDuIds(List<Long> duIds) {
		this.duIds = duIds;
	}

	public String getPrint() {
		return print;
	}

	public void setPrint(String print) {
		this.print = print;
	}

	public String getRunqianParams() {
		return runqianParams;
	}

	public void setRunqianParams(String runqianParams) {
		this.runqianParams = runqianParams;
	}

	public String getReportFileName() {
		return reportFileName;
	}

	public void setReportFileName(String reportFileName) {
		this.reportFileName = reportFileName;
	}

}
