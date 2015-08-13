#!/bin/sh
#
# Copyright (c) 2012-2015
#

export LANG=zh_CN.UTF-8

export LC_ALL=zh_CN.UTF-8

#cd ..

#echo ${UPPER_HOME}

if [ -f "${UPPER_HOME}/bin/upper.sh" ];then

echo "Using UPPER_HOME:   ${UPPER_HOME}"

else

echo "环境变量UPPER_HOME设置的不正确"

exit 0
fi

echo "Using JAVA_HOME:    ${JAVA_HOME}"


CLASSPATH="."

#for n in `find ${UPPER_HOME}/config -name '*.xml' -o -name '*.properties'`;do
# CLASSPATH="${CLASSPATH}:${n}"
#done

for n in `find ${UPPER_HOME}/lib -name '*.jar' -o -name '*.zip'`;do
 CLASSPATH="${CLASSPATH}:${n}"
done



#+UseG1GC only for java7u9 above
#-XX:+UseG1GC	开启G1
#-XX:MaxGCPauseMillis=n	最大GC停顿时间，这是个软目标，JVM将尽可能（但不保证）停顿小于这个时间
#-XX:InitiatingHeapOccupancyPercent=n	堆占用了多少的时候就触发GC，默认为45
#-XX:NewRatio=n	 new/old 比，默认为2
#-XX:SurvivorRatio=n	 eden/survivor比，默认为8
#-XX:MaxTenuringThreshold=n	新生代到老年代的岁数，默认是15
#-XX:ParallelGCThreads=n	并行GC的线程数，默认值会根据平台不同而不同
#-XX:ConcGCThreads=n	并发GC使用的线程数
#-XX:G1ReservePercent=n	设置作为空闲空间的预留内存百分比，以降低目标空间溢出的风险。默认值是 10%。
#-XX:G1HeapRegionSize=n	设置的 G1 区域的大小。值是2的幂，范围是1 MB 到32 MB。目标是根据最小的 Java 堆大小划分出约 2048 个区域。

JAVA_OPTS="-server -Xms128m -Xmx128m -XX:+UseG1GC -XX:MaxGCPauseMillis=400 -XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=logs/upper.memory.dump -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
-XX:+PrintGCApplicationStoppedTime -Xloggc:logs/upper.gc.log
"

java $JAVA_OPTS -classpath $CLASSPATH  com.sand.updiff.upper.Upper $@

exit 0
