#!/bin/bash
#source ./uumai_config.sh
#echo $UUMAI_HOME

#UUMAI_HOME=/home/rock/kanxg/Dropbox/mysourcecode/uumai/bitbucket
#UUMAI_JAVA_OPTS=" -Xms20m -Xmx100m " 

echo "start to build core..." 


echo "clean old logs and libs..." 

rm -rf ./build/fengchao/libs/*
rm -rf ./build/fengchao/logs/*


echo "build  uumai-common..." 

cd  ./fengchao/uumai-common
rm  -rf ./common-libs/libs
mvn clean install  

echo "build  uumai-core..." 
cd  ../uumai-core
mvn clean install 


echo "build  install package..." 
cd ../../
cp -r ./fengchao/uumai-common/common-libs/libs/* ./build/fengchao/libs/

find ./fengchao/uumai-common  -name 'uumai-*.jar' | xargs -i{}  cp {}  ./build/fengchao/libs/
find ./fengchao/uumai-core  -name 'uumai-*.jar' | xargs -i{}  cp {}  ./build/fengchao/libs/


#echo "build uumai yarn" 

#mvn package -f ./shop_indexer/uumai-yarn/pom.xml

#cp -r ./shop_indexer/uumai-yarn/target/uumai-yarn-1.0.jar ./build/libs/


:<<BLOCK


echo "build apps..."

./bin/buildapp.sh quartz

mvn package -f ./crawler-website/crawler-quartz/pom-storm.xml 

./bin/buildapp.sh chart2png
./bin/buildapp.sh quartz-client
./bin/buildapp.sh quartz-cloudinfotech

echo "build finished!" 




BLOCK
