#!/usr/bin/env bash
set -u

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

DIST_DIR="dist"
OUT_DIR="out"
MANIFEST="src/main/java/com/hszg/currencycalculator/META-INFO/MANIFEST.MF"
JAR_FILE="$DIST_DIR/currencycalculator.jar"

if [[ -n "${JAVA_HOME:-}" ]]; then
  JAR_CMD="$JAVA_HOME/bin/jar"
  JAVA_CMD="$JAVA_HOME/bin/java"

  if [[ ! -x "$JAR_CMD" ]]; then
    echo "[ERROR] jar not found at $JAR_CMD."
    exit 1
  fi

  if [[ ! -x "$JAVA_CMD" ]]; then
    echo "[ERROR] java not found at $JAVA_CMD."
    exit 1
  fi
else
  JAR_CMD="$(command -v jar || true)"
  JAVA_CMD="$(command -v java || true)"

  if [[ -z "$JAR_CMD" ]]; then
    echo "[ERROR] jar not found. Install a JDK and set JAVA_HOME or PATH."
    exit 1
  fi

  if [[ -z "$JAVA_CMD" ]]; then
    echo "[ERROR] java not found. Install a JDK and set JAVA_HOME or PATH."
    exit 1
  fi
fi

if [[ ! -f "$MANIFEST" ]]; then
  echo "[ERROR] Manifest file not found: $MANIFEST"
  exit 1
fi

"$SCRIPT_DIR/compile.sh"
COMPILE_EXIT=$?
if [[ $COMPILE_EXIT -ne 0 ]]; then
  echo "[ERROR] compile.sh failed."
  exit "$COMPILE_EXIT"
fi

rm -rf "$DIST_DIR"
mkdir -p "$DIST_DIR"

echo "Building executable JAR..."
"$JAR_CMD" cfm "$JAR_FILE" "$MANIFEST" -C "$OUT_DIR" .
JAR_EXIT=$?

if [[ $JAR_EXIT -ne 0 ]]; then
  echo "[ERROR] JAR build failed."
  exit "$JAR_EXIT"
fi

echo "[OK] Executable JAR created at $JAR_FILE."

if [[ "${1:-}" == "--build-only" ]]; then
  exit 0
fi

echo "Running $JAR_FILE ..."
"$JAVA_CMD" -jar "$JAR_FILE"
exit $?
