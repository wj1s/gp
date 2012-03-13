package gov.abrs.etms.service.repair;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.Record;
import gov.abrs.etms.model.repair.RecordItem;
import gov.abrs.etms.service.baseinfo.PersonService;
import gov.abrs.etms.service.util.CrudService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RecordService extends CrudService<Record> {
	private CardService cardService;
	private PersonService personService;

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	/* 检修记录的保存
	 * @name 郭翔
	 * @date 20110105
	 * 1、保存主记录信息
	 * 2、保存检修人员
	 * 3、保存检修项目
	 * 
	 * */
	public void saveRecord(Record record, String[] empIds, String[] cardIds) {
		Assert.notNull(empIds);
		Assert.notNull(cardIds);
		String empId;
		/*用来删除*/
		if (record.getRecordItems() != null) {
			record.getRecordItems().clear();
		} else {
			record.setRecordItems(new ArrayList<RecordItem>());
		}
		for (int i = 0; i < empIds.length; i++) {
			RecordItem recordItem = new RecordItem();
			String[] cr = empIds[i].split("!heng-gang!");
			/*cr[0]:类型，cr[1]:内容，cr[2]:检修人员*/
			if (cr.length >= 3) {
				/*判断是否为临时项目,类型：0检修项目，1临时检修项目*/
				if (cr[0].equals("0")) {
					Card card = cardService.get(Long.parseLong(cardIds[i].trim()));
					recordItem.setCard(card);
					recordItem.setContent(card.getName() + "(" + card.getPeriod().getName() + ")");
					recordItem.setCycleName(card.getCycleCell().getCycle().getName());
					recordItem.setType("0");
				} else {
					recordItem.setType("1");
					recordItem.setContent((cr[1].split("!dot!"))[0]);
					recordItem.setCycleName((cr[1].split("!dot!"))[1]);
				}
				recordItem.setRecord(record);
				/*检修项目人员编号查询*/
				empId = cr[2].substring(0, cr[2].lastIndexOf(","));
				recordItem.setPersons(personService.getPersons(empId));
			}
			record.getRecordItems().add(recordItem);
		}
		this.save(record);
	}

	//查询检修信息的分页方法
	public void get(Carrier<Record> carrier, Dept dept, Cycle cycle, Group group, Date startDate, Date endDate,
			String cardName, Equip equip) {
		String hql = "from Record where 1=1 ";
		if (dept != null) {
			hql += " and dept = :dept ";
		}
		if (group != null) {
			hql += " and group = :group ";
		}
		if (startDate != null) {
			hql += " and ddate >= :startDate ";
		}
		if (endDate != null) {
			hql += " and ddate <= :endDate ";
		}

		if (cardName != null && !"".equals(cardName)) {
			hql += " and id in (select record.id from RecordItem where content like :cardName)";

		}
		if (cycle != null) {
			hql += " and id in (select record.id from RecordItem where cycleName =:cycleName)";

		}
		if (equip != null) {
			hql += " and id in (select record.id from RecordItem where card.equip =:equip) ";
		}
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
			values.put("endDate", endDate);
		}
		if (cardName != null && !"".equals(cardName)) {
			values.put("cardName", "%" + cardName + "%");
		}
		if (cycle != null) {
			values.put("cycleName", cycle.getName());
		}
		if (equip != null) {
			values.put("equip", equip);
		}
		//如果按日期排序则自动转化成按开始时间排序
		carrier.setSidx("ddate");
		carrier.setSord("DESC");
		this.dao.find(carrier, hql, values);
	}
}
