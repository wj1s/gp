package gov.abrs.etms.service.report;

import gov.abrs.etms.model.rept.ZeroReptDtl;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ZeroReptDtlService extends CrudService<ZeroReptDtl> {

	public ZeroReptDtl getBySeq(Long seq) {
		List<ZeroReptDtl> list = dao.findBy("dtlSeq", seq);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

}
