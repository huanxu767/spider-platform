package com.sncfc.spider.fileparse.utils;

import com.sncfc.spider.fileparse.pojo.*;
import com.sun.deploy.util.ArrayUtil;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuhuan on 2016/9/12.
 */
public class AnalyticalRecordUtils {

    public static Map analyRecord(List<String> list){

        List<CourtExecute> CourtExecute = new ArrayList();
        List<InnerBank> innerBankList = new ArrayList();
        List<MultiLoan> multiLoanList = new ArrayList();
        List<Enterprise> enterpriseList = new ArrayList();
        Map<String,List> map = new HashMap();
        //逐条处理
        for (String lineWord : list) {
            try {
                if(StringUtils.isEmpty(lineWord)){
                    continue;
                }
                String[] wordArray = lineWord.split(ConstantsMission.SPLIT_STRING);
                String missionId = wordArray[0];
                //过滤空数据
                if(wordArray.length <= 2){
                    continue;
                }else if(ConstantsMission.MISSION_BAIDU.equals(missionId)){
                    anaBaidu(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_PPDAI.equals(missionId)){
                    anaPPD(wordArray,multiLoanList);
                }else if(ConstantsMission.MISSION_58.equals(missionId)){
                    ana58(wordArray,innerBankList);
                }else if(ConstantsMission.MISSION_GANJI.equals(missionId)){
                    anaGanji(wordArray,innerBankList);
                }else if(ConstantsMission.MISSION_HEBEI.equals(missionId)){
                    anaHbei(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_ANHUI.equals(missionId)){
                    anaAnhui(wordArray,CourtExecute);
                }else if("100013".equals(missionId)){
                    //石家庄市中级人民法院 暂不处理
                }else if(ConstantsMission.MISSION_TANGSHAN.equals(missionId)){
                    anaTangshan(wordArray,CourtExecute);
                }else if(ConstantsMission.getZh().contains(missionId)){
                    anaZH(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_CHONGQIN.equals(missionId)){
                    anaCQFY(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_CHONGQINDIWU.equals(missionId)){
                    anaCQDIWU(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_NANYUN.equals(missionId)){
                    anaNanyun(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_ZHEJIANG.equals(missionId)){
                    anaZhejiang(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_CHENGDE.equals(missionId)){
                    anaChende(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_RENMINW.equals(missionId)){
                    anaRMW(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_SHANDONG.equals(missionId)){
                    anaShandong(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_NANJING.equals(missionId)){
                    anaNaning(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_TAIZHOU.equals(missionId)){
                    anaTaizhou(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_KUNMING.equals(missionId)){
                    anaKunming(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_CHAOZHOU.equals(missionId)){
                    anaChaozhou(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_CHAOZHOU.equals(missionId)){
                    anaChaozhou(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_JIXI.equals(missionId)){
                    anaJixi(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_NANTONG.equals(missionId)){
                    anaNantong(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_YUXI.equals(missionId)){
                    anaYuxi(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_GUANGZHOU.equals(missionId)){
                    anaGuangzhou(wordArray,CourtExecute);
                }
                else if(ConstantsMission.MISSION_SUQIAN.equals(missionId)){
                    anaSuqian(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_SHANGHAIDIYI.equals(missionId)){
                    anaShdiyi(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_SHANTOU.equals(missionId)){
                    anaShantou(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_FOSHAN.equals(missionId)){
                    anaFoshan(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_HUIZHOU.equals(missionId)){
                    anaHuizhou(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_ZHONGSHAN.equals(missionId)){
                    anaZhongshan(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_LONGYAN.equals(missionId)){
                    anaLongyan(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_TTAIZHOU.equals(missionId)){
                    anaTTaizhou(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_YANGJIANG.equals(missionId)){
                    anaYangjiang(wordArray,CourtExecute);
                }else if(ConstantsMission.MISSION_SANMING.equals(missionId)){
                    anaSanming(wordArray,CourtExecute);
                }
                else if(ConstantsMission.MISSION_TIANYAN.contains(missionId)){
                    anaTianyan(wordArray,enterpriseList);
                }
                else{
//                    System.out.println("else:"+lineWord);
                }

            }catch (Exception e){
                System.out.println("lineWord:"+lineWord);
                e.printStackTrace();
            }
        }
        map.put("courtExecute",CourtExecute);
        map.put("innerBank",innerBankList);
        map.put("multiLoan",multiLoanList);
        map.put("enterprise",enterpriseList);
        return map;
    }

    private static void anaTianyan(String[] wordArray, List<Enterprise> enterpriseList) {
        if(StringUtils.isEmpty(wordArray[2])){
            return;
        }
        if(wordArray[2].startsWith("{{")){
            return;
        }
        try {
            if(wordArray[17].trim().length() > 64 ){
                return;
            }
        }catch (Exception e){

        }
        Enterprise enterprise = new Enterprise();
        enterprise.setGrapDate(getShortDate(wordArray[1]));
        if("无记录".equals(wordArray[2])){
            enterprise.setExisted("0");
            enterprise.setOrganizationCode(wordArray[3]);
        }else {
            enterprise.setExisted("1");
            enterprise.setName(wordArray[2].trim());
            enterprise.setTelephone(wordArray[3].trim());
            enterprise.setMail(wordArray[4].trim());
            enterprise.setUrl(wordArray[5].trim());
            enterprise.setAddress(wordArray[6].trim());
            enterprise.setScore(wordArray[7].trim().replace("评分",""));
            enterprise.setRegistCapital(wordArray[8].trim());
            enterprise.setRegistTime(wordArray[9].trim());
            enterprise.setRegistStatus(wordArray[10].trim());
            enterprise.setLegalPersonNames(wordArray[11].trim());
            enterprise.setIndustry(wordArray[13].trim());
            enterprise.setIndustryCommerceId(wordArray[14].trim());
            enterprise.setType(wordArray[15].trim());
            try {
                enterprise.setOrganizationCode(wordArray[16].trim());
                enterprise.setBusinessHours(wordArray[17].trim());
                enterprise.setRegistAuthority(wordArray[18].trim());
                enterprise.setApprovedDate(wordArray[19].trim());
                enterprise.setUniformCreditCode(wordArray[20].trim());
                enterprise.setRegistAddr(wordArray[21].trim());
                if(wordArray[22].length() > 500){
                    enterprise.setBusinessScope(wordArray[22].substring(0,500));
                }else{
                    enterprise.setBusinessScope(wordArray[22].trim());
                }
            }catch (Exception e){

            }
        }
        enterpriseList.add(enterprise);
    }

    /**
     * 解析百度
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaBaidu(String[] wordArray,List CourtExecute){
//        1000345$@$$2016-10-12 10:57:46$@$$王双全$@$$14223019690****0515$@$$(2016)晋0928执59号$@$$五寨县人民法院$@$$2016年10月09日$@$$山西省五寨县人民法院$@$$20160321
        if(StringUtils.isEmpty(wordArray[3])){
            //被执行 无身份证 无号码 无地址
            return;
        }
        String cardNo = wordArray[3].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength == 19){
            cardNo = cardNo.substring(0,14) +cardNo.substring(15,19);
        }
        if(cardNoLength == 16){
//            37280155****4513
            cardNo = cardNo.substring(0,11) +cardNo.substring(12,16);
        }
        if((cardNoLength != 16 && cardNoLength != 19 )){
            //过滤身份证乱的
            return;
        }
        String name = wordArray[2];
        if(name.contains("（")){
            name = name.substring(0,name.indexOf("（"));
        }
        if( name.length()> 20){
            System.out.println(wordArray.toString());
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo);
        black.setKeyHide("1");
        black.setName(name);
        black.setReason("失信人");
        black.setCaseNo(wordArray[4]);
        black.setSource(wordArray[5]);
//        2016年10月09日
//        if(wordArray[6].length() > 10){
//            System.out.println(wordArray.toString());
//        }
        black.setIssueDate(wordArray[6].replace("年","").replace("月","").replace("日",""));
        black.setRemark("百度");
        CourtExecute.add(black);
    }
    /**
     * 解析拍拍贷
     * @param wordArray
     * @param multiLoanList
     */
    private static void anaPPD(String[] wordArray,List multiLoanList){
        //拍拍贷  进入灰名单 和黑名单
//       00005$@$$2016-9-14 17:50:25$@$$范美炟$@$$18230902***$@$$52242419950228****
        if(ConstantsMission.UNDEFINED.equals(wordArray[3].trim())){
            return;
        }
        MultiLoan multiLoan = new MultiLoan();
        multiLoan.setStaticDate(getShortDate(wordArray[1]));
        multiLoan.setSource("拍拍贷");
        multiLoan.setRemark("拍拍贷");
        multiLoan.setKeyType("06");
        multiLoan.setKeyValue(wordArray[3].trim());
        multiLoan.setKeyHide("1");
        multiLoan.setName(wordArray[2].trim());
        multiLoan.setReason("欠款逾期");
        multiLoanList.add(multiLoan);

        MultiLoan cBlack = new MultiLoan();
        cBlack.setStaticDate(getShortDate(wordArray[1]));
        cBlack.setSource("拍拍贷");
        cBlack.setRemark("拍拍贷");
        cBlack.setKeyType("01");
        cBlack.setKeyValue(wordArray[4].trim());
        cBlack.setKeyHide("1");
        cBlack.setName(wordArray[2].trim());
        cBlack.setReason("欠款逾期");
        multiLoanList.add(cBlack);
    }

    /**
     * 解析58
     * @param wordArray
     * @param innerBankList
     */
    private static void ana58(String[] wordArray,List innerBankList){
        //58 同城 只有手机号码 进入灰名单
        if(wordArray.length < 7){
           return;
        }
        int mNoLength = wordArray[4].trim().length();
        if(mNoLength > 13 ){
            //超长号码
            return;
        }
        InnerBank gray = new InnerBank();
        gray.setStaticDate(getShortDate(wordArray[1]));
        gray.setSource("58同城");
        gray.setKeyType("06");
        gray.setKeyValue(wordArray[4]);
        gray.setKeyHide("0");
        if(wordArray[5].contains("span")){
            gray.setName("");
        }else{
            gray.setName(wordArray[5]);
        }
        gray.setReason("贷款信息发布");
        gray.setRemark("58同城");
        innerBankList.add(gray);
    }
    /**
     * 解析赶集
     * @param wordArray
     * @param innerBankList
     */
    private static void anaGanji(String[] wordArray,List innerBankList){
        //赶集 只有手机号码 进入灰名单
        if(wordArray.length < 6){
            return;
        }
        int mNoLength = wordArray[4].trim().length();
        if(mNoLength > 13 ){
            //超长号码
            return;
        }
        InnerBank gray = new InnerBank();
        gray.setStaticDate(wordArray[0]);
        gray.setSource("赶集");
        gray.setKeyType("06");
        gray.setKeyValue(wordArray[4]);
        gray.setKeyHide("0");
        gray.setReason("贷款信息发布");
        gray.setRemark("赶集");
        innerBankList.add(gray);
    }
    /**
     * 解析河北
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaHbei(String[] wordArray,List CourtExecute){
        //河北省高级人民法院
        if(wordArray.length < 6){
            return;
        }
        if(StringUtils.isEmpty(wordArray[4])){
            //被执行 无身份证 无号码 无地址
            return;
        }
        String cardNo = wordArray[4].trim();
        int cardNoLength = cardNo.length();
        if((cardNoLength != 15 && cardNoLength != 18 )){
            //过滤身份证乱的
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[4].trim().toUpperCase());
        black.setKeyHide("1");
        //如果有逗号 逗号前
        //如果有( （前
        //如果有空格 空格前
        String name = wordArray[2].trim();
        if(name.contains("（")){
            name = name.substring(0,name.indexOf("（"));
        }else if(name.contains(" ")){
            name = name.substring(0,name.indexOf(" "));
        }else if(name.contains("，")){
            name = name.substring(0,name.indexOf("，"));
        }
        if(name.length() > 5 ){
            //过滤公司
            return;
        }
        black.setName(name);
        black.setReason(wordArray[5]);
        black.setCaseNo(wordArray[3]);
        black.setSource("河北省高级人民法院");
        black.setRemark("河北省高级人民法院");
        CourtExecute.add(black);
    }
    /**
     * 解析安徽
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaAnhui(String[] wordArray,List CourtExecute){
        //安徽省高级人民法院
//                    100012$@$$2016-9-22 17:13:29$@$$任莉萍$@$$341222199303266340$@$$未结执行实施案件$@$$(2016)皖1222执120号$@$$太和县人民法院
        if(StringUtils.isEmpty(wordArray[3])){
            //被执行 无身份证 无号码 无地址
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3]);
        black.setKeyHide("0");
        black.setName(wordArray[2]);
        black.setReason(wordArray[4]);
        black.setCaseNo(wordArray[5]);
        black.setSource(wordArray[6]);
        black.setRemark("安徽省高级人民法院");
        CourtExecute.add(black);
    }

    /**
     * 解析唐山
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaTangshan(String[] wordArray,List CourtExecute){
//        100014$@$$2016-9-22 17:33:51$@$$（2014）古执字第12号$@$$王健晔$@$$13020419XXXX251813$@$$0.42$@$$古冶区人民法院
        int cardNoLength = wordArray[4].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        String key = wordArray[4].trim();
        String temp = key.substring(0,key.length()-1);
        if(temp.contains("X")){
            temp = temp.replaceAll("X","*");
        }
        black.setKeyValue(temp + key.substring(key.length()-1,key.length()));
        black.setKeyHide("1");
        if(wordArray[3].contains("（")){
            black.setName(wordArray[3].replaceAll(" ","").substring(0,wordArray[3].indexOf("（")));
        }else{
            black.setName(wordArray[3].replaceAll(" ",""));
        }
        black.setReason("失信人");
        black.setCaseNo(wordArray[2].trim());
        if(wordArray.length == 7){
            black.setSource(wordArray[6].replaceAll(" ",""));
        }else{
            black.setSource("唐山市中级人民法院");
        }
        black.setRemark("唐山市中级人民法院");
        CourtExecute.add(black);
    }

    /**
     * 解析长春市 吉林 四平 辽源 贵州
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaZH(String[] wordArray,List CourtExecute){
        //长春市 吉林 四平 辽源 贵州
        if(StringUtils.isEmpty(wordArray[4]) || !(wordArray[4].trim().length() == 15 || wordArray[4].trim().length() == 18)){
            //被执行 无身份证 无号码 无地址
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[4].toUpperCase());
        black.setKeyHide("1");
        //如果有逗号 逗号前
        //如果有( （前
        //如果有空格 空格前
        String name = wordArray[2].trim();

        if(name.contains("（")){
            name = name.substring(0,name.indexOf("（"));
        }else if(name.contains(" ")){
            name = name.substring(0,name.indexOf(" "));
        }else if(name.contains("，")){
            name = name.substring(0,name.indexOf("，"));
        }
        if(name.length() > 5 ){
            //过滤公司
            return;
        }
        black.setName(name);
        black.setReason(wordArray[5]);
        black.setCaseNo(wordArray[3]);
        if(ConstantsMission.MISSION_CHANGCHUN.equals(wordArray[0])){
            black.setRemark("长春市中级人民法院");
            black.setSource("长春市中级人民法院");
        }else if(ConstantsMission.MISSION_JILIN.equals(wordArray[0])){
            black.setRemark("吉林市中级人民法院");
            black.setSource("吉林市中级人民法院");
        }else if(ConstantsMission.MISSION_SIPIN.equals(wordArray[0])){
            black.setRemark("四平市中级人民法院");
            black.setSource("四平市中级人民法院");
        }else if(ConstantsMission.MISSION_LIAOYUAN.equals(wordArray[0])){
            black.setRemark("辽源市中级人民法院");
            black.setSource("辽源市中级人民法院");
        }else if(ConstantsMission.MISSION_TONGHUA.equals(wordArray[0])){
            black.setRemark("通化市中级人民法院");
            black.setSource("通化市中级人民法院");
        }else if(ConstantsMission.MISSION_BAISHAN.equals(wordArray[0])){
            black.setRemark("白山市中级人民法院");
            black.setSource("白山市中级人民法院");
        }else if(ConstantsMission.MISSION_SONGYUAN.equals(wordArray[0])){
            black.setRemark("松原市中级人民法院");
            black.setSource("松原市中级人民法院");
        }else if(ConstantsMission.MISSION_BBAICHENG.equals(wordArray[0])){
            black.setRemark("白城市中级人民法院");
            black.setSource("白城市中级人民法院");
        }else if(ConstantsMission.MISSION_YANBIAN.equals(wordArray[0])){
            black.setRemark("延边朝鲜族自治州中级人民法院");
            black.setSource("延边朝鲜族自治州中级人民法院");
        }else if(ConstantsMission.MISSION_GUIZHOU.equals(wordArray[0])){
            black.setRemark("贵州法院诉讼服务平台");
            black.setSource("贵州法院诉讼服务平台");
        }else if(ConstantsMission.MISSION_JIAYUGUAN.equals(wordArray[0])){
            black.setRemark("嘉峪关市中级人民法院");
            black.setSource("嘉峪关市中级人民法院");
        }else if(ConstantsMission.MISSION_JINCHANGSHI.equals(wordArray[0])){
            black.setRemark("金昌市中级人民法院");
            black.setSource("金昌市中级人民法院");
        }else if(ConstantsMission.MISSION_NINGXIA.equals(wordArray[0])){
            black.setRemark("宁夏回族自治州中级人民法院");
            black.setSource("宁夏回族自治州中级人民法院");
        }
        CourtExecute.add(black);
    }
    /**
     * 重庆法院公众服务网
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaCQFY(String[] wordArray,List CourtExecute){
        //重庆法院公众服务网
//                    100024$@$$2016-9-12 15:19:10$@$$（2011）璧法民执字第518号$@$$璧山区人民法院$@$$2013-12-03$@$$何欣$@$$5102321970****0026
        if(wordArray.length < 7){
            return;
        }
        int cardNoLength = wordArray[6].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[6].trim().toUpperCase());
        black.setKeyHide("1");
        black.setName(removeKh(wordArray[5].trim()));
        black.setReason("失信人");
        black.setCaseNo(wordArray[2].trim());
        if(!StringUtils.isEmpty(wordArray[4])){
            black.setIssueDate(wordArray[4].replace("-",""));
        }
        black.setSource(wordArray[3].trim());
        black.setRemark("重庆法院公众服务网");
        CourtExecute.add(black);
    }

    /**
     * 重庆市第五中级人民法院
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaCQDIWU(String[] wordArray,List CourtExecute){
        //重庆市第五中级人民法院
        if(wordArray.length < 7){
            return;
        }
        int cardNoLength = wordArray[6].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[6].trim().toUpperCase());
        black.setKeyHide("1");
        black.setName(wordArray[4].trim());
        black.setSource("重庆市第五中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[2].trim());
        black.setRemark("重庆市第五中级人民法院");
        CourtExecute.add(black);
    }
    /**
     * 南允
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaNanyun(String[] wordArray,List CourtExecute){
        if(wordArray.length < 8){
            return;
        }
        String cardNo = wordArray[7].trim().replaceAll("\\?","");
        int cardNoLength = cardNo.length();
        if((cardNoLength != 15 && cardNoLength != 18 ) || wordArray[7].contains("代码")){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[4].trim().replaceAll("\\?","");
        if(name.contains("（")){
            black.setName(name.substring(0,name.indexOf("（")));
        }else{
            black.setName(name);
        }
        black.setSource("南允市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[5].trim().replaceAll("\\?","").split("、")[0]);
        black.setRemark("南允市中级人民法院");
        CourtExecute.add(black);
    }

    /**
     * 浙江
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaZhejiang(String[] wordArray,List CourtExecute){
        if(wordArray.length < 7){
            return;
        }
        String cardNo = wordArray[3].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("1");
        String name = wordArray[2].trim();
        name = name.replace("（程序终结）","");
        //去除括号中的内容
//                    System.out.println(name + "   " + cardNo);
        if(name.contains("(") && name.contains(")")){
            String khContent = name.substring(name.indexOf("("),name.indexOf(")")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains("（") && name.contains("）")){
            String  khContent = name.substring(name.indexOf("（"),name.indexOf("）")+1);
            name = name.replace(khContent,"");
        }
        if(name.trim().length() > 20 ){
            System.out.println(name + "   " + cardNo);
            return;
        }
        if(name.contains("(") && name.contains("）")){
            String khContent = name.substring(name.indexOf("("),name.indexOf("）")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains("（") && name.contains(")")){
            String khContent = name.substring(name.indexOf("（"),name.indexOf(")")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains("【") && name.contains("】")){
            String khContent = name.substring(name.indexOf("【"),name.indexOf("】")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains("、") ){
            name = name.split("、")[0];
        }
        if(name.contains("，") ){
            name = name.split("，")[0];
        }
        if(name.contains(",") ){
            name = name.split(",")[0];
        }
        if(name.contains(" ") ){
            name = name.split(" ")[0];
        }
        black.setName(name.trim());
        black.setSource("浙江省高级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setIssueDate(wordArray[6].replaceAll("-",""));
        black.setRemark("浙江省高级人民法院");
        CourtExecute.add(black);
    }

    /**
     * 承德
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaChende(String[] wordArray,List CourtExecute){
        // 100031$@$$2016-9-19 15:4:28$@$$郝殿合$@$$（2013）承民初字第391号$@$$承德县法院$@$$130821196311272110$@$$承德县下板城镇大杖子村$@$$2014/4/23
        if(wordArray.length < 7){
            return;
        }
        String cardNo = wordArray[5].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        //去除括号中的内容
        black.setName(name.replaceAll(" ",""));
        black.setReason("失信人");
        black.setSource(wordArray[4].trim());
        black.setCaseNo(wordArray[3].trim());
        black.setRemark("承德市中级人民法院");
        CourtExecute.add(black);
    }

    /**
     * 人民网
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaRMW(String[] wordArray,List CourtExecute){
//                    1000333$@$$20160922$@$$汪海涛$@$$342622197508028411$@$$(2014)庐江执字第00538号$@$$安徽省合肥市庐江县人民法院$@$$2014-05-13
        if(wordArray.length < 7){
            return;
        }
        String cardNo = wordArray[3].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(wordArray[1]);
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        if(name.contains("(") && name.contains(")")){
            String khContent = name.substring(name.indexOf("("),name.indexOf(")")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains("（") && name.contains("）")){
            String  khContent = name.substring(name.indexOf("（"),name.indexOf("）")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains(" ")){
            name = name.split(" ")[0];
        }
        if(name.trim().length() > 20 ){
            return;
        }
        black.setName(name.trim());
        black.setSource(wordArray[5].trim());
        black.setReason("失信人");
        black.setIssueDate(wordArray[6].replaceAll("-",""));
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("人民网");
        CourtExecute.add(black);
    }
    private static void anaShandong(String[] wordArray,List CourtExecute){
        if(wordArray.length < 4){
            return;
        }
        String cardNo = wordArray[3].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        if(name.length() > 20){
            return;
        }
        //去除括号中的内容
        black.setName(name.replaceAll(" ",""));
        black.setSource("山东省高级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("山东省高级人民法院");
        CourtExecute.add(black);
    }

    /**
     * 解析南京
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaNaning(String[] wordArray,List CourtExecute){
        //                    1000321$@$$2016-9-23 15:33:18$@$$张云$@$$(2015)宁执字第00435号$@$$320114198210070045
        if(wordArray.length < 4){
            return;
        }
        String cardNo = wordArray[4].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        if(name.length() > 20){
            return;
        }
        //去除括号中的内容
        black.setName(name.replaceAll(" ",""));
        black.setSource("南京市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[3].trim());
        black.setRemark("南京市中级人民法院");
        CourtExecute.add(black);
    }
    /**
     * 解析台州
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaTaizhou(String[] wordArray,List CourtExecute){
//                    1000323$@$$2016-9-23 15:43:27$@$$陈亚娣$@$$332601195505020024$@$$（2016）浙10执274号$@$$2016-09-12
        if(wordArray.length < 5){
            return;
        }
        String cardNo = wordArray[3].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        if(name.length() > 20){
            return;
        }
        //去除括号中的内容
        black.setName(name.replaceAll(" ",""));
        black.setSource("台州市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("台州市中级人民法院");
        CourtExecute.add(black);
    }
    /**
     * 解析昆明
     * @param wordArray
     * @param CourtExecute
     */
    private static void anaKunming(String[] wordArray,List CourtExecute){
//                   1000324$@$$2016-9-23 15:56:57$@$$刘祖有$@$$（2016）云0128执626号$@$$5301281982****2459$@$$未结执行实施案件
        if(wordArray.length < 5){
            return;
        }
        String cardNo = wordArray[4].trim();
        int cardNoLength = cardNo.length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(cardNo.toUpperCase());
        black.setKeyHide("1");
        String name = wordArray[2].trim();
        if(name.length() > 20){
            return;
        }
        //去除括号中的内容
        black.setName(name.replaceAll(" ",""));
        black.setSource("昆明市中级人民法院");
        black.setReason(wordArray[5].trim());
        black.setCaseNo(wordArray[3].trim());
        black.setRemark("昆明市中级人民法院");
        CourtExecute.add(black);
    }

    private static void anaChaozhou(String[] wordArray,List CourtExecute){
//        1000328$@$$2016-9-20 14:39:29$@$$王树铁$@$$（2016）粤51执38号$@$$潮州中院$@$$440520196306225934
        int cardNoLength = wordArray[5].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[5].trim().toUpperCase());
        black.setKeyHide("0");
        black.setName(wordArray[2].trim());
        black.setSource(wordArray[4].trim());
        black.setReason("失信人");
        black.setCaseNo(wordArray[3].trim());
        black.setRemark("潮州市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaJixi(String[] wordArray,List CourtExecute){
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        black.setName(wordArray[2].trim());
        black.setSource("鸡西市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("鸡西市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaNantong(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000322$@$$2016-9-24 14:43:11$@$$钱? 斌$@$$320623196801146819$@$$（2013）通中商初字第0261号民事调解书
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        black.setName(wordArray[2].replaceAll("\\?","").replaceAll(" ",""));
        black.setSource("04");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("南通市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaYuxi(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000331$@$$2016-9-20 14:37:46$@$$廖朝炳$@$$51022319690209461x$@$$（2014）峨法执字第159号
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        black.setName(wordArray[2]);
        black.setSource("玉溪市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("玉溪市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaGuangzhou(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000334$@$$2016-9-26 9:45:10$@$$南沙法院$@$$[2014]穗南法执字第1050号$@$$阙三星$@$$352624196901273413
        if(wordArray.length < 6){
            return;
        }
        int cardNoLength = wordArray[5].trim().length();
        if(wordArray[5].contains("组织") || wordArray[5].contains("×") ){
            return;
        }
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[5].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[4].replaceAll("\\?","");
        if(name.length()>20){
//            System.out.println(wordArray[5] + " " +name);
            return;
        }
        black.setName(name);
        black.setSource("广州市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[3].trim());
        black.setRemark("广州市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaSuqian(String[] wordArray, List<CourtExecute> CourtExecute) {
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[4].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[4].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[3];
        if(name.length()>20){
            System.out.println(wordArray[5] + " " +name);
            return;
        }
        black.setName(name);
        black.setSource("宿迁市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[2].trim());
        black.setRemark("宿迁市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaShdiyi(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000330$@$$2016-9-20 14:23:22$@$$王宏$@$$310102197207184015$@$$（2016）沪01执699号$@$$20160704
        if(wordArray.length < 6){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2];
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("上海市第一中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("上海市第一中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaShantou(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000336$@$$2016-9-26 14:45:42$@$$陈顺喜$@$$440500196406240415$@$$(2013)汕濠法执字第30号
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2];
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("汕头市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("汕头市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaFoshan(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000337$@$$2016-9-26 16:53:19$@$$杨志辉$@$$441622197810072598$@$$（2016）粤06执462号
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2];
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("佛山市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("佛山市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaHuizhou(String[] wordArray, List<CourtExecute> CourtExecute) {
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("1");
        String name = wordArray[2];
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("惠州市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("惠州市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaZhongshan(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000339$@$$2016-9-27 10:18:15$@$$项斌$@$$36232219860413××××$@$$中山市中级人民法院$@$$(2014)中中法执字第00157号
        if(wordArray.length < 6){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].replaceAll("×","*").trim().toUpperCase());
        black.setKeyHide("1");
        String name = wordArray[2];
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("中山市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[5].trim());
        black.setRemark("中山市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaLongyan(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000340$@$$2016-9-27 14:10:54$@$$黄伟$@$$350823198605183714$@$$(2015)龙新执字第900号
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2];
        if(name.length()>20){
            return;
        }
        black.setName(name.replaceAll("\\?",""));
        black.setSource("龙岩市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("龙岩市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaTTaizhou(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000341$@$$2016-9-27 15:4:11$@$$陆强$@$$违反财产报告制度且拒不履行生效法律文书确定的义务$@$$3101011976****4419
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[4].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[4].trim().toUpperCase());
        black.setKeyHide("1");
        String name = wordArray[2].trim();
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("泰州市中级人民法院");
        black.setReason("失信人");
        black.setCaseNo(wordArray[4].trim());
        black.setRemark("泰州市中级人民法院");
        CourtExecute.add(black);
    }
    private static void anaYangjiang(String[] wordArray, List<CourtExecute> CourtExecute) {
//        1000342$@$$2016-9-27 15:31:22$@$$黄心怡$@$$44170219890930172X$@$$(2015)阳中法执字第00073号
        if(wordArray.length < 5){
            return;
        }
        int cardNoLength = wordArray[3].trim().length();
        if(cardNoLength != 15 && cardNoLength != 18){
            //过滤公司
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("阳江市中级人民法院");
        black.setCaseNo(wordArray[4].trim().toUpperCase());
        black.setReason("失信人");
        black.setRemark("阳江市中级人民法院");
        CourtExecute.add(black);
    }
//
    private static void anaSanming(String[] wordArray, List<CourtExecute> CourtExecute) {
//        123$@$$2016-9-27 15:46:0$@$$王凤庆$@$$350423196910010018$@$$(2016)闽0423执649号
        if(wordArray.length < 5){
            return;
        }
        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(wordArray[3].trim());
        //判断用户输入是否为身份证号
        if(!idNumMatcher.matches()){
            return;
        }
        CourtExecute black = new CourtExecute();
        black.setStaticDate(getShortDate(wordArray[1]));
        black.setKeyType("01");
        black.setKeyValue(wordArray[3].trim().toUpperCase());
        black.setKeyHide("0");
        String name = wordArray[2].trim();
        if(name.length()>20){
            return;
        }
        black.setName(name);
        black.setSource("三明市中级人民法院");
        black.setCaseNo(wordArray[4].trim().toUpperCase());
        black.setReason("失信人");
        black.setRemark("三明市中级人民法院");
        CourtExecute.add(black);
    }

    private static String getShortDate(String date){
        String[] dateArray = date.split(" ");
        String[] mainDate = dateArray[0].split("-");
        String month = mainDate[1];
        if( month.length() == 1){
            month = "0"+month;
        }
        String day = mainDate[2];
        if( day.length() == 1){
            day = "0"+day;
        }
        return mainDate[0] + month + day;
    }

    /**
     * 是否包含数字
     * @param str
     * @return
     */
    static boolean isExistsNum(String str) {
        return str.matches("[^\\d]*");
    }

    private static String removeKh(String name){
        if(name.contains("(") && name.contains(")")){
            String khContent = name.substring(name.indexOf("("),name.indexOf(")")+1);
            name = name.replace(khContent,"");
        }
        if(name.contains("（") && name.contains("）")){
            String  khContent = name.substring(name.indexOf("（"),name.indexOf("）")+1);
            name = name.replace(khContent,"");
        }
        return name;
    }
    public static void main(String[] args) {
        String date = AnalyticalRecordUtils.getShortDate("1991-1-1");
        System.out.println(date);
//        Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9xX])");
//        //通过Pattern获得Matcher
//        Matcher idNumMatcher = idNumPattern.matcher("32012119900110071a");
//        //判断用户输入是否为身份证号
//        if(!idNumMatcher.matches()){
//            System.out.println("no");
//        }else{
//            System.out.println("yes");
//        }

    }
}
