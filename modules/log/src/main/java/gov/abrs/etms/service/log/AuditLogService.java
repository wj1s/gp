package gov.abrs.etms.service.log;

import gov.abrs.etms.model.log.AuditLog;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AuditLogService extends CrudService<AuditLog> {

}
