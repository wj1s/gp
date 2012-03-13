package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Group;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class GroupService extends CrudService<Group> {

	//删除班组时，人员不会被级联删除
	@Override
	public void delete(Long id) {
		Group group = this.dao.get(id);
		if (group.getDuties().size() > 0 || group.getRuleItems().size() > 0 || group.getRecords().size() > 0) {
			throw new CanNotDeleteException("业务发生过异态,有级联的异态信息，不能删除，请清除对应异态信息后再进行删除操作!");
		} else {
			for (Person person : group.getPeople()) {
				person.setGroup(null);
			}
			group.getPeople().clear();
			this.dao.delete(group);
		}
	}
}
