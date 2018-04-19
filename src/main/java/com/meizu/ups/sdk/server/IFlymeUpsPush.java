package com.meizu.ups.sdk.server;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meizu.ups.sdk.constant.PushType;
import com.meizu.ups.sdk.constant.SystemConstants;
import com.meizu.ups.sdk.server.constant.ResultPack;
import com.meizu.ups.sdk.server.model.HttpResult;
import com.meizu.ups.sdk.server.model.push.Message;
import com.meizu.ups.sdk.server.model.push.PushResult;
import com.meizu.ups.sdk.server.model.push.UnVarnishedMessage;
import com.meizu.ups.sdk.server.model.push.VarnishedMessage;
import com.meizu.ups.sdk.server.model.statistics.DailyPushStatics;
import com.meizu.ups.sdk.utils.CollectionUtils;
import com.meizu.ups.sdk.utils.DateUtils;
import com.meizu.ups.sdk.utils.HttpClient;
import com.meizu.ups.sdk.utils.StringUtils;
import com.meizu.ups.sdk.vo.AdvanceInfo;
import com.meizu.ups.sdk.vo.ClickTypeInfo;
import com.meizu.ups.sdk.vo.NoticeBarInfo;
import com.meizu.ups.sdk.vo.NotificationType;
import com.meizu.ups.sdk.vo.PushTimeInfo;
import com.meizu.ups.sdk.vo.UnVarnishedMessageJson;
import com.meizu.ups.sdk.vo.VarnishedMessageJson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

/**
 * @author wangxinguo <wangxinguo@meizu.com>
 * @version 1.0
 * @class IFlymeUpsPush
 * @date 2016-8-23 14:07
 */
public class IFlymeUpsPush extends HttpClient {

    private static final String SUCCESS_CODE = "200";

    public IFlymeUpsPush(String appSecret) {
        super(appSecret);
    }

    public IFlymeUpsPush(String appSecret, boolean useSSL) {
        super(appSecret, useSSL);
    }

    /**
     * 通知栏推送 不重试
     *
     * @param message
     * @param pushIds
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessage(VarnishedMessage message, List<String> pushIds) throws IOException {
        return this.pushMessage(message, pushIds, 0);
    }


    /**
     * 通知栏推送 可重试
     *
     * @param message 推送通知栏消息
     * @param pushIds 推送目标用户
     * @param retries 重试次数
     * @return 推送结果
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessage(VarnishedMessage message, List<String> pushIds, int retries) throws IOException {
        if (CollectionUtils.isEmpty(pushIds)) {
            return ResultPack.failed("pushIds is empty");
        }
        if (message == null) {
            return ResultPack.failed("message is null");
        }
        String pushIdStr = CollectionUtils.list2Str(pushIds);
        return this.pushMessage(UserType.PUSHID, PushType.STATUSBAR, message, pushIdStr, retries);
    }

    /**
     * 别名通知栏推送 不重试
     *
     * @param message
     * @param alias
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessageByAlias(VarnishedMessage message, List<String> alias) throws IOException {
        return this.pushMessageByAlias(message, alias, 0);
    }


    /**
     * 别名通知栏推送 可重试
     *
     * @param message 推送通知栏消息
     * @param alias
     * @param retries 重试次数
     * @return 推送结果
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessageByAlias(VarnishedMessage message, List<String> alias, int retries) throws IOException {
        if (CollectionUtils.isEmpty(alias)) {
            return ResultPack.failed("alias is empty");
        }
        if (message == null) {
            return ResultPack.failed("message is null");
        }
        String aliasStr = CollectionUtils.list2Str(alias);
        return this.pushMessage(UserType.ALIAS, PushType.STATUSBAR, message, aliasStr, retries);
    }

    /**
     * 透传推送 不重试
     *
     * @param message 推送透传消息
     * @param pushIds 推送目标用户
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessage(UnVarnishedMessage message, List<String> pushIds) throws IOException {
        return this.pushMessage(message, pushIds, 0);
    }

    /**
     * 透传推送 可重试
     *
     * @param message 推送透传消息
     * @param pushIds 推送目标用户
     * @param retries 重试次数
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessage(UnVarnishedMessage message, List<String> pushIds, int retries) throws IOException {
        if (CollectionUtils.isEmpty(pushIds)) {
            return ResultPack.failed("pushIds is empty");
        }
        if (message == null) {
            return ResultPack.failed("message is null");
        }
        String pushIdStr = CollectionUtils.list2Str(pushIds);
        return this.pushMessage(UserType.PUSHID, PushType.DIRECT, message, pushIdStr, retries);
    }

    /**
     * 别名透传消息推送
     *
     * @param message 推送消息
     * @param alias   别名集合
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessageByAlias(UnVarnishedMessage message, List<String> alias) throws IOException {
        return this.pushMessageByALias(message, alias, 0);
    }

    /**
     * 别名透传消息推送
     *
     * @param message 推送消息
     * @param alias   别名集合
     * @param retries 失败重试次数
     * @return
     * @throws IOException
     */
    public ResultPack<PushResult> pushMessageByALias(UnVarnishedMessage message, List<String> alias, int retries) throws IOException {
        if (CollectionUtils.isEmpty(alias)) {
            return ResultPack.failed("alias is empty");
        }
        if (message == null) {
            return ResultPack.failed("message is null");
        }
        String aliasStr = CollectionUtils.list2Str(alias);
        return this.pushMessage(UserType.ALIAS, PushType.DIRECT, message, aliasStr, retries);
    }


    public ResultPack<List<DailyPushStatics>> dailyPushStatics(long appId, Date startTime, Date endTime) throws IOException {

        String _url = SystemConstants.GET_PUSH_DAILY_STATICS;

        StringBuilder body = newBody("appId", String.valueOf(appId));
        addParameter(body, "startTime", DateUtils.date2String(startTime, "yyyyMMdd"));
        addParameter(body, "endTime", DateUtils.date2String(endTime, "yyyyMMdd"));

        HttpResult httpResult = super.post(useSSL, _url, body.toString());
        String code = httpResult.getCode();
        String msg = httpResult.getMessage();
        String value = httpResult.getValue();
        List<DailyPushStatics> taskStatistics = new ArrayList<DailyPushStatics>();
        if (SUCCESS_CODE.equals(code)) {
            if (StringUtils.isNotBlank(value)) {
                try {
                    taskStatistics = JSONObject.parseArray(value, DailyPushStatics.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ResultPack.succeed(taskStatistics);
        } else {
            return ResultPack.failed(code, msg);
        }
    }


    private ResultPack<PushResult> pushMessage(UserType userType, PushType pushType, Message message, String targets, int retries) throws IOException {
        int attempt = 0;
        ResultPack<PushResult> result;
        int backoff = 1000;
        boolean tryAgain;
        do {
            ++attempt;
            if (logger.isLoggable(Level.FINE)) {
                logger.fine(String.format("attempt [%s] to pushMessage [%s] to %s [%s]", attempt, message, userType.getValue(), targets));
            }
            result = this.pushMessageNoRetry(userType, pushType, message, targets);
            tryAgain = result == null && attempt <= retries;
            backoff = getBackoffTime(backoff, tryAgain);
        } while (tryAgain);
        if (result == null) {
            throw new IOException(String.format("Could not send message after [%s] attempts", attempt));
        } else {
            return result;
        }
    }

    private int getBackoffTime(int backoff, boolean tryAgain) {
        if (tryAgain) {
            int sleepTime = backoff / 2 + this.random.nextInt(backoff);
            this.sleep((long) sleepTime);
            if (2 * backoff < 60000) {
                backoff *= 2;
            }
        }
        return backoff;
    }

    private ResultPack<PushResult> pushMessageNoRetry(UserType userType, PushType pushType, Message message, String targets) throws IOException {
        String _url = null;
        StringBuilder body = null;
        if (UserType.PUSHID == userType) {
            body = newBody("pushIds", targets);
        } else if (UserType.ALIAS == userType) {
            body = newBody("alias", targets);
        }

        Long appId = message.getAppId();
        if (appId != null && appId > 0) {
            addParameter(body, "appId", String.valueOf(appId));
        } else {
            return ResultPack.failed("appId is empty");
        }
        if (PushType.DIRECT == pushType) {
            UnVarnishedMessage msgInfo = (UnVarnishedMessage) message;

            PushTimeInfo pushTimeInfo = new PushTimeInfo(msgInfo.isOffLine(), msgInfo.getValidTime());

            UnVarnishedMessageJson messageJson = new UnVarnishedMessageJson(msgInfo.getTitle(), msgInfo.getContent(), pushTimeInfo);
            addParameter(body, "messageJson", JSON.toJSONString(messageJson));

            if (UserType.PUSHID == userType) {
                _url = SystemConstants.PUSH_APPID_UNVARNISHED_PUSHIDS;
            } else if (UserType.ALIAS == userType) {
                _url = SystemConstants.PUSH_APPID_UNVARNISHED_ALIAS;
            }
        } else if (PushType.STATUSBAR == pushType) {
            VarnishedMessage msgInfo = (VarnishedMessage) message;

            NoticeBarInfo noticeBarInfo = new NoticeBarInfo(msgInfo.getTitle(), msgInfo.getContent());
            ClickTypeInfo clickTypeInfo = new ClickTypeInfo(msgInfo.getClickType(), msgInfo.getUrl(),
                    msgInfo.getParameters(), msgInfo.getActivity(),
                    msgInfo.getCustomAttribute(), msgInfo.getCustomUri());
            PushTimeInfo pushTimeInfo = new PushTimeInfo(msgInfo.isOffLine(), msgInfo.getValidTime());
            NotificationType notificationType = new NotificationType(msgInfo.isVibrate(), msgInfo.isLights(), msgInfo.isSound());
            AdvanceInfo advanceInfo = new AdvanceInfo(msgInfo.isFixSpeed(), msgInfo.getFixSpeedRate(), msgInfo.isSuspend(),
                    msgInfo.isClearNoticeBar(), notificationType, msgInfo.isFixDisplay(), msgInfo.getFixStartDisplayDate(),
                    msgInfo.getFixEndDisplayDate(), msgInfo.getNotifyKey());

            VarnishedMessageJson messageJson = new VarnishedMessageJson(noticeBarInfo,
                    clickTypeInfo, pushTimeInfo, advanceInfo);
            addParameter(body, "messageJson", JSON.toJSONString(messageJson));

            if (UserType.PUSHID == userType) {
                _url = SystemConstants.PUSH_APPID_VARNISHED_PUSHIDS;
            } else if (UserType.ALIAS == userType) {
                _url = SystemConstants.PUSH_APPID_VARNISHED_ALIAS;
            }
        }

        HttpResult httpResult = super.post(useSSL, _url, body.toString());
        if (httpResult == null) {
            return null;
        }

        String code = httpResult.getCode();
        String msg = httpResult.getMessage();
        String value = httpResult.getValue();
        if (SUCCESS_CODE.equals(code)) {
            PushResult respTarget = new PushResult();
            if (StringUtils.isNotBlank(value)) {
                respTarget = JSONObject.parseObject(value, PushResult.class);
            }
            return ResultPack.succeed(code, msg, respTarget);
        } else {
            return ResultPack.failed(code, msg);
        }
    }
    
    /**
     * 应用全网推送
     *
     * @param pushType
     * @param message
     * @throws IOException
     * @return
     */
    public ResultPack<Long> pushToApp(PushType pushType, Message message) throws IOException {
        String _url = SystemConstants.PUSH_APPID_PUSH_TO_APP;
        if (pushType == null) {
            return ResultPack.failed("pushType is null");
        }
        if (!pushType.equals(PushType.STATUSBAR)) {
			return ResultPack.failed("pushType is error");
		}
        if (message == null) {
            return ResultPack.failed("message is null");
        }
        Long appId = message.getAppId();
        if (appId == null) {
            return ResultPack.failed("appId is null");
        }

        StringBuilder body = newBody("pushType", String.valueOf(pushType.getDesc()));
        addParameter(body, "appId", String.valueOf(appId));

        if (PushType.STATUSBAR == pushType) {
            if (!(message instanceof VarnishedMessage)) {
                return ResultPack.failed("message must be instanceof VarnishedMessage");
            }
            VarnishedMessage msgInfo = (VarnishedMessage) message;

            NoticeBarInfo noticeBarInfo = new NoticeBarInfo(msgInfo.getTitle(), msgInfo.getContent());
            ClickTypeInfo clickTypeInfo = new ClickTypeInfo(msgInfo.getClickType(), msgInfo.getUrl(),
                    msgInfo.getParameters(), msgInfo.getActivity(),
                    msgInfo.getCustomAttribute(), msgInfo.getCustomUri());
            String startTime = "";
            if (msgInfo.getStartTime() != null) {
                startTime = DateUtils.date2String(msgInfo.getStartTime());
            }
            PushTimeInfo pushTimeInfo = new PushTimeInfo(msgInfo.isOffLine(), msgInfo.getValidTime(), msgInfo.getPushTimeType(), startTime);
            NotificationType notificationType = new NotificationType(msgInfo.isVibrate(), msgInfo.isLights(), msgInfo.isSound());
            AdvanceInfo advanceInfo = new AdvanceInfo(msgInfo.isFixSpeed(), msgInfo.getFixSpeedRate(), msgInfo.isSuspend(),
                    msgInfo.isClearNoticeBar(), notificationType, msgInfo.isFixDisplay(), msgInfo.getFixStartDisplayDate(),
                    msgInfo.getFixEndDisplayDate(), msgInfo.getNotifyKey());

            VarnishedMessageJson messageJson = new VarnishedMessageJson(noticeBarInfo,
                    clickTypeInfo, pushTimeInfo, advanceInfo);
            addParameter(body, "messageJson", JSON.toJSONString(messageJson));
        } else {
        	return ResultPack.failed("pushType is invalid");
        }

        HttpResult httpResult = super.post(useSSL, _url, body.toString());
        if (httpResult == null) {
            return null;
        }
        String code = httpResult.getCode();
        String msg = httpResult.getMessage();
        String value = httpResult.getValue();
        if (SUCCESS_CODE.equals(code)) {
            JSONObject objValue = JSON.parseObject(value);
            if (objValue.containsKey("taskId")) {
                Long taskId = objValue.getLong("taskId");
                return ResultPack.succeed(code, msg, taskId);
            } else {
                return ResultPack.failed("error return value");
            }
        } else {
            return ResultPack.failed(code, msg);
        }
    }


    enum UserType {

        PUSHID(0, "pushId"), ALIAS(1, "alias");
        private Integer key;
        private String value;

        UserType(Integer key, String value) {
            this.key = key;
            this.value = value;
        }

        public Integer getKey() {
            return key;
        }

        public void setKey(Integer key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
