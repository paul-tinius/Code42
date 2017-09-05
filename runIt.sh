#!/usr/bin/env bash

if [ -z "$JAVA_HOME" ]; then
    echo "JAVA_HOME environment variable isn't set, please set it and re-run."
    exit 1
fi

$JAVA_HOME/bin/java -cp "build/classes/main" codefourtytwo.orgtool.Main
