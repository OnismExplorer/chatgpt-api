package com.onism.chatgpt.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * token用量
 *
 * @author HeXin
 * @date 2024/03/20
 */
@NoArgsConstructor
@Data
public class UsageDTO {
    /**
     * 提示 token 数
     */
    private Integer promptTokens;
    /**
     * 完成 token 数
     */
    private Integer completionTokens;
    /**
     * 消耗 token 总数
     */
    private Integer totalTokens;
}
