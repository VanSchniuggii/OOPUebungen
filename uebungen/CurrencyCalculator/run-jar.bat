@echo off
setlocal EnableExtensions

set "SCRIPT_DIR=%~dp0"
pushd "%SCRIPT_DIR%" >nul

set "DIST_DIR=dist"
set "OUT_DIR=out"
set "MANIFEST=src\main\java\com\hszg\currencycalculator\META-INFO\MANIFEST.MF"
set "JAR_FILE=%DIST_DIR%\currencycalculator.jar"

if defined JAVA_HOME (
    if not exist "%JAVA_HOME%\bin\jar.exe" (
        echo [ERROR] jar not found at %JAVA_HOME%\bin\jar.exe.
        popd >nul
        exit /b 1
    )
    if not exist "%JAVA_HOME%\bin\java.exe" (
        echo [ERROR] java not found at %JAVA_HOME%\bin\java.exe.
        popd >nul
        exit /b 1
    )
    set "JAR_CMD=%JAVA_HOME%\bin\jar.exe"
    set "JAVA_CMD=%JAVA_HOME%\bin\java.exe"
) else (
    set "JAR_CMD=jar"
    set "JAVA_CMD=java"
    where "%JAR_CMD%" >nul 2>&1
    if errorlevel 1 (
        echo [ERROR] jar not found. Install a JDK and set JAVA_HOME or PATH.
        popd >nul
        exit /b 1
    )
    where "%JAVA_CMD%" >nul 2>&1
    if errorlevel 1 (
        echo [ERROR] java not found. Install a JDK and set JAVA_HOME or PATH.
        popd >nul
        exit /b 1
    )
)

if not exist "%MANIFEST%" (
    echo [ERROR] Manifest file not found: %MANIFEST%
    popd >nul
    exit /b 1
)

call "%SCRIPT_DIR%compile.bat"
if errorlevel 1 (
    echo [ERROR] compile.bat failed.
    popd >nul
    exit /b 1
)

if exist "%DIST_DIR%" rmdir /s /q "%DIST_DIR%"
mkdir "%DIST_DIR%"

echo Building executable JAR...
"%JAR_CMD%" cfm "%JAR_FILE%" "%MANIFEST%" -C "%OUT_DIR%" .
set "JAR_EXIT=%ERRORLEVEL%"

if not "%JAR_EXIT%"=="0" (
    echo [ERROR] JAR build failed.
    popd >nul
    exit /b %JAR_EXIT%
)

echo [OK] Executable JAR created at %JAR_FILE%.

if /I "%~1"=="--build-only" (
    popd >nul
    exit /b 0
)

echo Running %JAR_FILE% ...
"%JAVA_CMD%" -jar "%JAR_FILE%"
set "RUN_EXIT=%ERRORLEVEL%"

popd >nul
exit /b %RUN_EXIT%
