package gov.abrs.etms.action.repair;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.repair.CardService;
import gov.abrs.etms.service.repair.CycleCellService;
import gov.abrs.etms.service.repair.CycleService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CardAction extends GridAction<Card> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5875318814281893606L;
	private CycleService cycleService;
	private CycleCellService cycleCellService;
	private CardService cardService;
	private List<Dept> depts = new ArrayList<Dept>();
	private List<Cycle> cycles = new ArrayList<Cycle>();
	private List<CycleCell> cycleCells = new ArrayList<CycleCell>();
	private List<Card> cards = new ArrayList<Card>();

	private Long dpId;
	private Long cellId;
	private Long cycleId;
	private Long cardsId;
	private Card cardDetail;
	private String shutdownTimeShow;//显示用的停机时间
	private String processTimeShow;//显示用的需用时间

	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	@Autowired
	public void setCycleService(CycleService cycleService) {
		this.cycleService = cycleService;
	}

	@Autowired
	public void setCycleCellService(CycleCellService cycleCellService) {
		this.cycleCellService = cycleCellService;
	}

	public void setCycles(List<Cycle> cycles) {
		this.cycles = cycles;
	}

	public List<Cycle> getCycles() {
		return cycles;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<CycleCell> getCycleCells() {
		return cycleCells;
	}

	public void setCycleCells(List<CycleCell> cycleCells) {
		this.cycleCells = cycleCells;
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

	public Long getCycleId() {
		return cycleId;
	}

	public void setCycleId(Long cycleId) {
		this.cycleId = cycleId;
	}

	/*页面传入的属性，不在实体类中存在*/
	private String[] empIds;
	private String[] cardIds;//选中的检修卡片ID

	public String[] getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String[] empIds) {
		this.empIds = empIds;
	}

	public String[] getCardIds() {
		return cardIds;
	}

	public void setCardIds(String[] cardIds) {
		this.cardIds = cardIds;
	}

	public String add() {
		return "add";
	}

	/*
	 * 为编写检修项的记录，查询出所有的本部门下的检修项
	 * 
	 * */
	public String itemInputAjax() {
		cycles = this.cycleService.getCycleByDept(this.getCurUser().getDept());
		if (cycles.size() > 0) {
			cycleCells = cycleCellService.getCycleCellByDept(cycles.get(0));
			if (cycleCells.size() > 0) {
				cards = cycleCells.get(0).getCards();
			}
		}
		return "item_input";
	}

	/*临时检修项目*/
	public String itemTemp() {
		cycles = this.cycleService.getCycleByDept(this.getCurUser().getDept());
		return "item_temp";
	}

	/*取得所有检修卡片*/
	public String getCardList() throws Exception {
		List<Card> cardList = new ArrayList<Card>();
		if (id != null) {
			cardList = cycleCellService.get(id).getCards();
		}
		/*传递特定的参数名为list的卡片列表*/
		list = cardList;
		return NORMAL;
	}

	//查询检修卡片模块
	public String view() {
		depts = getCurUser().getDeptsFun(FunModule.REPAIR);
		if (depts != null && depts.size() != 0) {
			dpId = depts.get(0).getId();
		}
		return SUCCESS;
	}

	//检修卡片查询条件
	public String loadCard() throws UnsupportedEncodingException {
		Dept dept = null;
		CycleCell cycleCell = null;
		Cycle cycle = null;
		Card card = null;
		if (dpId != null) {
			dept = new Dept(dpId);
		}
		if (cardsId != null) {
			card = new Card(cardsId);
		}
		if (cellId != null) {
			cycleCell = new CycleCell(cellId);
		}
		if (cycleId != null) {
			cycle = cycleService.get(cycleId);
		}
		this.cardService.get(carrier, dept, cycle, card, cycleCell);
		return GRID;
	}

	//跳转检修记录详细界面
	public String scanCardDetail() {
		if (id != null) {
			cardDetail = this.cardService.get(id);
			String shutdownTimes[] = (cardDetail.getShutdownTime() + "").split("\\.");
			if ((shutdownTimes.length > 1)) {
				if (shutdownTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					shutdownTimes[1] = shutdownTimes[1] + "0";
				}
			}
			shutdownTimeShow = (shutdownTimes.length > 1) ? (shutdownTimes[0] + "小时" + shutdownTimes[1] + "分钟")
					: (shutdownTimes[0] + "小时00分钟");
			String processTimes[] = (cardDetail.getProcessTime() + "").split("\\.");
			if ((processTimes.length > 1)) {
				if (processTimes[1].length() == 1) {//如果是一个位数，后面需要加0补位
					processTimes[1] = processTimes[1] + "0";
				}
			}
			processTimeShow = (processTimes.length > 1) ? (processTimes[0] + "小时" + processTimes[1] + "分钟")
					: (processTimes[0] + "小时00分钟");
		}
		return "detail";
	}

	public Long getCellId() {
		return cellId;
	}

	public void setCellId(Long cellId) {
		this.cellId = cellId;
	}

	public Long getCardsId() {
		return cardsId;
	}

	public void setCardsId(Long cardsId) {
		this.cardsId = cardsId;
	}

	public Card getCardDetail() {
		return cardDetail;
	}

	public void setCardDetail(Card cardDetail) {
		this.cardDetail = cardDetail;
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
