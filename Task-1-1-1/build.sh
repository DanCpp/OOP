#!/bin/bash

set -e

MAIN_CLASS="org.example.Main"
APP_NAME="heapsort_app"

SRC="src/main/java"
BUILD="shell_build"
CLASSES="$BUILD/classes"
JAR="$BUILD/jar"
DOCS="$BUILD/docs"

rm -rf "$BUILD"
mkdir -p "$CLASSES" "$JAR" "$DOCS"

find "$SRC" -name "*.java" >"$BUILD/all_sources.txt"
javac -d "$CLASSES" @"$BUILD/all_sources.txt"

jar --create --file="$JAR/$APP_NAME.jar" --main-class="$MAIN_CLASS" -C "$CLASSES" .

javadoc -d "$DOCS" -sourcepath "$SRC" -subpackages org.example

java -jar "$JAR/$APP_NAME.jar"
