/**
 * @author 郭翔
 * 本类为人员同步的主程序。
 */
package gov.abrs.etms.adsyn.core;

import gov.abrs.etms.adsyn.model.AbrsLdapSyn;
import gov.abrs.etms.adsyn.service.ExecuteSynDAO;
import gov.abrs.etms.adsyn.util.OpenSessionUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.DeptType;
import gov.abrs.etms.service.baseinfo.DeptService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.baseinfo.PersonService;
import gov.abrs.etms.service.baseinfo.StationService;

import java.util.List;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

public class AbrsLdapSynCore extends TimerTask {
	private static final Log logger = LogFactory.getLog(AbrsLdapSynCore.class);
	/*临时变量*/
	private int addUserSuccess = 0;
	private int addUserError = 0;
	private int uptUserSuccess = 0;
	private int uptUserError = 0;
	private int delUserSuccess = 0;
	private int delUserError = 0;
	private int addDeptSuccess = 0;
	private int addDeptError = 0;
	private int uptDeptSuccess = 0;
	private int uptDeptError = 0;
	private int delDeptSuccess = 0;
	private int delDeptError = 0;
	private int all = 0;
	private int dowork = 0;
	/**/
	private ExecuteSynDAO<AbrsLdapSyn> executeSynDAO;
	private DeptService deptService;
	private PersonService personService;
	private OpenSessionUtil openSessionUtil;

	private ParaDtlService paraDtlService;
	private StationService stationService;
	private final static String personCodeSQL = "select max(cast([EMP_CODE] as bigint))+1 from [TB_ETMS_BASE_PERSON]";
	private final static String deptCodeSQL = "select max(replace(right(space(1)+dept_CODE,2), ' ', '0'))+1 from [TB_ETMS_BASE_DEPT]";

	public void init() {
		executeSynDAO.setEntityClass(AbrsLdapSyn.class);
	}

	/**
	 * 描述：主方法
	 */
	@Override
	public void run() {

		addUserSuccess = 0;
		addUserError = 0;
		uptUserSuccess = 0;
		uptUserError = 0;
		delUserSuccess = 0;
		delUserError = 0;
		addDeptSuccess = 0;
		addDeptError = 0;
		uptDeptSuccess = 0;
		uptDeptError = 0;
		delDeptSuccess = 0;
		delDeptError = 0;
		all = 0;
		dowork = 0;
		List<AbrsLdapSyn> dataList = this.getAdData();
		if (dataList != null && dataList.size() > 0) {
			try {
				try {
					// 变更：开启OPENSESSION
					openSessionUtil.onSetUp();
					for (int i = 0; i < dataList.size(); i++) {
						all++;
						AbrsLdapSyn data = (AbrsLdapSyn) dataList.get(i);
						String type = String.valueOf(data.getObjectChangetype());
						if (type.equals("3")) {
							removeUser(data);
							removeDep(data);
							dowork++;
						} else if (String.valueOf(data.getObjectType()).endsWith("2")) {
							if (type.equals("1")) {
								addUser(data);
								dowork++;
							} else if (type.equals("2")) {
								updataUser(data);
								dowork++;
							}
						} else if (String.valueOf(data.getObjectType()).endsWith("1")) {
							if (type.equals("1")) {
								addDep(data);
								dowork++;
							} else if (type.equals("2")) {
								updataDep(data);
								dowork++;
							}
						}
					}
				} finally {
					// 变更：关闭OPENSESSION
					openSessionUtil.onTearDown();
					// 变更结束
				}
			} catch (Exception e) {
				logger.error("==>ad同步中实现OPENSESSION出现异常!!)", e);
			}
			if (all > 0) {
				logger.info(getResultReportMessage());
			}
		} else {
			logger.debug("没有扫描到同步数据，继续等待。");
		}

	}

	/*主属性的各类方法*/

	/**
	 * @author guoxiang
	 * @see  中间表中的所有数据
	 * @return AbrsLdapSyn  list
	 */
	@SuppressWarnings("unchecked")
	public List<AbrsLdapSyn> getAdData() {
		List<AbrsLdapSyn> changeList = executeSynDAO.getAll("objectChangetime", true);
		return changeList;
	}

	public void delAdData(Long id) throws HibernateException {
		try {
			executeSynDAO.delete(id);
		} catch (HibernateException ex) {
			logger.error("==>同步完成清理数据不成功,出现异常!!)", ex);
		}
	}

	public void addDep(AbrsLdapSyn abrsLdapSyn) {
		String adId = abrsLdapSyn.getObjectGuid();
		String deptName = abrsLdapSyn.getObjectDisplayname();
		try {

			Dept deptHasHave = deptService.getDeptByAd("adId", adId);
			if (deptHasHave != null) {
				logger.error("==>添加部门(adId=" + adId + ",deptName=" + deptName + ")不成功,数据库中已经存在AD_ID为" + adId + "的部门:"
						+ deptHasHave.getName() + "(dpId=" + deptHasHave.getId() + ")!!");
				addDeptError++;
				return;
			}
			deptHasHave = deptService.getDeptByAd("name", deptName);
			if (deptHasHave != null) {
				String oldAdId = deptHasHave.getAdId();
				deptHasHave.setAdId(adId);
				deptService.save(deptHasHave);
				this.delAdData(abrsLdapSyn.getId());// 更新部门成功删除AD中的数据
				logger.info("==>成功更新一条部门信息(" + deptHasHave.getName() + "):adid:" + oldAdId + "->" + adId);
				uptDeptSuccess++;
				return;
			} else {
				Dept dept = new Dept();
				dept.setAdId(adId);
				dept.setName(deptName);
				dept.setStation(stationService.getStation());
				dept.setDeptType((DeptType) paraDtlService.getByCode(DeptType.class, "OTDP"));// 默认为其他部门
				/*编号生成 台站code+最大数2位*/
				dept.setCode(deptService.getMaxDeptCode(deptCodeSQL));
				dept.setEnable("1");
				deptService.save(dept);
				this.delAdData(abrsLdapSyn.getId());// 新增部门成功删除AD中的数据
				logger.info("==>成功添加一条部门信息:" + deptName);
				addDeptSuccess++;
			}
		} catch (Exception ex) {
			logger.error("==>添加部门(adId=" + adId + ",deptName=" + deptName + ")不成功,出现异常!!)", ex);
			addDeptError++;
			return;
		}
	}

	public void updataDep(AbrsLdapSyn abrsLdapSyn) {
		String adId = abrsLdapSyn.getObjectGuid();
		String deptName = abrsLdapSyn.getObjectDisplayname();
		try {
			Dept dept = deptService.getDeptByAd("adId", adId);
			if (dept != null) {
				String oldDeptName = dept.getName();
				dept.setName(deptName);
				deptService.save(dept);
				this.delAdData(abrsLdapSyn.getId());// 更新部门成功删除AD中的数据
				logger.info("==>成功更新一条部门信息:" + oldDeptName + "->" + deptName);
				uptDeptSuccess++;
			} else {
				logger.error("==>更新部门(adId=" + adId + ",deptName=" + deptName + ")不成功,没有找到或是找到多条AD_ID为" + adId
						+ "的部门!!");
				uptDeptError++;
				return;
			}
		} catch (Exception ex) {
			logger.error("==>更新部门(adId=" + adId + ",deptName=" + deptName + ")不成功,出现异常!!)", ex);
			uptDeptError++;
			return;
		}
	}

	public void removeDep(AbrsLdapSyn abrsLdapSyn) {
		String adId = abrsLdapSyn.getObjectGuid();
		try {
			Dept dept = deptService.getDeptByAd("adId", adId);
			if (dept != null) {
				String oldDeptName = dept.getName();
				dept.setEnable("0");
				deptService.save(dept);
				this.delAdData(abrsLdapSyn.getId());// 删除部门成功删除AD中的数据
				logger.info("==>成功删除一条部门信息:" + oldDeptName);
				delDeptSuccess++;
			}
		} catch (Exception ex) {
			logger.error("==>删除部门(adId=" + adId + ")不成功,出现异常!!)", ex);
			delDeptError++;
			return;
		}
	}

	public void addUser(AbrsLdapSyn abrsLdapSyn) {
		String adId = abrsLdapSyn.getObjectGuid();
		String loginUserName = abrsLdapSyn.getObjectLoginname();
		String loginPassword = abrsLdapSyn.getObjectLoginname();
		String empName = abrsLdapSyn.getObjectDisplayname();
		String dpAdId = abrsLdapSyn.getObjectParentGuid();

		try {
			Person personHasHave = personService.getPersonByAd("adId", adId);

			if (personHasHave != null) {
				logger.error("==>添加人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
						+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,数据库中已经存在AD_ID为" + adId
						+ "的人员:" + personHasHave.getName() + "(empId=" + personHasHave.getId() + ")!!");
				addUserError++;
				return;
			}
			Dept dept = deptService.getDeptByAd("adId", dpAdId);

			if (dept != null) {
				//查询是否具有相同登录名称的人员
				Person personHasName = personService.get(loginUserName);
				if (personHasName != null) {
					logger.error("==>添加人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
							+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,没有找到或是找到多条loginUserName为"
							+ loginUserName + "的人员!!");
					addUserError++;
					return;
				} else {
					Person person = new Person();
					person.setAdId(adId);
					person.setDept(dept);
					person.setLoginName(loginUserName);
					person.setLoginPassword(loginPassword);
					person.setName(empName);
					person.setEnable("1");
					person.setCode(personService.getMaxPersonCode(personCodeSQL));
					personService.savePersonInitRolePer(person);
					this.delAdData(abrsLdapSyn.getId());// 新增人员成功删除AD中的数据
					logger.info("==>成功添加一条人员信息:" + empName);
					addUserSuccess++;
				}
			} else {
				logger.error("==>添加人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
						+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,没有找到或是找到多条AD_ID为" + dpAdId
						+ "的部门!!");
				addUserError++;
				return;
			}
		} catch (Exception ex) {
			logger.error("==>添加人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
					+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,出现异常!!)", ex);
			addUserError++;
			return;
		}
	}

	public void updataUser(AbrsLdapSyn abrsLdapSyn) {
		String adId = abrsLdapSyn.getObjectGuid();
		String loginUserName = abrsLdapSyn.getObjectLoginname();
		String loginPassword = abrsLdapSyn.getObjectLoginname();
		String empName = abrsLdapSyn.getObjectDisplayname();
		String dpAdId = abrsLdapSyn.getObjectParentGuid();

		try {
			Person person = personService.getPersonByAd("adId", adId);
			if (person != null) {
				Dept dept = deptService.getDeptByAd("adId", dpAdId);
				if (dept != null) {
					String oldDeptName = person.getDept().getName();
					String oldEmpName = person.getName();
					String oldLoginName = person.getLoginName();
					String oldPassword = person.getLoginPassword();
					person.setName(empName);
					person.setLoginName(loginUserName);
					person.setLoginPassword(loginPassword);
					person.setDept(dept);
					/*人员变换部门时，才会去除班组的信息*/
					if ((person.getDept().getAdId()).equals(dpAdId)) {
						person.setGroup(null);
					}
					personService.updatePersonAndRefreshPer(person);
					this.delAdData(abrsLdapSyn.getId());// 更新人员成功删除AD中的数据
					logger.info("==>成功更新一条人员信息:(" + oldEmpName + "," + oldLoginName + "," + oldPassword + ","
							+ oldDeptName + ")->(" + empName + "," + loginUserName + "," + loginPassword + ","
							+ dept.getName() + ")");
					uptUserSuccess++;
				} else {
					logger.error("==>更新人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
							+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,没有找到或是找到多条AD_ID为"
							+ dpAdId + "的部门!!");
					uptUserError++;
					return;
				}
			} else {
				logger.error("==>更新人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
						+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,没有找到或是找到多条AD_ID为" + adId
						+ "的人员!!");
				uptUserError++;
				return;
			}
		} catch (Exception ex) {
			logger.error("==>更新人员(adId=" + adId + ",empName=" + empName + ",loginUserName=" + loginUserName
					+ ",loginPassword=" + loginPassword + ",dpAdId=" + dpAdId + ")不成功,出现异常!!)", ex);
			uptUserError++;
			return;
		}
	}

	public void removeUser(AbrsLdapSyn abrsLdapSyn) {
		String adId = abrsLdapSyn.getObjectGuid();
		try {
			Person person = personService.getPersonByAd("adId", adId);
			if (person != null) {
				String oldEmpName = person.getName();
				person.setEnable("0");
				personService.save(person);
				this.delAdData(abrsLdapSyn.getId());// 删除人员成功删除AD中的数据
				logger.info("==>成功删除一条人员信息:" + oldEmpName);
				delUserSuccess++;
			}
		} catch (Exception ex) {
			logger.error("==>删除人员(adId=" + adId + ")不成功,出现异常!!)", ex);
			delUserError++;
			return;
		}
	}

	public String getResultReportMessage() {
		String result = "main(String[]) - 成功完成数据同步工作，共获取更新命令" + all + "条,处理" + dowork + "条有效信息.";
		if (addUserSuccess > 0) {
			result += "用户添加成功" + addUserSuccess + "条;";
		}
		if (addUserError > 0) {
			result += "用户添加失败" + addUserError + "条;";
		}
		if (uptUserSuccess > 0) {
			result += "用户更新成功" + uptUserSuccess + "条;";
		}
		if (uptUserError > 0) {
			result += "用户更新失败" + uptUserError + "条;";
		}
		if (delUserSuccess > 0) {
			result += "用户删除成功" + delUserSuccess + "条;";
		}
		if (delUserError > 0) {
			result += "用户删除失败" + delUserError + "条;";
		}
		if (addDeptSuccess > 0) {
			result += "部门添加成功" + addDeptSuccess + "条;";
		}
		if (addDeptError > 0) {
			result += "部门添加失败" + addDeptError + "条;";
		}
		if (uptDeptSuccess > 0) {
			result += "部门更新成功" + uptDeptSuccess + "条;";
		}
		if (uptDeptError > 0) {
			result += "部门更新失败" + uptDeptError + "条;";
		}
		if (delDeptSuccess > 0) {
			result += "部门删除成功" + delDeptSuccess + "条;";
		}
		if (delDeptError > 0) {
			result += "部门删除失败" + delDeptError + "条;";
		}
		return result;
	}

	/**/

	@Resource(name = "executeSynDAO")
	public void setExecuteSynDAO(ExecuteSynDAO executeSynDAO) {
		this.executeSynDAO = executeSynDAO;
	}

	@Autowired
	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	@Autowired
	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	@Autowired
	public void setOpenSessionUtil(OpenSessionUtil openSessionUtil) {
		this.openSessionUtil = openSessionUtil;
	}

}
