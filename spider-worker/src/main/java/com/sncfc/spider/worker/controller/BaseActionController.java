package com.sncfc.spider.worker.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.sncfc.spider.worker.utils.JSONUtil;


/**
 * 
 * 项目通用的Controller基类，留作扩展
 * 
 */
public class BaseActionController extends MultiActionController {

	public static Logger log=Logger.getLogger(BaseActionController.class);
	

	
	public void outCrossDomainResult(HttpServletResponse response, String result) {
    	response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print(result);
		out.flush();
		out.close();
	}
	

	/** 
	 * 以JSON格式输出 
	 * @param response 
	 */  
	protected void responseWithJson(HttpServletResponse response,  
	        Object responseObject) {  
	    response.setCharacterEncoding("UTF-8");  
	    response.setContentType("application/json; charset=utf-8");  
	    PrintWriter out = null;  
	    try {  
	        out = response.getWriter();  
	        out.append(JSONUtil.toJSONString(responseObject));  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (out != null) {  
	            out.close();  
	        }  
	    }  
	}  
	  /** 
	 * 从request中获得参数Map，并返回可读的Map 
	 * 重复的不可读
	 * @param request 
	 * @return 
	 */  
	public  Map<String,String> getParameterMap(HttpServletRequest request) {
	    // 参数Map  
	    Map properties = request.getParameterMap();  
	    // 返回值Map  
	    Map returnMap = new HashMap();  
	    Iterator entries = properties.entrySet().iterator();  
	    Map.Entry entry;  
	    String name = "";  
	    String value = "";  
	    while (entries.hasNext()) {  
	        entry = (Map.Entry) entries.next();  
	        name = (String) entry.getKey();  
	        Object valueObj = entry.getValue();  
	        if(null == valueObj){  
	            value = "";  
	        }else if(valueObj instanceof String[]){  
	            String[] values = (String[])valueObj;  
	            for(int i=0;i<values.length;i++){  
	                value = values[i] + ",";  
	            }  
	            value = value.substring(0, value.length()-1);  
	        }else{  
	            value = valueObj.toString();  
	        }  
	        returnMap.put(name, value);  
	    }  
	    return returnMap;  
	}  
}
