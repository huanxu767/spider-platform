package com.sncfc.spider.dubbo.impl;


import com.sncfc.spider.dubbo.SpiderFileProcessService;
import com.sncfc.spider.dubbo.SpiderLogBackService;
import com.sncfc.spider.fileparse.Thread.LogProcessThread;
import com.sncfc.spider.fileparse.propertie.ConfigProperties;
import com.sncfc.spider.fileparse.service.IFileProcessService;
import com.sncfc.spider.fileparse.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("spiderFileProcessService")
public class SpiderFileProcessServiceImpl implements SpiderFileProcessService {

	@Autowired
	private SpiderLogBackService spiderLogBackService;

    @Autowired
    private IFileProcessService fileProcessService;

    @Autowired
    private ConfigProperties configProperties;

	@Override
	public List<Map> getFiles(String path) {
        FileUtil fileUtil = new FileUtil(configProperties.getFileHome(),configProperties.getFileAnalysisedHome());
		List<File> fileList = fileUtil.getFileList(path);
		List<Map> fileMapList = new ArrayList<Map>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (File tFile : fileList) {
			Date date = new Date(tFile.lastModified());
			String now = format.format(date);
			Map tempMap = new HashMap();
			tempMap.put("date",now);
			tempMap.put("name",tFile.getName());
			tempMap.put("size",Math.ceil(tFile.length()/1024.0));
			tempMap.put("isDirectory",tFile.isDirectory());
			fileMapList.add(tempMap);
		}
		return fileMapList;
	}

	@Override
	public void analysisLog(Map params) {
        FileUtil fileUtil = new FileUtil(configProperties.getFileHome(),configProperties.getFileAnalysisedHome());
		//获取根目录 使用分隔符|分割
		String path =  params.get("path") == null?"":params.get("path").toString();
		//获取文件名 使用分隔符|分割
		String names = params.get("names").toString();
		//解析出全部文件
		List<File> fileList = fileUtil.ansToFiles(path,names);
//		for (File tempFile : fileList) {
//			System.out.println(tempFile.getName() + "  " +tempFile.getPath());
//		}
        //是否包含log结尾的
        boolean flag = false;
		for (File tempFile : fileList) {
			String name = tempFile.getName();
			String exp = name.substring(name.lastIndexOf(".") + 1);
			if("log".equalsIgnoreCase(exp)){
				//有锁定的文件
                flag = true;
				break;
			}
		}
        if(flag){
            spiderLogBackService.releaseControlLog();
        }
		//一次性把文件全部移到tmp目录 如果有spiderfile
        fileUtil.removeToTmpFile(fileList);
        if(flag){
            spiderLogBackService.startLog();
        }
		//启动线程处理
		if(!LogProcessThread.isRunning()){
			LogProcessThread st = new LogProcessThread(new File(fileUtil.getTempPath()),fileUtil,fileProcessService);
			Thread thread = new Thread(st, "解析入库");
			thread.start();
		}
	}

}
