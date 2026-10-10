#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"

if ! command -v java >/dev/null 2>&1; then
  echo "JDK 21 or newer is required. Install a JDK and try again." >&2
  exit 1
fi
if ! command -v mvn >/dev/null 2>&1; then
  echo "Apache Maven 3.9 or newer is required. Install Maven and try again." >&2
  exit 1
fi

echo "Starting Book Haven. The first run needs internet to download dependencies."
exec mvn clean compile exec:java
