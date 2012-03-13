package gov.abrs.etms.service.repair;

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
import org.springframework.util.Assert;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CycleCellService extends CrudService<CycleCell> {
	public List<CycleCell> getCycleCellByDept(Cycle cycle) {
		Assert.notNull(cycle);
		String hql = "from CycleCell where cycle =:cycle";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("cycle", cycle);
		List<CycleCell> cycleCells = this.dao.find(hql, values);
		return cycleCells;
	}

	//查询出card为active启用的列表

	//将周期
	public Cycle getShowList(String id) {
		String hql = "from Cycle cycle where cycle.id ='" + id + "'";
		List<Cycle> list = this.dao.find(hql);
		Cycle c = null;
		if (!list.isEmpty()) {
			c = list.get(0);
		}
		return c;
	}

	//获取周期
	public List<Period> getPeriod() {
		String hql = "from Period p order by p.name asc";
		List<Period> list = this.dao.find(hql);
		return list;
	}

	//新增区间
	public String addCycleCell(String name, String cycleId) {
		String id = "";
		String hql = "from Cycle c where c.id=" + cycleId;
		List<Cycle> list = this.dao.find(hql);
		if (list.size() != 0) {
			Cycle c = list.get(0);
			CycleCell cc = new CycleCell();
			cc.setCycle(c);
			cc.setName(name);
			this.dao.save(cc);
			id = cc.getId() + "";
		}
		return id;
	}

	//编辑区间
	public void editCycleCell(String name, String id) {
		String hql = "from CycleCell cc where cc.id=" + id;
		List<CycleCell> list = this.dao.find(hql);
		if (list.size() != 0) {
			CycleCell cc = list.get(0);
			cc.setName(name);
			this.dao.save(cc);
		}
	}

	//判断周期表区间存在检修记录， true 可以删除 false 不能删除
	public boolean checkDelete(String id) {
		boolean rtnFlag = true;
		String hql = "from CycleCell c where c.id=" + id;
		List<CycleCell> lcc = this.dao.find(hql);
		if (!lcc.isEmpty()) {
			CycleCell cc = lcc.get(0);
			List<Card> lcca = cc.getCards();
			for (int l = 0; l < lcca.size(); l++) {
				Card card = lcca.get(l);
				if (!(card.getRecordItems()).isEmpty()) {
					return false;
				}
			}
		}
		return rtnFlag;
	}

	//删除分区以及对应的卡片
	public void deleteCycleCell(String id) {
		long idT = Long.parseLong(id);
		//删除item 删除区间的时候有检修记录不能删
		this.delete(idT);

	}
}
