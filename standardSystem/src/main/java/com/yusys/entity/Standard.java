package com.yusys.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class Standard implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String std_um;
	private String zhname;
	private String version;
	private String keys;
	private Date release_date;
	private Date impl_date;
	private String ackage_path;
	private MultipartFile file;
	
	public String getAckage_path() {
		return ackage_path;
	}

	public void setAckage_path(String ackage_path) {
		this.ackage_path = ackage_path;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStd_um() {
		return std_um;
	}
	public void setStd_um(String std_um) {
		this.std_um = std_um;
	}
	public String getZhname() {
		return zhname;
	}
	public void setZhname(String zhname) {
		this.zhname = zhname;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public Date getRelease_date() {
		return release_date;
	}
	public void setRelease_date(Date release_date) {
		this.release_date = release_date;
	}
	public Date getImpl_date() {
		return impl_date;
	}
	public void setImpl_date(Date impl_date) {
		this.impl_date = impl_date;
	}
	
	
	
	
}
