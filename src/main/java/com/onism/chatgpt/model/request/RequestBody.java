package com.onism.chatgpt.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 请求正文
 *
 * @author HeXin
 * @date 2024/03/20
 */

@Data
public class RequestBody {

    /**
     * 模型
     */
    @NotNull
    private String model;
    /**
     * 消息
     */
    @NotNull
    private List<MessagesDTO> messages;
    /**
     * 温度<br>
     * 采样温度，介于 0 和 1 之间。较高的值（如 0.8）将使输出更加随机，而较低的值（如 0.2）将使其更加集中和确定。<br>
     * 如果设置为 0，则模型将使用对数概率自动升高温度，直到达到某些阈值。
     */
    private Double temperature;

    public RequestBody() {
    }

    public RequestBody(@NotNull String model,@NotNull List<MessagesDTO> messages, Double temperature) {
        this.model = model;
        this.messages = messages;
        this.temperature = temperature;
    }

    public void setModel(@NotNull String model) {
        this.model = model;
    }

    public void setMessages(@NotNull List<MessagesDTO> messages) {
        this.messages = messages;
    }
}
