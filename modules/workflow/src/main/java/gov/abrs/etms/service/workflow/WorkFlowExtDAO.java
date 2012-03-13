package gov.abrs.etms.service.workflow;

import gov.abrs.etms.model.baseinfo.Person;

import java.util.List;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

public interface WorkFlowExtDAO {

	public void reviveProcessInstence(ProcessInstance processInstance, String backToTaskNode);

	/* type=2 */
	public List<TaskInstance> findEndedTaskInstances(Person person);

	/* type=0 *//* type=1 */
	public List<TaskInstance> findNoStartedAndStartedTaskInstances(Person person);
}
