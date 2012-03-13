package gov.abrs.etms.action.repair;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.repair.Cycle;
import gov.abrs.etms.model.repair.CycleCell;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.repair.CycleService;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results( { @Result(name = "redirectShow", type = "redirect", location = "cycle!show.action?cycShowNum=${cycShowNum}") })
public class CycleAction extends GridAction<Cycle> {
	private String cycShowNum;//显示tab页

	public String getCycShowNum() {
		return cycShowNum;
	}

	public void setCycShowNum(String cycShowNum) {
		this.cycShowNum = cycShowNum;
	}

	private String ids;//删除校验用的cycle ids

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	private Map formMap = new HashMap();//动态form

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

	private CycleService cycleService;

	@Autowired
	public void setCycleService(CycleService cycleService) {
		this.cycleService = cycleService;
	}

	//系统联动分区的ajax
	public String getCycleCellsAjax() throws Exception {
		List<CycleCell> cycleCells = this.cycleService.get(id).getCycleCells();
		JSONObject jsonObject = new JSONObject();
		if (cycleCells.size() != 0) {
			jsonObject.put("result", true);
			jsonObject.put("data", json = this.assemblyJsonArray(cycleCells, "name"));
			json = jsonObject.toString();
		} else {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	//展现
	public String show() {
		//depts = getCurUser().getDeptsFun(FunModule.REPAIR);
		//List showList = cycleService.getShowList();//存放部门对应的周期表
		List showList = cycleService.getShowList(getCurUser().getDeptsFun(FunModule.REPAIR));
		if (cycShowNum == null) {
			cycShowNum = "0";
		} else {
			if (Integer.parseInt(cycShowNum) > (showList.size() - 1)) {
				cycShowNum = "0";
			}
		}
		setFormValue("cycleList", showList);
		return SUCCESS;
	}

	//获取部门
	public String getDepartment() {
		//List deptList = cycleService.getAddListDepartment();
		List deptList = getCurUser().getDeptsFun(FunModule.REPAIR);
		setFormValue("deptList", deptList);
		return "add";
	}

	//新增周期表
	public String add() {
		String deptId = ((String[]) formMap.get("deptId"))[0] + "";
		String name = ((String[]) formMap.get("name"))[0] + "";
		cycleService.addCycle(deptId, name);//问题	
		cycShowNum = "0";
		List deptList = getCurUser().getDeptsFun(FunModule.REPAIR);
		for (int i = 0; i < deptList.size(); i++) {
			Dept d = (Dept) deptList.get(i);
			if ((d.getId() + "").equals(deptId)) {
				cycShowNum = i + "";
				break;
			}
		}
		return "redirectShow";
	}

	//编辑周期表
	public String edit() {
		String idStr = ((String[]) formMap.get("newNameAndIds"))[0] + "";
		String ids[] = idStr.split("!1643in!");
		cycleService.editCycle(ids);
		return "redirectShow";
	}

	//判断周期表能否删除
	public String checkDelete() {
		String[] idsArr = ids.split("!1538ck!");
		boolean flag = cycleService.checkDelete(idsArr);
		if (flag) {
			return RIGHT;
		} else {
			return WRONG;
		}
	}

	//删除
	@Override
	public String delete() {
		String tabNum = ((String[]) formMap.get("tabNum"))[0] + "";
		String id[] = ((String[]) formMap.get("autoIDUnActive" + tabNum));
		cycleService.deleteCycle(id);
		return "redirectShow";
	}

	//设置启用禁用
	public String setAble() {
		String tabNum = ((String[]) formMap.get("tabNum"))[0] + "";
		String ableNum = ((String[]) formMap.get("ableNum"))[0] + "";
		String id[];
		if (ableNum.equals("0")) {//禁用
			id = ((String[]) formMap.get("autoIDActive" + tabNum));
		} else {//启用
			id = ((String[]) formMap.get("autoIDUnActive" + tabNum));
		}
		cycleService.setAbleCycle(id, ableNum);
		return "redirectShow";
	}

	//检修执行情况
	public String showExecute() {
		//List deptList = cycleService.getAddListDepartment();// 获取数据权限内的部门列表
		List deptList = getCurUser().getDeptsFun(FunModule.REPAIR);
		if (deptList.size() == 0) {//为0了怎么办
			return "execute";
		}
		// 根据部门获取部门与检修系统列表的MAP
		List deptCycList = cycleService.getCycleByDept(deptList);
		GregorianCalendar g = new GregorianCalendar();
		String sysYear = g.get(Calendar.YEAR) + "";
		setFormValue("dept", deptList.get(0));
		setFormValue("deptCycList", deptCycList);
		setFormValue("sysYear", sysYear);
		return "execute";
	}

	//查询检修执行情况-获取某年某周期表的执行情况
	public String searchExecute() {
		String cycleId = ((String[]) formMap.get("cycleId"))[0] + "";//周期表id
		String year = ((String[]) formMap.get("year"))[0] + "";//年份
		// 根据周期表id和年份获取包涵对应年份的周期表对象
		if (cycleId != null && !"".equals(cycleId)) {//获取周期表			
			List deptSys = cycleService.getCycle(new Integer(cycleId), year);
			setFormValue("deptSys", deptSys);// 获得系统年份			
		}
		return "executeContent";
	}

	//获取提醒信息
	public String showItemContext() {
		String cardId = ((String[]) formMap.get("cardId"))[0] + "";//周期表id
		setFormValue("itemContext", cycleService.getitemContext(cardId));
		return "itemContext";
	}
}
