package com.hszg.currencycalculator.logic;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class DataWriter {

    private static final String DATA_DIRECTORY_NAME = "currency-data";
    private static final String CURRENCIES_FILE_NAME = "currencies.json";

    public void writeDate(List<Currency> currencies) {
        writeData(currencies, resolveDefaultCurrenciesPath());
    }

    public void writeData(List<Currency> currencies, String fileName) {
        if (currencies == null) {
            throw new IllegalArgumentException("currencies must not be null");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName must not be empty");
        }

        Path filePath = Path.of(fileName);
        Path parent = filePath.getParent();

        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create data directory for JSON file: " + filePath, e);
        }

        try (Writer dataWriter = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            dataWriter.write(toJson(currencies));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write currencies to JSON file: " + fileName, e);
        }
    }

    private String resolveDefaultCurrenciesPath() {
        return resolveRuntimeBaseDirectory()
            .resolve(DATA_DIRECTORY_NAME)
            .resolve(CURRENCIES_FILE_NAME)
            .toString();
    }

    private Path resolveRuntimeBaseDirectory() {
        try {
            URI codeSourceUri = DataWriter.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI();

            Path codeSourcePath = Path.of(codeSourceUri).toAbsolutePath().normalize();
            if (codeSourcePath.toString().toLowerCase().endsWith(".jar")) {
                Path jarParent = codeSourcePath.getParent();
                if (jarParent != null) {
                    return jarParent;
                }
            }
        } catch (Exception ignored) {
            // Fallback below uses working directory.
        }

        return Path.of(System.getProperty("user.dir", ".")).toAbsolutePath().normalize();
    }

    private String toJson(List<Currency> currencies) {
        StringBuilder json = new StringBuilder();
        json.append("[\n");

        for (int i = 0; i < currencies.size(); i++) {
            Currency currency = currencies.get(i);
            json.append("  {\"name\": \"")
                .append(escapeJson(currency.getName()))
                .append("\", \"exchangeRateToUSD\": ")
                .append(currency.exchangeRateToUSD)
                .append("}");

            if (i < currencies.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }

        json.append("]");
        return json.toString();
    }

    private String escapeJson(String value) {
        if (value == null) {
            return "";
        }
        return value
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");
    }

}

