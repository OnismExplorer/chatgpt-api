# 基于 OpenAI 给出的API封装的一个工具 Demo
这其中包括了ChatGPT的普通对话问答、图片生成，以及文本转语音等功能。

-----
本项目为 SpringBoot 3.2.3 项目，引入了 hutool 等工具类。<br>
通过引入 Apache 的 HttpClient 库，实现对 OpenAI API 的调用。<br>
将请求体参数封装为 RequestBody 对象，并使用 HttpClient 发送请求。<br>
调用 OpenAI 的 api 生成返回的结果封装之后返回用户需要的结果，去除了其他多余的参数。<br>
当前实现的文本转音频的 api 功能`仅限于英语，中文无法正常流畅播放`(放出来有点奇怪，是中文标点的问题)。<br>
引入项目时，先在 application.yml 文件中配置好 OpenAI 的秘钥Key。