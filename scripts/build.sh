#!/bin/sh

# apollo config db info
apollo_config_db_url=jdbc:mysql://10.29.132.27:3306/ApolloConfigDB?characterEncoding=utf8
apollo_config_db_username=apollo
apollo_config_db_password=888888+

# apollo portal db info
apollo_portal_db_url=jdbc:mysql://10.29.132.27:3306/ApolloPortalDB?characterEncoding=utf8
apollo_portal_db_username=apollo
apollo_portal_db_password=888888+

# meta server url, different environments should have different meta server addresses
dev_meta=http://10.29.183.62:8080,http://10.29.183.78:8080,http://10.29.183.104:8080
fat_meta=http://10.29.184.29:8080,http://10.29.184.30:8080,http://10.29.184.31:8080
pro_meta=http://10.29.131.24:8080,http://10.29.131.25:8080,http://10.29.131.26:8080
aliyun_pro_meta=http://10.29.27.31:8002,http://10.29.27.31:8003,http://10.29.27.31:8004

META_SERVERS_OPTS="-Ddev_meta=$dev_meta -Dfat_meta=$fat_meta -Duat_meta=$uat_meta -Dpro_meta=$pro_meta -Daliyun_pro_meta=$aliyun_pro_meta"

# =============== Please do not modify the following content =============== #
# go to script directory
cd "${0%/*}"

cd ..

# package config-service and admin-service
echo "==== starting to build config-service and admin-service ===="

mvn clean package -DskipTests -pl apollo-configservice,apollo-adminservice -am -Dapollo_profile=github -Dspring_datasource_url=$apollo_config_db_url -Dspring_datasource_username=$apollo_config_db_username -Dspring_datasource_password=$apollo_config_db_password

echo "==== building config-service and admin-service finished ===="

echo "==== starting to build portal ===="

mvn clean package -DskipTests -pl apollo-portal -am -Dapollo_profile=github,auth,zts -Dspring_datasource_url=$apollo_portal_db_url -Dspring_datasource_username=$apollo_portal_db_username -Dspring_datasource_password=$apollo_portal_db_password $META_SERVERS_OPTS

echo "==== building portal finished ===="
