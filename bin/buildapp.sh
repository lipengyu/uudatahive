#!/bin/bash
#source ./uumai_config.sh


echo "start to build..." 

cd ./crawler-website/crawler-$1
mvn clean install

cd ../..

cp -r ./crawler-website/crawler-$1/target/crawler-*-1.0.jar ./build/fengchao/libs/
#cp -r ./crawler-website/crawler-$1/libs/* ./build/fengchao/libs/

echo "build finished!" 


:<<BLOCK
echo "start to deploy..." 
while read line
do
    ##echo $line
    OLD_IFS="$IFS" 
	IFS="," 
	arr=($line) 
    echo "scp to ${arr[0]}" 
    scp -r -q ./crawler-website/crawler-$1/target/crawler-*-1.0.jar ${arr[0]}:${arr[1]}libs/
 
 ssh -t ${arr[0]} > /dev/null 2>&1 << eeooff
 cd ${arr[1]}
 ./sbin/syncjar.sh  ./libs/crawler-$1-1.0.jar  libs/
 exit
 eeooff

done < ./conf/masters
BLOCK

echo "finish deploy!" 