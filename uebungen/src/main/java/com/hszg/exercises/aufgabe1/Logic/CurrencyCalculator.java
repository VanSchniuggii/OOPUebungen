package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;

import java.util.List;

public interface CurrencyCalculator {
    double convert(double amount, String fromCurrency, String toCurrency);
    double getExchangeRate(Currency fromCurrency, Currency toCurrency);
    void addCurrency(String name, double exchangeRate, String referenceCurrency);
    List<String> getCurrencyNames();
}
