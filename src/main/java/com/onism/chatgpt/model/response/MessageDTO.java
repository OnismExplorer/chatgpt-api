package com.onism.chatgpt.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应消息 DTO
 *
 * @author HeXin
 * @date 2024/03/20
 */
@NoArgsConstructor
@Data
public class MessageDTO {
    /**
     * 角色
     */
    private String role;
    /**
     * 内容
     */
    private String content;
}
