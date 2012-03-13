package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Schedule;
import gov.abrs.etms.service.baseinfo.ChannelService;
import gov.abrs.etms.service.baseinfo.EquipService;
import gov.abrs.etms.service.baseinfo.OperationService;
import gov.abrs.etms.service.baseinfo.ScheduleService;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleAction extends CrudAction<Schedule> {

	private static final long serialVersionUID = -6411018432446088211L;
	private String equipName;//根据设备查询运行图
	private Date startTime;//开始时间
	private Date endTime;//结束时间

	private String startDate;
	private String endDate;

	private String operationList;
	private String channelList;
	private String transType;

	@Override
	protected void beforeUpdate(Schedule model) {
		model.setChannel(null);
		model.setOperation(null);
	}

	@Override
	protected void preSave(Schedule model) {
		Date start = DateUtil.createDate(startDate);
		Date end = DateUtil.createDate(endDate);
		model.setStartDate(start);
		model.setEndDate(end);
	}

	public String show() throws Exception {
		try {
			List<Operation> operations = this.operationService.getAll();
			operationList = this.assemblyJsonArray(operations, "name");
			List<Channel> channels = this.channelService.getAll();
			channelList = this.assemblyJsonArray(channels, "name");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String getAbnOperationsByEquipAjax() throws UnsupportedEncodingException {
		Equip equip = equipService.getByName(equipName);
		list = scheduleService.getAbnOperation(equip, startTime, endTime, transType);
		return NORMAL;

	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getOperationList() {
		return operationList;
	}

	public void setOperationList(String operationList) {
		this.operationList = operationList;
	}

	public String getChannelList() {
		return channelList;
	}

	public void setChannelList(String channelList) {
		this.channelList = channelList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	private ScheduleService scheduleService;
	private EquipService equipService;
	private ChannelService channelService;
	private OperationService operationService;

	@Autowired
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	@Autowired
	public void setOperationService(OperationService operationService) {
		this.operationService = operationService;
	}

	@Autowired
	public void setScheduleService(ScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	@Autowired
	public void setEquipService(EquipService equipService) {
		this.equipService = equipService;
	}

}
