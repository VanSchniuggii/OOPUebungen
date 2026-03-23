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
    
    public double normalizeValue(String input) {
			if (input == null) {
				throw new IllegalArgumentException("Input cannot be null.");
			}

			String s = input.trim();
			if (s.isEmpty()) {
				throw new IllegalArgumentException("Input cannot be empty.");
			}

			s = s.replace(" ", "").replace("'", "");

			int lastComma = s.lastIndexOf(',');
			int lastDot = s.lastIndexOf('.');

			if (lastComma >= 0 && lastDot >= 0) {
				if (lastComma > lastDot) {
					s = s.replace(".", "");
					s = s.replace(",", ".");
				} else {
					s = s.replace(",", "");
				}
			} else if (lastComma >= 0) {
				s = s.replace(",", ".");
			}

			if (!s.matches("-?\\d+(\\.\\d+)?")) {
				throw new IllegalArgumentException("Input must be a valid number.");
			}

			return Double.parseDouble(s);
	};

}
