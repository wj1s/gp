/**
 * 
 */
package com.taiji.security.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author 郭翔
 * @see自定义过滤连，主要为了根据CAS和DB初始化不同的过滤链
 *
 */
public class FilterChainProxyEtms extends GenericFilterBean implements ApplicationContextAware {
	//~ Static fields/initializers =====================================================================================
	private ApplicationContext applicationContext;
	private static final Log logger = LogFactory.getLog(FilterChainProxy.class);
	public static final String TOKEN_NONE = "#NONE#";
	public static final String DB_FILTER = "httpSessionContextIntegrationFilter,logoutFilter,authenticationProcessingFilter,anonymousProcessingFilter,exceptionTranslationFilterForDB,filterInvocationInterceptor";
	public static final String CAS_FILTER = "httpSessionContextIntegrationFilter,singleLogoutFilter,requestSingleLogoutFilter,casAuthenticationFilter,anonymousProcessingFilter,exceptionTranslationFilterForCAS,filterInvocationInterceptor";
	private String filterChain = null;
	//~ Instance fields ================================================================================================

	/** Map of the original pattern Strings to filter chains */
	private Map<String, List<Filter>> uncompiledFilterChainMap;
	/** Compiled pattern version of the filter chain map */
	private Map<Object, List<Filter>> filterChainMap;
	private UrlMatcher matcher = new AntUrlPathMatcher();
	private boolean stripQueryStringFromUrls = true;
	private FilterChainValidator filterChainValidator = new NullFilterChainValidator();
	private String authType;

	//~ Methods ========================================================================================================

	@Override
	public void afterPropertiesSet() {
		if (uncompiledFilterChainMap == null) {
			this.setFilterChainMap(filterChainMap);
		}

		//		Assert.notNull(uncompiledFilterChainMap, "filterChainMap must be set");
		filterChainValidator.validate(this);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		FilterInvocation fi = new FilterInvocation(request, response, chain);
		List<Filter> filters = getFilters(fi.getRequestUrl());

		if (filters == null || filters.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(fi.getRequestUrl() + filters == null ? " has no matching filters"
						: " has an empty filter list");
			}

			chain.doFilter(request, response);

			return;
		}

		VirtualFilterChain virtualFilterChain = new VirtualFilterChain(fi, filters);
		virtualFilterChain.doFilter(fi.getRequest(), fi.getResponse());
	}

	/**
	 * Returns the first filter chain matching the supplied URL.
	 *
	 * @param url the request URL
	 * @return an ordered array of Filters defining the filter chain
	 */
	public List<Filter> getFilters(String url) {
		if (stripQueryStringFromUrls) {
			// String query string - see SEC-953
			int firstQuestionMarkIndex = url.indexOf("?");

			if (firstQuestionMarkIndex != -1) {
				url = url.substring(0, firstQuestionMarkIndex);
			}
		}

		for (Map.Entry<Object, List<Filter>> entry : filterChainMap.entrySet()) {
			Object path = entry.getKey();

			if (matcher.requiresLowerCaseUrl()) {
				url = url.toLowerCase();

				if (logger.isDebugEnabled()) {
					logger.debug("Converted URL to lowercase, from: '" + url + "'; to: '" + url + "'");
				}
			}

			boolean matched = matcher.pathMatchesUrl(path, url);

			if (logger.isDebugEnabled()) {
				logger.debug("Candidate is: '" + url + "'; pattern is " + path + "; matched=" + matched);
			}

			if (matched) {
				return entry.getValue();
			}
		}

		return null;
	}

	/**
	 * Obtains all of the <b>unique</b><code>Filter</code> instances registered in the map of
	 * filter chains.
	 * <p>This is useful in ensuring a <code>Filter</code> is not initialized or destroyed twice.</p>
	 *
	 * @return all of the <code>Filter</code> instances in the application context which have an entry
	 *         in the map (only one entry is included in the array for
	 *         each <code>Filter</code> that actually exists in application context, even if a given
	 *         <code>Filter</code> is defined multiples times in the filter chain map)
	 */
	protected Collection<Filter> obtainAllDefinedFilters() {
		Set<Filter> allFilters = new LinkedHashSet<Filter>();

		for (List<Filter> filters : filterChainMap.values()) {
			allFilters.addAll(filters);
		}

		return allFilters;
	}

	/**
	 * Sets the mapping of URL patterns to filter chains.
	 *
	 * The map keys should be the paths and the values should be arrays of <tt>Filter</tt> objects.
	 * It's VERY important that the type of map used preserves ordering - the order in which the iterator
	 * returns the entries must be the same as the order they were added to the map, otherwise you have no way
	 * of guaranteeing that the most specific patterns are returned before the more general ones. So make sure
	 * the Map used is an instance of <tt>LinkedHashMap</tt> or an equivalent, rather than a plain <tt>HashMap</tt>, for
	 * example.
	 *
	 * @param filterChainMap the map of path Strings to <tt>List&lt;Filter&gt;</tt>s.
	 * @see强行指定过滤连的FILTER，从此配置文件中配置其它过滤器将不再可用
	 * 本类通过注入在SPRING中的类名，实例化出所对应的过滤器
	 */
	@SuppressWarnings("unchecked")
	public void setFilterChainMap(Map filterChainMap) {
		if (filterChainMap == null || filterChainMap.isEmpty() || filterChainMap.size() < 1) {
			filterChainMap = new HashMap();
			String authType = this.getAuthType().toLowerCase();
			if (authType.equals("cas")) {
				filterChain = CAS_FILTER;
			} else {
				filterChain = DB_FILTER;
			}
			String[] filters = filterChain.split(",");
			List<Filter> filterchains = new ArrayList<Filter>();
			for (String filter : filters) {
				filterchains.add((Filter) applicationContext.getBean(filter));
			}
			filterChainMap.put("/**", filterchains);
		}
		checkContents(filterChainMap);
		uncompiledFilterChainMap = new LinkedHashMap<String, List<Filter>>(filterChainMap);
		checkPathOrder();
		createCompiledMap();

	}

	private void checkContents(Map filterChainMap) {
		for (Object key : filterChainMap.keySet()) {
			Assert.isInstanceOf(String.class, key, "Path key must be a String but found " + key);
			Object filters = filterChainMap.get(key);
			Assert.isInstanceOf(List.class, filters, "Value must be a filter list");
			// Check the contents
			Iterator filterIterator = ((List) filters).iterator();

			while (filterIterator.hasNext()) {
				Object filter = filterIterator.next();
				Assert.isInstanceOf(Filter.class, filter, "Objects in filter chain must be of type Filter. ");
			}
		}
	}

	private void checkPathOrder() {
		// Check that the universal pattern is listed at the end, if at all
		String[] paths = (String[]) uncompiledFilterChainMap.keySet().toArray(new String[0]);
		String universalMatch = matcher.getUniversalMatchPattern();

		for (int i = 0; i < paths.length - 1; i++) {
			if (paths[i].equals(universalMatch)) {
				throw new IllegalArgumentException("A universal match pattern " + universalMatch + " is defined "
						+ " before other patterns in the filter chain, causing them to be ignored. Please check the "
						+ "ordering in your <security:http> namespace or FilterChainProxy bean configuration");
			}
		}
	}

	private void createCompiledMap() {
		filterChainMap = new LinkedHashMap<Object, List<Filter>>(uncompiledFilterChainMap.size());

		for (String path : uncompiledFilterChainMap.keySet()) {
			filterChainMap.put(matcher.compile(path), uncompiledFilterChainMap.get(path));
		}
	}

	/**
	 * Returns a copy of the underlying filter chain map. Modifications to the map contents
	 * will not affect the FilterChainProxy state - to change the map call <tt>setFilterChainMap</tt>.
	 *
	 * @return the map of path pattern Strings to filter chain lists (with ordering guaranteed).
	 */
	public Map<String, List<Filter>> getFilterChainMap() {
		return new LinkedHashMap<String, List<Filter>>(uncompiledFilterChainMap);
	}

	public void setMatcher(UrlMatcher matcher) {
		this.matcher = matcher;
	}

	public UrlMatcher getMatcher() {
		return matcher;
	}

	/**
	 * If set to 'true', the query string will be stripped from the request URL before
	 * attempting to find a matching filter chain. This is the default value.
	 */
	public void setStripQueryStringFromUrls(boolean stripQueryStringFromUrls) {
		this.stripQueryStringFromUrls = stripQueryStringFromUrls;
	}

	/**
	 * Used (internally) to specify a validation strategy for the filters in each configured chain.
	 *
	 * @param filterChainValidator
	 */
	public void setFilterChainValidator(FilterChainValidator filterChainValidator) {
		this.filterChainValidator = filterChainValidator;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FilterChainProxy[");
		sb.append(" UrlMatcher = ").append(matcher);
		sb.append("; Filter Chains: ");
		sb.append(uncompiledFilterChainMap);
		sb.append("]");

		return sb.toString();
	}

	//~ Inner Classes ==================================================================================================

	/**
	 * A <code>FilterChain</code> that records whether or not {@link
	 * FilterChain#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse)} is called.
	 * <p>
	 * This <code>FilterChain</code> is used by <code>FilterChainProxy</code> to determine if the next
	 * <code>Filter</code> should be called or not.</p>
	 */
	private static class VirtualFilterChain implements FilterChain {
		private FilterInvocation fi;
		private List<Filter> additionalFilters;
		private int currentPosition = 0;

		private VirtualFilterChain(FilterInvocation filterInvocation, List<Filter> additionalFilters) {
			this.fi = filterInvocation;
			this.additionalFilters = additionalFilters;
		}

		public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
			if (currentPosition == additionalFilters.size()) {
				if (logger.isDebugEnabled()) {
					logger.debug(fi.getRequestUrl()
							+ " reached end of additional filter chain; proceeding with original chain");
				}

				fi.getChain().doFilter(request, response);
			} else {
				currentPosition++;

				Filter nextFilter = additionalFilters.get(currentPosition - 1);

				if (logger.isDebugEnabled()) {
					logger.debug(fi.getRequestUrl() + " at position " + currentPosition + " of "
							+ additionalFilters.size() + " in additional filter chain; firing Filter: '" + nextFilter
							+ "'");
				}

				nextFilter.doFilter(request, response, this);
			}
		}
	}

	public interface FilterChainValidator {
		void validate(FilterChainProxyEtms filterChainProxy);
	}

	private class NullFilterChainValidator implements FilterChainValidator {
		public void validate(FilterChainProxyEtms filterChainProxy) {}
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
