package uebungen.src.main.java.com.hszg.exercises.aufgabe1bis3.Logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLoader {

    private static final Pattern CURRENCY_OBJECT_PATTERN = Pattern.compile(
        "\\{\\s*\\\"name\\\"\\s*:\\s*\\\"((?:\\\\.|[^\\\"\\\\])*)\\\"\\s*,\\s*\\\"exchangeRateToUSD\\\"\\s*:\\s*([-+]?\\d+(?:\\.\\d+)?(?:[eE][-+]?\\d+)?)\\s*\\}"
    );

    public List<Currency> loadData() {
        return loadData("currencies.json");
    }

    public List<Currency> loadData(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            throw new IllegalArgumentException("fileName must not be empty");
        }

        Path filePath = Path.of(fileName);
        if (!Files.exists(filePath)) {
            return new ArrayList<>();
        }

        String json;
        try {
            json = Files.readString(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read currencies JSON file: " + fileName, e);
        }

        return parseCurrenciesJson(json);
    }

    private List<Currency> parseCurrenciesJson(String json) {
        List<Currency> currencies = new ArrayList<>();
        if (json == null) {
            return currencies;
        }

        String trimmed = json.trim();
        if (trimmed.isEmpty() || "[]".equals(trimmed)) {
            return currencies;
        }

        if (!trimmed.startsWith("[") || !trimmed.endsWith("]")) {
            throw new IllegalArgumentException("Invalid JSON format: expected an array");
        }

        Matcher matcher = CURRENCY_OBJECT_PATTERN.matcher(trimmed);
        while (matcher.find()) {
            String name = unescapeJson(matcher.group(1));
            double exchangeRateToUSD = Double.parseDouble(matcher.group(2));
            currencies.add(new Currency(name, exchangeRateToUSD));
        }

        return currencies;
    }

    private String unescapeJson(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char current = value.charAt(i);
            if (current == '\\' && i + 1 < value.length()) {
                char next = value.charAt(++i);
                switch (next) {
                    case '"':
                        result.append('"');
                        break;
                    case '\\':
                        result.append('\\');
                        break;
                    case 'n':
                        result.append('\n');
                        break;
                    case 'r':
                        result.append('\r');
                        break;
                    case 't':
                        result.append('\t');
                        break;
                    default:
                        result.append(next);
                        break;
                }
            } else {
                result.append(current);
            }
        }

        return result.toString();
    }

}
