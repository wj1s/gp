package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.repair.RecordItem;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.sys.Role;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Lists;

//人员信息
@Entity
@Table(name = "TB_ETMS_BASE_PERSON")
public class Person extends IdEntity implements Jsonable, AutoCompleteable, UserDetails {

	private static final long serialVersionUID = 859350993315462795L;

	private Group group;//所属班组
	private Dept dept;//所属部门
	private String code;//人员编号
	private String loginName;//登录名
	private String loginPassword;//密码
	private String name;//员工姓名
	private Post post;//职务
	private Boolean sex;//性别
	private String mobile;//移动电话
	private String officeTel;//电话
	private String officePassword;//短号码
	private String email;//电子邮件
	private String dorm;//宿舍号码
	private String idtyCard;//身份证号码
	private String adId;//adId
	private String enable;//有效性

	private List<Role> roles; //角色
	private List<DeptPer> deptPers = Lists.newArrayList(); //功能权限

	private List<RecordItem> recordItems;//检修项目

	@Transient
	public List<String> getActorIds() {
		List<String> actorIds = new ArrayList<String>();
		for (Role role : this.roles) {
			actorIds.add("ROLE_" + role.getName());
		}
		return actorIds;
	}

	@Transient
	public List<Dept> getDeptsFun(FunModule funModule) {
		List<Dept> deptsFun = Lists.newArrayList();
		for (DeptPer deptPer : this.getDeptPers()) {
			if (deptPer.getFunModuleKey().equalsIgnoreCase(funModule.getKey())) {
				deptsFun.add(deptPer.getDept());
			}
		}
		return deptsFun;
	}

	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("dept.name", this.getDept().getName());
		jsonObject.put("group.name", this.getGroup() == null ? "" : this.getGroup().getName());
		jsonObject.put("code", this.getCode());
		jsonObject.put("name", this.getName());
		jsonObject.put("post.name", this.getPost() == null ? "" : this.getPost().getName());
		String sex = "";
		if (this.getSex() != null) {
			if (this.getSex()) {
				sex = "男";
			} else {
				sex = "女";
			}
		}
		jsonObject.put("sex", sex);
		jsonObject.put("loginName", getLoginName());
		jsonObject.put("loginPassword", getLoginPassword());
		jsonObject.put("mobile", this.getMobile());
		jsonObject.put("officeTel", this.getOfficeTel());
		jsonObject.put("officePassword", this.getOfficePassword());
		jsonObject.put("email", this.getEmail());
		jsonObject.put("dorm", this.getDorm());
		jsonObject.put("idtyCard", this.getIdtyCard());

		return jsonObject.toString();
	}

	@Override
	@Transient
	public String getAutoCompleteJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Transient
	@Override
	public List<Dept> getDeptsPopedom() {
		return Lists.newArrayList(getDept());
	}

	public Person() {}

	public Person(long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "GROUP_ID", nullable = true)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@ManyToOne
	@JoinColumn(name = "DP_ID", nullable = true)
	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "EMP_CODE", length = 10, nullable = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "LOGIN_NAME", length = 60, nullable = true)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "LOGIN_PASSWORD", length = 60, nullable = true)
	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	@Column(name = "EMP_NAME", length = 20, nullable = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "POST", nullable = true)
	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	@Column(name = "SEX", length = 1, nullable = true)
	public Boolean getSex() {
		return sex;
	}

	public void setSex(Boolean sex) {
		this.sex = sex;
	}

	@Column(name = "MOBILE", length = 20, nullable = true)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "OFFICE_TEL", length = 40, nullable = true)
	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	@Column(name = "OFFICE_PASSWORD", length = 40, nullable = true)
	public String getOfficePassword() {
		return officePassword;
	}

	public void setOfficePassword(String officePassword) {
		this.officePassword = officePassword;
	}

	@Column(name = "EMAIL", length = 100, nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "DORM", length = 100, nullable = true)
	public String getDorm() {
		return dorm;
	}

	public void setDorm(String dorm) {
		this.dorm = dorm;
	}

	@Column(name = "IDTY_CARD", length = 18, nullable = true)
	public String getIdtyCard() {
		return idtyCard;
	}

	public void setIdtyCard(String idtyCard) {
		this.idtyCard = idtyCard;
	}

	@Column(name = "AD_ID", length = 50, nullable = true)
	public String getAdId() {
		return adId;
	}

	public void setAdId(String adId) {
		this.adId = adId;
	}

	@Column(name = "IS_ENABLE", length = 1, nullable = false)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "TB_SEC_PRI_ASS", joinColumns = @JoinColumn(name = "EMP_ID"), inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "person", fetch = FetchType.LAZY, targetEntity = DeptPer.class)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<DeptPer> getDeptPers() {
		return deptPers;
	}

	public void setDeptPers(List<DeptPer> deptPers) {
		this.deptPers = deptPers;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "persons")
	public List<RecordItem> getRecordItems() {
		return this.recordItems;
	}

	public void setRecordItems(List<RecordItem> recordItems) {
		this.recordItems = recordItems;
	}

	@Transient
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> roleCollection = new ArrayList<GrantedAuthority>();
		for (Role role : roles) {
			role.setName("ROLE_" + role.getName().toUpperCase());
			roleCollection.add(role);
		}
		return roleCollection;
	}

	@Transient
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Transient
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Transient
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Transient
	@Override
	public String getPassword() {
		return loginPassword;
	}

	@Transient
	@Override
	public String getUsername() {
		return loginName;
	}

}
