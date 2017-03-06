package com.sncfc.spider.dubbo.impl;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.sncfc.spider.dubbo.SpiderMissionService;
import com.sncfc.spider.dubbo.SpiderWorkerService;
import com.sncfc.spider.queue.pojo.Constants;
import com.sncfc.spider.queue.service.IMissionService;
import com.sncfc.spider.queue.thread.SpiderThread;
import com.sncfc.spider.queue.utils.CacheStatic;
import org.springframework.util.StringUtils;


@Service("spiderMissionService")
public class SpiderMissionServiceImpl implements SpiderMissionService {


	@Autowired
	private SpiderWorkerService spiderWorkerService;

	@Autowired
	private SpiderWorkerService spiderWorkerBroadcastService;

	@Autowired
	private IMissionService missionService;
	/**
	 * 标识分页参数
	 */
	private final static String PAGENO = "pageno";
    /**
     * 标识数组参数
     */
    private final static String ARRAY = "array";

	@Override
	public void startMissionByType(String type) {
		Map map = missionService.queryMission(type);
		//解析参数
        String fyType = map.get("PARSE_TYPE").toString();
		int threadMaxNum = Integer.parseInt(map.get("THREAD_NUM").toString());
		String url = map.get("URL").toString();
		String params =  map.get("PARAMS") == null ? "" : map.get("PARAMS").toString();
//		当前状态是否是暂停的  如果是暂停的
		int beginPage = 0;
		if(Constants.MISSION_PAUSE.equals(map.get("STATUS"))){
			Map pauseMission = missionService.queryPauseMission(type);
			beginPage = Integer.parseInt(pauseMission.get("CURRENT_PAGE")+"");
		}
		//修改状态为执行中
		missionService.startMission(type);
		int intervalTime = Integer.parseInt(map.get("SLEEP_TIME").toString());
		String js = map.get("JS").toString();
		//创建任务
		ConcurrentLinkedQueue<String> urlQueue = assembleUrl(url, params,beginPage);
		CacheStatic.addMission(type);
		//广播脚本
		spiderWorkerBroadcastService.addSpiderScript(type,js);
		SpiderThread st = new SpiderThread(spiderWorkerService,spiderWorkerBroadcastService,missionService, urlQueue,intervalTime,type,fyType);
		for (int i = 1; i <= threadMaxNum; i++) {
			Thread thread = new Thread(st, i+"号爬虫窗口");
			thread.start();
		}
		System.out.println(type+"开始执行");
	}
	@Override
	public boolean pause(String type) {
		boolean flag = false;
		try {
			CacheStatic.pauseMission(type);
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	private  ConcurrentLinkedQueue<String> assembleUrl(String url,String params,int beginPage){
		ConcurrentLinkedQueue<String> urlQueue = new ConcurrentLinkedQueue<String>();
        //无参数
		if(StringUtils.isEmpty(params)){
			urlQueue.add(url);
			return urlQueue;
		}
        //有参数
		if(params.startsWith("[")){
            //数组类型
			List list = new Gson().fromJson(params, ArrayList.class);
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map) list.get(i);
				//是否包含pageno 
				urlQueue = ognise(map,url,urlQueue);
			}
		}else{
            //非数组类型
			Map map = new Gson().fromJson(params, HashMap.class);
			urlQueue = ognise(map,url,urlQueue);
		}
        //去除暂停前已处理完的
		while(beginPage > 0){
			urlQueue.poll();
			beginPage--;
		}
		return urlQueue;
	}

	private static ConcurrentLinkedQueue<String> ognise(Map map ,String url,ConcurrentLinkedQueue<String> urlQueue){
        //查看是否包涵array,pageno关键字 不支持同时包含多个不同类关键字
        boolean isContainsPageno = false;
        boolean isContainsArray = false;
        for (Object key : map.keySet()) {
            String temKey = key.toString().toLowerCase();
            if(temKey.contains(PAGENO)){
                isContainsPageno = true;
            }else if(temKey.contains(ARRAY)){
                isContainsArray = true;
            }
        }
        if(isContainsPageno){
			//包含pageno 为分页类型
			String page = (String) map.get(PAGENO);
			String[] pages = page.split("-");
			int startPageNo = Integer.parseInt(pages[0]);
			int endPageNo = Integer.parseInt(pages[1]);
			for (int j = startPageNo; j <= endPageNo; j++) {
				String tempUrl = url.replaceAll("\\$\\{"+PAGENO+"\\}", j+"");
				//循环map {city=nj, pageno=1-10}
				for (Object key : map.keySet()) {
					if(!PAGENO.equals(key)){
						tempUrl = tempUrl.replaceAll("\\$\\{"+key+"\\}", (String) map.get(key));
					}
				}
				urlQueue.add(tempUrl);
			}
		}else if(isContainsArray){
            //包含array数组
            List list = new ArrayList();
            for (Object key : map.keySet()) {
                String temKey = key.toString();
                if(temKey.toLowerCase().contains(ARRAY)){
                    String tempValue = map.get(temKey).toString();
                    String[] temArr = tempValue.split(",");
                    List tList = new ArrayList();
                    for (int i = 0; i < temArr.length; i++) {
                        if(list.isEmpty()){
                            Map tMap = (Map) ((HashMap)map).clone();
                            tMap.put(temKey,temArr[i]);
                            tList.add(tMap);
                        }else{
                            for (int j = 0; j < list.size(); j++) {
                                HashMap sMap = (HashMap)list.get(j);
                                Map dMap = (Map) sMap.clone();
                                dMap.put(temKey,temArr[i]);
                                tList.add(dMap);
                            }
                        }
                    }
                    list = tList;
                }
            }
            for (int i = 0; i < list.size(); i++) {
                String tempUrl = url;
                Map ttMap = (Map)list.get(i);
                System.out.println(ttMap);
                for (Object key : ttMap.keySet()) {
                    tempUrl = tempUrl.replaceAll("\\$\\{"+key+"\\}", (String) ttMap.get(key));
                }
                urlQueue.add(tempUrl);
            }
        }else{
			String tempUrl = url;
			for (Object key : map.keySet()) {
				if(!PAGENO.equals(key)){
					tempUrl = tempUrl.replaceAll("\\$\\{"+key+"\\}", (String) map.get(key));
				}
			}
			urlQueue.add(tempUrl);
		}
		return urlQueue;
	}
	public static void main(String[] args) {
//        String json = "{" +
//                "\"noArray\":\"0,50,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800,850,900,950,1000\"," +
//                "\"queryArray\":\"%E5%8C%97%E4%BA%AC%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%A4%A9%E6%B4%A5%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B2%B3%E5%8C%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%B1%B1%E8%A5%BF%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%86%85%E8%92%99%E5%8F%A4%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%90%89%E6%9E%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E9%BB%91%E9%BE%99%E6%B1%9F%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E4%B8%8A%E6%B5%B7%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B1%9F%E8%8B%8F%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B5%99%E6%B1%9F%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%AE%89%E5%BE%BD%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E7%A6%8F%E5%BB%BA%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B1%9F%E8%A5%BF%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%B1%B1%E4%B8%9C%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B2%B3%E5%8D%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B9%96%E5%8C%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B9%96%E5%8D%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%B9%BF%E4%B8%9C%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%B9%BF%E8%A5%BF%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%B5%B7%E5%8D%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E9%87%8D%E5%BA%86%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%9B%9B%E5%B7%9D%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E8%B4%B5%E5%B7%9E%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E4%BA%91%E5%8D%97%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E8%A5%BF%E8%97%8F%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E9%99%95%E8%A5%BF%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E9%9D%92%E6%B5%B7%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E5%AE%81%E5%A4%8F%20%E6%89%A7%E8%A1%8C%E4%BA%BA,%E6%96%B0%E7%96%86%20%E6%89%A7%E8%A1%8C%E4%BA%BA\"" +
//                "}";
//        HashMap map = new Gson().fromJson(json, HashMap.class);
//        List list = new ArrayList();
//        for (Object key : map.keySet()) {
//            String temKey = key.toString();
//            if(temKey.toLowerCase().contains(ARRAY)){
//                String tempValue = map.get(temKey).toString();
//                String[] temArr = tempValue.split(",");
//                List tList = new ArrayList();
//                for (int i = 0; i < temArr.length; i++) {
//                    if(list.isEmpty()){
//                        Map tMap = (Map) map.clone();
//                        tMap.put(temKey,temArr[i]);
//                        tList.add(tMap);
//                    }else{
//                        for (int j = 0; j < list.size(); j++) {
//                            HashMap sMap = (HashMap)list.get(j);
//                            Map dMap = (Map) sMap.clone();
//                            dMap.put(temKey,temArr[i]);
//                            tList.add(dMap);
//                        }
//                    }
//                }
//                list = tList;
//            }
//        }
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).toString());
//        }
    }
}
