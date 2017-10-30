package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.svnadmin.constant.Constants;
import org.svnadmin.entity.Pj;

import com.mysql.jdbc.Statement;

/**
 * 项目DAO
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
@Repository(PjDao.BEAN_NAME)
public class PjDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjDao";

	/**
	 * @param pj
	 *            项目
	 * @return 项目
	 */
	public Pj get(String pj) {
		String sql = "select id,pj,path,url,des,type from pj where pj = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pj);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPj(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return null;
	}
	
	public Pj getById(Integer pjId) {
		String sql = "select id,pj,path,url,des,type from pj where id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pjId);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPj(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return null;
	}

	/**
	 * @return 项目列表
	 */
	public List<Pj> getList() {
		String sql = "select id,pj,path,url,des,type from pj order by id";
		List<Pj> list = new ArrayList<Pj>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readPj(rs));
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	/**
	 * @param usr
	 *            用户
	 * @return 用户有权限的项目列表(用户是否是这个项目的管理员)
	 */
	public List<Pj> getList(String usr) {
		String sql = "SELECT p.id, p.pj, p.path, p.url, p.des, p.type , pm.pj AS manager"
				+ " FROM (SELECT DISTINCT a.id, a.pj, a.path, a.url, a.des, a.type"
				+ " FROM pj a WHERE EXISTS (SELECT b.usr FROM pj_gr_usr b"
				+ " WHERE a.id = b.`pj_id` AND b.usr = ?) OR EXISTS (SELECT c.usr"
				+ " FROM pj_usr_auth c WHERE a.`id` = c.`pj_id` AND c.usr = ?)) p "
				+ " LEFT JOIN (SELECT DISTINCT a.id, a.pj FROM pj a"
				+ " WHERE EXISTS (SELECT b.usr FROM pj_gr_usr b"
				+ " WHERE a.id = b.pj_id AND b.usr = ? AND b.gr LIKE ?)"
				+ ") pm ON p.id = pm.id";
		List<Pj> list = new ArrayList<Pj>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr);
			pstmt.setString(index++, usr);
			pstmt.setString(index++, usr);
			pstmt.setString(index++, "%" + Constants.GROUP_MANAGER);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Pj pj = readPj(rs);
				String manager = rs.getString("manager");// 是否是管理员组的用户
				pj.setManager(StringUtils.isNotBlank(manager));
				list.add(pj);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
	}

	/**
	 * @param rs
	 *            ResultSet
	 * @return 项目
	 * @throws SQLException
	 *             jdbc异常
	 */
	public Pj readPj(ResultSet rs) throws SQLException {
		Pj result = new Pj();
		result.setId(rs.getInt("id"));
		result.setPj(rs.getString("pj"));
		result.setPath(rs.getString("path"));
		result.setUrl(rs.getString("url"));
		result.setDes(rs.getString("des"));
		result.setType(rs.getString("type"));
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 */
	public void delete(String pj) {
		String sql = "delete from pj where pj = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}
	
	public void deleteById(int id) {
		String sql = "delete from pj where id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 增加项目
	 * 
	 * @param pj
	 *            项目
	 * @return 如果成功返回主键ID，失败则返回-1
	 */
	public int insert(Pj pj) {
		String sql = "insert into pj (pj,path,url,des,type) values (?,?,?,?,?)";

		Connection conn = null;
		PreparedStatement pstmt = null;
		int genKey = -1 ;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pstmt.setString(index++, pj.getPj());
			pstmt.setString(index++, pj.getPath());
			pstmt.setString(index++, pj.getUrl());
			pstmt.setString(index++, pj.getDes());
			pstmt.setString(index++, pj.getType());

			int updateRow =  pstmt.executeUpdate();
			if(updateRow > 0) {
				/**
                 * 获取刚刚插入进去的记录中关注的那几列的值 
                 */
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next()) {
                	genKey = rs.getInt(1);
                	pj.setId(genKey);
                }
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
		return genKey;
	}

	/**
	 * 保存项目
	 * 
	 * @param pj
	 *            项目
	 * @return 影响数量
	 */
	public int update(Pj pj) {
		String sql = "update pj set pj=?,path=?,url=?,des=?,type=? where id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, pj.getPj());
			pstmt.setString(index++, pj.getPath());
			pstmt.setString(index++, pj.getUrl());
			pstmt.setString(index++, pj.getDes());
			pstmt.setString(index++, pj.getType());
			pstmt.setInt(index++, pj.getId());

			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 获取具有相同路径或访问地址的项目数量
	 * 
	 * @param path
	 *            路径
	 * @param url
	 *            访问地址
	 * @return 具有相同路径或访问地址的项目数量
	 */
	public int getCount(String path, String url) {
		String sql = "select count(*) from pj where path=? or url=?";//TODO 大小写敏感?
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, path);
			pstmt.setString(index++, url);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(rs, pstmt, conn);
		}
		return 0;
	}
}