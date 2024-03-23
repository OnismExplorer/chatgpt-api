package com.onism.chatgpt.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 图像 DTO
 *
 * @author HeXin
 * @date 2024/03/21
 */
@NoArgsConstructor
@Data
public class ImageDTO {

    /**
     * 模型
     */
    @NotNull
    private String model;
    /**
     * 提示词(用户生成图像要求)
     */
    @NotNull
    private String prompt;
    /**
     * 生成图片数量，必须介于 1 和 10 之间
     */
    @NotNull
    private Integer n;
    /**
     * 图片大小(单位：像素)
     */
    @NotNull
    private String size;

    public ImageDTO(@NotNull String model, @NotNull String prompt,@NotNull  Integer n, @NotNull String size) {
        this.model = model;
        this.prompt = prompt;
        this.n = n;
        this.size = size;
    }

    public void setModel(@NotNull String model) {
        this.model = model;
    }

    public void setPrompt(@NotNull String prompt) {
        this.prompt = prompt;
    }

    public void setN(@NotNull Integer n) {
        this.n = n;
    }

    public void setSize(@NotNull String size) {
        this.size = size;
    }
}
