package com.sncfc.spider.fileparse.utils;

import com.sncfc.spider.fileparse.pojo.ConstantsMission;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by xuhuan on 2016/9/6.
 */
public class FileUtil {

    public  String fileHome;
    public  String fileAnalysisedHome;
    public final static String TEMP = "tmp";
    public final static String UNZIP_TEMP = "unzipTmp";
    public final static String BAK = "bak";

    public FileUtil(String fileHome,String fileAnalysisedHome) {
        this.fileHome = fileHome;
        this.fileAnalysisedHome = fileAnalysisedHome;
    }


    public  String getUnZipTempPath(){
        return fileAnalysisedHome + UNZIP_TEMP;
    }
    public String getBakPath(){
        return fileAnalysisedHome + BAK;
    }
    public String getTempPath(){
        return fileAnalysisedHome + TEMP;
    }


    /**
     * 获取本目录文件列表
     * @param path
     * @return
     */
    public  List<File> getFileList(String path){
        path = ognisePath(path);
        List<File> list = new ArrayList<File>();
        File file = new File(path);
        File[] files = file.listFiles(new LogFileFilter());
        for (File tFile : files) {
            list.add(tFile);
        }
        Collections.sort(list, new ListSortComparator());
        return list;
    }

    /**
     * 递归获取目录下所有符合规则的文件
     * @param path
     * @return
     */
    public static List<File> recursiveFileList(String path){
        List<File> fileList = new ArrayList<File>();
        File file = new File(path);
        if(!file.exists()){
            return null;
        }
        if(!file.isDirectory()){
            fileList.add(file);
            return fileList;
        }
        File[] fileArray= file.listFiles(new LogFileFilter());
        for (File tempFile : fileArray) {
            if (tempFile.isDirectory()) {
                //属于文件夹 递归
                fileList.addAll(recursiveFileList(tempFile.getPath()));
            } else {
                //属于文件
                fileList.add(tempFile);
            }
        }
        return fileList;
    }
    private String ognisePath(String path){
        if(StringUtils.isEmpty(path)){
            path = fileHome;
        }else{
            if("/".equals(File.separator)){
                //linux
                path = path.replaceAll("\\|","/");
            }else{
                //window
                path = path.replaceAll("\\|","\\\\");
            }
            path = fileHome + path;
        }
        return path;
    }
    /**
     * 批量移动文件
     * @param fileList
     */
    public void removeToTmpFile(List<File> fileList) {
        for (File file : fileList) {
            File dest = new File(fileAnalysisedHome + TEMP , System.currentTimeMillis()+file.getName());
            try {
                Files.copy(file.toPath(), dest.toPath());
                file.delete();
//                System.out.println(fileAnalysisedHome + TEMP);
//                System.out.println(System.currentTimeMillis()+file.getName());
//                System.out.println(flag);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 比较类
     */
     static class ListSortComparator implements Comparator<File> {
        @Override
        public int compare(File o1, File o2) {
            return o1.lastModified() <= o2.lastModified() ? 1:-1;
        }
    }

    /**
     * 文件过滤
     */
    static class LogFileFilter implements FileFilter{
        @Override
        public boolean accept(File file) {
            if(file.isDirectory()){
                return true;
            }else{
                String name = file.getName();
                if(name.contains("spiderfile") ){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }

    /**
     * 解析出全部文件
     * @param path 目录 使用分隔符|分割
     * @param names 名称 使用分隔符|分割
     * @return
     */
    public List<File> ansToFiles(String path, String names) {
        //如果path为空 则为根目录
        path = ognisePath(path);
        //循环解析文件名
        String[] nameArray = names.split("\\|");
        List<File> fileList = new ArrayList<File>();
        for (String name : nameArray) {
            //如果 是 文件夹 则递归出里面所有符合的文件
            //如果 是 文件 则直接解析
            fileList.addAll(FileUtil.recursiveFileList(path + name));
        }
        return fileList;
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(String des) {
        File file = new File(des);
        List<String> list = new ArrayList<String>();
        File[] files = file.listFiles();
        for (File temFile : files) {
            list.addAll(readFileByLines(temFile));
        }
        return list;
    }
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(File file) {
        List<String> list = new ArrayList<String>();
        BufferedReader reader = null;
        try {
//            reader = new BufferedReader(new FileReader(file));
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": " + tempString);
                list.add(tempString);
                line++;
            }
            reader.close();
            System.out.println(file.getName() +"记录数:"+line);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return list;
    }
    public static void main(String[] args) {
//        String path = "C:\\Users\\123\\Desktop\\remoteLogs";
        String path = "C:\\Users\\123\\Desktop\\remoteLogs\\work01\\spiderfile.log";
        File file = new File(path);
        List<String>  list = readFileByLines(file);
        StringBuffer sb = new StringBuffer();
        int noRecord = 0;
        int haveRecord = 0;
        int falses = 0;
        for (String lineWord : list) {
            String flag = "";
            String[] wordArray = lineWord.split(ConstantsMission.SPLIT_STRING);
            if(wordArray.length < 3){
                System.out.println(lineWord);
                continue;
            }
            if(wordArray[2].equals("无记录")){
                flag ="无";
                noRecord++;
            }else if("".equals(wordArray[2])){
                falses++;
                flag ="空";
            }else{
                flag ="有";
                haveRecord++;
            }
            System.out.println(flag + " || " + lineWord);
        }
        System.out.println(noRecord);
        System.out.println(haveRecord);
        System.out.println(falses);
    }
}



