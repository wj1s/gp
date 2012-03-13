package gov.abrs.etms.jms.resend;

import gov.abrs.etms.model.abnormal.TechAccident;
import gov.abrs.etms.model.abnormal.TechAccidentMedia;
import gov.abrs.etms.model.para.Dir;
import gov.abrs.etms.service.abnormal.TechAccidentService;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class TechUploadHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7487752889769516667L;

	private static final Logger log = Logger.getLogger(TechUploadHandler.class);

	private SendDataService sendDataService;

	private WorkFlowService workFlowService;
	private ParaDtlService paraDtlService;
	private TechHelper techHelper;
	private TechAccidentService techAccidentService;

	@Override
	public void execute(ExecutionContext context) throws Exception {
		ProcessInstance pi = context.getContextInstance().getProcessInstance();//流程		
		TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
		String workflowId = taskInstance.getId() + "";
		String id = pi.getKey();//id
		TechAccident instance = techAccidentService.get(Long.parseLong(id));//获取实例		
		boolean flag = false;//flag		
		try {
			boolean ifR = workFlowService.ifHaveTaskByName(workflowId, "技术安全事故后果更新");
			String type = "TECH";
			if (ifR) {
				type = "TECHR";
			} else {
				type = "TECH";
			}
			String path = getPath();
			List<Map> fileList = new ArrayList();
			List<TechAccidentMedia> techAccidentMedias = instance.getTechAccidentMedias();
			JSONObject obj = techHelper.assembleAccd(instance, type);//设计json
			if (techAccidentMedias.size() == 0) {// 没有附件的情况下
				sendDataService.sendData(obj, "ACCDTECH");// 放入队列
			} else {
				for (TechAccidentMedia techAccidentMedia : techAccidentMedias) {
					obj.put("fileName", techAccidentMedia.getFileName());
					obj.put("saveName", techAccidentMedia.getSaveName());
					String jsonStr = obj.toString();
					Map map = new HashMap();
					map.put("json", jsonStr);
					map.put("fileName", path + techAccidentMedia.getSaveName());
					fileList.add(map);
				}
				sendDataService.sendFileData(fileList, "ACCDTECH");// 放入队列
			}
			log.info("调用服务,向局端上报编号" + instance.getAccdCode() + "的技术安全事故!");
			flag = true;
		} catch (Exception e) {
			log.info("编号" + instance.getAccdCode() + "的技术安全事故上报不成功!");
			log.info("错误信息为" + e);
		}

		//flag = true;//flag
		if (flag) {
			try {
				workFlowService.startTaskInstance(taskInstance);
				if ((instance.getEndFlag()).equalsIgnoreCase("Y")) {
					workFlowService.endTaskInstance(Long.parseLong(workflowId));
				} else {
					workFlowService.endTaskInstance(workflowId, "更新事故后果");//更新事故后果					
				}
				log.info("编号" + instance.getAccdCode() + "的技术安全事故已成功上报至局端!");
			} catch (Exception e) {
				e.printStackTrace();
				log.info("错误信息为" + e);
			} finally {}
		} else {
			log.info("编号" + instance.getAccdCode() + "的技术安全事故上报不成功,等待1分钟后下一次重发!");
		}
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

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@Autowired
	public void setTechHelper(TechHelper techHelper) {
		this.techHelper = techHelper;
	}

	@Autowired
	public void setSendDataService(SendDataService sendDataService) {
		this.sendDataService = sendDataService;
	}

	@Autowired
	public void setTechAccidentService(TechAccidentService techAccidentService) {
		this.techAccidentService = techAccidentService;
	}

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

}