package edu.jhun.dao;

import edu.jhun.bean.PreParedData;
import weka.core.pmml.jaxbbindings.MiningBuildTask;

import java.util.ArrayList;

/**
 * Created by hgw on 2018/3/21.
 */
public class TimeDomainDAO {
	
 

	//终值
    public  double FinalValue(ArrayList<PreParedData> preList){
        double finalvalue = 0;
       
        double[] MaxMix = new double[2];
       int length =   preList.size();
        finalvalue = preList.get(length-1).getOuputValue();
        MaxMix[0]=finalvalue;
        MaxMix[1]=finalvalue;
        double max =finalvalue;
        double min=finalvalue ;
        double piont =finalvalue ;
        double Flag=0 ;
        for(int i = length-2;i>0;i-- ){
        	if(preList.get(i).getOuputValue()<preList.get(i-1).getOuputValue()){
        			MaxMix[1] =preList.get(i).getOuputValue();
        	}
        	if(preList.get(i).getOuputValue()>preList.get(i-1).getOuputValue()){
        			
            			MaxMix[0] =preList.get(i).getOuputValue();
        		}
			
        	
        		double tem=2*(Math.abs(MaxMix[0]-MaxMix[1]))/(MaxMix[0]+MaxMix[1]);
        		if(tem>0.02){
        			finalvalue=(MaxMix[0]+MaxMix[1])/2 ;
        			break;
        	
        		}
        

    }      
        return finalvalue;
    
        }

    public double overShoot(ArrayList<PreParedData> preList){
        double finalvalue = 0;
        double maxValue = 0 ;
        double overShoot = 0;//超调量
        finalvalue = FinalValue(preList);
        for (int i = 0; i < preList.size(); i++) {

            maxValue =  Math.max(maxValue, preList.get(i).getOuputValue());

        }
        System.out.println(maxValue+"最大值是");
        System.out.println("终值是"+finalvalue);
        overShoot = (maxValue-finalvalue)/finalvalue;
        return overShoot;
    }

    //计算每个量写到一个数组中啦啦啦

    public synchronized String CaculateStatistics(ArrayList<PreParedData> preList) {

        double maxValue=0;
        double[] staList = new double[6];
        String qString = "";
        double finalvalue = 0;

        double overShoot = 0;//超调量
        double adjustmentTime = 0;//调节时间
        double numberOfOscillation = 0;//振荡次数
        double delayTime = 0;//延迟时间
        double riseTime = 0;//上升时间
        double peakTime = 0;//峰值时间
        //这里就是处理计算
        //终值
        finalvalue = FinalValue(preList);
        //超调量
        overShoot=overShoot(preList);

        //调节时间
        for (int i = 0; i < preList.size(); i++) {
            if((preList.get(i).getOuputValue()>0.98*finalvalue)
                    &&(preList.get(i).getOuputValue()<1.02*finalvalue)){
                adjustmentTime = preList.get(i).getTime()-preList.get(0).getTime();
                break;
            }
        }
        //震荡次数 就是到终值时之前有多少步
        
        for(int  i = 0;i<preList.size()-1;i++){
        	if(preList.get(i).getOuputValue()==finalvalue){
        		numberOfOscillation = i;
        		System.out.println(numberOfOscillation+"震荡次数");
        	}
        }
        
        
        

        //峰值时间
        for (int i = 0; i < preList.size(); i++) {
            if(maxValue<preList.get(i).getOuputValue()){
                peakTime = preList.get(i).getTime();
                maxValue=preList.get(i).getOuputValue();
            }
        }
        
        System.out.println("peaktime"+peakTime);
        double firstTime =0;
        for (int i = 0; i < preList.size(); i++) {
            if(preList.get(i).getOuputValue()<finalvalue*0.1){

              firstTime=preList.get(i).getTime();
                
            }else{
                break;
            }
        }
        System.out.println("fisttime"+firstTime);
        double endTime = 0;

        for (int i = 0; i < preList.size(); i++) {
            if(preList.get(i).getOuputValue()<finalvalue*0.9){

            	endTime=preList.get(i).getTime();
            }else{
                break;
            }
        }
        System.out.println("endtime"+endTime);
        riseTime = endTime-firstTime;
        System.out.println("risetime"+riseTime);
        delayTime  = riseTime /2;


        //steadyStateError--overShoot--adjustmentTime--
        // numberOfOscillation--delayTime--riseTime--peakTime
        staList[0] =overShoot;
        staList[1] = adjustmentTime;
        staList[2] = numberOfOscillation;
        staList[3] = delayTime;
        staList[4] = riseTime;
        staList[5] = peakTime;
        
        	qString +="超调量-"+staList[0]+"-"; 
        	qString +="调整时间-"+staList[1]+"-"; 
        	qString +="震荡次数-"+staList[2]+"-"; 
        	qString +="延迟时间-"+staList[3]+"-"; 
        	qString +="上升时间-"+staList[4]+"-"; 
        	qString +="峰值时间-"+staList[5]+"-"; 
        	 
        	
        return qString ;
    }
}
