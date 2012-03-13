package gov.abrs.etms.model.repair;

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
 * CycleCell entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_REPAIR_CYCLE_CELL")
public class CycleCell extends IdEntity implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1071656694499444645L;
	private Cycle cycle;//检修周期表
	private String name;//名称
	private List<Card> cards;//卡片

	// Constructors

	/** default constructor */
	public CycleCell() {}

	public CycleCell(long id) {
		this.id = id;
	}

	/** minimal constructor */
	public CycleCell(long id, Cycle cycle, String name) {
		this.id = id;
		this.cycle = cycle;
		this.name = name;
	}

	/** full constructor */
	public CycleCell(long id, Cycle cycle, String name, List<Card> cards) {
		this.id = id;
		this.cycle = cycle;
		this.name = name;
		this.cards = cards;
	}

	// Property accessors

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CYCLE_ID", nullable = false)
	public Cycle getCycle() {
		return this.cycle;
	}

	public void setCycle(Cycle cycle) {
		this.cycle = cycle;
	}

	@Column(name = "NAME", nullable = false, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "cycleCell")
	@OrderBy(clause = "name asc")
	public List<Card> getCards() {
		return this.cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

}