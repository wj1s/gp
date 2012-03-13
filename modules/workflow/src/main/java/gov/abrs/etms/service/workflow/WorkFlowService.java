package gov.abrs.etms.service.workflow;

import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.service.workflow.WorkFlowServiceImpl.JbpmCommand;

import java.util.Date;
import java.util.List;

import org.jbpm.graph.exe.Comment;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;

public interface WorkFlowService {
	public static final String BACKTRNAME = "驳回";
	public static final String ENDABNTRNAME = "未引起事故";

	public Object execute(final JbpmCommand command);

	/*开始新流程*/
	public ProcessInstance startProcessInstance(String processName, String keyWord, String piDesc, Person person,
			String comment);

	/*开始新流程*/
	public ProcessInstance startProcessInstance(String processName, String keyWord, String piDesc, Person person,
			String comment, Dept deptPer, FunModule funModule);

	/*查找流程实例当前正在活动的任务实例*/
	public TaskInstance findActiveTaskInstance(ProcessInstance pi);

	/*确认在某个正在进行的流程实例中，是否有当前人员的活动任务实例*/
	public boolean hasActiveTaskInstance(ProcessInstance processInstance, Person person);

	/*获取一个任务实例的描述信息*/
	public String getProcessInstanceDesc(ProcessInstance processInstance);

	public ProcessInstance getProcessInstance(String id);

	public ProcessInstance getProcessInstance(String processName, String key);

	public List<ProcessInstance> getProcessInstances(int taskType, Person person);

	public List<String> getComments(ProcessInstance processInstance);

	public List<String> getComments(String taskInstanceId);

	public void startTaskInstance(TaskInstance taskInstance);

	public void endTaskInstance(Long taskInstanceId, String empName, String comment);

	public void endTaskInstance(Long taskInstanceId, String empName, String comment, Date time);

	public void endTaskInstance(Long taskInstanceId);

	/*按指定路径结束一个任务实例，将任务推向下一个节点*/
	public void endTaskInstance(String taskInstanceId, String transitionName);

	public void endAbnTaskInstance(final Long taskInstanceId, final String empName, final String comment);

	public void endTaskInstance(final Long taskInstanceId, final String transitionName, final Person person,
			final String comment);

	public void backTaskInstance(Long taskInstanceId, String empName, String comment);

	public void backTaskInstance(Long taskInstanceId, String empName, String comment, Date time);

	public String getRedirectURL(TaskInstance taskInstance);

	public void reviveProcessInstence(ProcessInstance processInstance, String backToTaskNode);

	public List<Comment> getCommentList(ProcessInstance processInstance);

	/* 根据流程名称及关键字获取其流程实例的所有评论（对象）*/
	public List<Comment> getCommentObjects(String processName, String key);

	public TaskInstance findTaskInstance(final String taskInstanceId);

	public Boolean isAuditedByMp(String workflowId);

	//判断事故是否经过某个节点
	public Boolean ifHaveTaskByName(String workflowId, String taskInstanceName);
}
