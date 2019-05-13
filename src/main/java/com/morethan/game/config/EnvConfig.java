package com.morethan.game.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 张良
 */
@ConfigurationProperties(prefix = "env")
public class EnvConfig {

    private static Boolean needSign;
    private static Boolean dev;


    public static Boolean getNeedSign() {
        return needSign;
    }

    public void setNeedSign(Boolean needSign) {
        this.needSign = needSign;
    }

    public static Boolean getDev() {
        return dev;
    }

    public void setDev(Boolean dev) {
        EnvConfig.dev = dev;
    }
}
