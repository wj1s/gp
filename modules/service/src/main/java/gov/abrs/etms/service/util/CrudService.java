package gov.abrs.etms.service.util;

import gov.abrs.etms.common.util.ReflectionUtils;
import gov.abrs.etms.dao.util.BaseDAO;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public class CrudService<T> {

	protected Class<T> entityClass;

	protected BaseDAO<T> dao;

	//子类初始化时获取泛型类型，用户DAO辨别当前操作对象和表
	public CrudService() {
		entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	//根据名称，模糊查询
	public List<T> getByNameLike(String nameLike) {
		return dao.getByNameLike(nameLike);
	}

	//根据名称，精确查询
	@SuppressWarnings("unchecked")
	public T getByName(String name) {
		return (T) dao.getByName(name);
	}

	//公共方法
	public void save(T t) {
		dao.save(t);
	}

	public void delete(Long id) {
		dao.delete(id);
	}

	public T get(Long id) {
		return dao.get(id);
	}

	public List<T> getAll() {
		return dao.getAll();
	}

	//findAll带排序
	public List<T> getAll(String orderByProperty, boolean isAsc) {
		return dao.getAll(orderByProperty, isAsc);
	}

	//因为要在spring注入后初始化baseDAO的entityClass属性，所以只能用set注入，如果用
	//属性注入，因为注入是在调用构造函数之后，所以无法使用构造函数实现set entityClass
	//因为BaseDAO的@Scope("prototype")配置，而DeptService类似这样的service类是单例
	//模式，所以保证了所有的service具体类都只在服务器启动时初始化一次，并且各自都是注入一个
	//新的只属于自己的baseDAO，其类型就是自己的泛型类型，不会引起线程问题
	@Resource(name = "baseDAO")
	public void setDao(BaseDAO<T> dao) {
		this.dao = dao;
		dao.setEntityClass(entityClass);
	}
}
