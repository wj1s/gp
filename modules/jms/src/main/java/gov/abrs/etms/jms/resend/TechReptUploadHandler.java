package gov.abrs.etms.jms.resend;

import gov.abrs.etms.model.para.Dir;
import gov.abrs.etms.model.rept.ReptTechDtl;
import gov.abrs.etms.model.rept.TechReptDef;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.report.TechReptDefService;
import gov.abrs.etms.service.report.TechReptDtlService;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;

public class TechReptUploadHandler implements ActionHandler {

	private static final Logger log = Logger.getLogger(TechReptUploadHandler.class);

	private SendDataService sendDataService;

	private TechHelper techHelper;

	@Autowired
	public void setTechHelper(TechHelper techHelper) {
		this.techHelper = techHelper;
	}

	private WorkFlowService workFlowService;

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	@Autowired
	public void setSendDataService(SendDataService sendDataService) {
		this.sendDataService = sendDataService;
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

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	@Override
	public void execute(ExecutionContext context) throws Exception {
		ProcessInstance pi = context.getContextInstance().getProcessInstance();//流程		
		TaskInstance taskInstance = workFlowService.findActiveTaskInstance(pi);
		String workflowId = taskInstance.getId() + "";
		String id = pi.getKey();//id		
		TechReptDef instance = techReptDefService.get(Long.parseLong(id));
		boolean flag = false;//flag
		try {
			List dirList = paraDtlService.get(Dir.class);
			String path = "";
			for (int i = 0; i < dirList.size(); i++) {
				Dir d = (Dir) dirList.get(i);
				if (d.getParaCode().equalsIgnoreCase("TECH")) {
					path = d.getCodeDesc();
				}
			}
			path = path + instance.getId() + instance.getName() + "\\";
			List<Map> fileList = new ArrayList();
			List<ReptTechDtl> reptTechDtls = instance.getReptTechDtl();
			for (int i = 0; i < reptTechDtls.size(); i++) {
				ReptTechDtl rt = reptTechDtls.get(i);
				Map map = new HashMap();
				map.put("json", (techHelper.assembleRept(rt)).toString());
				map.put("fileName", path + rt.getSaveName());
				fileList.add(map);
			}
			sendDataService.sendFileData(fileList, "REPTTECH");//放入队列
			log.info("调用服务,向局端上报" + instance.getName());
			flag = true;
		} catch (Exception e) {
			log.info("上报" + instance.getName() + "不成功!");
			log.info("错误信息为" + e);
		}
		if (flag) {
			try {
				workFlowService.endTaskInstance(Long.parseLong(workflowId));
				log.info(instance.getName() + "已成功上报至局端!");
			} catch (Exception e) {
				e.printStackTrace();
				log.info("错误信息为" + e);
			} finally {}
		} else {
			log.info("上报" + instance.getName() + "不成功,等待1分钟后下一次重发!");
		}
	}
}
