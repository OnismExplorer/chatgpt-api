package com.onism.chatgpt.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 音频 DTO
 *
 * @author HeXin
 * @date 2024/03/22
 */
@NoArgsConstructor
@Data
public class AudioDTO {
    /**
     * 模型
     */
    @NotNull
    private String model;
    /**
     * 文本(描述)输入<br>
     * 要为其生成音频的文本。最大长度为 4096 个字符。
     */
    @NotNull
    private String input;
    /**
     * 声色选择
     * 参考{@link com.onism.chatgpt.constant.AudioEnum}
     */
    @NotNull
    private String voice;

    /**
     * 速度<br>
     * 生成的音频的速度。有 0.25、1.0、4.0(默认为 1.0)
     */
    private Double speed;

    public AudioDTO(@NotNull String model, @NotNull String input, @NotNull String voice) {
        this.model = model;
        this.input = input;
        this.voice = voice;
    }

    public AudioDTO(@NotNull String model, @NotNull String input, @NotNull String voice, Double speed) {
        this.model = model;
        this.input = input;
        this.voice = voice;
        this.speed = speed;
    }

    public void setModel(@NotNull String model) {
        this.model = model;
    }

    public void setInput(@NotNull String input) {
        this.input = input;
    }

    public void setVoice(@NotNull String voice) {
        this.voice = voice;
    }
}
