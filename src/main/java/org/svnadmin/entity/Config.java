package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 配置信息
 * 
 * 
 */
public class Config implements Serializable {

	private static final long serialVersionUID = 8251147689572549482L;
	private Integer id;
	private String config;
	private String code;
	private String value;
	private Boolean isValid;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
}
