package org.svnadmin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.svnadmin.constant.Constants;
import org.svnadmin.entity.PjGrUsr;

/**
 * 项目的组的用户
 * 
 * @author <a href="mailto:yuanhuiwu@gmail.com">Huiwu Yuan</a>
 * 
 */
@Repository(PjGrUsrDao.BEAN_NAME)
public class PjGrUsrDao extends Dao {
	/**
	 * Bean名称
	 */
	public static final String BEAN_NAME = "pjGrUsrDao";

	/**
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param usr
	 *            用户
	 * @return 组用户
	 */
	public PjGrUsr get(int pjId, String gr, String usr) {
		String sql = "select a.pj_id,a.usr,a.gr,b.name as usrname from pj_gr_usr a left join usr b on (a.usr=b.usr) where a.pj_id = ? and a.gr=? and a.usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setInt(index++, pjId);
			pstmt.setString(index++, gr);
			pstmt.setString(index++, usr);

			rs = pstmt.executeQuery();
			if (rs.next()) {
				return readPjGrUsr(rs);
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
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @return 组用户列表
	 */
	public List<PjGrUsr> getList(int pjId, String gr) {
		String sql = "select a.pj_id,a.usr,a.gr,b.name as usrname from pj_gr_usr a left join usr b on (a.usr = b.usr) where a.pj_id=? and a.gr=? order by a.usr";
		List<PjGrUsr> list = new ArrayList<PjGrUsr>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setInt(index++, pjId);
			pstmt.setString(index++, gr);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readPjGrUsr(rs));
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
	 * 项目的组用户列表(用户可能为空),导出authz文件时使用
	 * 
	 * @param pj
	 *            项目
	 * @return 项目的组用户列表
	 */
	public List<PjGrUsr> getList(int pjId) {
		String sql = "select a.pj_id,a.gr,b.usr,c.name as usrname from pj_gr a left join pj_gr_usr b on (a.pj_id=b.pj_id and a.gr=b.gr) left join usr c on (b.usr = c.usr) where a.pj_id=? order by a.gr,b.usr";
		List<PjGrUsr> list = new ArrayList<PjGrUsr>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setInt(index++, pjId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readPjGrUsr(rs));
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
	 * 有相同的svn root的项目的组用户列表(用户可能为空),导出authz文件时使用
	 * 
	 * @param rootPath
	 *            svn root
	 * @return 有相同的svn root的项目组用户
	 */
	public List<PjGrUsr> getListByRootPath(String rootPath) {
		String sql = "select a.pj_id,a.gr,b.usr,c.name as usrname from pj_gr a left join pj_gr_usr b on (a.pj_id=b.pj_id and a.gr=b.gr) left join usr c on (b.usr=c.usr) "
				+ " where a.pj_id in (select distinct id from pj where type=? and path like ?) order by a.pj_id,a.gr,b.usr";
		List<PjGrUsr> list = new ArrayList<PjGrUsr>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, Constants.HTTP_MUTIL);
			pstmt.setString(index++, rootPath + "%");//TODO 大小写敏感?

			rs = pstmt.executeQuery();
			while (rs.next()) {
				list.add(readPjGrUsr(rs));
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
	 * @return 组用户
	 * @throws SQLException
	 *             jdbc异常
	 */
	PjGrUsr readPjGrUsr(ResultSet rs) throws SQLException {
		PjGrUsr result = new PjGrUsr();
		result.setPjId(rs.getInt("pj_id"));
		result.setUsr(rs.getString("usr"));
		result.setGr(rs.getString("gr"));
		result.setUsrName(rs.getString("usrname"));
		return result;
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 * @param usr
	 *            用户
	 */
	public void delete(int pjId, String gr, String usr) {
		String sql = "delete from pj_gr_usr where pj_id = ? and gr=? and usr=?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setInt(index++, pjId);
			pstmt.setString(index++, gr);
			pstmt.setString(index++, usr);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 删除
	 * 
	 * @param usr
	 *            用户
	 */
	public void deleteUsr(String usr) {
		String sql = "delete from pj_gr_usr where usr = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setString(index++, usr);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 * @param gr
	 *            组
	 */
	public void deletePjGr(int pjId, String gr) {
		String sql = "delete from pj_gr_usr where pj_id = ? and gr = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setInt(index++, pjId);
			pstmt.setString(index++, gr);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 删除
	 * 
	 * @param pj
	 *            项目
	 */
	public void deletePj(int pjId) {
		String sql = "delete from pj_gr_usr where pj_id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = this.getConnection();
			pstmt = conn.prepareStatement(sql);
			int index = 1;
			pstmt.setInt(index++, pjId);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			this.close(null, pstmt, conn);
		}
	}

	/**
	 * 保存
	 * 
	 * @param pjGrUsr
	 *            项目用户
	 */
	public void save(PjGrUsr pjGrUsr) {
		if (this.get(pjGrUsr.getPjId(), pjGrUsr.getGr(), pjGrUsr.getUsr()) == null) {
			String sql = "insert into pj_gr_usr (pj_id,usr,gr) values (?,?,?)";
			Connection conn = null;
			PreparedStatement pstmt = null;
			try {
				conn = this.getConnection();
				pstmt = conn.prepareStatement(sql);
				int index = 1;
				pstmt.setInt(index++, pjGrUsr.getPjId());
				pstmt.setString(index++, pjGrUsr.getUsr());
				pstmt.setString(index++, pjGrUsr.getGr());

				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				this.close(null, pstmt, conn);
			}
		}
	}

}