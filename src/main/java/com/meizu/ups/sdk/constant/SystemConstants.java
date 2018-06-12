package com.meizu.ups.sdk.constant;

/**
 * Created by wangxinguo on 2016-8-21.
 */
public class SystemConstants {

    public static final String CHAR_SET = "UTF-8";

    public static final String SDK_VERSION = "1.0.1.20180612";

    private static final String PUSH_HOST_NAME = "server-api-mzups.meizu.com";

    /**
     * 推送服务:pushId推送接口（透传消息）
     */
    public static final String PUSH_APPID_UNVARNISHED_PUSHIDS = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/unvarnished/pushByPushId";
    /**
     * 推送服务:alias推送接口(透传消息)
     */
    public static final String PUSH_APPID_UNVARNISHED_ALIAS = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/unvarnished/pushByAlias";

    /**
     * 推送服务:pushId推送接口（通知栏消息）
     */
    public static final String PUSH_APPID_VARNISHED_PUSHIDS = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/varnished/pushByPushId";

    /**
     * 推送服务:alias推送接口（通知栏消息）
     */
    public static final String PUSH_APPID_VARNISHED_ALIAS = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/varnished/pushByAlias";

    /**
     * 统计服务: 获取应用推送统计（最长跨度30天）
     */
    public static final String GET_PUSH_DAILY_STATICS = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/statistics/dailyPushStatics";
    
    /**
     * 全部用户推送
     */
    public static final String PUSH_APPID_PUSH_TO_APP = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/pushTask/pushToApp";

    /**
     * 取消全量推送任务
     */
    public static final String CANCEL_PUSH_TO_APP = "http://" + PUSH_HOST_NAME + "/ups/api/server/push/pushTask/cancel";


}
