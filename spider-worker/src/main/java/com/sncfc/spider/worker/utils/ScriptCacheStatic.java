package com.sncfc.spider.worker.utils;

import java.util.HashMap;
import java.util.Map;

public class ScriptCacheStatic {

    /**
     * 存储任务脚本
     */
    private static Map<String, String> SCRIPT = new HashMap<String, String>();

    /**
     * 新增脚本
     *
     * @param type
     */
    public static void addScript(String type, String js) {
        SCRIPT.put(type, js);
    }

    /**
     * 结束缓存脚本
     *
     * @param type
     */
    public static void removeScript(String type) {
        SCRIPT.remove(type);

    }

    /**
     * 是否有
     *
     * @param type
     */
    public static boolean has(String type) {
        return SCRIPT.containsKey(type);
    }
    /**
     * 获取脚本
     *
     * @param type
     */
    public static String getScript(String type) {
        return SCRIPT.get(type);
    }
}

