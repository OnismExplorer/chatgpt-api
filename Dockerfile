# 基础镜像
FROM openjdk:17

# 作者信息
LABEL maintainer="HeXin"

# 设置时区
ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/"$TZ" /etc/localtime && echo "$TZ" > /etc/timezone

# 设置工作目录
WORKDIR /app

# 添加jar包到容器中
COPY /chatgpt-api.jar /app/chatgpt-api.jar

# 暴露端口
EXPOSE 8088

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/chatgpt-api.jar"]

# 健康检查
HEALTHCHECK --interval=30s --timeout=5s --start-period=5s --retries=3 CMD curl -f http://127.0.0.1:8088/actuator/health || exit 1