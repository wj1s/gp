package gov.abrs.etms.model.stay;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class SectionPersonId implements Serializable {

	private static final long serialVersionUID = -1510483213682895344L;

	private StaySection staySection;//留守分区
	private String empName;//名称

	public SectionPersonId() {}

	public SectionPersonId(StaySection staySection, String empName) {
		this.staySection = staySection;
		this.empName = empName;
	}

	@ManyToOne
	@JoinColumn(name = "SECTION_ID", nullable = false, insertable = false, updatable = false)
	public StaySection getStaySection() {
		return staySection;
	}

	public void setStaySection(StaySection staySection) {
		this.staySection = staySection;
	}

	@Column(name = "EMP_NAME", nullable = false, length = 20)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
}
