package gov.abrs.etms.action.util;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.Station;
import gov.abrs.etms.model.baseinfo.TechSystem;
import gov.abrs.etms.model.para.TransType;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.sys.PopedomView;
import gov.abrs.etms.service.baseinfo.StationService;
import gov.abrs.etms.service.security.SecurityService;
import gov.abrs.etms.service.sys.PopedomViewService;
import gov.abrs.etms.service.util.UtilService;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

@Results({ @Result(name = "easy", type = "grid", params = { "type", "easy", "exposedValue", "json" }) })
public abstract class BaseAction implements Action, Serializable {

	private static final long serialVersionUID = 7026530169731783701L;

	public static final String EASY = "easy";

	protected String json;

	private String navMessage;

	public Person getCurUser() {
		return securityService.getCurUser();
	}

	public List<TransType> getCurTransTypes(FunModule funModule) {
		return securityService.getCurTransTypes(funModule);
	}

	public List<TechSystem> getCurTechSystems(FunModule funModule) {
		return securityService.getCurTechSystems(funModule);
	}

	public Date getCurDate() {
		return utilService.getSysTime();
	}

	public String getCurDateStr() {
		return DateUtil.dateToString(getCurDate(), DateUtil.FORMAT_DAY_CN_HM);
	}

	public PopedomView getCurMenu() {
		Person person = getCurUser();
		PopedomView popedomView = popedomViewService.findAllByPerson(person);
		return popedomView;
	}

	public Station getStation() {
		return stationService.getStation();
	}

	//空的execute方法，接口需要
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	private SecurityService securityService;
	private UtilService utilService;
	private StationService stationService;
	@Autowired
	private PopedomViewService popedomViewService;

	@Autowired
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Autowired
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	public String getJson() {
		return json;
	}

	public String getNavMessage() {
		return navMessage;
	}

	public void setNavMessage(String navMessage) {
		this.navMessage = navMessage;
	}

	/*判定登陆的类型*/
	public String getLoginType() {
		String authType = (System.getProperty("authType")).toLowerCase();
		if (authType.equals("cas")) {
			authType = "/j_spring_cas_security_logout";
		} else if (authType.equals("db")) {
			authType = "/j_spring_security_logout";
		}
		return authType;
	}

}
