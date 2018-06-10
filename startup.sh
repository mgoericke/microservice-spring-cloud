#!/usr/bin/env bash
mvn clean package
trap 'kill $(jobs -p)' SIGINT
java -jar config-server/target/*.jar &
sleep 15
java -jar client/target/*.jar &
read -p "Press enter to continue"
