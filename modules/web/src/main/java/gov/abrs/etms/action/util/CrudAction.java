package gov.abrs.etms.action.util;

import gov.abrs.etms.model.sys.PopedomView;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.service.sys.PopedomViewService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class CrudAction<T extends IdEntity> extends GridAction<T> {

	private static final long serialVersionUID = 8702443076934612134L;

	private String curUrl;
	private String crudType;
	private String crudFlag;

	@Override
	public void prepare() {
		if (crudFlag != null) {
			String gridAdd = "0";
			String gridEdit = "0";
			String gridDel = "0";
			List<PopedomView> list = popedomViewService.getNeedCrudPop(getCurUser().getRoles(), curUrl.substring(1));
			for (PopedomView popCrud : list) {
				if (popCrud.getUrl() != null) {
					switch (popCrud.getUrl().toCharArray()[0]) {
					case 'a':
						gridAdd = "1";
						break;
					case 'e':
						gridEdit = "1";
						break;
					case 'd':
						gridDel = "1";
						break;
					default:
						break;
					}
				}
			}
			crudType = gridAdd + "-" + gridEdit + "-" + gridDel;
		} else {
			crudType = "0-0-0";
		}
	}

	private PopedomViewService popedomViewService;

	@Autowired
	public void setPopedomViewService(PopedomViewService popedomViewService) {
		this.popedomViewService = popedomViewService;
	}

	public String getCurUrl() {
		return curUrl;
	}

	public void setCurUrl(String curUrl) {
		this.curUrl = curUrl;
	}

	public String getCrudType() {
		return crudType;
	}

	public void setCrudType(String crudType) {
		this.crudType = crudType;
	}

	public String getCrudFlag() {
		return crudFlag;
	}

	public void setCrudFlag(String crudFlag) {
		this.crudFlag = crudFlag;
	}

}
