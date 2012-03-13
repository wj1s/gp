package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.sys.RoleService;
import gov.abrs.etms.service.util.CrudService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PersonService extends CrudService<Person> {
	private RoleService roleService;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Person get(String loginName) {
		List<Person> people = this.dao.findBy("loginName", loginName);
		if (people != null && people.size() != 0) {
			if (people.size() > 1) {
				throw new RuntimeException("不应该有登录名相同的用户!");
			} else {
				return people.get(0);
			}
		}
		return null;
	}

	/*获取多个names的人员names='1','2','3'*/
	public List<Person> getPersonsByNames(String names) {
		Assert.notNull(names);
		List<Person> persons = new ArrayList<Person>();
		if (!"".equals(names)) {
			String hql = "from Person where  enable='1' and name in (" + names + ") order by name asc";
			persons = this.dao.find(hql);
		}
		return persons;

	}

	/*获取多个id的人员personIds='1','2','3'*/
	public List<Person> getPersons(String personIds) {
		Assert.notNull(personIds);
		List<Person> persons = new ArrayList<Person>();
		if (!"".equals(personIds)) {
			String hql = "from Person where enable='1' and id in (" + personIds + ") order by name asc";
			persons = this.dao.find(hql);
		}
		return persons;

	}

	//删除人员时，有检修相关的不可删除
	@Override
	public void delete(Long id) {
		Person person = dao.get(id);
		if (person.getRecordItems().size() > 0) {
			throw new CanNotDeleteException("业务发生过异态,有级联的异态信息，不能删除，请清除对应异态信息后再进行删除操作!");
		} else {
			dao.delete(id);
		}
	}

	@Override
	public List<Person> getByNameLike(String q) {
		String hql = "from Person where name like :nameLike or loginName like :nameLike";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("nameLike", "%" + q + "%");
		return dao.find(hql, values);
	}

	//	通过AD获取人员数据
	public Person getPersonByAd(final String propertyName, final Object value) {
		return this.dao.findUniqueBy(propertyName, value);
	}

	public void savePersonInitRolePer(Person person) {
		// 初始化数据权限
		savePersonRolePer(person);
		// 初始化角色
		save(person);
	}

	/*
	 * 
	 */
	public void updatePersonAndRefreshPer(Person person) {
		// 重置数据权限
		//	savePersonRolePer(person);

		save(person);

	}

	public void savePersonRolePer(Person person) {

		//	//配置默认的功能权限
		if (person.getId() == null) {
			List<DeptPer> deptPers = new ArrayList<DeptPer>();
			for (FunModule fun : FunModule.values()) {
				DeptPer deptPer = new DeptPer();
				deptPer.setDept(person.getDept());
				deptPer.setPerson(person);
				deptPer.setFunModuleKey(fun.getKey());
				deptPers.add(deptPer);
			}
			if (person.getDeptPers() != null && person.getDeptPers().size() != 0) {
				person.getDeptPers().clear();
				person.getDeptPers().addAll(deptPers);
			} else {
				if (person.getDeptPers() == null) {
					person.setDeptPers(deptPers);
				} else {
					person.getDeptPers().addAll(deptPers);
				}
			}
		}
		//配个默认的User角色
		if (person.getRoles() == null || person.getRoles().size() == 0) {
			person.setRoles(Lists.newArrayList(roleService.getByName("USER")));
		}
	}

	//通过SQL取出PERSON的CODE的最大值加1，并且person_code

	public String getMaxPersonCode(String sql) {
		return this.dao.findObjectUnique(sql).toString();
	}

	@Autowired
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

}
