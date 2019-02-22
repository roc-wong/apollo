@echo off

rem apollo config db info
set apollo_config_db_url="jdbc:mysql://10.29.132.27:3306/ApolloConfigDB?characterEncoding=utf8"
set apollo_config_db_username="apollo"
set apollo_config_db_password="888888+"

rem apollo portal db info
set apollo_portal_db_url="jdbc:mysql://10.29.132.27:3306/ApolloPortalDB?characterEncoding=utf8"
set apollo_portal_db_username="apollo"
set apollo_portal_db_password="888888+"

rem meta server url, different environments should have different meta server addresses
set dev_meta="http://10.29.183.62:8080,http://10.29.183.78:8080,http://10.29.183.104:8080"
set fat_meta="http://10.29.184.29:8080,http://10.29.184.30:8080,http://10.29.184.31:8080"
set pro_meta="http://10.29.131.24:8080,http://10.29.131.25:8080,http://10.29.131.26:8080"
set aliyun_pro_meta="http://10.29.27.31:8002,http://10.29.27.31:8003,http://10.29.27.31:8004"

set META_SERVERS_OPTS=-Ddev_meta=%dev_meta% -Dfat_meta=%fat_meta% -Duat_meta=%uat_meta% -Dpro_meta=%pro_meta% -Daliyun_pro_meta=%aliyun_pro_meta%

rem =============== Please do not modify the following content =============== 
rem go to script directory
cd "%~dp0"

cd ..

rem package config-service and admin-service
echo "==== starting to build config-service and admin-service ===="

call mvn clean package -DskipTests -pl apollo-configservice,apollo-adminservice -am -Dapollo_profile=github -Dspring_datasource_url=%apollo_config_db_url% -Dspring_datasource_username=%apollo_config_db_username% -Dspring_datasource_password=%apollo_config_db_password%

echo "==== building config-service and admin-service finished ===="

echo "==== starting to build portal ===="

call mvn clean package -DskipTests -pl apollo-portal -am -Dapollo_profile=github,auth,zts -Dspring_datasource_url=%apollo_portal_db_url% -Dspring_datasource_username=%apollo_portal_db_username% -Dspring_datasource_password=%apollo_portal_db_password% %META_SERVERS_OPTS%

echo "==== building portal finished ===="

pause
