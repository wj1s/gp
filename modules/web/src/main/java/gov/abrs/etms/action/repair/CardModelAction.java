package gov.abrs.etms.action.repair;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.para.EquipType;
import gov.abrs.etms.model.repair.CardModel;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.repair.CardModelService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CardModelAction extends GridAction<CardModel> {

	private static final long serialVersionUID = 8928667521338216610L;
	private CardModelService cardModelService;
	private List<CardModel> cardModels = new ArrayList<CardModel>();
	private List equipTypes = new ArrayList<EquipType>();//设备类型list
	private ParaDtlService paraDtlService;
	private CardModel cardModel;
	private Long equipTypeId;//设备类型id
	private String shutdownTimeShow;//显示用的停机时间
	private String processTimeShow;//显示用的需用时间

	//查询检修卡片模块
	public String view() {
		equipTypes = paraDtlService.get(EquipType.class);
		return SUCCESS;
	}

	//查询条件
	public String loadCardModel() throws UnsupportedEncodingException {
		EquipType equipType = null;
		if (equipTypeId != null && equipTypeId != -1) {
			equipType = new EquipType(equipTypeId);
		}
		this.cardModelService.get(carrier, equipType);
		return GRID;
	}

	//查看
	public String scanCardModelDetail() {
		cardModel = cardModelService.get(id);
		if (cardModel == null) {

		} else {
			String shutdownTimes[] = (cardModel.getShutdownTime() + "").split("\\.");
			if ((shutdownTimes.length > 1)) {
				if (shutdownTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					shutdownTimes[1] = shutdownTimes[1] + "0";
				}
			}
			shutdownTimeShow = (shutdownTimes.length > 1) ? (shutdownTimes[0] + "小时" + shutdownTimes[1] + "分钟")
					: (shutdownTimes[0] + "小时00分钟");
			String processTimes[] = (cardModel.getProcessTime() + "").split("\\.");
			if ((processTimes.length > 1)) {
				if (processTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					processTimes[1] = processTimes[1] + "0";
				}
			}
			processTimeShow = (processTimes.length > 1) ? (processTimes[0] + "小时" + processTimes[1] + "分钟")
					: (processTimes[0] + "小时00分钟");
		}
		return "view";
	}

	//输入输出
	public String toAddEdit() {
		equipTypes = paraDtlService.get(EquipType.class);
		if (id != null) {
			cardModel = cardModelService.get(id);
			if (cardModel == null) {

			} else {
				String shutdownTimes[] = (cardModel.getShutdownTime() + "").split("\\.");
				if ((shutdownTimes.length > 1)) {
					if (shutdownTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
						shutdownTimes[1] = shutdownTimes[1] + "0";
					}
				}
				shutdownTimeShow = (shutdownTimes.length > 1) ? (shutdownTimes[0] + "小时" + shutdownTimes[1] + "分钟")
						: (shutdownTimes[0] + "小时00分钟");
				String processTimes[] = (cardModel.getProcessTime() + "").split("\\.");
				if ((processTimes.length > 1)) {
					if (processTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
						processTimes[1] = processTimes[1] + "0";
					}
				}
				processTimeShow = (processTimes.length > 1) ? (processTimes[0] + "小时" + processTimes[1] + "分钟")
						: (processTimes[0] + "小时00分钟");
			}
		}
		return "input";
	}

	//保存
	public String saveCardModelDeal() {
		if (cardModel.getId() == null) {
			this.cardModelService.addCardModel(cardModel);
		} else {
			this.cardModelService.editCardModel(cardModel);
		}
		return SUCCESS;
	}

	@Autowired
	public void setCardModelService(CardModelService cardModelService) {
		this.cardModelService = cardModelService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	public List<CardModel> getCardModels() {
		return cardModels;
	}

	public void setCardModels(List<CardModel> cardModels) {
		this.cardModels = cardModels;
	}

	public Long getEquipTypeId() {
		return equipTypeId;
	}

	public void setEquipTypeId(Long equipTypeId) {
		this.equipTypeId = equipTypeId;
	}

	public List<EquipType> getEquipTypes() {
		return equipTypes;
	}

	public void setEquipTypes(List<EquipType> equipTypes) {
		this.equipTypes = equipTypes;
	}

	public CardModel getCardModel() {
		return cardModel;
	}

	public void setCardModel(CardModel cardModel) {
		this.cardModel = cardModel;
	}

	public String getShutdownTimeShow() {
		return shutdownTimeShow;
	}

	public void setShutdownTimeShow(String shutdownTimeShow) {
		this.shutdownTimeShow = shutdownTimeShow;
	}

	public String getProcessTimeShow() {
		return processTimeShow;
	}

	public void setProcessTimeShow(String processTimeShow) {
		this.processTimeShow = processTimeShow;
	}

}
