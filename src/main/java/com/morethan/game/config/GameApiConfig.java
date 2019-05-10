package com.morethan.game.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述:用于请求gameApi的商户properties
 *
 * @outhor anthony
 * @create 2018-11-15 上午11:34
 */
@ConfigurationProperties(prefix = "game-api")
public class GameApiConfig {

    private static String url;
    private static String secretId;
    private static String secretKey;

    public static String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String getSecretId() {
        return secretId;
    }

    public void setSecretId(String secretId) {
        this.secretId = secretId;
    }

    public static String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
