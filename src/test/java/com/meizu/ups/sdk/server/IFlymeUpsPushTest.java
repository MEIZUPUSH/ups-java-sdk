package com.meizu.ups.sdk.server;


import com.alibaba.fastjson.JSONObject;
import com.meizu.ups.sdk.constant.ClickType;
import com.meizu.ups.sdk.server.constant.ResultPack;
import com.meizu.ups.sdk.server.model.push.PushResult;
import com.meizu.ups.sdk.server.model.push.UnVarnishedMessage;
import com.meizu.ups.sdk.server.model.push.VarnishedMessage;
import com.meizu.ups.sdk.server.model.statistics.DailyPushStatics;
import com.meizu.ups.sdk.utils.DateUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author wangxinguo <wangxinguo@meizu.com>
 * @date 2017-11-21
 */
public class IFlymeUpsPushTest {
    /**
     * 平台注册应用secretKey
     */
    public static final String APP_SECRET_KEY = "0c199e04a7af4854a8a3b142db9875aa";
    /**
     * 平台注册应用ID
     */
    public static final Long appId = 1000000L;


    /**
     * 通知栏消息推送（pushMessage）
     *
     * @throws Exception
     */
    @Test
    public void testVarnishedMessagePush() throws Exception {
        //推送对象
        IFlymeUpsPush push = new IFlymeUpsPush(APP_SECRET_KEY);
        JSONObject param = new JSONObject();
        param.put("key", "v1");
        param.put("k2", "v2");
        param.put("k3", "v3");

        //组装消息
        VarnishedMessage message = new VarnishedMessage.Builder().appId(appId)
                .title("Java SDK 推送标题").content("消息内容")
                .clickType(ClickType.CUSTOM_URI.getDesc())
//                .activity("com.meizu.upspushdemo.TestActivity")
                .customUri("upspushscheme://com.meizu.upspush/notify_detail?title=ups title&content=ups content")
//                .parameters(param)
//                .url("https://www.baidu.com/")
//                .customAttribute("客户端自定义参数")
                .build();

        //目标用户
        List<String> pushIds = new ArrayList<String>();
        pushIds.add("1_S5Q4b72627d456c797d5e445d030507545c4275617446");
        pushIds.add("2_fW3FqiMlR0uBqMeUO97SC11ggZxnGaFeiBoQ7pugWu0=");
        pushIds.add("3_0869154022499037300001002200CN01");

        // 1 调用推送服务
        ResultPack<PushResult> result = push.pushMessage(message, pushIds);
        handleResult(result);
    }


    /**
     * 别名通知栏消息推送（pushMessage）
     *
     * @throws Exception
     */
    @Test
    public void testVarnishedMessagePushByAlias() throws Exception {
        //推送对象
        IFlymeUpsPush push = new IFlymeUpsPush(APP_SECRET_KEY);

        //组装消息
        VarnishedMessage message = new VarnishedMessage.Builder().appId(appId)
                .title("Java SDK 推送标题").content("Java SDK 推送内容")
                .build();

        //目标用户
        List<String> alias = new ArrayList<String>();
        alias.add("alias1");
        alias.add("alias2");

        // 1 调用推送服务
        ResultPack<PushResult> result = push.pushMessageByAlias(message, alias);
        // 2 处理推送结果
        handleResult(result);
    }

    /**
     * 透传消息推送（pushMessage）
     *
     * @throws Exception
     */
    @Test
    public void testUnVarnishedMessagePush() throws Exception {
        //推送对象
        IFlymeUpsPush push = new IFlymeUpsPush(APP_SECRET_KEY);
        //组装透传消息
        UnVarnishedMessage message = new UnVarnishedMessage.Builder()
                .appId(appId)
                .title("Java SDK 透传推送标题")
                .content("Java Sdk透传推送内容")
                .isOffLine(true)
                .validTime(10)
                .build();

        //目标用户
        List<String> pushIds = new ArrayList<String>();
        pushIds.add("2_PLhHeEuuYSFHkmhaLdX6VdkpfmhFH9fH5hYD2IEcnwA=");

        ResultPack<PushResult> result = push.pushMessage(message, pushIds);
        // 2 处理推送结果
        handleResult(result);
    }

    /**
     * 别名透传推送
     *
     * @throws Exception
     */
    @Test
    public void testUnVarnishedMessagePushByALias() throws Exception {
        //推送对象
        IFlymeUpsPush push = new IFlymeUpsPush(APP_SECRET_KEY);
        //组装透传消息
        UnVarnishedMessage message = new UnVarnishedMessage.Builder()
                .appId(appId)
                .title("Java SDK 透传推送标题")
                .content("Java Sdk透传推送内容")
                .build();

        //目标用户
        List<String> alias = new ArrayList<String>();
        alias.add("ups");
        alias.add("alias");
        alias.add("alias2");

        ResultPack<PushResult> result = push.pushMessageByAlias(message, alias);
        // 2 处理推送结果
        handleResult(result);
    }


    @Test
    public void testDailyPushStatics() throws IOException {
        //推送对象
        IFlymeUpsPush push = new IFlymeUpsPush(APP_SECRET_KEY);
        Date startTime = DateUtils.str2Date("2017-06-03", "yyyy-MM-dd");
        Date endTime = DateUtils.str2Date("2017-06-10", "yyyy-MM-dd");
        ResultPack<List<DailyPushStatics>> resultPack = push.dailyPushStatics(appId, startTime, endTime);
        System.out.println(resultPack);
    }

    /**
     * 处理推送结果
     *
     * @param result
     */
    private void handleResult(ResultPack<PushResult> result) {
        if (result.isSucceed()) {
            // 2 调用推送服务成功 （其中map为设备的具体推送结果，一般业务针对超速的code类型做处理）
            PushResult pushResult = result.value();
            String msgId = pushResult.getMsgId();//推送消息ID，用于推送流程明细排查
            Map<String, List<String>> targetResultMap = pushResult.getRespTarget();//推送结果，全部推送成功，则map为empty
            System.out.println("push result:" + pushResult);
            if (targetResultMap != null && !targetResultMap.isEmpty()) {
                System.err.println("push fail token:" + targetResultMap);
            }
        } else {
            // 调用推送接口服务异常 eg: appId、appKey非法、推送消息非法.....
            // result.code(); //服务异常码
            // result.comment();//服务异常描述
            System.out.println(String.format("pushMessage error code:%s comment:%s", result.code(), result.comment()));
        }
    }
}