package com.simpleplugins.simpletablist;

import com.simpleplugins.simpletablist.util.JsonConfig;

public class SimpleTablist {
    private static JsonConfig jsonConfig;

    public static void setJsonConfig(JsonConfig jsonConfig) {
        SimpleTablist.jsonConfig = jsonConfig;
    }

    public static JsonConfig getJsonConfig() {
        return jsonConfig;
    }
}
