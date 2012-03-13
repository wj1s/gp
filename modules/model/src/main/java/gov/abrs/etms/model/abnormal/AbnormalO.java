package gov.abrs.etms.model.abnormal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.annotations.Cascade;

@Entity
@DiscriminatorValue(value = "O")
public class AbnormalO extends Abnormal {
	private List<AbnEquip> abnEquips = new ArrayList<AbnEquip>();//影响设备

	@Override
	@Transient
	public String getJsonObject() {
		JSONObject jsonObject = getJson();
		JSONArray jsonArray = new JSONArray();
		for (AbnEquip abnEquip : abnEquips) {
			jsonArray.add(abnEquip.getJsonObject());
		}
		jsonObject.put("abnEquips", jsonArray.toString());
		jsonObject.put("type", "O");
		return jsonObject.toString();
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "abnormalO", fetch = FetchType.LAZY, targetEntity = AbnEquip.class)
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	@OrderBy(value = "sortby")
	public List<AbnEquip> getAbnEquips() {
		return abnEquips;
	}

	public void setAbnEquips(List<AbnEquip> abnEquips) {
		this.abnEquips = abnEquips;
	}
}
