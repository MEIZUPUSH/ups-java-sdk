package com.meizu.ups.sdk.vo;

import java.io.Serializable;

/**
 * 通知栏设置
 *
 * @author wangxinguo
 * @date 2016年7月15日
 * @time 下午6:23:18
 */
public class NoticeBarInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 推送标题, 【必填，字数限制1~32】
     */
    private String title = "";
    /**
     * 推送内容, 【必填，字数限制1~100】
     */
    private String content = "";

    public NoticeBarInfo() {
    }

    public NoticeBarInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NoticeBarInfo{" +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
