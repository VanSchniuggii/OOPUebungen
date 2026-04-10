package com.hszg.currencycalculator.logic;

import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class DataWriter {

    public void writeDate(List<Currency> currencies) {
        writeData(currencies, "currencies.json");
    }

    public void writeData(List<Currency> currencies, String fileName) {
        if (currencies == null) {
            throw new IllegalArgumentException("currencies must not be null");
        }
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName must not be empty");
        }

        try (Writer dataWriter = new FileWriter(fileName)) {
            dataWriter.write(toJson(currencies));
        } catch (IOException e) {
            throw new RuntimeException("Failed to write currencies to JSON file: " + fileName, e);
        }
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

