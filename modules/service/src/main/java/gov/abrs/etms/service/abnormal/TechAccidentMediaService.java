package gov.abrs.etms.service.abnormal;

import gov.abrs.etms.model.abnormal.TechAccidentMedia;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description: 描述该类的功能
 * @author zhangzx
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TechAccidentMediaService extends CrudService<TechAccidentMedia> {

}
