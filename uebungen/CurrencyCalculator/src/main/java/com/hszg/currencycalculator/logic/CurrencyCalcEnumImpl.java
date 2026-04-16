package com.hszg.currencycalculator.logic;


import java.util.Collections;
import java.util.List;

public enum CurrencyCalcEnumImpl implements CurrencyCalculator {

    EUR(new Currency("EUR (Euro)", 0.85)),
    USD(new Currency("USD (US Dollar)", 1.0)),
    GBP(new Currency("GBP (Great Britain Pound)", 0.75)),
    JPY(new Currency("JPY (Japanese Yen)", 110.0)),
    CHF(new Currency("CHF (Swiss Franc)", 0.92)),
    CAD(new Currency("CAD (Canadian Dollar)", 1.25)),
    AUD(new Currency("AUD (Australian Dollar)", 1.35)),
    CNY(new Currency("CNY (Chinese Yuan)", 6.5)),
    INR(new Currency("INR (Indian Rupees)", 74.0)),
    LBP(new Currency("LBP (Lebanese Pounds)", 89731.97)),
    KWD(new Currency("KWD (Kuwait Dollar)", 0.31)),
    BTC(new Currency("BTC (Bitcoin)", 0.00001429)),
    NZD(new Currency("NZD (New Zealand Dollar)", 1.60)),
    SEK(new Currency("SEK (Swedish Krona)", 10.40)),
    NOK(new Currency("NOK (Norwegian Krone)", 10.70)),
    DKK(new Currency("DKK (Danish Krone)", 6.90)),
    PLN(new Currency("PLN (Polish Zloty)", 3.95)),
    CZK(new Currency("CZK (Czech Koruna)", 22.80)),
    MXN(new Currency("MXN (Mexican Peso)", 17.10)),
    BRL(new Currency("BRL (Brazilian Real)", 5.10)),
    ZAR(new Currency("ZAR (South African Rand)", 18.20)),
    SGD(new Currency("SGD (Singapore Dollar)", 1.35)),
    HKD(new Currency("HKD (Hong Kong Dollar)", 7.82)),
    AED(new Currency("AED (UAE Dirham)", 3.67)),
    RUB(new Currency("RUB (Russian Ruble)", 82.26));

    private Currency currency;

    private CurrencyCalcEnumImpl(Currency currency) {
        this.currency = currency;
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
        throw new UnsupportedOperationException("Adding new currencies is not supported in the enum implementation.");
    }

    @Override
    public List<String> getCurrencyNames() {
        List<String> names = new java.util.ArrayList<>();
        for (CurrencyCalcEnumImpl c : CurrencyCalcEnumImpl.values()) {
            names.add(c.currency.getName());
        }
        Collections.sort(names);
        return names;
    }

    private Currency findCurrencyByName(String name) {
        for (CurrencyCalcEnumImpl c : CurrencyCalcEnumImpl.values()) {
            if (c.currency.getName().equals(name)) {
                return c.currency;
            }
        }
        throw new IllegalArgumentException("Currency not found: " + name);
    }
}

