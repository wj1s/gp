package gov.abrs.etms.service.abnormal;

import gov.abrs.etms.model.abnormal.AbnEquip;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class AbnEquipService extends CrudService<AbnEquip> {}
