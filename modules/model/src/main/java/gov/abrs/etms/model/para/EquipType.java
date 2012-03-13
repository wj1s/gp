package gov.abrs.etms.model.para;

import gov.abrs.etms.model.baseinfo.EquipModel;
import gov.abrs.etms.model.repair.CardModel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue(value = "EQTP")
public class EquipType extends ParaDtl {

	private List<EquipModel> equipModels;
	private List<CardModel> cardModels;

	public EquipType() {
		super();
	}

	public EquipType(Long id) {
		super(id);
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equipType", fetch = FetchType.LAZY, targetEntity = EquipModel.class)
	public List<EquipModel> getEquipModels() {
		return equipModels;
	}

	public void setEquipModels(List<EquipModel> equipModels) {
		this.equipModels = equipModels;
	}

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "equipType", fetch = FetchType.LAZY, targetEntity = CardModel.class)
	public List<CardModel> getCardModels() {
		return cardModels;
	}

	public void setCardModels(List<CardModel> cardModels) {
		this.cardModels = cardModels;
	}

}
