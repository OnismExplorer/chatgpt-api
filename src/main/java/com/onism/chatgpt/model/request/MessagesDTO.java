package com.onism.chatgpt.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * 请求消息 DTO
 *
 * @author HeXin
 * @date 2024/03/20
 */
@NoArgsConstructor
@Data
public class MessagesDTO {
    /**
     * 角色
     */
    @NotNull
    private String role;
    /**
     * 消息内容
     */
    @NotNull
    private String content;

    public MessagesDTO(@NonNull String role, @NonNull String content) {
        this.role = role;
        this.content = content;
    }

    public void setRole(@NotNull String role) {
        this.role = role;
    }

    public void setContent(@NotNull String content) {
        this.content = content;
    }
}
