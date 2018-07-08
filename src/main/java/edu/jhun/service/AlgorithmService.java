
package edu.jhun.service;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import edu.jhun.bean.PreParedData;
import edu.jhun.bean.RequestedString;
import edu.jhun.dao.*;
import weka.associations.Apriori;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instances;
import weka.core.converters.DatabaseLoader;

/**
 * @author Administrator
 * @time 2018年1月20日下午12:49:59
 */
public class AlgorithmService {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    // 设置日期格式

    private static final boolean car = false;
    private static final int classIndex = -1;
    private static final double delta = 0.05;
    private static final boolean doNotCheckCapabilities = false;
    private static final double lowerBoundMinSupport = 0.5;
    private static final double minMetric = 0.9;
    private static final int numRules = 10;
    private static final boolean outputItemSets = true;
    private static final boolean removeAllMissingCols = false;
    private static final double significanceLevel = -1.0;
    private static final boolean treatZeroAsMissing = false;
    private static final double upperBoundMinSupport = 1.0;
    private static final boolean verbose = false;

    // 检查最大步数
    private synchronized boolean checkMaxStep(Connection connection, String attrId, String steplength) {

        int maxStep = 0;
        String[] s1 = new String[attrId.length()];
        s1 = attrId.split("-");// 以-为分界符找到类属性的位置，s1[2]
        String schemeName = s1[0];
        String schemeId = s1[1];

        String[] s2 = new String[steplength.length()];
        s2 = steplength.split("-");// 以-为分界符找到分析步长，s2[0]起s2[1]止
        int endStep = Integer.valueOf(s2[1]);
        ResultSet rs = InstanceDAO.query(connection, "SELECT MAX(step) FROM " + schemeName);
        try {
            if (rs.first()) {
                maxStep = rs.getInt(1);// 查询方案中步数最大值
                System.out.println(maxStep);
                rs.close();
            } else {
                return false;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (endStep <= maxStep) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 通过weka工具获取数据库数据集
     *
     * @param tb 数据库表名
     * @return weka数据集
     */

    private synchronized Instances getDatasetByDB(String tb) {
        final String sqlUrl = "jdbc:mysql://localhost:3306/test";
        final String sqlUser = "hgw";
        final String sqlPwd = "hgw11";
        Instances dataset = null;
        try {
            DatabaseLoader loader = new DatabaseLoader();
            loader.setUrl(sqlUrl);
            loader.setUser(sqlUser);
            loader.setPassword(sqlPwd);
            loader.setQuery("select * from " + tb);
            dataset = loader.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public synchronized String getSimpleLinearRegression(Connection connection, String attrIndex, String steplength) {

        System.out.println(df.format(new Date()) + "  Request the Service -START: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "***");
        double[] res = null;
        if (!checkMaxStep(connection, attrIndex, steplength)) {
            return "Step out of bounds!";
        }

        Instances dataset = new InstanceDAO().getSLRDataset(connection, attrIndex, steplength);
        // 进行线性回归运算
        LinearRegression linearRegression = new LinearRegression();
        try {
            // 为给出的数据建立回归模型
            linearRegression.buildClassifier(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回函数回归系数
        res = linearRegression.coefficients();
        System.out.println("The length of coef is:" + res.length);
        System.out.println(df.format(new Date()) + "  Request the Service -END: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "***" + "\nResult:");
        for (double coef : res) {

            System.out.println(coef);
        }
        for (double coef : res) {

            System.out.println(coef);
        }
        String str = "";
        for (int i = 0; i < res.length; i++) {
            str += res[i] + "$";
        }
        return str;
    }

    /**
     * 线性回归算法服务
     *
     * @param connection 数据库连接对象
     * @param schemeId   欲分析方案id ex:[1]
     * @param attrId     欲分析属性id ex:[1-1-3]
     * @param steplength 欲分析步长范围 ex:[1-5000]
     * @return 线性回归关系表达式
     */
    public synchronized String getLinearRegression(Connection connection, String attrId, String steplength) {

        System.out.println(df.format(new Date()) + "  Request the Service -START: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "***");
        if (!checkMaxStep(connection, attrId, steplength)) {
            System.out.println("Step out of bounds!");
            return "Step out of bounds";
        }
        Instances dataset = new  InstanceDAO().getLRDataset(connection, attrId, steplength);
        // 进行线性回归运算
        LinearRegression linearRegression = new LinearRegression();
        try {
            // 为给出的数据建立回归模型
            linearRegression.buildClassifier(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回函数回归系数
        double[] res = linearRegression.coefficients();
        for (double coef : res) {

            System.out.println(coef);
        }
        String result = "";
        for (int i = 0; i < res.length; i++) {
            result += res[i] + "$";
        }
        /// String result = linearRegression.toString();
        // double[] coef = linearRegression.coefficients();
        // String result = "(" + attrId +")" + "=" ;
        // for (int i = 0; i < coef.length; i++) {
        // if (i==coef.length-1) {
        // result += coef[i] ;
        // }else {
        // result += coef[i] + "* (" + schemeId +"-"+ (i+1) +")"+ "+";
        // }
        // }
        System.out.println(df.format(new Date()) + "  Request the Service -END: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "***" + "\nResult:");
        System.out.println(result);
        return result;

    }

    /**
     * @param tb 数据库表名
     * @return apriori算法结果
     */
    public synchronized String getApriori(String tb) {

        System.out.println(df.format(new Date()) + "  Request the Service -START: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "***");
        // 创建数据集实例对象
        Instances dataset = null;
        // 读取weka数据库文件，并配置nominal
        dataset = getDatasetByDB(tb);// "tb_contact_lenses"

        // 设置类属性索引
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // 获取Apriori函数实例 ，进行算法计算 ，并输出结果
        String aprioriResult = null;
        String string = null;
        Apriori apriori = new Apriori();
        // 设置算法运行参数
        // 关联规则：false：全局关联
        apriori.setCar(car);
        // 类属性索引
        apriori.setClassIndex(classIndex);
        // 迭代递减单位
        apriori.setDelta(delta);
        // 不检查容量
        apriori.setDoNotCheckCapabilities(doNotCheckCapabilities);
        // 最小支持度下界
        apriori.setLowerBoundMinSupport(lowerBoundMinSupport);
        // 度量最小值
        apriori.setMinMetric(minMetric);
        // 关联规则数
        apriori.setNumRules(numRules);
        // 是否输出项集
        apriori.setOutputItemSets(outputItemSets);
        // 移除全部为缺省的列
        apriori.setRemoveAllMissingCols(removeAllMissingCols);
        // 重要程度
        apriori.setSignificanceLevel(significanceLevel);
        // 是否视0为丢失值
        apriori.setTreatZeroAsMissing(treatZeroAsMissing);
        // 最小支持度上届
        apriori.setUpperBoundMinSupport(upperBoundMinSupport);
        // 是否以冗余模式运行
        apriori.setVerbose(verbose);

        // 开始建立关联算法运算
        try {
            apriori.buildAssociations(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        string = apriori.toString();

        File file = new File("apriori.txt");
        PrintStream ps;
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            ps = new PrintStream(new FileOutputStream(file));
            ps.println("=====说明=====");// 往文件里写入字符串
            ps.append("<conf>:置信度（百分比）\n<lift>:提升度  （min=1,表示事件独立,越大关联越紧密）\n"
                    + "<lev>:杠杆率 （min=0,表示事件独立,越大关联越紧密）\n<conv>:确信度,越大关联越紧密\n");
            ps.append("============");
            ps.append(string);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringBuilder.append(line).append("$");
            }
            aprioriResult = stringBuilder.toString();
            System.out.println(stringBuilder.toString());
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(df.format(new Date()) + "  Request the Service -END: "
                + Thread.currentThread().getStackTrace()[1].getMethodName() + "***" + "\nResult:");
        return aprioriResult;
    }

    /**
     * @param connection [数据库的连接]
     * @param reqStr     [请求的数据格式]
     * @return str [返回时域分析的一串数组]
     */
    public String getTimeDomainAnalysis(Connection connection, String reqStr) {

        // 已经分好的每个方案名,成员,属性,起始步长,和结束步长并封装到一个类中
        RequestedString requestString = new RequestedString(reqStr);

        // 下一步就是去从数据库中取出数据(数据格式为:值:步长数)
        ArrayList<PreParedData> datacon = new StepValueDao().getStructData(requestString, connection);


        // 下一步就是进行时域分析写一个函数返回的是一串double[]数组
        String str = new TimeDomainDAO().CaculateStatistics(datacon);
        // 测试

        System.out.println(str);
        return str;
    }
    // 计算终值


}
