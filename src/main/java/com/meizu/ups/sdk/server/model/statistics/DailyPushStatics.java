package com.meizu.ups.sdk.server.model.statistics;



import com.meizu.ups.sdk.utils.DateUtils;

import java.util.Date;

/**
 * 应用日统计结果
 */
public class DailyPushStatics {

    /**
     * 日期
     */
    private Date date;

    /**
     * 推送数
     */
    private Long pushedNo;

    /**
     * 接受数
     */
    private Long acceptNo;

    /**
     * 点击数
     */
    private Long clickNo;


    public Long getPushedNo() {
        return pushedNo;
    }

    public void setPushedNo(Long pushedNo) {
        this.pushedNo = pushedNo;
    }

    public Long getAcceptNo() {
        return acceptNo;
    }

    public void setAcceptNo(Long acceptNo) {
        this.acceptNo = acceptNo;
    }

    public Long getClickNo() {
        return clickNo;
    }

    public void setClickNo(Long clickNo) {
        this.clickNo = clickNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String dateStr) {
        date = DateUtils.str2Date(dateStr, "yyyy-MM-dd");
    }

    @Override
    public String toString() {
        return "DailyPushStatics{" +
                "date=" + date +
                ", pushedNo=" + pushedNo +
                ", acceptNo=" + acceptNo +
                ", clickNo=" + clickNo +
                '}';
    }
}
