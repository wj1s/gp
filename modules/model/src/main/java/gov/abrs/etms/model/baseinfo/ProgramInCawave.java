package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.util.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//节目所属节目流对应信息
@Entity
@Table(name = "TB_ETMS_BASE_CAWAVE_PG_REF")
public class ProgramInCawave extends IdEntity {

	private Program program;//节目
	private Cawave cawave;//节目流
	private Date startDate;//起效日期
	private Date endDate;//无效日期(为空则一直有效)

	public ProgramInCawave() {}

	public ProgramInCawave(long id) {
		this.id = id;
	}

	public ProgramInCawave(Program program, Date startDate, Date endDate) {
		super();
		this.program = program;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@ManyToOne()
	@JoinColumn(name = "PROGRAM_ID", nullable = false)
	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	@ManyToOne()
	@JoinColumn(name = "CAWAVE_ID", nullable = false)
	public Cawave getCawave() {
		return cawave;
	}

	public void setCawave(Cawave cawave) {
		this.cawave = cawave;
	}

	@Column(name = "START_DATE", nullable = false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", nullable = true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
