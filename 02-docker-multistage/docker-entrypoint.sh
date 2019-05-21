#!/bin/sh
set -e

DIRECTORY=${DIRECTORY:-/tmp/myapp}

# If $DIRECTORY doesn't exist.
if [ ! -d "$DIRECTORY" ]; then
  echo creating $DIRECTORY
  mkdir -p $DIRECTORY
fi

exec "$@"
