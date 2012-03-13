package gov.abrs.etms.action.util;

import gov.abrs.etms.common.util.DateUtil;

public class ReptCarrier {

	private String startTime;
	private String endTime;
	private String transType;
	private String imptPeriod;
	private String calMonth;
	private String broadByFlag;
	private String transTypeDB;

	private String params;//用于润乾报表拼好的参数字符串

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = DateUtil.addDay(endTime, "yyyy-MM-dd", 1);
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getImptPeriod() {
		return imptPeriod;
	}

	public void setImptPeriod(String imptPeriod) {
		this.imptPeriod = imptPeriod;
	}

	public String getCalMonth() {
		return calMonth;
	}

	public void setCalMonth(String calMonth) {
		this.calMonth = calMonth;
	}

	public String getBroadByFlag() {
		return broadByFlag;
	}

	public void setBroadByFlag(String broadByFlag) {
		this.broadByFlag = broadByFlag;
	}

	public String getTransTypeDB() {
		return transTypeDB;
	}

	public void setTransTypeDB(String transTypeDB) {
		this.transTypeDB = transTypeDB;
	}

	public String getParams() {
		params = "";
		if (startTime != null && !startTime.equals("")) {
			params += "start_time=" + startTime + ";";
		}
		if (endTime != null && !endTime.equals("")) {
			params += "end_time=" + endTime + ";";
		}
		if (transType != null && !transType.equals("")) {
			params += "trans_type=" + transType + ";";
		}
		if (imptPeriod != null && !imptPeriod.equals("")) {
			params += "impt_period_seq=" + imptPeriod + ";";
		}
		if (calMonth != null && !calMonth.equals("")) {
			params += "cal_month=" + calMonth + ";";
		}
		if (broadByFlag != null && !broadByFlag.equals("")) {
			params += "broad_by_flag=" + broadByFlag + ";";
		}
		if (transTypeDB != null && !transTypeDB.equals("")) {
			params += "trans_type=" + transType + ";";
		}
		return params + "AAA";
	}

	public void setParams(String params) {
		this.params = params;
	}

}
