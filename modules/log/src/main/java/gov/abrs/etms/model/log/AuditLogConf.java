package gov.abrs.etms.model.log;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

//日志的配置信息
@Entity
@Table(name = "TB_ETMS_LOG_CONF")
public class AuditLogConf {

	private String className;//实体类的完整类名（gov.abrs.etms.model.baseinfo.dept）
	private String enable;//是否开启审计功能

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "CLASS_NAME", length = 100)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	@Column(name = "ENABLE", length = 1)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

}
