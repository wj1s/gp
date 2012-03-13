package gov.abrs.etms.model.rept;

import gov.abrs.etms.model.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_ETMS_REPT_ZERO_RMKS")
public class ZeroRmks extends IdEntity {

	private ZeroReptDtl zeroReptDtl;//零报告详细
	private String rmks;//零报告文字说明
	private String accdFlag; //Y:有事故  N:无事故

	@OneToOne(optional = false)
	@JoinColumn(name = "ZERO_DTL_ID", referencedColumnName = "ID", unique = true)
	public ZeroReptDtl getZeroReptDtl() {
		return zeroReptDtl;
	}

	public void setZeroReptDtl(ZeroReptDtl zeroReptDtl) {
		this.zeroReptDtl = zeroReptDtl;
	}

	@Column(name = "RMKS", nullable = false, length = 1024)
	public String getRmks() {
		return this.rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

	@Column(name = "ACCD_FLAG", nullable = true, length = 1)
	public String getAccdFlag() {
		return accdFlag;
	}

	public void setAccdFlag(String accdFlag) {
		this.accdFlag = accdFlag;
	}

}
