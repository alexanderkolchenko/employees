FROM tomcat:9-alpine
ADD /target/employees-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

COPY lib /usr/local/tomcat/lib

EXPOSE 8080

CMD ["catalina.sh", "run"]