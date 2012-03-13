package gov.abrs.etms.model.log;

import gov.abrs.etms.model.util.IdEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

//一次日志的历史记录
@Entity
@Table(name = "TB_ETMS_LOG")
public class AuditLog extends IdEntity implements Serializable {

	private static final long serialVersionUID = -765050831037038321L;

	private String classNameCn;//类的中文名，用自定义标注配置@ClassName
	private String modelName;//类的完整类名
	private Long modelId;//对象的id
	private String updName;//修改人
	private Date updDate;//修改时间

	private List<AuditLogHis> auditLogHisList;

	@Column(name = "CLASS_NAME_CN", length = 20)
	public String getClassNameCn() {
		return classNameCn;
	}

	public void setClassNameCn(String classNameCn) {
		this.classNameCn = classNameCn;
	}

	@Column(name = "MODEL_NAME", length = 100)
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "MODEL_ID")
	public Long getModelId() {
		return modelId;
	}

	public void setModelId(Long modelId) {
		this.modelId = modelId;
	}

	@Column(name = "UPD_DATE")
	public Date getUpdDate() {
		return updDate;
	}

	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	@Column(name = "UPD_NAME", length = 50)
	public String getUpdName() {
		return updName;
	}

	public void setUpdName(String updName) {
		this.updName = updName;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "auditLog", fetch = FetchType.LAZY, targetEntity = AuditLogHis.class)
	public List<AuditLogHis> getAuditLogHisList() {
		return auditLogHisList;
	}

	public void setAuditLogHisList(List<AuditLogHis> auditLogHisList) {
		this.auditLogHisList = auditLogHisList;
	}

}
