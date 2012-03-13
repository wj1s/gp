package gov.abrs.etms.service.workflow;

import gov.abrs.etms.model.baseinfo.Person;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Query;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * Data access object (DAO) for domain model class CodeRadioSet.
 * 
 * @see gov.abrstms.model.para.CodeRadioSet
 * @author MyEclipse Persistence Tools
 */
public class WorkFlowExtDAOImpl extends HibernateDaoSupport implements WorkFlowExtDAO {

	public void reviveProcessInstence(ProcessInstance processInstance, String backToTaskNode) {
		try {
			Connection conn = getSession().connection();
			String sql = "{call restoreEndedProcInstanceToTaskNode_proc(?,?)}";
			CallableStatement stmt = conn.prepareCall(sql);
			stmt.setLong(1, processInstance.getId());
			stmt.setString(2, backToTaskNode);
			stmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* type=2 */
	public List<TaskInstance> findEndedTaskInstances(Person person) {
		List<String> actorIds = person.getActorIds();
		Query query = getSession().createQuery(
				"from org.jbpm.taskmgmt.exe.TaskInstance as ti " + "where ti.actorId in ( :actorIds ) "
						+ "and ti.isSuspended != true " + "and ti.end is not null order by ti.create")
				.setParameterList("actorIds", actorIds.toArray(new String[actorIds.size()]));
		return query.list();
	}

	public List<TaskInstance> findNoStartedAndStartedTaskInstances(Person person) {
		List<String> actorIds = person.getActorIds();
		Query query = getSession().createQuery(
				"from org.jbpm.taskmgmt.exe.TaskInstance as ti " + "where ti.actorId in ( :actorIds ) "
						+ "and ti.isSuspended != true " + "and ti.end is null order by ti.create").setParameterList(
				"actorIds", actorIds.toArray(new String[actorIds.size()]));
		return query.list();
	}
}