#!/bin/bash
source ~/.bashrc

agentlib=""
for arg ; do
    if [  "$arg" = '-debug' ]; then        
        agentlib="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
    fi
done

exec java $agentlib  -classpath '.:./libs/*' -Dfengchao.home=. -Dlog4j.configuration=./log4j.properties $@
#exec java $agentlib  -classpath '.:./libs/*' -Duser.timezone="Asia/Shanghai"  -Duumai.home=. $@
#exec java -classpath '.:./libs/*' -Duumai.home=. $@
