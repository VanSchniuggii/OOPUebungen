package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;

import java.util.ArrayList;
import java.util.List;

public class CurrencyCalcImpl implements CurrencyCalculator {

    private final List<Currency> currencies = new ArrayList<>();

    public CurrencyCalcImpl() {
        currencies.add(new Currency("EUR", 0.85));
        currencies.add(new Currency("USD", 1.0));
        currencies.add(new Currency("GBP", 0.75));
        currencies.add(new Currency("JPY", 110.0));
        currencies.add(new Currency("CHF", 0.92));
    }

    @Override
    public double convert(double amount, String fromCurrency, String toCurrency) {
        Currency from = findCurrencyByName(fromCurrency);
        Currency to = findCurrencyByName(toCurrency);
        double exchangeRate = getExchangeRate(from, to);
        return amount * exchangeRate;
        
    }

    @Override
    public double getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.exchangeRateToUSD/toCurrency.exchangeRateToUSD;
    }

    @Override
    public void addCurrency(String name, double exchangeRate, String referenceCurrency) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency name must not be empty.");
        }
        if (exchangeRate <= 0) {
            throw new IllegalArgumentException("Exchange rate must be positive.");
        }

        String normalizedName = name.trim();
        for (Currency c : currencies) {
            if (c.getName().equalsIgnoreCase(normalizedName)) {
                throw new IllegalArgumentException("Currency already exists.");
            }
        }

        Currency reference = findCurrencyByName(referenceCurrency);
        double exchangeRateToUSD = exchangeRate * reference.exchangeRateToUSD;

        currencies.add(new Currency(normalizedName, exchangeRateToUSD));
    }

    @Override
    public List<String> getCurrencyNames() {
        List<String> names = new ArrayList<>();
        for (Currency currency : currencies) {
            names.add(currency.getName());
        }
        return names;
    }

    private Currency findCurrencyByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Currency name must not be empty.");
        }

        for (Currency currency : currencies) {
            if (currency.getName().equalsIgnoreCase(name.trim())) {
                return currency;
            }
        }

        throw new IllegalArgumentException("Unknown currency: " + name);
    }

}
