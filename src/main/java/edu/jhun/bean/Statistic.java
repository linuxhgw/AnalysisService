package edu.jhun.bean;

/**
 * 统计数据类：名称，值ֵ
 * @author Administrator
 *
 */
public class Statistic {

	private String name;
	private double value;
	
	public Statistic() {}
	
	public Statistic(String name, double value) {
		super();
		this.name = name;
		this.value = value;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.getName()+"-"+this.getValue()+"-";
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}

}
