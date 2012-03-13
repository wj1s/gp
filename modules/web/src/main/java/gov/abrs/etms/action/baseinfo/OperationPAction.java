package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.OperationP;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.service.baseinfo.CawaveService;
import gov.abrs.etms.service.baseinfo.OperationPService;
import gov.abrs.etms.service.baseinfo.TransTypeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class OperationPAction extends CrudAction<OperationP> {
	private static final long serialVersionUID = -6515264761126217793L;
	private String startDate;

	private String endDate;
	OperationP operationP;

	public OperationP getOperationP() {
		return operationP;
	}

	public void setOperationP(OperationP operationP) {
		this.operationP = operationP;
	}

	@Override
	protected void beforeUpdate(OperationP model) {
		model.setProgram(null);
		model.setCawaveP(null);
		model.setTransmitDef(null);
	}

	@Override
	protected void preSave(OperationP model) {
		model.setTransType(this.transTypeService.get("P"));
		model.setEmpName(this.getCurUser().getName());
		model.setUpdDate(this.getCurDate());
		Date start = DateUtil.createDate(startDate);
		if (!endDate.equals("")) {
			Date end = DateUtil.createDate(endDate);
			model.setEndDate(end);
		}
		model.setStartDate(start);

	}

	private TransTypeService transTypeService;

	@Autowired
	public void setTransTypeService(TransTypeService transTypeService) {
		this.transTypeService = transTypeService;
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

	private OperationPService operationPService;

	@Autowired
	public void setOperationPService(OperationPService operationPService) {
		this.operationPService = operationPService;
	}

	private List<Cawave> cawavePList;//节目流
	private List<Program> programPList;//节目流

	public List<Cawave> getCawavePList() {
		return cawavePList;
	}

	public void setCawavePList(List<Cawave> cawavePList) {
		this.cawavePList = cawavePList;
	}

	public List<Program> getProgramPList() {
		return programPList;
	}

	public void setProgramPList(List<Program> programPList) {
		this.programPList = programPList;
	}

	private CawaveService cawaveService;

	@Autowired
	public void setCawaveService(CawaveService cawaveService) {
		this.cawaveService = cawaveService;
	}

	public String input() {
		//获取所有的节目流
		cawavePList = cawaveService.getAll("name", true);
		//获取所有的卫星
		if (id != null) {//编辑
			//获取operations对象	
			operationP = operationPService.get(id);
			if (operationP != null) {
				programPList = cawaveService.getCurrentPrograms(operationP.getCawaveP());
			} else {
				programPList = new ArrayList<Program>();
			}
		} else {
			operationP = null;
			if (cawavePList.size() != 0) {
				programPList = cawaveService.getCurrentPrograms(cawavePList.get(0));
			} else {
				programPList = new ArrayList<Program>();
			}
		}
		return "input";
	}

	public String getProgramPAjax() throws Exception {
		Cawave cawave = cawaveService.get(id);
		if (cawave != null) {
			programPList = cawaveService.getCurrentPrograms(cawave);
		} else {
			programPList = new ArrayList<Program>();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", true);
		jsonObject.put("data", json = this.assemblyJsonArray(programPList, "name"));
		json = jsonObject.toString();
		return EASY;
	}
}
