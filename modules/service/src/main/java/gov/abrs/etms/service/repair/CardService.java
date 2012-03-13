/**
 * 
 */
package gov.abrs.etms.service.repair;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.IsConstants;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.model.repair.Period;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 郭翔
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CardService extends CrudService<Card> {
	//保存卡片
	public void saveCard(String id, String shutdownTime, String processTime, String tools, String measure,
			String attention, String other, String methods, String technicalStandards, String remark, Equip equip) {
		String hql = "from Card c where c.id=" + id;
		List<Card> list = this.dao.find(hql);
		if (list.size() != 0) {
			Card c = list.get(0);
			double st = Double.parseDouble(shutdownTime);
			double pt = Double.parseDouble(processTime);
			c.setShutdownTime(st);
			c.setProcessTime(pt);
			c.setTools(tools);
			c.setMeasure(measure);
			c.setAttention(attention);
			c.setOther(other);
			c.setMethods(methods);
			c.setTechnicalStandards(technicalStandards);
			c.setRemark(remark);
			c.setEquip(equip);
			this.dao.save(c);
		}
	}

	//添加卡片
	public String addCard(String id, String name, String periodId) {
		String rtnId = "";
		String hql = "from CycleCell cc where cc.id=" + id;
		String hql2 = "from Period p where p.id=" + periodId;
		List<CycleCell> list = this.dao.find(hql);
		if (list.size() != 0) {
			List<Period> listP = this.dao.find(hql2);
			if (listP.size() != 0) {
				CycleCell cc = list.get(0);
				Card c = new Card();
				Period p = listP.get(0);
				c.setName(name);
				c.setPeriod(p);
				c.setCycleCell(cc);
				c.setProcessTime(0);
				c.setShutdownTime(0);
				c.setActive(IsConstants.STATE_NORMAL);
				this.dao.save(c);
				rtnId = c.getId() + "";
			}
		}
		return rtnId;
	}

	//编辑卡片
	public void editCard(String id, String name, String periodId) {
		String hql = "from Card c where c.id=" + id;
		String hql2 = "from Period p where p.id=" + periodId;
		List<Card> list = this.dao.find(hql);
		if (list.size() != 0) {
			List<Period> listP = this.dao.find(hql2);
			if (listP.size() != 0) {
				Period p = listP.get(0);
				Card c = list.get(0);
				c.setName(name);
				c.setPeriod(p);
				this.dao.save(c);
			}
		}
	}

	//判断卡片存在检修记录， true 可以删除 false 不能删除
	public boolean checkDelete(String id) {
		boolean rtnFlag = true;
		String hql = "from Card c where c.id=" + id;
		List<Card> lcc = this.dao.find(hql);
		if (!lcc.isEmpty()) {
			Card card = lcc.get(0);
			if (!(card.getRecordItems()).isEmpty()) {
				return false;
			}
		}
		return rtnFlag;
	}

	//删除卡片
	public void deleteCard(String id) {
		long idT = Long.parseLong(id);
		//active的也不能删
		//删除item 的时候有检修记录不能删
		//删除周期表对应的区间对应的项目
		this.delete(idT);
	}

	//设置卡片启用停用
	public boolean setAbleItem(String id, String value) {
		String hql = "from Card c where c.id=" + id;
		List<Card> list = this.dao.find(hql);
		if (list.size() != 0) {
			Card c = list.get(0);
			c.setActive(value);
			this.dao.save(c);
			return true;
		}
		return false;
	}

	//获取卡片
	public Card getCard(String id) {
		Card c = null;
		String hql = "from Card c where c.id=" + id;
		List<Card> list = this.dao.find(hql);
		if (list.size() != 0) {
			c = list.get(0);
		}
		return c;
	}

	//查询检修卡片的分页方法
	public void get(Carrier<Card> carrier, Dept dept, Cycle cycle, Card card, CycleCell cycleCell) {
		String hql = "from Card where tools!=null ";
		if (dept != null) {
			hql += " and cycleCell.cycle.dept = :dept ";
		}
		if (cycle != null) {
			hql += " and cycleCell.cycle = :cycle ";
		}
		if (cycleCell != null) {
			hql += " and cycleCell = :cycleCell ";
		}
		if (card != null) {
			hql += " and id=:card ";
		}

		Map<String, Object> values = new HashMap<String, Object>();
		if (dept != null) {
			values.put("dept", dept);
		}
		if (cycle != null) {
			values.put("cycle", cycle);
		}
		if (cycleCell != null) {
			values.put("cycleCell", cycleCell);
		}
		if (card != null) {
			values.put("card", card.getId());
		}

		//如果按日期排序则自动转化成按开始时间排序
		//if (carrier.getSidx().equals("date")) {
		//carrier.setSidx("ddate");
		//}
		this.dao.find(carrier, hql, values);
	}

	//获取卡片
	public List<Card> getCards(Long id, String active) {
		String hql = "from Card c where c.cycleCell.id=" + id + " and c.active=" + active;
		return this.dao.find(hql);
	}
}
