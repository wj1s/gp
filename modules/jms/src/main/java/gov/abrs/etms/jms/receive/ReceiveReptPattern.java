package gov.abrs.etms.jms.receive;

import gov.abrs.etms.model.para.Dir;
import gov.abrs.etms.model.rept.ReptPattern;
import gov.abrs.etms.service.baseinfo.ParaDtlService;
import gov.abrs.etms.service.report.ReptPatternService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

//更新模版库
public class ReceiveReptPattern {

	private static final Logger log = Logger.getLogger(ReceiveReptPattern.class);
	private ReptPatternService reptPatternService;

	@Autowired
	public void setReptPatternService(ReptPatternService reptPatternService) {
		this.reptPatternService = reptPatternService;
	}

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	//更新模版库
	public void doProcess(InputStream inputStream, JSONObject json) {
		String flag = json.getString("flag");
		String id = json.getString("id");//id
		String patternName = json.getString("patternName");//patternName
		ReptPattern reptPattern = reptPatternService.findBySeq(id);
		try {
			if (flag.equalsIgnoreCase("A")) {
				//新增模版
				if (reptPattern == null) {
					//正常情况
					if (addFile(inputStream, patternName)) {//执行方法保存文件
						reptPattern = new ReptPattern();
						reptPattern.setSeq(Long.parseLong(id));
						reptPattern.setPatternName(patternName);
						reptPatternService.save(reptPattern);
					}
				} else {
					//由于网络原因导致已经接受到模版，但没有给局端返回导致局端重发
					//更新模版文件即可
					if (deleteFile(patternName)) {
						addFile(inputStream, patternName);
					}
				}
				log.info(reptPattern.getPatternName() + " 模版定义下发成功!");
			} else if (flag.equalsIgnoreCase("U")) {
				//更新模版
				if (reptPattern == null) {
					//正常情况
					if (addFile(inputStream, patternName)) {//执行方法保存文件
						reptPattern = new ReptPattern();
						reptPattern.setSeq(Long.parseLong(id));
						reptPattern.setPatternName(patternName);
						reptPatternService.save(reptPattern);
					}
				} else {
					//更新模版文件即可
					if (deleteFile(patternName)) {
						addFile(inputStream, patternName);
					}
				}
				log.info(reptPattern.getPatternName() + " 模版定义更新成功!");
			} else if (flag.equalsIgnoreCase("D")) {
				//删除模版文件
				if (reptPattern == null) {
					//已经删除过了直接返回删除成功
					log.info(json.getString("patternName") + " 模版定义删除成功!");
				} else {
					//如果被报表引用则不能删除
					if ((reptPattern.getReptTechDtl()).size() != 0) {
						//不能删除数据
						log.info(json.getString("patternName") + " 模版被报表引用不能删除!");
					} else {
						//先删除模版
						//执行删除数据
						if (deleteFile(patternName)) {
							reptPatternService.delete(reptPattern.getId());
						}
						log.info(json.getString("patternName") + " 模版定义删除成功!");
					}
				}
			} else if (flag.equalsIgnoreCase("U_DTL")) {
				//改原来文件的文件名
				if (reptPattern == null) {
					if (addFile(inputStream, json.getString("patternNameNew"))) {//执行方法保存文件
						reptPattern = new ReptPattern();
						reptPattern.setSeq(Long.parseLong(id));
						reptPattern.setPatternName(json.getString("patternNameNew"));
						reptPatternService.save(reptPattern);
					}
				} else {
					if (updateFile(patternName, json.getString("patternNameNew"))) {
						reptPattern.setPatternName(json.getString("patternNameNew"));
						reptPatternService.delete(reptPattern.getId());
					}
				}
				log.info(json.getString("patternName") + " 模版单一细节更新成功!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.info(json.getString("patternName") + "更新失败!");
			log.info(e.toString());
		}
	}

	//获取路径
	private String getPath() {
		List dirList = paraDtlService.get(Dir.class);
		String path = "";
		for (int i = 0; i < dirList.size(); i++) {
			Dir d = (Dir) dirList.get(i);
			if (d.getParaCode().equalsIgnoreCase("PATT")) {
				path = d.getCodeDesc();
			}
		}
		return path;
	}

	//保存文件
	private boolean addFile(InputStream inputStream, String fileName) {
		try {
			int byteread = 0;
			fileName = getPath() + fileName;
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
				file = new File(fileName);
			}
			FileOutputStream fs = new FileOutputStream(file);
			byte[] buffer = new byte[256];
			while ((byteread = inputStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
			inputStream.close();
			fs.close();
		} catch (Exception e) {
			log.info("复制" + fileName + " 出错!");
			log.info(e.toString());
			return false;
		}
		return true;
	}

	//删除磁盘上文件
	private boolean deleteFile(String fileName) {
		try {
			fileName = getPath() + fileName;
			File file = new File(fileName);
			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			log.info("删除 " + fileName + " 出错!");
			log.info(e.toString());
			return false;
		}
		return true;
	}

	//给文件改个名
	private boolean updateFile(String fileName, String newName) {
		try {
			fileName = getPath() + fileName;
			newName = getPath() + newName;
			File file = new File(fileName);
			File newFile = new File(newName);
			if (file.exists()) {
				file.renameTo(newFile);
			}
		} catch (Exception e) {
			log.info(fileName + "改名为 " + newName + " 出错!");
			log.info(e.toString());
			return false;
		}
		return true;
	}
}
