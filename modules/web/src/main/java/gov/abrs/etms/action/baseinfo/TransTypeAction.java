package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.service.baseinfo.ParaDtlService;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class TransTypeAction extends CrudAction<TransType> {

	private static final long serialVersionUID = -2491744485346839397L;

	public String getOperationByTransType() throws Exception {
		List<Operation> operationList = new ArrayList<Operation>();
		if (id != null) {
			operationList = ((TransType) this.paraDtlService.get(id)).getOperations();
		}
		JSONObject jsonObject = new JSONObject();
		if (operationList.size() != 0) {
			jsonObject.put("result", true);
			jsonObject.put("data", json = this.assemblyJsonArray(operationList, "name"));
			json = jsonObject.toString();
		} else {
			jsonObject.put("result", false);
			json = jsonObject.toString();
		}
		return EASY;
	}

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

}
