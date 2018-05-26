package edu.jhun.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper2 {
	
	public static String url= "jdbc:mysql://172.16.73.131:3306/scenariodb";
	public static Connection connection;
	
	
	public DBHelper2(String sqlName, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("无法加载数据库驱动");
			e.printStackTrace();			
		}
		try {
			connection = (Connection) DriverManager.getConnection(url, sqlName, password);
			System.out.println("数据库连接成功");
		} catch (SQLException e) {
			System.out.println("创建数据库出错");
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
	public void ExecuteInsert(String sql) {
		try {
			Statement stmt=connection.createStatement();
			stmt.execute(sql);
		} catch (SQLException e) {
			System.out.println("ExecuteInsert()Exception:SQLException");
			e.printStackTrace();
		}
		
	}
	
	public void ExecuteDelete(String sql){
		try{	   
			Statement stmt=connection.createStatement();
			stmt.execute(sql);
		}catch(SQLException e){
			System.out.println("ExecuteDelete()Exception:SQLException");
			e.printStackTrace();
		}
	}
	
	public void ExecuteUpdate(String sql) {
		
		try {
			Statement stmt=connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.out.println("ExecuteUpdate()Exception:SQLException");
			e.printStackTrace();
		}
	}
	
	public ResultSet ExecuteQuery(String sql) throws SQLException {		   
		  
		Statement stmt=connection.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		return rs;
	   }
	
	
}