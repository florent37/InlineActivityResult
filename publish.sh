#!/usr/bin/env bash
./gradlew clean assembleDebug install

./gradlew :no-activity-result:install
./gradlew :no-activity-result:bintrayUpload

./gradlew :no-activity-result-kotlin:install
./gradlew :no-activity-result-kotlin:bintrayUpload

./gradlew :no-activity-result-rx:install
./gradlew :no-activity-result-rx:bintrayUpload