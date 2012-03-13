package gov.abrs.etms.model.sys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OrderBy;

/**
 * PopedomView entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_SEC_POPEDOM_VIEW")
public class PopedomView implements java.io.Serializable {

	// Fields    
	private static final long serialVersionUID = 8248632014545162450L;
	private String popeId;
	private PopedomView popedomView;
	private String popeName;
	private String decs;
	private Integer position;
	private String url;
	private String type;
	private String activity;
	private List<PopedomView> popedomViews = new ArrayList();
	private List<Role> roles = new ArrayList();
	@Transient
	private boolean personHas;

	//获取是否当前生效	
	@Transient
	public boolean isEnable() {
		if (activity != null && activity.equals("1") && type.equals("1")) {
			return true;
		} else {
			return false;
		}
	}

	// Constructors
	/** default constructor */
	public PopedomView() {}

	/** minimal constructor */
	public PopedomView(String popeId) {
		this.popeId = popeId;
	}

	/** full constructor */
	public PopedomView(String popeId, PopedomView popedomView, String popeName, String decs, Integer position,
			String url, String type, String activity, List<PopedomView> popedomViews, List<Role> roles) {
		this.popeId = popeId;
		this.popedomView = popedomView;
		this.popeName = popeName;
		this.decs = decs;
		this.position = position;
		this.url = url;
		this.type = type;
		this.activity = activity;
		this.popedomViews = popedomViews;
		this.roles = roles;
	}

	// Property accessors
	@Id
	@Column(name = "POPE_ID", unique = true, nullable = false, length = 50)
	public String getPopeId() {
		return this.popeId;
	}

	public void setPopeId(String popeId) {
		this.popeId = popeId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PA_POPE_ID")
	public PopedomView getPopedomView() {
		return this.popedomView;
	}

	public void setPopedomView(PopedomView popedomView) {
		this.popedomView = popedomView;
	}

	@Column(name = "POPE_NAME", length = 50)
	public String getPopeName() {
		return this.popeName;
	}

	public void setPopeName(String popeName) {
		this.popeName = popeName;
	}

	@Column(name = "DECS", length = 50)
	public String getDecs() {
		return this.decs;
	}

	public void setDecs(String decs) {
		this.decs = decs;
	}

	@Column(name = "POSITION")
	public Integer getPosition() {
		return this.position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	@Column(name = "URL", length = 100)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "TYPE", length = 1)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "ACTIVITY", length = 1)
	public String getActivity() {
		return this.activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "popedomView")
	@OrderBy(clause = "POPE_ID asc")
	public List<PopedomView> getPopedomViews() {
		return this.popedomViews;
	}

	public void setPopedomViews(List<PopedomView> popedomViews) {
		this.popedomViews = popedomViews;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "popedomViews")
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public boolean isPersonHas() {
		return personHas;
	}

	@Transient
	public void setPersonHas(boolean personHas) {
		this.personHas = personHas;
	}

}