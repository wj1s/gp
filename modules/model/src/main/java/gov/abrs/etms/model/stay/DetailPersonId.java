package gov.abrs.etms.model.stay;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class DetailPersonId implements Serializable {

	private static final long serialVersionUID = -6864418165150295820L;

	private StayDetail stayDetail;//留守明晰
	private String empName;//人员名称

	public DetailPersonId() {}

	public DetailPersonId(StayDetail stayDetail, String empName) {
		this.stayDetail = stayDetail;
		this.empName = empName;
	}

	@ManyToOne
	@JoinColumn(name = "DETAIL_ID", nullable = false)
	public StayDetail getStayDetail() {
		return stayDetail;
	}

	public void setStayDetail(StayDetail stayDetail) {
		this.stayDetail = stayDetail;
	}

	@Column(name = "EMP_NAME", nullable = false, length = 20)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

}
