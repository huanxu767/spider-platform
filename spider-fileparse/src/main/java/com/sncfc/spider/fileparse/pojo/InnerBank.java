package com.sncfc.spider.fileparse.pojo;

/**
 * Created by xuhuan on 2016/10/10.
 * 同业发布信息
 */
public class InnerBank {
    /**
     * 数据入库日期
     */
    private String staticDate;
    /**
     * 键值类型 01:身份证 02:易付宝账号 03:易购账号04:QQ05:邮箱06:手机号
     */
    private String keyType;
    /**
     * 键值
     */
    private String keyValue;
    /**
     * 是否包含隐藏 1：包含 0：不包含
     */
    private String keyHide;
    /**
     * 姓名
     */
    private String name;
    /**
     * 原因
     */
    private String reason;
    /**
     * 失信金额
     */
    private Double lostConfAmt;
    /**
     * 信息发布日期
     */
    private String issueDate;
    /**
     * 创建类型00 系统自动创建
     01 手动批量创建
     02 手动单笔创建
     03 网络爬取
     */
    private String addType = "03";
    /**
     * 创建人编号
     */
    private String createId = "16070449";
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 更新人
     */
    private String updateId;
    /**
     * 来源 58同城/赶集网等
     */
    private String source;
    /**
     * 备注
     */
    private String remark;

    public String getStaticDate() {
        return staticDate;
    }

    public void setStaticDate(String staticDate) {
        this.staticDate = staticDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getLostConfAmt() {
        return lostConfAmt;
    }

    public void setLostConfAmt(Double lostConfAmt) {
        this.lostConfAmt = lostConfAmt;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
