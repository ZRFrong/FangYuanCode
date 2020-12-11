#这是基础镜像
FROM openjdk:8-jdk-alpine
#复制jar包到镜像中，并且将名字改成 app.jar
ADD *.jar tcp.jar
# 在容器启动的时候运行命里，来启动我们的项目 （这其实就是一段Linux命令）
# 这个启动命里根据你的实际情况更改
ENTRYPOINT ["java","-jar","/tcp.jar"]
#日志中文乱码
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8