package com.sncfc.spider.worker.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;

public  class Constants {
	/**
	 * 重试次数
	 */
	public final static Integer RETRY_TIMES = 3;
	
	/**
	 * 换行字符串
	 * 
	 */
	public final static String SPLIT_STRING = "@&@@";
	/**
	 * jquery
	 */
	public final static String getJQUERY(String local){
		return "function loadScript(url){"+
				"var script = document.createElement('script');"+
				"script.type = 'text/javascript'; "+
				"if (script.readyState){  "+
				"script.onreadystatechange = function(){"+
				"if (script.readyState == 'loaded' || "+
				"script.readyState == 'complete'){ "+
				"script.onreadystatechange = null; "+
				"} "+
				"}; "+
				"} else {"+
				"script.onload = function(){ "+
				"}; "+
				"} "+
				"script.src = url;"+
				"document.body.appendChild(script);"+
				"}  " +
				"loadScript('http://"+local+":8080/spiderworker/js/jquery-1.6.4.min.js');";
	}

	/**
	 * 头JS
	 */
	public final static String getHeaderJs(String local,String missionId){
		return "var parseUrl = 'http://" + local+ ":8080/spiderworker/work/parseHtml';"
				+ "var parseUrlToFile = 'http://" + local+ ":8080/spiderworker/work/parseHtmlToFile';"
				+ "var missionId = '" + missionId + "';";
	} 
	public static String getPrefix(String type){
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return type + SPLIT_STRING + time + SPLIT_STRING;
	}
}
