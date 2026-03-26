package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;

import java.util.List;

public class CurrencyCalcEnumImpl implements CurrencyCalculator {

    @Override
    public double convert(double amount, String fromCurrency, String toCurrency) {
        // Implementation for converting between currencies using an enum-based approach
        return 0; // Placeholder return value
    }

    @Override
    public double getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        // Implementation for retrieving exchange rates between currencies
        return 0; // Placeholder return value
    }

    @Override
    public void addCurrency(String name, double exchangeRate, String referenceCurrency) {
        // Implementation for adding a new currency with its exchange rate
    }

    @Override
    public List<String> getCurrencyNames() {
        // Implementation for retrieving the list of available currency names
        return null; // Placeholder return value
    }

}
