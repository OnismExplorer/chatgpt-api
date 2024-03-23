package com.onism.chatgpt.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 响应正文
 *
 * @author HeXin
 * @date 2024/03/20
 */
@NoArgsConstructor
@Data
public class ResponseBody {
    /**
     * 编号
     */
    private String id;
    /**
     * 对象
     */
    private String object;
    /**
     * 会话创建时间戳
     */
    private Integer created;
    /**
     * 模型
     */
    private String model;
    /**
     * 系统指纹
     */
    private String systemFingerprint;
    /**
     * 选择
     */
    private List<ChoicesDTO> choices;
    private UsageDTO usage;
}
