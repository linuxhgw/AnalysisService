package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.jhun.utils.DBHelper;





public class MemberDAO  extends BaseDAO{
	//private static DBHelper2 dbhelper=new DBHelper2("root", "root");
	private Connection connection = DBHelper.getConnection();
	public String MemberResult(String SchemeId) throws SQLException {
		String sql = "SELECT SCHEME_MEMBER_ID  , model_name FROM simu_model_info where SCHEME_ID =?";
		ResultSet rs = null;
//		PreparedStatement preparedStatement = dbhelper.connection.prepareStatement(sql);
//		preparedStatement.setString(1, SchemeId);
//		rs = preparedStatement.executeQuery();
//	
		rs= query(connection, sql, SchemeId);
        String formulalist="";
		if (rs != null) {
			try {
				while (rs.next()) {
					formulalist=formulalist+rs.getString("model_name")+"-"+rs.getString("SCHEME_MEMBER_ID")+";";
				}
				return formulalist;
			} catch (SQLException e) {
				e.printStackTrace();
				return "FormulaLoadFail";
			}
		}
		return "FormulaLoadFail";
	
		
	}
}
