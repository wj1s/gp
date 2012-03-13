/**
 * 
 */
package com.taiji.security.web.access;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.taiji.security.authorization.RdbmsEntryHolder;
import com.taiji.security.authorization.RdbmsSecuredUrlDefinitionNew;

/**
 * @author 郭翔
 *此类为获取所有的安全资源，，角色和资源的对应关系
 */

public class SecurityMetadataEditSource extends JdbcDaoSupport implements FilterInvocationSecurityMetadataSource {
	protected static final Log logger = LogFactory.getLog(SecurityMetadataEditSource.class);
	private PathMatcher pathMatcher = new AntPathMatcher();
	private Ehcache webresdbCache;
	private RdbmsSecuredUrlDefinitionNew rdbmsInvocationDefinition;

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if ((object == null) || !this.supports(object.getClass())) {
			throw new IllegalArgumentException("抱歉，目标对象不是FilterInvocation类型");
		}

		// 抽取出待请求的URL
		String url = ((FilterInvocation) object).getRequestUrl();

		List list = this.getRdbmsEntryHolderList();
		if (list == null || list.size() == 0)
			return null;

		int firstQuestionMarkIndex = url.indexOf("?");
		if (firstQuestionMarkIndex != -1) {
			url = url.substring(0, firstQuestionMarkIndex);
		}

		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			RdbmsEntryHolder entryHolder = (RdbmsEntryHolder) iter.next();
			boolean matched = pathMatcher.match(entryHolder.getUrl(), url);
			if (logger.isDebugEnabled()) {
				logger.debug("匹配到如下URL： '" + url + "；模式为 " + entryHolder.getUrl() + "；是否被匹配：" + matched);
			}

			if (matched) {
				return entryHolder.getCad();
			}
		}

		return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> set = new HashSet();
		Iterator iter = this.getRdbmsEntryHolderList().iterator();
		while (iter.hasNext()) {
			RdbmsEntryHolder entryHolder = (RdbmsEntryHolder) iter.next();
			set.addAll(entryHolder.getCad());
		}
		return set;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		if (FilterInvocation.class.isAssignableFrom(clazz)) {
			return true;
		} else {
			return false;
		}
	}

	private List getRdbmsEntryHolderList() {
		List list = null;
		Element element = null;
		try {
			element = this.webresdbCache.get("webres");
		} catch (Exception e) {
			logger.info("Cache:webres in dest store has not active!creat one!");
		}

		if (element != null) {
			list = (List) element.getValue();
		} else {
			list = this.rdbmsInvocationDefinition.execute();
			Element elem = new Element("webres", list);
			this.webresdbCache.put(elem);
		}
		return list;
	}

	protected void initDao() throws Exception {
		this.rdbmsInvocationDefinition = new RdbmsSecuredUrlDefinitionNew(this.getDataSource());
		if (this.webresdbCache == null)
			throw new IllegalArgumentException("必须为RdbmsFilterInvocationDefinitionSource配置一EhCache缓存");
	}

	public void setWebresdbCache(Ehcache webresdbCache) {
		this.webresdbCache = webresdbCache;
	}

	public void setRdbmsInvocationDefinition(RdbmsSecuredUrlDefinitionNew rdbmsInvocationDefinition) {
		this.rdbmsInvocationDefinition = rdbmsInvocationDefinition;
	}
}
