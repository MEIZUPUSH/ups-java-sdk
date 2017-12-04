package com.meizu.ups.sdk.server.constant;
/**
 * User: jasperxgwang
 * Date: 2017-11-1 16:44
 */

import java.util.HashMap;
import java.util.Map;

public enum CP {

    MEIZU(1, "魅族"),
    XIAOMI(2, "小米"),
    HUAWEI(3, "华为");


    private Integer key;
    private String value;

    CP(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    private static final Map<Integer, CP> ENUMMAP = new HashMap<Integer, CP>();

    static {
        for (CP cp : CP.values()) {
            ENUMMAP.put(cp.getKey(), cp);
        }
    }

    public static CP fromValue(Integer key) {
        return ENUMMAP.get(key);
    }
}
