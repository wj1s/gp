package gov.abrs.etms.action.abnormal;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnormalO;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Results({ @Result(name = "success", type = "redirect", location = "abnormal.action") })
public class AbnormalOAction extends GridAction<AbnormalO> {
	private static final long serialVersionUID = -7446840981195738261L;
	//影响业务详细信息
	private List<Long> abnoIds;//abnoid
	private List<String> abnoNames;//影响业务名称
	private List<Date> abnoStartTimes;//影响业务开始时间
	private List<Date> abnoEndTimes;//影响业务结束时间
	private List<Integer> accdFlags;//是否停传

	//影响设备详细信息
	private List<Long> abneIds;//id
	private List<String> abneNames;//影响设备名称
	private List<Date> abneStartTimes;//影响业务开始时间
	private List<Date> abneEndTimes;//影响业务结束时间
	private List<Integer> faultFlags;//是否停传
	private Long taskId;
	private Long editInJb;//是否是技术办审核时的异态编辑，如果是1，保存时只保存，返回JSON，否则保存时推进流程，返回成功

	@Override
	protected void beforeUpdate(AbnormalO model) {
		model.setAbnType(null);
	}

	@Override
	public String load() {
		try {
			carrier.setSidx("updDate");
			carrier.setSord("desc");
			executeDAO.find(entityClass, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GRID;
	}

	@Override
	public String save() {
		Assert.notNull(editInJb, "必须有是否技办编辑标示");
		if (abnoIds != null) {
			Assert.isTrue(abnoIds.size() == abnoNames.size());
			Assert.isTrue(abnoIds.size() == abnoStartTimes.size());
			Assert.isTrue(abnoIds.size() == abnoEndTimes.size());
		}
		if (abneIds != null) {
			Assert.isTrue(abneIds.size() == abneNames.size());
			Assert.isTrue(abneIds.size() == abneStartTimes.size());
			Assert.isTrue(abneIds.size() == abneEndTimes.size());
		}
		try {
			AbnormalO abnormal = assembly();
			abnormalService.save(abnormal);
			if (editInJb == 1) {//技办编辑异态信息
				this.model = abnormal;
				return MODEL;
			} else {
				if (taskId == null) {//值班长上报
					ParaDtl pd = paraDtlService.get(abnormal.getAbnType().getId());
					String discription = "异态：" + pd.getCodeDesc() + ", 时间："
							+ DateUtil.dateToString(abnormal.getStartTime(), DateUtil.FORMAT_DAYTIME) + "到"
							+ DateUtil.dateToString(abnormal.getEndTime(), DateUtil.FORMAT_DAYTIME);
					Person person = getCurUser();
					workFlowService.startProcessInstance(ProcessEnum.ACCD_REPORT.getDataSource(),
							abnormal.getId() + "", discription, person, "上报异态", person.getDept(), FunModule.ACCD);
				} else {//机房主任审核
					workFlowService.endTaskInstance(taskId, getCurUser().getName(), "机房主任审核");
				}
				return RIGHT;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return WRONG;
		}
	}

	private AbnormalO assembly() {
		AbnormalO abnormalO = getModel();
		//级联故障设备
		abnormalO.setTransType(new TransType(3L));
		abnormalO.setEmpName(this.getCurUser().getName());
		abnormalO.setUpdDate(this.getCurDate());
		abnormalService.assemblyAbos(abnormalO, abnoIds, abnoNames, abnoStartTimes, abnoEndTimes, accdFlags);
		abnormalService.assemblyAbes(abnormalO, abneIds, abneNames, abneStartTimes, abneEndTimes, faultFlags);
		return abnormalO;
	}

	private AbnormalService abnormalService;
	private ParaDtlService paraDtlService;

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	public List<Long> getAbnoIds() {
		return abnoIds;
	}

	public void setAbnoIds(List<Long> abnoIds) {
		this.abnoIds = abnoIds;
	}

	public List<String> getAbnoNames() {
		return abnoNames;
	}

	public void setAbnoNames(List<String> abnoNames) {
		this.abnoNames = abnoNames;
	}

	public List<Date> getAbnoStartTimes() {
		return abnoStartTimes;
	}

	public void setAbnoStartTimes(List<Date> abnoStartTimes) {
		this.abnoStartTimes = abnoStartTimes;
	}

	public List<Date> getAbnoEndTimes() {
		return abnoEndTimes;
	}

	public void setAbnoEndTimes(List<Date> abnoEndTimes) {
		this.abnoEndTimes = abnoEndTimes;
	}

	public List<Integer> getAccdFlags() {
		return accdFlags;
	}

	public void setAccdFlags(List<Integer> accdFlags) {
		this.accdFlags = accdFlags;
	}

	public List<Long> getAbneIds() {
		return abneIds;
	}

	public void setAbneIds(List<Long> abneIds) {
		this.abneIds = abneIds;
	}

	public List<String> getAbneNames() {
		return abneNames;
	}

	public void setAbneNames(List<String> abneNames) {
		this.abneNames = abneNames;
	}

	public List<Date> getAbneStartTimes() {
		return abneStartTimes;
	}

	public void setAbneStartTimes(List<Date> abneStartTimes) {
		this.abneStartTimes = abneStartTimes;
	}

	public List<Date> getAbneEndTimes() {
		return abneEndTimes;
	}

	public void setAbneEndTimes(List<Date> abneEndTimes) {
		this.abneEndTimes = abneEndTimes;
	}

	public List<Integer> getFaultFlags() {
		return faultFlags;
	}

	public void setFaultFlags(List<Integer> faultFlags) {
		this.faultFlags = faultFlags;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getEditInJb() {
		return editInJb;
	}

	public void setEditInJb(Long editInJb) {
		this.editInJb = editInJb;
	}
}
