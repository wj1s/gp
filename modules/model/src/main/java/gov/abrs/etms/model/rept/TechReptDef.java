package gov.abrs.etms.model.rept;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.util.Jsonable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

@Entity
@DiscriminatorValue(value = "TECH")
public class TechReptDef extends ReptPool implements Jsonable {
	@Override
	@Transient
	public String getJsonObject() {
		return getJson().toString();
	}

	@Transient
	protected JSONObject getJson() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("startDate", DateUtil.getDateTimeStr(this.getStartDate()));
		jsonObject.put("endDate", DateUtil.getDateTimeStr(this.getEndDate()));
		return jsonObject;
	}

	private List<ReptTechDtl> reptTechDtl = new ArrayList<ReptTechDtl>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "techReptDef")
	public List<ReptTechDtl> getReptTechDtl() {
		return this.reptTechDtl;
	}

	public void setReptTechDtl(List<ReptTechDtl> reptTechDtl) {
		this.reptTechDtl = reptTechDtl;
	}
}
