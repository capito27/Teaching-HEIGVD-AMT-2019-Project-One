FROM payara/server-full:5.183

MAINTAINER Olivier Liechti <olivier.liechti@heig-vd.ch>

ADD target/Project-One.war /opt/payara5/glassfish/domains/domain1/autodeploy/Project-One.war
RUN curl http://repo1.maven.org/maven2/mysql/mysql-connector-java/5.1.46/mysql-connector-java-5.1.46.jar -o glassfish/lib/mysql-connector-java-5.1.46.jar

#COPY drivers/domain.xml glassfish/domains/domain1/config/domain.xml

#ENTRYPOINT ${PAYARA_PATH}/generate_deploy_commands.sh && echo 'create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.Driver --restype java.sql.Driver --property user=root:password=root:URL=jdbc:mysql://db:3306/amt:useSSL=false:allowPublicKeyRetrieval=true amt-pool' > mycommands.asadmin && cat ${DEPLOY_COMMANDS} >> mycommands.asadmin && ${PAYARA_PATH}/bin/asadmin start-domain -v --postbootcommandfile mycommands.asadmin ${PAYARA_DOMAIN}
ENTRYPOINT ${PAYARA_PATH}/generate_deploy_commands.sh && \
  echo 'create-jdbc-connection-pool --driverclassname com.mysql.jdbc.Driver --restype java.sql.Driver --property user=root:password=root:URL=jdbc\:mysql\://db\:3306/amt:useSSL=false:allowPublicKeyRetrieval=true amt_pool' >> mycommands.asadmin && \
  echo 'create-jdbc-resource --connectionpoolid amt_pool jdbc/app' >> mycommands.asadmin && \
  cat ${DEPLOY_COMMANDS} >> mycommands.asadmin && \
  ${PAYARA_PATH}/bin/asadmin start-domain -v --postbootcommandfile mycommands.asadmin ${PAYARA_DOMAIN}