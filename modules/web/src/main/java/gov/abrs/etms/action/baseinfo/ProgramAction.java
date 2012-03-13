package gov.abrs.etms.action.baseinfo;

import gov.abrs.etms.action.util.CrudAction;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.model.para.ParaDtl;
import gov.abrs.etms.model.para.ProgramType;
import gov.abrs.etms.service.baseinfo.ParaDtlService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class ProgramAction extends CrudAction<Program> {
	private static final long serialVersionUID = -6515264761126217793L;

	private String programTypeList;

	@Override
	protected void preSave(Program model) {
		model.setEmpName(this.getCurUser().getName());
		model.setUpdDate(this.getCurDate());
	}

	public String show() throws Exception {
		List<ParaDtl> programTypes = this.paraDtlService.get(ProgramType.class);
		programTypeList = this.assemblyJsonArray(programTypes, "codeDesc");
		return SUCCESS;
	}

	private ParaDtlService paraDtlService;

	@Autowired
	public void setParaDtlService(ParaDtlService paraDtlService) {
		this.paraDtlService = paraDtlService;
	}

	public String getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(String programTypeList) {
		this.programTypeList = programTypeList;
	}

}
