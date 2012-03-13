/**
 * 
 */
package com.taiji.security.authorization;

import java.io.Serializable;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;

/**
 * @author 郭翔
 *@see 自定义URL和角色对应集合
 */
public class RdbmsEntryHolder implements Serializable {

	private static final long serialVersionUID = 2317309106087370323L;

	//保护的URL模式
	private String url;

	//要求的角色集合
	private Collection<ConfigAttribute> cad;

	public String getUrl() {
		return url;
	}

	public Collection<ConfigAttribute> getCad() {
		return cad;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setCad(Collection<ConfigAttribute> cad) {
		this.cad = cad;
	}

}
