package gov.abrs.etms.model.para;

import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

@Entity
@Table(name = "TB_ETMS_BASE_PARA_DTL")
@DiscriminatorColumn(name = "PARA_TYPE", discriminatorType = DiscriminatorType.STRING, length = 4)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ParaDtl extends IdEntity implements Jsonable {

	// Fields    
	private String paraType;
	private String paraCode;
	private String codeDesc;
	private String sortby;
	private Date updDate;
	private String empName;

	// Constructors

	/** default constructor */
	public ParaDtl() {}

	public ParaDtl(Long id) {
		this.id = id;
	}

	/** minimal constructor */
	public ParaDtl(String paraType, String paraCode, String codeDesc, String empName) {
		this.paraType = paraType;
		this.paraCode = paraCode;
		this.codeDesc = codeDesc;
		this.empName = empName;
	}

	/** full constructor */
	public ParaDtl(String paraType, String paraCode, String codeDesc, String sortby, Date updDate, String empName) {
		this.paraType = paraType;
		this.paraCode = paraCode;
		this.codeDesc = codeDesc;
		this.sortby = sortby;
		this.updDate = updDate;
		this.empName = empName;
	}

	@Override
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("paraCode", paraCode);
		jsonObject.put("codeDesc", codeDesc);
		return jsonObject;
	}

	// Property accessors
	@Column(name = "CODE_DESC", nullable = false, length = 40)
	public String getCodeDesc() {
		return this.codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	@Column(name = "SORTBY", length = 4)
	public String getSortby() {
		return this.sortby;
	}

	public void setSortby(String sortby) {
		this.sortby = sortby;
	}

	@Column(name = "UPD_DATE", length = 23)
	public Date getUpdDate() {
		return this.updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "EMP_NAME", nullable = false, length = 20)
	public String getEmpName() {
		return this.empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "PARA_TYPE", insertable = false, updatable = false)
	public String getParaType() {
		return paraType;
	}

	public void setParaType(String paraType) {
		this.paraType = paraType;
	}

	@Column(name = "PARA_CODE", nullable = false, length = 6)
	public String getParaCode() {
		return paraCode;
	}

	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}

}