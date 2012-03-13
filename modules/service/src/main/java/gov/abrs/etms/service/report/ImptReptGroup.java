package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ImptPeriod;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.List;

import org.jbpm.graph.exe.ProcessInstance;

public class ImptReptGroup extends ReptGroup {

	private final ImptPeriodService imptPeriodService;

	private final AbnormalService abnormalService;

	private final WorkFlowService workFlowService;

	private final UtilService utilService;

	private final ImptPeriod imptPeriod;

	public ImptReptGroup(String reptTime, List<Rept> repts, WorkFlowService workFlowService,
			AbnormalService abnormalService, UtilService utilService, ImptPeriodService imptPeriodService) {
		super(reptTime, ProcessEnum.REPT_IMPT, repts);
		this.abnormalService = abnormalService;
		this.workFlowService = workFlowService;
		this.utilService = utilService;
		this.imptPeriodService = imptPeriodService;
		this.imptPeriod = imptPeriodService.get(Long.valueOf(this.getReptTime()));
	}

	@Override
	public String getCanNotReportReason() {
		Date now = utilService.getSysTime();
		if (DateUtil.beforeDay(now, getReptSubmitDate())) {
			return "未到上报时间！";
		} else {
			if (DateUtil.afterDay(imptPeriod.getEndDate(), now)) {
				return "重要保证期未结束，还不能上报该报表！";
			} else {
				ProcessInstance pi = workFlowService.getProcessInstance(this.getReptProcess().getDataSource(), this
						.getReptTime());
				if (pi == null) {
					return abnormalService.getCanReportAbnInfo(imptPeriod.getStartDate(), imptPeriod.getEndDate());
				} else if (pi.getRootToken().getNode().getName().equals("重新上报")) {
					return "请点击右上角任务重新上报！";
				} else {
					return "已上报，目前正处于" + pi.getRootToken().getNode().getName() + "节点。";
				}
			}
		}
	}

	@Override
	public boolean getCanReport() {
		return this.getCanNotReportReason().equals("可以上报!") ? true : false;
	}

	@Override
	public String getReptGroupName() {
		ImptPeriod ip = imptPeriodService.get(Long.valueOf(this.getReptTime()));
		String name = ip.getImptPeriodName();
		if (name.indexOf("重要保证期") == -1) {
			name += "重要保证期报表";
		} else {
			name += "报表";
		}
		return name;
	}

	@Override
	public Date getReptSubmitDate() {
		return imptPeriod.getSubmitDate();
	}

	@Override
	public Date getReptCreateDate() {
		return imptPeriod.getUpdDate();
	}

	@Override
	public String getToSubmitUrl() {
		return "report/impt-reporting!toSubmit.action";
	}

}
