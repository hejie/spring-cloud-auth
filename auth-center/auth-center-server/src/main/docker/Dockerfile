FROM d.onedream.com/omd/jdk8:12121139

VOLUME /tmp

ADD target/*.jar app.jar

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar $SPRING_BOOT_ARGS

EXPOSE 18001