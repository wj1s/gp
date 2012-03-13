package gov.abrs.etms.service.abnormal;

import gov.abrs.etms.model.abnormal.AbnOperation;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AbnOperationService extends CrudService<AbnOperation> {}
