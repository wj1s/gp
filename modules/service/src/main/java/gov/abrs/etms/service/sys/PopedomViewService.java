package gov.abrs.etms.service.sys;

import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.PopedomView;
import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.service.util.CrudService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class PopedomViewService extends CrudService<PopedomView> {
	//根据person判断
	public PopedomView findAllByPerson(Person person) {
		PopedomView popedom = this.findById("-1");// 查询根节点
		checkWithPerson(person, popedom);
		return popedom;
	}

	public PopedomView findById(String id) {
		String hql = "from PopedomView c where c.popeId='" + id + "'";
		List<PopedomView> list = new ArrayList();
		try {
			list = this.dao.find(hql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	//根据person判断
	private void checkWithPerson(Person person, PopedomView popedom) {
		if (popedom.getPopedomViews() != null && popedom.getPopedomViews().size() != 0) {
			for (PopedomView pop : popedom.getPopedomViews()) {
				checkWithPerson(person, pop);
			}
		}
		if (hasPerToTheView(person, popedom)) {
			popedom.setPersonHas(true);
		} else {
			popedom.setPersonHas(false);
		}
		return;
	}

	//根据person判断
	private boolean hasPerToTheView(Person person, PopedomView popedom) {
		if (popedom.getPopeId().equals("-1")) {
			return true;
		}
		for (Role role : popedom.getRoles()) {
			for (Role comRole : person.getRoles()) {
				if (role.equals(comRole)) {
					return true;
				}
			}
		}
		return false;
	}

	//根据角色判断
	public PopedomView findAllByRole(Role role) {
		PopedomView popedom = this.findById("-1");// 查询根节点
		checkWithRole(role, popedom);
		return popedom;
	}

	//根据角色判断
	private void checkWithRole(Role role, PopedomView popedom) {
		if (popedom.getPopedomViews() != null) {
			for (PopedomView pop : popedom.getPopedomViews()) {
				checkWithRole(role, pop);
			}
		}
		if (hasPerToTheView(role, popedom)) {
			popedom.setPersonHas(true);
		} else {
			popedom.setPersonHas(false);
		}
		return;
	}

	//根据角色判断
	private boolean hasPerToTheView(Role role, PopedomView popedom) {
		if (popedom.getPopeId().equals("-1")) {
			return true;
		}
		List<Role> roleList = popedom.getRoles();
		if (roleList.contains(role)) {
			return true;
		}
		return false;
	}

	public List<PopedomView> getNeedCrudPop(List<Role> roles, String url) {
		String hql = "from PopedomView p where p.url =:url)";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("url", url);
		PopedomView popedomView1 = (PopedomView) dao.find(hql, values).get(0);
		List<PopedomView> list = Lists.newArrayList();
		for (PopedomView popedomView : popedomView1.getPopedomViews()) {
			for (Role role : roles) {
				if (popedomView.getRoles().contains(role)) {
					list.add(popedomView);
					break;
				}
			}
		}
		return list;
	}
}
