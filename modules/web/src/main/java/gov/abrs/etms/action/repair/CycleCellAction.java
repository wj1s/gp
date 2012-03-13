package gov.abrs.etms.action.repair;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.CardModel;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.repair.CardModelService;
import gov.abrs.etms.service.repair.CardService;
import gov.abrs.etms.service.repair.CycleCellService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results({
		@Result(name = "redirectCycleShow", type = "redirect", location = "cycle-cell!show.action?cycShowId=${cycShowId}"),
		@Result(name = "redirectShow", type = "redirect", location = "cycle!show.action") })
public class CycleCellAction extends GridAction<CycleCell> {
	//周期表id
	private String cycShowId;

	//用来 动态传值的id
	private String ids;
	private String equipName;
	private Long cardModelId;
	//动态map用来传值
	private Map formMap = new HashMap();

	private CardModel cardModel;

	private CardModelService cardModelService;

	private CycleCellService cycleCellService;

	private CardService cardService;
	private EquipService equipService;

	private List cardModels = new ArrayList<CardModel>();

	//分区项目联动的ajax
	public String getCardsAjax() throws Exception {
		List<Card> cards = this.cycleCellService.get(id).getCards();
		JSONObject jsonObject = new JSONObject();
		if (cards.size() != 0) {
			jsonObject.put("result", true);
			jsonObject.put("data", json = this.assemblyJsonArray(cards, "name"));
			json = jsonObject.toString();
		} else {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	//显示详细的检修周期表
	public String show() {
		String id = "";
		if (formMap.get("id") == null) {
			id = cycShowId;
			formMap.put("id", cycShowId);
		} else {
			id = ((String[]) formMap.get("id"))[0] + "";
		}
		Cycle c = cycleCellService.getShowList(id);//存放周期表以及对应的区间和卡片	
		if (c == null) {//判断一下 为空的话返回原界面
			return "redirectShow";
		}
		List p = cycleCellService.getPeriod();
		setFormValue("cycle", c);
		setFormValue("period", p);
		if (formMap.get("print") != null) {
			String print = ((String[]) formMap.get("print"))[0] + "";
			if (print.equals("print")) {
				return "print";
			}
		}
		return SUCCESS;
	}

	//新增检修周期区间
	public String add() {
		String name = ((String[]) formMap.get("name"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";
		cycShowId = id;
		cycleCellService.addCycleCell(name, id);
		return "redirectCycleShow";
	}

	//编辑区间
	public String edit() {
		String name = ((String[]) formMap.get("name"))[0] + "";
		String cycleCellId = ((String[]) formMap.get("cycleCellId"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		cycShowId = id;
		cycleCellService.editCycleCell(name, cycleCellId);
		return "redirectCycleShow";
	}

	//判断区间是否可以删除
	public String checkDeleteCell() {
		boolean flag = cycleCellService.checkDelete(ids);
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	//判断卡片是否可以删除
	public String checkDeleteCard() {

		boolean flag = cardService.checkDelete(ids);
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	//删除区间
	@Override
	public String delete() {
		String cycleCellId = ((String[]) formMap.get("cycleCellId"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		cycShowId = id;
		cycleCellService.deleteCycleCell(cycleCellId);
		return "redirectCycleShow";
	}

	//添加卡片 
	public String addCard() {
		String name = ((String[]) formMap.get("name"))[0] + "";
		String periodId = ((String[]) formMap.get("periodId"))[0] + "";
		String cycleCellId = ((String[]) formMap.get("cycleCellId"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		cycShowId = id;
		cardService.addCard(cycleCellId, name, periodId);
		return "redirectCycleShow";
	}

	//编辑卡片
	public String editCard() {
		String name = ((String[]) formMap.get("name"))[0] + "";
		String periodId = ((String[]) formMap.get("periodId"))[0] + "";
		String cardId = ((String[]) formMap.get("cardId"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		cycShowId = id;
		cardService.editCard(cardId, name, periodId);
		return "redirectCycleShow";
	}

	//删除卡片
	public String deleteCard() {
		String cardId = ((String[]) formMap.get("cardId"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		cycShowId = id;
		cardService.deleteCard(cardId);
		return "redirectCycleShow";
	}

	//启用卡片
	public String enableCard() {
		boolean flag = cardService.setAbleItem(ids, "1");
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	//停用卡片
	public String disableCard() {
		boolean flag = cardService.setAbleItem(ids, "0");
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	//显示卡片详细信息
	public String showCard() {
		String cardId = ((String[]) formMap.get("cardId"))[0] + "";
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		Card c = cardService.getCard(cardId);
		if (c == null) {
			cycShowId = id;
			return "redirectCycleShow";
		} else {
			String shutdownTimes[] = (c.getShutdownTime() + "").split("\\.");
			if ((shutdownTimes.length > 1)) {
				if (shutdownTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					shutdownTimes[1] = shutdownTimes[1] + "0";
				}
			}
			String shutdownTime = (shutdownTimes.length > 1) ? (shutdownTimes[0] + ":" + shutdownTimes[1])
					: (shutdownTimes[0]);
			String shutdownTimeShow = (shutdownTimes.length > 1) ? (shutdownTimes[0] + "小时" + shutdownTimes[1] + "分钟")
					: (shutdownTimes[0] + "小时00分钟");
			String processTimes[] = (c.getProcessTime() + "").split("\\.");
			if ((processTimes.length > 1)) {
				if (processTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					processTimes[1] = processTimes[1] + "0";
				}
			}
			String processTime = (processTimes.length > 1) ? (processTimes[0] + ":" + processTimes[1])
					: (processTimes[0]);
			String processTimeShow = (processTimes.length > 1) ? (processTimes[0] + "小时" + processTimes[1] + "分钟")
					: (processTimes[0] + "小时00分钟");
			setFormValue("shutdownTime", shutdownTime);
			setFormValue("processTime", processTime);
			setFormValue("shutdownTimeShow", shutdownTimeShow);
			setFormValue("processTimeShow", processTimeShow);
			setFormValue("card", c);
			setFormValue("id1", id);
		}
		return "input";
	}

	//保存卡片详细信息
	public String saveCardDeal() {
		String cardId = ((String[]) formMap.get("cardId"))[0] + "";
		String shutdownTime = ((String[]) formMap.get("shutdownTime"))[0] + "";
		String processTime = ((String[]) formMap.get("processTime"))[0] + "";
		String tools = ((String[]) formMap.get("tools"))[0] + "";
		String measure = ((String[]) formMap.get("measure"))[0] + "";
		String attention = ((String[]) formMap.get("attention"))[0] + "";
		String other = ((String[]) formMap.get("other"))[0] + "";
		String methods = ((String[]) formMap.get("methods"))[0] + "";
		String technicalStandards = ((String[]) formMap.get("technicalStandards"))[0] + "";
		String remark = ((String[]) formMap.get("remark"))[0] + "";
		String shutdownTimes[] = shutdownTime.split(":");
		Equip equip = equipService.getByName(equipName);
		if (shutdownTimes.length > 1) {
			shutdownTime = shutdownTimes[0] + "." + shutdownTimes[1];
		}
		String processTimes[] = processTime.split(":");
		if (processTimes.length > 1) {
			processTime = processTimes[0] + "." + processTimes[1];
		}
		String id = ((String[]) formMap.get("id"))[0] + "";//周期表id
		cycShowId = id;
		cardService.saveCard(cardId, shutdownTime, processTime, tools, measure, attention, other, methods,
				technicalStandards, remark, equip);
		return "redirectCycleShow";
	}

	//通过设备查找卡片模版的jsonArray
	public String getCardModelByEquip() {
		JSONArray array = new JSONArray();
		Equip equip = equipService.getByName(equipName);
		JSONObject jsonObjectTotal = new JSONObject();
		if (equip != null) {
			cardModels = equip.getEquipModel().getEquipType().getCardModels();
			for (int i = 0; i < cardModels.size(); i++) {
				CardModel cardModelT = (CardModel) cardModels.get(i);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", cardModelT.getName());
				jsonObject.put("id", cardModelT.getId());
				array.add(jsonObject);
			}
			jsonObjectTotal.put("result", true);
			jsonObjectTotal.put("data", array);
		} else {
			jsonObjectTotal.put("result", false);
		}

		json = jsonObjectTotal.toString();
		return EASY;
	}

	//通过卡片模版id查找对应的卡片模版并返回
	//通过设备查找卡片模版list
	public String getCardModelById() {
		cardModel = cardModelService.get(cardModelId);
		JSONObject cardModelJson = new JSONObject();
		if (cardModel != null) {
			String shutdownTimes[] = (cardModel.getShutdownTime() + "").split("\\.");
			if ((shutdownTimes.length > 1)) {
				if (shutdownTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					shutdownTimes[1] = shutdownTimes[1] + "0";
				}
			}
			String shutdownTimeShow = (shutdownTimes.length > 1) ? (shutdownTimes[0] + "小时" + shutdownTimes[1] + "分钟")
					: (shutdownTimes[0] + "小时00分钟");
			String processTimes[] = (cardModel.getProcessTime() + "").split("\\.");
			if ((processTimes.length > 1)) {
				if (processTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					processTimes[1] = processTimes[1] + "0";
				}
			}
			String processTimeShow = (processTimes.length > 1) ? (processTimes[0] + "小时" + processTimes[1] + "分钟")
					: (processTimes[0] + "小时00分钟");
			cardModelJson.put("id", cardModel.getId());
			cardModelJson.put("name", cardModel.getName());
			cardModelJson.put("shutdownTime", shutdownTimeShow);
			cardModelJson.put("processTime", processTimeShow);
			cardModelJson.put("measure", cardModel.getMeasure());
			cardModelJson.put("tools", cardModel.getTools());
			cardModelJson.put("other", cardModel.getOther());
			cardModelJson.put("attention", cardModel.getAttention());
			cardModelJson.put("methods", cardModel.getMethods());
			cardModelJson.put("technicalStandards", cardModel.getTechnicalStandards());
			cardModelJson.put("remark", cardModel.getRemark() == null ? "" : cardModel.getRemark());
			cardModelJson.put("result", true);
		} else {
			cardModelJson.put("result", false);
		}
		json = cardModelJson.toString();
		return EASY;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public String getCycShowId() {
		return cycShowId;
	}

	public void setCycShowId(String cycShowId) {
		this.cycShowId = cycShowId;
	}

	public CardModel getCardModel() {
		return cardModel;
	}

	public void setCardModel(CardModel cardModel) {
		this.cardModel = cardModel;
	}

	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	@Autowired
	public void setCycleCellService(CycleCellService cycleCellService) {
		this.cycleCellService = cycleCellService;
	}

	@Autowired
	public void setCardModelService(CardModelService cardModelService) {
		this.cardModelService = cardModelService;
	}

	@Autowired
	public void setEquipService(EquipService equipService) {
		this.equipService = equipService;
	}

	public Map getFormMap() {
		return formMap;
	}

	public void setFormMap(Map _map) {
		this.formMap = _map;
	}

	public void setFormValue(String key, Object value) {
		formMap.put(key, value);
	}

	public Object getFormValue(String key) {
		return formMap.get(key);
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List getCardModels() {
		return cardModels;
	}

	public void setCardModels(List cardModels) {
		this.cardModels = cardModels;
	}

	public Long getCardModelId() {
		return cardModelId;
	}

	public void setCardModelId(Long cardModelId) {
		this.cardModelId = cardModelId;
	}

}
