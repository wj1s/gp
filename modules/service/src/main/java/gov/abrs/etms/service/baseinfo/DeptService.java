package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Device;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class DeptService extends CrudService<Dept> {
	private StationService stationService;

	//删除部门时设备和人员不会被级联删除
	@Override
	public void delete(Long id) {
		String queryString = "from Person c where c.dept.id= '" + id + "' ";
		Dept dept = dao.get(id);
		List<Person> people = this.dao.find(queryString);
		if (people.size() != 0) {
			for (int i = 0; i < people.size(); i++) {
				people.get(i).setDept(null);
			}
		}
		if (dept.getCycles().size() > 0) {
			throw new CanNotDeleteException("业务发生过异态,有级联的异态信息，不能删除，请清除对应异态信息后再进行删除操作!");
		} else {
			for (Device device : dept.getDevices()) {
				device.setDept(null);
			}
			for (Person person : dept.getPersons()) {
				person.setDept(null);
				person.setGroup(null);
			}

			dao.delete(id);
		}
	}

	public List<Dept> get(DeptType deptType) {
		String hql = "from Dept where deptType=:deptType";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("deptType", deptType);
		return this.dao.find(hql, values);
	}

	//通过ADID获取部门数据
	public Dept getDeptByAd(final String propertyName, final Object value) {
		return this.dao.findUniqueBy(propertyName, value);
	}

	//通过SQL取出DEPT的CODE的最大值，并且和台站code组装成为dept_code

	public String getMaxDeptCode(String sql) {

		return stationService.getStation().getCode() + ((Integer) this.dao.findObjectUnique(sql)).toString();
	}

	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

}
