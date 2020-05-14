#!/bin/bash

./gradlew wordPressUpload -Pwordpress-host="$WORDPRESS_HOST" -Pwordpress-username="$WORDPRESS_USER" -Pwordpress-password="$WORDPRESS_PASSWORD" -Pstage=production --stacktrace
