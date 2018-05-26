package edu.jhun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.jhun.bean.PreParedData;
import edu.jhun.bean.Statistic;

public class CaculationDAO {

	public synchronized static ArrayList<Statistic> caculateStatistics(ArrayList<PreParedData> preList)
	{
		double temp;
		double sum=0.0;
		int count=0;
		ArrayList<Statistic> staList=new ArrayList<Statistic>();
		Statistic max=new Statistic("最大值",0);
		Statistic avg=new Statistic("平均值",0);
		Statistic min=new Statistic("最小值",0);	
		Statistic var=new Statistic("方差",0);
		staList.add(max);		
		staList.add(min);
		staList.add(avg);
		staList.add(var);
		min.setValue(preList.get(0).getOuputValue());
		max.setValue(preList.get(0).getOuputValue());
		for (PreParedData preParedData : preList) {
			temp=preParedData.getOuputValue();
			if(min.getValue()>temp)
			{
				min.setValue(temp);               //最小值
			}
			if(max.getValue()<temp)
			{
				max.setValue(temp);                //最大值
			}
			sum=sum+temp;
			count++;
		}
		if(count>0)
		{
			double tempAvg=sum/count;
			avg.setValue(tempAvg);                   //均值
			for(PreParedData preParedData : preList)
			{
				var.setValue(var.getValue()+(preParedData.getOuputValue()-tempAvg)*(preParedData.getOuputValue()-tempAvg));
			}
			var.setValue(Math.sqrt(var.getValue())/count);        //方差
		}
		else{
			//抛异常
			System.out.println("无数据");			
		}			
		return staList;
	}

	//结果集转存，Outputvalue,step
	public synchronized static ArrayList<PreParedData> getStructData(ResultSet rs)
	{
		ArrayList<PreParedData> preList=new ArrayList<PreParedData>();
		try {
			while(rs.next())
			{
				PreParedData pre=new PreParedData(Double.parseDouble(rs.getString(1)),rs.getInt(2));
				preList.add(pre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return preList;

	}

}
