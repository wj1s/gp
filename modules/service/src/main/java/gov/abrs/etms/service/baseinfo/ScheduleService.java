package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.model.abnormal.AbnOperationA;
import gov.abrs.etms.model.baseinfo.Channel;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Schedule;
import gov.abrs.etms.service.util.CrudService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ScheduleService extends CrudService<Schedule> {
	public List<AbnOperation> getAbnOperation(Equip equip, Date startTime, Date endTime, String transType) {
		List<AbnOperation> result = new ArrayList<AbnOperation>();
		List<Channel> channels = equip.getChannels();
		for (Channel channel : channels) {
			for (int i = 0; i < channel.getSchedules().size(); i++) {
				Schedule schedule = channel.getSchedules().get(i);
				//首先判断时间是否有交集
				if ((schedule.getEndDate() == null || startTime.before(schedule.getEndDate()))
						&& endTime.after(schedule.getStartDate())) {
					Operation operation = schedule.getOperation();
					//再判断传输类型
					if ((operation.getTransType().getId() + "").equals(transType)) {
						Date abnStartTime = startTime.before(schedule.getStartDate()) ? schedule.getStartDate()
								: startTime;
						Date abnEndTime = schedule.getEndDate() == null || endTime.before(schedule.getEndDate()) ? endTime
								: schedule.getEndDate();
						//默认引起停传
						result.add(new AbnOperationA(operation, abnStartTime, abnEndTime, i));
					}
				}
			}
		}
		return result;
	}

}
