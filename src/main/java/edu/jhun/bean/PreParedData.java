package edu.jhun.bean;

/*
 * 数据库取出的数据，（步长，值）
 */
public class PreParedData {

	private double ouputValue;
	private int step;
	private double time=0.0;
	
	
	
	public PreParedData()
	{
		
	}
	
	public PreParedData(double ouputValue, int step) {
		super();
		this.ouputValue = ouputValue;
		this.step = step;
	}
	public PreParedData(double ouputValue, int step,double time) {
		super();
		this.ouputValue = ouputValue;
		this.step = step;
		this.time = time;
		
	}
	

	public double getOuputValue() {
		return ouputValue;
	}
	public void setOuputValue(double ouputValue) {
		this.ouputValue = ouputValue;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "值："+ouputValue+"  步长"+step;
	}

	/**
	 * @return the time
	 */
	public double getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(double time) {
		this.time = time;
	}
}
