package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.jhun.bean.MutilAttributeRequestedString;
import edu.jhun.bean.RequestedString;
import edu.jhun.utils.DBHelper;

public class StepValueStepEqualDao extends BaseDAO{

	private String caseName;//表名
	//数据库名称：simulationdatacollection
	//表名：datacollect0
	public StepValueStepEqualDao() {
	
		this.caseName=null;
	}
	


	public synchronized ResultSet findReqData(MutilAttributeRequestedString reqStr,Connection con)
	{
		//System.out.println("_______________________"+reqStr);
		caseName=reqStr.getCaseName();
		ResultSet rs=null;
		rs=query(con, 
				"select d1.Outputvalue, d2.Outputvalue "+
				"from "+ caseName+ " d1,"+caseName+" d2 "+
				"where d1.MemberId=? and d2.MemberId=? and d1.step=d2.step and d1.step BETWEEN ? and ?"
				,reqStr.getMemberId(),reqStr.getAnotherAtrributes(),reqStr.getStartStep(),reqStr.getEndStep());
		if(rs==null){
			System.out.println("null______________________________________");
		}
		
		
		return rs;
	}
 }
