package com.onism.chatgpt.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 生成图片
 *
 * @author HeXin
 * @date 2024/03/21
 */
@NoArgsConstructor
@Data
public class Image {

    /**
     * 创建时间戳
     */
    private Integer created;
    /**
     * 图片数据
     */
    private List<DataDTO> data;
}
