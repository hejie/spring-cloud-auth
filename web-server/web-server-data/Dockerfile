FROM d.onedream.com/omd/jdk8:12121139

WORKDIR /app

MAINTAINER onedream

ADD target/*.jar app.jar

EXPOSE 8089

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar $SPRING_BOOT_ARGS
