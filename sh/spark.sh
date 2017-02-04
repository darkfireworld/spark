#!/bin/bash

# port 18085

# kill

MY_APP_PID=`(netstat -apn | grep :::18085 | awk {'print $7'} | awk -F / {'print $1'})`

echo $MY_APP_PID

if [ -n "$MY_APP_PID" ];then
    kill -15 $MY_APP_PID
fi

unset MY_APP_PID

# copy
cp target/netease-purchase-survey-1.0-SNAPSHOT.jar /var/purchase-survey/netease-purchase-survey-1.0-SNAPSHOT.jar

# start
java -jar /var/purchase-survey/netease-purchase-survey-1.0-SNAPSHOT.jar &

# javadoc

rm -fr /var/www/doc/purchase-survey/*

cp -fr target/site/apidocs/* /var/www/doc/purchase-survey/