package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//值班时间段规则
@Entity
@Table(name = "TB_ETMS_BASE_SCHEDULE")
public class Schedule extends IdEntity implements Jsonable {

	private Operation operation;//业务
	private Channel channel;//通路
	private Date startDate;//开始时间
	private Date endDate;//结束时间

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("operation.name", this.getOperation().getName());
		jsonObject.put("channel.name", this.getChannel().getName());
		jsonObject.put("startDate", DateUtil.getDateStr(this.getStartDate()));
		jsonObject.put("endDate", DateUtil.getDateStr(this.getEndDate()));
		return jsonObject.toString();
	}

	public Schedule() {}

	public Schedule(long id) {
		this.id = id;
	}

	public Schedule(Operation operation, Channel channel, Date startDate, Date endDate) {
		super();
		this.operation = operation;
		this.channel = channel;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@ManyToOne()
	@JoinColumn(name = "OP_ID", nullable = true)
	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	@ManyToOne()
	@JoinColumn(name = "CHANNEL_ID", nullable = false)
	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	@Column(name = "START_DATE", nullable = false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "END_DATE", nullable = false)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
