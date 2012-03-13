package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.ProgramInCawave;
import gov.abrs.etms.service.baseinfo.CawaveService;
import gov.abrs.etms.service.baseinfo.ProgramService;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;

public class CawaveAction extends CrudAction<Cawave> {

	private static final long serialVersionUID = -6515264761126217793L;

	private String programNames;
	private Cawave cawave;

	//	@Override
	//	protected void beforeUpdate(Cawave model) {
	//		model.getProgramInCawaves().clear();
	//	}
	//
	//	@Override
	//	protected void preSave(Cawave model) {
	//		List<ProgramInCawave> picList = new ArrayList<ProgramInCawave>();
	//		if (!programNames.equals("")) {
	//			picList = new ArrayList<ProgramInCawave>();
	//			String[] pgNames = programNames.split(",");
	//			for (int i = 0; i < pgNames.length; i++) {
	//				ProgramInCawave pic = new ProgramInCawave(this.programService.getByName(pgNames[i]), this.getCurDate(),
	//						null);
	//				pic.setCawave(model);
	//				picList.add(pic);
	//			}
	//		}
	//		if (model.getProgramInCawaves() == null) {
	//			model.setProgramInCawaves(picList);
	//		} else {
	//			model.getProgramInCawaves().addAll(picList);
	//		}
	//		model.setEmpName(this.getCurUser().getName());
	//		model.setUpdDate(this.getCurDate());
	//	}

	@Override
	public String save() {
		String[] pgNames = programNames.split(",");
		if (model.getId() == null) {
			try {
				List<ProgramInCawave> picList = new ArrayList<ProgramInCawave>();
				if (!programNames.equals("")) {
					picList = new ArrayList<ProgramInCawave>();
					for (int i = 0; i < pgNames.length; i++) {
						ProgramInCawave pic = new ProgramInCawave(this.programService.getByName(pgNames[i]), this
								.getCurDate(), null);
						pic.setCawave(model);
						picList.add(pic);
					}
				}
				model.setProgramInCawaves(picList);
				model.setEmpName(this.getCurUser().getName());
				model.setUpdDate(this.getCurDate());
				cawaveService.save(model);
				return RIGHT;
			} catch (Exception e) {
				e.printStackTrace();
				return WRONG;
			}
		} else {
			try {
				//首先判断原来有的现在没有的，说明这个节目不在节目流中
				for (ProgramInCawave pic : model.getProgramInCawaves()) {
					Boolean flag = false;
					for (int i = 0; i < pgNames.length; i++) {
						if (pic.getProgram().getId() == programService.getByName(pgNames[i]).getId()) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						pic.setEndDate(getCurDate());
					}
				}
				//再判断现在有的原来没有，说明是新增
				List<ProgramInCawave> newList = Lists.newArrayList();
				for (int i = 0; i < pgNames.length; i++) {
					Boolean flag = false;
					for (ProgramInCawave pic : model.getProgramInCawaves()) {
						if (pic.getProgram().getId() == programService.getByName(pgNames[i]).getId()) {
							flag = true;
							break;
						}
					}
					if (!flag) {
						ProgramInCawave newPic = new ProgramInCawave();
						newPic.setCawave(model);
						newPic.setProgram(programService.getByName(pgNames[i]));
						newPic.setStartDate(getCurDate());
						newList.add(newPic);
					}
				}
				model.getProgramInCawaves().addAll(newList);
				cawaveService.save(model);
				return RIGHT;
			} catch (Exception e) {
				e.printStackTrace();
				return WRONG;
			}
		}
	}

	public String show() {
		return SUCCESS;
	}

	public String input() {
		if (id != null) {
			cawave = this.cawaveService.get(id);
		}
		programNames = cawaveService.getCurProgramsStr(cawave);
		return "input";
	}

	@Override
	public String load() throws Exception {
		try {
			executeDAO.find(entityClass, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONArray jsonArray = new JSONArray();
		if (carrier.getResult() != null) {
			for (Cawave cawave : carrier.getResult()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", cawave.getId());
				jsonObject.put("name", cawave.getName());
				jsonObject.put("programInCawaves", cawaveService.getCurProgramsStr(cawave));
				jsonArray.add(jsonObject);
			}
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("page", carrier.getCurrentPage());
		jsonObj.put("total", carrier.getTotalPage());
		jsonObj.put("records", carrier.getTotalSize());
		jsonObj.put("data", jsonArray.toString());
		json = jsonObj.toString();
		return EASY;
	}

	private ProgramService programService;
	private CawaveService cawaveService;

	@Autowired
	public void setProgramService(ProgramService programService) {
		this.programService = programService;
	}

	@Autowired
	public void setCawaveService(CawaveService cawaveService) {
		this.cawaveService = cawaveService;
	}

	public String getProgramNames() {
		return programNames;
	}

	public void setProgramNames(String programNames) {
		this.programNames = programNames;
	}

	public Cawave getCawave() {
		return cawave;
	}

	public void setCawave(Cawave cawave) {
		this.cawave = cawave;
	}
}
