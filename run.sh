#!/bin/sh
./gradlew check && SPRING_PROFILES_ACTIVE=development ./gradlew :wikiboot-web:bootRun
