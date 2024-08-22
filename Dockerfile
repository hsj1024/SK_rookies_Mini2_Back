FROM openjdk:17
VOLUME /tmp
COPY target/rookies_talk-0.0.1-SNAPSHOT.jar rookies_talk-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/rookies_talk-0.0.1-SNAPSHOT.jar"]