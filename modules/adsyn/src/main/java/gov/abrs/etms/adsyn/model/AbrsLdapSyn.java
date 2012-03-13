package gov.abrs.etms.adsyn.model;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ABRS_LDAP_SYN_SUB_ETMS")
public class AbrsLdapSyn extends IdEntity {
	// Fields

	private String objectGuid;// ADID
	private String objectDisplayname;// 部门名称或者是人员的名称
	private String objectLoginname;// 用户的用户名
	private String objectParentGuid;// 用户所属部门的ADID
	private Integer objectType;// 操作的对象类型 8:用户 2:部门
	private Integer objectChangetype;// 操作类型 3:删除 1:新增 2:更新
	private String objectCode;//组织机构代码
	private Date objectChangetime;//变更时间
	private String objectOfficeName;//临时、借调等状态
	private String objectHomePage;//主页
	private String objectNeed;

	// Constructors

	/** default constructor */
	public AbrsLdapSyn() {}

	/** minimal constructor */
	public AbrsLdapSyn(Long id) {
		this.id = id;
	}

	/** full constructor */
	public AbrsLdapSyn(Long id, String objectGuid, String objectDisplayname, String objectLoginname,
			String objectParentGuid, Integer objectType, Integer objectChangetype, String objectCode,
			Date objectChangetime, String objectOfficeName, String objectHomePage, String objectNeed) {
		this.id = id;
		this.objectGuid = objectGuid;
		this.objectDisplayname = objectDisplayname;
		this.objectLoginname = objectLoginname;
		this.objectParentGuid = objectParentGuid;
		this.objectType = objectType;
		this.objectChangetype = objectChangetype;
		this.objectCode = objectCode;
		this.objectChangetime = objectChangetime;
		this.objectOfficeName = objectOfficeName;
		this.objectHomePage = objectHomePage;
		this.objectNeed = objectNeed;
	}

	// Property accessors

	@Column(name = "OBJECT_GUID", length = 36)
	public String getObjectGuid() {
		return this.objectGuid;
	}

	public void setObjectGuid(String objectGuid) {
		this.objectGuid = objectGuid;
	}

	@Column(name = "OBJECT_DISPLAYNAME", length = 200)
	public String getObjectDisplayname() {
		return this.objectDisplayname;
	}

	public void setObjectDisplayname(String objectDisplayname) {
		this.objectDisplayname = objectDisplayname;
	}

	@Column(name = "OBJECT_LOGINNAME", length = 50)
	public String getObjectLoginname() {
		return this.objectLoginname;
	}

	public void setObjectLoginname(String objectLoginname) {
		this.objectLoginname = objectLoginname;
	}

	@Column(name = "OBJECT_PARENT_GUID", length = 36)
	public String getObjectParentGuid() {
		return this.objectParentGuid;
	}

	public void setObjectParentGuid(String objectParentGuid) {
		this.objectParentGuid = objectParentGuid;
	}

	@Column(name = "OBJECT_TYPE")
	public Integer getObjectType() {
		return this.objectType;
	}

	public void setObjectType(Integer objectType) {
		this.objectType = objectType;
	}

	@Column(name = "OBJECT_CHANGETYPE")
	public Integer getObjectChangetype() {
		return this.objectChangetype;
	}

	public void setObjectChangetype(Integer objectChangetype) {
		this.objectChangetype = objectChangetype;
	}

	@Column(name = "OBJECT_CODE", length = 50)
	public String getObjectCode() {
		return this.objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	@Column(name = "OBJECT_CHANGETIME", length = 23)
	public Date getObjectChangetime() {
		return this.objectChangetime;
	}

	public void setObjectChangetime(Date objectChangetime) {
		this.objectChangetime = objectChangetime;
	}

	@Column(name = "OBJECT_OFFICE_NAME", length = 1000)
	public String getObjectOfficeName() {
		return this.objectOfficeName;
	}

	public void setObjectOfficeName(String objectOfficeName) {
		this.objectOfficeName = objectOfficeName;
	}

	@Column(name = "OBJECT_HOME_PAGE", length = 1000)
	public String getObjectHomePage() {
		return this.objectHomePage;
	}

	public void setObjectHomePage(String objectHomePage) {
		this.objectHomePage = objectHomePage;
	}

	@Column(name = "OBJECT_NEED", length = 2000)
	public String getObjectNeed() {
		return this.objectNeed;
	}

	public void setObjectNeed(String objectNeed) {
		this.objectNeed = objectNeed;
	}

}