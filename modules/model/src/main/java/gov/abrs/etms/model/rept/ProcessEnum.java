package gov.abrs.etms.model.rept;

public enum ProcessEnum {
	REPT_TECH("reptTech"), REPT_ACCD("reptAccd"), REPT_IMPT("reptImpt"), ACCD_REPORT("accidentReport"), REPT_ZERO(
			"reptZero"), ACCD_TECH("techAccident");
	private String dataSource;//数据源

	public String getDataSource() {
		return dataSource;
	}

	public static String getDataSource(String EnumName) {
		return ProcessEnum.valueOf(EnumName).getDataSource();
	}

	private ProcessEnum(String dataSource) {
		this.dataSource = dataSource;
	}

	private ProcessEnum(String dataSource, ProcessEnum subProcessName) {
		this.dataSource = dataSource;
	}
}
