package uebungen.src.test.java;

import uebungen.src.main.java.com.hszg.exercises.aufgabe1bis3.Logic.CurrencyCalcImpl;
import uebungen.src.main.java.com.hszg.exercises.aufgabe1bis3.Logic.CurrencyCalculator;
import uebungen.src.main.java.com.hszg.exercises.aufgabe1bis3.Logic.Currency;

public class CurrencyCalcTest {

    static CurrencyCalculator currencyCalculator = new CurrencyCalcImpl();

    public static void main(String[] args) {
        boolean failed = false;

        if (testGetExchangeRate()) {
            failed = true;
        }

        if (testConvert()) {
            failed = true;
        }

        if (testAddCurrency()) {
            failed = true;
        }

        if (!failed) {
            System.out.println("All tests passed!");
        }
    }

    static private boolean testGetExchangeRate() {
        
        boolean failed = false;

        Currency[] currencies = {
            new Currency("EUR", 0.85),
            new Currency("USD", 1.0),
            new Currency("GBP", 0.75),
            new Currency("JPY", 110.0)
        };

        double exchangeRate = currencyCalculator.getExchangeRate(currencies[0], currencies[1]);

        if (exchangeRate != 1/0.85) {
            failed = true;
            System.out.println("Test EUR to USD failed: expected " + (1/0.85) + ", got " + exchangeRate);
        }

        exchangeRate = currencyCalculator.getExchangeRate(currencies[1], currencies[0]);

        if (exchangeRate != 0.85) {
            failed = true;
            System.out.println("Test USD to EUR failed: expected 0.85, got " + exchangeRate);
        }
        
        exchangeRate = currencyCalculator.getExchangeRate(currencies[0], currencies[2]);

        if (exchangeRate != 0.75/0.85) {
            failed = true;
            System.out.println("Test EUR to GBP failed: expected " + (0.75/0.85) + ", got " + exchangeRate);
        }

        return failed;
    }

    static private boolean testConvert() {
        boolean failed = false;

        double convertedAmount = currencyCalculator.convert(100, "EUR", "USD");

        if (convertedAmount != 100/0.85) {
            failed = true;
            System.out.println("Test convert EUR to USD failed: expected " + (100/0.85) + ", got " + convertedAmount);
        }

        convertedAmount = currencyCalculator.convert(100, "USD", "EUR");

        if (convertedAmount != 85) {
            failed = true;
            System.out.println("Test convert USD to EUR failed: expected 85, got " + convertedAmount);
        }

        convertedAmount = currencyCalculator.convert(100, "EUR", "GBP");

        if (convertedAmount != 100*0.75/0.85) {
            failed = true;
            System.out.println("Test convert EUR to GBP failed: expected " + (100*0.75/0.85) + ", got " + convertedAmount);
        }

        return failed;
    }

    static private boolean testAddCurrency() {
        boolean failed = false;

        currencyCalculator.addCurrency("TST", 1.5, "USD");

            double convertedAmount = currencyCalculator.convert(100, "TST", "USD");
            if (Math.abs(convertedAmount - 150.0) > 1e-9) {
                failed = true;
                System.out.println("Test convert AUD to USD failed: expected 150.0, got " + convertedAmount);
            }

             convertedAmount = currencyCalculator.convert(100, "USD", "AUD");

        return failed;
    }



}
