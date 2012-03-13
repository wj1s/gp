package gov.abrs.etms.service.report;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.rept.ZeroReptDtl;
import gov.abrs.etms.service.util.UtilService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.Date;

import org.jbpm.graph.exe.ProcessInstance;

public class ZeroReptGroup extends ReptGroup {

	private ZeroReptDtl zeroReptDtl;

	private ZeroReptDtlService zeroReptDtlService;

	private WorkFlowService workFlowService;

	private UtilService utilService;

	private Boolean flag = false;

	public ZeroReptGroup() {}

	public ZeroReptGroup(WorkFlowService workFlowService, ZeroReptDtlService zeroReptDtlService,
			UtilService utilService, ZeroReptDtl zeroReptDtl) {
		super(zeroReptDtl.getDtlSeq().toString(), ProcessEnum.REPT_ZERO, new ArrayList<Rept>());
		this.zeroReptDtlService = zeroReptDtlService;
		this.workFlowService = workFlowService;
		this.utilService = utilService;
		this.zeroReptDtl = zeroReptDtlService.getBySeq(zeroReptDtl.getDtlSeq());
	}

	@Override
	public String getCanNotReportReason() {
		Date sysTime = utilService.getSysTime();
		if (DateUtil.beforeDay(sysTime, getReptSubmitDate())) {
			return "未到上报时间！";
		} else {
			if (DateUtil.afterDay(zeroReptDtl.getEndTime(), sysTime)) {
				return "本天的保证期还未结束，还不能上报零报告！";
			} else {
				ProcessInstance pi = null;
				try {
					pi = workFlowService.getProcessInstance(this.getReptProcess().getDataSource(), this.getReptTime());
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (pi == null) {
					flag = true;
					return "可以上报！";
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
		return flag;
	}

	@Override
	public Date getReptCreateDate() {
		return zeroReptDtl.getZeroReptDef().getPromtTime();
	}

	@Override
	public String getReptGroupName() {
		String name = zeroReptDtl.getZeroReptDef().getName() + " "
				+ DateUtil.dateToString(zeroReptDtl.getEndTime(), "MM月dd日");
		return name;
	}

	@Override
	public Date getReptSubmitDate() {
		return zeroReptDtl.getEndTime();
	}

	@Override
	public String getToSubmitUrl() {
		return "report/zero-reporting!toSubmit.action";
	}

	public ZeroReptDtl getZeroReptDtl() {
		return zeroReptDtl;
	}

}
