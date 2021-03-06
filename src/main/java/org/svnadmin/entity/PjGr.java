package org.svnadmin.entity;

import java.io.Serializable;

/**
 * 组
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
public class PjGr implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6413099229527347938L;
	/**
	 * 项目
	 */
	private Integer pjId;
	/**
	 * 组
	 */
	private String gr;
	/**
	 * 描述
	 */
	private String des;

	/**
	 * @return 项目
	 */
	public Integer getPjId() {
		return pjId;
	}

	/**
	 * @param pj
	 *            项目
	 */
	public void setPjId(Integer pjId) {
		this.pjId = pjId;
	}

	/**
	 * @return 描述
	 */
	public String getDes() {
		return des;
	}

	/**
	 * @param des
	 *            描述
	 */
	public void setDes(String des) {
		this.des = des;
	}

	/**
	 * @return 组
	 */
	public String getGr() {
		return gr;
	}

	/**
	 * @param gr
	 *            组
	 */
	public void setGr(String gr) {
		this.gr = gr;
	}

}
