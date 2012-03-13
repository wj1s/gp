package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.EquipModel;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EquipModelService extends CrudService<EquipModel> {

}
