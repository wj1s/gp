/**
 * 
 */
package gov.abrs.etms.service.repair;

import gov.abrs.etms.model.repair.RecordItem;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 郭翔
 *
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class RecordItemService extends CrudService<RecordItem> {

}
