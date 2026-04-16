#!/usr/bin/env bash
set -u

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
cd "$SCRIPT_DIR" || exit 1

SRC_DIR="src/main/java"
OUT_DIR="out"
DOC_DIR="$OUT_DIR/javadoc"
TMP_SOURCES="$(mktemp -t currencycalculator_sources.XXXXXX)"

cleanup() {
  rm -f "$TMP_SOURCES"
}
trap cleanup EXIT

if [[ -n "${JAVA_HOME:-}" ]]; then
  JAVAC="$JAVA_HOME/bin/javac"
  JAVADOC="$JAVA_HOME/bin/javadoc"

  if [[ ! -x "$JAVAC" ]]; then
    echo "[ERROR] javac not found at $JAVAC."
    exit 1
  fi

  if [[ ! -x "$JAVADOC" ]]; then
    echo "[ERROR] javadoc not found at $JAVADOC."
    exit 1
  fi
else
  JAVAC="$(command -v javac || true)"
  JAVADOC="$(command -v javadoc || true)"

  if [[ -z "$JAVAC" ]]; then
    echo "[ERROR] javac not found. Install a JDK and set JAVA_HOME or PATH."
    exit 1
  fi

  if [[ -z "$JAVADOC" ]]; then
    echo "[ERROR] javadoc not found. Install a JDK and set JAVA_HOME or PATH."
    exit 1
  fi
fi

if [[ ! -d "$SRC_DIR" ]]; then
  echo "[ERROR] Source directory not found: $SRC_DIR"
  exit 1
fi

rm -rf "$OUT_DIR"
mkdir -p "$DOC_DIR"

find "$SRC_DIR" -type f -name "*.java" > "$TMP_SOURCES"

if [[ ! -s "$TMP_SOURCES" ]]; then
  echo "[ERROR] No Java files found under $SRC_DIR."
  exit 1
fi

echo "Compiling Java sources..."
"$JAVAC" -encoding UTF-8 -d "$OUT_DIR" @"$TMP_SOURCES"
BUILD_EXIT=$?

if [[ $BUILD_EXIT -ne 0 ]]; then
  echo "[ERROR] Compilation failed."
  exit "$BUILD_EXIT"
fi

echo "[OK] Compilation finished. Class files are in $OUT_DIR."

echo "Generating Javadoc HTML files..."
"$JAVADOC" -encoding UTF-8 -d "$DOC_DIR" @"$TMP_SOURCES"
JAVADOC_EXIT=$?

if [[ $JAVADOC_EXIT -ne 0 ]]; then
  echo "[ERROR] Javadoc generation failed."
  exit "$JAVADOC_EXIT"
fi

echo "[OK] Javadoc generated in $DOC_DIR."
exit 0
