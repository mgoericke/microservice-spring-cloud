#!/usr/bin/env bash
mvn clean package
trap 'kill $(jobs -p)' SIGINT
java -jar config-server/target/*.jar &
sleep 5
java -Dspring.application.name=eureka-primary -jar eureka-server/target/*.jar &
java -Dspring.application.name=eureka-secondary -jar eureka-server/target/*.jar &
java -Dspring.application.name=eureka-tertiary -jar eureka-server/target/*.jar &
sleep 30
java -Dspring.application.name=eureka-client-operand -jar client-response/target/*.jar &
java -Dspring.application.name=eureka-client-operator -jar client-response/target/*.jar &
sleep 30
java -Dspring.application.name=eureka-client-root -jar client-request/target/*.jar &
read -p "Press enter to continue"
