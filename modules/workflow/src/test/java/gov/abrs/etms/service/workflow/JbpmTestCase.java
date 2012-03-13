package gov.abrs.etms.service.workflow;

import gov.abrs.etms.service.workflow.WorkFlowServiceImpl.JbpmCommand;

import java.util.List;

import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.taiji.common.test.DatabaseTestCase;

public abstract class JbpmTestCase extends DatabaseTestCase {
	@Autowired
	private WorkFlowService workFlowService;

	@Before
	public void loadDefaultProcess() {
		workFlowService.execute(new JbpmCommand() {
			public ProcessDefinition execute(JbpmContext jbpmContext) {
				ProcessDefinition pd = ProcessDefinition
						.parseXmlResource("gov/abrs/etms/workflow/process/accdReport/processdefinition.xml");
				jbpmContext.deployProcessDefinition(pd);
				return pd;
			}
		});
	}

	@After
	public void cleanAllProcess() {
		workFlowService.execute(new JbpmCommand() {
			public Object execute(JbpmContext jbpmContext) {
				List<ProcessDefinition> pdList = jbpmContext.getGraphSession().findAllProcessDefinitions();
				for (ProcessDefinition pd : pdList) {
					jbpmContext.getGraphSession().deleteProcessDefinition(pd);
				}
				return null;
			}
		});
	}
}
