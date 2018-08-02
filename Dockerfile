FROM tomcat
RUN apt-get update && apt-get -y upgrade
RUN rm -rf /usr/local/tomcat/webapps/*
COPY build/libs/ROOT.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080


CMD ["catalina.sh", \
    "run", \
    "-Dhibernate.url=$HIBERNATE_URL", \
    "-Dhibernate.username=$HIBERNATE_USERNAME",\
    "-Dhibernate.password=$HIBERNATE_PASSWORD"]