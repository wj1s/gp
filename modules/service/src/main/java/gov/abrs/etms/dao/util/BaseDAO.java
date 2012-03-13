/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SimpleHibernateDao.java 1205 2010-09-09 15:12:17Z calvinxiu $
 */
package gov.abrs.etms.dao.util;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.ReflectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("prototype")
@SuppressWarnings("unchecked")
public class BaseDAO<T> {

	private ExecuteDAO executeDAO;

	protected Logger logger = Logger.getLogger(getClass());

	protected Class<T> entityClass;

	public BaseDAO() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 保存新增或修改的对象.
	 */
	public void save(final T entity) {
		executeDAO.save(entityClass, entity);
	}

	/**
	 * 删除对象.
	 * 
	 * @param entity
	 *            对象必须是session中的对象或含id属性的transient对象.
	 */
	public void delete(final T entity) {
		executeDAO.delete(entityClass, entity);
	}

	/**
	 * 按id删除对象.
	 */
	public void delete(final Long id) {
		executeDAO.delete(entityClass, id);
	}

	/**
	 * 按id获取对象.
	 */

	public T get(final Long id) {
		return (T) executeDAO.get(entityClass, id);
	}

	/**
	 * 按id列表获取对象列表.
	 */
	public List<T> get(final Collection<Long> ids) {
		return executeDAO.get(entityClass, ids);
	}

	/**
	 * 获取全部对象.
	 */
	public List<T> getAll() {
		return executeDAO.getAll(entityClass);
	}

	/**
	 * 获取全部对象, 支持按属性行序.
	 */
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		return executeDAO.getAll(entityClass, orderByProperty, isAsc);
	}

	/**
	 * 按属性查找对象列表, 匹配方式为相等.
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		return executeDAO.findBy(entityClass, propertyName, value);
	}

	/**
	 * 按属性查找唯一对象, 匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		return (T) executeDAO.findUniqueBy(entityClass, propertyName, value);
	}

	/**
	 * 按SQL查询唯一对象.
	 * 
	 * @param sql
	 *            数量可变的Sql.
	 */
	public Object findObjectUnique(String sql) {
		return this.executeDAO.findObjectUnique(sql);
	}

	/**
	 * 按Criteria查询对象列表.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public List<T> find(final Criterion... criterions) {
		return executeDAO.find(entityClass, criterions);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> List<X> find(final String hql, final Object... values) {
		return executeDAO.find(hql, values);
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public <X> List<X> find(final String hql, final Map<String, ?> values) {
		return executeDAO.createQuery(hql, values).list();
	}

	/**
	 * 按HQL分页查询.
	 * 
	 * @param carrier 分页参数. 注意不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 * 
	 * @return 分页查询结果, 附带结果列表及所有查询输入参数.
	 */
	public void find(final Carrier carrier, String hql, final Map<String, ?> values) {
		executeDAO.find(carrier, hql, values);
	}

	/**
	 * 按HQL查询唯一对象.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public <X> X findUnique(final String hql, final Object... values) {
		return (X) executeDAO.findUnique(hql, values);
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, ?> values) {
		return executeDAO.batchExecute(hql, values);
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		return executeDAO.getIdName(entityClass);
	}

	public void flush() {
		this.executeDAO.flush();
	}

	public List<T> getByNameLike(String nameLike) {
		return executeDAO.getByNameLike(entityClass, nameLike);
	}

	//根据名称，精确查询
	public <X> X getByName(String name) {
		return (X) executeDAO.getByName(entityClass, name);
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * 
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object oldValue) {
		return executeDAO.isPropertyUnique(entityClass, propertyName, newValue, oldValue);
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	@Autowired
	public void setExecuteDAO(ExecuteDAO executeDAO) {
		this.executeDAO = executeDAO;
	}
}