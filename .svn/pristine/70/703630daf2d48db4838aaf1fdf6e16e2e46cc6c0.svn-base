package com.sncfc.spider.worker.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileUtils {
	
    public static void writeLog(String filename,String word){
        try{
	        String path="D:\\spiderfile\\"+filename;
	        File file=new File(path);
	        if(!file.exists()){
	        	file.createNewFile();
	        }
	        FileOutputStream out=new FileOutputStream(file,false); //如果追加方式用true        
	        out.write(word.getBytes("utf-8"));//注意需要转换对应的字符集
	        out.close();
        }
        catch(IOException ex){
           ex.printStackTrace();
        }
    }

	/**
	 * 获取CLASSPATH 绝对地址
	 * @return
     */
    public  String getPath(){
		String url = this.getClass().getClassLoader().getResource("").getPath();
		String os = System.getProperty("os.name");
		if(os.toLowerCase().startsWith("win")){
			url = url.substring(1);
		}
		return url;
	}
    public static void main(String[] args) {
    	FileUtils.writeLog("test.txt", "许欢");
	}
}
