package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.model.baseinfo.Route;
import gov.abrs.etms.model.baseinfo.Transfer;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.para.TransmitDef;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.service.baseinfo.CawaveService;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.baseinfo.ProgramService;
import gov.abrs.etms.service.baseinfo.RouteService;
import gov.abrs.etms.service.baseinfo.TransferService;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class OperationAction extends CrudAction<Operation> {

	private static final long serialVersionUID = 1L;
	private String programList;
	private String cawaveSList;
	private String transferList;
	private String cawaveTList;
	private String routeList;
	private String transmitDefList;
	private List<TransType> transTypesPer;
	private String autoData;

	public String getAutoData() {
		return autoData;
	}

	public void setAutoData(String autoData) {
		this.autoData = autoData;
	}

	public String show() throws Exception {
		transTypesPer = this.getCurTransTypes(FunModule.BASEINFO);
		List<Program> programs = this.programService.getAll();
		programList = this.assemblyJsonArray(programs, "name");
		List<Cawave> cawaveSs = this.cawaveService.getAll();
		cawaveSList = this.assemblyJsonArray(cawaveSs, "name");
		List<Transfer> transfers = this.transferService.getAll();
		transferList = this.assemblyJsonArray(transfers, "name");
		List<Cawave> cawaveTs = this.cawaveService.getAll();
		cawaveTList = this.assemblyJsonArray(cawaveTs, "name");
		List<Route> routes = this.routeService.getAll();
		routeList = this.assemblyJsonArray(routes, "pl");
		List<ParaDtl> transmitDefs = this.paraDtlService.get(TransmitDef.class);
		transmitDefList = this.assemblyJsonArray(transmitDefs, "codeDesc");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String autocompleteTAjax() throws UnsupportedEncodingException {
		q = new String(q.getBytes("iso-8859-1"), "UTF-8");//传入的参数
		List<Object> tempList = operationService.getByNameLike(entityClass, q, autoData);//获取数据
		//判断此类对象是否与部门有关系
		boolean classWithDept = false;//判断对象是否和部门有关系
		for (Object object : tempList) {//循环
			if (((AutoCompleteable) object).getDeptsPopedom().size() > 0) {//如果获取部门是否是集成平台等相关
				classWithDept = true;
				break;
			}
		}
		List finalList = Lists.newArrayList();
		//当传入的自动完成url带权限信息，并且此类自动完成的对象与dept有关系时，对结果进行按权限筛选
		if (funModule != null && !"".equals(funModule) && classWithDept) {
			List<Dept> depts = getCurUser().getDeptsFun(FunModule.getFunModule(funModule));
			for (Object obj : tempList) {
				List<Dept> deptTarget = ((AutoCompleteable) obj).getDeptsPopedom();
				List<Dept> listTarget = (List<Dept>) CollectionUtils.intersection(depts, deptTarget);
				if (deptTarget.size() == 0 || listTarget != null && listTarget.size() > 0) {
					finalList.add(obj);
				}
			}
			list = finalList;
		} else {
			list = tempList;
		}
		return AUTO;
	}

	@Override
	public String delete() {
		try {
			operationService.delete(carrier);
			return RIGHT;
		} catch (Exception e) {
			return WRONG;
		}
	}

	private TransferService transferService;
	private CawaveService cawaveService;
	private RouteService routeService;
	private ProgramService programService;
	private ParaDtlService paraDtlService;
	private OperationService operationService;

	@Autowired
	public void setTransferService(TransferService transferService) {
		this.transferService = transferService;
	}

	@Autowired
	public void setCawaveService(CawaveService cawaveService) {
		this.cawaveService = cawaveService;
	}

	@Autowired
	public void setRouteService(RouteService routeService) {
		this.routeService = routeService;
	}

	@Autowired
	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public String getRouteList() {
		return routeList;
	}

	public void setRouteList(String routeList) {
		this.routeList = routeList;
	}

	public String getProgramList() {
		return programList;
	}

	public void setProgramList(String programList) {
		this.programList = programList;
	}

	public String getCawaveSList() {
		return cawaveSList;
	}

	public void setCawaveSList(String cawaveSList) {
		this.cawaveSList = cawaveSList;
	}

	public String getTransferList() {
		return transferList;
	}

	public void setTransferList(String transferList) {
		this.transferList = transferList;
	}

	public String getCawaveTList() {
		return cawaveTList;
	}

	public void setCawaveTList(String cawaveTList) {
		this.cawaveTList = cawaveTList;
	}

	public List<TransType> getTransTypesPer() {
		return transTypesPer;
	}

	public void setTransTypesPer(List<TransType> transTypesPer) {
		this.transTypesPer = transTypesPer;
	}

	public String getTransmitDefList() {
		return transmitDefList;
	}

	public void setTransmitDefList(String transmitDefList) {
		this.transmitDefList = transmitDefList;
	}

}
