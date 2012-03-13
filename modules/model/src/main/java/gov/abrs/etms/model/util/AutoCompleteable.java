package gov.abrs.etms.model.util;

import gov.abrs.etms.model.baseinfo.Dept;

import java.util.List;

public interface AutoCompleteable {
	String getAutoCompleteJson();

	List<Dept> getDeptsPopedom();
}
