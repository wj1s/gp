package gov.abrs.etms.common.util;

import java.util.List;

import com.google.common.collect.Lists;

public class Carrier<T> {
	private int currentPage; //当前是第几页
	private int pageSize = 10; //每页有几条数据
	private Long totalSize; //共有多少条数据
	private String sidx; //排序字段
	private String sord; //正序或倒序
	private String filters;
	private String delIds;

	private List<T> result = Lists.newArrayList(); //返回结果集
	private boolean autoCount = true; //是否自动查询结果集的总数，默认为true

	public Carrier() {

	}

	public Carrier(int currentPage, String sidx, String sord, String filters) {
		super();
		this.currentPage = currentPage;
		this.sidx = sidx;
		this.sord = sord;
		this.filters = filters;
	}

	public Carrier(int currentPage, Long totalSize) {
		this.currentPage = currentPage;
		this.totalSize = totalSize;
	}

	public Carrier(int currentPage, Long totalSize, int pageSize, String sidx, String sord) {
		this.currentPage = currentPage;
		this.totalSize = totalSize;
		this.pageSize = pageSize;
		this.sidx = sidx;
		this.sord = sord;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getDelIds() {
		return delIds;
	}

	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	public long getTotalPage() {
		if (totalSize < 0) {
			return -1;
		}
		long count = totalSize / pageSize;
		if (totalSize % pageSize != 0)
			count++;
		return count;
	}

	public long getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}

	public int getFirstResult() {
		return (this.currentPage - 1) * this.pageSize;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	/**
	 * 获得查询对象时是否先自动执行count查询获取总记录数, 默认为false.
	 */
	public boolean isAutoCount() {
		return autoCount;
	}

	/**
	 * 设置查询对象时是否自动先执行count查询获取总记录数.
	 */
	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	/**
	 * 返回Page对象自身的setAutoCount函数,可用于连续设置。
	 */
	public Carrier<T> autoCount(boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}

}
