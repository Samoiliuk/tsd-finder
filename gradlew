#!/usr/bin/env sh
set -eu

APP_HOME="$(cd "$(dirname "$0")" && pwd)"
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
PROPS="$APP_HOME/gradle/wrapper/gradle-wrapper.properties"

# якщо jar не існує — зупиняємось (ми його зараз додамо)
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Missing $WRAPPER_JAR"
  exit 1
fi

JAVA_CMD="${JAVA_HOME:-}/bin/java"
if [ ! -x "$JAVA_CMD" ]; then
  JAVA_CMD="java"
fi

exec "$JAVA_CMD" -jar "$WRAPPER_JAR" "$@"
