/**
 * 
 */
package edu.jhun.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Administrator
 * @time 2018年1月19日下午1:08:25
 *
 */
public class DBHelper {
	
	static boolean DBflag=true;//默认连接ORACLE的数据库
	static String DBType;
	
	//获取数据库连接
	public static Connection getConnection()

	{
		Connection con = null;
		try {
			String encording="UTF-8";
			String lineTxt=null;
			String serverIP=null;
			String username=null;
			String password=null;
			String database=null;
			File file=new File("DB_INFO/DBType.txt");
			InputStreamReader read=new InputStreamReader(new FileInputStream(file),encording);
			//			FileOutputStream fos=new FileOutputStream(file);
			BufferedReader bufferedReader=new BufferedReader(read);//读取数据库类型的文件流
			while((lineTxt=bufferedReader.readLine())!=null)
			{
				if(lineTxt.equals("DBType"))
				{
					String Style=bufferedReader.readLine();
					if(Style.equalsIgnoreCase("MYSQL"))
					{
						DBType="MYSQL";
						DBflag=false;
						System.out.println("数据库类型为MySql");
					}
					else if(Style.equalsIgnoreCase("ORACLE"))
					{
						DBType="ORACLE";
						System.out.println("数据库类型为Oracle");
					}
				}
			}
			bufferedReader.close();
			if(DBflag==true)//连接的是Oracle 的数据库
			{
				File file1=new File("DB_INFO/OracleDBset.txt");
				InputStreamReader read1=new InputStreamReader(new FileInputStream(file1),encording);
				BufferedReader bufferedReader1=new BufferedReader(read1);
				while((lineTxt=bufferedReader1.readLine())!=null)
				{		
					if(lineTxt.equals("ServerIP")){
						serverIP=bufferedReader1.readLine();
						System.out.println("DB_IP: "+serverIP);
					}
					if(lineTxt.equals("username")){
						username=bufferedReader1.readLine();
						System.out.println(username);
					}
					if(lineTxt.equals("password")){
						password=bufferedReader1.readLine();
						System.out.println(password);
					}
				}
			}
			else//连接的是MYSQL 的数据库
			{
				File file1=new File("DB_INFO/MySQLDBset2.txt");
				InputStreamReader read1=new InputStreamReader(new FileInputStream(file1),encording);
				BufferedReader bufferedReader1=new BufferedReader(read1);
				while((lineTxt=bufferedReader1.readLine())!=null)
				{		
					if(lineTxt.equals("ServerIP")){
						serverIP=bufferedReader1.readLine();
						System.out.println("DB_IP: "+serverIP);
					}
					if(lineTxt.equals("username")){
						username=bufferedReader1.readLine();
						//System.out.println(username);
					}
					if(lineTxt.equals("password")){
						password=bufferedReader1.readLine();
						//System.out.println(password);
					}
					if(lineTxt.equals("database")){
						database=bufferedReader1.readLine();
						//System.out.println(database);
					}
				}
			}
			if(DBflag==true)//连接的是Oracle的数据库
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				con = (Connection) DriverManager.getConnection("jdbc:oracle:thin:@"+serverIP+":1521:xe", username, password);
				System.out.println("数据库Oracle连接成功");
			}
			else//连接的是MySql 的数据库
			{
				//System.out.println("LocalHostIP :"+InetAddress.getLocalHost().getHostAddress());
				Class.forName("com.mysql.jdbc.Driver");
				con=(Connection) DriverManager.getConnection("jdbc:mysql://"+serverIP+":3306/"+database,username,password);
				System.out.println("数据库MySql连接成功,连接库为："+database);
				System.out.println("jdbc:mysql://"+serverIP+":3306/"+database);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("连接数据库失败"+e.getMessage());
		}
		return con;
	}

}
