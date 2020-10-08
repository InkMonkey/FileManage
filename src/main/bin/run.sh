#!/usr/bin/env bash

#注意，在linux环境下初次使用时，请执行dos2unix，否则会报错


#这里可替换为你自己的执行程序，其他代码无需更改
APP_NAME=filemanage-0.0.1-SNAPSHOT.jar

#JVM启动参数
PARAMS=
#远程调试启动参数
#-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8087
#分配堆内存启动参数
#-Xmx   Java Heap最大值，默认值为物理内存的1/4
#-Xms   Java Heap初始值，
#-Xmx1g -Xms512m

#使用说明，用来提示输入参数
usage() {
    echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
    exit 1
}

#检查程序是否在运行
is_exist(){
  pid=`ps -ef|grep ${APP_NAME}|grep -v grep|awk '{print $2}' `
  #如果不存在返回1，存在返回0
  if [[ -z "${pid}" ]]; then
   return 1
  else
    return 0
  fi
}

#启动方法
start(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME} is already running. pid is ${pid} ."
  else
    nohup java -jar ${PARAMS} ${APP_NAME} > /dev/null 2>&1 &
    status
  fi
}

#停止方法
stop(){
  is_exist
  if [[ $? -eq "0" ]]; then
    kill -9 ${pid}
    echo "${APP_NAME} is shutdown."
  else
    echo "${APP_NAME} is not running"
  fi
}

#输出运行状态
status(){
  is_exist
  if [[ $? -eq "0" ]]; then
    echo "${APP_NAME} is running. pid is ${pid}"
  else
    echo "${APP_NAME} is NOT running."
  fi
}

#重启
restart(){
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$1" in
  "start")
    start
    ;;
  "stop")
    stop
    ;;
  "status")
    status
    ;;
  "restart")
    restart
    ;;
  *)
    usage
    ;;
esac