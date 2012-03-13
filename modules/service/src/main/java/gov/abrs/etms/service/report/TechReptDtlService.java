package gov.abrs.etms.service.report;

import gov.abrs.etms.model.rept.ReptTechDtl;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TechReptDtlService extends CrudService<ReptTechDtl> {
	public ReptTechDtl findBySeq(String seq) {
		String hql = "from ReptTechDtl c where c.seq = :reptSeq ";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("reptSeq", seq);
		List<ReptTechDtl> list = this.dao.find(hql, values);
		if (list.isEmpty()) {
			return null;
		} else {
			return list.get(0);
		}
	}
}
