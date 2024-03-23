package com.onism.chatgpt.constant;

/**
 * 模型枚举
 *
 * @author HeXin
 * @date 2024/03/20
 */
public enum ModelEnum {
    /**
     * GPT4 0125 预览
     */
    GPT4_0125_PREVIEW("gpt-4-0125-preview"),

    /**
     * GPT4 Turbo 预览
     */
    GPT4_TURBO_PREVIEW("gpt-4-turbo-preview"),

    /**
     * GPT4 具有理解图像的能力，以及所有其他 GPT4 Turbo 功能。
     */
    GPT4_1106_VERSION_PREVIEW("gpt-4-1106-vision-preview"),
    /**
     * GPT 3.5 Turbo
     * 最新的 GPT-3.5 Turbo 型号在响应请求的格式时具有更高的准确性，并修复了导致非英语语言函数调用的文本编码问题的错误。
     * 最多返回 4,096 个输出令牌
     */
    GPT3_TURBO_0125("gpt-3.5-turbo-0125"),
    /**
     * GPT 3 Turbo 1106
     * 具有改进的指令跟随、JSON 模式、可重复的输出、并行函数调用等。最多返回 4,096 个输出令牌。
     */
    GPT3_TURBO_1106("gpt-3.5-turbo-1106"),
    /**
     * dall-e-2<br>
     * 图片模型<br>
     * The 2nd iteration of DALL·E 具有比原始模型更逼真、更准确且分辨率更高的 4 倍的图像。
     */
    DALL_E_2("dall-e-2"),
    /**
     * dall-e-3<br>
     * 图片模型<br>
     * 最新 DALL·E车型于2023年11月发布,需要plus用户
     */
    DALL_E_3("dall-e-3"),
    /**
     * TTS 1<br>
     * 音频模型<br>
     * 最新的文本转语音模型，针对速度进行了优化。
     */
    TTS_1("tts-1"),
    /**
     * TTS 1 高清<br>
     * 音频模型<br>
     * 最新的文本转语音模型，针对质量进行了优化。
     */
    TTS_1_HD("tts-1-hd")
    ;

    /**
     * 值
     */
    private String value;

    public String value() {
        return value;
    }

    ModelEnum(String value) {
        this.value = value;
    }
}
