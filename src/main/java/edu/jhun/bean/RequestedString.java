package edu.jhun.bean;

/**
 * * 前端请求数据类；
 * 请求数据的分割
 */

public class RequestedString {


    private String caseName;//表名
    private String MemberId;


    private String AttributeId;


    private String AttributeId2;
    private String startStep;
    private String endStep;
    private String RunID;

    private String reqStr;
    private String steplength;
    private String  attrIndex;



    @Override
    public String toString() {
        return "方案名:" + caseName + "运行次数：" + RunID + "  MemberId:" + MemberId +
                "  startStep:" + startStep + "  endStep:" + endStep;
    }

    public RequestedString() {
        reqStr = null;
        caseName = null;
        RunID = null;
        AttributeId= null;
        MemberId = null;
        startStep = null;
        endStep = null;
        steplength= null;
        attrIndex=null;
        AttributeId2 =null;


    }

    //reqString:方案名(0)-运行次数(1)-成员(2)-属性(3)-起始步长(4)-终止步长(5)
    //方案名(0)-运行次数(1)-成员(2)-起始步长(3)-终止步长(4)
    //请求数据的分割、储存
    public RequestedString(String reqString) {
        this();
        reverseStr(reqString);

    }

    public RequestedString(String attrId, String steplength){
        this();
        Split(attrId, steplength);
    }


    public synchronized void Split(String attrId, String steplength){
        this.attrIndex = attrId;
        this.steplength =steplength;
        String[] AttrIndexSplite= attrId.split("-");
        this.caseName = AttrIndexSplite[0];
        this.RunID= AttrIndexSplite[1];
        this.MemberId = AttrIndexSplite[2];
        this.AttributeId = AttrIndexSplite[3];
        if(AttrIndexSplite.length==5){
            this.AttributeId2 = AttrIndexSplite[4];
        }
        String[] StepLengthSplite= steplength.split("-");
        this.startStep = StepLengthSplite[0];
        this.endStep = StepLengthSplite[1];

    }
    public synchronized void reverseStr(String reqString) {
        this.reqStr = reqString;
        String[] splitedStrings = reqString.split("-");
        this.caseName = splitedStrings[0];
        if (splitedStrings.length == 5) {
            this.RunID = splitedStrings[1];
            this.AttributeId = splitedStrings[2];
            this.startStep = splitedStrings[3];
            this.endStep = splitedStrings[4];
        } else if (splitedStrings.length == 6) { //属性默认不管，有属性时再处理
            this.RunID = splitedStrings[1];
            this.MemberId = splitedStrings[2];
            this.AttributeId = splitedStrings[3];
            this.startStep = splitedStrings[4];
            this.endStep = splitedStrings[5];
        } else {
            System.out.println("请求字符串出错！");
        }
    }


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


    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }
    public String getAttributeId() {
        return AttributeId;
    }

    public void setAttributeId(String attributeId) {
        AttributeId = attributeId;
    }

    public String getAttributeId2() {
        return AttributeId2;
    }

    public void setAttributeId2(String attributeId2) {
        AttributeId2 = attributeId2;
    }
}
