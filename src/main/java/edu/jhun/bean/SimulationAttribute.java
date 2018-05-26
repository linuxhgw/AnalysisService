/**
 * 
 */
package edu.jhun.bean;

/**
 * @author Administrator
 * @time 2018年1月19日下午12:57:39
 *	仿真属性bean类
 */
public class SimulationAttribute {
	
	private String schemeId;
	private String schemeName;
	private String memberId;
	private String attrId;
	private String attrName;
	private int step;
	private double outputvalue;
	
	public SimulationAttribute() {
		super();
	}
	
	public SimulationAttribute(String schemeId,String schemeName, String memberId, String attrId, String attrName, int step,
			double outputvalue) {
		super();
		this.schemeId = schemeId;
		this.schemeName = schemeName;
		this.memberId = memberId;
		this.attrId = attrId;
		this.attrName = attrName;
		this.step = step;
		this.outputvalue = outputvalue;
	}
	
	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	public double getOutputvalue() {
		return outputvalue;
	}
	public void setOutputvalue(double outputvalue) {
		this.outputvalue = outputvalue;
	}

	@Override
	public String toString() {
		return "SimulationAttribute [schemeId=" + schemeId + ", schemeName=" + schemeName + ", memberId=" + memberId
				+ ", attrId=" + attrId + ", attrName=" + attrName + ", step=" + step + ", outputvalue=" + outputvalue
				+ "]";
	}
}
