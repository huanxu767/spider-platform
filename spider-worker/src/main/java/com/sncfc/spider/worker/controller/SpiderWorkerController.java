package com.sncfc.spider.worker.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sncfc.spider.worker.propertie.ConfigProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sncfc.spider.worker.utils.SpiderUtils;

import java.io.File;

@Controller
@RequestMapping("/work")
public class SpiderWorkerController extends BaseActionController {

	Logger logger = LoggerFactory.getLogger(SpiderWorkerController.class);

    Logger spiderLog = LoggerFactory.getLogger("spiderLog");

	@Autowired
	private ConfigProperties configPropertys;

	@ResponseBody
	@RequestMapping(value = "testlog")
	public String testlog() {

//
//		String url = this.getClass().getClassLoader().getResource("").getPath();
//		String path = url+"logback.xml";
//		System.out.println(path);
//		String os = System.getProperty("os.name");
//		if(os.toLowerCase().startsWith("win")){
//			System.out.println(os + " can't gunzip");
//		}
//
//		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//		lc.reset();

//
//		File file = new File(path);
//		File desFile = new File(url+"22222.log");
//		System.out.println(file.renameTo(desFile));
//
//		JoranConfigurator configurator = new JoranConfigurator();
//		configurator.setContext(lc);
//		try {
//			configurator.doConfigure(path);
//		} catch (JoranException e) {
//			e.printStackTrace();
//		}
		spiderLog.info("\u8881\u6676");
		spiderLog.trace("======trace");
		spiderLog.debug("======debug");
		spiderLog.info("======info");
		spiderLog.warn("======warn");
		spiderLog.error("======error");
		logger.error("另一个");
		return SpiderUtils.getMyIPLocal();
	}

	/**
	 * 代理解析
	 * 
	 * @param url
	 * @param js
	 */
	@RequestMapping(value = "parseHtml" )
	public void parseHtml(HttpServletRequest request,
			HttpServletResponse response,String missionId, String url, String js) {
//		long startTime=System.currentTimeMillis();   //获取开始时间
		String result = "";
		if(StringUtils.isEmpty(url)){
			outCrossDomainResult(response,"url为空");
			return;
		}
		try {
			result = SpiderUtils.crawler(url, js,missionId,configPropertys.getProxyUrl(),configPropertys.getProxyPort(),configPropertys.isProxyFlag());
		} catch (Exception e) {
			logger.error("代理解析",e);
		}
//		long endTime=System.currentTimeMillis(); //获取结束时间
//		System.out.println("程序运行时间： "+(endTime-startTime)+"ms" );
		outCrossDomainResult(response,result);
	}
	/**
	 * 代理解析
	 * 
	 * @param url
	 * @param js
	 */
	@RequestMapping(value = "parseHtmlToFile")
	public void parseHtmlToFile(HttpServletRequest request,
			HttpServletResponse response,String missionId, String url, String js,String sleepTime) {
		String result = "";
//        System.out.println("进来了");
        if(StringUtils.isEmpty(url)){
			outCrossDomainResult(response,"url为空");
			return;
		}
		if(!StringUtils.isEmpty(sleepTime)){
			try {
				Thread.sleep(Long.parseLong(sleepTime));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			result = SpiderUtils.crawler(url, js,missionId,configPropertys.getProxyUrl(),configPropertys.getProxyPort(),configPropertys.isProxyFlag());
			if(!StringUtils.isEmpty(result)){
//                System.out.println(result);
                spiderLog.info(result);
			}else{
				logger.warn("url解析为空:"+url+"");
			}
		} catch (Exception e) {
			logger.error("代理解析",e);
		}
		outCrossDomainResult(response,"success");
	}
	@RequestMapping(value = "testRead")
	public void testRead(){
	}
	@RequestMapping(value = "testDelete")
	public void testDelete(){
		File file = new File("/home/weblogic/java/remoteFile1/ceshi.txt");
		file.delete();
	}
}
