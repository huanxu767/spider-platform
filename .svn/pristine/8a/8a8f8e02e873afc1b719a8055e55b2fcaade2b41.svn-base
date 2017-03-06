package com.sncfc.spider.fileparse.Thread;


import com.sncfc.spider.fileparse.service.IFileProcessService;
import com.sncfc.spider.fileparse.utils.FileUtil;
import com.sncfc.spider.fileparse.utils.ZipUtils;

import java.io.File;
import java.util.List;


public class LogProcessThread implements Runnable{
	private static boolean isRunning = false;
	private File file ;
    private FileUtil fileUtil;
    private IFileProcessService service;
    public LogProcessThread(File file,FileUtil fileUtil,IFileProcessService service) {
		super();
		this.file = file;
        this.fileUtil = fileUtil;
        this.service = service;
	}

	public void run(){
		isRunning = true;
        ZipUtils zipUtils = new ZipUtils(fileUtil);
		File[] fileList =  file.listFiles();
        try {
            for (File temFile : fileList) {
                //解压至
                zipUtils.unZip(temFile);
                //读文件内容 写入数据库
                List<String> list =  FileUtil.readFileByLines(fileUtil.getUnZipTempPath());
                //移动到bak目录
                zipUtils.zip(fileUtil.getUnZipTempPath());
                //入库
                try {
                    service.addBlackOrGrayList(list);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            isRunning = false;
        }
    }

	public static boolean isRunning() {
		return isRunning;
	}

	public static void main(String[] args) {
//        zipUtils.unZip(new File("C:\\Users\\123\\Desktop\\analysisedLogs\\tmp\\spider-worker_spiderfile_all_2016-08-26.0.log.zip"));
//		FileUtil.readFileByLines(FileUtil.getUnZipTempPath());
//		ZipUtils.zip(FileUtil.getUnZipTempPath());
	}
}
