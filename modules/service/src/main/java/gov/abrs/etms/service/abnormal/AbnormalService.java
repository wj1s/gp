package gov.abrs.etms.service.abnormal;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.AbnEquip;
import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.abnormal.AbnOperationA;
import gov.abrs.etms.model.abnormal.AbnOperationN;
import gov.abrs.etms.model.abnormal.Abnormal;
import gov.abrs.etms.model.abnormal.AbnormalB;
import gov.abrs.etms.model.abnormal.AbnormalO;
import gov.abrs.etms.model.abnormal.AccdDutyTime;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.AccdDuty;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.util.CrudService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AbnormalService extends CrudService<Abnormal> {
	@Override
	public void save(Abnormal abnormal) {
		for (AbnOperation abno : abnormal.getAbnOperations()) {
			abno.setAbnormal(abnormal);
		}
		dao.save(abnormal);
	}

	/**
	 * 拼装影响业务对象
	 * 	List<Long> abnoIds;//abnoid
	 * 	List<String> abnoNames;//影响业务名称
	 *	List<Date> abnoStartTimes;//影响业务开始时间
	 *	List<Date> abnoEndTimes;//影响业务结束时间
	 *	List<Integer> accdFlags;//是否停传
	 */
	public void assemblyAbos(Abnormal abnormal, List<Long> abnoIds, List<String> abnoNames, List<Date> abnoStartTimes,
			List<Date> abnoEndTimes, List<Integer> accdFlags) {
		//如果是更新操作，需要去除已经删除的影响业务详细信息
		if (abnormal.getId() != null) {
			List<AbnOperation> abnOperations = abnormal.getAbnOperations();
			if (abnoIds == null) {
				abnormal.getAbnOperations().clear();
			} else {
				for (int i = 0; i < abnOperations.size(); i++) {
					AbnOperation abno = abnOperations.get(i);
					if (!abnoIds.contains(abno.getId())) {
						abnormal.getAbnOperations().remove(abno);
						if (accdFlags != null && accdFlags.size() > 0) {
							for (int j = 0; j < accdFlags.size(); j++) {
								Integer accdFlag = accdFlags.get(j);
								if (accdFlag > i) {
									accdFlags.set(j, accdFlag - 1);
								}
							}
						}
						i = i - 1;
					}
				}
			}
		}
		List<AbnOperation> abnos = abnormal.getAbnOperations();
		if (abnoIds != null) {
			//级联影响业务
			for (int i = 0; i < abnoIds.size(); i++) {
				Long abnoId = abnoIds.get(i);
				boolean isaccd = false;
				if (accdFlags != null && accdFlags.contains(i)) {
					isaccd = true;
				}
				if (abnoId == 0) {
					//如果是手动新增的，则新建对象关联
					Operation operation = operationService.getByName(abnoNames.get(i));
					AbnOperation abno = isaccd ? new AbnOperationA() : new AbnOperationN();
					abno.setOperation(operation);
					abno.setStartTime(abnoStartTimes.get(i));
					abno.setEndTime(abnoEndTimes.get(i));
					abno.setAbnormal(abnormal);
					abno.setSortby(i);
					abnos.add(i, abno);
				} else {
					//如果已经在异态中，只更新hibernate缓存数据即可，保存异态时会级联保存
					AbnOperation abno = abnOperationService.get(abnoId);
					boolean oldAccdFlag = abno instanceof AbnOperationA;
					if ((isaccd && oldAccdFlag) || (!isaccd && !oldAccdFlag)) {
						abno.setOperation(operationService.getByName(abnoNames.get(i)));
						abno.setStartTime(abnoStartTimes.get(i));
						abno.setEndTime(abnoEndTimes.get(i));
						abno.setSortby(i);
					} else {
						//删除旧的
						abnormal.getAbnOperations().remove(abno);
						//增加一个新都
						AbnOperation abnoNew = isaccd ? new AbnOperationA() : new AbnOperationN();
						abnoNew.setOperation(operationService.getByName(abnoNames.get(i)));
						abnoNew.setStartTime(abnoStartTimes.get(i));
						abnoNew.setEndTime(abnoEndTimes.get(i));
						abnoNew.setAbnormal(abnormal);
						abnoNew.setSortby(i);
						abnos.add(i, abnoNew);
					}
				}
			}
		}
	}

	public void assemblyAbes(AbnormalO abnormalO, List<Long> abneIds, List<String> abneNames,
			List<Date> abneStartTimes, List<Date> abneEndTimes, List<Integer> faultFlags) {
		//如果是更新操作，需要去除已经删除的影响业务详细信息
		if (abnormalO.getId() != null) {
			List<AbnEquip> abnEquips = abnormalO.getAbnEquips();
			if (abneIds == null) {
				abnormalO.getAbnEquips().clear();
			} else {
				//必须反着循环
				for (int i = 0; i < abnEquips.size(); i++) {
					AbnEquip abnoe = abnEquips.get(i);
					if (!abneIds.contains(abnoe.getId())) {
						abnormalO.getAbnEquips().remove(abnoe);
						if (faultFlags != null && faultFlags.size() > 0) {
							for (int j = 0; j < faultFlags.size(); j++) {
								Integer faultFlag = faultFlags.get(j);
								if (faultFlag > i) {
									faultFlags.set(j, faultFlag - 1);
								}
							}
						}
						i = i - 1;
					}
				}
			}
		}
		List<AbnEquip> abnes = abnormalO.getAbnEquips();
		if (abneIds != null) {
			//级联影响业务
			for (int i = 0; i < abneIds.size(); i++) {
				Long abneId = abneIds.get(i);
				boolean isfault = false;
				if (faultFlags != null && faultFlags.contains(i)) {
					isfault = true;
				}
				Equip equip = equipService.getByName(abneNames.get(i));
				if (abneId == 0) {
					//如果是手动新增的，则新建对象关联
					AbnEquip abne = new AbnEquip();
					abne.setEquip(equip);
					abne.setStartTime(abneStartTimes.get(i));
					abne.setEndTime(abneEndTimes.get(i));
					abne.setAbnormalO(abnormalO);
					abne.setSortby(i);
					abnes.add(i, abne);
					if (isfault) {
						createAbnormalBByAbnEquip(abne);
					}
				} else {
					//如果已经在异态中，只更新hibernate缓存数据即可，保存异态时会级联保存
					AbnEquip abne = abnEquipService.get(abneId);
					abne.setEquip(equip);
					abne.setStartTime(abneStartTimes.get(i));
					abne.setEndTime(abneEndTimes.get(i));
					abne.setSortby(i);
					boolean oldfaultFlag = abne.getAbnormalB() != null;
					if (!oldfaultFlag && isfault) {//旧的不是新的是，新增新的
						createAbnormalBByAbnEquip(abne);
					} else if (oldfaultFlag && !isfault) {//旧的是新的不是，删除旧的
						AbnormalB abnormalB = abne.getAbnormalB();
						abne.setAbnormalB(null);
						this.dao.delete(abnormalB);
					} else if (oldfaultFlag && isfault) {//新旧都是，更新旧的
						updateAbnormalBByAbnEquip(abne);
					}
				}
			}
		}
	}

	private void createAbnormalBByAbnEquip(AbnEquip abne) {
		AbnormalB abnb = new AbnormalB();
		abnb.setEquipB(abne.getEquip());
		/*有问题*/
		abnb.setTransType(abne.getAbnormalO().getTransType());
		abnb.setStartTime(abne.getStartTime());
		abnb.setEndTime(abne.getEndTime());
		abnb.setDesc(abne.getAbnormalO().getDesc());
		abnb.setReason(abne.getAbnormalO().getReason());
		abnb.setProcessStep(abne.getAbnormalO().getProcessStep());
		abnb.setAbnEquip(abne);
		abnb.setAbnType(abne.getAbnormalO().getAbnType());
		abnb.setEmpName(abne.getAbnormalO().getEmpName());
		abnb.setUpdDate(abne.getAbnormalO().getUpdDate());
		abne.setAbnormalB(abnb);
	}

	private void updateAbnormalBByAbnEquip(AbnEquip abne) {
		AbnormalB abnb = abne.getAbnormalB();
		abnb.setEquipB(abne.getEquip());
		abnb.setStartTime(abne.getStartTime());
		abnb.setEndTime(abne.getEndTime());
		abnb.setDesc("无");
		abnb.setReason("异态引起");
		abnb.setProcessStep("无");
	}

	//目前的算法有个约束，就是abnOperationIds中必须是按照abnormal的abnOperationIds中的顺序存储，并且相同的都在一起11122333
	public void assemblyAbts(Abnormal abnormal, List<Long> abnOperationIds, List<Long> dutyTimeIds,
			List<String> accdDutys, List<String> dutyTimes) {
		Assert.notNull(abnormal.getId());
		//去除已经删除的定性信息
		if (dutyTimeIds == null) {
			for (AbnOperationA abnOperationA : abnormal.getAbnOperationAs()) {
				abnOperationA.getAccdDutyTimes().clear();
			}
		} else {
			for (AbnOperationA abnoA : abnormal.getAbnOperationAs()) {
				for (int j = 0; j < abnoA.getAccdDutyTimes().size(); j++) {
					AccdDutyTime accdDutyTime = abnoA.getAccdDutyTimes().get(j);
					if (!dutyTimeIds.contains(accdDutyTime.getId())) {
						abnoA.getAccdDutyTimes().remove(accdDutyTime);
						j = j - 1;
					}
				}
			}
		}
		//新增和更新操作
		if (dutyTimeIds != null) {
			int count = 0;//aop指针
			for (AbnOperationA abnOperationA : abnormal.getAbnOperationAs()) {
				for (int i = 0; i < abnOperationIds.size(); i++) {
					Long id = abnOperationIds.get(i);
					//属于本业务的定性信息
					if (abnOperationA.getId().equals(id)) {
						Long dutyTimeId = dutyTimeIds.get(i);
						AccdDuty accdDuty = (AccdDuty) paraDtlService.get(Long.parseLong(accdDutys.get(i)));
						Long dutyTime = DateUtil.getTimeSecond(dutyTimes.get(i));
						int sortBy = i - count;
						if (dutyTimeId == 0) {
							//如果是手动新增的，则新建对象关联
							AccdDutyTime accdDutyTime = new AccdDutyTime(abnOperationA, accdDuty, dutyTime.intValue());
							accdDutyTime.setSortby(sortBy);
							abnOperationA.getAccdDutyTimes().add(sortBy, accdDutyTime);
						} else {
							//如果已经存在中，只更新hibernate缓存数据即可，保存异态时会级联保存
							AccdDutyTime accdDutyTime = accdDutyTimeService.get(dutyTimeId);
							accdDutyTime.setAbnOperationA(abnOperationA);
							accdDutyTime.setAccdDuty(accdDuty);
							accdDutyTime.setDutyTime(dutyTime.intValue());
							accdDutyTime.setSortby(sortBy);
						}
					} else {
						if (count < i) {
							count = i;
							break;
						}
					}
				}
			}
		}
	}

	//根据开始时间和结束时间查找事故（带事故编号，需要上报的）的集合 biandi add
	public List<Abnormal> get(Date startTime, Date endTime) {
		String hql = "from Abnormal where accdCode is not null ";
		if (startTime != null) {
			hql += " and startTime>=:startTime ";
		}
		if (endTime != null) {
			hql += " and startTime<=:endTime";
		}
		Map<String, Object> values = new HashMap<String, Object>();
		if (startTime != null) {
			values.put("startTime", startTime);
		}
		if (endTime != null) {
			values.put("endTime", endTime);
		}
		return this.dao.find(hql, values);
	}

	//根据reptTime返回事故能否上报的信息 biandi add
	public String getCanReportAbnInfo(String reptTime) {
		Date startTime = DateUtil.getFirstDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM"));
		Date endTime = DateUtil.getLastDayOfMonth(DateUtil.stringToDate(reptTime, "yyyyMM"));
		return getCanReportAbnInfo(startTime, endTime);
	}

	//查询时间段内待上报的事故信息
	public String getCanReportAbnInfo(Date startTime, Date endTime) {
		StringBuffer abnMessage = new StringBuffer();
		List<Abnormal> abnormals = get(startTime, endTime);
		if (abnormals != null && abnormals.size() != 0) {
			int num = 0;
			for (Abnormal abnormal : abnormals) {
				ProcessInstance pi = workFlowService.getProcessInstance(ProcessEnum.ACCD_REPORT.getDataSource(),
						abnormal.getId().toString());
				if (!pi.hasEnded()) {
					TaskInstance ti = workFlowService.findActiveTaskInstance(pi);
					if (ti != null) {
						num++;
						abnMessage.append("<" + abnormal.getAccdCode() + ">号事故处于" + ti.getName() + "节点。");
					} else {
						throw new RuntimeException("未结束的事故流程没有激活的节点，这是不可能的！");
					}
				}
			}
			return abnMessage.toString().equals("") ? "可以上报!" : "待上报的事故有" + "(<strong><font color='red'>" + num
					+ "</font><strong>)" + "条是(" + abnMessage + ")";
		} else {
			return "可以上报!";
		}
	}

	//查找这段时间内的所有事故
	public List<Abnormal> getAll(Date startDate, Date endDate) {
		String hql = "from Abnormal where startTime <=:endDate and startTime >=:startDate";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("startDate", startDate);
		values.put("endDate", DateUtil.addDay(endDate, 1));
		return this.dao.find(hql, values);
	}

	public Abnormal get(String accdCode) {
		List<Abnormal> list = dao.findBy("accdCode", accdCode);
		return list.size() == 0 ? null : list.get(0);
	}

	public void get(Carrier<Abnormal> carrier, Date startDate, Date endDate) {
		String hql = "from Abnormal where accdCode is not null ";
		hql += " and startTime>=:startTime ";
		hql += " and startTime<=:endTime";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("startTime", startDate);
		values.put("endTime", endDate);
		this.dao.find(carrier, hql, values);
	}

	private OperationService operationService;
	private EquipService equipService;
	private AbnOperationService abnOperationService;
	private AbnEquipService abnEquipService;
	private ParaDtlService paraDtlService;
	private AccdDutyTimeService accdDutyTimeService;
	private WorkFlowService workFlowService;

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@Autowired
	public void setAbnOperationService(AbnOperationService abnOperationService) {
		this.abnOperationService = abnOperationService;
	}

	@Autowired
	public void setEquipService(EquipService equipService) {
		this.equipService = equipService;
	}

	@Autowired
	public void setAbnEquipService(AbnEquipService abnEquipService) {
		this.abnEquipService = abnEquipService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setAccdDutyTimeService(AccdDutyTimeService accdDutyTimeService) {
		this.accdDutyTimeService = accdDutyTimeService;
	}

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

}