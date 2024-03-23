package com.onism.chatgpt.constant;

/**
 * URL地址枚举
 *
 * @author HeXin
 * @date 2024/03/22
 */
public enum URLEnum {
    /**
     * 消息url
     */
    MESSAGE_URL("https://api.openai.com/v1/chat/completions"),
    IMAGE_URL("https://api.openai.com/v1/images/generations"),
    AUDIO_URL("https://api.openai.com/v1/audio/speech"),
    ;
    /**
     * url地址
     */
    private final String url;

    URLEnum(String url) {
        this.url = url;
    }

    public String url(){
        return url;
    }
}
