package com.onism.chatgpt.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模型选择 DTO
 *
 * @author HeXin
 * @date 2024/03/20
 */
@NoArgsConstructor
@Data
public class ChoicesDTO {
    /**
     * 指数
     */
    private Integer index;
    /**
     * 响应消息
     */
    private MessageDTO message;
    /**
     * logprobs 日志：是否返回输出令牌的对数概率。如果为 true，则返回 中返回的每个输出标记的对数概率。
     */
    private Object logprobs;
    /**
     * 完成原因
     */
    private String finishReason;
}
