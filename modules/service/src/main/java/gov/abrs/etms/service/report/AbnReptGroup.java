package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.List;

import org.jbpm.graph.exe.ProcessInstance;

public class AbnReptGroup extends ReptGroup {
	private final AbnormalService abnormalService;

	private final WorkFlowService workFlowService;

	private final UtilService utilService;

	public AbnReptGroup(String reptTime, List<Rept> repts, WorkFlowService workFlowService,
			AbnormalService abnormalService, UtilService utilService) {
		super(reptTime, ProcessEnum.REPT_ACCD, repts);
		this.abnormalService = abnormalService;
		this.workFlowService = workFlowService;
		this.utilService = utilService;
	}

	@Override
	public String getCanNotReportReason() {
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
				return abnormalService.getCanReportAbnInfo(this.getReptTime());
			} else if (pi.getRootToken().getNode().getName().equals("重新上报")) {
				return "请点击右上角任务重新上报！";
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
	public String getReptGroupName() {
		return DateUtil.dateToString(DateUtil.stringToDate(this.getReptTime(), "yyyyMM"), "yyyy年MM月") + "事故报表";
	}

	@Override
	public Date getReptSubmitDate() {
		Date month = DateUtil.stringToDate(this.getReptTime(), DateUtil.FORMAT_YYYYMM);
		return DateUtil.addMonth(month, 1);
	}

	@Override
	public Date getReptCreateDate() {
		return DateUtil.stringToDate(this.getReptTime(), DateUtil.FORMAT_YYYYMM);
	}

	@Override
	public String getToSubmitUrl() {
		return "report/abn-reporting!toSubmit.action";
	}
}
