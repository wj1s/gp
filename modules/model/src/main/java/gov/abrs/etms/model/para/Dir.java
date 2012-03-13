package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 任务类型:VI_TMS_SYS_PARA_DTL_DIR
 *  PARA_CODE     CODE_DESC     SORTBY    
 *  ------------  ------------  --------- 
 *  PATT            E:\pattern\          1         
 *  TECH            E:\tech\             1  
 *
 */
@Entity
@DiscriminatorValue(value = "DIR")
public class Dir extends ParaDtl {

	public Dir() {
		super();
	}

	public Dir(Long id) {
		super(id);
	}
}