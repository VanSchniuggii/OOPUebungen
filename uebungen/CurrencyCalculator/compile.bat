@echo off
setlocal EnableExtensions

set "SCRIPT_DIR=%~dp0"
pushd "%SCRIPT_DIR%" >nul

set "SRC_DIR=src\main\java"
set "OUT_DIR=out"
set "DOC_DIR=%OUT_DIR%\javadoc"
set "TMP_SOURCES=%TEMP%\currencycalculator_sources_%RANDOM%.txt"

if defined JAVA_HOME (
    if not exist "%JAVA_HOME%\bin\javac.exe" (
        echo [ERROR] javac not found at %JAVA_HOME%\bin\javac.exe.
        popd >nul
        exit /b 1
    )
    if not exist "%JAVA_HOME%\bin\javadoc.exe" (
        echo [ERROR] javadoc not found at %JAVA_HOME%\bin\javadoc.exe.
        popd >nul
        exit /b 1
    )
    set "JAVAC=%JAVA_HOME%\bin\javac.exe"
    set "JAVADOC=%JAVA_HOME%\bin\javadoc.exe"
) else (
    set "JAVAC=javac"
    set "JAVADOC=javadoc"
    where "%JAVAC%" >nul 2>&1
    if errorlevel 1 (
        echo [ERROR] javac not found. Install a JDK and set JAVA_HOME or PATH.
        popd >nul
        exit /b 1
    )
    where "%JAVADOC%" >nul 2>&1
    if errorlevel 1 (
        echo [ERROR] javadoc not found. Install a JDK and set JAVA_HOME or PATH.
        popd >nul
        exit /b 1
    )
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

if not "%BUILD_EXIT%"=="0" (
    del "%TMP_SOURCES%" >nul 2>&1
    echo [ERROR] Compilation failed.
    popd >nul
    exit /b %BUILD_EXIT%
)

echo [OK] Compilation finished. Class files are in %OUT_DIR%.

echo Generating Javadoc HTML files...
"%JAVADOC%" -encoding UTF-8 -d "%DOC_DIR%" @"%TMP_SOURCES%"
set "JAVADOC_EXIT=%ERRORLEVEL%"

del "%TMP_SOURCES%" >nul 2>&1

if not "%JAVADOC_EXIT%"=="0" (
    echo [ERROR] Javadoc generation failed.
    popd >nul
    exit /b %JAVADOC_EXIT%
)

echo [OK] Javadoc generated in %DOC_DIR%.

popd >nul
exit /b 0
