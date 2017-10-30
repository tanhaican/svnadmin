package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;
import org.svnadmin.entity.Config;

/**
 * 用户DAO
 * 
 * @author <a href="mailto:tanhaican@gmail.com">Danny</a>
 * 
 */
@Repository(CommonDao.BEAN_NAME)
public class CommonDao extends Dao {

	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "commonDao";

	public Config getConfig(String config) {
		String sql = "SELECT c.`code`, c.`value` FROM config c WHERE c.`config` = ? AND c.`is_vaild` = TRUE  ";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, config);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readConfig(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return null;
	}
	
	
	Config readConfig(ResultSet rs) throws SQLException {
		Config result = new Config();
		result.setCode(rs.getString("code"));
		result.setValue(rs.getString("value"));
		return result;
	}

}