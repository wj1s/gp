package gov.abrs.etms.action.repair;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.common.util.IsConstants;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.repair.Card;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.model.repair.Record;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.repair.CardService;
import gov.abrs.etms.service.repair.CycleCellService;
import gov.abrs.etms.service.repair.CycleService;
import gov.abrs.etms.service.repair.RecordService;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class RecordAction extends CrudAction<Record> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5875318814281893606L;
	private CycleService cycleService;
	private CycleCellService cycleCellService;
	private RecordService recordService;
	private CardService cardService;
	private List<Dept> depts = new ArrayList<Dept>();
	private List<Group> groups = new ArrayList<Group>();
	private List<Cycle> cycles = new ArrayList<Cycle>();
	private List<CycleCell> cycleCells = new ArrayList<CycleCell>();
	private List<Card> cards = new ArrayList<Card>();
	private Long dpId;
	private Long groupId;
	private Long cycleId;
	private Long cycleCellId;
	private String cardName;
	private String startDate;
	private String endDate;
	private Record recordDetail;
	/*页面传入的属性，不在实体类中存在*/
	private String[] empIds;
	private String[] cardIds;//选中的检修卡片ID
	private String equipName;//设备名称
	private EquipService equipService;

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
			//获取开启的检修周期
			Cycle cycleTemp = cycles.get(0);
			cycleTemp.setActive(IsConstants.STATE_NORMAL);
			cycleCells = cycleCellService.getCycleCellByDept(cycleTemp);
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

	/*检修记录项的级联查询*/

	public String getCycleCellList() throws Exception {
		List<CycleCell> cellCellList = new ArrayList<CycleCell>();
		if (id != null) {
			//获取开启的检修周期
			Cycle cycleTemp = this.cycleService.get(id);
			cycleTemp.setActive(IsConstants.STATE_NORMAL);
			cellCellList = cycleCellService.getCycleCellByDept(cycleTemp);
		}
		JSONObject jsonObject = new JSONObject();
		if (cellCellList.size() != 0) {
			jsonObject.put("result", true);
			jsonObject.put("data", json = this.assemblyJsonArray(cellCellList, "name"));
			json = jsonObject.toString();
		} else {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	/*取得所有检修卡片*/
	public String getCardList() {
		if (id != null) {
			list = cardService.getCards(id, IsConstants.STATE_NORMAL);
		}
		/*传递特定的参数名为list的卡片列表*/
		return NORMAL;
	}

	/*保存检修记录*/
	@Override
	public String save() {
		if (this.getId() != null) {
			Record recordTemp = this.recordService.get(this.getId());
			this.getModel().setRecordItems(recordTemp.getRecordItems());
		} else {
			this.getModel().setGroup(this.getCurUser().getGroup());
			this.getModel().setDept(this.getCurUser().getDept());
			this.getModel().setPersonName(this.getCurUser().getName());
		}
		this.getModel().setUpdDate(this.getCurDate());
		JSONObject jsonObject = new JSONObject();
		try {
			this.recordService.saveRecord(this.getModel(), this.getEmpIds(), this.getCardIds());
			jsonObject.put("result", true);
			json = jsonObject.toString();
		} catch (Exception e) {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	//查询检修记录模块
	public String view() {
		depts = getCurUser().getDeptsFun(FunModule.REPAIR);
		if (depts != null && depts.size() != 0) {
			groups = depts.get(0).getGroups();
			dpId = depts.get(0).getId();
		}
		return SUCCESS;
	}

	//检修记录查询条件
	public String loadRecord() throws UnsupportedEncodingException {
		Dept dept = null;
		Group group = null;
		Date start = null;
		Date end = null;
		Cycle cycle = null;
		Equip equip = null;
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
		if (cycleId != null) {
			cycle = cycleService.get(cycleId);
		}
		if (equipName != null && !"".equals(equipName)) {
			equip = equipService.getByName(equipName);
		}
		this.recordService.get(carrier, dept, cycle, group, start, end, encodeContent(cardName), equip);
		return GRID;
	}

	//跳转检修记录详细界面
	public String scanRecordDetail() {
		if (id != null) {
			recordDetail = this.recordService.get(id);
		}
		return "detail";
	}

	//跳转检修编辑界面
	public String inputRecordDetail() {
		if (id != null) {
			recordDetail = this.recordService.get(id);
		}
		return "edit";
	}

	/*检修记录删除*/
	public String delRecord() {
		JSONArray array = JSONArray.fromObject(carrier.getDelIds());
		try {
			for (Object object : array) {
				Long delId = ((JSONObject) object).getLong("id");
				this.recordService.delete(delId);
			}
			return RIGHT;
		} catch (Exception e) {
			e.printStackTrace();
			return WRONG;
		}
	}

	public Record getRecordDetail() {
		return recordDetail;
	}

	public void setRecordDetail(Record recordDetail) {
		this.recordDetail = recordDetail;
	}

	@Autowired
	public void setRecordService(RecordService recordService) {
		this.recordService = recordService;
	}

	@Autowired
	public void setCycleService(CycleService cycleService) {
		this.cycleService = cycleService;
	}

	@Autowired
	public void setCycleCellService(CycleCellService cycleCellService) {
		this.cycleCellService = cycleCellService;
	}

	@Autowired
	public void setCardService(CardService cardService) {
		this.cardService = cardService;
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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
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

	public Long getCycleId() {
		return cycleId;
	}

	public void setCycleId(Long cycleId) {
		this.cycleId = cycleId;
	}

	public Long getCycleCellId() {
		return cycleCellId;
	}

	public void setCycleCellId(Long cycleCellId) {
		this.cycleCellId = cycleCellId;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

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

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	@Autowired
	public void setEquipService(EquipService equipService) {
		this.equipService = equipService;
	}

}
