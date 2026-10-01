#!/bin/sh
set -e
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
GRADLE_VERSION=9.2.0
DIST="$HOME/.gradle/wrapper/dists/gradle-$GRADLE_VERSION-bin"
ZIP="$DIST/gradle-$GRADLE_VERSION-bin.zip"
if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
fi
if [ ! -x "$DIST/gradle-$GRADLE_VERSION/bin/gradle" ]; then
  mkdir -p "$DIST"
  if [ ! -f "$ZIP" ]; then
    echo "Downloading Gradle $GRADLE_VERSION..."
    curl -fL --retry 3 -o "$ZIP" "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"
  fi
  unzip -q -o "$ZIP" -d "$DIST"
fi
exec "$DIST/gradle-$GRADLE_VERSION/bin/gradle" "$@"
