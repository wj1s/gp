package gov.abrs.etms.model.abnormal;

import gov.abrs.etms.model.util.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TechAccidentMedia entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TB_ETMS_TECH_ACCIDENT_MEDIA")
public class TechAccidentMedia extends IdEntity implements java.io.Serializable {

	// Fields    

	/**
	 * 
	 */
	private static final long serialVersionUID = -874498686076754652L;
	private TechAccident techAccident;
	private String mediaType;
	private String fileType;
	private String fileDir;
	private String fileName;
	private String saveName;// 文件名

	// Constructors

	/** default constructor */
	public TechAccidentMedia() {}

	/** minimal constructor */
	public TechAccidentMedia(long id, String mediaType, String fileType, String fileDir, String fileName) {
		this.id = id;
		this.mediaType = mediaType;
		this.fileType = fileType;
		this.fileDir = fileDir;
		this.fileName = fileName;
	}

	/** full constructor */
	public TechAccidentMedia(long id, TechAccident techAccident, String mediaType, String fileType, String fileDir,
			String fileName) {
		this.id = id;
		this.techAccident = techAccident;
		this.mediaType = mediaType;
		this.fileType = fileType;
		this.fileDir = fileDir;
		this.fileName = fileName;
	}

	// Property accessors
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ACCD_ID")
	public TechAccident getTechAccident() {
		return this.techAccident;
	}

	public void setTechAccident(TechAccident techAccident) {
		this.techAccident = techAccident;
	}

	@Column(name = "MEDIA_TYPE", length = 1)
	public String getMediaType() {
		return this.mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	@Column(name = "FILE_TYPE", length = 6)
	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "FILE_DIR", length = 100)
	public String getFileDir() {
		return this.fileDir;
	}

	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}

	@Column(name = "FILE_NAME", nullable = false, length = 50)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "SAVE_NAME", nullable = false, length = 50)
	public String getSaveName() {
		return saveName;
	}

	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}

}