SELECT * FROM ApolloConfigDB.`App` e ;

-- 发布配置
SELECT * FROM ApolloConfigDB.`ReleaseMessage` e ;

-- 生产环境配置
-- ApolloConfigService 8081
-- ApolloAdminService  8091
SELECT * FROM ApolloConfigDB.`ServerConfig` e 
WHERE e.`Key` = 'eureka.service.url';
-- 更新,需要配合调整为启动的那个环境的端口
-- update ApolloConfigDB.`ServerConfig` e set e.`Value` = 'http://localhost:8081/eureka/'
-- where e.`Key` = 'eureka.service.url';

SELECT * FROM ApolloPortalDB.`App` e;
