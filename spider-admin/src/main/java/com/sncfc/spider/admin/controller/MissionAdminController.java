package com.sncfc.spider.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sncfc.spider.dubbo.SpiderFileProcessService;
import com.sncfc.spider.admin.service.IMissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sncfc.spider.admin.pojo.BaseResultBean;
import com.sncfc.spider.admin.pojo.Constants;
import com.sncfc.spider.admin.utils.JSONUtil;




@Controller
@RequestMapping("/admin/mission")
public class MissionAdminController extends BaseActionController {

    static Logger logger = LoggerFactory.getLogger(MissionAdminController.class);
    @Autowired
	private IMissionService missionService;

	@Autowired
	private SpiderFileProcessService spiderFileProcessService;

    private static String JOB_GROUP = "spider";

	/**
	 * 开始任务
	 * 
	 * @return
	 */
	@RequestMapping(value = "initMission")
	public ModelAndView initMission() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mission/mission");
		return modelAndView;
	}

	/**
	 * 查询任务类型
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryMissionTypes")
	public void queryMissionTypes(HttpServletRequest request,
			HttpServletResponse response) {
		List<Map<String, String>> typesList = missionService.queryMissionTypes();
		responseWithJson(response, typesList);
	}

	/**
	 * 新增任务
	 * 
	 * @return
	 */
	@RequestMapping(value = "addMission")
	public void addMission(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> params = getParameterMap(request);
        params.put("jobGroup",JOB_GROUP);
		BaseResultBean resultBean = new BaseResultBean();
		try {
			Long missionId = missionService.addMission(params);
			resultBean.setSuccess(true);
			resultBean.getMap().put("missionId", missionId);
		} catch (DuplicateKeyException e) {
			resultBean.setSuccess(false);
			resultBean.setErrorMsg("该分类已创建任务，请务要重复创建");
		} catch (Exception e) {
            logger.error("新增任务",e);
			resultBean.setSuccess(false);
			resultBean.setErrorCode(Constants.ERROR_NOTKNOW);
			resultBean.setErrorMsg("系统异常，请稍后再试");
		}
		responseWithJson(response, resultBean);
	}
    /**
     * 修改任务
     *
     * @return
     */
    @RequestMapping(value = "modifyMission")
    public void modifyMission(HttpServletRequest request,HttpServletResponse response) {
        Map<String, String> params = getParameterMap(request);
        params.put("jobGroup",JOB_GROUP);
        BaseResultBean resultBean = new BaseResultBean();
        try {
            missionService.modifyMission(params);
            resultBean.setSuccess(true);
        } catch (Exception e) {
            logger.error("修改任务",e);
            resultBean.setSuccess(false);
            resultBean.setErrorCode(Constants.ERROR_NOTKNOW);
            resultBean.setErrorMsg("系统异常，请稍后再试");
        }
        responseWithJson(response, resultBean);
    }
	/**
	 * 任务树查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryMissionTree")
	public void queryMissionTree(HttpServletRequest request,
			HttpServletResponse response) {
		List missionTree = missionService.queryMissionTree();
		responseWithJson(response, missionTree);
	}

	/**
	 * 查询单个任务
	 * 
	 * @return
	 */
	@RequestMapping(value = "queryMission")
	public void queryMission(HttpServletResponse response,String id) {
		BaseResultBean resultBean = new BaseResultBean();
		try {
			Map mission = missionService.queryMission(id);
			if(mission == null){
				resultBean.setSuccess(false);
				resultBean.setErrorCode(Constants.ERROR_MISSION_NOT_CREATEED);
				resultBean.setErrorMsg("该ID尚未创建任务");
			}else{
				resultBean.setSuccess(true);
				resultBean.setMap(mission);	
			}
		}catch (Exception e) {
            logger.error("查询单个任务",e);
            resultBean.setSuccess(false);
			resultBean.setErrorCode(Constants.ERROR_NOTKNOW);
			resultBean.setErrorMsg("系统异常，请稍后再试");
		}
		responseWithJson(response, resultBean);
		
	}

	/**
	 * 开始任务
	 *
	 * @return
	 */
	@RequestMapping(value = "initMissionUpdate")
	public ModelAndView initMissionUpdate() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mission/updateMission");
		return modelAndView;
	}

	/**
	 * 日志管理页面
	 *
	 * @return
	 */
	@RequestMapping(value = "initMissionLog")
	public ModelAndView initMissionLog() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mission/missionLogAdmin");
		return modelAndView;
	}

	/**
	 * 查询日志文件目录
	 *
	 * @return
	 */
	@RequestMapping(value = "queryLogFiles")
	public void initMissionLog(HttpServletRequest request,HttpServletResponse response) {
		String filePath = request.getParameter("filePath");
		List<Map> files = spiderFileProcessService.getFiles(filePath);
		responseWithJson(response, files);
	}
	/**
	 * 解析日志
	 *
	 * @return
	 */
	@RequestMapping(value = "analysisLog")
	public void analysisLog(HttpServletRequest request,HttpServletResponse response) {
		Map map = getParameterMap(request);
		BaseResultBean resultBean = new BaseResultBean();
		try {
			spiderFileProcessService.analysisLog(map);
			resultBean.setSuccess(true);
		}catch (Exception e){
            logger.error("解析日志",e);
			resultBean.setSuccess(false);
		}
		responseWithJson(response, resultBean);
	}

	@ResponseBody
	@RequestMapping(value = "updateMission")
	public String updateMission(String missionId,String params,String js) {
		int i = missionService.updateMission(missionId,params,js);
		if(i>0){
			return "success";
		}else{
			return "false";
		}
	}
	@ResponseBody
	@RequestMapping(value = "hmUpdate")
	public String hmUpdate() {
//		List<Map> list = missionService.hmSelect();
		missionService.hmUpdate();
		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "hmQueryAllMission")
	public String hmQueryAllMission() {
		List list = missionService.hmQueryAllMission();
		return JSONUtil.toJSONArrayString(list);
	}


	/**
	 * test
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "test")
	public String test() {
		missionService.test("dsddsds");
		return "success";
	}
	/**
	 * test
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "testget")
	public String testget() {
		Map map = missionService.testget();
		return (String) map.get("NAME");
	}


    /**
     * 查询
     * @return
     */
    @RequestMapping(value = "initQuery")
    public ModelAndView initQuery() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("mission/update");
        return modelAndView;
    }
    /**
     * testUpdateSql
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "testUpdateSql")
    public String testUpdateSql(String sql) {
        int  i = missionService.excute(sql);
        return i+"";
    }
    /**
     * testQuerySql
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "testQuerySql")
    public String testQuerySql(String sql) {
        List list = missionService.query(sql);
        return JSONUtil.toJSONArrayString(list);
    }
    @ResponseBody
    @RequestMapping(value = "testInsert")
    public String testInsert() {
        missionService.testInsert();
        return "ok";
    }
}
