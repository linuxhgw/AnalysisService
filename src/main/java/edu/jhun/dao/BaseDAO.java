/**
 * 
 */
package edu.jhun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Administrator
 * @time 2018年1月19日下午1:14:05
 *
 */
public class BaseDAO {
	
	/**
	 * 执行查询
	 * @param conn 数据库连接
	 * @param sql
	 * @param params
	 * @return 结果集（包含查询到的数据）
	 */
	public static ResultSet query(Connection conn,String sql,Object... params){
		try{
			PreparedStatement ps = conn.prepareStatement(sql);
			for(int i = 0;i < params.length;i++){
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();
			return rs;
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		System.out.println("BaseDAO.query返回空值！");
		return null;
	}

}
