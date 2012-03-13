package gov.abrs.etms.action.util;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.common.util.ReflectionUtils;
import gov.abrs.etms.dao.util.ExecuteDAO;
import gov.abrs.etms.model.baseinfo.Dept;
import gov.abrs.etms.model.sys.FunModule;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.service.workflow.WorkFlowService;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

//定义返回json串的格式，grid为json数据，right为true，wrong为false
@Results({ @Result(name = "grid", type = "grid", params = { "type", "grid", "exposedValue", "carrier" }),
		@Result(name = "right", type = "grid", params = { "type", "right" }),
		@Result(name = "wrong", type = "grid", params = { "type", "wrong" }),
		@Result(name = "normal", type = "grid", params = { "type", "normal", "exposedValue", "list" }),
		@Result(name = "auto", type = "grid", params = { "type", "auto", "exposedValue", "list" }),
		@Result(name = "model", type = "grid", params = { "type", "model", "exposedValue", "model" }),
		@Result(name = "easy", type = "grid", params = { "type", "easy", "exposedValue", "json" }),
		@Result(name = "easyfile", type = "grid", params = { "type", "easyfile", "exposedValue", "json" }),
		@Result(name = "file", type = "grid", params = { "type", "file", "exposedValue", "list" }) })
public abstract class GridAction<T extends IdEntity> extends BaseAction implements Preparable, ModelDriven<T> {

	private static final long serialVersionUID = -2739180893051185772L;
	private static final Logger LOG = Logger.getLogger(GridAction.class);
	public static final String GRID = "grid";
	public static final String RIGHT = "right";
	public static final String WRONG = "wrong";
	public static final String NORMAL = "normal";
	public static final String EASYFILE = "easyfile";
	public static final String EASY = "easy";
	public static final String AUTO = "auto";
	public static final String MODEL = "model";
	public static final String FILE = "file";
	protected Class<T> entityClass;

	protected T model;

	protected Long id;

	protected Carrier<T> carrier;

	protected String json;

	protected String q;//autoComplete传入参数

	protected String funModule;//自动完成中用于根据不同权限判断返回数据

	protected List list;

	//检查唯一约束是使用
	private String checkKey;
	private String checkValue;

	public GridAction() {
		//在构造方法中通过类反射得到泛型中的类和类的实例
		entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
	}

	//prepareSave方法在action中的save方法执行前执行，组装用于保存的对象
	@SuppressWarnings("unchecked")
	public void prepareSave() throws InstantiationException, IllegalAccessException {
		if (id != null) {
			model = (T) this.executeDAO.get(entityClass, id);
			beforeUpdate(model);
		} else {
			model = entityClass.newInstance();
			beforeSave(model);
		}
	}

	//preSave方法由各实现类根据不同情况编写
	protected void preSave(T model) {}

	//beforeSave方法由各实现类根据不同情况编写
	protected void beforeSave(T model) {}

	//beforeUpdate方法由各实现类根据不同情况编写
	protected void beforeUpdate(T model) {}

	@Override
	public void prepare() {}

	public String load() throws Exception {
		try {
			executeDAO.find(entityClass, carrier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GRID;
	}

	public String save() {
		try {
			preSave(model);
			executeDAO.save(entityClass, model);
			executeDAO.flush();
			return RIGHT;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
			return WRONG;
		}
	}

	public String delete() {
		try {
			executeDAO.delete(entityClass, carrier);
			executeDAO.flush();
			return RIGHT;
		} catch (Exception e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
			return WRONG;
		}
	}

	@SuppressWarnings("unchecked")
	public String autocompleteAjax() throws UnsupportedEncodingException {
		q = new String(q.getBytes("iso-8859-1"), "UTF-8");
		List<Object> tempList = executeDAO.getByNameLike(entityClass, q);
		//判断此类对象是否与部门有关系
		boolean classWithDept = false;
		for (Object object : tempList) {
			if (((AutoCompleteable) object).getDeptsPopedom().size() > 0) {
				classWithDept = true;
				break;
			}
		}
		List finalList = Lists.newArrayList();
		//当传入的自动完成url带权限信息，并且此类自动完成的对象与dept有关系时，对结果进行按权限筛选
		if (!"".equals(funModule) && classWithDept) {
			List<Dept> depts = getCurUser().getDeptsFun(FunModule.getFunModule(funModule));
			for (Object obj : tempList) {
				List<Dept> deptTarget = ((AutoCompleteable) obj).getDeptsPopedom();
				List<Dept> listTarget = (List<Dept>) CollectionUtils.intersection(depts, deptTarget);
				if (deptTarget.size() == 0 || listTarget != null && listTarget.size() > 0) {
					finalList.add(obj);
				}
			}
			list = finalList;
		} else {
			list = tempList;
		}
		return AUTO;
	}

	@SuppressWarnings("unchecked")
	protected String assemblyJsonArray(List list, String propertyName) throws Exception {
		JSONArray array = new JSONArray();
		for (Object idEntity : list) {
			JSONObject json = new JSONObject();
			json.put("value", ((IdEntity) idEntity).getId());
			Class clazz = idEntity.getClass();
			Method method = clazz.getMethod(
					"get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), null);
			Object obj = method.invoke(idEntity, null);
			if (obj != null) {
				json.put("name", obj.toString());
			}
			array.add(json);
		}
		return array.toString();
	}

	public String encodeContent(String s) throws UnsupportedEncodingException {
		if (s != null) {
			s = new String(s.getBytes("iso8859_1"), "UTF-8");
		}
		return s;
	}

	//新增的时候调用，用来判断某字段是否重复
	public String checkUnique() {
		String hql = "from " + entityClass.getName() + " where " + checkKey + "=:checkValue";
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("checkValue", checkValue);
		if (id != null) {
			hql += " and id!=:id";
			values.put("id", id);
		}
		List<T> list = this.executeDAO.find(hql, values);
		if (list.size() != 0) {
			return WRONG;
		} else {
			return RIGHT;
		}
	}

	//空的execute方法，接口需要
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	protected ExecuteDAO executeDAO;
	protected WorkFlowService workFlowService;

	@Autowired
	public void setWorkFlowService(WorkFlowService workFlowService) {
		this.workFlowService = workFlowService;
	}

	//注入一个执行DAO类，用于进行默认的load,save,delete方法
	@Resource(name = "executeDAO")
	public void setExecuteDAO(ExecuteDAO executeDAO) {
		this.executeDAO = executeDAO;
	}

	//ModelDrive
	@Override
	public T getModel() {
		return model;
	}

	//get&&set方法

	//相当于Carrier的set方法，只不过是用其他属性组装的方式
	//检查，如果carrier对象还没有创建，则先创建一个
	private void initCarrier() {
		if (carrier == null) {
			carrier = new Carrier<T>();
			carrier.setPageSize(10);
		}
	}

	public void setPage(Integer page) {
		initCarrier();
		this.carrier.setCurrentPage(page);
	}

	public void setSidx(String sidx) {
		initCarrier();
		this.carrier.setSidx(sidx);
	}

	public void setSord(String sord) {
		initCarrier();
		this.carrier.setSord(sord);
	}

	public void setFilters(String filters) {
		if (filters != null && filters.indexOf("taijiFilter=") != -1) {
			initCarrier();
			this.carrier.setFilters(filters.substring(12));
		}
	}

	public void setRows(int rows) {
		initCarrier();
		this.carrier.setPageSize(rows);
	}

	public void setDelIds(String delIds) {
		initCarrier();
		this.carrier.setDelIds(delIds);
	}

	//Carrier的get方法
	public Carrier<T> getCarrier() {
		return carrier;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getJson() {
		return json;
	}

	public List getList() {
		return list;
	}

	//用于测试中注入模拟数据
	public void setModel(T model) {
		this.model = model;
	}

	//仅用于单元测试
	public void setCarrier(Carrier<T> carrier) {
		this.carrier = carrier;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getCheckKey() {
		return checkKey;
	}

	public void setCheckKey(String checkKey) {
		this.checkKey = checkKey;
	}

	public String getCheckValue() {
		return checkValue;
	}

	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}

	public String getFunModule() {
		return funModule;
	}

	public void setFunModule(String funModule) {
		this.funModule = funModule;
	}
}
