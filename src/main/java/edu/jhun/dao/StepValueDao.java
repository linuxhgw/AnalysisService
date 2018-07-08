package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import edu.jhun.bean.PreParedData;
import edu.jhun.bean.RequestedString;

public class StepValueDao extends BaseDAO {

    //根据条件取数据库的数据：Outputvalue,step
    public synchronized  ArrayList<PreParedData> getStructData(RequestedString reqStr, Connection con) {

        ArrayList<PreParedData> preList=new ArrayList<PreParedData>();
        String outputvalue;
        ResultSet rs = null;
        String sql = "select Outputvalue,step,time from "+reqStr.getCaseName()+" where RunID=? and MemberId=? and step between ? and ?";

        rs = query(con,sql,reqStr.getRunID(), reqStr.getMemberId(),
                reqStr.getStartStep(), reqStr.getEndStep());
        try {
            while (rs.next()) {
                outputvalue = rs.getString(1);
                JSONObject JSONData = JSONObject.parseObject(outputvalue);
                JSONObject AttributeCollection = (JSONObject) JSONData.get(reqStr.getMemberId());
                String Attributevalue = AttributeCollection.get(reqStr.getAttributeId()).toString();
                PreParedData pre=new PreParedData(Double.parseDouble(Attributevalue),rs.getInt(2),rs.getDouble(3));
                preList.add(pre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preList;
    }

}
