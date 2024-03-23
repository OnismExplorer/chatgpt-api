package com.onism.chatgpt;

import com.onism.chatgpt.constant.AudioEnum;
import com.onism.chatgpt.constant.ModelEnum;
import com.onism.chatgpt.model.request.RequestBody;
import com.onism.chatgpt.service.OpenAI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.Locale;

@SpringBootTest
class ChatgptApiApplicationTests {

    @Test
    void testMessage() throws IOException {
        System.out.println(OpenAI.sendMessage("你好"));
    }

    @Test
    public void testAudio(){
        String openaiApiKey = "YOUR_KEY";
        String apiUrl = "https://api.openai.com/v1/audio/speech";
        String inputText = "There was a white dog lying on the lawn, basking in the warm sun";
        String voice = AudioEnum.NOVA.name().toLowerCase(Locale.ENGLISH);
        String outputFilePath = "E:\\speech.mp3";

        CloseableHttpClient  httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setHeader("Authorization", "Bearer " + openaiApiKey);
        httpPost.setHeader("Content-Type", "application/json");

        try {
            String jsonInputString = "{\"model\": \"tts-1\", \"input\": \"" + inputText + "\", \"voice\": \"" + voice + "\"}";
            StringEntity jsonEntity = new StringEntity(jsonInputString);
            httpPost.setEntity(jsonEntity);

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = getEntity(response, outputFilePath);

            EntityUtils.consume(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testPicture(){
        String openaiApiKey = "YOUR_KEY";
        String apiUrl = "https://api.openai.com/v1/images/generations";
        String prompt = "一个小女孩坐在草坪上看夕阳";
        int n = 1;
        String size = "1024x1024";

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setHeader("Authorization", "Bearer " + openaiApiKey);
        httpPost.setHeader("Content-Type", "application/json");

        try {
            String jsonInputString = "{\"model\": \"dall-e-2\", \"prompt\": \"" + prompt + "\", \"n\": " + n + ", \"size\": \"" + size + "\"}";
            httpPost.setEntity(new StringEntity(jsonInputString,ContentType.create("text/json", "UTF-8")));
            HttpResponse response = httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                System.out.println(response.getStatusLine().getStatusCode());
            }
            String str = EntityUtils.toString(response.getEntity());
            System.out.println(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testAPI() throws IOException {
//        System.out.println(OpenAI.createImage("一只雄鹰在天空展翅翱翔", ModelEnum.DALL_E_2, 1, 1024, 1024));
        OpenAI.convertAudio("Can you help me ?","E:\\","help.mp3");
    }


    private static HttpEntity getEntity(HttpResponse response, String outputFilePath) throws IOException {
        HttpEntity entity = response.getEntity();

        if (entity != null) {
            try (InputStream inputStream = entity.getContent();
                 FileOutputStream outputStream = new FileOutputStream(outputFilePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        }
        return entity;
    }
}
