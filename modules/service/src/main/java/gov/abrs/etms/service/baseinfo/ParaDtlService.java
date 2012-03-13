package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ParaDtlService extends CrudService<ParaDtl> {

	@SuppressWarnings("unchecked")
	public List<ParaDtl> get(Class entityClass) {
		String hql = "from " + entityClass.getSimpleName() + " order by sortby";
		return this.dao.find(hql);
	}

	//根据类型和paraCode查找某一个参数
	@SuppressWarnings("unchecked")
	public ParaDtl getByCode(Class entityClass, String paraCode) {
		String hql = "from " + entityClass.getSimpleName() + " where paraCode =? ";
		return this.dao.findUnique(hql, paraCode);
	}

	//根据类型和paraCode查找某一个参数
	@SuppressWarnings("unchecked")
	public ParaDtl getByDesc(Class entityClass, String codeDesc) {
		String hql = "from " + entityClass.getSimpleName() + " where codeDesc =? ";
		return this.dao.findUnique(hql, codeDesc);
	}
}
