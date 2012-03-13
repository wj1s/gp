package gov.abrs.etms.model.repair;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.util.IdEntity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

/**
 * Cycle entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_CYCLE")
public class Cycle extends IdEntity implements java.io.Serializable {

	// Fields

	private Dept dept;//部门
	private String name;//名称
	private String active;//启用/停用
	private List<CycleCell> cycleCells;//检修周期区间

	// Constructors

	/** default constructor */
	public Cycle() {}

	public Cycle(long id) {
		this.id = id;
	}

	/** minimal constructor */
	public Cycle(long id, Dept dept, String name, String active) {
		this.id = id;
		this.dept = dept;
		this.name = name;
		this.active = active;
	}

	/** full constructor */
	public Cycle(long id, Dept dept, String name, String active, List<CycleCell> cycleCells) {
		this.id = id;
		this.dept = dept;
		this.name = name;
		this.active = active;
		this.cycleCells = cycleCells;
	}

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT_ID", nullable = false)
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ACTIVE", nullable = false, length = 1)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cycle")
	@OrderBy(clause = "name asc")
	public List<CycleCell> getCycleCells() {
		return this.cycleCells;
	}

	public void setCycleCells(List<CycleCell> cycleCells) {
		this.cycleCells = cycleCells;
	}

}