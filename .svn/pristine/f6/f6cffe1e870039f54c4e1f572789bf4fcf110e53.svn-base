package com.sncfc.spider.queue.thread;

import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sncfc.spider.dubbo.SpiderWorkerService;
import com.sncfc.spider.queue.service.IMissionService;
import com.sncfc.spider.queue.utils.CacheStatic;



public class SpiderThread implements Runnable{
	
	private SpiderWorkerService service;
	private SpiderWorkerService broadcastService;
	private IMissionService missionService;
    private ConcurrentLinkedQueue<String> urls;
    private int intervalTime;
    private String type;
    private int allPageSize;
    private String fyType;


    public SpiderThread(SpiderWorkerService service,SpiderWorkerService broadcastService,IMissionService missionService,
    		ConcurrentLinkedQueue<String> urls,int intervalTime,String type,String fyType) {
		super();
		this.service = service;
		this.missionService = missionService;
		this.urls = urls;
		this.intervalTime = intervalTime;
		this.type = type;
		this.allPageSize = urls.size();
		this.broadcastService = broadcastService;
        this.fyType = fyType;
	}

	public void run(){
		do {
			String url;
			synchronized(this){  
				if(!CacheStatic.has(type)){
					//若任务被移除则终止 
					System.out.println(type+"终止了");
					broadcastService.removeSpiderScript(type);
					break;
				}
				if(CacheStatic.isPause(type)){
					//暂停
					System.out.println(type+"暂停了，页码："+urls.peek());
					broadcastService.removeSpiderScript(type);
					missionService.pauseMission(type,allPageSize - urls.size(),allPageSize);
					CacheStatic.stopMission(type);
					break;
				}
				if(urls.isEmpty()){
					//任务结束
					System.out.println(type+":URL空了");
					broadcastService.removeSpiderScript(type);
					CacheStatic.stopMission(type);
					missionService.completeMission(type);
					break;
				}else{
					url = urls.poll(); 
				}
		    }  
			try {
				if(intervalTime>0){
					//固定睡眠
					Thread.sleep(intervalTime);
				}else{
					//随机时间
					Thread.sleep(new Random().nextInt(Math.abs(intervalTime)));
				}
                service.spiderByUrl(url,type,fyType);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (true);
    }
}
