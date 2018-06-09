#!/usr/bin/env bash
mvn clean package
trap 'kill $(jobs -p)' SIGINT

#././../kafka_2.12-1.1.0/bin/zookeeper-server-start.sh ././../kafka_2.12-1.1.0/config/zookeeper.properties &
#sleep 10
#././../kafka_2.12-1.1.0/bin/kafka-server-start.sh ././../kafka_2.12-1.1.0/config/server.properties &
#sleep 15

java -jar config-server/target/*.jar &
sleep 15
java -Dspring.application.name=eureka-primary -jar eureka-server/target/*.jar &
java -Dspring.application.name=eureka-secondary -jar eureka-server/target/*.jar &
java -Dspring.application.name=eureka-tertiary -jar eureka-server/target/*.jar &
sleep 30
java -jar turbine-server/target/*.jar &
java -Dspring.application.name=eureka-client-operand -jar client-response/target/*.jar &
java -Dspring.application.name=eureka-client-operator -jar client-response/target/*.jar &
sleep 30
java -Dspring.application.name=eureka-client-root -jar client-request/target/*.jar &
sleep 60
java -jar zuul-server/target/*.jar &
read -p "Press enter to continue"
