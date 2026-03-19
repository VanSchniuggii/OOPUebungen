package uebungen.src.main.java.com.hszg.exercises.aufgabe1.Logic;

import java.util.ArrayList;
import java.util.List;

public class CurrencyCalcImpl implements CurrencyCalculator {

    List<Currency> currencies = new ArrayList<>();

    public CurrencyCalcImpl() {
        currencies.add(new Currency("EUR", 0.85));
        currencies.add(new Currency("USD", 1.0));
        currencies.add(new Currency("GBP", 0.75));
        currencies.add(new Currency("JPY", 110.0));
        currencies.add(new Currency("CHF", 0.92));
    }

    @Override
    public double convert(double amount, String fromCurrency, String toCurrency) {
        double exchangeRate = 0d;

        for (Currency from : currencies) {
            if (from.name.equals(fromCurrency)) {
                for (Currency to : currencies) {
                    if (to.name.equals(toCurrency)) {
                        exchangeRate = getExchangeRate(from, to);
                        break;
                    }
                }
                break;
            }
        }

        return amount * exchangeRate;
        
    }

    @Override
    public double getExchangeRate(Currency fromCurrency, Currency toCurrency) {
        return fromCurrency.exchangeRateToUSD/toCurrency.exchangeRateToUSD;
    }

    @Override
    public void addCurrency(String name, double exchangeRate, String referenceCurrency) {
        double exchangeRateToUSD = 0d;

        for (Currency c : currencies) {
            if (c.name.equals(referenceCurrency)) {
                exchangeRateToUSD = exchangeRate * c.exchangeRateToUSD;
                break;
            }
        }

        currencies.add(new Currency(name, exchangeRateToUSD));
    }
    
    public List<Currency> getCurrencies() {
        return currencies;
    }    

}
