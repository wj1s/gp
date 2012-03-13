package gov.abrs.etms.service.baseinfo;

import gov.abrs.etms.model.baseinfo.Cawave;
import gov.abrs.etms.model.baseinfo.Program;
import gov.abrs.etms.model.baseinfo.ProgramInCawave;
import gov.abrs.etms.service.exception.CanNotDeleteException;
import gov.abrs.etms.service.util.CrudService;
import gov.abrs.etms.service.util.UtilService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CawaveService extends CrudService<Cawave> {
	@Override
	//支持级联保存通过program，startDate,endDate初始化的ProgramInCawave对象
	public void save(Cawave cawave) {
		for (ProgramInCawave pic : cawave.getProgramInCawaves()) {
			pic.setCawave(cawave);
		}
		dao.save(cawave);
	}

	@Override
	public void delete(Long id) {
		Cawave cawave = dao.get(id);
		if (cawave.getOperationTs().size() > 0 || cawave.getOperationSs().size() > 0) {
			throw new CanNotDeleteException("被删除节目流包含业务信息，请清除所有关联业务后，删除");
		} else {
			dao.delete(id);
		}
	}

	public List<Program> getCurrentPrograms(Cawave cawave) {
		Date date = utilService.getSysTime();
		List<Program> currentPrograms = new ArrayList<Program>();
		List<ProgramInCawave> programInCawaves = cawave.getProgramInCawaves();
		if (programInCawaves != null) {
			for (ProgramInCawave pic : programInCawaves) {
				//判断所有开始时间小于等于当前时间，并且结束时间大于等于当前时间或结束时间为空的节目
				if ((date.after(pic.getStartDate()) || date.equals(pic.getStartDate()))
						&& (pic.getEndDate() == null || date.before(pic.getEndDate()) || date.equals(pic.getEndDate()))) {
					currentPrograms.add(pic.getProgram());
				}
			}
		}
		return currentPrograms;
	}

	public String getCurProgramsStr(Cawave cawave) {
		String str = "";
		for (Program program : getCurrentPrograms(cawave)) {
			str += program.getName() + ",";
		}
		if (!str.equals("")) {
			return str.substring(0, str.length() - 1);
		} else {
			return "";
		}
	}

	private UtilService utilService;

	@Autowired
	public void setUtilService(UtilService utilService) {
		this.utilService = utilService;
	}
}
