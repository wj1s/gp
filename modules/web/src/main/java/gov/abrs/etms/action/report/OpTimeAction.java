package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.rept.OpTime;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.report.OpTimeService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class OpTimeAction extends GridAction<OpTime> {

	private static final long serialVersionUID = -8021901499148194776L;

	private String reptTime;
	private Map<Operation, String> map;
	private List<Double> broadTimes;
	private List<Long> opIds;
	private List<String> reptTimes = Lists.newArrayList();
	private int canEdit;
	private String msg;

	//跳转到修改传输时间页面的方法
	public String input() {
		Date startDate = DateUtil.getFirstDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM"));
		Date endDate = DateUtil.getLastDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM"));
		List<Operation> list = operationService.get(startDate, endDate);
		ProcessInstance pi = workFlowService.getProcessInstance("reptAccd", reptTime);
		if (pi == null) {
			canEdit = 1;
		} else {
			if (pi.hasEnded()) {
				canEdit = 0;
				msg = "无法修改，流程已经结束!";
			} else {
				TaskInstance ti = workFlowService.findActiveTaskInstance(pi);
				if (ti.getName().equals("技术主管审核")) {
					canEdit = 0;
					msg = "无法修改，流程出技术主管审核节点，请驳回到技办再进行修改!";
				} else if (ti.getName().equals("单位主管审核")) {
					canEdit = 0;
					msg = "无法修改，流程出单位主管审核节点，请驳回到技办再进行修改!";
				} else {
					canEdit = 1;
				}
			}
		}
		map = new HashMap<Operation, String>();
		for (Operation operation : list) {
			OpTime reptOpTime = opTimeService.get(operation, reptTime);
			if (reptOpTime == null) {
				map.put(operation, "0");
			} else {
				map.put(operation, reptOpTime.getBroadTime().toString());
			}
		}
		return "input";
	}

	public String show() {
		Date curDate = getCurDate();
		reptTimes.add(DateUtil.dateToString(curDate, "yyyyMM"));
		reptTimes.add(DateUtil.dateToString(DateUtil.addMonth(curDate, -1), "yyyyMM"));
		return SUCCESS;
	}

	@Override
	public String save() {
		Date updDate = getCurDate();
		String empName = getCurUser().getName();
		try {
			opTimeService.delete(reptTime);
			for (int i = 0; i < opIds.size(); i++) {
				Operation op = operationService.get(opIds.get(i));
				OpTime reptOpTime = new OpTime(op, reptTime, op.getTransType(), op.getTransmitDef(), broadTimes.get(i),
						updDate, empName);
				opTimeService.save(reptOpTime);
			}
			return RIGHT;
		} catch (Exception e) {
			e.printStackTrace();
			return WRONG;
		}
	}

	private OpTimeService opTimeService;
	private OperationService operationService;

	@Autowired
	public void setOpTimeService(OpTimeService opTimeService) {
		this.opTimeService = opTimeService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	public List<Double> getBroadTimes() {
		return broadTimes;
	}

	public void setBroadTimes(List<Double> broadTimes) {
		this.broadTimes = broadTimes;
	}

	public List<Long> getOpIds() {
		return opIds;
	}

	public void setOpIds(List<Long> opIds) {
		this.opIds = opIds;
	}

	public Map<Operation, String> getMap() {
		return map;
	}

	public void setMap(Map<Operation, String> map) {
		this.map = map;
	}

	public List<String> getReptTimes() {
		return reptTimes;
	}

	public void setReptTimes(List<String> reptTimes) {
		this.reptTimes = reptTimes;
	}

	public int getCanEdit() {
		return canEdit;
	}

	public void setCanEdit(int canEdit) {
		this.canEdit = canEdit;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
