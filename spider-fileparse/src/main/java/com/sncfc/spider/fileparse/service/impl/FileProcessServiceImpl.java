package com.sncfc.spider.fileparse.service.impl;


import com.sncfc.spider.fileparse.dao.IFileProcessDao;
import com.sncfc.spider.fileparse.service.IFileProcessService;
import com.sncfc.spider.fileparse.utils.AnalyticalRecordUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service(value = "missionService")
public class FileProcessServiceImpl implements IFileProcessService{

	@Resource
	private IFileProcessDao fileProcessDao;

	@Override
	public void addBlackOrGrayList(List<String> list) {
        Map<String,List> map = anaList(list);
		fileProcessDao.insertRiskList(map);
	}

	private static Map<String,List> anaList(List<String> list){
		return AnalyticalRecordUtils.analyRecord(list);
	}



    public static void main(String[] args) {
//        List<String> list = new ArrayList();
//        list.add("100005$@$$2016-8-24 12:38:59$@$$张文$@$$13026892***$@$$2,500.00$@$$32");
//        Map<String,List> map = anaList(list);
//        System.out.println(map);
        String name = "丁南军 （丁南君）";
        String exp = name.substring(name.indexOf(" （"),name.indexOf("）")+1);
        name = name.replace(exp,"");
        System.out.println(name);

    }

}
