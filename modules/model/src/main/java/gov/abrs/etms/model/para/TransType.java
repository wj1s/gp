package gov.abrs.etms.model.para;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Operation;
import gov.abrs.etms.model.baseinfo.Tache;
import gov.abrs.etms.model.baseinfo.TechSystem;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * 	传输类型
 *  PARA_TYPE     PARA_CODE     CODE_DESC   
 *  ------------  ------------  --------- 
 *  TRTP             P           集成平台    
 *  TRTP             T           节目源传输    
 *  TRTP             S           卫星上行    
 */
@Entity
@DiscriminatorValue(value = "TRTP")
public class TransType extends ParaDtl implements Comparable<TransType> {
	private List<TechSystem> techSystems;
	private List<Tache> taches;
	private List<Operation> operations;
	private List<Dept> depts;

	public TransType() {}

	public TransType(Long id) {
		super(id);
	}

	@Override
	@Transient
	public int compareTo(TransType o) {
		if (this.getId() > o.getId()) {
			return 1;
		} else if (this.getId() == o.getId()) {
			return 0;
		} else {
			return -1;
		}
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transType", fetch = FetchType.LAZY, targetEntity = TechSystem.class)
	public List<TechSystem> getTechSystems() {
		return techSystems;
	}

	public void setTechSystems(List<TechSystem> techSystems) {
		this.techSystems = techSystems;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transType", fetch = FetchType.LAZY, targetEntity = Tache.class)
	public List<Tache> getTaches() {
		return taches;
	}

	public void setTaches(List<Tache> taches) {
		this.taches = taches;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "transType", fetch = FetchType.LAZY, targetEntity = Operation.class)
	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinTable(name = "TB_ETMS_BASE_DP_TRTP", joinColumns = @JoinColumn(name = "TRANS_TYPE"), inverseJoinColumns = @JoinColumn(name = "DP_ID"))
	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}
}
