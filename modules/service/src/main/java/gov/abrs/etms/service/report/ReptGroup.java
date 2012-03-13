package gov.abrs.etms.service.report;

import gov.abrs.etms.model.rept.Rept;
import gov.abrs.etms.model.rept.ProcessEnum;

import java.util.Date;
import java.util.List;

public abstract class ReptGroup {
	private String reptTime;
	private ProcessEnum reptProcess;//对应流程
	private List<Rept> repts;

	public ReptGroup() {
		super();
	}

	protected ReptGroup(String reptTime, ProcessEnum reptProcess, List<Rept> repts) {
		super();
		this.reptTime = reptTime;
		this.reptProcess = reptProcess;
		this.repts = repts;
	}

	public String getReptTime() {
		return reptTime;
	}

	public ProcessEnum getReptProcess() {
		return reptProcess;
	}

	public List<Rept> getRepts() {
		return repts;
	}

	public abstract String getToSubmitUrl();

	public abstract boolean getCanReport();

	public abstract String getCanNotReportReason();

	public abstract String getReptGroupName();

	public abstract Date getReptSubmitDate();

	public abstract Date getReptCreateDate();

}
