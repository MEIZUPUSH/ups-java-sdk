package com.meizu.ups.sdk.constant;

import java.util.HashMap;
import java.util.Map;

public enum ClickType {
    APP(0, "打开应用"),
    ACTIVITY(1, "打开应用页面"),
    URI(2, "打开URI页面"),
    CUSTOM_ATTRIBUTE(3, "应用客户端自定义"),
    CUSTOM_URI(4, "打开自定Intent URI");

    private Integer desc;
    private String value;

    ClickType(Integer desc, String value) {
        this.desc = desc;
        this.value = value;
    }


    public Integer getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }


    private static final Map<Integer, ClickType> ENUMMAP = new HashMap<Integer, ClickType>();

    static {
        for (ClickType clickType : ClickType.values()) {
            ENUMMAP.put(clickType.getDesc(), clickType);
        }
    }

    public static ClickType fromValue(Integer desc) {
        return ENUMMAP.get(desc);
    }
}
