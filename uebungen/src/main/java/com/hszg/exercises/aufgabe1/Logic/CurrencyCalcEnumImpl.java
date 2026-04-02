package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;


import java.util.Collections;
import java.util.List;

public enum CurrencyCalcEnumImpl implements CurrencyCalculator {


     EUR(new Currency("EUR", 0.85)),
     USD(new Currency("USD", 1.0)),
     GBP(new Currency("GBP", 0.75)),
     JPY(new Currency("JPY", 110.0)),
     CHF(new Currency("CHF", 0.92)),
     CAD(new Currency("CAD", 1.25)),
     AUD(new Currency("AUD", 1.35)),
     CNY(new Currency("CNY", 6.5)),
     INR(new Currency("INR", 74.0)),
     LBP(new Currency("LBP", 89731.97)),
     KWD(new Currency("KWD", 0.31)),
     BTC(new Currency("BTC", 0.00001429)),
     NZD(new Currency("NZD", 1.60)),
     SEK(new Currency("SEK", 10.40)),
     NOK(new Currency("NOK", 10.70)),
     DKK(new Currency("DKK", 6.90)),
     PLN(new Currency("PLN", 3.95)),
     CZK(new Currency("CZK", 22.80)),
     MXN(new Currency("MXN", 17.10)),
     BRL(new Currency("BRL", 5.10)),
     ZAR(new Currency("ZAR", 18.20)),
     SGD(new Currency("SGD", 1.35)),
     HKD(new Currency("HKD", 7.82)),
     AED(new Currency("AED", 3.67)),
     RUB(new Currency("RUB", 82.26));

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
        return toCurrency.exchangeRateToUSD / fromCurrency.exchangeRateToUSD;
    }

    @Override
    public void addCurrency(String name, double exchangeRate, String referenceCurrency) {
        throw new UnsupportedOperationException("Adding new currencies is not supported in the enum implementation.");
    }

    @Override
    public List<String> getCurrencyNames() {
        List<String> names = new java.util.ArrayList<>();
        for (CurrencyCalcEnumImpl c : CurrencyCalcEnumImpl.values()) {
            names.add(c.currency.name);
        }
        Collections.sort(names);
        return names;
    }

    private Currency findCurrencyByName(String name) {
        for (CurrencyCalcEnumImpl c : CurrencyCalcEnumImpl.values()) {
            if (c.currency.name.equals(name)) {
                return c.currency;
            }
        }
        throw new IllegalArgumentException("Currency not found: " + name);
    }

    private List<Currency> getAllCurrencies() {
        List<Currency> currencies = new java.util.ArrayList<>();
        for (CurrencyCalcEnumImpl c : CurrencyCalcEnumImpl.values()) {
            currencies.add(c.currency);
        }
        return currencies;
    }

    @Override
    public void saveCurrencyData(List<Currency> currencies) {
        new DataWriter().writeDate(currencies);
    }

    @Override
    public void loadCurrencyData() {
        throw new UnsupportedOperationException("Loading currencies is not supported in the enum implementation.");
    }

}
