package edu.jhun.bean;
/**
 *  * 前端请求数据类；
 * 请求数据的分割
 * 
 */

public class RequestedString {

	private String reqStr;
	private String MemberId;
	private String startStep;
	private String endStep;
	private String RunID;

	//	private String Outputvalue;
	private String caseName;//表名




	public String getRunID() {
		return RunID;
	}

	public void setRunID(String timer) {
		this.RunID = timer;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	

	public String getStartStep() {
		return startStep;
	}

	public void setStartStep(String startStep) {
		this.startStep = startStep;
	}

	public String getEndStep() {
		return endStep;
	}

	public void setEndStep(String endStep) {
		this.endStep = endStep;
	}

	
	
	public String getReqStr() {
		return reqStr;
	}

	public void setReqStr(String reqStr) {
		this.reqStr = reqStr;
	}

	public String getMemberId() {
		return MemberId;
	}

	public void setMemberId(String memberId) {
		MemberId = memberId;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "方案名:"+caseName+"运行次数："+RunID+"  MemberId:"+MemberId+"  startStep:"+startStep+"  endStep:"+endStep;
	}
	
	public RequestedString()
	{
		this.reqStr=null;
		caseName=null;
		RunID=null;
		MemberId=null;
		startStep=null;
		endStep=null;
		
	}
	//reqString:方案名(0)-成员(1)-属性(2)-运行次数(3)-起始步长(4)-终止步长(5)
	//方案名(0)-成员(1)-运行次数(2)-起始步长(3)-终止步长(4)
	//请求数据的分割、储存
	public RequestedString(String reqString)
	{
		this();
		reverseStr(reqString);
		
	}
	
	public synchronized void reverseStr(String reqString)
	{
		this.reqStr=reqString;
		String[] splitedStrings=reqString.split("-");
		this.caseName=splitedStrings[0];
		if(splitedStrings.length==5)
		{
			this.RunID=splitedStrings[1];
			this.MemberId=splitedStrings[2];

			this.startStep=splitedStrings[3];
			this.endStep=splitedStrings[4];
		}else if(splitedStrings.length==6){ //属性默认不管，有属性时再处理
			this.RunID=splitedStrings[1];
			this.MemberId=splitedStrings[3];
			this.startStep=splitedStrings[4];
			this.endStep=splitedStrings[5];
		}
		else
		{			
			System.out.println("请求字符串出错！");
		}
	}
}
