package gov.abrs.etms.model.baseinfo;

import gov.abrs.etms.common.util.DateUtil;
import gov.abrs.etms.model.para.DistrictType;
import gov.abrs.etms.model.para.StationType;
import gov.abrs.etms.model.para.TransmitUnitType;
import gov.abrs.etms.model.util.IdEntity;
import gov.abrs.etms.model.util.Jsonable;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONObject;

//台站
@Entity
@Table(name = "TB_ETMS_BASE_STATION")
public class Station extends IdEntity implements Jsonable {

	private String name;//台站名称
	private String code;//台站编号
	private String abbreviation;//台站缩写
	private String regionCode;//行政区编号
	private String longitude;//经度
	private String latitude;//纬度
	private String address;//台站地址
	private int altitude;//海拔
	private int acreage;//占地面积
	private Date buildDate;//建立日期
	private Date startDate;//开播日期
	private String rmks;//备注
	private StationType stationType;//台站类型
	private String brev;//台站简况
	private String geoInfor;//地理情况
	private DistrictType districtType;//台站归属
	private String loc;//台站ITU编码
	private TransmitUnitType transmitUnitType;//发射单位类型
	private List<Dept> depts;//下属部门

	//grid专用json
	@Transient
	@Override
	public String getJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.getId());
		jsonObject.put("name", this.getName());
		jsonObject.put("regionCode", this.getRegionCode());
		jsonObject.put("longitude", this.getLongitude());
		jsonObject.put("code", this.getCode());
		jsonObject.put("abbreviation", this.getAbbreviation());
		jsonObject.put("latitude", this.getLatitude());
		jsonObject.put("address", this.getAddress());
		jsonObject.put("altitude", this.getAltitude());
		jsonObject.put("acreage", this.getAcreage());
		jsonObject.put("buildDate", DateUtil.getDateStr(this.getBuildDate()));
		jsonObject.put("startDate", DateUtil.getDateStr(this.getStartDate()));
		jsonObject.put("rmks", this.getRmks());
		jsonObject.put("stationType", this.getStationType());
		jsonObject.put("brev", this.getBrev());
		jsonObject.put("geoInfor", this.getGeoInfor());
		jsonObject.put("districtType", this.getDistrictType());
		jsonObject.put("loc", this.getLoc());
		jsonObject.put("transmitUnitType", this.getTransmitUnitType());
		return jsonObject.toString();
	}

	public Station() {}

	public Station(long id) {
		this.id = id;
	}

	@Column(name = "STATION_NAME", length = 100, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "STATION_CODE", length = 2, nullable = false, unique = true)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "S_CODE", length = 20, nullable = false)
	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@Column(name = "REGION_CODE", length = 6, nullable = false)
	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	@Column(name = "LONGITUDE", length = 20, nullable = true)
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Column(name = "LATITUDE", length = 20, nullable = true)
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Column(name = "STATION_ADDR", length = 100, nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ALTITUDE", precision = 10, nullable = false)
	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	@Column(name = "ACREAGE", precision = 10, nullable = false)
	public int getAcreage() {
		return acreage;
	}

	public void setAcreage(int acreage) {
		this.acreage = acreage;
	}

	@Column(name = "BUILD_DATE", nullable = false)
	public Date getBuildDate() {
		return buildDate;
	}

	public void setBuildDate(Date buildDate) {
		this.buildDate = buildDate;
	}

	@Column(name = "START_DATE", nullable = false)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name = "RMKS", length = 200, nullable = true)
	public String getRmks() {
		return rmks;
	}

	public void setRmks(String rmks) {
		this.rmks = rmks;
	}

	@ManyToOne()
	@JoinColumn(name = "STATION_TYPE", nullable = false)
	public StationType getStationType() {
		return stationType;
	}

	public void setStationType(StationType stationType) {
		this.stationType = stationType;
	}

	@Column(name = "STATION_BREV", length = 1024, nullable = true)
	public String getBrev() {
		return brev;
	}

	public void setBrev(String brev) {
		this.brev = brev;
	}

	@Column(name = "GEO_INFOR", length = 1024, nullable = true)
	public String getGeoInfor() {
		return geoInfor;
	}

	public void setGeoInfor(String geoInfor) {
		this.geoInfor = geoInfor;
	}

	@ManyToOne()
	@JoinColumn(name = "DISTRICT_TYPE", nullable = false)
	public DistrictType getDistrictType() {
		return districtType;
	}

	public void setDistrictType(DistrictType districtType) {
		this.districtType = districtType;
	}

	@Column(name = "LOC", length = 3, nullable = true)
	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	@ManyToOne()
	@JoinColumn(name = "TRANSMIT_UNIT_TYPE", nullable = false)
	public TransmitUnitType getTransmitUnitType() {
		return transmitUnitType;
	}

	public void setTransmitUnitType(TransmitUnitType transmitUnitType) {
		this.transmitUnitType = transmitUnitType;
	}

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "station", fetch = FetchType.LAZY, targetEntity = Dept.class)
	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}
}
