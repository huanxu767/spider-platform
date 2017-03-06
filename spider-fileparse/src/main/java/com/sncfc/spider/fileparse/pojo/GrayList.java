package com.sncfc.spider.fileparse.pojo;

/**
 * 白名单
 */
public class GrayList {
    /**
     * 数据日期
     */
    private String statisDate;
    /**
     * 灰名单类型 01
     */
    private String listType = "01";
    /**
     * 灰名单键值类型  01：手机号码 02：地址
     */
    private String keyType;
    /**
     * 灰名单键值
     */
    private String keyValue;
    /**
     * 键值是否包含隐藏字 1：包含 0：不包含
     */
    private String keyHide;
    /**
     * 姓名 或 法人
     */
    private String gName;
    /**
     * 灰名单来源
     *01:同业 02：同业逾期 03：法院被执行 04 赶集58
     */
    private String gSource;
    /**
     *进入灰名单原因
     * 欠款原因/法院被执行
     */
    private String gReason;
    /**
     * 法院案号
     */
    private String gCaseno;
//    /**
//     * 灰名单等级 不处理
//     */
//    private String g_grade;
    /**
     * 灰名单状态
     */
    private String gStatus = "00";
    /**
     * 灰名单生效时间 法院发布日期等
     */
    private String gIntime;
    /**
     * 创建类型 03网络爬取
     */
    private String addType = "03";
    /**
     * 创建人工号
     */
    private String createId = "10160400";
//    /**
//     * 更新时间 不处理
//     */
//    private String update_time;
//    /**
//     * 更新人工号 不处理
//     */
//    private String update_id;
    /**
     * 备注
     */
    private String remark;

    public String getStatisDate() {
        return statisDate;
    }

    public void setStatisDate(String statisDate) {
        this.statisDate = statisDate;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }

    public String getKeyHide() {
        return keyHide;
    }

    public void setKeyHide(String keyHide) {
        this.keyHide = keyHide;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getgSource() {
        return gSource;
    }

    public void setgSource(String gSource) {
        this.gSource = gSource;
    }

    public String getgReason() {
        return gReason;
    }

    public void setgReason(String gReason) {
        this.gReason = gReason;
    }

    public String getgCaseno() {
        return gCaseno;
    }

    public void setgCaseno(String gCaseno) {
        this.gCaseno = gCaseno;
    }

    public String getgStatus() {
        return gStatus;
    }

    public void setgStatus(String gStatus) {
        this.gStatus = gStatus;
    }

    public String getgIntime() {
        return gIntime;
    }

    public void setgIntime(String gIntime) {
        this.gIntime = gIntime;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "GrayList{" +
                "statisDate='" + statisDate + '\'' +
                ", listType='" + listType + '\'' +
                ", keyType='" + keyType + '\'' +
                ", keyValue='" + keyValue + '\'' +
                ", keyHide='" + keyHide + '\'' +
                ", gName='" + gName + '\'' +
                ", gSource='" + gSource + '\'' +
                ", gReason='" + gReason + '\'' +
                ", gCaseno='" + gCaseno + '\'' +
                ", gStatus='" + gStatus + '\'' +
                ", gIntime='" + gIntime + '\'' +
                ", addType='" + addType + '\'' +
                ", createId='" + createId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
