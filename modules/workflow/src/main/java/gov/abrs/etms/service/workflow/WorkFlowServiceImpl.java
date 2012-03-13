package gov.abrs.etms.service.workflow;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.baseinfo.Person;
import gov.abrs.etms.model.sys.DeptPer;
import gov.abrs.etms.model.sys.FunModule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.db.GraphSession;
import org.jbpm.db.TaskMgmtSession;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.Comment;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.job.Job;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class WorkFlowServiceImpl implements WorkFlowService {
	public interface JbpmCommand {
		public Object execute(JbpmContext jbpmContext);
	}

	public WorkFlowServiceImpl() {}

	private JbpmConfiguration jbpmConfiguration;
	private WorkFlowExtDAO workFlowExtDAO;

	@Autowired
	public void setWorkFlowExtDAO(WorkFlowExtDAO workFlowExtDAO) {
		this.workFlowExtDAO = workFlowExtDAO;
	}

	@Autowired
	public void setJbpmConfiguration(JbpmConfiguration jbpmConfiguration) {
		this.jbpmConfiguration = jbpmConfiguration;
	}

	@Override
	public Object execute(final JbpmCommand command) {
		JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();
		try {
			Object result = command.execute(jbpmContext);
			return result;
		} finally {
			jbpmContext.close();
		}
	}

	@Override
	public ProcessInstance startProcessInstance(final String processName, final String keyWord, final String piDesc,
			final Person person, final String comment) {
		return startProcessInstance(processName, keyWord, piDesc, person, comment, null, null);
	}

	@Override
	public ProcessInstance startProcessInstance(final String processName, final String keyWord, final String piDesc,
			final Person person, final String comment, final Dept deptPer, final FunModule funModule) {
		Assert.notNull(processName, "新建流程实例时流程定义名称不能为空");
		Assert.notNull(keyWord, "新建流程实例时keyWord不能为空");
		Assert.notNull(piDesc, "新建流程实例时piDesc不能为空");
		String process = ProcessNameMapping.getTaskNameMapping(processName);
		boolean hasProcess = process == null || "".equals(process) ? false : true;
		Assert.isTrue(hasProcess, "新建的流程实例必须包含在NameMapp中");
		return (ProcessInstance) execute(new JbpmCommand() {
			@Override
			public ProcessInstance execute(JbpmContext jbpmContext) {
				GraphSession graphSession = jbpmContext.getGraphSession();
				ProcessDefinition processDefinition = graphSession.findLatestProcessDefinition(ProcessNameMapping
						.getTaskNameMapping(processName));
				ProcessInstance pi;
				if (processDefinition != null) {
					pi = processDefinition.createProcessInstance();
					ContextInstance ci = pi.getContextInstance();
					if (deptPer != null && deptPer.getId() != null) {
						ci.setVariable("dpId", deptPer.getId() + "");
					}
					if (piDesc != null && !"".equals(piDesc)) {
						ci.setVariable("taskDescription", piDesc);
					}
					if (funModule != null) {
						ci.setVariable("funModuleKey", funModule.getKey());
					}
					pi.setKey(keyWord);
					//开始流程实例的开始节点任务
					TaskInstance taskInstance = pi.getTaskMgmtInstance().createStartTaskInstance();
					if (taskInstance != null) {
						taskInstance.start(taskInstance.getTask().getActorIdExpression());
						Comment taskComment = new Comment(person.getName(), comment);
						taskInstance.addComment(taskComment);
						taskInstance.end();
					} else {
						throw new RuntimeException(ProcessNameMapping.getTaskNameMapping(processName) + "流程还没有发布!");
					}
				} else {
					throw new RuntimeException(ProcessNameMapping.getTaskNameMapping(processName) + "流程还没有发布!");
				}
				return pi;
			}
		});
	}

	@Override
	public TaskInstance findActiveTaskInstance(final ProcessInstance pi) {
		return (TaskInstance) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext context) {
				List<TaskInstance> taskInstances = context.getTaskMgmtSession().findTaskInstancesByProcessInstance(pi);
				if (taskInstances != null && taskInstances.size() == 1) {
					return taskInstances.get(0);
				} else if (taskInstances == null || taskInstances.size() == 0) {
					return null;
				} else {
					throw new RuntimeException("流程实例存在多个活动任务,目前并不支持。");
				}
			}
		});
	}

	@Override
	public boolean hasActiveTaskInstance(ProcessInstance processInstance, Person person) {
		TaskInstance taskInstance = this.findActiveTaskInstance(processInstance);
		if (taskInstance != null && person.getActorIds().contains(taskInstance.getActorId())) {
			String dpId = (String) processInstance.getContextInstance().getVariable("dpId");
			String funModuleKey = (String) processInstance.getContextInstance().getVariable("funModuleKey");
			if (funModuleKey != null && !("".equals(funModuleKey)) && dpId != null && !("".equals(dpId))) {//如果有数据权限控制，则必须满足数据权限要求才可以
				for (DeptPer deptPer : person.getDeptPers()) {
					if (funModuleKey.equals(deptPer.getFunModuleKey()) && dpId.equals(deptPer.getDept().getId() + "")) {
						return true;
					}
				}
				return false;
			} else {//如果没有数据权限控制，则有角色就可以看到
				return true;
			}
		} else {
			return false;
		}
	}

	@Override
	public String getProcessInstanceDesc(ProcessInstance processInstance) {
		return (String) processInstance.getContextInstance().getVariable("taskDescription");
	}

	@Override
	public ProcessInstance getProcessInstance(final String id) {
		ProcessInstance result = (ProcessInstance) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				GraphSession graphSession = jbpmContext.getGraphSession();
				ProcessInstance processInstance = graphSession.loadProcessInstance(Long.valueOf(id));
				return processInstance;
			}
		});
		return result;

	}

	@Override
	public void startTaskInstance(final TaskInstance taskInstance) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				TaskInstance ti = jbpmContext.getTaskInstance(taskInstance.getId());
				if (ti.getStart() == null) {
					ti.start();
				}
				return null;
			}
		});
	}

	@Override
	public String getRedirectURL(final TaskInstance taskInstance) {
		String result = (String) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				StringBuffer redirectURL = new StringBuffer();
				TaskInstance ti = jbpmContext.getTaskInstance(taskInstance.getId());
				String str = TaskUrlMapping.getTaskUrl(ti.getDescription());
				redirectURL.append(str);
				if (redirectURL.indexOf("?") > 0) {
					redirectURL.append("&taskId=");
				} else {
					redirectURL.append("?taskId=");
				}
				redirectURL.append(ti.getId());
				redirectURL.append("&id=");
				redirectURL.append(ti.getProcessInstance().getKey());
				return redirectURL.toString();
			}
		});
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getComments(final ProcessInstance processInstance) {
		List result = (List) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				List remarks = new ArrayList<String>();
				List comments = processInstance.getRootToken().getComments();
				for (int i = 0; i < comments.size(); i++) {
					Comment comment = (Comment) comments.get(i);
					StringBuffer comentsBuffer = new StringBuffer();
					String recoder = comment.getActorId();
					comentsBuffer.append(DateUtil.dateToString(comment.getTime(), DateUtil.FORMAT_DAYTIME)
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					if (recoder == null)
						recoder = "系统提示";
					comentsBuffer.append(recoder);
					comentsBuffer.append("：");
					comentsBuffer.append(comment.getMessage());

					remarks.add(comentsBuffer.toString());
				}

				return remarks;
			}
		});
		return result;
	}

	@Override
	public List<ProcessInstance> getProcessInstances(final int taskType, final Person person) {
		List<TaskInstance> tasks = new ArrayList<TaskInstance>();
		switch (taskType) {
		case 0:
			tasks.addAll(workFlowExtDAO.findNoStartedAndStartedTaskInstances(person));
			break;
		case 1:
			tasks = workFlowExtDAO.findEndedTaskInstances(person);
			break;
		}
		tasks = doFilterWithDeptPer(tasks, person.getDeptPers());//
		List<ProcessInstance> processInstances = new ArrayList<ProcessInstance>();
		List<Long> ids = new ArrayList<Long>();
		for (TaskInstance taskInstance : tasks) {
			ProcessInstance pi = taskInstance.getProcessInstance();
			Long id = pi.getId();
			if (!ids.contains(id)) {
				processInstances.add(pi);
				ids.add(id);
			}
		}
		return processInstances;
	}

	@Override
	public void endTaskInstance(final Long taskInstanceId, final String empName, final String comment) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (!"".equals(taskInstanceId)) {
					try {
						TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceId);
						if (!taskInstance.hasEnded()) {
							taskInstance.end();
						} else {
							System.out.println("任务" + taskInstanceId + "已经结束");
						}
						Comment taskComment = new Comment(empName, comment);
						taskInstance.addComment(taskComment);
						jbpmContext.save(taskInstance);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("任务ID为空");
				}
				return null;
			}
		});
	}

	@Override
	public void endTaskInstance(final Long taskInstanceId, final String empName, final String comment, final Date time) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (!"".equals(taskInstanceId)) {
					try {
						TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceId);
						if (!taskInstance.hasEnded()) {
							taskInstance.end();
						} else {
							System.out.println("任务" + taskInstanceId + "已经结束");
						}
						Comment taskComment = new Comment(empName, comment);
						taskComment.setTime(time);
						taskInstance.addComment(taskComment);
						jbpmContext.save(taskInstance);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("任务ID为空");
				}
				return null;
			}
		});
	}

	/**
	 * 采用默认路径结束一个任务
	 * 
	 * @param taskInstanceId
	 */
	@Override
	public void endTaskInstance(final Long taskInstanceId) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				try {
					TaskInstance ti = jbpmContext.getTaskInstance(taskInstanceId);
					if (!ti.hasEnded()) {
						ti.end();
					} else {
						System.out.println("任务#" + taskInstanceId + "已结束！");
					}
					jbpmContext.save(ti);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	/*按指定路径结束一个任务实例，将任务推向下一个节点*/
	@Override
	/**
	 * 根据指定流转路径结束一个任务
	 * 
	 * @param taskInstanceId
	 * @param transitionName
	 */
	public void endTaskInstance(final String taskInstanceId, final String transitionName) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (taskInstanceId == null || "".equals(taskInstanceId)) {
					System.out.println("在结束任务时没有找到相应的任务实例taskInstanceId=" + taskInstanceId);
					return null;
				}
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));
				if (!taskInstance.hasEnded()) {
					try {
						if (transitionName == null || "null".equals(transitionName))
							taskInstance.end();
						else {
							boolean hasTrans = false;
							List transitionList = taskInstance.getAvailableTransitions();
							for (int i = 0; i < transitionList.size(); i++) {
								Transition transition = (Transition) transitionList.get(i);
								if (transitionName.equals(transition.getName())) {
									hasTrans = true;
									break;
								}
							}
							if (hasTrans) {
								taskInstance.end(transitionName);
								jbpmContext.save(taskInstance);
							} else {
								System.out.println("没有找到相应的Transition：" + transitionName);
							}
						}
					} catch (RuntimeException e) {
						System.out.print("[工作流调用异常]关键字为：《");
						System.out.print(transitionName);
						System.out.println("》");
						taskInstance.setVariable("transation", transitionName);
					}
				}
				return null;
			}
		});
	}

	@Override
	public void endAbnTaskInstance(final Long taskInstanceId, final String empName, final String comment) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (!"".equals(taskInstanceId)) {
					try {
						TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceId);
						if (!taskInstance.hasEnded()) {
							taskInstance.end(WorkFlowService.ENDABNTRNAME);
						} else {
							System.out.println("任务" + taskInstanceId + "已经结束");
						}
						Comment taskComment = new Comment(empName, comment);
						taskInstance.addComment(taskComment);
						jbpmContext.save(taskInstance);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("任务ID为空");
				}
				return null;
			}
		});
	}

	@Override
	public void backTaskInstance(final Long taskInstanceId, final String empName, final String comment) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (!"".equals(taskInstanceId)) {
					try {
						TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceId);
						if (!taskInstance.hasEnded()) {
							taskInstance.end(WorkFlowService.BACKTRNAME);
						} else {
							System.out.println("任务" + taskInstanceId + "已经结束");
						}
						Comment taskComment = new Comment(empName, comment);
						taskInstance.addComment(taskComment);
						jbpmContext.save(taskInstance);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("任务ID为空");
				}
				return null;
			}
		});
	}

	@Override
	public void backTaskInstance(final Long taskInstanceId, final String empName, final String comment, final Date time) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (!"".equals(taskInstanceId)) {
					try {
						TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceId);
						if (!taskInstance.hasEnded()) {
							taskInstance.end(WorkFlowService.BACKTRNAME);
						} else {
							System.out.println("任务" + taskInstanceId + "已经结束");
						}
						Comment taskComment = new Comment(empName, comment);
						taskComment.setTime(time);
						taskInstance.addComment(taskComment);
						jbpmContext.save(taskInstance);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("任务ID为空");
				}
				return null;
			}
		});
	}

	@Override
	public void endTaskInstance(final Long taskInstanceId, final String transitionName, final Person person,
			final String comment) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				if (taskInstanceId == null || "".equals(taskInstanceId)) {
					System.out.println("任务ID为空");
					return null;
				}
				TaskInstance taskInstance = jbpmContext.getTaskInstance(taskInstanceId);
				if (!taskInstance.hasEnded()) {
					try {
						if (transitionName == null || "null".equals(transitionName))
							taskInstance.end();
						else {
							boolean hasTrans = false;
							List transitionList = taskInstance.getAvailableTransitions();
							for (int i = 0; i < transitionList.size(); i++) {
								Transition transition = (Transition) transitionList.get(i);
								if (transitionName.equals(transition.getName())) {
									hasTrans = true;
									break;
								}
							}
							if (hasTrans) {
								Comment taskComment = new Comment(person.getName(), comment);
								taskInstance.addComment(taskComment);
								taskInstance.end(transitionName);
								jbpmContext.save(taskInstance);
							} else {
								System.out.println("没有" + transitionName + "这个transition");
							}
						}
					} catch (RuntimeException e) {
						System.out.print("流程在前往");
						System.out.print(transitionName);
						System.out.println("这个transition时出错了");
						taskInstance.setVariable("transation", transitionName);
					}
				}
				return null;
			}
		});
	}

	/**
	 * 重新激活流程实例到某一个节点
	 * 
	 * @param processInstance
	 * @param backToTaskNode
	 */
	@Override
	public void reviveProcessInstence(final ProcessInstance processInstance, final String backToTaskNode) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				workFlowExtDAO.reviveProcessInstence(processInstance, backToTaskNode);
				return null;
			}
		});
	}

	//	@Override
	//	public void reviveProcessInstence(final String processName, final String key, final String backToTaskNode) {
	//		final ProcessInstance processInstance = this.findProcessInstance(processName, key);
	//		execute(new JbpmCommand() {
	//			public Object execute(JbpmContext jbpmContext) {
	//				workFlowExtDAO.reviveProcessInstence(processInstance, backToTaskNode);
	//				return null;
	//			}
	//		});
	//	}
	//
	//	@Override
	//	public void reviveProcessInstence(final ProcessInstance processInstance, final String backToTaskNode) {
	//		execute(new JbpmCommand() {
	//			public Object execute(JbpmContext jbpmContext) {
	//				workFlowExtDAO.reviveProcessInstence(processInstance, backToTaskNode);
	//				return null;
	//			}
	//		});
	//	}

	/**
	 * ���ID��ȡ����
	 * @param taskInstanceId
	 * @return
	 */
	@Override
	public TaskInstance findTaskInstance(final String taskInstanceId) {
		return (TaskInstance) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));
				return taskInstance;
			}
		});
	}

	/**
	 * ���������Ƽ��ؼ��ֻ�ȡ������ʵ����������ۣ�����
	 * 
	 * @param processName
	 * @param key
	 * @return
	 */
	@Override
	public List<Comment> getCommentObjects(String processName, String key) {
		ProcessInstance processInstance = this.getProcessInstance(processName, key);
		List<Comment> comments = processInstance.getRootToken().getComments();
		return comments;
	}

	@Override
	public List<Comment> getCommentList(ProcessInstance processInstance) {
		List<Comment> comments = processInstance.getRootToken().getComments();
		return comments;
	}

	/**
	 * ��ȡ������������ʵ�����������(�ַ�LIST)
	 * 
	 * @param taskInstanceId
	 * @return
	 */
	@Override
	public List<String> getComments(final String taskInstanceId) {
		List result = (List) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				List remarks = new ArrayList();
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));

				List comments = taskInstance.getToken().getComments();
				for (int i = 0; i < comments.size(); i++) {
					Comment comment = (Comment) comments.get(i);
					StringBuffer comentsBuffer = new StringBuffer();
					String recoder = comment.getActorId();
					comentsBuffer.append(DateUtil.dateToString(comment.getTime(), DateUtil.FORMAT_DAYTIME)
							+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					if (recoder == null)
						recoder = "系统提示";
					comentsBuffer.append(recoder);
					comentsBuffer.append("：");
					comentsBuffer.append(comment.getMessage());

					remarks.add(comentsBuffer.toString());
				}

				return remarks;
			}
		});
		return result;
	}

	/**
	 * ���������Ƽ��ؼ��ֻ�ȡ������ʵ����������ۣ��ַ�LIST��
	 * 
	 * @param processName
	 * @param key
	 * @return
	 */
	public List<String> getComments(String processName, String key) {
		List remarks = new ArrayList();
		ProcessInstance processInstance = this.getProcessInstance(processName, key);

		List comments = processInstance.getRootToken().getComments();
		for (int i = 0; i < comments.size(); i++) {
			Comment comment = (Comment) comments.get(i);
			StringBuffer comentsBuffer = new StringBuffer();
			String recoder = comment.getActorId();
			comentsBuffer.append(DateUtil.dateToString(comment.getTime(), DateUtil.FORMAT_DAYTIME)
					+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			if (recoder == null)
				recoder = "系统提示";
			comentsBuffer.append(recoder);
			comentsBuffer.append("：");
			comentsBuffer.append(comment.getMessage());

			remarks.add(comentsBuffer.toString());
		}
		return remarks;
	}

	/**
	 * �������ID��ȡ����ʵ���KEY����������ʵ���KEY�в��ң����û�� ��������ʵ��ı����в��Ҽ�λkeyWord�ı�����ֵ
	 * 
	 * @param taskInstanceId
	 * @return
	 */
	public String getKey(final String taskInstanceId) {
		String result = (String) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));

				String key = taskInstance.getProcessInstance().getKey();
				if (key == null) {
					ContextInstance ci = taskInstance.getProcessInstance().getContextInstance();
					key = (String) ci.getVariable("keyWord");
				}
				return key;
			}
		});
		return result;
	}

	// /**
	// * ��ѯ��������������ΪprocessDescription�����ڽ��е�����ʹ����˵�ԭʼ״̬
	// *
	// * @author liceyoo
	// * @param processDescription
	// * ���̶����������������ͣ�
	// * @return true-�ɹ� false-ʧ��
	// */
	// public boolean rollback(final String processDescription,
	// final String reportDate) {
	// Boolean result = (Boolean) execute(new TaskManagerCallback() {
	// public Object doInTaskManagerBean(JbpmContext jbpmContext) {
	// TaskMgmtSession taskMgmtSession = jbpmContext
	// .getTaskMgmtSession();
	//
	// List taskList = taskMgmtSession
	// .findAllNotEndedByDescriptionAndNodeName(
	// processDescription, reportDate);
	// for (int i = 0; i < taskList.size(); i++) {
	// TaskInstance ti = (TaskInstance) taskList.get(i);
	// List transitionList = ti.getAvailableTransitions();
	// if (transitionList.size() > 1) {// ����Ҫ������ƹ�����ʱ��Ҫ����淶
	// if (ti.getStart() == null) {// �������δ������������
	// ti.start();
	// }
	// ti.addComment("�ֶ˲��أ�����������ˣ�");
	// ti.end("back");
	//
	// }
	// }
	//
	// return true;
	// }
	// });
	// return result;
	// }

	/**
	 * �������ID��ȡ��������������ʵ������÷�Χ�ڲ��ң����û����������ʵ��ķ�Χ�ڲ���
	 * 
	 * @param taskInstanceId
	 * @param variableName
	 * @return
	 */
	public Object getVariable(final String taskInstanceId, final String variableName) {
		return execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				Object obj = null;
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));

				obj = taskInstance.getVariable(variableName);
				if (obj == null) {
					ContextInstance ci = taskInstance.getProcessInstance().getContextInstance();
					obj = ci.getVariable(variableName);
				}
				return obj;
			}
		});
	}

	/**
	 * Ϊ����ʵ��ﶥһ��������ֵ
	 * 
	 * @param processInstanceId
	 * @param variableName
	 * @param variableValue
	 */
	public void setVariableForProcessInstance(final Long processInstanceId, final String variableName,
			final Object variableValue) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				ProcessInstance pi = jbpmContext.loadProcessInstance(processInstanceId.longValue());
				ContextInstance ci = pi.getContextInstance();
				ci.setVariable(variableName, variableValue);
				jbpmContext.save(pi);
				return null;
			}
		});
	}

	/**
	 * �������ID��Ϊ������������ʵ���һ��������ֵ
	 */
	public void setVariableForProcessInstanceWithTaskInstance(final String taskInstanceId, final String variableName,
			final Object variableValue) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));

				ContextInstance ci = taskInstance.getProcessInstance().getContextInstance();
				ci.setVariable(variableName, variableValue);
				jbpmContext.save(ci.getProcessInstance());
				return null;
			}
		});
	}

	/**
	 * Ϊ����ʵ���һ��������ֵ
	 * 
	 * @param taskInstanceId
	 * @param variableName
	 * @param variableValue
	 */
	public void setVariable(final String taskInstanceId, final String variableName, final Object variableValue) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				TaskMgmtSession taskMgmtSession = jbpmContext.getTaskMgmtSession();
				TaskInstance taskInstance = taskMgmtSession.getTaskInstance(Long.parseLong(taskInstanceId));

				taskInstance.setVariable(variableName, variableValue);
				jbpmContext.save(taskInstance);
				return null;
			}
		});
	}

	/**
	 * ������̶���͹ؼ��ֲ�������ʵ��
	 * 
	 * @param processName
	 * @param key
	 * @return
	 */
	@Override
	public ProcessInstance getProcessInstance(final String processName, final String key) {
		ProcessInstance result = (ProcessInstance) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				GraphSession graphSession = jbpmContext.getGraphSession();
				ProcessDefinition processDefinition = graphSession.findLatestProcessDefinition(ProcessNameMapping
						.getTaskNameMapping(processName));
				ProcessInstance processInstance = graphSession.getProcessInstance(processDefinition, key);
				return processInstance;
			}
		});
		return result;

	}

	/**
	 * �������ID�ҵ������е�job��������job
	 * 
	 * @param processInstance
	 * @return
	 */
	public void suspendJob(final ProcessInstance processInstance) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				long id = processInstance.getRootToken().getId();
				Session session = jbpmContext.getSession();
				Job temp = (Job) session.createQuery("from org.jbpm.job.Job job where job.token.id = :id")
						.setLong("id", id).uniqueResult();
				Job job = (Job) session.load(Job.class, temp.getId());
				job.setSuspended(true);
				session.saveOrUpdate(job);
				return null;
			}
		});
	}

	/**
	 * �������ID�ҵ������е�job��������job
	 * 
	 * @param processInstance
	 * @return
	 */
	public void resumeJob(final ProcessInstance processInstance) {
		execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				long id = processInstance.getRootToken().getId();
				Session session = jbpmContext.getSession();
				Job temp = (Job) session.createQuery("from org.jbpm.job.Job job where job.token.id = :id")
						.setLong("id", id).uniqueResult();
				Job job = (Job) session.load(Job.class, temp.getId());
				job.setSuspended(false);
				session.saveOrUpdate(job);
				return null;
			}
		});
	}

	/**
	 * �ж�һ�������Ƿ���Job
	 * @param processInstance
	 * @return
	 */
	public boolean isJobExistAndSuspand(final ProcessInstance processInstance) {
		boolean result = (Boolean) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				long id = processInstance.getRootToken().getId();
				Session session = jbpmContext.getSession();
				Job job = (Job) session.createQuery("from org.jbpm.job.Job job where job.token.id = :id")
						.setLong("id", id).uniqueResult();
				if (job != null && job.isSuspended()) {
					return true;
				} else {
					return false;
				}
			}
		});
		return result;
	}

	/**
	 * δ���
	 * 
	 * @param taskInstanceId
	 * @param tansitionName
	 * @return
	 */
	public String getProStateByTaskInstanceIdAndTansistionName(final String taskInstanceId, final String tansitionName) {
		String result = (String) execute(new JbpmCommand() {
			@Override
			public Object execute(JbpmContext jbpmContext) {
				String proState = null;
				TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.parseLong(taskInstanceId));
				List availableTransitions = taskInstance.getAvailableTransitions();
				if ((availableTransitions != null) && (availableTransitions.size() != 0)) {
					Iterator iter = availableTransitions.iterator();
					while (iter.hasNext()) {
						Transition transition = (Transition) iter.next();
						if (transition.getName().equals(tansitionName)) {
							proState = transition.getDescription();
							break;
						}
					}
				}
				return proState;
			}
		});
		return result;
	}

	private List<TaskInstance> doFilterWithDeptPer(List<TaskInstance> taskInstanceList, List<DeptPer> deptPers) {
		List<TaskInstance> resultList = new ArrayList<TaskInstance>();
		//ѭ��ÿһ������ʵ���жϵ�ǰ�˵Ĳ������Ȩ���Ƿ��ܿ���
		for (TaskInstance taskInstance : taskInstanceList) {
			String funModuleKey = (String) taskInstance.getVariable("funModuleKey");
			String dpId = (String) taskInstance.getVariable("dpId");
			if (dpId != null && dpId != "") {
				//��ݹ���ģ��ؼ����жϹ���ģ��ö�٣����Ϊ�ջ���ַ���Ĭ�Ϸ���GENERAL����
				//��ݴ�������Ȩ���ж��Ƿ��д������Ȩ��
				for (DeptPer deptPer : deptPers) {
					String deptPerId = deptPer.getDept().getId() + "";
					if (deptPerId.equals(dpId) && deptPer.getFunModuleKey().equals(funModuleKey)) {
						resultList.add(taskInstance);
						break;
					}
				}
			} else {
				resultList.add(taskInstance);//����û�й滮����Ȩ�������ڽ��
			}
		}
		return resultList;
	}

	private List<TaskInstance> doFilterWithDept(List<TaskInstance> taskInstanceList, List<Dept> deptPers) {
		List<TaskInstance> resultList = new ArrayList<TaskInstance>();
		//ѭ��ÿһ������ʵ���жϵ�ǰ�˵Ĳ������Ȩ���Ƿ��ܿ���
		for (TaskInstance taskInstance : taskInstanceList) {
			String dpId = (String) taskInstance.getVariable("dpId");
			if (dpId != null && dpId.equals("")) {
				//��ݴ�������Ȩ���ж��Ƿ��д������Ȩ��
				for (Dept deptPer : deptPers) {
					if (deptPer.getId().toString().equals(dpId)) {
						resultList.add(taskInstance);
						break;
					}
				}
			} else {
				resultList.add(taskInstance);//����û�й滮����Ȩ�������ڽ��
			}
		}
		return resultList;
	}

	@Override
	public Boolean isAuditedByMp(String workflowId) {
		TaskInstance temp = this.findTaskInstance(workflowId);
		Collection<TaskInstance> taskInstances = temp.getProcessInstance().getTaskMgmtInstance().getTaskInstances();
		for (TaskInstance taskInstance : taskInstances) {
			if ("上报局端".equals(taskInstance.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean ifHaveTaskByName(String workflowId, String taskInstanceName) {
		TaskInstance temp = this.findTaskInstance(workflowId);
		if (temp == null) {
			return false;
		}
		Collection<TaskInstance> taskInstances = temp.getProcessInstance().getTaskMgmtInstance().getTaskInstances();
		for (TaskInstance taskInstance : taskInstances) {
			if (taskInstanceName.equals(taskInstance.getName())) {
				return true;
			}
		}
		return false;
	}
}
