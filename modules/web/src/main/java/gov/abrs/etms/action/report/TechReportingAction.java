package gov.abrs.etms.action.report;

import gov.abrs.etms.action.util.GridAction;
import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.para.Dir;
import gov.abrs.etms.model.rept.ProcessEnum;
import gov.abrs.etms.model.rept.ReptTechDtl;
import gov.abrs.etms.model.rept.TechReptDef;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.report.TechReptDefService;
import gov.abrs.etms.service.report.TechReptDtlService;
import gov.abrs.etms.service.util.UtilService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

@Results({ @Result(name = "backToTop", type = "redirect", location = "../index/index!toIndex.action") })
public class TechReportingAction extends GridAction<TechReptDef> {
	private Long taskId;
	private File formFile;

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

	private Map formMap = new HashMap();//动态form

	public Map getFormMap() {
		return formMap;
	}

	public void setFormMap(Map _map) {
		this.formMap = _map;
	}

	private String reptTime;

	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	private TechReptDefService techReptDefService;
	private TechReptDtlService techReptDtlService;

	@Autowired
	public void setTechReptDefService(TechReptDefService techReptDefService) {
		this.techReptDefService = techReptDefService;
	}

	@Autowired
	public void setTechReptDtlService(TechReptDtlService techReptDtlService) {
		this.techReptDtlService = techReptDtlService;
	}

	private UtilService utilService;

	@Override
	@Autowired
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}

	//初始填报界面
	public String toSubmitTechReport() {
		String id = reptTime;
		TechReptDef tech = techReptDefService.get(Long.parseLong(id));
		List dirList = paraDtlService.get(Dir.class);
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("TECH")) {
				formMap.put("TECHPara", d.getCodeDesc());
			}
			if (d.getParaCode().equalsIgnoreCase("PATT")) {
				formMap.put("PATTPara", d.getCodeDesc());
			}
		}
		formMap.put("dirList", dirList);
		formMap.put("tech", tech);
		return "toSubmitTechReport";
	}

	//初始填报
	public String submitTechReport() {
		//添加更新时间和更新人
		String id = ((String[]) formMap.get("id"))[0] + "";
		TechReptDef instance = techReptDefService.get(Long.parseLong(id));
		instance.setReptFlag(true);
		techReptDefService.save(instance);
		startWorkFlow(instance);
		return "backToTop";
	}

	//初始重新上报界面
	public String toReSubmitTechReport() {
		TechReptDef tech = techReptDefService.get(id);
		List dirList = paraDtlService.get(Dir.class);
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("TECH")) {
				formMap.put("TECHPara", d.getCodeDesc());
			}
			if (d.getParaCode().equalsIgnoreCase("PATT")) {
				formMap.put("PATTPara", d.getCodeDesc());
			}
		}
		String workflowId = taskId + "";
		formMap.put("workflowId", workflowId);
		List<String> comments = workFlowService.getComments(taskId + "");
		formMap.put("comments", comments);
		formMap.put("dirList", dirList);
		formMap.put("tech", tech);
		return "toReSubmitTechReport";
	}

	//重新上报
	public String reSubmitTechReport() {
		//添加更新时间和更新人
		String workflowId = ((String[]) formMap.get("workflowId"))[0] + "";//存放workflowid
		workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), "重新上报");
		return "backToTop";
	}

	//初始审核界面
	public String toAuditTechReport() {
		TechReptDef tech = techReptDefService.get(id);
		List dirList = paraDtlService.get(Dir.class);
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("TECH")) {
				formMap.put("TECHPara", d.getCodeDesc());
			}
			if (d.getParaCode().equalsIgnoreCase("PATT")) {
				formMap.put("PATTPara", d.getCodeDesc());
			}
		}
		String workflowId = taskId + "";
		formMap.put("workflowId", workflowId);
		List<String> comments = workFlowService.getComments(taskId + "");
		formMap.put("comments", comments);
		formMap.put("dirList", dirList);
		formMap.put("tech", tech);
		return "toAuditTechReport";
	}

	//审核
	public String auditTechReport() {
		String id = ((String[]) formMap.get("id"))[0] + "";
		String workflowId = ((String[]) formMap.get("workflowId"))[0] + "";//存放workflowid
		String ifBackFlag = ((String[]) formMap.get("ifBackFlag"))[0] + "";
		String checkContent = ((String[]) formMap.get("checkContent"))[0] + "";
		if (ifBackFlag.equals("1")) {
			TechReptDef instance = techReptDefService.get(Long.parseLong(id));
			//更新审核人
			List<ReptTechDtl> reptTechDtls = instance.getReptTechDtl();
			for (int i = 0; i < reptTechDtls.size(); i++) {
				ReptTechDtl rtd = reptTechDtls.get(i);
				rtd.setGovernor(getCurUser().getName());
			}
			techReptDefService.save(instance);
			if (checkContent == null) {
				checkContent = "批准审核";
			}
			workFlowService.endTaskInstance(Long.parseLong(workflowId), getCurUser().getName(), checkContent);
		} else {
			if (checkContent == null) {
				checkContent = "驳回审核";
			}
			workFlowService.endTaskInstance(Long.parseLong(workflowId), "驳回", getCurUser(), checkContent);
		}
		return "backToTop";
	}

	//初始详细信息界面
	public String toPreviewTechReport() {
		if (formMap.get("id") != null) {
			id = Long.parseLong(((String[]) formMap.get("id"))[0] + "");//获取id		
		}
		TechReptDef tech = techReptDefService.get(id);
		List dirList = paraDtlService.get(Dir.class);
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("TECH")) {
				formMap.put("TECHPara", d.getCodeDesc());
			}
			if (d.getParaCode().equalsIgnoreCase("PATT")) {
				formMap.put("PATTPara", d.getCodeDesc());
			}
		}
		formMap.put("dirList", dirList);
		formMap.put("tech", tech);
		return "toPreviewTechReport";
	}

	//初始列表查询界面
	public String toQueryTechReport() {
		return "toQueryTechReport";
	}

	@Override
	public String load() {
		try {
			TechReptDef instance = new TechReptDef();
			if (formMap.get("startDate") != null && !(((String[]) formMap.get("startDate"))[0]).equals("")) {
				instance.setStartDate(DateUtil.stringToDate(((String[]) formMap.get("startDate"))[0] + "",
						DateUtil.FORMAT));
			}
			if (((formMap.get("endDate"))) != null && !(((String[]) formMap.get("endDate"))[0]).equals("")) {
				instance.setEndDate(DateUtil.stringToDate(((String[]) formMap.get("endDate"))[0] + "", DateUtil.FORMAT));
			}
			//更新人不能为空
			instance.setReptFlag(true);//列表中的一定是编辑过的			
			techReptDefService.findByInstance(instance, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GRID;
	}

	//新建流程并提交
	public void startWorkFlow(TechReptDef instance) {
		Person person = getCurUser();// 任务描述
		String discription = "上报时间：" + DateUtil.dateToString(instance.getUpdDate(), DateUtil.FORMAT) + ",报表名称："
				+ instance.getName();
		workFlowService.startProcessInstance(ProcessEnum.REPT_TECH.getDataSource(), instance.getId() + "", discription,
				person, "提交审核", person.getDept(), FunModule.ACCD);
	}

	//删除文件
	public String delLoadOne() throws Exception {
		String id = ((String[]) formMap.get("id"))[0] + "";//获取id
		ReptTechDtl rt = techReptDtlService.get(Long.parseLong(id));//dtl
		JSONObject jsonObject = new JSONObject();
		try {
			if (deleteFile(rt.getTechReptDef().getId() + rt.getTechReptDef().getName(), rt.getSaveName())) {//删除文件				
				rt.setTekofficer(getCurUser().getName());//更新技办人 和上传名称和实际存放的名称
				rt.setUploadName(null);
				rt.setSaveName(null);
				rt.setUpdDate(DateUtil.dateToDateByFormat(utilService.getSysTime(), DateUtil.FORMAT));
				techReptDtlService.save(rt);
			} else {
				jsonObject.put("result", false);
				json = jsonObject.toString();
				return EASY;
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
		String id = ((String[]) formMap.get("id"))[0] + "";//获取id	
		String uploadName = ((String[]) formMap.get("uploadName"))[0] + "";//获取id	
		ReptTechDtl rt = techReptDtlService.get(Long.parseLong(id));//dtl
		try {
			String targetDirectory = getTechPath() + rt.getTechReptDef().getId() + rt.getTechReptDef().getName();
			String temArr[] = uploadName.split("\\.");
			String fileName = UUID.randomUUID().toString();// 读取
			if (temArr.length != 0) {
				fileName = fileName + "." + temArr[temArr.length - 1];
			}
			File target = new File(targetDirectory, fileName);
			FileUtils.copyFile(formFile, target);
			//更新技办人 和上传名称和实际存放的名称
			rt.setTekofficer(getCurUser().getName());//
			rt.setUploadName(uploadName);
			rt.setSaveName(fileName);
			rt.setUpdDate(DateUtil.dateToDateByFormat(utilService.getSysTime(), DateUtil.FORMAT));
			techReptDtlService.save(rt);
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
		String id = ((String[]) formMap.get("id"))[0] + "";//获取id
		String ifBackFlag = ((String[]) formMap.get("ifBackFlag"))[0] + "";//下载模版还是下载附件 -TECH附件 PATT模版
		ReptTechDtl rt = techReptDtlService.get(Long.parseLong(id));//dtl
		String path = "";//path
		String tempName = "";
		List dirList = paraDtlService.get(Dir.class);//获取事故性质的list
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if ((d.getParaCode().equalsIgnoreCase("PATT")) && (ifBackFlag.equals("PATT"))) {
				path = d.getCodeDesc() + rt.getReptPattern().getPatternName();//路径
				tempName = rt.getAttachName();//名
			}
			if ((d.getParaCode().equalsIgnoreCase("TECH")) && (ifBackFlag.equals("TECH"))) {
				path = d.getCodeDesc() + rt.getTechReptDef().getId() + rt.getTechReptDef().getName() + "\\"
						+ rt.getSaveName();//附件路径
				tempName = rt.getUploadName();//名
			}
		}
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		list = new ArrayList();
		list.add(tempName);
		list.add(file);
		return FILE;
	}

	//保存文件
	private boolean addFile(InputStream inputStream, String forderName, String fileName) {
		int byteread = 0;
		forderName = getTechPath() + forderName;
		File file = new File(forderName);
		//判断文件夹是否存在、不存在则新建、新建后保存文件
		if (file.exists()) {
			file = new File(forderName + "\\" + fileName);
		} else {
			file.mkdir();
			file = new File(forderName + "\\" + fileName);
		}
		try {
			FileOutputStream fs = new FileOutputStream(file);
			byte[] buffer = new byte[256];
			while ((byteread = inputStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			inputStream.close();
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//删除磁盘上文件
	private boolean deleteFile(String forderName, String fileName) {
		try {
			fileName = getTechPath() + forderName + "\\" + fileName;
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

	//获取路径
	private String getTechPath() {
		List dirList = paraDtlService.get(Dir.class);//获取事故性质的list
		String path = "";
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("TECH")) {
				path = d.getCodeDesc();
			}
		}
		return path;
	}

}
