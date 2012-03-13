package gov.abrs.etms.service.duty;

import gov.abrs.etms.model.duty.DutySchedule;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DutyScheduleService extends CrudService<DutySchedule> {}
