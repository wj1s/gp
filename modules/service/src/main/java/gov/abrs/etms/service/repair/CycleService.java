/**
 * 
 */
package gov.abrs.etms.service.repair;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.common.util.IsConstants;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.model.repair.Period;
import gov.abrs.etms.model.repair.Record;
import gov.abrs.etms.model.repair.RecordItem;
import gov.abrs.etms.service.util.CrudService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author 郭翔
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CycleService extends CrudService<Cycle> {
	private CardService cardService;

	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	private CycleCellService cycleCellService;

	@Autowired
	public void setCycleCellService(CycleCellService cycleCellService) {
		this.cycleCellService = cycleCellService;
	}

	private RecordService recordService;

	@Autowired
	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	//按部门获取周期表
	public List<Cycle> getCycleByDept(Dept dept) {
		Assert.notNull(dept);
		String hql = "from Cycle where dept =:dept and active=" + IsConstants.STATE_NORMAL;
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", dept);
		List<Cycle> cycles = this.dao.find(hql, values);
		return cycles;
	}

	//将部门和对应的周期表拼装成list-map-list形式
	public List getShowList(List listDepartment) {
		List list = new ArrayList();
		//List listDepartment = getShowListDepartmet();
		for (int i = 0; i < listDepartment.size(); i++) {
			Dept d = (Dept) listDepartment.get(i);
			Map m = new HashMap();//存放部门对应的周期表//和部门信息
			List cycleList1 = getShowListCycle(d, "1");
			m.put("cycleListActive", cycleList1);
			List cycleList0 = getShowListCycle(d, "0");
			m.put("cycleListUnActive", cycleList0);
			if ((cycleList0.size() != 0) || (cycleList1.size() != 0)) {
				m.put("cycleDepartment", d);
				list.add(m);
			}
		}
		return list;
	}

	//按部门获取周期表
	private List<Cycle> getShowListCycle(Dept d, String active) {
		String hql = "from Cycle c where c.dept = :dept and c.active= :active order by c.id asc";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("dept", d);
		values.put("active", active);
		List<Cycle> list = this.dao.find(hql, values);
		return list;
	}

	//新增检修周期表
	public String addCycle(String deptId, String name) {
		String id = "";
		String hql = "from Dept d where d.id=" + deptId;
		List<Dept> list = this.dao.find(hql);
		if (list.size() != 0) {
			Dept d = list.get(0);
			Cycle c = new Cycle();
			c.setDept(d);
			c.setActive(IsConstants.STATE_NORMAL);
			c.setName(name);
			this.dao.save(c);
			id = c.getId() + "";
		}
		return id;
	}

	//设置启用/停用周期表
	public void setAbleCycle(String id[], String value) {
		if ((id != null) && (id.length != 0)) {
			for (int i = 0; i < id.length; i++) {
				String hql = "from Cycle c where c.id=" + id[i];
				List<Cycle> list = this.dao.find(hql);
				if (list.size() != 0) {
					Cycle c = list.get(0);
					c.setActive(value);
					this.dao.save(c);
				}
			}
		}
	}

	//判断周期表存在检修记录， true 可以删除 false 不能删除
	public boolean checkDelete(String id[]) {
		boolean rtnFlag = true;
		for (int i = 0; i < id.length; i++) {//每个id做判读
			String hql = "from Cycle c where c.id=" + id[i];
			List<Cycle> lc = this.dao.find(hql);
			if (!lc.isEmpty()) {
				Cycle c = lc.get(0);
				List lcc = c.getCycleCells();
				for (int k = 0; k < lcc.size(); k++) {//每个区间做判断
					CycleCell cc = (CycleCell) lcc.get(k);
					List lcca = cc.getCards();
					for (int l = 0; l < lcca.size(); l++) {
						Card card = (Card) lcca.get(l);
						if (!(card.getRecordItems()).isEmpty()) {
							return false;
						}
					}
				}
			}
		}
		return rtnFlag;
	}

	//删除周期表
	public void deleteCycle(String id[]) {
		if ((id != null) && (id.length != 0)) {
			for (int i = 0; i < id.length; i++) {
				try {
					long idT = Long.parseLong(id[i]);
					//删除item 删除区间的时候有检修记录不能删
					this.delete(idT);
				} catch (Exception e) {

				}
			}
		}
	}

	//编辑周期表
	public void editCycle(String[] ids) {
		if ((ids != null) && (ids.length != 0)) {
			long id;
			String name = "";
			for (int i = 0; i < ids.length; i++) {
				id = Long.parseLong(ids[i].split("!1642in!")[0] + "");
				name = ids[i].split("!1642in!")[1] + "";
				String hql = "from Cycle c where c.id=" + id;
				List<Cycle> list = this.dao.find(hql);
				if (list.size() != 0) {
					Cycle c = list.get(0);
					c.setName(name);
					this.dao.save(c);
				}
			}
		}
	}

	//获取部门和对应检修记录关系
	public List getCycleByDept(List<Dept> deptList) {
		List rtnList = new ArrayList();
		for (Dept dept : deptList) {//循环
			List<Cycle> deptSysList = this.getCycleByDept(dept);//获取
			if (deptSysList != null && deptSysList.size() > 0) {//判断				
				Map m = new HashMap();
				m.put("dept", dept);
				m.put("deptSysList", deptSysList);
				rtnList.add(m);
			}
		}
		return rtnList;
	}

	//将周期
	public Cycle getCycleById(String id) {
		String hql = "from Cycle cycle where cycle.id ='" + id + "'";
		List<Cycle> list = this.dao.find(hql);
		Cycle c = null;
		if (!list.isEmpty()) {
			c = list.get(0);
		}
		return c;
	}

	//将周期
	public Card getCardyId(String id) {
		String hql = "from Card card where card.id ='" + id + "'";
		List<Card> list = this.dao.find(hql);
		Card c = null;
		if (!list.isEmpty()) {
			c = list.get(0);
		}
		return c;
	}

	public List getCycle(Integer id, String year) {
		Cycle cycle = getCycleById(id + "");
		if (cycle == null) {//如果检修周期表被删掉了
			return new ArrayList();
		}
		//获取一个list-map 包涵所有需要的信息
		List rtnList = new ArrayList();//包含所有信息的list
		Map cellMap = new HashMap();//存放cell的Map
		List cellList = new ArrayList();//存放cell的list
		for (CycleCell transCell : cycle.getCycleCells()) {//循环
			List cardList = new ArrayList();//放卡片的list
			for (Card card : transCell.getCards()) {//循环
				if (!(card.getActive().equals("1"))) {
					continue;
				}
				Map cardMap = new HashMap();//放卡片的map
				cardMap.put("cardId", card.getId());//卡片id
				cardMap.put("cardName", card.getName());//卡片的名称
				cardMap.put("periodName", card.getPeriod().getName());//周期名称
				cardMap.put("state", getState(card));//检修状态								
				cardMap.put("repairList", getRepairList(card, year));//设置12个月的检修记录list
				cardList.add(cardMap);//将cardMap添加到cardList中
			}
			Map map = new HashMap();
			map.put("cellName", transCell.getName());
			map.put("cardList", cardList);
			cellList.add(map);
		}
		cellMap.put("sysName", cycle.getName());//系统名
		cellMap.put("cellList", cellList);//list
		rtnList.add(cellMap);
		return rtnList;
	}

	//通过list查找最近的检修记录
	private RecordItem findLastWithCardId(List<RecordItem> list) {
		// 如果传入参数为空，则返回空
		if (list == null || list.size() == 0) {
			return null;
		}
		//循环记录集合，查找系统中ITEM的最后一次检修记录项目
		RecordItem lastRecord = null;
		Date lastRecordDate = null;
		for (int i = 0; i < list.size(); i++) {
			RecordItem ir = list.get(i);
			if (i == 0) {
				lastRecordDate = ir.getRecord().getDdate();
				lastRecord = ir;
			} else {
				if (DateUtil.afterDay(ir.getRecord().getDdate(), lastRecordDate)) {
					lastRecordDate = ir.getRecord().getDdate();
					lastRecord = ir;
				}
			}
		}
		return lastRecord;
	}

	//获取每个检修卡片的状态
	/* 计算提醒状态:01:还没到提醒日期，02，已经过提醒日期还没到计划检修日期，03到期，04过期，05不正常 */
	public String getState(Card card) {
		String rtnStr = "05";
		//String rtnStr0 = "";
		Date now = new Date();//  获取当前时间
		List riList = card.getRecordItems();//检修记录list
		if (riList == null || (riList.size() == 0)) {//没有检修过
			rtnStr = "05";//设置未知
		} else {
			RecordItem ri = findLastWithCardId(riList);//获取最近的检修记录
			Period period = card.getPeriod();//获取检修周期
			Date riDate = ri.getRecord().getDdate();//最近一次的检修时间
			Date nextRmDate = new Date();//下次提醒时间
			Date nextRpDate1 = new Date();//下次检修时间
			Date nextRpDate2 = new Date();//下次检修时间
			int rmLength = period.getPreviousValue();//提前多少天提醒
			if (period.getType().equals("1")) {//类型1 每隔几天检修1次
				int rpLength = period.getValue();//检修周期-间隔
				nextRpDate1 = DateUtil.addDay(riDate, rpLength + 1);//下次执行时间=上次执行时间+周期
				nextRmDate = DateUtil.addDay(nextRpDate1, -rmLength);//下次提醒时间=上次执行时间+周期-提前提醒时间
			} else {//类型2 每年固定时期检修
				Calendar compareCalendar = DateUtil.dateToCalendar(now);//判断今年有没有检修
				int compareYear = compareCalendar.get(Calendar.YEAR);//年份
				nextRpDate1 = period.getStartDay();//获取
				nextRpDate2 = period.getEndDay();//获取
				nextRpDate1.setYear(compareYear - 1900);//下次执行时间1=上次执行时间下一年的begin day
				nextRpDate2.setYear(compareYear - 1900);//下次执行时间2=上次执行时间下一年的end day
				if (DateUtil.afterDay(nextRpDate1, nextRpDate2)) {// 如果开始时间晚于结束时间，结束时间加一年
					nextRpDate2 = DateUtil.addYear(nextRpDate2, 1);
				}
				nextRmDate = DateUtil.addDay(nextRpDate1, -rmLength);//下次提醒时间=下次执行时间1-提醒天数
			}
			if (DateUtil.afterDay(nextRmDate, now)) { //当前日期在提醒日期之前-01
				rtnStr = "01";
			} else if (DateUtil.afterDay(nextRpDate1, now)) { //当前日期在提醒日期和计划检修日期之间-02
				rtnStr = "02";
			} else if (!DateUtil.afterDay(now, nextRpDate1)) {//当前日期在计划检修日期当天-03
				rtnStr = "03";
			} else {//type不等于1的情况，当前日期在两个检修日期之间也是-03
				//当前日期在计划检修日期之后-04//type不等于1的情况，检修日期为下次计划检修时间-04
				if (period.getType().equals("1")) {//如果固定时间长度
					rtnStr = "04";//过期
				} else {
					if (!DateUtil.afterDay(now, nextRpDate2)) {//03
						rtnStr = "03";//到期
					} else {//04
						//判断是否检修
						if (DateUtil.valiSysTimeInScope(nextRpDate1, riDate, nextRpDate2)) {//检修过了
							rtnStr = "01";// 正常
						} else {
							rtnStr = "04";//过期
						}
					}
				}
			}
		}
		//获取最近的检修记录时间
		return rtnStr;
	}

	// 获取某一系统中的某一项目的上下文对象
	public Map getitemContext(String id) {
		Map itemContext = new HashMap();//设置一个map
		Date compareDate = new Date();// 待比对日期
		String state = "";
		String peoples = "";//检修人
		String lastDate;//上次检修时间
		int lastTime;//距离上次检修过了多长时间（天）
		String nextDate;//预计下次检修时间
		int nextTime;//距离下次检修需要多少时间（天）		
		// 获取系统当前时间
		Card card = getCardyId(id);
		if (card.getRecordItems() == null) {//如果没有检修记录则返回空
			itemContext.put("state", "05");
			return itemContext;
		}
		state = getState(card);
		if (state.equals("05")) {//如果没有检修记录则返回
			itemContext.put("state", "05");
			return itemContext;
		}
		List riList = card.getRecordItems();//检修记录list
		RecordItem ri = findLastWithCardId(riList);//获取最近的检修记录
		Period period = card.getPeriod();//检修周期
		Record rd = ri.getRecord();//最近一次的检修记录
		List<Person> peopleList = ri.getPersons();//检修人员的list		
		for (Person persion : peopleList) {
			if (!peoples.equals("")) {
				peoples = peoples + "," + persion.getName();
			} else {
				peoples = persion.getName();
			}
		}
		Date riDate = rd.getDdate();//最近一次的检修时间
		lastDate = DateUtil.dateToString(riDate, "yyyy年MM月dd日");
		lastTime = getIntervalDays(riDate, compareDate);//上次检修距离现在多久

		if (period.getType().equals("1")) {//类型1 每隔几天检修1次
			int rpLength = period.getValue();//检修周期-间隔
			Date nd = DateUtil.addDay(riDate, rpLength + 1);
			nextDate = DateUtil.dateToString(nd, "yyyy年MM月dd日");//下次执行时间=上次执行时间+周期
			nextTime = getIntervalDays(nd, compareDate);//距离下次检修还有多长时间
		} else {//对于类型2来说
			Calendar compareCalendar = DateUtil.dateToCalendar(compareDate);//判断今年有没有检修
			int compareYear = compareCalendar.get(Calendar.YEAR);//年份
			Date nd = period.getStartDay();//获取
			nd.setYear(compareYear - 1900);//设置年份
			//判断当前年有没有检修
			Date nextRpDate1 = period.getStartDay();//获取
			Date nextRpDate2 = period.getEndDay();//获取
			nextRpDate1.setYear(compareYear - 1900);//下次执行时间1=上次执行时间下一年的begin day
			nextRpDate2.setYear(compareYear - 1900);//下次执行时间2=上次执行时间下一年的end day
			if (DateUtil.afterDay(nextRpDate1, nextRpDate2)) {// 如果开始时间晚于结束时间，结束时间加一年
				nextRpDate2 = DateUtil.addYear(nextRpDate2, 1);//
			}
			if (DateUtil.valiSysTimeInScope(nextRpDate1, riDate, nextRpDate2)) {//如果检修了则下次检修为下一年
				nd.setYear(compareYear + 1 - 1900);//设置年份
			}
			nextDate = DateUtil.dateToString(nd, "yyyy年MM月dd日");//下次执行时间=上次执行时间+周期
			nextTime = getIntervalDays(nd, compareDate);//距离下次检修还有多长时间
		}
		itemContext.put("state", state);//设置状态
		itemContext.put("peoples", peoples);//设置人
		itemContext.put("lastDate", lastDate);//设置上次更新时间
		itemContext.put("lastTime", lastTime);//距离上次检修的时间
		itemContext.put("nextDate", nextDate);//下次检修时间
		itemContext.put("nextTime", nextTime);//距离下次检修还有多久
		return itemContext;
	}

	/**
	 * 计算两个任意时间中间的间隔天数
	 */
	public int getIntervalDays(Date startday, Date endday) {
		if (startday.after(endday)) {
			Date cal = startday;
			startday = endday;
			endday = cal;
		}
		long sl = startday.getTime();
		long el = endday.getTime();
		long ei = el - sl;
		int rtnVal = (int) (ei / (1000 * 60 * 60 * 24));
		long valTem = (ei / (1000 * 60 * 60 * 24));
		if (valTem < 1) {
			if (startday.getDate() == endday.getDate()) {//同一天
				rtnVal = 0;
			} else {
				rtnVal = 1;
			}
		}
		return rtnVal;
	}

	//设置12个月的检修list
	public List getRepairList(Card card, String year) {
		List<Record> oneList = new ArrayList<Record>();//1月
		List<Record> twoList = new ArrayList<Record>();//2月
		List<Record> threeList = new ArrayList<Record>();//3月
		List<Record> fourList = new ArrayList<Record>();//4月
		List<Record> fiveList = new ArrayList<Record>();//5月
		List<Record> sixList = new ArrayList<Record>();//6月
		List<Record> sevenList = new ArrayList<Record>();//7月
		List<Record> eightList = new ArrayList<Record>();//8月
		List<Record> nineList = new ArrayList<Record>();//9月
		List<Record> tenList = new ArrayList<Record>();//10月
		List<Record> elevenList = new ArrayList<Record>();//11月
		List<Record> twelveList = new ArrayList<Record>();//12月
		List<RecordItem> lri = card.getRecordItems();
		for (int j = 0; j < lri.size(); j++) {
			Record record = lri.get(j).getRecord();//问题在于不能获取record里面的属性	
			if (record != null && ((record.getDdate().getYear() + 1900) == Integer.parseInt(year))) {//设置年、判断
				int month = record.getDdate().getMonth();
				Date d = record.getDdate();
				if (month == 0) {
					oneList.add(record);
				} else if (month == 1) {
					twoList.add(record);
				} else if (month == 2) {
					threeList.add(record);
				} else if (month == 3) {
					fourList.add(record);
				} else if (month == 4) {
					fiveList.add(record);
				} else if (month == 5) {
					sixList.add(record);
				} else if (month == 6) {
					sevenList.add(record);
				} else if (month == 7) {
					eightList.add(record);
				} else if (month == 8) {
					nineList.add(record);
				} else if (month == 9) {
					tenList.add(record);
				} else if (month == 10) {
					elevenList.add(record);
				} else if (month == 11) {
					twelveList.add(record);
				}
			}
		}
		List<List<Record>> getTableDate = new ArrayList<List<Record>>();
		getTableDate.add(oneList);
		getTableDate.add(twoList);
		getTableDate.add(threeList);
		getTableDate.add(fourList);
		getTableDate.add(fiveList);
		getTableDate.add(sixList);
		getTableDate.add(sevenList);
		getTableDate.add(eightList);
		getTableDate.add(nineList);
		getTableDate.add(tenList);
		getTableDate.add(elevenList);
		getTableDate.add(twelveList);
		return getTableDate;//返回list
	}

}
