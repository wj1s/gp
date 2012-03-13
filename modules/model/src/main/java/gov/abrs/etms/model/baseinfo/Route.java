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

//路由信息
@Entity
@Table(name = "TB_ETMS_BASE_ROUTE")
public class Route extends IdEntity implements Jsonable {
	private String fromPl;//起点
	private String toPl;//终点
	private Date updDate;//更新日期
	private String empName;//操作员

	private List<OperationT> operationTs;//本路由的所属传输业务

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("fromPl", this.getFromPl());
		jsonObject.put("toPl", this.getToPl());
		jsonObject.put("updDate", DateUtil.getDateStr(this.getUpdDate()));
		jsonObject.put("empName", this.getEmpName());
		return jsonObject.toString();
	}

	public Route() {}

	public Route(long id) {
		this.id = id;
	}

	@Transient
	public String getPl() {
		return this.getFromPl() + "~" + this.getToPl();
	}

	public Route(String fromPl, String toPl, Date updDate, String empName) {
		super();
		this.fromPl = fromPl;
		this.toPl = toPl;
		this.updDate = updDate;
		this.empName = empName;
	}

	@Column(name = "FROM_PL", length = 20, nullable = false)
	public String getFromPl() {
		return fromPl;
	}

	public void setFromPl(String fromPl) {
		this.fromPl = fromPl;
	}

	@Column(name = "TO_PL", length = 20, nullable = false)
	public String getToPl() {
		return toPl;
	}

	public void setToPl(String toPl) {
		this.toPl = toPl;
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

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "route", fetch = FetchType.LAZY, targetEntity = OperationT.class)
	public List<OperationT> getOperationTs() {
		return operationTs;
	}

	public void setOperationTs(List<OperationT> operationTs) {
		this.operationTs = operationTs;
	}
}
