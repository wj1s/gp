package gov.abrs.etms.model.sys;

import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.util.IdEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "TB_SEC_ROLE")
public class Role extends IdEntity implements GrantedAuthority {

	private String name; //角色名称
	private String desc; //角色描述
	private List<Person> persons;//角色对应的人
	private List<PopedomView> popedomViews;//角色对应的菜单

	public Role() {}

	public Role(Long id) {
		this.id = id;
	}

	@Column(name = "ROLE_NAME", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ROLE_DESC", nullable = false, length = 50)
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "TB_SEC_POP_ASS", joinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "POPE_ID", updatable = false) })
	public List<PopedomView> getPopedomViews() {
		return this.popedomViews;
	}

	public void setPopedomViews(List<PopedomView> popedomViews) {
		this.popedomViews = popedomViews;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "TB_SEC_PRI_ASS", joinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "EMP_ID", updatable = false) })
	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	@Transient
	@Override
	public String getAuthority() {
		return name;
	}

}
