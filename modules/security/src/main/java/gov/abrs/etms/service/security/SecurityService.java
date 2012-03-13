package gov.abrs.etms.service.security;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.service.baseinfo.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class SecurityService {
	private PersonService personService;

	//查找当前登录人员
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Person getCurUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Person person = personService.get(auth.getName());
		return person;
	}

	//查找当前登录人的角色列表
	public ArrayList<String> getCurActorIds() {
		Person person = this.getCurUser();
		ArrayList<String> myactorIds = new ArrayList<String>();
		for (Role role : person.getRoles()) {
			myactorIds.add(role.getDesc());
		}
		return myactorIds;
	}

	//查找当前登录人的某一业务的部门数据权限
	public List<Dept> getCurDeptPers(FunModule funModule) {
		List<DeptPer> deptPers = getCurUser().getDeptPers();
		List<Dept> depts = new ArrayList<Dept>();
		for (DeptPer deptPer : deptPers) {
			if (funModule.getKey().equals(deptPer.getFunModuleKey())) {
				depts.add(deptPer.getDept());
			}
		}
		return depts;
	}

	//查找当前登录人的某一业务的系统访问呢权限
	public List<TechSystem> getCurTechSystems(FunModule funModule) {
		List<TechSystem> tsList = new ArrayList<TechSystem>();
		for (TransType transType : this.getCurTransTypes(funModule)) {
			tsList.addAll(transType.getTechSystems());
		}
		return tsList;
	}

	//查找当前登录人的某一业务的业务类型数据权限
	public List<TransType> getCurTransTypes(FunModule funModule) {
		List<TransType> ttList = new ArrayList<TransType>();
		Set<TransType> ttSet = new TreeSet<TransType>();
		for (Dept curDept : this.getCurDeptPers(funModule)) {
			ttSet.addAll(curDept.getTransTypes());
		}
		ttList.addAll(ttSet);
		return ttList;
	}

	public List<DeptPer> getCurDeptPers() {
		return getCurUser().getDeptPers();
	}

	public List<Role> getCurRoles() {
		Person person = this.getCurUser();
		return person.getRoles();
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

}
