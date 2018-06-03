#!/usr/bin/env bash
mvn clean package
trap 'kill $(jobs -p)' SIGINT

echo "start config server instances"
java -jar config-server/target/config-server-1.0.0-SNAPSHOT.jar &
sleep 15

echo "start eureka server instances"
java -Dspring.application.name=eureka-primary -jar eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar &
java -Dspring.application.name=eureka-secondary -jar eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar &
java -Dspring.application.name=eureka-tertiary -jar eureka-server/target/eureka-server-1.0.0-SNAPSHOT.jar &
sleep 30

echo "start eureka client instances"
java -Dspring.application.name=eureka-client-operand -jar client/target/client-1.0.0-SNAPSHOT.jar &
java -Dspring.application.name=eureka-client-operator -jar client/target/client-1.0.0-SNAPSHOT.jar &
sleep 30
java -Dspring.application.name=eureka-client-root -jar client/target/client-1.0.0-SNAPSHOT.jar &
read -p "Press enter to continue"
