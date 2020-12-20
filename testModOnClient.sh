#!/usr/bin/env sh

./gradlew build && ./gradlew runClient > logs/client_log.txt
