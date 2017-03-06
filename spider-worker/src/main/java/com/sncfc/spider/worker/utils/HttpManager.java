package com.sncfc.spider.worker.utils;

import net.sf.json.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.script.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class HttpManager {
    static Logger logger = LoggerFactory.getLogger(HttpManager.class);
    Logger spiderLog = LoggerFactory.getLogger("spiderLog");
    public static String requestGet(String url,String js,String type,String proxyHost,int proxyPort,boolean isProxyFlag){
        String result = requestByGetMethod(url,proxyHost,proxyPort,isProxyFlag,3);
        String script = "var missionId = '" + type + "';"+js;
//        String formatResult = result.substring(result.indexOf("(")+1,result.lastIndexOf(")"));
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        String res = null;
        try {
            engine.eval(script);
            Invocable inv = (Invocable) engine;
            res = (String) inv.invokeFunction("dodo", result);
//            System.out.println(res);
        } catch (Exception e) {
            logger.error("出错了："+url,e);
        }
        return res;
    }
    public static String requestRMW(String url,String js,String type,String proxyHost,int proxyPort,boolean isProxyFlag ){
//        String url = "http://58.68.145.122/phb/data/country_person_money.php?order=desc&page=2";
//        String js ="var nos = '';" +
//                "function dodo(data){ " +
//                "    var obj = eval('('+data+')');" +
//                "    var length = obj.length;" +
//                "    for(var i=0;i<obj.length; i++){" +
//                "      var temData = obj[i];" +
//                "      nos += temData.no + '@';" +
//                "    }" +
//                "    return nos;" +
//                "};";

        String url2 = "http://58.68.145.122/phb/data/person_info.php?no=";
        String result = requestByGetMethod(url,proxyHost,proxyPort,isProxyFlag,3);
        StringBuffer tempResult = new StringBuffer();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String date = sf.format(new Date());
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        try {
            engine.eval(js);
            Invocable inv = (Invocable) engine;
            String res = (String) inv.invokeFunction("dodo", result);
            if(!StringUtils.isEmpty(res)){
                String[] arr = res.split("\\@");
                for (String temp : arr) {
                    String resultTemp = requestByGetMethod(url2+temp,proxyHost,proxyPort,isProxyFlag,3);
                    JSONArray jsonArray = JSONArray.fromObject(resultTemp);
                    Map map = jsonArray.getJSONObject(0);
                    tempResult.append(type+ "$@$$"+ date + "$@$$"+map.get("iname") + "$@$$"+map.get("cardNum")+
                            "$@$$"+map.get("caseCode")+"$@$$"+map.get("courtName")
                            +"$@$$"+map.get("publishDate")+"\r\n");
                }
            }
        }catch (Exception e){
            logger.error("人民网",e);
        }
        return tempResult.toString();
    }
    /**
     * 通过GET方式发起http请求
     */
    public static String requestByGetMethod(String url,String proxyHost,int proxyPort,boolean isProxyFlag,int times){
        String res = null;
        //创建默认的httpClient实例
        CloseableHttpClient httpClient = getHttpClient();
        try {
            //用get方法发送http请求
            HttpGet get = new HttpGet(url);
            HttpHost proxy;
            RequestConfig config;
            // 依次是代理地址，代理端口号，协议类型
            //需要代理
            if(isProxyFlag){
                proxy = new HttpHost(proxyHost, proxyPort);
                config = RequestConfig.custom().setProxy(proxy).build();
            }else{
                config = RequestConfig.custom().build();
            }
            get.setConfig(config);
            System.out.println(get.getURI());
            CloseableHttpResponse httpResponse = null;
            //发送get请求
            httpResponse = httpClient.execute(get);
            //response实体
            HttpEntity entity = httpResponse.getEntity();
            if (null != entity){
                res = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            times--;
            logger.error("times:" + times);
            if(times > 0){
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return requestByGetMethod(url,proxyHost,proxyPort,isProxyFlag,times);
            }
            logger.error("访问异常",e);
        }
        finally{
            try{
                closeHttpClient(httpClient);
            } catch (IOException e){
                logger.error("访问异常",e);
            }
        }
        return res;
    }


    /**
     * POST方式发起http请求
     */
    public static String requestByPostMethod(String url,String js,String type,String proxyHost,int proxyPort,boolean isProxyFlag){
        System.out.println(url);
        String result = "{}";
        String appendScript = "var missionId = " + type + ";";
        CloseableHttpClient httpClient = getHttpClient();
        try {
            String[] paramsArray = url.split("\\?");
            HttpPost post = new HttpPost(paramsArray[0]);
            HttpHost proxy;
            RequestConfig config;
            // 依次是代理地址，代理端口号，协议类型
            if(isProxyFlag){
                //需要代理
                proxy = new HttpHost(proxyHost, proxyPort);
                config = RequestConfig.custom().setProxy(proxy).build();
            }else{
                config = RequestConfig.custom().build();
            }
            post.setConfig(config);
            //创建参数列表
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if(paramsArray.length > 1){
                String[] paArray = paramsArray[1].split("&");
                for (String temp : paArray) {
                    String[] paramsMap = temp.split("=");
                    list.add(new BasicNameValuePair(paramsMap[0],paramsMap[1]));
                }
            }
            post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            //url格式编码
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
            post.setEntity(uefEntity);
            //执行请求
            CloseableHttpResponse httpResponse = httpClient.execute(post);
            try{
                HttpEntity entity = httpResponse.getEntity();
                if (null != entity){
                    result = EntityUtils.toString(entity);
//                    System.out.println(result);
                }
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
                engine.eval(appendScript + js);
                Invocable inv = (Invocable) engine;
                String res = (String) inv.invokeFunction("dodo", result);
                System.out.println(res);
                return res;
            } catch (Exception e) {
                logger.error("访问异常",e);
            } finally{
                httpResponse.close();
            }

        } catch( UnsupportedEncodingException e){
            logger.error("访问异常",e);
        }
        catch (IOException e) {
            logger.error("访问异常",e);
        }
        finally{
            try{
                closeHttpClient(httpClient);
            } catch(Exception e){
                logger.error("访问异常",e);
            }
        }
        return null;
    }

    private static CloseableHttpClient getHttpClient(){
        return HttpClients.createDefault();
    }

    private static void closeHttpClient(CloseableHttpClient client) throws IOException{
        if (client != null){
            client.close();
        }
    }

    public static void main(String[] args) throws Exception {

        String url = "http://172.30.27.5:8080/rxcci/rule/execute?rulePkgId=123&params=11";
            CloseableHttpClient httpClient = getHttpClient();
            try {
                String[] paramsArray = url.split("\\?");
                HttpPost post = new HttpPost(paramsArray[0]);
                HttpHost proxy;
                RequestConfig config;
                // 依次是代理地址，代理端口号，协议类型
                config = RequestConfig.custom().build();
                post.setConfig(config);
                //创建参数列表
                List<NameValuePair> list = new ArrayList<NameValuePair>();
                if(paramsArray.length > 1){
                    String[] paArray = paramsArray[1].split("&");
                    for (String temp : paArray) {
                        String[] paramsMap = temp.split("=");
                        list.add(new BasicNameValuePair(paramsMap[0],paramsMap[1]));
                    }
                }
//                post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
                //url格式编码
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
                post.setEntity(uefEntity);
                //执行请求
                CloseableHttpResponse httpResponse = httpClient.execute(post);
                try{
                    HttpEntity entity = httpResponse.getEntity();
                    if (null != entity){
                        String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    }
                } catch (Exception e) {
                    logger.error("访问异常",e);
                } finally{
                    httpResponse.close();
                }

            } catch( UnsupportedEncodingException e){
                logger.error("访问异常",e);
            }
            catch (IOException e) {
                logger.error("访问异常",e);
            }
            finally{
                try{
                    closeHttpClient(httpClient);
                } catch(Exception e){
                    logger.error("访问异常",e);
                }
            }
//        String js ="var nos = '';" +
//        "function dodo(data){" +
//        "    var obj = eval('('+data+')');" +
//                "var result = obj.data[0].result;" +
//        "    var length = result.length;" +
//        "    for(var i=0;i<length; i++){" +
//        "      var temData = result[i];" +
//        "      nos += temData.iname + '@';" +
//        "    }" +
//        "    return nos;" +
//        "};";

//        String js ="var missionId = 1000345;\n" +
//                "var od = new Date();\n" +
//                "var y = od.getFullYear();\n" +
//                "var mm = od.getMonth() + 1;\n" +
//                "var d = od.getDate();\n" +
//                "var h = od.getHours();\n" +
//                "var m = od.getMinutes();\n" +
//                "var s = od.getSeconds();\n" +
//                "var dd = y + '-' + mm + '-' + d + ' ' + h + ':' + m + ':' + s;\n" +
//                "function dodo(data){ \n" +
//                "  var obj = eval('('+data+')') ;\n" +
//                "  var result = obj.data[0].result;\n" +
//                "  var msg='';\n" +
//                "  var length = result.length;\n" +
//                "  for(var i=0;i<length; i++){\n" +
//                "    var temData = result[i];\n" +
//                "    msg += missionId+'$@$$'+dd+'$@$$'+temData.iname +'$@$$'+temData.cardNum +'$@$$'+temData.caseCode+'$@$$'+temData.courtName +'$@$$'+temData.publishDate+'$@$$'+temData.gistUnit+'$@$$'+temData.regDate+'$@$$'+temData.duty;\n" +
//                "    if(i+1 < length){\n" +
//                "      msg += '\\r\\n';\n" +
//                "    }\n" +
//                "  }\n" +
//                "  return msg;\n" +
//                "};";

//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
//        engine.eval(js);
//        Invocable inv = (Invocable) engine;
//        String res = (String) inv.invokeFunction("dodo", formatResult);
//        System.out.println(res);













//        String result = "{}";
//        CloseableHttpClient httpClient = getHttpClient();
//        try {
//            String[] paramsArray = url.split("\\?");
//            HttpPost post = new HttpPost(paramsArray[0]);
//            HttpHost proxy;
//            RequestConfig config;
//            // 依次是代理地址，代理端口号，协议类型
//                //需要代理
//             proxy = new HttpHost("172.30.8.111", 8080);
//                config = RequestConfig.custom().setProxy(proxy).build();
//            post.setConfig(config);
//            //创建参数列表
//            List<NameValuePair> list = new ArrayList<NameValuePair>();
//            if(paramsArray.length > 1){
//                String[] paArray = paramsArray[1].split("&");
//                for (String temp : paArray) {
//                    String[] paramsMap = temp.split("=");
//                    if(paramsMap.length == 1){
//                        continue;
//                    }
//                    list.add(new BasicNameValuePair(paramsMap[0],paramsMap[1]));
//                }
//            }
//            post.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
//            //url格式编码
//            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(list,"UTF-8");
//            post.setEntity(uefEntity);
//            //执行请求
//            CloseableHttpResponse httpResponse = httpClient.execute(post);
//            try{
//                HttpEntity entity = httpResponse.getEntity();
//                if (null != entity){
//                    result = EntityUtils.toString(entity);
//                    System.out.println(result);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally{
//                httpResponse.close();
//            }
//
//        } catch( UnsupportedEncodingException e){
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
//        finally{
//            try{
//                closeHttpClient(httpClient);
//            } catch(Exception e){
//                e.printStackTrace();
//            }
//        }

//
//        String json = "{\"informationmodels\":[{\"ReallyName\":\"黄德胜\",\"CredentialsNumber\":\"330523600211131\",\"Address\":\"\",\"AH\":\"（2005）湖安执字第1017号\",\"ZXFY\":\"安吉县人民法院\",\"LARQ\":\"2005-08-26\",\"ZXYJ\":\"法院生效裁判\",\"ZXAY\":\"\",\"ZXJE\":\"290150.00\",\"WZXJE\":\"290150.00\",\"BGRQ\":\"2016-09-12\",\"LARQDateTime\":\"/Date(1124985600000)/\",\"BGRQDateTime\":\"/Date(1473609600000)/\"},{\"ReallyName\":\"陈庭荣\",\"CredentialsNumber\":\"330523********0012\",\"Address\":\"\",\"AH\":\"（2005）湖安执字第1104号\",\"ZXFY\":\"安吉县人民法院\",\"LARQ\":\"2005-09-29\",\"ZXYJ\":\"法院生效裁判\",\"ZXAY\":\"\",\"ZXJE\":\"46130.00\",\"WZXJE\":\"46130.00\",\"BGRQ\":\"2016-09-12\",\"LARQDateTime\":\"/Date(1127923200000)/\",\"BGRQDateTime\":\"/Date(1473609600000)/\"},{\"ReallyName\":\"吴小连\",\"CredentialsNumber\":\"330523********0318\",\"Address\":\"\",\"AH\":\"（2006）湖安行执字第1号\",\"ZXFY\":\"安吉县人民法院\",\"LARQ\":\"2005-11-23\",\"ZXYJ\":\"法院生效裁判\",\"ZXAY\":\"土地\",\"ZXJE\":\"0.00\",\"WZXJE\":\"0.00\",\"BGRQ\":\"2016-09-12\",\"LARQDateTime\":\"/Date(1132675200000)/\",\"BGRQDateTime\":\"/Date(1473609600000)/\"},{\"ReallyName\":\"方军\",\"CredentialsNumber\":\"330523701129057\",\"Address\":\"\",\"AH\":\"（2006）湖安行执字第2号\",\"ZXFY\":\"安吉县人民法院\",\"LARQ\":\"2005-11-23\",\"ZXYJ\":\"法院生效裁判\",\"ZXAY\":\"土地\",\"ZXJE\":\"0.00\",\"WZXJE\":\"0.00\",\"BGRQ\":\"2016-09-12\",\"LARQDateTime\":\"/Date(1132675200000)/\",\"BGRQDateTime\":\"/Date(1473609600000)/\"},{\"ReallyName\":\"俞飞云\",\"CredentialsNumber\":\"330523********0011\",\"Address\":\"\",\"AH\":\"（2006）湖安执字第137号\",\"ZXFY\":\"安吉县人民法院\",\"LARQ\":\"2005-12-05\",\"ZXYJ\":\"法院生效裁判\",\"ZXAY\":\"\",\"ZXJE\":\"58018.00\",\"WZXJE\":\"58018.00\",\"BGRQ\":\"2016-09-12\",\"LARQDateTime\":\"/Date(1133712000000)/\",\"BGRQDateTime\":\"/Date(1473609600000)/\"}],\"total\":1519617}";
//        String js ="var missionId= '333';" +
//                " var od = new Date();\n" +
//                "                var y = od.getFullYear();\n" +
//                "                var mm = od.getMonth() + 1;\n" +
//                "                var d = od.getDate();\n" +
//                "                var h = od.getHours();\n" +
//                "                var m = od.getMinutes();\n" +
//                "                var s = od.getSeconds();\n" +
//                "                var dd = y + '-' + mm + '-' + d + ' ' + h + ':' + m + ':' + s;\n" +
//                "                function dodo(data){ \n" +
//                "                  var obj = eval('('+data+')') ;\n" +
//                "                  var msg='';\n" +
//                "                  var length = obj.informationmodels.length;\n" +
//                "                  for(var i=0;i<obj.informationmodels.length; i++){\n" +
//                "                    var temData = obj.informationmodels[i];\n" +
//                "                    msg += missionId+'$@$$'+dd+'$@$$'+temData.ReallyName +'$@$$'+temData.CredentialsNumber +'$@$$'+temData.AH+'$@$$'+temData.BGRQ +'$@$$'+temData.LARQ;\n" +
//                "                    if(i+1 < length){\n" +
//                "                      msg += '\\r\\n';\n" +
//                "                    }\n" +
//                "                  }\n" +
//                "                  return msg;\n" +
//                "              };";
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
//
//        try {
//            engine.eval(js);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
//        Invocable inv = (Invocable) engine;
//        String res = null;
//        try {
//            res = (String) inv.invokeFunction("dodo", json);
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        System.out.println(res);
    }
}