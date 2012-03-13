package gov.abrs.etms.action.util;

import gov.abrs.etms.common.util.Carrier;
import gov.abrs.etms.model.util.AutoCompleteable;
import gov.abrs.etms.model.util.Jsonable;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;

//json串的返回值类型
public class GridResult extends StrutsResultSupport {

	private static final long serialVersionUID = -8066195294388521015L;
	private String exposedValue;
	private String type;

	//根据不同的返回类型返回不同的json串
	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		ValueStack stack = ServletActionContext.getContext().getValueStack();
		if ("grid".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonGrid(response, (Carrier<?>) object);
		} else if ("right".equals(type)) {
			jsonResult(response, true);
		} else if ("wrong".equals(type)) {
			jsonResult(response, false);
		} else if ("normal".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonNormal(response, (List) object);
		} else if ("auto".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonAuto(response, (List) object);
		} else if ("easy".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonEasy(response, (String) object);
		} else if ("easyfile".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonEasyFile(response, (String) object);
		} else if ("model".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonModel(response, object);
		} else if ("file".equals(type)) {
			Object object = stack.findValue(exposedValue);
			jsonFile(request, response, (List) object);
		}
	}

	private void jsonFile(HttpServletRequest request, HttpServletResponse response, List list) throws Exception {
		String key = (String) list.get(0);
		String fileName = "";
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
			//firefox
			fileName = new String(key.getBytes("UTF-8"), "ISO8859-1");
		} else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			//ie
			fileName = URLEncoder.encode(key, "UTF-8");
		}
		try {
			BufferedInputStream br = new BufferedInputStream(new FileInputStream((File) list.get(1)));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset();
			response.setContentType("application/x-msdownload;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//用于返回json串给前台的json串
	protected void jsonNormal(HttpServletResponse response, List list) throws IOException {
		JSONArray jsonArray = new JSONArray();
		if (list != null && list.size() != 0) {
			for (Object obj : list) {
				jsonArray.add(((Jsonable) obj).getJsonObject());
			}
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("result", true);
		jsonObj.put("data", jsonArray.toString());
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().write(jsonObj.toString());
	}

	//用于返回json串给前台的json串
	protected void jsonModel(HttpServletResponse response, Object model) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().write(((Jsonable) model).getJsonObject());
	}

	//用于返回json串给前台的json串
	protected void jsonAuto(HttpServletResponse response, List list) throws IOException {
		JSONArray jsonArray = new JSONArray();
		if (list != null && list.size() != 0) {
			for (Object obj : list) {
				jsonArray.add(((AutoCompleteable) obj).getAutoCompleteJson());
			}
		}
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().write(jsonArray.toString());
	}

	//用于返回json串给前台的grid，注意参数的名称是默认的
	protected void jsonGrid(HttpServletResponse response, Carrier<?> pager) throws IOException {
		try {
			JSONArray jsonArray = new JSONArray();
			if (pager.getResult() != null) {
				for (Object obj : pager.getResult()) {
					jsonArray.add(((Jsonable) obj).getJsonObject());
				}
			}
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("page", pager.getCurrentPage());
			jsonObj.put("total", pager.getTotalPage());
			jsonObj.put("records", pager.getTotalSize());
			jsonObj.put("data", jsonArray.toString());
			response.setContentType("text/json; charset=UTF-8");
			response.getWriter().write(jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//用于返回json串给前台
	protected void jsonEasy(HttpServletResponse response, String jsonStr) throws IOException {
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().write(jsonStr);
	}

	//用于返回json串给前台
	protected void jsonEasyFile(HttpServletResponse response, String jsonStr) throws IOException {
		response.setContentType("text/html;charaset=utf-8");
		response.getWriter().write("<textarea>" + jsonStr + "</textarea>");
	}

	//返回是否成功的json
	protected void jsonResult(HttpServletResponse response, boolean flag) throws IOException {
		JSONObject jsonObject = new JSONObject();
		if (flag) {
			jsonObject.put("result", true);
		} else {
			jsonObject.put("result", false);
		}
		response.setContentType("text/json;charset=UTF-8");
		response.getWriter().write(jsonObject.toString());
	}

	public String getExposedValue() {
		return exposedValue;
	}

	public void setExposedValue(String exposedValue) {
		this.exposedValue = exposedValue;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
