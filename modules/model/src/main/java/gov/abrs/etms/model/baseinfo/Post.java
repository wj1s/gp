package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.model.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

//职位信息
@Entity
@Table(name = "TB_ETMS_BASE_POST")
public class Post extends IdEntity {

	private String post;
	private String name;
	private int sort;

	public Post() {}

	public Post(Long id) {
		this.id = id;
	}

	@Column(name = "POST", length = 4, nullable = false)
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "POST_NAME", length = 20, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "SORT", nullable = false)
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
