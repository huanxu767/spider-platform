package com.sncfc.spider.fileparse.utils;

/**
 * Created by xuhuan on 2016/9/6.
 */

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;

import java.io.File;
import java.util.List;

/**
 * 功能：
 * 1 、实现把指定文件夹下的所有文件压缩为指定文件夹下指定 zip 文件
 * 2 、实现把指定文件夹下的 zip 文件解压到指定目录下
 */

public class ZipUtils {

    private FileUtil fileUtil;

    public ZipUtils(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }

    public static void main(String[] args) {
//        zip("D:\\zip测试", "D:\\测试结果.zip");
//        zip("C:\\Users\\123\\Desktop\\analysisedLogs\\unzipTmp\\","C:\\Users\\123\\Desktop\\analysisedLogs\\bak\\22.zip");
//        unzip("C:\\Users\\123\\Desktop\\remoteLogs\\work01\\archive\\spider-worker_spiderfile_all_2016-08-28.8.log.zip", "C:\\Users\\123\\Desktop\\bak\\work01\\");
    }

    public  void zip(String dir) {
        File[] files = new File(dir).listFiles();
        for (File file : files) {
            String name = file.getName();
            if(name.contains("spider-worker")){
                //该文件为解压过后的文件 为预防文件名称重复
                name = name.replace("spider-worker",System.currentTimeMillis()+"");
            }
            zip(file.getParent(),fileUtil.getBakPath()+File.separator+name+".zip");
            file.delete();
        }
    }
    public static void zip(String dir,String zipFile) {
        Zip zip = new Zip();
        zip.setBasedir(new File(dir));
        // zip.setIncludes(...); 包括哪些文件或文件夹eg:zip.setIncludes("*.java");
        // zip.setExcludes(...); 排除哪些文件或文件夹
        zip.setDestFile(new File(zipFile));
        Project p = new Project();
        // p.setBaseDir(new File(src));
        zip.setProject(p);
        zip.execute();
    }
    /**
     * 解压 并删除
     * @param zipFile
     * @param dir
     */
    public static void unzip(String zipFile,String dir) {
        Expand expand = new Expand();
        expand.setDest(new File(dir));
        expand.setSrc(new File(zipFile));
        Project p = new Project();
        expand.setProject(p);
        expand.execute();
        new File(zipFile).delete();
    }

    /**
     * 批量移动
     * @param fileList
     */
    public  void unZip(List<File> fileList) {
        for (File file : fileList) {
            unZip(file);
        }
    }

    /**
     * 解压缩单个文件
     * @param file
     */
    public  void unZip(File file) {
            String exp = getFileExp(file);
            if("zip".equalsIgnoreCase(exp)){
                //如果是zip文件 解压
                unzip(file.getPath(),fileUtil.getUnZipTempPath());
            }else{
                //否则直接移动
                boolean flag = file.renameTo(new File(fileUtil.getUnZipTempPath(),System.currentTimeMillis() + file.getName()));
            }
    }

    /**
     * 获取文件后缀名
     * @param file
     * @return
     */
    public static String getFileExp(File file){
        String path = file.getPath();
        String exp = path.substring(path.lastIndexOf(".")+1);
        return exp;
    }

}

