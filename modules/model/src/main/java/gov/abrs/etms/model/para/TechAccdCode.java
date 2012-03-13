package gov.abrs.etms.model.para;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * 任务类型:VI_TMS_SYS_PARA_DTL_TADU
 *  PARA_CODE     CODE_DESC     SORTBY    
 *  ------------  ------------  --------- 
 *  T1            技术安全未遂事故          1         
 *  T2            技术安全事故          2         
 *  T3            重大伤亡事故            0         
 *
 */
@Entity
@DiscriminatorValue(value = "TADU")
public class TechAccdCode extends ParaDtl {

	public TechAccdCode() {
		super();
	}

	public TechAccdCode(Long id) {
		super(id);
	}
}