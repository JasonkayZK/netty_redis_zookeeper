package com.crazymakercircle.zk.publishSubscribe;

import com.crazymakercircle.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Slf4j
@Data
public class ConfigManger {

    public static ConfigManger instance = new ConfigManger();

    private ConfigManger() {
    }

    private Map<String, ConfigItem> itemMap =
            new LinkedHashMap<>();

    public void setType(String t, byte[] payload) {
        String suffix = path2type(t);
        if (null == suffix) {
            return;
        }
        ConfigItem i =
                JsonUtil.JsonBytes2Object(payload, ConfigItem.class);
        itemMap.put(suffix, i);
    }

    public void removeType(String t) {
        String suffix = path2type(t);
        if (null == suffix) {
            return;
        }
        itemMap.remove(suffix);
    }

    public String getConfigValue(String t, String key) {
        ConfigItem i = itemMap.get(t);
        if (i == null) {
            return null;
        }
        return i.getValue(key);
    }

    private String path2type(String str) {
        int index = str.lastIndexOf("/");
        if (index >= 0) {
            index++;
            return index < str.length() ? str.substring(index) : null;
        }
        return null;
    }

}
