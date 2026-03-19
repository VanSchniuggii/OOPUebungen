package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;

public interface CurrencyCalculator {
    double convert(double amount, String fromCurrency, String toCurrency);
    double getExchangeRate(String fromCurrency, String toCurrency);
}
