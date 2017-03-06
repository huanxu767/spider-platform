package com.sncfc.spider.worker.utils;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.javascript.JavaScriptEngine;
import com.gargoylesoftware.htmlunit.util.NameValuePair;
import com.sncfc.spider.worker.pojo.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;

/**
 * 爬虫工具
 */
public class SpiderUtils {

	static Logger logger = LoggerFactory.getLogger(SpiderUtils.class);
	static String LOCAL_IP = "";
	static List<BrowserVersion> browserVersionList = new ArrayList<BrowserVersion>();
	static {
//		browserVersionList.add(BrowserVersion.EDGE);
		browserVersionList.add(BrowserVersion.FIREFOX_45);
//		browserVersionList.add(BrowserVersion.CHROME);
	}
	/**
	 * 解析，带扩展脚本
	 * @param url
	 * @param js
	 * @param missionId
	 * @return
	 */
	public static String crawler(String url, String js, String missionId,String proxyUrl,int proxyPort,boolean isProxyFlag) {
		String extendHeaderJs = Constants.getHeaderJs(getMyIPLocal(), missionId);
		js = extendHeaderJs + js;
		return crawlerOrginal(url, js,proxyUrl,proxyPort,isProxyFlag,Constants.RETRY_TIMES);
	}
	/**
	 * 解析，无扩展脚本
	 * @param url
	 * @param js
	 * @return
	 */
	public static String crawlerOrginal(String url, String js,String proxyUrl,int proxyPort,boolean isProxyFlag,int times) {
		/** HtmlUnit请求web页面 */
		final WebClient wc;
		if(isProxyFlag){
			//需要代理
			wc = new WebClient(randomBrowserVersion(),proxyUrl, proxyPort);
		}else{
			wc = new WebClient();
		}
        wc.setJavaScriptTimeout(30000);
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		wc.getOptions().setCssEnabled(false); // 禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
		wc.getOptions().setTimeout(30000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.waitForBackgroundJavaScript(60*1000);
        wc.setAjaxController(new NicelyResynchronizingAjaxController());
		System.out.println(url);
		HtmlPage page;
		try {
			page = wc.getPage(url);
//            System.out.println(page.asXml());
//            wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
//			page.executeJavaScript(Constants.getJQUERY(getMyIPLocal()));
			ScriptResult scriptResult = page.executeJavaScript(js);
			Object rs = scriptResult.getJavaScriptResult();
            wc.close();
			//编码 转换
            if(rs == null){
                return null;
            }
			String result = rs.toString();
            System.out.println("result:{"+result+"}");
			return getEncoding(result);
		} catch (Exception e) {
		    e.printStackTrace();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				logger.error("sleep",e1);
			}
			times--;
            logger.error("times:"+times);
            if(times > 0){
                return crawlerOrginal(url, js,proxyUrl,proxyPort,isProxyFlag,times);
			}else{
				logger.error("Exception:"+url,e);
			}
		}
		return null;

	}
	/**
	 *随机切换Agent
	 *
	 * @return
	 */
	private static BrowserVersion randomBrowserVersion() {
		int size = browserVersionList.size();
		Random random = new Random();
		return browserVersionList.get(random.nextInt(size));
	}
	/**
	 * 获取本地IP地址
	 * @return
	 */
	public static String getMyIPLocal() {
		if(LOCAL_IP == ""){
			InetAddress ia;
			try {
				ia = InetAddress.getLocalHost();
				LOCAL_IP = ia.getHostAddress();
			} catch (UnknownHostException e) {
				logger.error("获取本地IP地址",e);
			}
		}
		return LOCAL_IP;
	}

    public static String getEncoding(String str) {
        String encode;
        String transTemp;
        try {
            encode = "UTF-8";
            transTemp = new String(str.getBytes(encode), encode);
            if (str.equals(transTemp)) {
                return transTemp;
            }
            encode = "GB2312";
            transTemp = new String(str.getBytes(encode), encode);
            if (str.equals(transTemp)) {
                return transTemp;
            }
            encode = "ISO-8859-1";
            transTemp = new String(str.getBytes(encode), encode);
            if (str.equals(transTemp)) {
                return transTemp;
            }
            encode = "GBK";
            transTemp = new String(str.getBytes(encode), encode);
            if (str.equals(transTemp)) {
                return transTemp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void main(String[] args) throws UnsupportedEncodingException {
	    Long d = System.currentTimeMillis();
        testDD();
        Long ed = System.currentTimeMillis();
        System.out.println((ed - d)/1000);
    }

    public static void testDD(){
        String js = "\n" + "var tempUrl = new Array();\n" + "function parseHtml(){\n" + "\n" + "\t$('a').each(function(){\n" + "\t\tvar href = $(this).attr('href')+'';\n" + "\t\ttempUrl.push(href);\n" + "\t});\n" + "\treturn tempUrl.join(\",\");\n" + "}\n" + "parseHtml();";

        String url = "http://www.tianyancha.com/search?key=70840161-9&checkFrom=searchBox";

//        url = "http://172.30.20.21:8080/spiderworker/js/11.html";
        url = "http://www.tianyancha.com/company/2322028978";
         WebClient wc;
        wc = new WebClient(randomBrowserVersion(),"172.30.8.111", 8080);
        wc.setJavaScriptTimeout(30000);
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(false); // 禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(30000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        wc.waitForBackgroundJavaScript(60*1000);
        wc.setAjaxController(new NicelyResynchronizingAjaxController());
        System.out.println(url);
        HtmlPage page;
        try {
            page = wc.getPage(url);
            System.out.println(page.asXml());
//            wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
//            page.executeJavaScript(Constants.getJQUERY(getMyIPLocal()));
//            ScriptResult scriptResult = page.executeJavaScript(js);
//            Object rs = scriptResult.getJavaScriptResult();
//            System.out.println(rs.toString());
            wc.close();
            }catch (Exception e){
                e.printStackTrace();
            }
    }
	public static void testZhejiang() throws IOException {
        String url = " http://www.zjsfgkw.cn/Execute/CreditPersonal";
        String js ="  var msg = '';\n" +
                "  function dodo(){\n" +
                "   $.ajax({\n" +
                "         type: 'POST',\n" +
                "         url: 'http://www.zjsfgkw.cn/Execute/CreditPersonal',\n" +
                "         data: {PageNo:3,PageSize:5},\n" +
                "         async : false,\n" +
                "         success: function(data){\n" +
                "         msg = data;\n" +
                "          \n" +
                "         }\n" +
                "      });\n" +
                "      return msg;\n" +
                "}\n" +
                " dodo();";

        final WebClient wc;
        wc = new WebClient(randomBrowserVersion(),"172.30.8.111", 8080);
        wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
        wc.getOptions().setCssEnabled(true); // 禁用css支持
        wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
        wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
        HtmlPage page;
        try {
            page = wc.getPage(url);
            System.out.println(page.asXml());
            System.out.println("-----------------------------");
            try {

                    ScriptResult scriptResult = page.executeJavaScript(js);
                    Object rs = scriptResult.getJavaScriptResult();
                    System.out.println("页：  "+rs.toString());
            }catch (Exception e){
                wc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


	}
	public static void  testBaidu(){
//      全国
//      String url = "https://www.baidu.com/s?wd=%E6%89%A7%E8%A1%8C%E4%BA%BA&rsv_spt=1&rsv_iqid=0xccbc40f500000c21&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=5&rsv_sug1=3&rsv_sug7=101&rsv_sug2=0&inputT=2276&rsv_sug4=3949";
        //北京
        String url ="https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=%E5%8C%97%E4%BA%AC%20%E6%89%A7%E8%A1%8C%E4%BA%BA&oq=%E6%89%A7%E8%A1%8C%E4%BA%BA&rsv_pq=b2a8ae080001b278&rsv_t=408b6V2MJfOhwzhtGcwnTu8eaGu6w0VcFyz5GOXPGvfbzEbrPpw8IH9aK7E&rqlang=cn&rsv_enter=1&rsv_sug3=3&rsv_sug1=1&rsv_sug7=100&rsv_n=2&rsv_sug2=0&inputT=1049154&rsv_sug4=1049154&rsv_sug=1";
        //南京
//        String url = "https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=baidu&wd=%E5%8D%97%E4%BA%AC%20%E5%A4%B1%E4%BF%A1%E4%BA%BA%E5%91%98%E5%90%8D%E5%8D%95%E6%9F%A5%E8%AF%A2&oq=%E6%B1%9F%E8%8B%8F%20%E5%A4%B1%E4%BF%A1%E4%BA%BA%E5%91%98%E5%90%8D%E5%8D%95%E6%9F%A5%E8%AF%A2&rsv_pq=c731e859000248ff&rsv_t=0c40q5LxLNSgEowYn0iKv6y990WIyHItXsKnmV7RnuOSjwBUzKHe1c3sSh4&rqlang=cn&rsv_enter=1&inputT=1123&rsv_sug3=22&rsv_sug1=5&rsv_sug7=100&rsv_sug2=0&rsv_sug4=2402";
		String js = "function dodo(){" +
				"var xhhtml = '';" +
				"$('.c-border ').find('li.op_trust_item ').each(function(index){" +
				"var no = $(this).find('span.op_trust_papers').html();" +
				"var name = $(this).find('span.op_trust_name').html();" +
				"xhhtml += name + '@' + no +'\\r\\n'; " +
				"});" +
				"return xhhtml;" +
				"}" +
				"dodo();";
		String nextJS = "$('span.op_trust_page_next').click();";
		pa(url,js,nextJS);
	}
	public static void testGanji(){
		String url = "anshan@anyang@anqing@ankang@akesu@anshun@aletai@alashan@aba@ali@alaer@aomen@bj@baoding@binzhou@baotou@baoji@benxi@bengbu@beihai@bayannaoer@baicheng@baishan@bozhou@bazhong@baiyin@baise@bijie@bayinguoleng@baoshan@boertala@cd@cq@cs@cc@changzhou@cangzhou@chifeng@chengde@changde@changzhi@chenzhou@chuzhou@chaohu@chaozhou@changji@chizhou@chuxiong@chongzuo@changdu@chaoyang@changshu@cixi@dl@dg@dezhou@dongying@daqing@datong@dandong@danzhou@deyang@dazhou@dali@daxinganling@dingxi@dehong@diqing@eerduosi@enshi@ezhou@fz@foshan@fushun@fuyang@fuxin@jxfuzhou@fangchenggang@gz@gy@gl@ganzhou@guangyuan@guangan@guigang@guyuan@gannan@ganzi@guoluo@hz@huizhou@hrb@hf@nmg@hn@handan@heze@hengshui@huaian@hengyang@huludao@huainan@hanzhong@huaihua@huaibei@huanggang@huzhou@huangshi@hulunbeier@heyuan@hebi@hegang@huangshan@honghe@hechi@hami@heihe@hezhou@haixi@hetian@haibei@haidong@huangnan@jn@jining@jilin@jinzhou@jinhua@jiaxing@jiangmen@jingzhou@jiaozuo@jinzhong@jiamusi@jiujiang@jincheng@jingmen@jixi@jian@jieyang@jingdezhen@jiyuan@jiuquan@jinchang@jiayuguan@jiaozhou@jimo@km@kaifeng@kashi@kelamayi@kuerle@kezilesu@kunshan@lz@xz@langfang@linyi@luoyang@liaocheng@liuzhou@lianyungang@linfen@luohe@liaoyang@leshan@luzhou@luan@loudi@laiwu@longyan@lvliang@lishui@liangshan@lijiang@liupanshui@liaoyuan@laibin@lincang@longnan@linxia@linzhi@mianyang@mudanjiang@maoming@meizhou@maanshan@meishan@nj@nb@nn@nc@nantong@nanyang@nanchong@neijiang@nanping@ningde@nujiang@naqu@pingdingshan@puyang@panjin@putian@panzhihua@pingxiang@pingliang@puer@pixian@qd@qh@qinhuangdao@quanzhou@qiqihaer@qingyuan@qujing@quzhou@qingyang@qitaihe@qinzhou@qianjiang@qiandongnan@qiannan@qianxinan@rizhao@rikaze@sh@sz@sy@sjz@su@shantou@shangqiu@sanya@suqian@shaoxing@shiyan@siping@sanmenxia@shaoyang@shangrao@suining@sanming@suihua@shihezi@ahsuzhou@shaoguan@songyuan@suizhou@shanwei@shuangyashan@shuozhou@shizuishan@shangluo@shennongjia@shannan@shuangliu@tj@ty@tangshan@taian@zjtaizhou@jstaizhou@tieling@tongliao@tonghua@tianshui@tongling@tongchuan@tongren@tianmen@tacheng@tulufan@tumushuke@wh@wx@xj@wei@weifang@wenzhou@wuhu@weinan@wuhai@wuzhou@wulanchabu@wuwei@wenshan@wuzhong@wujiaqu@wuzhishan@xa@xm@xn@xuzhou@xianyang@xingtai@xiangyang@xinxiang@xiangtan@xuchang@xinyang@xiaogan@xinzhou@xianning@xinyu@xuancheng@xiantao@xilinguole@xiangxi@xingan@xishuangbanna@xianggang@yc@yichang@yantai@yangzhou@yancheng@yingkou@yueyang@yuncheng@sxyulin@yibin@yangquan@yanan@yiyang@yongzhou@gxyulin@jxyichun@yangjiang@yanbian@yuxi@yili@yunfu@hljyichun@yaan@yingtan@yushu@yiwu@zz@zhuhai@zibo@zhongshan@zaozhuang@zhangjiakou@zhuzhou@zhenjiang@zhoukou@zhanjiang@zhumadian@zhaoqing@zigong@zunyi@zhangzhou@zhoushan@zhangye@ziyang@zhangjiajie@zhaotong@zhongwei";
		String[] urls = url.split("@");
		System.out.println("urls = [" + urls.length + "]");
		List manList = new ArrayList();
		String js = "$('.no-search img').attr('src')";
		int[] number = {10,20,30,40,50,60,70,80,90,100};
		for (int i = 0; i < urls.length; i++) {

			for (int j =0;j < number.length; j++) {
				String tempUrl = "http://"+urls[i]+".ganji.com/daikuan/o"+number[j]+"/";
				boolean flag = pa(tempUrl,js,3);
				if(flag){
					//没有了
					for (int g = number[j]-1; g > number[j]-10; g--) {
						boolean fl = pa("http://"+urls[i]+".ganji.com/daikuan/o"+g+"/",js,3);
						if(!fl){
							//最大页
							Map map = new HashMap();
							map.put("url",urls[i]);
							map.put("no",g);
							manList.add(map);
							System.out.println(g+":"+urls[i]);
							break;
						}
					}
					break;
				}else {
					if(j == number.length - 1){
						//满页
						Map map = new HashMap();
						map.put("url",urls[i]);
						map.put("no",100);
						System.out.println(100+":"+urls[i]);
						manList.add(map);
					}
				}
			}
		}
		StringBuffer manBuffer = new StringBuffer();
		for (int i = 0; i < manList.size(); i++) {
			Map tempMap  = (Map)manList.get(i);
			manBuffer.append("{\"city\":\""+tempMap.get("url")+"\",\"pageno\":\"1-"+tempMap.get("no")+"\"},");
		}
		System.out.println("满 = [" + manBuffer.toString() + "]");
	}

	static void pa(String tempUrl,String js,String nextJS){
		final WebClient wc;
		wc = new WebClient(BrowserVersion.CHROME,"172.30.8.111", 8080);
		wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
		wc.getOptions().setCssEnabled(false); // 禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(true); // js运行错误时，是否抛出异常
		wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		HtmlPage page;
		try {
			page = wc.getPage(tempUrl);
			Page newPage = page.getEnclosingWindow().getEnclosedPage();
			System.out.println(newPage.getUrl());
			String result;
			int i = 0;
			try {
				do {
                    i++;
					try {
						Thread.sleep(550);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ScriptResult scriptResult = page.executeJavaScript(js);
					Object rs = scriptResult.getJavaScriptResult();
                    System.out.println("第"+ i++ +"页：  ");
                    System.out.println(rs.toString());
					page.executeJavaScript(nextJS);
					wc.waitForBackgroundJavaScript(1000);
				}while (i <= 101);
			}catch (Exception e){
				wc.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static String convert(String utfString){
		StringBuilder sb = new StringBuilder();
		int i = -1;
		int pos = 0;

		while((i=utfString.indexOf("\\u", pos)) != -1){
			sb.append(utfString.substring(pos, i));
			if(i+5 < utfString.length()){
				pos = i+6;
				sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));
			}
		}

		return sb.toString();
	}
	static boolean pa(String tempUrl,String js,int times){
		try {
			Thread.sleep(550);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		final WebClient wc;
		wc = new WebClient(randomBrowserVersion(),"172.30.8.111", 8080);
//		wc = new WebClient(randomBrowserVersion());
		wc.getOptions().setJavaScriptEnabled(false); // 启用JS解释器，默认为true
		wc.getOptions().setCssEnabled(false); // 禁用css支持
		wc.getOptions().setThrowExceptionOnScriptError(true); // js运行错误时，是否抛出异常
//			wc.getCookieManager().setCookiesEnabled(true);
//		wc.getCookieManager().clearCookies();
		wc.getOptions().setTimeout(10000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
		HtmlPage page;
		try {
			page = wc.getPage(tempUrl);
			Page newPage = page.getEnclosingWindow().getEnclosedPage();
			System.out.println(newPage.getUrl());
			wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
			page.executeJavaScript(Constants.getJQUERY(getMyIPLocal()));
			ScriptResult scriptResult = page.executeJavaScript(js);
			Object rs = scriptResult.getJavaScriptResult();
			wc.close();
			if(rs != null && "http://stacdn201.ganjistatic1.com/src/image/search/donkey.png".equals(rs.toString())){
				//已经没有了
				return true;
			}else{
				//还有
				return false;
			}
		} catch (Exception e) {
			try {
				Thread.sleep(550);
			} catch (InterruptedException e1) {
				logger.error("sleep",e1);
			}
			times--;
			logger.error("times:"+times);
			System.out.println("times:"+times);
			if(times > 0){
				pa(tempUrl,js,times);
			}else{
				System.out.println("出错tempUrl = [" + tempUrl + "]");
				e.printStackTrace();
			}
			return false;
		}
	}
	static void test58(){
		//		String url = "bj@sh@gz@sz@cd@hz@nj@tj@wh@cq@qd@jn@yt@wf@linyi@zb@jining@ta@lc@weihai@zaozhuang@dz@rizhao@dy@heze@bz@lw@zhangqiu@kl@zc@shouguang@su@nj@wx@cz@xz@nt@yz@yancheng@ha@lyg@taizhou@suqian@zj@shuyang@dafeng@rugao@qidong@liyang@haimen@donghai@yangzhong@xinghuashi@xinyishi@taixing@rudong@pizhou@xzpeixian@jingjiang@jianhu@haian@dongtai@danyang@hz@nb@wz@jh@jx@tz@sx@huzhou@lishui@quzhou@zhoushan@yueqingcity@ruiancity@yiwu@yuyao@zhuji@xiangshanxian@wenling@tongxiang@cixi@changxing@jiashanx@haining@deqing@hf@wuhu@bengbu@fy@hn@anqing@suzhou@la@huaibei@chuzhou@mas@tongling@xuancheng@bozhou@huangshan@chizhou@ch@hexian@hq@tongcheng@ningguo@tianchang@sz@gz@dg@fs@zs@zh@huizhou@jm@st@zhanjiang@zq@mm@jy@mz@qingyuan@yj@sg@heyuan@yf@sw@chaozhou@taishan@yangchun@sd@huidong@boluo@fz@xm@qz@pt@zhangzhou@nd@sm@np@ly@wuyishan@shishi@jinjiangshi@nananshi@nn@liuzhou@gl@yulin@wuzhou@bh@gg@qinzhou@baise@hc@lb@hezhou@fcg@chongzuo@haikou@sanya@wzs@sansha@qh@zz@luoyang@xx@ny@xc@pds@ay@jiaozuo@sq@kaifeng@puyang@zk@xy@zmd@luohe@smx@hb@jiyuan@mg@yanling@yuzhou@changge@wh@yc@xf@jingzhou@shiyan@hshi@xiaogan@hg@es@jingmen@xianning@ez@suizhou@qianjiang@tm@xiantao@snj@yidou@cs@zhuzhou@yiyang@changde@hy@xiangtan@yy@chenzhou@shaoyang@hh@yongzhou@ld@xiangxi@zjj@nc@ganzhou@jj@yichun@ja@sr@px@fuzhou@jdz@xinyu@yingtan@yxx@sy@dl@as@jinzhou@fushun@yk@pj@cy@dandong@liaoyang@benxi@hld@tl@fx@pld@wfd@hrb@dq@qqhr@mdj@suihua@jms@jixi@sys@hegang@heihe@yich@qth@dxal@cc@jl@sp@yanbian@songyuan@bc@th@baishan@liaoyuan@cd@mianyang@deyang@nanchong@yb@zg@ls@luzhou@dazhou@scnj@suining@panzhihua@ms@ga@zy@liangshan@guangyuan@ya@bazhong@ab@ganzi@km@qj@dali@honghe@yx@lj@ws@cx@bn@zt@dh@pe@bs@lincang@diqing@nujiang@gy@zunyi@qdn@qn@lps@bijie@tr@anshun@qxn@lasa@rkz@sn@linzhi@changdu@nq@al@sjz@bd@ts@lf@hd@qhd@cangzhou@xt@hs@zjk@chengde@dingzhou@gt@zhangbei@zx@zd@ty@linfen@dt@yuncheng@jz@changzhi@jincheng@yq@lvliang@xinzhou@shuozhou@linyixian@qingxu@hu@bt@chifeng@erds@tongliao@hlbe@bycem@wlcb@xl@xam@wuhai@alsm@hlr@xa@xianyang@baoji@wn@hanzhong@yl@yanan@ankang@sl@tc@xj@changji@bygl@yili@aks@ks@hami@klmy@betl@tlf@ht@shz@kzls@ale@wjq@tmsk@lz@tianshui@by@qingyang@pl@jq@zhangye@wuwei@dx@jinchang@ln@linxia@jyg@gn@yinchuan@wuzhong@szs@zw@guyuan@xn@hx@haibei@guoluo@haidong@huangnan@ys@hainan@hk@am@tw@cn";
		String url = "xinyishi@pizhou@xzpeixian@dongtai@anqing@suzhou@sansha@al";
		String[] urls = url.split("@");
		System.out.println("urls = [" + urls.length + "]");
		String js = "$('.next').prev().text()";
		List manList = new ArrayList();
		List bManList = new ArrayList();
		int number = 4;
		for (int i = 0; i < urls.length; i++) {
			/** HtmlUnit请求web页面 */
			String tempUrl = "http://"+urls[i]+".58.com/danbaobaoxiantouzi/pn"+number+"/";
			final WebClient wc;
			wc = new WebClient(randomBrowserVersion(),"172.30.8.111", 8080);
			wc.getOptions().setJavaScriptEnabled(false); // 启用JS解释器，默认为true
			wc.getOptions().setCssEnabled(false); // 禁用css支持
			wc.getOptions().setThrowExceptionOnScriptError(false); // js运行错误时，是否抛出异常
			wc.getOptions().setTimeout(30000); // 设置连接超时时间 ，这里是10S。如果为0，则无限期等待
			HtmlPage page;
			try {
				page = wc.getPage(tempUrl);
				wc.getOptions().setJavaScriptEnabled(true); // 启用JS解释器，默认为true
				page.executeJavaScript(Constants.getJQUERY(getMyIPLocal()));
				ScriptResult scriptResult = page.executeJavaScript(js);
				Object rs = scriptResult.getJavaScriptResult();
				String res = rs.toString();
				if(!"".equals(res) &&
						(((Integer.parseInt(res) <= number) && Integer.parseInt(res) != 3)
								|| (Integer.parseInt(res)>number &&  (Integer.parseInt(res) < number + 6))
						)){
					System.out.println("最大页码 = [" + urls[i] + "]" + res);
					Map map = new HashMap();
					map.put("url",urls[i]);
					map.put("no",res);
					manList.add(map);
				}else{
					bManList.add(urls[i]);
					System.out.println("不满 = [" + urls[i] + "]"+res);
				}
				wc.close();
			} catch (Exception e) {
				bManList.add(urls[i]);
				System.out.println("出错tempUrl = [" + tempUrl + "]");
			}
		}
		System.out.println("manList = [" + manList.size() + "]");
		StringBuffer manBuffer = new StringBuffer();
		for (int i = 0; i < manList.size(); i++) {
			Map tempMap  = (Map)manList.get(i);
			manBuffer.append("{\"city\":\""+tempMap.get("url")+"\",\"pageno\":\"1-"+tempMap.get("no")+"\"},");
		}
		System.out.println("满 = [" + manBuffer.toString() + "]");

		StringBuffer bManBuffer = new StringBuffer();
		for (int i = 0; i < bManList.size(); i++) {
			String city = (String)bManList.get(i);
			bManBuffer.append(city+"@");
		}
		System.out.println("不满 = [" + bManBuffer.toString() + "]");
	}
}
