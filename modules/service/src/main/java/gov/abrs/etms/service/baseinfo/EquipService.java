package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Equip;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EquipService extends CrudService<Equip> {

	//判断如果已经被异态关联，则不能删除
	@Override
	public void delete(Long id) {
		Equip equip = dao.get(id);
		if (equip.getAbnormalBs().size() > 0 || equip.getAbnormalFs().size() > 0 || equip.getAbnEquips().size() > 0) {
			throw new CanNotDeleteException("删除的设备信息已经存在异态信息，无法删除，请删除异态信息后再删除设备!");

		} else {
			dao.delete(id);
		}
	}

	//查找后四位最大的序列号
	public String findMaxSn() {
		String hql = "from Equip e where substring(e.code,21,4) = (select max(substring(a.code,21,4)) from Equip a)";
		Equip equip = this.dao.findUnique(hql);
		return equip.getCode();
	}

	public void clearChannels(Equip model) {
		model.getChannels().clear();
	}

	public <X> List<X> getByNameLike(final Class entityClass, final String nameLike) {
		String hql = "from " + entityClass.getSimpleName() + " c where c.name like '%" + nameLike + "%'";
		return dao.find(hql);
	}
}
