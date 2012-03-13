package gov.abrs.etms.action.abnormal;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnEquip;
import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.abnormal.AbnOperationA;
import gov.abrs.etms.model.abnormal.AbnOperationN;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.model.abnormal.AbnormalB;
import gov.abrs.etms.model.abnormal.AbnormalF;
import gov.abrs.etms.model.abnormal.AbnormalO;
import gov.abrs.etms.model.para.AbnType;
import gov.abrs.etms.model.para.AccdDuty;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Results( { @Result(name = "backToTop", type = "redirect", location = "../index/index!toIndex.action") })
public class AbnormalAction extends GridAction<Abnormal> {
	private static final long serialVersionUID = -1522032553436580594L;
	private List<ParaDtl> abnTypes;//异态类型
	private List<ParaDtl> allAccdDuty;//责任类型
	private Abnormal abnormal;//异态类型
	private List<Long> abnOperationIds;
	private List<Long> dutyTimeIds;
	private List<String> accdDutys;
	private List<String> dutyTimes;
	private Long taskId;
	private String comment;
	private Integer isPass;
	private String tabNum;
	private List<String> comments;
	private List<ParaDtl> transTypes;//异态类型
	private String transType;
	private String start;
	private String end;

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public List<ParaDtl> getTransTypes() {
		return transTypes;
	}

	public void setTransTypes(List<ParaDtl> transTypes) {
		this.transTypes = transTypes;
	}

	public List<String> getComments() {
		return comments;
	}

	public void setComments(List<String> comments) {
		this.comments = comments;
	}

	public String getTabNum() {
		return tabNum;
	}

	public void setTabNum(String tabNum) {
		this.tabNum = tabNum;
	}

	@Override
	public String load() {
		try {
			if (transType != null && !transType.equals("-1")) {
				carrier.setFilters("transType.id=" + transType);
			}
			carrier.setSidx("updDate");
			carrier.setSord("desc");
			executeDAO.find(entityClass, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GRID;
	}

	public String input() {
		abnTypes = paraDtlService.get(AbnType.class);
		if (id != null) {
			abnormal = abnormalService.get(id);
		}
		return INPUT;
	}

	public String report() {
		abnTypes = paraDtlService.get(AbnType.class);
		allAccdDuty = paraDtlService.get(AccdDuty.class);
		if (id != null) {
			abnormal = abnormalService.get(id);
			comments = workFlowService.getComments(taskId + "");
		}
		return "report";
	}

	public String review() {
		abnormal = abnormalService.get(id);
		comments = workFlowService.getComments(taskId + "");
		return "review";
	}

	public String detail() {
		//判断是否有事故、有故障返回故障和异态、没有返回异态
		abnormal = abnormalService.get(id);
		return "detail";
	}

	@Override
	public String save() {
		transTypes = paraDtlService.get(TransType.class);
		if (dutyTimeIds != null) {
			Assert.isTrue(dutyTimeIds.size() == abnOperationIds.size());
			Assert.isTrue(dutyTimeIds.size() == accdDutys.size());
			Assert.isTrue(dutyTimeIds.size() == dutyTimes.size());
		}
		try {
			Abnormal abnormal = assembly();
			abnormalService.save(abnormal);
			if (taskId != null) {
				workFlowService.endTaskInstance(taskId, getCurUser().getName(), "技办审核");
			}
			if (abnormal instanceof AbnormalF) {
				tabNum = "1";
			} else if (abnormal instanceof AbnormalO) {
				tabNum = "2";
			} else {
				tabNum = "0";
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return WRONG;
		}
	}

	public String refuseInput() {
		return "refuseInput";
	}

	public String refuse() {
		transTypes = paraDtlService.get(TransType.class);
		Abnormal abnormal = abnormalService.get(id);
		if (abnormal == null) {
			return SUCCESS;
		}
		if (abnormal instanceof AbnormalF) {
			tabNum = "1";
		} else if (abnormal instanceof AbnormalO) {
			tabNum = "2";
		}
		//判断是否是从局端博会的事故//如果是则调用接口废除局端的事故
		if (workFlowService.isAuditedByMp(taskId + "")) {
			return SUCCESS;//如果从局端过来的暂时不能停止
		}
		abnormal.setUpdDate(this.getCurDate());
		//判断是否是事故如果是事故，则去掉事故编码，去掉3个描述，将影响都设置为未影响停传
		abnormal.setAccdCode(null);//事故编码
		abnormal.setAccdDesc(null);//事故描述
		abnormal.setAccdPrevWay(null);//事故措施
		abnormal.setAccdReason(null);//事故原因
		List<AbnOperationA> abnoAs = abnormal.getAbnOperationAs();
		List<AbnOperation> abnos = abnormal.getAbnOperations();
		//业务影响设置为非停传
		for (int i = 0; i < abnoAs.size(); i++) {
			AbnOperationA abno = abnoAs.get(i);
			abno.getAccdDutyTimes().clear();//清掉定性信息
			AbnOperation abnoNew = new AbnOperationN();
			abnoNew.setOperation(abno.getOperation());
			abnoNew.setStartTime(abno.getStartTime());
			abnoNew.setEndTime(abno.getEndTime());
			abnoNew.setAbnormal(abnormal);
			abnoNew.setSortby(abno.getSortby());
			abnormal.getAbnOperations().remove(abno);
			abnos.add(abnoNew);
		}
		//将设备设置为非停传
		if (abnormal instanceof AbnormalO) {
			AbnormalO abnormalO = (AbnormalO) abnormal;
			List<AbnEquip> abnes = abnormalO.getAbnEquips();
			for (int i = 0; i < abnes.size(); i++) {
				AbnEquip abne = abnes.get(i);//获取影响的设备
				AbnormalB abnormalB = abne.getAbnormalB();
				if (abnormalB != null) {
					abne.setAbnormalB(null);
					abnormalService.delete(abnormalB.getId());
				}
			}
		}
		abnormalService.save(abnormal);
		//判断是否是从局端博会的事故//如果是则调用接口废除局端的事故
		workFlowService.endAbnTaskInstance(taskId, getCurUser().getName(), comment);
		//tabNum = "1";
		return SUCCESS;
	}

	public String audit() {
		Assert.notNull(taskId);
		transTypes = paraDtlService.get(TransType.class);
		try {
			if (isPass == 0) {
				workFlowService.backTaskInstance(taskId, getCurUser().getName(), comment);
			} else {
				workFlowService.endTaskInstance(taskId, getCurUser().getName(), comment);
			}
			Abnormal abnormal = abnormalService.get(id);
			abnormal.setUpdDate(this.getCurDate());
			abnormalService.save(abnormal);
			if (abnormal instanceof AbnormalF) {
				tabNum = "1";
			} else if (abnormal instanceof AbnormalO) {
				tabNum = "2";
			} else {
				tabNum = "0";
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return WRONG;
		}
	}

	private Abnormal assembly() {
		Abnormal abnormal = getModel();
		//级联故障设备
		abnormal.setEmpName(this.getCurUser().getName());
		abnormal.setUpdDate(this.getCurDate());
		abnormalService.assemblyAbts(abnormal, abnOperationIds, dutyTimeIds, accdDutys, dutyTimes);
		return abnormal;
	}

	public String show() {
		transTypes = paraDtlService.get(TransType.class);
		return SUCCESS;
	}

	public String loadForZeroInfo() {
		Date startDate = DateUtil.stringToDate(start, "yyyyMMddHHmmss");
		Date endDate = DateUtil.stringToDate(end, "yyyyMMddHHmmss");
		abnormalService.get(carrier, startDate, endDate);
		return GRID;
	}

	public List<ParaDtl> getAbnTypes() {
		return abnTypes;
	}

	public void setAbnTypes(List<ParaDtl> abnTypes) {
		this.abnTypes = abnTypes;
	}

	private ParaDtlService paraDtlService;
	private AbnormalService abnormalService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public List<Long> getAbnOperationIds() {
		return abnOperationIds;
	}

	public void setAbnOperationIds(List<Long> abnOperationIds) {
		this.abnOperationIds = abnOperationIds;
	}

	public List<Long> getDutyTimeIds() {
		return dutyTimeIds;
	}

	public void setDutyTimeIds(List<Long> dutyTimeIds) {
		this.dutyTimeIds = dutyTimeIds;
	}

	public List<String> getAccdDutys() {
		return accdDutys;
	}

	public void setAccdDutys(List<String> accdDutys) {
		this.accdDutys = accdDutys;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public List<String> getDutyTimes() {
		return dutyTimes;
	}

	public void setDutyTimes(List<String> dutyTimes) {
		this.dutyTimes = dutyTimes;
	}

	public List<ParaDtl> getAllAccdDuty() {
		return allAccdDuty;
	}

	public void setAllAccdDuty(List<ParaDtl> allAccdDuty) {
		this.allAccdDuty = allAccdDuty;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
}
