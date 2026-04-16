# CurrencyCalculator

Simple build scripts are included for both Windows and macOS/Linux.

## Requirements

- JDK installed (Java 17+ recommended)
- javac and javadoc available via JAVA_HOME or PATH

## Build And Generate Javadoc

### Windows (PowerShell or CMD)

Run from this folder:

```bat
.\compile.bat
```

### macOS/Linux (Terminal)

Run from this folder:

```bash
chmod +x ./compile.sh
./compile.sh
```

## Build And Run Executable JAR

### Windows (PowerShell or CMD)

Build JAR only:

```bat
.\run-jar.bat --build-only
```

Build and run JAR:

```bat
.\run-jar.bat
```

### macOS/Linux (Terminal)

Build JAR only:

```bash
chmod +x ./run-jar.sh
./run-jar.sh --build-only
```

Build and run JAR:

```bash
./run-jar.sh
```

## Output

- Compiled classes: out
- Javadoc HTML: out/javadoc
- Main Javadoc page: out/javadoc/index.html
- Executable JAR: dist/currencycalculator.jar
