package com.onism.chatgpt.service;

import cn.hutool.json.JSONUtil;
import com.onism.chatgpt.constant.AudioEnum;
import com.onism.chatgpt.constant.ModelEnum;
import com.onism.chatgpt.constant.URLEnum;
import com.onism.chatgpt.model.request.AudioDTO;
import com.onism.chatgpt.model.request.ImageDTO;
import com.onism.chatgpt.model.request.MessagesDTO;
import com.onism.chatgpt.model.request.RequestBody;
import com.onism.chatgpt.model.response.Image;
import com.onism.chatgpt.model.response.ResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ChatGPT 调用
 *
 * @author HeXin
 * @date 2024/03/20
 */
@Slf4j
@Component
public class OpenAI {
    /**
     * HTTP 客户端
     */
    private static final CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    private static final List<String> IMAGE_RULES = new ArrayList<>();
    private static String key;
    private static HttpPost post;

    static {
        IMAGE_RULES.add("256x256");
        IMAGE_RULES.add("512x512");
        IMAGE_RULES.add("1024x1024");
        IMAGE_RULES.add("1024x1792");
        IMAGE_RULES.add("1792x1024");
    }

    private OpenAI() {

    }

    /**
     * 初始化
     */
    private static void init(String url) {
        post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer " + key);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @return {@link String} 结果消息
     * @throws IOException IO异常
     */
    public static String sendMessage(String message) throws IOException {
        // 默认使用 gpt-3.5-turbo-0125
        return sendMessage(message, ModelEnum.GPT3_TURBO_0125);
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @param model   型
     * @return {@link String} 结果信息
     * @throws IOException IO异常
     */
    public static String sendMessage(String message, ModelEnum model) throws IOException {
        // 初始化
        if (post == null) {
            init(URLEnum.MESSAGE_URL.url());
        }
        // 创建请求体
        RequestBody request = new RequestBody();

        // 设置模型
        request.setModel(model.value());

        // 设置消息
        List<MessagesDTO> dtoList = new ArrayList<>();
        dtoList.add(new MessagesDTO("user", message));
        request.setMessages(dtoList);
        request.setTemperature(0.7);

        return sendMessage(request);
    }

    /**
     * 发送短消息
     *
     * @param body 身体
     * @return {@link String}
     * @throws IOException IO异常
     */
    public static String sendMessage(RequestBody body) throws IOException {
        // 初始化
        if (post == null) {
            init(URLEnum.MESSAGE_URL.url());
        }
        String str = getResultString(JSONUtil.toJsonStr(body));
        ResponseBody response = JSONUtil.toBean(str, ResponseBody.class);
        return response.getChoices().get(0).getMessage().getContent();
    }

    /**
     * 获取结果字符串
     *
     * @param body 身体
     * @return {@link String}
     * @throws IOException IO异常
     */
    private static String getResultString(String body) throws IOException {
        return EntityUtils.toString(getResult(body));
    }

    /**
     * 获取结果
     *
     * @param body 身体
     * @return {@link HttpEntity}
     * @throws IOException io异常
     */
    private static HttpEntity getResult(String body) throws IOException {
        // 设置字符串实体
        post.setEntity(new StringEntity(body, ContentType.create("text/json", "UTF-8")));
        // 执行请求
        CloseableHttpResponse httpResponse = httpClient.execute(post);
        if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new RuntimeException("执行失败，请检查后重试！");
        }
        return httpResponse.getEntity();
    }

    /**
     * 创建图片
     *
     * @param content 图片描述
     * @return {@link String} 图片url地址
     * @throws IOException IO异常
     */
    public static String createImage(String content) throws IOException {
        // 默认使用 dall-e-2
        return createImage(content, ModelEnum.DALL_E_2);
    }

    /**
     * 创建映像
     *
     * @param content   内容
     * @param modelEnum 模型枚举
     * @return {@link String}
     * @throws IOException IO异常
     */
    public static String createImage(String content, ModelEnum modelEnum) throws IOException {
        // 初始化
        if (post == null) {
            init(URLEnum.IMAGE_URL.url());
        }
        ImageDTO imageDTO = new ImageDTO();
        // 默认生成一张
        imageDTO.setN(1);
        // 默认为 1024x1024
        imageDTO.setSize("1024x1024");
        imageDTO.setPrompt(content);
        imageDTO.setModel(modelEnum.value());

        return createImage(imageDTO);
    }

    /**
     * 创建图片
     *
     * @param dto
     * @return {@link String}
     * @throws IOException IO异常
     */
    public static String createImage(ImageDTO dto) throws IOException {
        // 初始化
        if (post == null) {
            init(URLEnum.IMAGE_URL.url());
        }
        // 判断图片长宽是否符合规定
        if (!IMAGE_RULES.contains(dto.getSize())) {
            throw new RuntimeException("图片大小必须为：256x256，512x512，1024x1024，1024x1792，1792x1024其中之一");
        }
        // 设置字符串实体
        String str = getResultString(JSONUtil.toJsonStr(dto));
        Image image = JSONUtil.toBean(str, Image.class);
        if (image.getData().size() > 1) {
            return JSONUtil.toJsonStr(image.getData());
        }
        return image.getData().get(0).getUrl();
    }

    /**
     * 创建映像
     *
     * @param content 内容
     * @param model   模型
     * @param n       生成图片数量
     * @param height  高度
     * @param width   宽度
     * @return {@link String}
     * @throws IOException IO异常
     */
    public static String createImage(String content, ModelEnum model, int n, int height, int width) throws IOException {
        // 初始化
        if (post == null) {
            init(URLEnum.IMAGE_URL.url());
        }
        // 判断图片长宽是否符合规定
        String size = width + "x" + height;
        if (!IMAGE_RULES.contains(size)) {
            throw new RuntimeException("图片大小必须为：256x256，512x512，1024x1024，1024x1792，1792x1024其中之一");
        }
        ImageDTO dto = new ImageDTO();
        dto.setModel(model.value());
        dto.setN(n);
        dto.setSize(size);
        dto.setPrompt(content);
        return createImage(dto);
    }

    /**
     * 文本转换音频(暂时只支持英文)
     *
     * @param input          输入文本
     * @param outputFilePath 输出文件路径
     * @param fileName       文件名(需要包含后缀名，mp3，opus，aac，flac，wav，pcm其中之一)
     * @throws IOException io异常
     */
    public static void convertAudio(String input, String outputFilePath, String fileName) throws IOException {
        // 默认使用 tts-1 模型
        convertAudio(input, outputFilePath, fileName, ModelEnum.TTS_1);
    }

    /**
     * 文本转换音频(暂时只支持英文)
     *
     * @param input     输入
     * @param modelEnum 模型枚举
     */
    public static void convertAudio(String input, String outputFilePath, String fileName, ModelEnum modelEnum) throws IOException {
        // 初始化
        if (post == null) {
            init(URLEnum.AUDIO_URL.url());
        }
        // 新建音频对象
        AudioDTO audioDTO = new AudioDTO();
        // 音速默认为 1.0
        audioDTO.setSpeed(1.0);
        audioDTO.setModel(modelEnum.value());
        audioDTO.setInput(input);
        // 默认为 alloy 男声
        audioDTO.setVoice(AudioEnum.ALLOY.name().toLowerCase(Locale.ENGLISH));
        // 获取响应结果
        HttpEntity entity = getResult(JSONUtil.toJsonStr(audioDTO));
        if (entity != null) {
            String filePath = outputFilePath + "\\" + fileName;
            try (
                InputStream inputStream = entity.getContent();
                 FileOutputStream outputStream = new FileOutputStream(filePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            // 消耗响应结果
            EntityUtils.consume(entity);
            log.info("转换成功，结果已放置：{}",filePath);
            return;
        }
        log.error("转换失败，请检查后重试！");
    }

    @Value("${chatgpt.api.key}")
    public void setKey(String key) {
        OpenAI.key = key;
    }
}
