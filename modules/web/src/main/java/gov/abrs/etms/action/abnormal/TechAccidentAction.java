package gov.abrs.etms.action.abnormal;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.abnormal.TechAccident;
import gov.abrs.etms.model.abnormal.TechAccidentH;
import gov.abrs.etms.model.abnormal.TechAccidentMedia;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.baseinfo.Station;
import gov.abrs.etms.model.para.Dir;
import gov.abrs.etms.model.para.TechAccdCode;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.abnormal.TechAccidentHService;
import gov.abrs.etms.service.abnormal.TechAccidentMediaService;
import gov.abrs.etms.service.abnormal.TechAccidentService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.baseinfo.StationService;
import gov.abrs.etms.service.util.UtilService;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results({ @Result(name = "success", type = "redirect", location = "abnormal.action"),
		@Result(name = "backToTop", type = "redirect", location = "../index/index!toIndex.action") })
public class TechAccidentAction extends GridAction<TechAccident> {
	private static final long serialVersionUID = 3825432269846307373L;
	private static final String PageTool = null;
	private Long taskId;
	private String saveName;
	private String showName;
	private File formFile;
	private String addEditFlag;
	private String uploadName;
	private Map formMap = new HashMap();//动态form

	@Autowired
	private TechAccidentMediaService techAccidentMediaService;
	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	private TechAccidentService techAccidentService;

	@Autowired
	public void setTechAccidentService(TechAccidentService techAccidentService) {
		this.techAccidentService = techAccidentService;
	}

	private UtilService utilService;

	@Override
	@Autowired
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	private StationService stationService;

	@Override
	@Autowired
	public void setStationService(StationService stationService) {
		this.stationService = stationService;
	}

	private TechAccidentHService techAccidentHService;

	@Autowired
	public void setTechAccidentHService(TechAccidentHService techAccidentHService) {
		this.techAccidentHService = techAccidentHService;
	}

	//形成新增界面
	public String toInsert() {
		List kindList = paraDtlService.get(TechAccdCode.class);//获取事故性质的lists
		formMap.put("kindList", kindList);
		return "toInsert";//返回toinsert
	}

	//插入事故
	public String insertTechAccident() {
		Date today = DateUtil.dateToDateByFormat(utilService.getSysTime(), DateUtil.FORMAT);
		TechAccident instance = new TechAccident();//新建一个事故
		Station st = stationService.getStation();
		instance.setAccdCode("A" + st.getCode() + DateUtil.dateToString(today, "yyyyMMddHHmmss"));//事故编码
		instance.setStation(st);//台站编号
		instance.setStartTime(DateUtil.stringToDate(((String[]) formMap.get("startTime"))[0] + "", DateUtil.FORMAT));
		instance.setEndDate(DateUtil.stringToDate(((String[]) formMap.get("endDate"))[0] + "", DateUtil.FORMAT));
		instance.setHappenLocation(((String[]) formMap.get("happenLocation"))[0] + "");
		instance.setDutyPerson(((String[]) formMap.get("dutyPerson"))[0] + "");
		instance.setAccdKind(((String[]) formMap.get("accdKind"))[0] + "");
		instance.setAccdCourse(((String[]) formMap.get("accdCourse"))[0] + "");
		instance.setAccdReason(((String[]) formMap.get("accdReason"))[0] + "");
		instance.setAccdResult(((String[]) formMap.get("accdResult"))[0] + "");
		instance.setAccdManage(((String[]) formMap.get("accdManage"))[0] + "");
		instance.setAccdPrev(((String[]) formMap.get("accdPrev"))[0] + "");
		instance.setEndFlag(((String[]) formMap.get("endFlag"))[0] + "");
		instance.setUpdName(getCurUser().getName());
		instance.setUpdDate(today);
		techAccidentService.save(instance);//保存
		if (saveName != null && !saveName.equals("")) {
			String saveNameArr[] = saveName.split("!1103!");
			String showNameArr[] = showName.split("!1103!");
			for (int i = 0; i < saveNameArr.length; i++) {
				TechAccidentMedia techAccidentMedia = new TechAccidentMedia();
				techAccidentMedia.setFileName(showNameArr[i]);
				techAccidentMedia.setSaveName(saveNameArr[i]);
				techAccidentMedia.setTechAccident(instance);
				techAccidentMediaService.save(techAccidentMedia);
			}
		}
		startWorkFlow(instance);
		//跳转到已办流程界面
		return "backToTop";//返回已办列表
	}

	//形成编辑界面
	public String toUpdate() {//获取事故信息		
		TechAccident instance = techAccidentService.get(id);//获取
		List kindList = paraDtlService.get(TechAccdCode.class);//获取事故性质的lists	
		for (int i = 0; i < kindList.size(); i++) {
			TechAccdCode t = (TechAccdCode) kindList.get(i);
			if (t.getParaCode().equalsIgnoreCase(instance.getAccdKind())) {
				formMap.put("accdKindShow", t.getCodeDesc());
			}
		}
		formMap.put("kindList", kindList);
		formMap.put("workflowId", taskId + "");
		List<String> comments = workFlowService.getComments(taskId + "");
		formMap.put("comments", comments);
		formMap.put("techAccident", instance);
		//不断迭代判断前一个节点是否来自技术安全事故后果更新(技办)如果有则跳转后果审核否则跳转toUpdateH否则跳转到toUpdate
		boolean ifR = workFlowService.ifHaveTaskByName(taskId + "", "技术安全事故后果更新");
		formMap.put("rFlag", "0");//设置事故性质list
		if (ifR) {
			return "toUpdateH";//返回toinsert
		} else {
			return "toUpdate";//返回toinsert
		}
	}

	//形成重新上报界面
	public String toUpdateR() {//获取事故信息			
		TechAccident instance = techAccidentService.get(id);//获取
		List kindList = paraDtlService.get(TechAccdCode.class);//获取事故性质的lists		
		for (int i = 0; i < kindList.size(); i++) {
			TechAccdCode t = (TechAccdCode) kindList.get(i);
			if (t.getParaCode().equalsIgnoreCase(instance.getAccdKind())) {
				formMap.put("accdKindShow", t.getCodeDesc());
			}
		}
		String workflowId = taskId + "";
		formMap.put("workflowId", workflowId);
		List<String> comments = workFlowService.getComments(taskId + "");
		formMap.put("comments", comments);
		formMap.put("techAccident", instance);
		formMap.put("kindList", kindList);
		formMap.put("rFlag", "1");//设置事故性质list
		boolean ifR = workFlowService.ifHaveTaskByName(taskId + "", "技术安全事故后果更新");
		if (ifR) {
			return "toUpdateH";//返回toinsert
		} else {
			return "toReUpdate";//返回toinsert
		}
	}

	//编辑技术安全事故：单位领导审核；单位领导驳回；技办再次提交；
	public String updateTechAccident() {
		String rFlag = ((String[]) formMap.get("rFlag"))[0] + "";
		if (rFlag.equals("1")) {//重新上报的话
			String id = ((String[]) formMap.get("id"))[0] + "";//获取id
			TechAccident oldInstance = techAccidentService.get(Long.parseLong(id));//获取
			if (!(oldInstance.getAccdResult() + "").equals(((String[]) formMap.get("accdResult"))[0] + "")) {//选择性插入历史记录
				//将原来的插入进去
				TechAccidentH tah = new TechAccidentH();
				tah.setAccdResult(oldInstance.getAccdResult() + "");
				tah.setTechAccident(oldInstance);
				tah.setUpdDate(oldInstance.getUpdDate());
				tah.setUpdName(oldInstance.getUpdName());
				techAccidentHService.save(tah);
				oldInstance.setUpdName(getCurUser().getName());
				oldInstance.setUpdDate(DateUtil.dateToDateByFormat(utilService.getSysTime(), DateUtil.FORMAT));
			}
			oldInstance.setStartTime(DateUtil.stringToDate(((String[]) formMap.get("startTime"))[0] + "",
					DateUtil.FORMAT));
			oldInstance.setEndDate(DateUtil.stringToDate(((String[]) formMap.get("endDate"))[0] + "", DateUtil.FORMAT));
			oldInstance.setHappenLocation(((String[]) formMap.get("happenLocation"))[0] + "");
			oldInstance.setDutyPerson(((String[]) formMap.get("dutyPerson"))[0] + "");
			oldInstance.setAccdKind(((String[]) formMap.get("accdKind"))[0] + "");
			oldInstance.setAccdCourse(((String[]) formMap.get("accdCourse"))[0] + "");
			oldInstance.setAccdReason(((String[]) formMap.get("accdReason"))[0] + "");
			oldInstance.setAccdResult(((String[]) formMap.get("accdResult"))[0] + "");
			oldInstance.setAccdManage(((String[]) formMap.get("accdManage"))[0] + "");
			oldInstance.setAccdPrev(((String[]) formMap.get("accdPrev"))[0] + "");
			oldInstance.setEndFlag(((String[]) formMap.get("endFlag"))[0] + "");
			techAccidentService.save(oldInstance); //techAccidentService.modify(instance);
		}
		//判断结束标识跳转流程
		String workflowId = ((String[]) formMap.get("workflowId"))[0] + ""; //存放workflowid
		String ifBackFlag = ((String[]) formMap.get("ifBackFlag"))[0] + "";
		if (rFlag.equals("1")) {
			workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), "重新上报");
		} else {
			if (ifBackFlag.equals("1")) {
				workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), "批准审核");
			} else {
				workFlowService.endTaskInstance(Long.parseLong(workflowId), "驳回", getCurUser(), "驳回审核");
			}
		}
		return "backToTop";//返回已办列表
	}

	//形成重新上报结果界面
	public String toUpdateH() {
		//获取事故信息		
		TechAccident instance = techAccidentService.get(id);//获取
		List kindList = paraDtlService.get(TechAccdCode.class);//获取事故性质的lists		
		for (int i = 0; i < kindList.size(); i++) {
			TechAccdCode t = (TechAccdCode) kindList.get(i);
			if (t.getParaCode().equalsIgnoreCase(instance.getAccdKind())) {
				formMap.put("accdKindShow", t.getCodeDesc());
			}
		}
		String workflowId = taskId + "";
		formMap.put("workflowId", workflowId);
		List<String> comments = workFlowService.getComments(taskId + "");
		formMap.put("comments", comments);
		formMap.put("techAccident", instance);
		formMap.put("kindList", kindList);
		formMap.put("rFlag", "2");//设置事故性质list		
		//判断跳转到事故审核界面还是跳转到历史记录更新审核界面
		return "toUpdateH";
	}

	//重新上报结果：技办重新上报，单位领导审核，单位领导驳回
	public String updateTechAccidentH() {
		String rFlag = ((String[]) formMap.get("rFlag"))[0] + "";
		if (rFlag.equals("1") || rFlag.equals("2")) {
			String id = ((String[]) formMap.get("id"))[0] + "";//获取id
			TechAccident oldInstance = techAccidentService.get(Long.parseLong(id));//获取
			if (!(oldInstance.getAccdResult() + "").equals(((String[]) formMap.get("accdResult"))[0] + "")) {//选择性插入历史记录
				//将原来的插入进去
				TechAccidentH tah = new TechAccidentH();
				tah.setAccdResult(oldInstance.getAccdResult() + "");
				tah.setTechAccident(oldInstance);
				tah.setUpdDate(oldInstance.getUpdDate());
				tah.setUpdName(oldInstance.getUpdName());
				techAccidentHService.save(tah);
				oldInstance.setUpdName(getCurUser().getName());
				oldInstance.setUpdDate(DateUtil.dateToDateByFormat(utilService.getSysTime(), DateUtil.FORMAT));
			}
			oldInstance.setAccdResult(((String[]) formMap.get("accdResult"))[0] + "");
			oldInstance.setEndFlag(((String[]) formMap.get("endFlag"))[0] + "");
			techAccidentService.save(oldInstance); //	
		}
		String workflowId = ((String[]) formMap.get("workflowId"))[0] + "";
		String ifBackFlag = ((String[]) formMap.get("ifBackFlag"))[0] + "";
		if (rFlag.equals("1")) {
			workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), "重新上报");
		} else if (rFlag.equals("2")) {
			workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), "更新事故后果");
		} else {
			if (ifBackFlag.equals("1")) {
				workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), "批准审核");
			} else {
				workFlowService.endTaskInstance(Long.parseLong(workflowId), "驳回", getCurUser(), "驳回审核");
			}
		}
		return "backToTop";//返回已办列表
	}

	//查询界面
	public String showList() {
		List kindList = paraDtlService.get(TechAccdCode.class);//获取事故性质的lists	
		formMap.put("kindList", kindList);
		formMap.put("endFlag", "-1");
		formMap.put("accdKind", "-1");
		return "showList";
	}

	@Override
	public String load() {
		try {
			TechAccident instance = new TechAccident();
			if (formMap.get("startTime") != null && !(((String[]) formMap.get("startTime"))[0]).equals("")) {
				instance.setStartTime(DateUtil.stringToDate(((String[]) formMap.get("startTime"))[0] + "",
						DateUtil.FORMAT));
			}
			if (((formMap.get("endDate"))) != null && !(((String[]) formMap.get("endDate"))[0]).equals("")) {
				instance.setEndDate(DateUtil.stringToDate(((String[]) formMap.get("endDate"))[0] + "", DateUtil.FORMAT));
			}

			if (((formMap.get("endFlag"))) != null && !(((String[]) formMap.get("endFlag"))[0]).equals("")
					&& (!(((String[]) formMap.get("endFlag"))[0]).equals("-1"))) {
				instance.setEndFlag(((String[]) formMap.get("endFlag"))[0]);
			}
			if ((formMap.get("accdKind")) != null && !(((String[]) formMap.get("accdKind"))[0]).equals("")
					&& (!(((String[]) formMap.get("accdKind"))[0]).equals("-1"))) {
				instance.setAccdKind(((String[]) formMap.get("accdKind"))[0]);
			}
			//carrier.setPageSize(2);
			techAccidentService.findByInstance(instance, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GRID;
	}

	//查看
	public String viewTechAccident() {
		if (formMap.get("id") != null) {
			id = Long.parseLong(((String[]) formMap.get("id"))[0] + "");//获取id		
		}
		TechAccident instance = techAccidentService.get(id);//获取
		List kindList = paraDtlService.get(TechAccdCode.class);//获取事故性质的lists	
		for (int i = 0; i < kindList.size(); i++) {
			TechAccdCode t = (TechAccdCode) kindList.get(i);
			if (t.getParaCode().equalsIgnoreCase(instance.getAccdKind())) {
				formMap.put("accdKindShow", t.getCodeDesc());
			}
		}
		formMap.put("techAccident", instance);
		formMap.put("kindList", kindList);
		return "viewTechAccident";
	}

	//新建流程并提交
	public void startWorkFlow(TechAccident instance) {
		Person person = getCurUser();
		// 任务描述
		String discription = "上报时间：" + DateUtil.dateToString(instance.getUpdDate(), DateUtil.FORMAT) + ",事故编号："
				+ instance.getAccdCode();
		workFlowService.startProcessInstance(ProcessEnum.ACCD_TECH.getDataSource(), instance.getId() + "", discription,
				person, "提交审核", person.getDept(), FunModule.ACCD);

	}

	//删除文件
	public String delLoadOne() throws Exception {
		String saveName = ((String[]) formMap.get("fileName"))[0] + "";//获取id
		JSONObject jsonObject = new JSONObject();
		try {
			if (addEditFlag.equalsIgnoreCase("edit")) {// 编辑
				TechAccidentMedia instance = techAccidentMediaService.get(id);// 获取
				if (instance != null) {
					saveName = instance.getSaveName();
					if (deleteFile("", saveName)) {// 删除文件
						techAccidentMediaService.delete(instance.getId());
					} else {
						jsonObject.put("result", false);
						json = jsonObject.toString();
						return EASY;
					}
				} else {
					jsonObject.put("result", false);
					json = jsonObject.toString();
					return EASY;
				}
			} else {// 直接删
				if (!deleteFile("", saveName)) {// 删除文件
					jsonObject.put("result", false);
					json = jsonObject.toString();
					return EASY;
				}
			}
		} catch (Exception e) {
			jsonObject.put("result", false);
			json = jsonObject.toString();
			return EASY;
		}
		jsonObject.put("result", true);
		json = jsonObject.toString();
		return EASY;
	}

	//上传文件
	public String upLoadOne() throws Exception {
		JSONObject jsonObject = new JSONObject();
		try {
			uploadName = ((String[]) formMap.get("fileName"))[0] + "";//获取id
			String temArr[] = uploadName.split("\\.");
			String fileName = UUID.randomUUID().toString();// 读取
			if (temArr.length != 0) {
				fileName = fileName + "." + temArr[temArr.length - 1];
			}
			String targetDirectory = getPath();
			File target = new File(targetDirectory, fileName);
			FileUtils.copyFile(formFile, target);
			//更新技办人 和上传名称和实际存放的名称
			if (addEditFlag.equalsIgnoreCase("edit")) {// 编辑
				TechAccident instance = techAccidentService.get(id);// 获取
				if (instance != null) {
					TechAccidentMedia techAccidentMedia = new TechAccidentMedia();
					techAccidentMedia.setFileName(uploadName);
					techAccidentMedia.setSaveName(fileName);
					techAccidentMedia.setTechAccident(instance);
					techAccidentMediaService.save(techAccidentMedia);
					jsonObject.put("id", techAccidentMedia.getId().toString());
				} else {
					jsonObject.put("result", false);// 返回出错信息
					return EASYFILE;
				}
			}
			jsonObject.put("saveName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("result", false);
			json = jsonObject.toString();
			return EASYFILE;
		}
		jsonObject.put("result", true);
		json = jsonObject.toString();
		return EASYFILE;
	}

	//下载
	public String downLoadOne() throws Exception {
		showName = new String((showName).getBytes("ISO8859-1"), "UTF-8");// showName
		String path = getPath() + saveName;//path
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		list = new ArrayList();
		list.add(showName);
		list.add(file);
		return FILE;
	}

	// 删除磁盘上文件
	private boolean deleteFile(String forderName, String fileName) {
		try {
			fileName = getPath() + fileName;
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private String getPath() {
		List dirList = paraDtlService.get(Dir.class);//获取事故性质的list
		String path = "";
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("ACCD")) {
				path = d.getCodeDesc();
			}
		}
		return path;
	}

	public String getAddEditFlag() {
		return addEditFlag;
	}

	public void setAddEditFlag(String addEditFlag) {
		this.addEditFlag = addEditFlag;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public File getFormFile() {
		return formFile;
	}

	public void setFormFile(File formFile) {
		this.formFile = formFile;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Map getFormMap() {
		return formMap;
	}

	public void setFormMap(Map _map) {
		this.formMap = _map;
	}

}
