/**
 *
 */
package edu.jhun.dao;

import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edu.jhun.bean.PreParedData;
import edu.jhun.bean.RequestedString;
import edu.jhun.bean.SimulationAttribute;
import weka.Run;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Administrator
 * @time 2018年1月22日下午3:39:59
 */
public class InstanceDAO extends BaseDAO {

    private String caseName;//表名
    private String MemberId;


    private String AttributeId;
    private String AttributeId2;
    private String startStep;
    private String endStep;
    private String RunID;



    /**
     * 1.线性回归获取分析数据实例集
     *
     * @param connection 数据库连接对象
     * @param attrId     欲分析属性id ex:[1-1-3]
     * @param steplength 欲分析步长范围 ex:[1-5000]
     * @return weka数据集
     */
    public synchronized Instances getLRDataset(Connection connection, String attrId, String steplength) {

        int maxAttrs = 0;

        RequestedString splitParamenter = new RequestedString(attrId, steplength);
        List<String> PARA_ID =new  ArrayList<String>();
        caseName = splitParamenter.getCaseName();
        MemberId = splitParamenter.getMemberId();
        AttributeId = splitParamenter.getAttributeId();
        startStep = splitParamenter.getStartStep();
        endStep = splitParamenter.getEndStep();
        RunID = splitParamenter.getRunID();

        Instances dataset = null;
        String sql = "SELECT  PARA_ID from model_para_info " +
                " WHERE PARA_TYPE=\'output_para\' AND MUID = ?";
        ResultSet rs = query(connection, sql, MemberId);
        try {
           while (rs.next()){
               PARA_ID.add(rs.getString(1));
               maxAttrs++;
           }
            //建立属性list，储存属性名
            ArrayList<Attribute> weka_attrs = new ArrayList<Attribute>();
            for (int i = 0; i < maxAttrs; i++) {
                String attributeName = caseName + "-" + String.valueOf(i + 1);
                weka_attrs.add(i, new Attribute(attributeName, i));
            }
            //建立实例数据集
            dataset = new Instances(caseName, weka_attrs, weka_attrs.size());
            //定位要分析的属性位置
            dataset.setClassIndex(PARA_ID.indexOf(AttributeId));
            //为各个属性添加属性值实例
            List<SimulationAttribute> attrs = new ArrayList<SimulationAttribute>();
            String outputvalue;
            rs = query(connection, "select * from " + caseName +
                    " where RunID=? and MemberId=? and step between ? and ?",RunID, MemberId, startStep, endStep);
            while (rs.next()) {
                outputvalue = rs.getString("Outputvalue");
                JSONObject JSONData = JSONObject.parseObject(outputvalue);

                JSONObject  AttributeCollection =(JSONObject) JSONData.get(MemberId);
                for (int i = 0;i<AttributeCollection.size();i++){
                    String Attributevalue1 = AttributeCollection.get(PARA_ID.get(i)).toString();
                    SimulationAttribute attr = new SimulationAttribute();
                    attr.setSchemeId(caseName);
                    attr.setMemberId(MemberId);
                    attr.setAttrId(String.valueOf(PARA_ID.get(i)));
                    attr.setStep(rs.getInt("step"));
                    attr.setOutputvalue(Double.parseDouble(Attributevalue1));

                    attrs.add(attr);
                }

            }
            for (int i = Integer.parseInt(startStep); i <= Integer.parseInt(endStep); i++) {
                //创建单个实例（即一组分析属性值）
                Instance instance = new DenseInstance(weka_attrs.size());
                for (int j = 0; j < maxAttrs; j++) {
                    instance.setValue(weka_attrs.get(j), attrs.get(j+maxAttrs*(i - Integer.parseInt(startStep))).getOutputvalue());
                }
                //将单个实例添加到实例数据集中
                dataset.add(instance);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("222");
        }
        return dataset;
    }

    /**
     * 2.单一线性回归获取分析数据实例集
     *
     * @param connection 数据库连接对象
     * @param attrId     欲分析属性id ex:[1-1-2-3]
     * @param steplength 欲分析步长范围 ex:[1-5000]
     * @return weka数据集
     */
    public synchronized Instances getSLRDataset(Connection connection, String attrIndex, String steplength) {


        RequestedString splitParamenter = new RequestedString(attrIndex, steplength);

        this.caseName = splitParamenter.getCaseName();
        MemberId = splitParamenter.getMemberId();
        AttributeId = splitParamenter.getAttributeId();
        AttributeId2 = splitParamenter.getAttributeId2();
        startStep = splitParamenter.getStartStep();
        endStep = splitParamenter.getEndStep();
        RunID = splitParamenter.getRunID();


        Instances dataset = null;
        //建立属性list，储存属性名
        ArrayList<Attribute> weka_attrs = new ArrayList<Attribute>();
        weka_attrs.add(0, new Attribute(AttributeId, 1));
        weka_attrs.add(1, new Attribute(AttributeId2, 2));
        //建立实例数据集
        dataset = new Instances(caseName, weka_attrs, weka_attrs.size());
        //定位要分析的属性位置
        dataset.setClassIndex(1);
        List<SimulationAttribute> attrs = new ArrayList<SimulationAttribute>();
        //找出属性一和属性二的步长输出值

        ArrayList<PreParedData> preList=new ArrayList<PreParedData>();
        String outputvalue;
        ResultSet rs = null;
        String sql = "select Outputvalue,step,time from "+caseName+" where RunID=? and MemberId=? and step between ? and ?";

        rs = query(connection,sql,RunID, MemberId,
                startStep, endStep);
        try {
            while (rs.next()) {
                outputvalue = rs.getString(1);
                JSONObject JSONData = JSONObject.parseObject(outputvalue);
                JSONObject AttributeCollection = (JSONObject) JSONData.get(MemberId);
                String Attributevalue1 = AttributeCollection.get(AttributeId).toString();
                String Attributevalue2 = AttributeCollection.get(AttributeId2).toString();
                SimulationAttribute attr = new SimulationAttribute();
                attr.setSchemeId(caseName);
                attr.setMemberId(MemberId);
                attr.setAttrId(AttributeId);
                attr.setStep(rs.getInt("step"));
                attr.setOutputvalue(Double.parseDouble(Attributevalue1));
                attrs.add(attr);
                SimulationAttribute attr2 = new SimulationAttribute();
                attr2.setSchemeId(caseName);
                attr2.setMemberId(MemberId);
                attr2.setAttrId(AttributeId2);
                attr2.setStep(rs.getInt("step"));
                attr2.setOutputvalue(Double.parseDouble(Attributevalue2));
                attrs.add(attr2);
            }
            for (int i = Integer.parseInt(startStep); i <= Integer.parseInt(endStep); i++) {
                //创建单个实例（即一组分析属性值）
                Instance instance = new DenseInstance(weka_attrs.size());
                for (int j = 0; j < 2; j++) {
                    instance.setValue(weka_attrs.get(j), attrs.get(j + 2 * (i - Integer.parseInt(startStep))).getOutputvalue());
                }
                //将单个实例添加到实例数据集中
                dataset.add(instance);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dataset;
    }


}
