#!/usr/bin/env bash
. ~/.bash_profile

./gradlew clean assembleDebug install

./gradlew :inline-activity-result:install
./gradlew :inline-activity-result:bintrayUpload

./gradlew :inline-activity-result-kotlin:install
./gradlew :inline-activity-result-kotlin:bintrayUpload

./gradlew :inline-activity-result-rx:install
./gradlew :inline-activity-result-rx:bintrayUpload