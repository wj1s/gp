package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//其他异态影响设备情况
@Entity
@Table(name = "TB_ETMS_ACCD_ABN_EQUIP")
public class AbnEquip extends IdEntity implements Jsonable {
	private AbnormalO abnormalO;//异态
	private Equip equip;//影响设备
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer sortby;//顺序
	private AbnormalB abnormalB;//由异态引起的设备故障信息

	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("startTime", DateUtil.getDateTimeStr(this.getStartTime()));
		jsonObject.put("endTime", DateUtil.getDateTimeStr(this.getEndTime()));
		jsonObject.put("equip", equip.getJsonObject());
		jsonObject.put("type", abnormalB != null ? "B" : "N");
		return jsonObject.toString();
	}

	@ManyToOne
	@JoinColumn(name = "ABN_O_ID", nullable = false)
	public AbnormalO getAbnormalO() {
		return abnormalO;
	}

	public void setAbnormalO(AbnormalO abnormalO) {
		this.abnormalO = abnormalO;
	}

	@ManyToOne
	@JoinColumn(name = "EQUIP_ID", nullable = false)
	public Equip getEquip() {
		return equip;
	}

	public void setEquip(Equip equip) {
		this.equip = equip;
	}

	@Column(name = "START_TIME", nullable = false)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name = "END_TIME", nullable = false)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "ABN_B_ID")
	public AbnormalB getAbnormalB() {
		return abnormalB;
	}

	public void setAbnormalB(AbnormalB abnormalB) {
		this.abnormalB = abnormalB;
	}

	@Column(name = "SORTBY", nullable = false)
	public Integer getSortby() {
		return sortby;
	}

	public void setSortby(Integer sortby) {
		this.sortby = sortby;
	}

}
