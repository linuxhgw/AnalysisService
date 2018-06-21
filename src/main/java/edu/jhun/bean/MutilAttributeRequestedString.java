package edu.jhun.bean;


public class MutilAttributeRequestedString extends RequestedString {

	private  String anotherAtrributes;

	public MutilAttributeRequestedString(String reqStr) {
		super(reqStr);
		String[] splitedStrings=reqStr.split("-");
		this.anotherAtrributes = splitedStrings[2];
		
	}

	public String getAnotherAtrributes() {
		return anotherAtrributes;
	}

	public void setAnotherAtrributes(String anotherAtrributes) {
		this.anotherAtrributes = anotherAtrributes;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "方案名:"+getCaseName()+"运行次数："+ getRunID()+"  MemberId:"+getMemberId()+"  another:"+getAnotherAtrributes()+"  startStep:"+getStartStep()+"  endStep:"+getEndStep();
	}
	
	
}
