#!/bin/sh

# 这里修改成分配的docker镜像名称
DOCKER_NAME=ajidou/oct-server

mvn clean package -Dmaven.test.skip=true

DOCKER_TAG=`date +%m%d%H%M`
DOCKER_URL=registry.cn-shanghai.aliyuncs.com/$DOCKER_NAME:$DOCKER_TAG

docker build . -t $DOCKER_URL
docker push $DOCKER_URL
