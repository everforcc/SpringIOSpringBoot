echo

set url="180.76.156.43:3308"
set username="cceverfor"
set password="5664c.c."

start "configservice" java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Dserver.port=8080 -Dspring.datasource.url=jdbc:mysql://%url%/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=%username% -Dspring.datasource.password=%password% -jar apollo-configservice-1.3.0.jar
start "adminservice" java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Dserver.port=8090 -Dspring.datasource.url=jdbc:mysql://%url%/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=%username% -Dspring.datasource.password=%password% -jar apollo-adminservice-1.3.0.jar
start "portal" java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Ddev_meta=http://localhost:8080/ -Dserver.port=8070 -Dspring.datasource.url=jdbc:mysql://%url%/ApolloPortalDB?characterEncoding=utf8 -Dspring.datasource.username=%username% -Dspring.datasource.password=%password% -jar apollo-portal-1.3.0.jar