package org.svnadmin.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.svnadmin.dao.CommonDao;
import org.svnadmin.entity.Config;

/**
 * 
 * @since 3.0
 * 
 */
@Service(CommonService.BEAN_NAME)
public class CommonService {

	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "commonService";

	/**
	 * 用户DAO
	 */
	@Resource(name = CommonDao.BEAN_NAME)
	protected CommonDao commonDao;

	public String getConfig(String config) {
		Config conf = commonDao.getConfig(config);
		return null == conf ? "" : conf.getValue();
	}
}
