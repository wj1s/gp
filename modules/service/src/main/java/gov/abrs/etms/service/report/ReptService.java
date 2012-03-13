package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.BroadByStation;
import gov.abrs.etms.model.rept.BroadByTime;
import gov.abrs.etms.model.rept.ImptPeriod;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.rept.TechReptDef;
import gov.abrs.etms.model.rept.ZeroReptDtl;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.util.CrudService;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ReptService extends CrudService<Rept> {

	//获取某个月的所有报表组
	public List<ReptGroup> getReptGroups(String reptTime) {
		List<Rept> reptList = this.getAllReptsInMonth(reptTime);
		List<ReptGroup> rsList = new ArrayList<ReptGroup>();
		List<Rept> accdList = new ArrayList<Rept>();
		List<TechReptDef> techList = getTechRepts(reptTime);
		List<Rept> imptList = new ArrayList<Rept>();
		List<ZeroReptDtl> zeroList = getZeroRepts(reptTime);
		for (Rept temp : reptList) {
			// 如果对象的dateSource为ACCD那么为事故报表
			if (temp.getReptDef().getDataSource().equals("ACCD")) {
				accdList.add(temp);
			} else if (temp.getReptDef().getDataSource().equals("IMPT")) {
				imptList.add(temp);
			}
		}
		//构造技术安全事故上报组，有可能是多个
		for (int i = 0; i < techList.size(); i++) {
			TechReptDef tech = techList.get(i);
			rsList.add(new TechReptGroup(workFlowService, zeroReptDtlService, utilService, tech));
		}
		//零报告
		for (int i = 0; i < zeroList.size(); i++) {
			ZeroReptDtl zeroReptDtl = zeroList.get(i);
			rsList.add(new ZeroReptGroup(workFlowService, zeroReptDtlService, utilService, zeroReptDtl));
		}
		for (Map.Entry<String, List<Rept>> entry : this.getTimeReptsMap(accdList).entrySet()) {
			rsList
					.add(new AbnReptGroup(entry.getKey(), entry.getValue(), workFlowService, abnormalService,
							utilService));
		}
		for (Map.Entry<String, List<Rept>> entry : this.getTimeReptsMap(imptList).entrySet()) {
			rsList.add(new ImptReptGroup(entry.getKey(), entry.getValue(), workFlowService, abnormalService,
					utilService, imptPeriodService));
		}
		return rsList;
	}

	//查询某一个月的所有可上报报表，包括重要保证期和零报告
	public List<Rept> getAllReptsInMonth(String reptTime) {
		List<Rept> result = new ArrayList<Rept>();
		//获取当月所有的月报表
		result.addAll(this.getRepts(reptTime));
		//获取当月所有的重要保证期报表
		result.addAll(this.getImptRepts(reptTime));
		//获取当月所有的零报告
		//		result.addAll(this.getZeroRepts(reptTime));
		return result;
	}

	//查询某月有多少条零报告
	private List<ZeroReptDtl> getZeroRepts(String reptTime) {
		String hql = "from ZeroReptDtl where startTime <=:end and startTime >=:start";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("start", DateUtil.getFirstDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM")));
		values.put("end", DateUtil.getLastDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM")));
		return dao.find(hql, values);
	}

	private List<TechReptDef> getTechRepts(String reptTime) {
		Date today = DateUtil.dateToDateByFormat(utilService.getSysTime(), DateUtil.FORMAT);
		String hql = "from TechReptDef where endDate <=:end and endDate >=:start and promtTime <=:promtTime ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("start", DateUtil.getFirstDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM")));
		values.put("end", DateUtil.getLastDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM")));
		values.put("promtTime", today);
		return dao.find(hql, values);
	}

	//根据月份查询普通报表
	private List<Rept> getRepts(String reptTime) {
		String hql = "from Rept where reptTime=:reptTime";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("reptTime", reptTime);
		return dao.find(hql, values);
	}

	//根据月份查询重要保证期报表
	public List<Rept> getImptRepts(String reptTime) {
		List<Rept> imptRepts = new ArrayList<Rept>();
		List<ImptPeriod> ips = imptPeriodService.get(reptTime);
		for (ImptPeriod imptPeriod : ips) {
			imptRepts.addAll(this.getRepts(imptPeriod.getId() + ""));
		}
		return imptRepts;
	}

	//根据某一个类型的报表，根据reptTime进行分组并返回
	private Map<String, List<Rept>> getTimeReptsMap(List<Rept> reptList) {
		Map<String, List<Rept>> res = new TreeMap<String, List<Rept>>();
		List<String> reptTimes = new ArrayList<String>();
		for (Rept rept : reptList) {
			reptTimes.add(rept.getReptTime());
		}
		for (String reptTime : reptTimes) {
			List<Rept> resReptList = new ArrayList<Rept>();
			for (Rept rept : reptList) {
				if (rept.getReptTime().equals(reptTime)) {
					resReptList.add(rept);
				}
			}
			res.put(reptTime, resReptList);
		}
		return res;
	}

	//查询事故报表组
	public AbnReptGroup getAbnReptGroup(String reptTime) {
		List<Rept> reptList = this.getRepts(reptTime);
		List<Rept> result = Lists.newArrayList();
		for (Rept temp : reptList) {
			// 如果对象的dateSource为ACCD那么为事故报表
			if (temp.getReptDef().getDataSource().equals("ACCD")) {
				result.add(temp);
			}
		}
		return new AbnReptGroup(reptTime, result, workFlowService, abnormalService, utilService);
	}

	//查询零报告报表组
	public ZeroReptGroup getZeroReptGroup(Long dtlSeq) {
		ZeroReptDtl zeroReptDtl = zeroReptDtlService.getBySeq(dtlSeq);
		return new ZeroReptGroup(workFlowService, zeroReptDtlService, utilService, zeroReptDtl);
	}

	//查询保证期报表组
	public ImptReptGroup getImptReptGroup(String reptTime) {
		List<Rept> reptList = this.getRepts(reptTime);
		List<Rept> result = Lists.newArrayList();
		for (Rept temp : reptList) {
			// 如果对象的dateSource为IMPT那么为保证期报表
			if (temp.getReptDef().getDataSource().equals("IMPT")) {
				result.add(temp);
			}
		}
		return new ImptReptGroup(reptTime, result, workFlowService, abnormalService, utilService, imptPeriodService);
	}

	//根据不同的dataSource和reptTime返回不同的reptGroup
	public ReptGroup getReptGroup(String reptTime, String dataSource) {
		List<Rept> reptList = this.getRepts(reptTime);
		List<Rept> resultAccd = Lists.newArrayList();
		List<Rept> resultImpt = Lists.newArrayList();
		for (Rept temp : reptList) {
			// 如果对象的dateSource为ACCD那么为事故报表
			if (temp.getReptDef().getDataSource().equals(dataSource) && dataSource.equals("ACCD")) {
				resultAccd.add(temp);

			}
			// 如果对象的dateSource为IMPT那么为重要保证期报表
			if (temp.getReptDef().getDataSource().equals(dataSource) && dataSource.equals("IMPT")) {
				resultImpt.add(temp);
			}
		}
		if (dataSource.equals("ACCD")) {
			return new AbnReptGroup(reptTime, resultAccd, workFlowService, abnormalService, utilService);
		}
		if (dataSource.equals("IMPT")) {
			return new ImptReptGroup(reptTime, resultImpt, workFlowService, abnormalService, utilService,
					imptPeriodService);
		} else {
			return null;
		}
	}

	//判断一个时间段影响了多少个事故报表
	public List<String> getAccdReptTimeArray(Date startTime, Date endTime) {
		//先找出开始时间影响到的所有报表
		List<String> reptTimeArray = Lists.newArrayList();
		//判断开始时间距现在总共有多少个月
		int index = 0;
		if (endTime == null) {
			index = DateUtil.compareDate(startTime, utilService.getSysTime(), 1);
		} else {
			index = DateUtil.compareDate(startTime, endTime, 1);
		}
		for (int i = 0; i < index; i++) {
			String reptTime = DateUtil.dateToString(DateUtil.addMonth(startTime, i), DateUtil.FORMAT_YYYYMM);
			reptTimeArray.add(reptTime);
		}
		return reptTimeArray;
	}

	//用于判断保存时代播信息是否影响了报表
	public List<String> getAccdReptTimeArrayForSave(Date startTime, Date endTime, Long id, String broadByFlag,
			BroadByStation broadByStation) {
		//影响事故报表分为3段（开始时间、结束时间、其他参数）
		if (id != null) {
			BroadByTime bbt = broadByTimeService.get(id);
			//判断开始时间
			List<String> accdReptTimeArray1 = Lists.newArrayList();
			if (startTime != bbt.getStartTime()) {
				if (startTime.before(bbt.getStartTime())) {
					accdReptTimeArray1 = getAccdReptTimeArray(startTime, bbt.getStartTime());
				} else {
					accdReptTimeArray1 = getAccdReptTimeArray(bbt.getStartTime(), startTime);
				}
			}
			//判断结束时间
			List<String> accdReptTimeArray2 = Lists.newArrayList();
			Date curDate = utilService.getSysTime();
			if (bbt.getEndTime() == null && endTime != null) {
				if (endTime.before(curDate)) {
					accdReptTimeArray2 = getAccdReptTimeArray(endTime, curDate);
				} else {
					accdReptTimeArray2 = getAccdReptTimeArray(curDate, endTime);
				}
			} else if (bbt.getEndTime() != null && endTime == null) {
				if (bbt.getEndTime().before(curDate)) {
					accdReptTimeArray2 = getAccdReptTimeArray(bbt.getEndTime(), curDate);
				} else {
					accdReptTimeArray2 = getAccdReptTimeArray(curDate, bbt.getEndTime());
				}
			} else if (bbt.getEndTime() != null && endTime != null) {
				if (endTime.before(bbt.getEndTime())) {
					accdReptTimeArray2 = getAccdReptTimeArray(endTime, bbt.getEndTime());
				} else {
					accdReptTimeArray2 = getAccdReptTimeArray(bbt.getEndTime(), endTime);
				}
			}

			//判断其他参数
			List<String> accdReptTimeArray3 = Lists.newArrayList();
			if (!broadByFlag.equals(bbt.getBroadByFlag()) || broadByStation.getId() != bbt.getBroadByStation().getId()) {
				if (bbt.getEndTime() == null) {
					accdReptTimeArray3 = getAccdReptTimeArray(bbt.getStartTime(), curDate);
				} else {
					accdReptTimeArray3 = getAccdReptTimeArray(bbt.getStartTime(), bbt.getEndTime());
				}
			}
			//取出重复
			List<String> finalList = Lists.newArrayList();
			finalList.addAll(accdReptTimeArray1);
			for (String string : accdReptTimeArray2) {
				if (!accdReptTimeArray1.contains(string)) {
					finalList.add(string);
				}
			}
			for (String string : accdReptTimeArray3) {
				if (!accdReptTimeArray1.contains(string)) {
					finalList.add(string);
				}
			}
			return finalList;
		} else {
			if (endTime == null) {
				endTime = utilService.getSysTime();
			}
			List<String> finalList = getAccdReptTimeArray(startTime, endTime);
			return finalList;
		}
	}

	private ImptPeriodService imptPeriodService;

	private WorkFlowService workFlowService;

	private AbnormalService abnormalService;

	private UtilService utilService;

	private ZeroReptDtlService zeroReptDtlService;

	private BroadByTimeService broadByTimeService;

	@Autowired
	public void setImptPeriodService(ImptPeriodService imptPeriodService) {
		this.imptPeriodService = imptPeriodService;
	}

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	@Autowired
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	@Autowired
	public void setZeroReptDtlService(ZeroReptDtlService zeroReptDtlService) {
		this.zeroReptDtlService = zeroReptDtlService;
	}

	@Autowired
	public void setBroadByTimeService(BroadByTimeService broadByTimeService) {
		this.broadByTimeService = broadByTimeService;
	}

}
