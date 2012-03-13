package gov.abrs.etms.jms.receive;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.rept.ReptPattern;
import gov.abrs.etms.model.rept.ReptTechDtl;
import gov.abrs.etms.model.rept.TechReptDef;
import gov.abrs.etms.service.report.ReptPatternService;
import gov.abrs.etms.service.report.TechReptDefService;
import gov.abrs.etms.service.report.TechReptDtlService;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiveReptList {

	private static final Logger log = Logger.getLogger(ReceiveReptList.class);
	private ReptPatternService reptPatternService;

	@Autowired
	public void setReptPatternService(ReptPatternService reptPatternService) {
		this.reptPatternService = reptPatternService;
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

	public void doProcess(JSONObject json) {
		String flag = json.getString("flag");
		String id = json.getString("id");//id
		String reptName = json.getString("reptName");
		TechReptDef reptList = this.techReptDefService.findBySeq(id);
		try {
			if (flag.equalsIgnoreCase("A")) {
				//新增
				if (reptList == null) {
					//正常情况
					reptList = new TechReptDef();
					addRept(reptList, json);
				} else {
					//由于网络原因导致已经接受到，但没有给局端返回导致局端重发
					if (reptList.getReptFlag() != null) {
						log.info(reptName + " 进入上报流程不能重新定义!");
					} else {
						editRept(reptList, json);
					}
				}
				log.info(reptName + " 定义下发成功!");
			} else if (flag.equalsIgnoreCase("U")) {
				//更新
				if (reptList == null) {
					//由于网络原因导致之前下发的没有收到
					reptList = new TechReptDef();
					addRept(reptList, json);
				} else {
					//正常更新应该先调用存储过程删除以前的REPT
					if (reptList.getReptFlag() != null) {
						log.info(reptName + " 进入上报流程不能重新定义!");
					} else {
						editRept(reptList, json);
					}
				}
				log.info(reptName + " 定义更新成功!");
			} else if (flag.equalsIgnoreCase("D")) {
				if (reptList == null) {
					//已经删除过了直接返回删除成功
					log.info(reptName + " 定义删除成功!");
				} else {
					//执行删除方法 删除之
					if (reptList.getReptFlag() != null) {
						log.info(reptName + " 进入上报流程不能删除!");
					} else {
						deleteRept(reptList);
						log.info(reptName + " 定义删除成功!");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(reptName + " 定义操作失败!");
			log.info(e.toString());
		}
	}

	//新增
	private boolean addRept(TechReptDef reptList, JSONObject json) {
		JSONArray list = json.getJSONArray("reptTechDtls");
		reptList.setReptSeq(Long.parseLong(json.getString("id")));
		//reptList.setReptType(json.getString("reptType"));
		reptList.setName(json.getString("reptName"));
		reptList.setReptTime(json.getString("reptTime"));
		reptList.setStartDate(DateUtil.stringToDate(json.getString("startDate"), DateUtil.FORMAT));
		reptList.setEndDate(DateUtil.stringToDate(json.getString("endDate"), DateUtil.FORMAT));
		reptList.setSubmitTime(DateUtil.stringToDate(json.getString("submitDate"), DateUtil.FORMAT));
		reptList.setPromtTime(DateUtil.stringToDate(json.getString("promtTime"), DateUtil.FORMAT));
		reptList.setUpdDate(DateUtil.stringToDate(json.getString("updDate"), DateUtil.FORMAT));
		reptList.setUpdName(json.getString("updEmp"));
		//reptList.setReptName(json.getString("reptName"));
		techReptDefService.save(reptList);
		for (int i = 0; i < list.size(); i++) {
			JSONObject jsonDtl = list.getJSONObject(i);
			ReptTechDtl rt = new ReptTechDtl();
			rt.setTechReptDef(reptList);
			String patternId = jsonDtl.getString("patternId");
			if (patternId != null) {
				ReptPattern reptPattern = reptPatternService.findBySeq(patternId);
				if (reptPattern != null) {
					rt.setReptPattern(reptPattern);
				}
			}
			rt.setAttachName(jsonDtl.getString("attachName"));
			techReptDtlService.save(rt);
		}
		return true;
	}

	//编辑-删除后新增
	private boolean editRept(TechReptDef reptList, JSONObject json) {
		deleteRept(reptList);
		reptList = new TechReptDef();
		addRept(reptList, json);
		return true;
	}

	//删除
	private boolean deleteRept(TechReptDef reptList) {
		List<ReptTechDtl> reptTechDtls = reptList.getReptTechDtl();
		for (int i = reptTechDtls.size() - 1; i >= 0; i--) {//是否有必要这么做
			ReptTechDtl rt = reptTechDtls.get(i);
			techReptDtlService.delete(rt.getId());
		}
		techReptDefService.delete(reptList.getId());
		return true;
	}
}
