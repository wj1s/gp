package gov.abrs.etms.action.abnormal;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnormalF;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.abnormal.AbnormalService;
import gov.abrs.etms.service.baseinfo.EquipService;

import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Results({ @Result(name = "success", type = "redirect", location = "abnormal.action"),
		@Result(name = "backToTop", type = "redirect", location = "../index/index!toIndex.action") })
public class AbnormalFAction extends GridAction<AbnormalF> {
	private static final long serialVersionUID = 3825432269846307373L;
	private String equipName;//故障类型的设备名称
	//影响业务详细信息
	private List<Long> abnoIds;//abnoid
	private List<String> abnoNames;//影响业务名称
	private List<Date> abnoStartTimes;//影响业务开始时间
	private List<Date> abnoEndTimes;//影响业务结束时间
	private List<Integer> accdFlags;//是否停传
	private Long taskId;
	private Long editInJb;//是否是技术办审核时的异态编辑，如果是1，保存时只保存，返回JSON，否则保存时推进流程，返回成功
	private TransType transType;

	public TransType getTransType() {
		return transType;
	}

	public void setTransType(TransType transType) {
		this.transType = transType;
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
		Assert.notNull(equipName, "设备不能为空");
		Assert.notNull(editInJb, "必须有是否技办编辑标示");
		if (abnoIds != null) {
			Assert.isTrue(abnoIds.size() == abnoNames.size());
			Assert.isTrue(abnoIds.size() == abnoStartTimes.size());
			Assert.isTrue(abnoIds.size() == abnoEndTimes.size());
		}
		try {
			Person person = getCurUser();
			AbnormalF abnormal = assembly();
			abnormalService.save(abnormal);
			if (editInJb == 1) {//技办编辑异态信息
				this.model = abnormal;
				return MODEL;
			} else {
				if (taskId == null) {//值班长上报
					String discription = "设备故障：" + abnormal.getEquipF().getName() + ", 时间："
							+ DateUtil.dateToString(abnormal.getStartTime(), DateUtil.FORMAT_DAYTIME) + "到"
							+ DateUtil.dateToString(abnormal.getEndTime(), DateUtil.FORMAT_DAYTIME);
					workFlowService.startProcessInstance(ProcessEnum.ACCD_REPORT.getDataSource(),
							abnormal.getId() + "", discription, person, "上报故障", person.getDept(), FunModule.ACCD);
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

	private AbnormalF assembly() {
		AbnormalF abnormalF = getModel();
		//级联故障设备
		Equip equip = equipService.getByName(equipName);
		abnormalF.setEquipF(equip);
		abnormalF.setTransType(transType);
		abnormalF.setEmpName(this.getCurUser().getName());
		abnormalF.setUpdDate(this.getCurDate());
		abnormalService.assemblyAbos(abnormalF, abnoIds, abnoNames, abnoStartTimes, abnoEndTimes, accdFlags);
		return abnormalF;
	}

	private EquipService equipService;
	private AbnormalService abnormalService;

	@Autowired
	public void setEquipService(EquipService equipService) {
		this.equipService = equipService;
	}

	@Autowired
	public void setAbnormalService(AbnormalService abnormalService) {
		this.abnormalService = abnormalService;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
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
