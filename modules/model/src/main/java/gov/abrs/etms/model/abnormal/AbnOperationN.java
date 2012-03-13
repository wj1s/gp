package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.model.baseinfo.Operation;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//由异态产生的没引起停传的业务信息
@Entity
@DiscriminatorValue(value = "N")
public class AbnOperationN extends AbnOperation {

	public AbnOperationN() {
		super();
	}

	public AbnOperationN(Operation operation, Date startTime, Date endTime, Integer sortby) {
		super(operation, startTime, endTime, sortby);
	}

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = super.getJson();
		jsonObject.put("type", "N");
		return jsonObject.toString();
	}
}
