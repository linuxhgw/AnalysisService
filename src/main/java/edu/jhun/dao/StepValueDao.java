package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;

import edu.jhun.bean.RequestedString;

public class StepValueDao extends BaseDAO{

	private String caseName;//表名
	//数据库名称：simulationdatacollection
	//表名：datacollect0
	public StepValueDao() {
	
		this.caseName=null;
	}
	
	
	//根据条件取数据库的数据：Outputvalue,step
	
	public synchronized ResultSet findReqData(RequestedString reqStr,Connection con)
	{
		caseName=reqStr.getCaseName();
		ResultSet rs=null;
		rs=query(con, 
				"select Outputvalue,step,time from "+caseName+" where RunID=? and MemberId=? and step between ? and ?",
				reqStr.getRunID(),reqStr.getMemberId(),reqStr.getStartStep(),reqStr.getEndStep());

		return rs;
	}
 }
