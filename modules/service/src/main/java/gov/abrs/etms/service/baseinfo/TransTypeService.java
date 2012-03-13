package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TransTypeService extends CrudService<TransType> {

	public List<TransType> getByPopedom() {
		String hql = "from TransType";
		return this.dao.find(hql);
	}

	//根据paraCode查找传输类型（DEMO用）
	public TransType get(String paraCode) {
		return this.dao.findUniqueBy("paraCode", paraCode);
	}
}
