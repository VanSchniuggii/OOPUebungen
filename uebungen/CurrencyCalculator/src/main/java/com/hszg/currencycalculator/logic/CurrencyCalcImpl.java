package com.hszg.currencycalculator.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CurrencyCalcImpl implements CurrencyCalculator {

    private final List<Currency> currencies = new ArrayList<>();

    public CurrencyCalcImpl() {
        initializeDefaultCurrencies();
    }

    private void initializeDefaultCurrencies() {
        currencies.clear();
        currencies.add(new Currency("EUR (Euro)", 0.85));
        currencies.add(new Currency("USD (US Dollar)", 1.0));
        currencies.add(new Currency("GBP (Great Britain Pound)", 0.75));
        currencies.add(new Currency("JPY (Japanese Yen)", 110.0));
        currencies.add(new Currency("CHF (Swiss Franc)", 0.92));
        currencies.add(new Currency("CAD (Canadian Dollar)", 1.25));
        currencies.add(new Currency("AUD (Australian Dollar)", 1.35));
        currencies.add(new Currency("CNY (Chinese Yuan)", 6.5));
        currencies.add(new Currency("INR (Indian Rupees)", 74.0));
        currencies.add(new Currency("LBP (Lebanese Pounds)", 89731.97));
        currencies.add(new Currency("KWD (Kuwait Dollar)", 0.31));
        currencies.add(new Currency("BTC (Bitcoin)", 0.00001429));
        currencies.add(new Currency("NZD (New Zealand Dollar)", 1.60));
        currencies.add(new Currency("SEK (Swedish Krona)", 10.40));
        currencies.add(new Currency("NOK (Norwegian Krone)", 10.70));
        currencies.add(new Currency("DKK (Danish Krone)", 6.90));
        currencies.add(new Currency("PLN (Polish Zloty)", 3.95));
        currencies.add(new Currency("CZK (Czech Koruna)", 22.80));
        currencies.add(new Currency("MXN (Mexican Peso)", 17.10));
        currencies.add(new Currency("BRL (Brazilian Real)", 5.10));
        currencies.add(new Currency("ZAR (South African Rand)", 18.20));
        currencies.add(new Currency("SGD (Singapore Dollar)", 1.35));
        currencies.add(new Currency("HKD (Hong Kong Dollar)", 7.82));
        currencies.add(new Currency("AED (UAE Dirham)", 3.67));
        currencies.add(new Currency("RUB (Russian Ruble)", 82.26));
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
        return toCurrency.getExchangeRate() / fromCurrency.getExchangeRate();
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
        double exchangeRateToUSD = reference.getExchangeRate() / exchangeRate;

        currencies.add(new Currency(normalizedName, exchangeRateToUSD));
    }

    @Override
    public List<String> getCurrencyNames() {
        List<String> names = new ArrayList<>();
        for (Currency currency : currencies) {
            names.add(currency.getName());
        }
        Collections.sort(names);
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

