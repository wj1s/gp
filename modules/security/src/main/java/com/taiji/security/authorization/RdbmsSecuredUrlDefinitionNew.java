/**
 * 
 */
package com.taiji.security.authorization;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;

/**
 * @author 郭翔
 *@see通过数据库查询出，角色对应的URL
 */
public class RdbmsSecuredUrlDefinitionNew {
	private JdbcTemplate jt;

	private DataSource dataSource;

	public RdbmsSecuredUrlDefinitionNew(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List execute() {
		jt = new JdbcTemplate(dataSource);
		List rows = jt.queryForList(""
				+ "select tb_sec_permission.per_name,tb_sec_role.role_name from tb_sec_permission "
				+ "join tb_sec_per_ass on tb_sec_per_ass.per_id=tb_sec_permission.id "
				+ "join tb_sec_role on tb_sec_per_ass.role_id=tb_sec_role.id " + "order by tb_sec_permission.position");
		String lastPerName = "";
		List list = new ArrayList();
		RdbmsEntryHolder rsh = new RdbmsEntryHolder();
		List<ConfigAttribute> cad = new ArrayList<ConfigAttribute>();
		rsh.setCad(cad);
		for (int i = 0; i < rows.size(); i++) {
			Map row = (Map) rows.get(i);
			//当前行对应的链接
			String perName = (String) row.get("per_name");
			//当前行对应的角色
			String roleName = (String) row.get("role_name");
			//下一行对应的链接（用于判断是否添加到list中）
			String nextPerName = "";
			if (i + 1 == rows.size()) {
				nextPerName = "_end";
			} else {
				nextPerName = (String) ((Map) rows.get(i + 1)).get("per_name");
			}

			if (perName.equals(lastPerName)) {//判断如果当前行与上一行对应的链接相同，则往该链接对应的对象中加一个角色定义
				cad.add(new SecurityConfig("ROLE_" + roleName));
				if (!perName.equals(nextPerName)) {//如果为当前链接的最后一个配制，则加入到list中并初始化链接对象
					list.add(rsh);
					rsh = new RdbmsEntryHolder();
					cad = new ArrayList<ConfigAttribute>();
					rsh.setCad(cad);
				}
			} else {//判断如果当前行与上一行对应的链接不同，则往新建一个链接的配制字段
				rsh.setUrl(perName);
				cad.add(new SecurityConfig("ROLE_" + roleName));
				lastPerName = perName;
				if (nextPerName.equals("_end")) {//如果为最后一行纪录，则加入到list中
					list.add(rsh);
				}
			}
		}
		return list;
	}
}
