package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.rept.TechReptDef;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.Date;

import org.jbpm.graph.exe.ProcessInstance;

public class TechReptGroup extends ReptGroup {

	private TechReptDef tech;

	private WorkFlowService workFlowService;

	private UtilService utilService;

	private final Boolean flag = false;

	public TechReptGroup() {}

	public TechReptGroup(WorkFlowService workFlowService, ZeroReptDtlService zeroReptDtlService,
			UtilService utilService, TechReptDef tech) {
		super(tech.getId() + "", ProcessEnum.REPT_TECH, new ArrayList<Rept>());
		this.tech = tech;
		this.workFlowService = workFlowService;
		this.utilService = utilService;
	}

	@Override
	public String getCanNotReportReason() {//界面用
		//什么时候不能上报-未到上报时间-在目前在上报途中
		//不能上报:A-END_DATE 之前
		if (DateUtil.beforeDay(utilService.getSysTime(), getReptSubmitDate())) {
			return "未到上报时间！";
		} else {
			ProcessInstance pi = null;
			try {
				pi = workFlowService.getProcessInstance(this.getReptProcess().getDataSource(), this.getReptTime());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (pi == null) {
				return "可以上报!";
			} else if (pi.getRootToken().getNode().getName().equals("重新上报(技办)")) {
				return "请点击右上角任务重新上报！";
			} else if (pi.getRootToken().getNode().getName().equals("结束")) {
				return "上报完毕";
			} else {
				return "已上报，目前正处于" + pi.getRootToken().getNode().getName() + "节点。";
			}
		}
	}

	@Override
	public boolean getCanReport() {
		return this.getCanNotReportReason().equals("可以上报!") ? true : false;
	}

	@Override
	public Date getReptCreateDate() {
		return tech.getEndDate();
	}

	@Override
	public String getReptGroupName() {//界面用
		return tech.getName();
	}

	@Override
	public Date getReptSubmitDate() {//界面用
		return tech.getEndDate();
	}

	@Override
	public String getToSubmitUrl() {//界面用
		return "report/tech-reporting!toSubmitTechReport.action";
	}

	public String getToPreviewUrl() {//界面用
		return "report/tech-reporting!toPreviewTechReport.action";
	}
}
