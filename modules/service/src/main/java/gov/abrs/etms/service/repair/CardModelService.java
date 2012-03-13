/**
 * 
 */
package gov.abrs.etms.service.repair;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.IsConstants;
import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.model.repair.CardModel;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 赵振喜
 *
 */

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CardModelService extends CrudService<CardModel> {
	//保存卡片模版
	public void saveCardModel(String id, String shutdownTime, String processTime, String tools, String measure,
			String attention, String other, String methods, String technicalStandards, String remark) {
		String hql = "from CardModel c where c.id=" + id;
		List<CardModel> list = this.dao.find(hql);
		if (list.size() != 0) {
			CardModel c = list.get(0);
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
			this.dao.save(c);
		}
	}

	//添加卡片模版
	public void addCardModel(CardModel cardModel) {
		cardModel.setActive(IsConstants.STATE_NORMAL);
		this.dao.save(cardModel);
	}

	//编辑卡片模版
	public void editCardModel(CardModel cardModel) {
		CardModel cardModelOld = this.get(cardModel.getId());
		if (cardModelOld != null) {
			cardModelOld.setName(cardModel.getName());//名称
			cardModelOld.setEquipType(cardModel.getEquipType());//设备类型
			cardModelOld.setShutdownTime(cardModel.getShutdownTime());//停机时间
			cardModelOld.setProcessTime(cardModel.getProcessTime());//需用时间
			cardModelOld.setMeasure(cardModel.getMeasure());//安全措施
			cardModelOld.setTools(cardModel.getTools());//仪器工具
			cardModelOld.setOther(cardModel.getOther());//其它措施
			cardModelOld.setAttention(cardModel.getAttention());
			cardModelOld.setMethods(cardModel.getMethods());//检修方法
			cardModelOld.setTechnicalStandards(cardModel.getTechnicalStandards());//技术标准
			cardModelOld.setRemark(cardModel.getRemark());//备注
			this.dao.save(cardModelOld);
		}
	}

	//删除卡片模版
	public void deleteCardModel(String id) {
		long idT = Long.parseLong(id);
		this.delete(idT);
	}

	//设置卡片启用停用
	public boolean setAbleItem(String id, String value) {
		String hql = "from CardModel c where c.id=" + id;
		List<CardModel> list = this.dao.find(hql);
		if (list.size() != 0) {
			CardModel c = list.get(0);
			c.setActive(value);
			this.dao.save(c);
			return true;
		}
		return false;
	}

	//获取卡片模版
	public CardModel getCardModel(String id) {
		CardModel c = null;
		String hql = "from CardModel c where c.id=" + id;
		List<CardModel> list = this.dao.find(hql);
		if (list.size() != 0) {
			c = list.get(0);
		}
		return c;
	}

	//查询检修卡片的分页方法
	public void get(Carrier<CardModel> carrier, EquipType equipType) {
		String hql = "from CardModel where 1=1 ";
		if (equipType != null) {
			hql += " and equipType=:equipType ";
		}
		Map<String, Object> values = new HashMap<String, Object>();
		if (equipType != null) {
			values.put("equipType", equipType);
		}
		this.dao.find(carrier, hql, values);
	}

	//获取卡片
	public List<CardModel> getCardModels(String active) {
		String hql = "from CardModel c where  c.active=" + active;
		return this.dao.find(hql);
	}
}
