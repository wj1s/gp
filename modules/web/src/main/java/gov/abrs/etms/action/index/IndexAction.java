package gov.abrs.etms.action.index;

import gov.abrs.etms.action.util.BaseAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.duty.Duty;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.duty.DutyService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class IndexAction extends BaseAction {

	private static final long serialVersionUID = 1397293161233731407L;

	private Duty dutyScan;
	private List<Dept> depts;
	private List<Map<String, ProcessInstance>> newList;

	public String toIndex() {
		Person person = getCurUser();
		Date date = getCurDate();
		depts = person.getDeptsFun(FunModule.DUTY);
		for (Dept dept : depts) {
			dutyScan = dutyService.get(dept, date);
			if (dutyScan != null) {
				break;
			}
		}

		List<ProcessInstance> piList = workFlowService.getProcessInstances(0, person);
		newList = new ArrayList<Map<String, ProcessInstance>>();
		for (int i = piList.size() - 1; i >= 0; i--) {
			ProcessInstance processInstance = piList.get(i);
			String desc = (String) processInstance.getContextInstance().getVariable("taskDescription");
			Map<String, ProcessInstance> piMap = new TreeMap<String, ProcessInstance>();
			piMap.put(desc, processInstance);
			newList.add(piMap);
		}
		return SUCCESS;
	}

	private DutyService dutyService;
	private WorkFlowService workFlowService;

	@Autowired
	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	public Duty getDutyScan() {
		return dutyScan;
	}

	public void setDutyScan(Duty dutyScan) {
		this.dutyScan = dutyScan;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	public List<Map<String, ProcessInstance>> getNewList() {
		return newList;
	}

	public void setNewList(List<Map<String, ProcessInstance>> newList) {
		this.newList = newList;
	}

}
