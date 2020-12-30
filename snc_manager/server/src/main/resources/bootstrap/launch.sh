#!/bin/bash
source /etc/profile
APP_DIR=/export/icity/compute-online/snc-manager
LIB_DIR=$APP_DIR/jars
BIN_DIR=$APP_DIR/bin
jar_name=$APP_DIR/bin/master.jar

CP=.
for f in $LIB_DIR/*.jar; do
  CP=$CP:$f
done
	

DEBUG_MOD="-Xdebug -Xrunjdwp:transport=dt_socket,address=5018,server=y,suspend=y"
D_OPTS="-Xmx6g -Xms2g -Xmn2g  -XX:+UseParallelGC -XX:MaxGCPauseMillis=500 -XX:+UseParallelOldGC -XX:+UseAdaptiveSizePolicy -XX:SurvivorRatio=5 -Dmanager_conf_dir=$APP_DIR/config/ -Dlogback.configurationFile=file://$APP_DIR/config/logback.xml"
out_file=$APP_DIR/m.log

#启动或者存活监控脚本
start(){
pid=`ps -aux | grep $APP_DIR/bin | grep manager_conf_dir | grep $jar_name| grep -v grep | awk '{print $2}'`
echo $pid
if [ -z $pid ]
then
    echo ' starting ......'
    cd $BIN_DIR;
    nohup java -cp $CP:$jar_name $D_OPTS server.app  > $out_file 2>&1 &
else
     echo 'progress already exist!'
fi
pid_2=`ps -aux | grep $APP_DIR/bin | grep manager_conf_dir | grep $jar_name| grep -v grep | awk '{print $2}'`
echo "program started pid is $pid_2"
}

# 部署脚本
restart(){
stop
start
}

#安全重启脚本
stop(){

pid=`ps -aux | grep $APP_DIR/bin | grep manager_conf_dir | grep $jar_name | grep -v grep | awk '{print $2}'`

echo "before stop pid is $pid"

if [ -z $pid ]
then
    echo 'Program not started '
else
     echo 'kill process'
     kill -9 $pid
fi
}

case $1 in
  "start")
    start
    ;;
  "killstart")
    restart
    ;;
  "stop")
    stop
    ;;
  *)
    echo "Usage: launch [start | stop | killstart] "    
    ;;
esac
