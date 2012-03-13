package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

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

//转发器
@Entity
@Table(name = "TB_ETMS_BASE_TRANSFER")
public class Transfer extends IdEntity implements Jsonable {

	private String name;//转发器名称
	private Date updDate;//更新时间
	private String empName;//操作员
	private Satellite satellite;//卫星

	private List<OperationS> OperationSs;//对应业务

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("satellite.name", this.getSatellite().getName());
		jsonObject.put("name", this.getName());
		jsonObject.put("updDate", DateUtil.getDateStr(this.getUpdDate()));
		jsonObject.put("empName", this.getEmpName());
		return jsonObject.toString();
	}

	public Transfer() {}

	public Transfer(long id) {
		this.id = id;
	}

	public Transfer(String name, Date updDate, String empName, Satellite satellite) {
		super();
		this.name = name;
		this.updDate = updDate;
		this.empName = empName;
		this.satellite = satellite;
	}

	@ManyToOne()
	@JoinColumn(name = "STL_ID", nullable = false)
	public Satellite getSatellite() {
		return satellite;
	}

	public void setSatellite(Satellite satellite) {
		this.satellite = satellite;
	}

	@Column(name = "SK_NAME", length = 20, nullable = false)
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

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "transfer", fetch = FetchType.LAZY, targetEntity = OperationS.class)
	public List<OperationS> getOperationSs() {
		return OperationSs;
	}

	public void setOperationSs(List<OperationS> operationSs) {
		OperationSs = operationSs;
	}
}
