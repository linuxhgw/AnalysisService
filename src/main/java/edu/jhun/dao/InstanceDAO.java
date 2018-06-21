/**
 * 
 */
package edu.jhun.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.jhun.bean.SimulationAttribute;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Administrator
 * @time 2018年1月22日下午3:39:59
 *
 */
public class InstanceDAO extends BaseDAO{
	
	/**
	 * 1.线性回归获取分析数据实例集
	 * @param connection 数据库连接对象
	 * @param attrId 欲分析属性id ex:[1-1-3]
	 * @param steplength 欲分析步长范围 ex:[1-5000]
	 * @return weka数据集
	 */
	public synchronized static Instances getLRDataset(Connection connection , String attrId,String steplength){

		int maxAttrs = 0;

		String[] s1= new String[attrId.length()];
		s1=attrId.split("-");//以-为分界符找到类属性的位置，s1[2]
		String schemeName = s1[0];
		String schemeId = s1[1];
		String memberId = s1[2];
		int attrIndex = Integer.valueOf(s1[3]);
		System.out.println("attrindex"+attrIndex);

		String[] s2= new String[steplength.length()];
		s2=steplength.split("-");//以-为分界符找到分析步长，s2[0]起s2[1]止
		int startStep = Integer.valueOf(s2[0]);
		int endStep = Integer.valueOf(s2[1]);

		Instances dataset = null;
		try {//查询数据进行算法计算
			ResultSet rs = query(connection, "SELECT MAX(MemberId) FROM " +schemeName+ " WHERE RunID = ?", schemeId);
			if (rs.first()) {
				maxAttrs = rs.getInt(1);//查询方案中属性个数
				rs.close();
			}else {
				return null;
			}
			//建立属性list，储存属性名
			ArrayList<Attribute> weka_attrs = new ArrayList<Attribute>();
			for (int i = 0; i < maxAttrs; i++) {
				String attributeName = schemeId +"-"+String.valueOf(i+1);
				weka_attrs.add(i,new Attribute(attributeName, i));
			}
			//建立实例数据集
			dataset = new Instances(schemeId, weka_attrs, weka_attrs.size());
			//定位要分析的属性位置
			dataset.setClassIndex(attrIndex-1);
			//为各个属性添加属性值实例
			List<SimulationAttribute> attrs = new ArrayList<SimulationAttribute>();
			
			rs = query(connection, "select * from " +schemeName+ " where RunID = ? and step between ? and ?",schemeId, startStep,endStep);
			while (rs.next()) {
				SimulationAttribute attr = new SimulationAttribute();
				attr.setSchemeId(schemeId);
				attr.setMemberId(memberId);
				attr.setAttrId(String.valueOf(attrIndex));
				attr.setStep(rs.getInt("step"));
				attr.setOutputvalue(rs.getDouble("Outputvalue"));
				attrs.add(attr);
			}
			for (int i = startStep; i <= endStep; i++) {
				//创建单个实例（即一组分析属性值）
				Instance instance = new DenseInstance(weka_attrs.size());
				for (int j = 0; j < maxAttrs; j++) {
					instance.setValue(weka_attrs.get(j), attrs.get(j+3*(i-startStep)).getOutputvalue());
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
	 * @param connection 数据库连接对象
	 * @param attrId 欲分析属性id ex:[1-1-2-3]
	 * @param steplength 欲分析步长范围 ex:[1-5000]
	 * @return weka数据集
	 */
	public synchronized static Instances getSLRDataset(Connection connection, String attrIndex, String steplength){
		
		String[] s1= new String[attrIndex.length()];
		s1=attrIndex.split("-");//以-为分界符找到类属性的位置
		String schemeName = s1[0];
		String schemeId = s1[1];
		String memberId = s1[2];
		String attr1 = s1[3];//简单线性回归属性1自变量
		String attr2 = s1[4];//简单线性回归属性2因变量
		
		System.out.println("atrr1"+s1[3]+"atrr2"+s1[4]);
		String[] s2= new String[steplength.length()];
		s2=steplength.split("-");//以-为分界符找到分析步长，s2[0]起s2[1]止
		int startStep = Integer.valueOf(s2[0]);
		int endStep = Integer.valueOf(s2[1]);
		
		Instances dataset = null;
		//建立属性list，储存属性名
		ArrayList<Attribute> weka_attrs = new ArrayList<Attribute>();
		weka_attrs.add(0,new Attribute(attr1, 1));
		weka_attrs.add(1,new Attribute(attr2, 2));
		//建立实例数据集
		dataset = new Instances(schemeId, weka_attrs, weka_attrs.size());
		//定位要分析的属性位置
		dataset.setClassIndex(1);
		List<SimulationAttribute> attrs = new ArrayList<SimulationAttribute>();
		ResultSet rs = query(connection, "select * from " +schemeName+ " where FederationId = ? and (MemberId = ? or MemberId = ?) and step between ? and ?", schemeId, attr1, attr2, startStep, endStep);
		
		try {
			while (rs.next()) {
				SimulationAttribute attr = new SimulationAttribute();
				attr.setSchemeId(schemeId);
				attr.setMemberId(memberId);
				attr.setAttrId(rs.getString("memberId"));
				attr.setStep(rs.getInt("step"));
				attr.setOutputvalue(rs.getDouble("Outputvalue"));
				attrs.add(attr);
			}
			for (int i = startStep; i <= endStep; i++) {
				//创建单个实例（即一组分析属性值）
				Instance instance = new DenseInstance(weka_attrs.size());
				for (int j = 0; j < 2; j++) {
					instance.setValue(weka_attrs.get(j), attrs.get(j+2*(i-startStep)).getOutputvalue());
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
