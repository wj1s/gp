package gov.abrs.etms.model.sys;


public enum FunModule {
	GENERAL("general", "默认"), DUTY("duty", "值班"), ACCD("accd", "故障事故"), REPAIR("repair", "检修"), REPORT("report", "报表"), BASEINFO(
			"baseinfo", "基础信息");
	private String key;//职位代码
	private String desc;//职位描述

	public static FunModule getFunModule(String key) {
		for (FunModule funModule : FunModule.values()) {
			if (funModule.getKey().equals(key)) {
				return funModule;
			}
		}
		return GENERAL;
	}

	private FunModule(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	public String getKey() {
		return key;
	}

	public String getDesc() {
		return desc;
	}

}
