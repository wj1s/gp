package gov.abrs.etms.model.rept;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//报表定义
@Entity
@Table(name = "TB_ETMS_REPT_DEF")
public class ReptDef {

	private String reptId;//报表代码
	private String reptName;//报表名称
	private String dataSource;//数据源 ACCD事故报表，IMPT重要保证期报表， EXAM查询统计表
	private String reptDept;//上报处室
	private String modelName;//润前报表名称
	private String modelInput;//润前报表输入参数，用驼峰命名法，','相隔
	private String ifUsing;//是否在使用

	@Id
	@Column(name = "REPT_ID", nullable = false, unique = true, length = 5)
	public String getReptId() {
		return this.reptId;
	}

	public void setReptId(String reptId) {
		this.reptId = reptId;
	}

	@Column(name = "REPT_NAME", nullable = false, length = 60)
	public String getReptName() {
		return this.reptName;
	}

	public void setReptName(String reptName) {
		this.reptName = reptName;
	}

	@Column(name = "DATA_SOURCE", nullable = false, length = 4)
	public String getDataSource() {
		return this.dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Column(name = "REPT_DEPT", nullable = false, length = 60)
	public String getReptDept() {
		return this.reptDept;
	}

	public void setReptDept(String reptDept) {
		this.reptDept = reptDept;
	}

	@Column(name = "MODEL_NAME", nullable = false, length = 60)
	public String getModelName() {
		return this.modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "IF_USING", nullable = false, length = 1)
	public String getIfUsing() {
		return ifUsing;
	}

	public void setIfUsing(String ifUsing) {
		this.ifUsing = ifUsing;
	}

	@Column(name = "MODEL_INPUT", nullable = true, length = 128)
	public String getModelInput() {
		return modelInput;
	}

	public void setModelInput(String modelInput) {
		this.modelInput = modelInput;
	}

}