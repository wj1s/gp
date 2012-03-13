package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.OperationS;
import gov.abrs.etms.model.baseinfo.Satellite;
import gov.abrs.etms.model.baseinfo.Transfer;
import gov.abrs.etms.service.baseinfo.CawaveService;
import gov.abrs.etms.service.baseinfo.OperationSService;
import gov.abrs.etms.service.baseinfo.SatelliteService;
import gov.abrs.etms.service.baseinfo.TransTypeService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class OperationSAction extends CrudAction<OperationS> {
	private static final long serialVersionUID = -6515264761126217793L;
	private String startDate;

	private String endDate;
	OperationS operationS;

	public OperationS getOperationS() {
		return operationS;
	}

	public void setOperationS(OperationS operationS) {
		this.operationS = operationS;
	}

	private List<Cawave> cawaveSList;//节目流
	private List<Satellite> satelliteSList;//卫星
	private List<Transfer> transferSList;//转发器

	public List<Transfer> getTransferSList() {
		return transferSList;
	}

	public void setTransferSList(List<Transfer> transferSList) {
		this.transferSList = transferSList;
	}

	public List<Cawave> getCawaveSList() {
		return cawaveSList;
	}

	public void setCawaveSList(List<Cawave> cawaveSList) {
		this.cawaveSList = cawaveSList;
	}

	public List<Satellite> getSatelliteSList() {
		return satelliteSList;
	}

	public void setSatelliteSList(List<Satellite> satelliteSList) {
		this.satelliteSList = satelliteSList;
	}

	@Override
	protected void beforeUpdate(OperationS model) {
		model.setTransfer(null);
		model.setCawaveS(null);
		model.setTransmitDef(null);
	}

	@Override
	protected void preSave(OperationS model) {
		model.setTransType(this.transTypeService.get("S"));
		model.setEmpName(this.getCurUser().getName());
		model.setUpdDate(this.getCurDate());
		Date start = DateUtil.createDate(startDate);
		if (!endDate.equals("")) {
			Date end = DateUtil.createDate(endDate);
			model.setEndDate(end);
		}
		model.setStartDate(start);
	}

	private CawaveService cawaveService;

	@Autowired
	public void setCawaveService(CawaveService cawaveService) {
		this.cawaveService = cawaveService;
	}

	private TransTypeService transTypeService;

	@Autowired
	public void setTransTypeService(TransTypeService transTypeService) {
		this.transTypeService = transTypeService;
	}

	private OperationSService operationSService;

	@Autowired
	public void setOperationSService(OperationSService operationSService) {
		this.operationSService = operationSService;
	}

	private SatelliteService satelliteService;

	@Autowired
	public void setSatelliteService(SatelliteService satelliteService) {
		this.satelliteService = satelliteService;
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

	public String input() {
		//获取所有的节目流		
		cawaveSList = cawaveService.getAll("name", true);
		satelliteSList = satelliteService.getAll("name", true);
		//获取所有的卫星
		if (id != null) {//编辑
			//获取operations对象	
			operationS = operationSService.get(id);
			if (operationS != null) {
				transferSList = operationS.getTransfer().getSatellite().getTransfers();
			}
		} else {
			operationS = null;
			if (satelliteSList.size() != 0) {
				transferSList = satelliteSList.get(0).getTransfers();
			} else {
				transferSList = new ArrayList();
			}
		}
		return "input";
	}

	public String getSatelliteSAjax() throws Exception {
		//String transferSHsql = "from Transfer t where t.satellite.id=" + id + " order by t.name";
		//transferSList = operationSService.find(transferSHsql);
		Satellite satellite = satelliteService.get(id);
		if (satellite != null) {
			transferSList = satellite.getTransfers();
		} else {
			transferSList = new ArrayList();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("result", true);
		jsonObject.put("data", json = this.assemblyJsonArray(transferSList, "name"));
		json = jsonObject.toString();
		return EASY;
	}
}
