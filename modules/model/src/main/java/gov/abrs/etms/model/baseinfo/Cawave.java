package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

//节目流
@Entity
@Table(name = "TB_ETMS_BASE_CAWAVE")
public class Cawave extends IdEntity {

	private String name;//节目流名称
	private Date updDate;//更新日期
	private String empName;//操作员

	private List<ProgramInCawave> programInCawaves;//本节目流的所包含节目对应信息
	private List<OperationT> operationTs;//本节目流所属传输业务
	private List<OperationS> operationSs;//本节目流所属平台业务

	public Cawave() {}

	public Cawave(long id) {
		this.id = id;
	}

	public Cawave(String name, Date updDate, String empName, List<ProgramInCawave> programInCawaves) {
		super();
		this.name = name;
		this.updDate = updDate;
		this.empName = empName;
		this.programInCawaves = programInCawaves;
	}

	@Column(name = "CAWAVE_NAME", length = 64, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "UPD_DATE", nullable = false)
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "EMP_NAME", length = 20, nullable = false)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cawave", fetch = FetchType.LAZY, targetEntity = ProgramInCawave.class)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	public List<ProgramInCawave> getProgramInCawaves() {
		return programInCawaves;
	}

	public void setProgramInCawaves(List<ProgramInCawave> programInCawaves) {
		this.programInCawaves = programInCawaves;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cawaveT", fetch = FetchType.LAZY, targetEntity = OperationT.class)
	public List<OperationT> getOperationTs() {
		return operationTs;
	}

	public void setOperationTs(List<OperationT> operationTs) {
		this.operationTs = operationTs;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "cawaveS", fetch = FetchType.LAZY, targetEntity = OperationS.class)
	public List<OperationS> getOperationSs() {
		return operationSs;
	}

	public void setOperationSs(List<OperationS> operationSs) {
		this.operationSs = operationSs;
	}

}
