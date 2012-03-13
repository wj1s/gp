package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.ProgramType;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//节目
@Entity
@Table(name = "TB_ETMS_BASE_PROGRAM")
public class Program extends IdEntity implements Jsonable, AutoCompleteable {

	private String code;//节目代码
	private String name;//节目名称
	private ProgramType programType;//节目类型

	private Date updDate;//更新时间
	private String empName;//操作员

	private List<ProgramInCawave> programInCawaves;//本节目的所属节目流对应信息
	private List<OperationP> OperationPs;//本节目的所属平台业务

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("code", this.getCode());
		jsonObject.put("name", this.getName());
		jsonObject.put("programType.codeDesc", this.getProgramType().getCodeDesc());
		jsonObject.put("updDate", DateUtil.getDateStr(this.getUpdDate()));
		jsonObject.put("empName", this.getEmpName());
		return jsonObject.toString();
	}

	@Transient
	public String getAutoCompleteJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		return jsonObject.toString();
	}

	@Transient
	public List<Dept> getDeptsPopedom() {
		return new ArrayList<Dept>();
	}

	public Program() {}

	public Program(long id) {
		this.id = id;
	}

	public Program(String code, String name, ProgramType programType, Date updDate, String empName) {
		super();
		this.code = code;
		this.name = name;
		this.programType = programType;
		this.updDate = updDate;
		this.empName = empName;
	}

	@Column(name = "PROGRAM_CODE", length = 20, nullable = false, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "PROGRAM_NAME", length = 64, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne()
	@JoinColumn(name = "PROGRAM_TYPE", nullable = false)
	public ProgramType getProgramType() {
		return programType;
	}

	public void setProgramType(ProgramType programType) {
		this.programType = programType;
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

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "program", fetch = FetchType.LAZY, targetEntity = ProgramInCawave.class)
	public List<ProgramInCawave> getProgramInCawaves() {
		return programInCawaves;
	}

	public void setProgramInCawaves(List<ProgramInCawave> programInCawaves) {
		this.programInCawaves = programInCawaves;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "program", fetch = FetchType.LAZY, targetEntity = OperationP.class)
	public List<OperationP> getOperationPs() {
		return OperationPs;
	}

	public void setOperationPs(List<OperationP> operationPs) {
		OperationPs = operationPs;
	}
}
