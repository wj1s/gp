package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class OperationService extends CrudService<Operation> {
	@Override
	public void delete(Long id) {
		Operation op = dao.get(id);
		if (op.getAbnOperations().size() > 0) {
			throw new CanNotDeleteException("业务发生过异态,有级联的异态信息，不能删除，请清除对应异态信息后再进行删除操作!");
		} else if (op.getDutyWarnings().size() > 0) {
			throw new CanNotDeleteException("业务在值班记录中有关联告警信息，不能删除，请清除对应告警信息后再进行删除操作!");
		} else if (op.getBroadByTimes().size() > 0) {
			throw new CanNotDeleteException("业务在值班记录中有关联代播信息，不能删除，请清除对应代播信息后再进行删除操作!");
		} else if (op.getOpTimes().size() > 0) {
			throw new CanNotDeleteException("业务有对应的传输时间，不能删除!");
		} else {
			dao.delete(id);
		}
	}

	//根据传输类型查找业务
	public List<Operation> get(TransType transType) {
		return this.dao.findBy("transType", transType);
	}

	//根据开始时间和结束时间找
	public List<Operation> get(Date startDate, Date endDate) {
		String hql = "from Operation where startDate <=:endDate and (endDate >=:startDate or endDate is null)";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("startDate", startDate);
		values.put("endDate", DateUtil.addDay(endDate, 1));
		return this.dao.find(hql, values);
	}

	//对应页面上的表格删除
	public void delete(Carrier<Operation> carrier) throws Exception {
		JSONArray array = JSONArray.fromObject(carrier.getDelIds());
		for (Object object : array) {
			Long delId = ((JSONObject) object).getLong("id");
			this.delete(delId);
		}
	}

	public <X> List<X> getByNameLike(final Class entityClass, final String nameLike, String transType) {
		String hql = "from " + entityClass.getSimpleName() + " c where c.transType.id=" + transType
				+ " and c.name like '%" + nameLike + "%'";
		return dao.find(hql);
	}
}
