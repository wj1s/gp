package gov.abrs.etms.action.common;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.PersonService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

public class PersonExAction extends GridAction<Person> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6723574607679791925L;

	/*人员变更传入的参数*/
	private String ids;
	private String names;
	/*人员变更传出数据*/
	private List<Person> persons;

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	private DeptService deptService;
	private PersonService personService;

	@Autowired
	public DeptService getDeptService() {
		return deptService;
	}

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	/*查询出已经使用的人员信息*/
	public String getUserUsedAjax() {
		if (ids != null) {
			persons = personService.getPersons(ids);
			int i = 0;
			for (Person person : persons) {
				if (i == 0) {
					names = person.getName();
				} else {
					names = names + "," + person.getName();
				}
				i++;
			}
		}
		return "show";
	}

	/*查询出已经使用的人员信息*/
	public String getUserIdsAjax() {
		if (names != null) {
			if (names.endsWith(",")) {
				names = names.substring(0, names.length() - 1);
			}
			String[] nameArr = names.split(",");
			for (int i = 0; i < nameArr.length; i++) {
				if (i == 0) {
					names = "'" + nameArr[0] + "'";
				} else {
					names = names + ",'" + nameArr[i] + "'";
				}
			}
			persons = personService.getPersonsByNames(names);
			int i = 0;
			for (Person person : persons) {
				if (i == 0) {
					ids = person.getId() + "";
					names = person.getName();
				} else {
					ids = ids + "," + person.getId();
					names = names + "," + person.getName();
				}
				i++;
			}
		}
		JSONObject jsonUser = new JSONObject();
		jsonUser.put("ids", ids);
		jsonUser.put("names", names);
		json = jsonUser.toString();
		return EASY;
	}

	public String getUserTree() {
		List<Dept> depts = deptService.getAll();
		JSONArray jsonArray = new JSONArray();
		String[] nameIds = {};
		if (this.getIds() != null) {
			nameIds = this.getIds().split(",");
		}
		for (Dept dept : depts) {
			JSONObject jsonDept = new JSONObject();
			jsonDept.put("data", dept.getName());
			List<Person> people = dept.getPersons();
			JSONArray arrayPeople = new JSONArray();
			for (Person person : people) {
				boolean flagPerson = false;
				for (int i = 0; i < nameIds.length; i++) {
					if (nameIds[i].equals(person.getId() + "")) {
						flagPerson = true;
					}
				}
				if (flagPerson == false) {
					JSONObject jsonPerson = new JSONObject();
					jsonPerson.put("data", person.getName());
					JSONObject temp = new JSONObject();
					temp.put("empId", person.getId());
					jsonPerson.put("attr", temp);
					arrayPeople.add(jsonPerson);
				}

			}
			jsonDept.put("children", arrayPeople);
			jsonArray.add(jsonDept);
		}
		json = jsonArray.toString();
		return EASY;
	}
}
