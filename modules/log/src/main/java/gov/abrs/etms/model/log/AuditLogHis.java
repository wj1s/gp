package gov.abrs.etms.model.log;

import gov.abrs.etms.model.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//日志的历史记录
@Entity
@Table(name = "TB_ETMS_LOG_HIS")
public class AuditLogHis extends IdEntity {

	private AuditLog auditLog;//一次历史记录
	private String propertyName;//属性的中文名，用自定义标注标示@PropertyName
	private String oldVal;//旧值
	private String newVal;//新值

	@ManyToOne()
	@JoinColumn(name = "LOG_ID", nullable = false)
	public AuditLog getAuditLog() {
		return auditLog;
	}

	public void setAuditLog(AuditLog auditLog) {
		this.auditLog = auditLog;
	}

	@Column(name = "PROPERTY_NAME", length = 25)
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	@Column(name = "OLD_VAL", length = 1024)
	public String getOldVal() {
		return oldVal;
	}

	public void setOldVal(String oldVal) {
		this.oldVal = oldVal;
	}

	@Column(name = "NEW_VAL", length = 1024)
	public String getNewVal() {
		return newVal;
	}

	public void setNewVal(String newVal) {
		this.newVal = newVal;
	}

}
