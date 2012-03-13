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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//卫星
@Entity
@Table(name = "TB_ETMS_BASE_SATELLITE")
public class Satellite extends IdEntity implements Jsonable {

	private String name;//卫星名称
	private String stlOrbit;//轨道位置
	private Date updDate;//更新日期
	private String empName;//操作员

	private List<Transfer> transfers;//转发器

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("stlOrbit", this.getStlOrbit());
		jsonObject.put("updDate", DateUtil.getDateStr(this.getUpdDate()));
		jsonObject.put("empName", this.getEmpName());
		return jsonObject.toString();
	}

	public Satellite() {}

	public Satellite(String name, String stlOrbit, Date updDate, String empName) {
		super();
		this.name = name;
		this.stlOrbit = stlOrbit;
		this.updDate = updDate;
		this.empName = empName;
	}

	public Satellite(long id) {
		this.id = id;
	}

	@Column(name = "STL_NAME", length = 20, nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STL_ORBIT", length = 64, nullable = true)
	public String getStlOrbit() {
		return stlOrbit;
	}

	public void setStlOrbit(String stlOrbit) {
		this.stlOrbit = stlOrbit;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "satellite", targetEntity = Transfer.class)
	public List<Transfer> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}

}
