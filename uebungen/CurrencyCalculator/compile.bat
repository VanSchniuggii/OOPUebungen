@echo off
setlocal EnableExtensions

set "SCRIPT_DIR=%~dp0"
pushd "%SCRIPT_DIR%" >nul

set "SRC_DIR=src\main\java"
set "OUT_DIR=out"
set "TMP_SOURCES=%TEMP%\currencycalculator_sources_%RANDOM%.txt"

if defined JAVA_HOME (
    set "JAVAC=%JAVA_HOME%\bin\javac.exe"
) else (
    set "JAVAC=javac"
)

where "%JAVAC%" >nul 2>&1
if errorlevel 1 (
    echo [ERROR] javac not found. Install a JDK and set JAVA_HOME or PATH.
    popd >nul
    exit /b 1
)

if not exist "%SRC_DIR%" (
    echo [ERROR] Source directory not found: %SRC_DIR%
    popd >nul
    exit /b 1
)

if exist "%OUT_DIR%" rmdir /s /q "%OUT_DIR%"
mkdir "%OUT_DIR%"

> "%TMP_SOURCES%" (
    for /r "%SRC_DIR%" %%F in (*.java) do echo %%F
)

for %%A in ("%TMP_SOURCES%") do if %%~zA==0 (
    echo [ERROR] No Java files found under %SRC_DIR%.
    del "%TMP_SOURCES%" >nul 2>&1
    popd >nul
    exit /b 1
)

echo Compiling Java sources...
"%JAVAC%" -encoding UTF-8 -d "%OUT_DIR%" @"%TMP_SOURCES%"
set "BUILD_EXIT=%ERRORLEVEL%"

del "%TMP_SOURCES%" >nul 2>&1

if not "%BUILD_EXIT%"=="0" (
    echo [ERROR] Compilation failed.
    popd >nul
    exit /b %BUILD_EXIT%
)

echo [OK] Compilation finished. Class files are in %OUT_DIR%.

popd >nul
exit /b 0
