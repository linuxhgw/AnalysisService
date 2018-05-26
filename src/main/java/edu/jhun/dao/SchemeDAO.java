package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.jhun.utils.DBHelper;



public class SchemeDAO extends BaseDAO {
	//private static DBHelper2 dbhelper=new DBHelper2("root", "root");
	private Connection connection = DBHelper.getConnection();
	public String SchemeResult() {
		String sql = "SELECT SCHEME_ID,SCHEME_NAME FROM SIMU_SCHEME";
		ResultSet rs = null;
	
			rs= query(connection, sql);
			//rs = dbhelper.ExecuteQuery(sql);
		
		String shemeId = "";
		String shemeName = "";
        String formulalist="";
		if (rs != null) {
			try {
				while (rs.next()) {
					shemeId = rs.getString("SCHEME_ID");
					shemeName = rs.getString("SCHEME_NAME");
					formulalist=formulalist+(shemeName+"-"+shemeId)+";";
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
