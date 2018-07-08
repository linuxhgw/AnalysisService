package edu.jhun.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import edu.jhun.bean.PreParedData;
import edu.jhun.bean.Statistic;

public class CaculationDAO {

    public synchronized static ArrayList<Statistic> caculateStatistics(ArrayList<PreParedData> preList) {
        double CurrentOutputValue;
        double sum = 0.0;
        int count = preList.size();
        ArrayList<Statistic> staList = new ArrayList<Statistic>();
        Statistic max = new Statistic("最大值", 0);
        Statistic avg = new Statistic("平均值", 0);
        Statistic min = new Statistic("最小值", 0);
        Statistic var = new Statistic("方差", 0);
        staList.add(max);
        staList.add(min);
        staList.add(avg);
        staList.add(var);
        min.setValue(preList.get(0).getOuputValue());
        max.setValue(preList.get(0).getOuputValue());
        for (PreParedData preParedData : preList) {
            CurrentOutputValue = preParedData.getOuputValue();
            min.setValue(Math.min(min.getValue(), CurrentOutputValue));
            max.setValue(Math.max(max.getValue(), CurrentOutputValue));

            sum += CurrentOutputValue;

        }
        if (count > 0) {
            double tempAvg = sum / count;
            avg.setValue(tempAvg);                   //均值
            for (PreParedData preParedData : preList) {//方差的计算
                var.setValue(var.getValue() + (preParedData.getOuputValue() - tempAvg) * (preParedData.getOuputValue() - tempAvg));
            }
            var.setValue(Math.sqrt(var.getValue()) / count);        //方差
        } else {
            //抛异常
            System.out.println("无数据");
        }
        return staList;
    }


}
