echo

set url="another.db"
set username="cceverfor"
set password="5664c.c."

start "configservice" java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Dserver.port=8081 -Dspring.datasource.url=jdbc:mysql://%url%/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=%username% -Dspring.datasource.password=%password% -jar apollo-configservice-1.3.0.jar
start "adminservice" java -Xms256m -Xmx256m -Dapollo_profile=github,auth -Dserver.port=8091 -Dspring.datasource.url=jdbc:mysql://%url%/ApolloConfigDB?characterEncoding=utf8 -Dspring.datasource.username=%username% -Dspring.datasource.password=%password% -jar apollo-adminservice-1.3.0.jar