package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Post;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PostService extends CrudService<Post> {

	public List<Post> getAllBySort() {
		String hql = "from Post order by sort";
		return this.dao.find(hql);
	}
}
