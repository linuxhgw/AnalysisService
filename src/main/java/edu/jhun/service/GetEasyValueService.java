package edu.jhun.service;

import edu.jhun.bean.MutilAttributeRequestedString;
import edu.jhun.bean.PreParedData;
import edu.jhun.bean.RequestedString;
import edu.jhun.bean.Statistic;
import edu.jhun.dao.CaculationDAO;
import edu.jhun.dao.StepValueDao;
import edu.jhun.dao.StepValueStepEqualDao;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hgw on 2018/7/5.
 */
public class GetEasyValueService {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // 获取统计数据
    public synchronized String getStatistics(String reqStr, Connection con) {
        RequestedString requestString = new RequestedString(reqStr);
        StepValueDao dbDao = new StepValueDao();
        String result = "";
        ArrayList<Statistic> sta = CaculationDAO
                .caculateStatistics(dbDao.getStructData(requestString, con));
        for (Statistic statistic : sta) {
            result += statistic.toString();
        }
        System.out.println(df.format(new Date()) + " Request the Service: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nResult:\n");
        System.out.println(result);

        return result;
    }

    // 获取步长，值
    public String/* ArrayList<PreParedData> */ getDirectData(String reqStr, Connection connection) {
        RequestedString requestString = new RequestedString(reqStr);
        String str = "";
        ArrayList<PreParedData> pre = new StepValueDao().getStructData(requestString, connection);
        for (PreParedData preParedData : pre) {
            str = str + preParedData.getStep() + "-" + preParedData.getOuputValue() + "-";
        }
        System.out.println(df.format(new Date()) + " Request the Service: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nResult:\n");
        System.out.println(str);
        return str;

    }

    // 获取步长，值
    public String/* ArrayList<PreParedData> */ getDirectTime(String reqStr, Connection con) {
        RequestedString requestString = new RequestedString(reqStr);

        String str = "";

        // 下一步就是去从数据库中取出数据(数据格式为:值:步长数)
        ArrayList<PreParedData> datacon1 = new StepValueDao().getStructData(requestString, con);


        for (PreParedData preParedData : datacon1) {
            str = str + preParedData.getTime() + "-" + preParedData.getOuputValue() + "-";
        }
        System.out.println(df.format(new Date()) + " Request the Service: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nResult:\n");
        System.out.println(str);

        return str;

    }


    public String/*ArrayList<PreParedData>*/ getDirectStepEqualData(String reqStr, Connection con) {
        MutilAttributeRequestedString requestString = new MutilAttributeRequestedString(reqStr);
        StepValueStepEqualDao dbDao = new StepValueStepEqualDao();
        String str = "";
        ArrayList<PreParedData> pre = new StepValueDao().getStructData(requestString, con);
        for (PreParedData preParedData : pre) {
            str = str + preParedData.getStep() + "-" + preParedData.getOuputValue() + "-";
        }
        System.out.println(df.format(new Date()) + " Request the Service: " + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nResult:\n");
        System.out.println(str);
        return str;

    }

}
