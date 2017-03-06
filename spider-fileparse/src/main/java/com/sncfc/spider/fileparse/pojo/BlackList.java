package com.sncfc.spider.fileparse.pojo;

/**
 * 黑名单
 */
public class BlackList {
    /**
     * 数据日期
     */
    private String statisDate;
    /**
     * 黑名单类型 01
     */
    private String listType = "01";
    /**
     * 灰名单键值类型  01:身份证  02:易付宝账号  03:易购账号  04:QQ   05:邮箱
     */
    private String keyType;
    /**
     * 黑名单键值
     */
    private String keyValue;
    /**
     * 键值是否包含隐藏字 1：包含 0：不包含
     */
    private String keyHide;
    /**
     * 姓名 或 法人
     */
    private String bName;
    /**
     * 黑名单来源
     *01：同盾反欺诈 02：PBOC 03：银联 04：法院被执行人  05：同业逾期
     */
    private String bSource;
    /**
     *进入黑名单原因
     * "法院被执行人 同业逾期"
     */
    private String bReason;
    /**
     * 法院案号
     */
    private String bCaseno;
//    /**
//     * 黑名单等级 不处理
//     */
//    private String b_grade;
    /**
     * 黑名单状态
     */
    private String bStatus = "00";
    /**
     * 黑名单生效时间 法院发布日期等
     */
    private String bIntime;
    /**
     * 创建类型 03网络爬取
     */
    private String addType = "03";
    /**
     * 创建人工号
     */
    private String createId;
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

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getbSource() {
        return bSource;
    }

    public void setbSource(String bSource) {
        this.bSource = bSource;
    }

    public String getbReason() {
        return bReason;
    }

    public void setbReason(String bReason) {
        this.bReason = bReason;
    }

    public String getbCaseno() {
        return bCaseno;
    }

    public void setbCaseno(String bCaseno) {
        this.bCaseno = bCaseno;
    }

    public String getbStatus() {
        return bStatus;
    }

    public void setbStatus(String bStatus) {
        this.bStatus = bStatus;
    }

    public String getbIntime() {
        return bIntime;
    }

    public void setbIntime(String bIntime) {
        this.bIntime = bIntime;
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
        return "BlackList{" +
                "statisDate='" + statisDate + '\'' +
                ", listType='" + listType + '\'' +
                ", keyType='" + keyType + '\'' +
                ", keyValue='" + keyValue + '\'' +
                ", keyHide='" + keyHide + '\'' +
                ", bName='" + bName + '\'' +
                ", bSource='" + bSource + '\'' +
                ", bReason='" + bReason + '\'' +
                ", bCaseno='" + bCaseno + '\'' +
                ", bStatus='" + bStatus + '\'' +
                ", bIntime='" + bIntime + '\'' +
                ", addType='" + addType + '\'' +
                ", createId='" + createId + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
