package com.sncfc.spider.fileparse.dao.impl;

import com.sncfc.spider.fileparse.dao.IFileProcessDao;
import com.sncfc.spider.fileparse.pojo.*;
import com.sncfc.spider.fileparse.utils.BaseJdbcDAO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository(value = "fileProcessDao")
public class FileProcessDaoImpl extends BaseJdbcDAO implements IFileProcessDao {

    @Value("#{setting[DBNAME]}")
    private String DB_ALIAS;

    private final static Logger logger = Logger.getLogger(FileProcessDaoImpl.class);



    @Override
    public void insertRiskList(Map<String, List> map) {
        final String statisDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        final List<CourtExecute> courtExecuteList = map.get("courtExecute");
        final List<InnerBank> innerBankList = map.get("innerBank");
        final List<MultiLoan> multiLoanList = map.get("multiLoan");
        final List<Enterprise> enterpriseList = map.get("enterprise");

        //法院
        String insertCourtSql = "insert into "+DB_ALIAS+"TSOR_RISK_COURT_EXECUTE_LIST" +
                "(STATIS_DATE,GRAP_DATE,KEY_TYPE,KEY_VALUE,KEY_HIDE,NAME,REASON,CASE_NO,ISSUE_DATE,ADD_TYPE,CREATE_ID,SOURCE,REMARK) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        //同业发布信息
        String insertBankSql = "insert into "+DB_ALIAS+"TSOR_RISK_INTER_BANK_LIST" +
                "(STATIS_DATE,GRAP_DATE,KEY_TYPE,KEY_VALUE,KEY_HIDE,NAME,REASON,ISSUE_DATE,ADD_TYPE,CREATE_ID,SOURCE,REMARK) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?)";
        //多头借贷 58 ganji
        String insertMultiSql = "insert into "+DB_ALIAS+"TSOR_RISK_MULTI_LOAN_LIST" +
                "(STATIS_DATE,GRAP_DATE,KEY_TYPE,KEY_VALUE,KEY_HIDE,NAME,REASON,ISSUE_DATE,ADD_TYPE,CREATE_ID,SOURCE,REMARK) " +
                "values(?,?,?,?,?,?,?,?,?,?,?,?)";
        //天眼网
        String insertEnterpriseSql = "insert into s_enterprise (STATIS_DATE, GRAP_DATE, NAME, TELEPHONE, MAIL, URL, ADDRESS, SCORE, REGIST_CAPITAL, REGIST_TIME, " +
                "REGIST_STATUS, LEGAL_PERSON_NAMES, INDUSTRY, INDUSTRY_COMMERCE_ID, TYPE, ORGABUZATUIB_CODE, BUSINESS_HOURS, REGIST_AUTHORITY, " +
                "APPROVED_DATE, UNIFORM_CREDIT_CODE, REGIST_ADDR, BUSINESS_SCOPE,EXISTED) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        if(courtExecuteList != null && !courtExecuteList.isEmpty()){
            this.getJdbcTemplate().batchUpdate(insertCourtSql,new BatchPreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    CourtExecute courtExecute =  courtExecuteList.get(i);
                    preparedStatement.setString(1,statisDate);
                    preparedStatement.setString(2,courtExecute.getStaticDate());
                    preparedStatement.setString(3,courtExecute.getKeyType());
                    preparedStatement.setString(4,courtExecute.getKeyValue());
                    preparedStatement.setString(5,courtExecute.getKeyHide());
                    preparedStatement.setString(6,courtExecute.getName());
                    preparedStatement.setString(7,courtExecute.getReason());
                    preparedStatement.setString(8,courtExecute.getCaseNo());
                    preparedStatement.setString(9,courtExecute.getIssueDate());
                    preparedStatement.setString(10,courtExecute.getAddType());
                    preparedStatement.setString(11,courtExecute.getCreateId());
                    preparedStatement.setString(12,courtExecute.getSource());
                    preparedStatement.setString(13,courtExecute.getRemark());
                }
                @Override
                public int getBatchSize() {
                    return courtExecuteList.size();
                }
            });
        }
        if(innerBankList != null && !innerBankList.isEmpty()){
            this.getJdbcTemplate().batchUpdate(insertBankSql,new BatchPreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    InnerBank innerBank =  innerBankList.get(i);
                    preparedStatement.setString(1,statisDate);
                    preparedStatement.setString(2,innerBank.getStaticDate());
                    preparedStatement.setString(3,innerBank.getKeyType());
                    preparedStatement.setString(4,innerBank.getKeyValue());
                    preparedStatement.setString(5,innerBank.getKeyHide());
                    preparedStatement.setString(6,innerBank.getName());
                    preparedStatement.setString(7,innerBank.getReason());
                    preparedStatement.setString(8,innerBank.getIssueDate());
                    preparedStatement.setString(9,innerBank.getAddType());
                    preparedStatement.setString(10,innerBank.getCreateId());
                    preparedStatement.setString(11,innerBank.getSource());
                    preparedStatement.setString(12,innerBank.getRemark());
                }
                @Override
                public int getBatchSize() {
                    return innerBankList.size();
                }
            });
        }
        if(multiLoanList != null && !multiLoanList.isEmpty()){
            this.getJdbcTemplate().batchUpdate(insertMultiSql,new BatchPreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    MultiLoan multiLoan =  multiLoanList.get(i);
                    preparedStatement.setString(1,statisDate);
                    preparedStatement.setString(2,multiLoan.getStaticDate());
                    preparedStatement.setString(3,multiLoan.getKeyType());
                    preparedStatement.setString(4,multiLoan.getKeyValue());
                    preparedStatement.setString(5,multiLoan.getKeyHide());
                    preparedStatement.setString(6,multiLoan.getName());
                    preparedStatement.setString(7,multiLoan.getReason());
                    preparedStatement.setString(8,multiLoan.getIssueDate());
                    preparedStatement.setString(9,multiLoan.getAddType());
                    preparedStatement.setString(10,multiLoan.getCreateId());
                    preparedStatement.setString(11,multiLoan.getSource());
                    preparedStatement.setString(12,multiLoan.getRemark());
                }
                @Override
                public int getBatchSize() {
                    return multiLoanList.size();
                }
            });
        }

        if(enterpriseList != null && !enterpriseList.isEmpty()){
            this.getJdbcTemplate().batchUpdate(insertEnterpriseSql,new BatchPreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                    Enterprise enterprise =  enterpriseList.get(i);
                    preparedStatement.setString(1,statisDate);
                    preparedStatement.setString(2,enterprise.getGrapDate());
                    preparedStatement.setString(3,enterprise.getName());
                    preparedStatement.setString(4,enterprise.getTelephone());
                    preparedStatement.setString(5,enterprise.getMail());
                    preparedStatement.setString(6,enterprise.getUrl());
                    preparedStatement.setString(7,enterprise.getAddress());
                    preparedStatement.setString(8,enterprise.getScore());
                    preparedStatement.setString(9,enterprise.getRegistCapital());
                    preparedStatement.setString(10,enterprise.getRegistTime());
                    preparedStatement.setString(11,enterprise.getRegistStatus());
                    preparedStatement.setString(12,enterprise.getLegalPersonNames());
                    preparedStatement.setString(13,enterprise.getIndustry());
                    preparedStatement.setString(14,enterprise.getIndustryCommerceId());
                    preparedStatement.setString(15,enterprise.getType());
                    preparedStatement.setString(16,enterprise.getOrganizationCode());
                    preparedStatement.setString(17,enterprise.getBusinessHours());
                    preparedStatement.setString(18,enterprise.getRegistAuthority());
                    preparedStatement.setString(19,enterprise.getApprovedDate());
                    preparedStatement.setString(20,enterprise.getUniformCreditCode());
                    preparedStatement.setString(21,enterprise.getRegistAddr());
                    preparedStatement.setString(22,enterprise.getBusinessScope());
                    preparedStatement.setString(23,enterprise.getExisted());

                }
                @Override
                public int getBatchSize() {
                    return enterpriseList.size();
                }
            });
        }
    }

//    @Override
//    public void insertInnerBank(final List<InnerBank> list) {
//        String insertBlackSql = "insert into "+DB_ALIAS+"TSOR_RISK_INTER_BANK_LIST" +
//                "(STATIS_DATE,KEY_TYPE,KEY_VALUE,KEY_HIDE,NAME,REASON,LOST_CONF_AMT,ISSUE_DATE,ADD_TYPE,CREATE_ID,SOURCE,REMARK) " +
//                "values(?,?,?,?,?,?,?,?,?,?,?,?)";
//        if(list != null && !list.isEmpty()){
//            this.getJdbcTemplate().batchUpdate(insertBlackSql,new BatchPreparedStatementSetter(){
//                @Override
//                public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
//                    InnerBank bank =  list.get(i);
//                    preparedStatement.setString(1,bank.getStaticDate());
//                    preparedStatement.setString(2,bank.getKeyType());
//                    preparedStatement.setString(3,bank.getKeyValue());
//                    preparedStatement.setString(4,bank.getKeyHide());
//                    preparedStatement.setString(5,bank.getName());
//                    preparedStatement.setString(6,bank.getReason());
//                    preparedStatement.setDouble(7,bank.getLostConfAmt());
//                    preparedStatement.setString(8,bank.getIssueDate());
//                    preparedStatement.setString(9,bank.getAddType());
//                    preparedStatement.setString(10,bank.getCreateId());
//                    preparedStatement.setString(11,bank.getSource());
//                    preparedStatement.setString(12,bank.getRemark());
//                }
//                @Override
//                public int getBatchSize() {
//                    return list.size();
//                }
//            });
//        }
//    }
public static void main(String[] args) {
    String grapDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
    System.out.println(grapDate);
}
}
