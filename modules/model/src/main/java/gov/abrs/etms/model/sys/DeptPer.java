package gov.abrs.etms.model.sys;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_SEC_DEPT_PER")
public class DeptPer extends IdEntity {

	private static final long serialVersionUID = 2598488625897471563L;

	private Dept dept; //权限部门
	private Person person; //人员
	private String funModuleKey; //功能

	public DeptPer() {}

	public DeptPer(Dept dept, Person person, String funModuleKey) {
		this.dept = dept;
		this.person = person;
		this.funModuleKey = funModuleKey;
	}

	@ManyToOne
	@JoinColumn(name = "DP_ID", nullable = false)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@ManyToOne
	@JoinColumn(name = "EMP_ID", nullable = false)
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Column(name = "FUN_MODULE", length = 50)
	public String getFunModuleKey() {
		return funModuleKey;
	}

	public void setFunModuleKey(String funModuleKey) {
		this.funModuleKey = funModuleKey;
	}
}
